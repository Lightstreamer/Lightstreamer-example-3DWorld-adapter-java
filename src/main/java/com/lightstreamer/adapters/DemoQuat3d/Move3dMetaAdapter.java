/*
 * 
 *  Copyright (c) Lightstreamer Srl
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * 
 */

package com.lightstreamer.adapters.DemoQuat3d;

import java.io.File;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lightstreamer.adapters.metadata.LiteralBasedProvider;
import com.lightstreamer.interfaces.metadata.CreditsException;
import com.lightstreamer.interfaces.metadata.NotificationException;
import com.lightstreamer.interfaces.metadata.MetadataProviderException;
import com.lightstreamer.interfaces.metadata.TableInfo;

public class Move3dMetaAdapter extends LiteralBasedProvider {

    private static Logger logger;
    
    /**
     * Private logger; a specific "LS_3DWorldDemo_Logger" category
     * should be supplied by logback configuration.
     */
    public static Logger tracer = null;
    
    /**
     * Keeps the client context information supplied by Lightstreamer on the
     * new session notifications.
     * Session information is needed to pass the IP to logging purpose.
     */
    private ConcurrentHashMap<String,Map<String,String>> sessions = new ConcurrentHashMap<String,Map<String,String>>();
    
    /**
     * 
     * Executor for tasks of bandwidth polls.
     * 
     */
    private final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    
    private final ConcurrentHashMap<String, String> players = new ConcurrentHashMap<String, String>();
    private final static ConcurrentHashMap<String, PollsBandwidth> checkBandWidths = new ConcurrentHashMap<String, PollsBandwidth>();
    
    private static String LOGON_CUSTOM_PREFIX = "c_logon_";
    private static String BAND_PREFIX = "My_Band_";
    private int jmxPort = 9999;
    private static int maxSrvSideUsers = 10;
    private static int curSrvSideUsers = 10;
    
    
    @Override
    public void init(Map params, File configDir) throws MetadataProviderException {
        super.init(params,configDir);
        
        logger = LogManager.getLogger("LS_demos_Logger.Move3dDemo");
        
        try{
            tracer = LogManager.getLogger("LS_3DWorldDemo_Logger.tracer");
        } catch (Exception e) {
            logger.warn("Error on tracer initialization.",  e);            
        }
        
        if (params.containsKey("jmxPort")) {
            this.jmxPort = new Integer((String)params.get("jmxPort")).intValue();
        }
        logger.info("JMX Port:" + this.jmxPort);
        
        if (params.containsKey("Max_Srv_Players")) {
            maxSrvSideUsers = new Integer((String)params.get("Max_Srv_Players")).intValue();
        }
        logger.debug("Max server side users:" + maxSrvSideUsers);
        curSrvSideUsers = 0;
    }
    
    @Override
    public boolean wantsTablesNotification(java.lang.String user) {
        return true;
    }
    
    public static void killBandChecker(String itemName) {
        PollsBandwidth p = checkBandWidths.get(itemName);
        if ( p != null ) {
            p.getTask().cancel(true);
            checkBandWidths.remove(itemName);
        }
    }
    
    public static double getTotalBandwidthOut() {
        double sum = 0.0;
        Enumeration<PollsBandwidth> e = checkBandWidths.elements();
        PollsBandwidth p;
        while ( e.hasMoreElements() ) {
            p = e.nextElement();
            sum += p.getBandwidth();
        }
        
        return sum;
    }
    
    @Override
    public void notifySessionClose(String session) throws NotificationException {
        //we have to remove session information from the session HashMap
        sessions.remove(session);
    }
    
    @Override
    public void notifyNewSession(String user, String session, Map sessionInfo) throws CreditsException, NotificationException {
        // Register the session details on the sessions HashMap.
        sessions.put(session, sessionInfo);
    }
    
    @Override
    public void notifyTablesClose(java.lang.String sessionID, TableInfo[] tables) {
        if ( tables[0].getId().startsWith("ServerSide") ) {
            if (curSrvSideUsers > 0) {
                curSrvSideUsers--;
            }
        }
    }
                     
    @Override
    public void notifyNewTables(java.lang.String user, java.lang.String sessionID, TableInfo[] tables) throws CreditsException {
         if (tables[0].getId().startsWith(LOGON_CUSTOM_PREFIX)) {
            String item = tables[0].getId().substring(LOGON_CUSTOM_PREFIX.length());
            String pieces[] = item.split("_");
            
            if ( Move3dAdapter.tooManyUsers() ) {
                throw new CreditsException(-3, "Too many users, please wait ... ");
            }
            
            if  ( Move3dAdapter.worldOvercrwoded(tables[0].getId()) ) {
                throw new CreditsException(-3, "This world is full and you are a watcher. Teleport yourself to another world to become an active player");
            }
            
            if (pieces.length > 1) {
                players.put(sessionID, pieces[1]);   
            }
        } else if ( tables[0].getId().startsWith(BAND_PREFIX) ) {
            String usr = tables[0].getId().substring(BAND_PREFIX.length());
            PollsBandwidth p = new PollsBandwidth(sessionID, usr, this.jmxPort);
            ScheduledFuture<?> tsk = executor.scheduleAtFixedRate(p,10,2000,TimeUnit.MILLISECONDS);
            
            p.setTask(tsk);
            checkBandWidths.put(tables[0].getId(), p);
        } else if ( tables[0].getId().startsWith("ServerSide") ) {
            if (curSrvSideUsers < maxSrvSideUsers) {
                curSrvSideUsers++;
                logger.debug("Current Server side players: " + curSrvSideUsers);
            } else {
                logger.warn("Too many server side players, upgrade rejected.");
                throw new CreditsException(-3, "Too many server side players!");
            }
        }
    }
    
    public static void terminateUser(String usr) {
        PollsBandwidth p = checkBandWidths.get(BAND_PREFIX + usr);
        if (p != null ) {
            p.getTask().cancel(true);
            p.forceMeOut();
            checkBandWidths.remove(BAND_PREFIX + usr);
        } else {
            logger.warn("terminateUser failed for user: " + usr);
        }
    }
    
    @Override
    public CompletionStage<String> notifyUserMessage(String user, String sessionID, String message) {

        // we won't introduce blocking operations, hence we can proceed inline

        if (message == null) {
            return null;
        }
        
        if ( message.startsWith("n|") ) {
            // Set new NickName for the players. 
            try {
                String newNick = message.split("\\|")[1];
                String ip = "";
                String player = players.get(sessionID);
                Map<String,String> sessionInfo = sessions.get(sessionID);
                
                if (sessionInfo == null) {
                     logger.warn("New nick received from non-existent session: " + message);
                } else {
                    //  read from info the IP of the user
                    ip =  sessionInfo.get("REMOTE_IP");
                }
                
                tracer.info("New nickname: " + newNick + " from " + player + " (" + ip + ").");
                
                Move3dAdapter.myWorld.changeNickName(players.get(sessionID), newNick);
            } catch (Exception e) {
                // Skip, message not well formatted
                logger.warn("Message not well formatted, skipped.", e);
            }
        } else if ( message.startsWith("m|") ) {
            // Set new NickName for the players.
            String newMsg = "";
            try {
                newMsg = message.split("\\|")[1];
                
            } catch (ArrayIndexOutOfBoundsException aiobe) {
                // Skip, message not well formatted
                logger.warn("Message not well formatted, skipped.", aiobe);
            }
            try {
                String ip = "";
                String player = players.get(sessionID);
                Map<String,String> sessionInfo = sessions.get(sessionID);
                
                if (sessionInfo == null) {
                     logger.warn("Message received from non-existent session: " + message);
                } else {
                    //  read from info the IP of the user
                    ip =  sessionInfo.get("REMOTE_IP");
                }
                
                tracer.info("New message: " + newMsg + " from " + player + " (" + ip + ").");
                
                Move3dAdapter.myWorld.updateMyMsg(player, newMsg);
            }  catch (Exception e) {
                // Skip, message not well formatted
                logger.warn("Unexpected error handling new message from user.", e);
            }
        } else {
            Move3dAdapter.myWorld.dispatchMsgs(players.get(sessionID) + "|" + message);
            logger.debug("Input command from user " + players.get(sessionID) + ": " + message);
        }

        return CompletableFuture.completedStage(null);
    }
    
}
