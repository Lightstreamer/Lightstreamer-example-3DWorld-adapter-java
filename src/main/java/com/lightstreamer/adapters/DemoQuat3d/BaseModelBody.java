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

import org.apache.commons.math3.complex.Quaternion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseModelBody implements IBody {

    private static final double ROTATE_DELTA    = 0.005;
    private static final double TRANSLATE_DELTA = 0.002;
    private static final double WORLD_SIZE_X = 160;
    private static final double WORLD_SIZE_Y = 90;
    private static final double WORLD_SIZE_Z = 120;

    public static Logger logger = LogManager.getLogger("LS_3DWorldDemo_Logger.tracer");
    
    private String nickName = "";
    private String origName = "";
    
    private String lastMsg = "";

    private long    lifeSpan = 0;
    private long    lastCmdRcvd = 0;
    
    private double  x, y, z;                                // position         Vector3
    private double  vX, vY, vZ;                             // velocity         Vector3
    private Quaternion  axisAngle;                          // Spin             Quaternion
    private double  deltaRotX, deltaRotY, deltaRotZ;        // angularMomentum  Vector3

    public BaseModelBody() {
        this.axisAngle = new Quaternion(0.0, 1.0, 1.0, 1.0); 
        this.x = (double)((Math.random() * 50) - 25);
        this.y = (double)((Math.random() * 50) - 25);
        this.z = (double)((Math.random() * 50) - 25);
    }
    
    public BaseModelBody(final Quaternion axisAngle, final double x, final double y, final double z) {
        
        this.axisAngle = axisAngle;
    
        this.x = x;    
        this.y = y;
        this.z = z;
    }
    
    public String getOrigName() {
        return origName;
    }
    
    public String getNickName() {
        return nickName;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }
    
    public void setNickName(String nickName) {
        if ( this.nickName.equals("") ) {
            this.origName = nickName;
        }
        this.nickName = nickName;
    }
    
    public long getLifeSpan() {
        return lifeSpan;
    }
    
    
    @Override
    public Quaternion getAxisAngle() {
        return axisAngle;
    }
    
    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public void setAxisAngle(Quaternion axisAngle) {
        this.axisAngle = axisAngle;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setZ(double z) {
        this.z = z;
    }

    public double getvX() {
        return vX;
    }

    public double getvY() {
        return vY;
    }

    public double getvZ() {
        return vZ;
    }

    public double getDeltaRotX() {
        return deltaRotX;
    }

    public double getDeltaRotY() {
        return deltaRotY;
    }

    public double getDeltaRotZ() {
        return deltaRotZ;
    }
    
    public long getInactivityPeriod() {
        return (this.lifeSpan - this.lastCmdRcvd);
    }
    
    @Override
    public void rotate(Quaternion axisAngleRot) {
        // AxisAngleMut newAxisAngleMut = this.axisAngle.toQuat( ).multiply(axisAngleRot.toQuat()).toAxisAngle ( );
    	Quaternion quat = this.axisAngle.multiply(axisAngleRot);

        this.axisAngle = quat.normalize();
    }

    @Override
    public void rotate(Axis axis, double degrees) {
        // TODO Auto-generated method stub

    }

    @Override
    public void rotate(Rotation rotation, double degrees) {
        // TODO Auto-generated method stub

    }

    @Override
    public void rotate() {
        rotate ( new Quaternion ( this.deltaRotX, 1, 0, 0 ) );
        rotate ( new Quaternion ( this.deltaRotY, 0, 1, 0 ) );
        rotate ( new Quaternion ( this.deltaRotZ, 0, 0, 1 ) );
    }
    
    @Override
    public void rotate(double factor) {
        rotate ( new Quaternion ( this.deltaRotX * factor, 1, 0, 0 ) );
        rotate ( new Quaternion ( this.deltaRotY * factor, 0, 1, 0 ) );
        rotate ( new Quaternion ( this.deltaRotZ * factor, 0, 0, 1 ) );
    }
    
    @Override
    public void translate(Axis axis, double distance) {
        switch ( axis )
        {
            case X:
                this.x += distance;
              
                break;
              
            case Y:
                this.y += distance;
                
                break;
                
            case Z:
                this.z += distance;
                
                break;
            
            default:
                // Skip.
        }
    }
    
    private double wrapX(double pos) {
        double bound = WORLD_SIZE_X/2;

        logger.debug("wrapX in: " + pos);

    	if ( pos > bound ) {
            double tmp = (-1*bound)+(pos-bound);
            logger.debug("wrapX out (1): " + tmp);
            return tmp;
        } else if ( pos < -1*bound ) {
            double tmp = bound-(pos+bound);
            logger.debug("wrapX out (2): " + tmp);
            return tmp;
        } else {
            logger.debug("wrapX out (3): " + pos);
            return pos;
        }
    	
    }
    
    private double wrapY(double pos) {
    	double bound = WORLD_SIZE_Y/2;

    	if ( pos > bound ) {
            return (-1*bound)+(pos-bound);
        } else if ( pos < -1*bound ) {
            return bound-(pos+bound);
        } else {
            return pos;
        }
    }
    
    private double wrapZ(double pos) {
    	double bound = WORLD_SIZE_Z/2;

    	if ( pos > bound ) {
            return (-1*bound)+(pos-bound);
        } else if ( pos < -1*bound ) {
            return bound-(pos+bound);
        } else {
            return pos;
        }
    }
    @Override
    public void translate() {
        this.x += (double)(this.vX * TRANSLATE_DELTA);
        this.y += (double)(this.vY * TRANSLATE_DELTA);
        this.z += (double)(this.vZ * TRANSLATE_DELTA);
                
        this.x = wrapX(this.x);
        this.y = wrapY(this.y);
        this.z = wrapZ(this.z);
        
        this.lifeSpan += 1;
    }
    
    @Override
    public void translate(double factor) {
        this.x += (double)(this.vX * TRANSLATE_DELTA * factor);
        this.y += (double)(this.vY * TRANSLATE_DELTA * factor);
        this.z += (double)(this.vZ * TRANSLATE_DELTA * factor);
                
        this.x = wrapX(this.x);
        this.y = wrapY(this.y);
        this.z = wrapZ(this.z);
        
        this.lifeSpan += 1;
    }

    @Override
    public void translate(Translation translation, double distance) {
        // TODO Auto-generated method stub

    }
    
    public void setImpulse(Axis axis, double intensity) {
        switch ( axis )
        {
            case X:
                this.vX += intensity;
                
                break;
                
            case Y:
                this.vY += intensity;
                
                break;
                
            case Z:
                this.vZ += intensity;
                
                break;
                
            default:
                // throw new Exception (  );
        }
        this.lastCmdRcvd = this.lifeSpan;
    }
    
    public void setTourque(Axis axis, double intensity) {
        switch ( axis )
        {
            case X:
                this.deltaRotX += (intensity * ROTATE_DELTA);
                
                break;
                
            case Y:
                this.deltaRotY += (intensity  * ROTATE_DELTA);
                
                break;
                
            case Z:
                this.deltaRotZ += (intensity  * ROTATE_DELTA);
                
                break;
                
            default:
                // throw new EnumUnknownException ( axis );
        }
        this.lastCmdRcvd = this.lifeSpan;
    }

}
