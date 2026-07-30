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
import java.util.List;
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

import com.lightstreamer.adapter.common.InitException;
import com.lightstreamer.adapter.metadata.CreditsException;
import com.lightstreamer.adapter.metadata.LoginInfo;
import com.lightstreamer.adapter.metadata.MetadataAdapter;
import com.lightstreamer.adapter.metadata.SessionListener;
import com.lightstreamer.adapter.metadata.SessionRequestHandler;
import com.lightstreamer.adapter.metadata.SubscriptionHandler;
import com.lightstreamer.adapter.metadata.SubscriptionInfo;
import com.lightstreamer.adapter.metadata.SubscriptionStatistics;

public class Move3dMetaAdapter implements MetadataAdapter {

    private Logger logger;
    
    /**
     * Private logger; a specific "LS_3DWorldDemo_Logger" category
     * should be supplied by logback configuration.
     */
    public Logger tracer = null;
    
    /**
     * 
     * Executor for tasks of bandwidth polls.
     * 
     */
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    
    private final ConcurrentHashMap<String, PollsBandwidth> checkBandWidths = new ConcurrentHashMap<String, PollsBandwidth>();
    
    private static final String LOGON_CUSTOM_PREFIX = "c_logon_";
    private static final String BAND_PREFIX = "My_Band_";
    private int jmxPort = 9999;
    private int maxSrvSideUsers = 10;
    private int curSrvSideUsers = 10;
    
    private volatile Move3dAdapter dataAdapter;
    private String dataAdapterName;
    
    
    @Override
    public void init(Map<String, String> params, File configDir) throws InitException {
        logger = LogManager.getLogger("LS_demos_Logger.Move3dDemo");
        
        try{
            tracer = LogManager.getLogger("LS_3DWorldDemo_Logger.tracer");
        } catch (Exception e) {
            logger.warn("Error on tracer initialization.",  e);            
        }
        
        if (params.containsKey("jmxPort")) {
            this.jmxPort = Integer.parseInt((String)params.get("jmxPort"));
        }
        logger.info("JMX Port:" + this.jmxPort);
        
        if (params.containsKey("Max_Srv_Players")) {
            maxSrvSideUsers = Integer.parseInt((String)params.get("Max_Srv_Players"));
        }
        logger.debug("Max server side users:" + maxSrvSideUsers);
        curSrvSideUsers = 0;
    }
    
    @Override
    public void onDataAdapterAvailable(String name, Object adapter) {
        if (!(adapter instanceof Move3dAdapter)) {
            logger.warn("Data Adapter " + name + " is not of the expected type; ignoring it.");
            return;
        }
        if (dataAdapter != null) {
            logger.warn("A second Data Adapter (" + name + ") is available; ignoring it.");
            return;
        }
        dataAdapter = (Move3dAdapter) adapter;
        dataAdapter.setMetaAdapter(this);
        dataAdapterName = name;
        logger.info("Data Adapter " + name + " available.");
    }
    
    @Override
    public void onDataAdapterUnavailable(String name) {
        if (name.equals(dataAdapterName)) {
            if (dataAdapter != null) {
                dataAdapter.setMetaAdapter(null);
            }
            dataAdapter = null;
            dataAdapterName = null;
            logger.info("Data Adapter " + name + " unavailable.");
        }
    }
    
    public void killBandChecker(String itemName) {
        PollsBandwidth p = checkBandWidths.get(itemName);
        if ( p != null ) {
            p.getTask().cancel(true);
            checkBandWidths.remove(itemName);
        }
    }
    
    public double getTotalBandwidthOut() {
        double sum = 0.0;
        Enumeration<PollsBandwidth> e = checkBandWidths.elements();
        PollsBandwidth p;
        while ( e.hasMoreElements() ) {
            p = e.nextElement();
            sum += p.getBandwidth();
        }
        
        return sum;
    }
    
    public void terminateUser(String usr) {
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
    public CompletionStage<SessionRequestHandler> loginAsync(LoginInfo loginInfo) {
        //////////////////////////////////////////////////////////////////////////
        // WARNING: No authentication logic is implemented; any login is accepted.
        // This is suitable for demos only and MUST NOT be used in production.
        //////////////////////////////////////////////////////////////////////////
        String user = loginInfo.getUser();
        Map<String, String> clientContext = loginInfo.getClientContext();
        
        return CompletableFuture.completedFuture(new SessionRequestHandler() {
            private String sessionID;
            private String player;
            
            @Override
            public void onStart(SessionListener listener) {
                sessionID = listener.getSessionId();
            }
            
            @Override
            public void onClose() {
            }
            
            @Override
            public CompletionStage<SubscriptionHandler> newSubscription(SubscriptionInfo info) throws CreditsException {
                String itemRef = info.getItemRefs().get(0);
                
                // Validate subscription
                if (itemRef.startsWith(LOGON_CUSTOM_PREFIX)) {
                    if ( dataAdapter.tooManyUsers() ) {
                        throw new CreditsException(-3, "Too many users, please wait ... ");
                    }
                    
                    if  ( dataAdapter.worldOvercrwoded(itemRef) ) {
                        throw new CreditsException(-3, "This world is full and you are a watcher. Teleport yourself to another world to become an active player");
                    }
                } else if ( itemRef.startsWith("ServerSide") ) {
                    if (curSrvSideUsers >= maxSrvSideUsers) {
                        logger.warn("Too many server side players, upgrade rejected.");
                        throw new CreditsException(-3, "Too many server side players!");
                    }
                }
                
                return CompletableFuture.completedFuture(new SubscriptionHandler(info) {
                    @Override
                    public void onStart(com.lightstreamer.adapter.metadata.SubscriptionListener listener) {
                        if (itemRef.startsWith(LOGON_CUSTOM_PREFIX)) {
                            String item = itemRef.substring(LOGON_CUSTOM_PREFIX.length());
                            String pieces[] = item.split("_");
                            
                            if (pieces.length > 1) {
                                player = pieces[1];
                            }
                        } else if ( itemRef.startsWith(BAND_PREFIX) ) {
                            String usr = itemRef.substring(BAND_PREFIX.length());
                            PollsBandwidth p = new PollsBandwidth(sessionID, usr, jmxPort, dataAdapter);
                            ScheduledFuture<?> tsk = executor.scheduleAtFixedRate(p,10,2000,TimeUnit.MILLISECONDS);
                            
                            p.setTask(tsk);
                            checkBandWidths.put(itemRef, p);
                        } else if ( itemRef.startsWith("ServerSide") ) {
                            curSrvSideUsers++;
                            logger.debug("Current Server side players: " + curSrvSideUsers);
                        }
                    }
                    
                    @Override
                    public void onClose(List<SubscriptionStatistics> stats) {
                        if ( itemRef.startsWith("ServerSide") ) {
                            if (curSrvSideUsers > 0) {
                                curSrvSideUsers--;
                            }
                        }
                    }
                });
            }
            
            @Override
            public CompletionStage<String> userMessageAsync(String message) throws CreditsException {

                // we won't introduce blocking operations, hence we can proceed inline

                if (message == null) {
                    return null;
                }
                
                if ( message.startsWith("n|") ) {
                    // Set new NickName for the players. 
                    try {
                        String newNick = message.split("\\|")[1];
                        String ip = "";
                        
                        if (clientContext == null) {
                             logger.warn("New nick received from non-existent session: " + message);
                        } else {
                            //  read from info the IP of the user
                            ip =  clientContext.get("REMOTE_IP");
                        }
                        
                        tracer.info("New nickname: " + newNick + " from " + player + " (" + ip + ").");
                        
                        Move3dAdapter da = dataAdapter;
                        if (da != null) {
                            da.myWorld.changeNickName(player, newNick);
                        }
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
                        
                        if (clientContext == null) {
                             logger.warn("Message received from non-existent session: " + message);
                        } else {
                            //  read from info the IP of the user
                            ip =  clientContext.get("REMOTE_IP");
                        }
                        
                        tracer.info("New message: " + newMsg + " from " + player + " (" + ip + ").");
                        
                        Move3dAdapter da = dataAdapter;
                        if (da != null) {
                            da.myWorld.updateMyMsg(player, newMsg);
                        }
                    }  catch (Exception e) {
                        // Skip, message not well formatted
                        logger.warn("Unexpected error handling new message from user.", e);
                    }
                } else {
                    Move3dAdapter da = dataAdapter;
                    if (da != null) {
                        da.myWorld.dispatchMsgs(player + "|" + message);
                    }
                    logger.debug("Input command from user " + player + ": " + message);
                }

                return CompletableFuture.completedFuture(null);
            }
        });
    }
    
    @Override
    public void shutdown() {
        executor.shutdownNow();
    }
    
}
