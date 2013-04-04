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

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class TheWorld extends Thread {
    
    private static final ConcurrentHashMap<String, BaseModelBody> playersBody =
            new ConcurrentHashMap<String, BaseModelBody>();
    
    private static final ConcurrentHashMap<String, Move3dAdapter> playersListener =
            new ConcurrentHashMap<String, Move3dAdapter>();
    
    private Move3dAdapter listener = null;
    
    private Object listMutex = new Object();
    
    private String name = "Default";
    
    private int frameRate = 10;
    
    private boolean started = false;
    
    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }
    
    public TheWorld() {
        super();
    }
    
    public TheWorld(String name) {
        super();
        this.name = name;
        
        Move3dAdapter.logger.debug("World " + name + " created.");
    }

    public void dispatchMsgs(String msg) {
        String[] pieces = msg.split("\\|");
        BaseModelBody box = playersBody.get(pieces[0]);
        
        if ( box != null ) {
            if ( pieces[1].equals("87") ) {
                box.setImpulse(IBody.Axis.Y, 1.0);
            } else if ( pieces[1].equals("83") ) {
                box.setImpulse(IBody.Axis.Y, -1.0);
            } else if ( pieces[1].equals("65") ) {
                box.setImpulse(IBody.Axis.X, -1.0);
            } else if ( pieces[1].equals("68") ) {
                box.setImpulse(IBody.Axis.X, 1.0);
            } else if ( pieces[1].equals("49") ) {
                box.setImpulse(IBody.Axis.Z, 1.0);
            } else if ( pieces[1].equals("50") ) {
                box.setImpulse(IBody.Axis.Z, -1.0);
            } else if ( pieces[1].equals("1087") ) {
                box.setTourque(IBody.Axis.Y, 1.0);
            } else if ( pieces[1].equals("1083") ) {
                box.setTourque(IBody.Axis.Y, -1.0);
            } else if  ( pieces[1].equals("1065") ) {
                box.setTourque(IBody.Axis.X, -1.0);
            } else if ( pieces[1].equals("1068") ) {
                box.setTourque(IBody.Axis.X, 1.0);
            } else if  ( pieces[1].equals("1049") ) {
                box.setTourque(IBody.Axis.Z, 1.0);
            } else if ( pieces[1].equals("1050") ) {
                box.setTourque(IBody.Axis.Z, -1.0);
            }
            
            this.listener.sendCommands(box.getOrigName(), pieces[1]);  
        }
    }
    
    public boolean started() {
        return this.started;
    }
    
    public void setListener(Move3dAdapter listener) {
        this.listener = listener;
    }
    
    public Enumeration<String> getPlayersList() {
        return playersBody.keys();
    }
    
    public BaseModelBody addPlayerBody(String user, Move3dAdapter listener) {
        
        if ( playersBody.containsKey(user) ) {
            return playersBody.get(user);           
        }
        
        BaseModelBody myBody = new BaseModelBody();
        
        playersBody.put(user, myBody);
        playersListener.put(user, listener);
        
        myBody.setNickName(user);

        return myBody;        
    }
    
    public boolean playerGameOver(String user) {
        synchronized (listMutex) {
            if ( playersBody.remove(user) != null ) {
                return true;
            }
        }
        
        return false;
    }
    
    public void updateMyMsg(String user, String newMsg) {
        BaseModelBody boxN = playersBody.get(user);
        if ( boxN != null ) {
            Move3dAdapter.logger.info("New message for " + user + " :" + newMsg);
            if ( newMsg.length() > 50 ) { 
                boxN.setLastMsg(newMsg.substring(0,50));
            } else {
                boxN.setLastMsg(newMsg);
            }
        } else {
            Move3dAdapter.logger.warn(user + " not found!");
        }
        
        return ;
    }
    
    public void changeNickName(String user, String nick) {
        BaseModelBody boxN = playersBody.get(user);
        if ( boxN != null ) {
            boxN.setNickName(nick);
        } else {
            Move3dAdapter.logger.warn(user + " not found!");
        }
        
        return ;
    }
    
    @Override
    public void run () {
        this.started = true;
        while (true) {
            synchronized (listMutex) {
                
                BaseModelBody boxN;
                
                Enumeration<BaseModelBody> users = playersBody.elements();
                while(users.hasMoreElements()) {
                    boxN = users.nextElement();
                    
                    boxN.translate();
                    boxN.rotate();
                    
                    this.listener.sendUpdates(boxN.getOrigName(), boxN);                    
                }
            }
        
            try {
                Thread.sleep(this.frameRate);
            } catch (InterruptedException ie) {
                // Skip.
            }
        }
    }

}
