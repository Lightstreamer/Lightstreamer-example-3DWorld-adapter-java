/*
 * 
 *  Copyright 2013 Weswit s.r.l.
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.nio.ByteBuffer;

import javax.management.ObjectName;


import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.lightstreamer.interfaces.data.DataProviderException;
import com.lightstreamer.interfaces.data.FailureException;
import com.lightstreamer.interfaces.data.ItemEventListener;
import com.lightstreamer.interfaces.data.SmartDataProvider;
import com.lightstreamer.interfaces.data.SubscriptionException;

public class Move3dAdapter implements SmartDataProvider {

    private static String CUSTOM_WORLD = "Custom_list";
    private static String BAND_PREFIX = "My_Band_";
    private static String LOGON_CUSTOM_PREFIX = "c_logon_";
    private static String DEFAULT_WORLD = "Default";
    private static String STATISTICS = "Statistics";
    
    private static int MAX_INACTIVITY = 30000;
    private static int MAX_PLAYERS = 20;
    private static int TOTAL_MAX_PLAYERS = 50; 
    private static int GHOST_PLAYERS = 0;
    
    private boolean goStats = false;
    
    public static Logger logger;
    
    /**
     * Private logger; a specific "LS_Move3dDemo_Logger" category
     * should be supplied by log4j configuration.
     */
    public static Logger tracer = null;
    
    private Object listHandle;
    
    /**
     * The listener of updates set by Lightstreamer Kernel.
     */
    private static ItemEventListener listener = null;
    
    /**
     * Used to enqueue the calls to the listener.
     */
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    
    private static final ConcurrentHashMap<String, ArrayList<String>> worldsPrecisions =
            new ConcurrentHashMap<String, ArrayList<String>>();
    
    private static final ConcurrentHashMap<String, ArrayList<String>> customWorlds =
            new ConcurrentHashMap<String, ArrayList<String>>();
    
    private static final ConcurrentHashMap<String, String> userWorldMap =
            new ConcurrentHashMap<String, String>();
    
    private static final ConcurrentSkipListSet<String> subscribed = new  ConcurrentSkipListSet<String>();
    
    public static TheWorld myWorld = new TheWorld();
    
    private String roundToSend(double value, String precision) {
        String tmp = ""+value;

        int cut = tmp.length() - tmp.indexOf(".") - 1;
        int pre;
        try {
            pre = new Integer(precision).intValue();
        } catch (NumberFormatException  nfe) {
            logger.warn("Precision requested invalid (" + precision +") 8 assumed.");
            pre = 8;
        }
            
        if (pre < cut  ) {
            tmp = tmp.substring(0, tmp.length() - (cut - pre));
        }
        
        return tmp;
    }

    private String roundToSend(double value, int pre) {
        String tmp = ""+value;

        int cut = tmp.length() - tmp.indexOf(".") - 1;
        if (pre < cut  ) {
            tmp = tmp.substring(0, tmp.length() - (cut - pre));
        }
        
        return tmp;
    }
    
    private static byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    private static byte[] toByteArray(float value) {
        byte[] bytes = new byte[4];
        ByteBuffer.wrap(bytes).putFloat(value);
        return bytes;
    }
    
    private static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
    
    private static boolean killGhosts( Iterator<String> playersList, String world) {
        String player = "";
        
        while ( playersList.hasNext() ) {
            player = playersList.next();
            if ( player.startsWith("GhostPlayer_") ) {
                playersList.remove();
                updateList("DELETE", player, world, CUSTOM_WORLD);
                
                return true;
            }
        }
        
        return false;
    }
    
    private String RegenOneGhost(ArrayList<String> playersList) {
        BaseModelBody ghost;
        double v = Math.random();
        String ghostName = "GhostPlayer_" + (int)(v*10000)%10000;
                
        playersList.add(ghostName);
    
        ghost = myWorld.addPlayerBody(ghostName, this);
        if ( v < 0.27 ) {
            ghost.setTourque(IBody.Axis.X, 1);
        } else if ( v < 0.39) {
            ghost.setTourque(IBody.Axis.Y, -3);
        } else if ( v < 0.50) {
            ghost.setTourque(IBody.Axis.Z, 5);
        } else if ( v < 0.67) {
            ghost.setTourque(IBody.Axis.X, 5);
        } else if ( v < 0.80) {
            ghost.setTourque(IBody.Axis.Z, -1);
        } else {
            // No rotation for this ghost.
        }
        
        v = Math.random();
        if ( v < 0.29 ) {
            ghost.setImpulse(IBody.Axis.X, 15.0);
         } else if ( v < 0.40 ) {
             ghost.setImpulse(IBody.Axis.Y, -5.0);
        } else if ( v < 0.51) {
            ghost.setImpulse(IBody.Axis.Z, 10.0);
        } else if ( v < 0.62) {
            ghost.setImpulse(IBody.Axis.Z, -5.0);
        } else if ( v < 0.77) {
            ghost.setImpulse(IBody.Axis.X, -8.0);
        } else if ( v < 0.85) {
            ghost.setImpulse(IBody.Axis.Y, 50.0);
        } else {
            // No impulse for this ghost player.
        }
        
        return ghost.getOrigName();
    }
    
    public static boolean worldOvercrwoded(String world) {
        String parser = world.substring(LOGON_CUSTOM_PREFIX.length());
        String pieces[] = parser.split("_");
        
        synchronized(myWorld) {
            logger.debug("Ask for custom world " + pieces[0] + " user: " + pieces[1]);
        
            if (pieces.length > 1) {
                if ( customWorlds.containsKey(pieces[0]) ) {
                    ArrayList<String> playersList = customWorlds.get(pieces[0]);
                    if ( playersList.size() >= MAX_PLAYERS ) {
                        // Check for Ghost Players.
                        if ( !killGhosts(playersList.iterator(), pieces[0]) ) {
                            // Max entry list for world 
                            logger.warn("Custom world " + pieces[0] + " is overcrowded; subscription rejected.");
                            
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    public static boolean tooManyUsers() {
        ArrayList<String> playersList = null;
        int sum = 0;
        
        synchronized(myWorld) {
            Enumeration<ArrayList<String>> es = customWorlds.elements();
            while ( es.hasMoreElements()) {
                playersList = es.nextElement();
                sum = sum + playersList.size();
            }
        }
        
        if ( sum > TOTAL_MAX_PLAYERS ) {
            logger.warn("Total players exceed TOTAL MAX PLAYERS limit (" + sum + ").");
            return true;
        }
        
        return false;
    }
    
    public void sumTotalPlayer() {
        ArrayList<String> playersList = null;
        int sum = 0;
        
        synchronized(myWorld) {
            Enumeration<ArrayList<String>> es = customWorlds.elements();
            while ( es.hasMoreElements()) {
                playersList = es.nextElement();
                sum = sum + playersList.size();
            }
        }
        
        logger.info("STATISTICS - Total players in the demo: " + sum + ".");
        if ( listener != null && goStats) {
            HashMap<String, String> update = new HashMap<String, String>();
            
            update.put("total_players", sum+"");
            listener.update(STATISTICS, update, false);
        }
        
        return ;
    }

    public void postOverallBandwidth() {
        double totBandwidth = Move3dMetaAdapter.getTotalBandwidthOut();
        
        logger.info("STATISTICS - Total bandwidth for the demo: " + totBandwidth + ".");
        if ( listener != null && goStats) {
            HashMap<String, String> update = new HashMap<String, String>();
            
            update.put("total_bandwidth", totBandwidth+"");
            listener.update(STATISTICS, update, false);
        }
        
        return ;
    }
    
    public static void postBandwith(String itemName, Double d) {
        final HashMap<String, String> update = new HashMap<String, String>();
        update.put("currentBandwidth", d.toString());
        if ( tracer != null ) {
            tracer.debug("Update current bandwidth for user " + itemName + ": " + d);
        }
        
        listener.update(itemName,update,false);
    }
    
    public void sendCommands(String user, String cmd) {
        HashMap<String, String> update = new HashMap<String, String>();
        
        update.put("Cmd", cmd);
        
        return ;
    }
    
    public void sendUpdates(String user, BaseModelBody box) {
        try {
            String s = null;
            int indx = 0;
            String precision;
            Iterator<String> i = null;

            synchronized (myWorld) {
                
                /*
                if ( userWorldMap.containsKey(user) ) {
                    ArrayList <String> aL = worldsPrecisions.get(userWorldMap.get(user)); 
                    if (aL != null) {
                        i = aL.iterator();
                    }
                } else {
                    if ( !user.startsWith("GhostPlayer_") ) {
                        
                        logger.warn("worldsPrecisions void for " + s);
                        
                        if ( !myWorld.playerGameOver(user) ) {
                            logger.warn("Game over procedure failed for " + user + " player (unknow player).");
                            throw new SubscriptionException("Unknow player.");
                        } else {
                            logger.info(user + " game over!");
                        }
                    }
                    
                    return ;
                }
                    
                */
                Enumeration<String> e = customWorlds.keys();
                while ( e.hasMoreElements()) {
                    s = e.nextElement();
                    if ( (customWorlds.get(s)).contains(user) ) {
                        ArrayList <String> aL = worldsPrecisions.get(s);
                        if (aL != null) {
                            i = aL.iterator();
                        }
                    } 
                }
            
                if ( i == null ) {
                    if ( !user.startsWith("GhostPlayer_") ) {
                        
                        logger.warn("worldsPrecisions void for " + s);
                        
                        if ( !myWorld.playerGameOver(user) ) {
                            logger.warn("Game over procedure failed for " + user + " player (unknow player).");
                            // throw new SubscriptionException("Unknow player.");
                        } else {
                            logger.info(user + " game over!");
                        }
                    }
                    
                    return ;
                }
            
                if ( i == null ) {
                    return ;                    
                }
                
                while (i.hasNext()) {
                    precision = i.next();
                    if (subscribed.contains(user+precision)) {
                        
                        if ( !user.startsWith("GhostPlayer_") ) {
                            if ( box.getInactivityPeriod() > MAX_INACTIVITY ) {

                                logger.info(user + " gamed over for inactivity.");
                            
                                Move3dMetaAdapter.terminateUser(user);
                                
                                if ( !myWorld.playerGameOver(user) ) {
                                    logger.warn("Game over procedure failed for " + user + " player (unknow player).");
                                    // throw new SubscriptionException("Unknow player.");
                                }
                            
                                return ;
                            }
                        }
                        
                        HashMap<String, String> update = new HashMap<String, String>();
                        update.put("nick", box.getNickName());
                        update.put("msg", box.getLastMsg());
                        update.put("lifeSpan", box.getLifeSpan()+"");

                        if ( precision.equals("_bd") ) {
                            s = (new Base64Manager()).encodeBytes(toByteArray(box.getX()),true);
                            indx = s.indexOf("=");
                            update.put("posX", s.substring(0, indx));
                            s = (new Base64Manager()).encodeBytes(toByteArray(box.getY()),true);
                            indx = s.indexOf("=");
                            update.put("posY", s.substring(0, indx));
                            s = (new Base64Manager()).encodeBytes(toByteArray(box.getZ()),true);
                            indx = s.indexOf("=");
                            update.put("posZ", s.substring(0, indx));
                            s = (new Base64Manager()).encodeBytes(toByteArray(box.getAxisAngle().toQuat().getX()),true);
                            indx = s.indexOf("=");
                            update.put("rotX", s.substring(0, indx));
                            s = (new Base64Manager()).encodeBytes(toByteArray(box.getAxisAngle().toQuat().getY()),true);
                            indx = s.indexOf("=");
                            update.put("rotY", s.substring(0, indx));
                            s = (new Base64Manager()).encodeBytes(toByteArray(box.getAxisAngle().toQuat().getZ()),true);
                            indx = s.indexOf("=");
                            update.put("rotZ", s.substring(0, indx));
                            s = (new Base64Manager()).encodeBytes(toByteArray(box.getAxisAngle().toQuat().getW()),true);
                            indx = s.indexOf("=");
                            update.put("rotW", s.substring(0, indx));
                        } else if ( precision.equals("_bs") ) {
                            s = (new Base64Manager()).encodeBytes(toByteArray((float)box.getX()),true);
                            indx = s.indexOf("=");
                            update.put("posX", s.substring(0, indx));
                            s = (new Base64Manager()).encodeBytes(toByteArray((float)box.getY()),true);
                            indx = s.indexOf("=");
                            update.put("posY", s.substring(0, indx));
                            s = (new Base64Manager()).encodeBytes(toByteArray((float)box.getZ()),true);
                            indx = s.indexOf("=");
                            update.put("posZ", s.substring(0, indx));
                            s = (new Base64Manager()).encodeBytes(toByteArray((float)box.getAxisAngle().toQuat().getX()),true);
                            indx = s.indexOf("=");
                            update.put("rotX", s.substring(0, indx));
                            s = (new Base64Manager()).encodeBytes(toByteArray((float)box.getAxisAngle().toQuat().getY()),true);
                            indx = s.indexOf("=");
                            update.put("rotY", s.substring(0, indx));
                            s = (new Base64Manager()).encodeBytes(toByteArray((float)box.getAxisAngle().toQuat().getZ()),true);
                            indx = s.indexOf("=");
                            update.put("rotZ", s.substring(0, indx));
                            s = (new Base64Manager()).encodeBytes(toByteArray((float)box.getAxisAngle().toQuat().getW()),true);
                            indx = s.indexOf("=");
                            update.put("rotW", s.substring(0, indx));
                        } else {
                            int px = 8;
                            try {
                                px = new Integer(precision.substring(2)).intValue();
                            } catch (NumberFormatException  nfe) {
                                logger.warn("Precision requested invalid (" + precision +") 8 assumed.");
                            }
                            update.put("posX", roundToSend(box.getX(), px));
                            update.put("posY", roundToSend(box.getY(), px));
                            update.put("posZ", roundToSend(box.getZ(), px));
                            update.put("rotX", roundToSend(box.getAxisAngle().toQuat().getX(), px));
                            update.put("rotY", roundToSend(box.getAxisAngle().toQuat().getY(), px));
                            update.put("rotZ", roundToSend(box.getAxisAngle().toQuat().getZ(), px));
                            update.put("rotW", roundToSend(box.getAxisAngle().toQuat().getW(), px));
                        }
                        
                        update.put("dVx", box.getvX()+"");
                        update.put("dVy", box.getvY()+"");
                        update.put("dVz", box.getvZ()+"");
                        update.put("dRx", box.getDeltaRotX()+"");
                        update.put("dRy", box.getDeltaRotY()+"");
                        update.put("dRz", box.getDeltaRotZ()+"");
                        
                        if ( tracer != null ) {
                            tracer.debug("Update for " + user+precision);
                        }
                            
                        if (listener != null) {
                            listener.update(user+precision,update,false);
                        }
                    }
                }  
            }
        } catch (Exception e) {
            // Skip.
            logger.warn("Exception in update procedure.", e);
        }
        
    }    
    
    private static void updateList(final String command, final String key, final String world, final String list) {
        //If we have a listener create a new Runnable to be used as a task to pass the
        //new update to the listener
        
        if ( listener == null ) {
            return ;
        }
        
        Runnable updateTask = new Runnable() {
            @Override
            public void run() {
                String precision = "";
                Iterator<String> i;
                
                logger.info("Update players list for world: " + world);
                
                synchronized(myWorld) {
                    ArrayList<String> listprs = worldsPrecisions.get(world);
                    if ( listprs  == null ) {
                       return ;
                    }
                    
                    i = listprs.iterator();
                    while (i.hasNext()) {
                        HashMap<String, String> update = new HashMap<String, String>();
                        update.put("command", command);
                        
                        precision = i.next();
                        update.put("key", key+precision);
                    
                        String itemName = "";
                        if ( world != null ) {
                            logger.debug("Update list " + list+"_"+world+precision + " " + command + " " + key+precision);
                            itemName = list+"_"+world+precision;
                        } else {
                            logger.debug("Update list " + list+precision + " " + command + " " + key+precision);
                            itemName = list+precision;
                        }
                        
                        listener.update(itemName, update, false);
                    }
                }
            }
         };

        //We add the task on the executor to pass to the listener the actual status
        executor.execute(updateTask);
    }

    private void addList(final String command, final String itemName, final String key) {
        final HashMap<String, String> update = new HashMap<String, String>();
        
        if ( listener == null ) {
            return ;
        }
        
        update.put("command", command);
        update.put("key", key);
        
        //If we have a listener create a new Runnable to be used as a task to pass the
        //new update to the listener
        Runnable updateTask = new Runnable() {
            @Override
            public void run() {
                logger.debug("Add list " + itemName + " " + command + " " + key);
                    
                listener.update(itemName, update, false);
            }
         };

        //We add the task on the executor to pass to the listener the actual status
        executor.execute(updateTask);
    }
    
    private void sendListEOS() {
        final Object currHandle = listHandle;
        if (currHandle == null) {
            return;
        }

        //If we have a listener create a new Runnable to be used as a task to pass the
        //new update to the listener
        Runnable eosTask = new Runnable() {
            @Override
            public void run() {
                // call the update on the listener;
                // in case the listener has just been detached,
                // the listener should detect the case
                listener.smartEndOfSnapshot(currHandle);
            }
        };

        //We add the task on the executor to pass to the listener the actual status
        executor.execute(eosTask);

    }
    
    public Move3dAdapter() {

    }
    
    @Override
    public void init(Map params, File configDir) throws DataProviderException {
        String logConfig = (String) params.get("log_config");
        
        if (logConfig != null) {
            File logConfigFile = new File(configDir, logConfig);
            String logRefresh = (String) params.get("log_config_refresh_seconds");
            if (logRefresh != null) {
                DOMConfigurator.configureAndWatch(logConfigFile.getAbsolutePath(), Integer.parseInt(logRefresh) * 1000);
            } else {
                DOMConfigurator.configure(logConfigFile.getAbsolutePath());
            }
            
            tracer = Logger.getLogger("LS_Move3dDemo_Logger");
        }
        try {
            logger = Logger.getLogger("LS_demos_Logger.Move3dDemo");
           
        } catch (Exception e) { 
            System.out.println("Loggers failed to load: " + e);
        }
        
        
        synchronized(myWorld) {
            myWorld.setListener(this);
    
            if (params.containsKey("frameRate")) {
                logger.debug("frameRate configured: " + params.get("frameRate"));
                myWorld.setFrameRate(new Integer((String)params.get("frameRate")).intValue());
            }
        
            if ( !myWorld.started() ) {
                myWorld.start();
            }
            
            if (params.containsKey("Max_Inactivity")) {
                logger.debug("Max_Inactivity configured: " + params.get("Max_Inactivity"));
                MAX_INACTIVITY = (new Integer((String)params.get("Max_Inactivity")).intValue());
            }
            
            if (params.containsKey("overcrowded")) {
                logger.debug("overcrowded configured: " + params.get("overcrowded"));
                MAX_PLAYERS = (new Integer((String)params.get("overcrowded")).intValue());
            }
            
            if (params.containsKey("Max_Players")) {
                logger.debug("overcrowded configured: " + params.get("Max_Players"));
                TOTAL_MAX_PLAYERS = (new Integer((String)params.get("Max_Players")).intValue());
            }
            
            if (params.containsKey("Ghost_Players")) {
                GHOST_PLAYERS = new Integer((String)params.get("Ghost_Players")).intValue();
            }
            ArrayList<String> aL = new ArrayList<String>();
            for (int ik = 0; ik < GHOST_PLAYERS; ik++) {
                BaseModelBody ghost;
                
                aL.add("GhostPlayer_" + ik);
                ghost = myWorld.addPlayerBody("GhostPlayer_" + ik, this);
                userWorldMap.put("GhostPlayer_" + ik, DEFAULT_WORLD);
                
                // Set initial rotation for Ghost
                double v = Math.random();
                if ( v < 0.22 ) {
                    ghost.setTourque(IBody.Axis.X, 1);
                } else if ( v < 0.5) {
                    ghost.setTourque(IBody.Axis.Y, -2);
                } else if ( v < 0.75) {
                    ghost.setTourque(IBody.Axis.Z, 3);
                } else if ( v < 0.9 ) {
                    ghost.setTourque(IBody.Axis.Z, 2);
                    ghost.setTourque(IBody.Axis.Y, -1);
                } else {
                    // No rotation for this ghost player.
                }  
                
                // Set initial impulse for ghost.
                v = Math.random();
                if ( v < 0.09 ) {
                    ghost.setImpulse(IBody.Axis.X, 15.0);
                 } else if ( v < 0.25 ) {
                     ghost.setImpulse(IBody.Axis.Y, -5.0);
                } else if ( v < 0.391) {
                    ghost.setImpulse(IBody.Axis.Z, 10.0);
                } else if ( v < 0.5) {
                    ghost.setImpulse(IBody.Axis.Z, -5.0);
                } else if ( v < 0.71) {
                    ghost.setImpulse(IBody.Axis.X, -8.0);
                } else if ( v < 0.80) {
                    ghost.setImpulse(IBody.Axis.Y, 50.0);
                } else {
                    // No impulse for this ghost player.
                }
            }
            customWorlds.put(DEFAULT_WORLD, aL);
        }
        
        logger.info("Move3dAdapter start!");
    }

    @Override
    public void setListener(ItemEventListener lstnr) {
        if (listener == null) {
            listener = lstnr;
        }
    }

    @Override
    public void unsubscribe(String itemName) throws SubscriptionException, FailureException {
        
        if (itemName.startsWith(LOGON_CUSTOM_PREFIX)) {
            String parser = itemName.substring(LOGON_CUSTOM_PREFIX.length());
            String pieces[] = parser.split("_");
            
            logger.debug("Logout for custom world " + pieces[0] + " user: " + pieces[1] + ".");

            synchronized (myWorld) {
                if (pieces.length > 1) {
                    if ( customWorlds.containsKey(pieces[0]) ) {
                        ArrayList<String> aL = customWorlds.get(pieces[0]);
                        
                        aL.remove(pieces[1]);
                        
                        updateList("DELETE", pieces[1], pieces[0], CUSTOM_WORLD);
                        
                        if (pieces[0].equals(DEFAULT_WORLD)) {
                            if ( (aL.size() < MAX_PLAYERS) && (aL.size() < (GHOST_PLAYERS)) ) {
                                String ghost = RegenOneGhost(aL);
                                if ( ghost != null ) {
                                    updateList("ADD", ghost, pieces[0], CUSTOM_WORLD);
                                }
                            }
                        }
                    } else {
                        //customWorlds.put(pieces[1], new TheWorld(pieces[1]));
                        logger.warn("Not find user in custom world " + pieces[0]);
                    }
                    userWorldMap.remove(pieces[1]);
                }
                
                sumTotalPlayer();
            }
        } else if (itemName.startsWith(CUSTOM_WORLD)) {
            synchronized (myWorld) {
                String parser = itemName.substring(CUSTOM_WORLD.length());
                String pieces[] = parser.split("_");
                
                if (pieces.length > 2) {
                    logger.debug("Unsubscribe request for " + itemName + ". Precision: " + pieces[2] + ", world name: " + pieces[1]);
                    
                    if (!worldsPrecisions.containsKey(pieces[1])) {
                        logger.debug("worldPrecisions not found this: " + pieces[2]);
                    } else {
                        ArrayList<String> aW = worldsPrecisions.get(pieces[1]);
                        if ( aW != null ) {
                            aW.remove("_"+pieces[2]);
                            logger.debug("worldPrecisions removed this: " + pieces[2]);
                        }
                    }
                        
                } else {
                    throw new SubscriptionException(CUSTOM_WORLD + " malformed.");
                }
            }
        } else if (itemName.startsWith(BAND_PREFIX)) {
            Move3dMetaAdapter.killBandChecker(itemName);
        } else if (itemName.startsWith(STATISTICS)) {
            goStats = false;
        } else {
            subscribed.remove(itemName);
            logger.info(itemName + " unsubscribed!");
        }
    }
        

    @Override
    public boolean isSnapshotAvailable(String itemName)  throws SubscriptionException {
        if (itemName.startsWith(CUSTOM_WORLD)) {
            return true;
        }
        return false;
    }

    @Override
    public void subscribe(String itemName, Object handle, boolean needsIterator) throws SubscriptionException,FailureException {
        
         if (itemName.startsWith(CUSTOM_WORLD)) {
            synchronized (myWorld) {
                String parser = itemName.substring(CUSTOM_WORLD.length());
                String pieces[] = parser.split("_");
                
                if (pieces.length > 2) {
                    logger.debug("Subscribe request for " + itemName + ". Precision: " + pieces[2] + ", world name: " + pieces[1]);
                    if ( customWorlds.containsKey(pieces[1]) ) {
                        // Estraggo la lista dei players di questo mondo.
                        //TheWorld w = customWorlds.get(pieces[1]);
                        ArrayList<String> aL = customWorlds.get(pieces[1]);
                        
                        ListIterator<String> keys = aL.listIterator();
    
                        while(keys.hasNext()) {
                            String user = keys.next();
                            this.addList("ADD", itemName, user+"_"+pieces[2]);
                        }
                        sendListEOS();
                        
                    } else {
                        logger.debug("Added custom world " + pieces[1]);
                        //customWorlds.put(pieces[1], new TheWorld(pieces[1])); 
                        ArrayList<String> aL = new ArrayList<String>();
                        customWorlds.put(pieces[1], aL);
                    }
                    
                    if (!worldsPrecisions.containsKey(pieces[1])) {
                        ArrayList<String> aW = new ArrayList<String>();
                        aW.add("_"+pieces[2]);
                        worldsPrecisions.put(pieces[1], aW);
                        
                        logger.debug("worldPrecisions !contains. Add " + pieces[2]);
                    } else {
                        ArrayList<String> aW = worldsPrecisions.get(pieces[1]);
                        aW.add("_"+pieces[2]);
                        logger.debug("worldPrecisions contains. Add " + pieces[2]);
                    }                     
                } else {
                    throw new SubscriptionException(CUSTOM_WORLD + " malformed.");
                }
            }
        } else if (itemName.startsWith(LOGON_CUSTOM_PREFIX)) {
            logger.debug("ItemName: " + itemName);
                
            String parser = itemName.substring(LOGON_CUSTOM_PREFIX.length());
            String pieces[] = parser.split("_");
                
            logger.debug("Logon for custom world " + pieces[0] + " user: " + pieces[1]);
            synchronized (myWorld) {    
                if (pieces.length > 1) {
                    if ( customWorlds.containsKey(pieces[0]) ) {
                        ArrayList<String> playersList = customWorlds.get(pieces[0]);
                        if ( playersList.size() < 20 ) {
                            playersList.add(pieces[1]);
                        } else {
                            // Max entry list for world. Should never go here, checked in metadata.
                            logger.warn("Custom world " + pieces[0] + " is overcrowded; subscription rejected.");
                            
                            throw new SubscriptionException("This world is overcrowded, please migrate elsewhere.");
                        }
                    } else {
                        //customWorlds.put(pieces[1], new TheWorld(pieces[1]));
                        logger.debug("Added custom world " + pieces[0]);
                        
                        ArrayList<String> aL = new ArrayList<String>();
                        aL.add(pieces[1]);
                        customWorlds.put(pieces[0], aL);
                    }
                    userWorldMap.put(pieces[1],pieces[0]);
                    
                    myWorld.addPlayerBody(pieces[1], this);
                    
                    updateList("ADD", pieces[1], pieces[0], CUSTOM_WORLD);
                }
                
                sumTotalPlayer();
            }
        } else if (itemName.startsWith(BAND_PREFIX)) {
            // Nothing to do.
        } else if (itemName.startsWith(STATISTICS)) {
            goStats = true;
        } else {
            subscribed.add(itemName);
            logger.info(itemName + " subscribed!");
        }

    }
    
    @Override
    public void subscribe(String itemName, boolean needsIterator)
            throws SubscriptionException, FailureException {
        // Never Called.

    }

}