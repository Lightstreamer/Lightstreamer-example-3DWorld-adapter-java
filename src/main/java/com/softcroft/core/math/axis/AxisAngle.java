    package com.croftsoft.core.math.axis;

    import com.croftsoft.core.math.matrix.Matrix3x3Mut;
    import com.croftsoft.core.math.quat.QuatMut;

    /***********************************************************************
    * Accessor interface for an axis-angle.
    * 
    * @version
    *   $Id: AxisAngle.java,v 1.1 2008/05/09 18:35:55 croft Exp $
    * @since
    *   2008-05-09
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public interface  AxisAngle
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    // accessor methods
      
    double  getDegrees ( );
      
    double  getX ( );
    
    double  getY ( );
    
    double  getZ ( );
    
    // operand methods
    
    boolean  matches ( AxisAngle  axisAngle );
    
    boolean  matches (
               AxisAngle  axisAngle,
               double     tolerance );
    
    // calculation methods
    
    double        magnitude ( );
    
    Matrix3x3Mut  toRotationMatrix ( );
    
    QuatMut       toQuat ( );
    
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }