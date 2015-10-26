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

public class WorldsStatistics {
    
    private double minBandwidth;
    private double maxBandwidth;
    private long   minPlayers;
    private long   maxPlayers; 
    private boolean bFlag = false;
    private boolean pFlag = false;

    public WorldsStatistics(long nGhosts) {
        this.minBandwidth = 0.0;
        this.maxBandwidth = 0.0;
        this.maxPlayers = nGhosts;
        this.minPlayers = nGhosts;
        this.bFlag = true;
        this.pFlag = true;
    }
    
    public void feedBandwidth(double now) {
        
        if ( bFlag ) {
            maxBandwidth = now;
            minBandwidth = now;
            bFlag = false;
        } else {
            if (now > maxBandwidth) {
                maxBandwidth = now;
            } else if ( now < minBandwidth ) {
                minBandwidth = now;
            }
        }
    }
    
    public void feedPlayers(long now) {
        
        if ( pFlag ) {
            maxPlayers = now;
            minPlayers = now;
            pFlag = false;
        } else {
            if (now > maxPlayers) {
                maxPlayers = now;
            } else if ( (now < minPlayers) ) {
                minPlayers = now;
            }
        }
    }
    
    public void reset() {
        this.minBandwidth = 0;
        this.maxBandwidth = 0;
        this.maxPlayers = 0;
        this.minPlayers = 0;
        this.pFlag = true;
        this.bFlag = true;
    }
    
    @Override
    public String toString() {
        return "Statistics - Max Bandwidth: " + (double)((double)(Math.round(maxBandwidth*100))/100.0) + " Kbps, Min Bandwidth: " + (double)((double)(Math.round(minBandwidth*100))/100.0) + " Kbps, Max Players: " + maxPlayers + ", Min Players: " + minPlayers + ".";
    }
}
