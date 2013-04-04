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

import java.util.ArrayList;
import java.util.Hashtable;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class PollsBandwidth extends Thread {

    private boolean end = false;
    
    private static String BAND_PREFIX = "My_Band_";
    
    //private MBeanServerConnection mbsc = null;
    private MBeanServer server;  
    private ObjectName sessionMBeanName = null;
    private String user = "";
    
    public PollsBandwidth(String sessionId, String user, int port) {
        try {
            /*
            JMXServiceURL url = new JMXServiceURL("service:jmx:jmxmp://localhost:"+port);
            JMXConnector jmxc = JMXConnectorFactory.connect(url, null); 
                
            this.mbsc = jmxc.getMBeanServerConnection();
            this.mbeanName =  new ObjectName("com.lightstreamer:type=Session,sessionId="+sessionId);
            */

            server = null;  
            ObjectName mbeanName = new ObjectName("com.lightstreamer", "type", "Server");
            if (mbeanName != null) {  
                ArrayList<MBeanServer> servers = MBeanServerFactory.findMBeanServer(null);  
                MBeanServer found = null;  
                for (int i = 0; i < servers.size(); i++) {  
                    found = (MBeanServer) servers.get(i);  
                    if (found.isRegistered(mbeanName)) {  
                        break;  
                    } else {  
                        found = null;  
                    }  
                }  
                server = found;  
            } 
            
            Hashtable<String, String> props = new Hashtable<String, String>();  
            props.put("type", "Session");  
            props.put("sessionId", sessionId);  
           
            sessionMBeanName = new ObjectName("com.lightstreamer", props);  
            
            
            
            this.user = user;
        } catch (Exception e) {
            // Skip. Eventually log here ...
        }
    }
    
    public void setEnd() {
        end = true;
    }
    
    public void forceMeOut() {
        try {  
            Object ret = server.invoke(sessionMBeanName, "destroySession", null, null);  
        } catch (Exception e) {  
            // it is still possible that the session has just ended  
            return;  
        } 
    }
        
    @Override
    public void run () {
        while (!end) {
            try {
                Thread.sleep(2000);
            
                //Double d = (Double)mbsc.getAttribute(mbeanName, "CurrentBandwidthKbps");
                Double d = (Double)server.getAttribute(sessionMBeanName, "CurrentBandwidthKbps");
                Move3dAdapter.postBandwith(BAND_PREFIX+this.user, d);
            } catch (InterruptedException ie) {
                // Skip.
            } catch (Exception e) {
                // send update ERR.
                // this.listener.postBandwith(BAND_PREFIX+this.user, new Double(0));
            }
        }
    }
    
}
