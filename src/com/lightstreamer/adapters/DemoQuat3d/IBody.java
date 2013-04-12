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
import com.croftsoft.core.math.axis.AxisAngle;

/***********************************************************************
* Mutator interface for a JoglCamera.
*  
* @version
*   $Id: JoglCameraMut.java,v 1.3 2008/05/16 20:25:34 croft Exp $
* @since
*   2008-05-16
* @author
*   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
***********************************************************************/

public interface  IBody {
  
    public enum  Axis {
        X,
        Y,
        Z
    }
      
    public enum  Rotation {
        PITCH_DOWN,
        PITCH_UP,
        ROLL_LEFT,
        ROLL_RIGHT,
        YAW_LEFT,
        YAW_RIGHT
    }

    public enum  Translation {
        BACKWARD,
        DOWN,
        FORWARD,
        LEFT,
        RIGHT,
        UP
    }

    AxisAngle  getAxisAngle ( );
    
    double  getX ( );
    
    double  getY ( );
    
    double  getZ ( );
     
    void  setAxisAngle ( AxisAngle  axisAngle );
      
    void  setX ( double  x );
      
    void  setY ( double  y );
      
    void  setZ ( double  z );
    
    // relative mutator methods
      
    void  rotate (AxisAngle axisAngle);
     
    void  rotate (Axis axis, double degrees);
    
    void  rotate (Rotation rotation, double degrees);
    
    void  rotate();
    
    void  rotate(double factor);
    
    void  translate (Axis axis, double  distance);
    
    void  translate (Translation  translation, double distance);
    
    void translate();
    
    void translate(double factor);
}