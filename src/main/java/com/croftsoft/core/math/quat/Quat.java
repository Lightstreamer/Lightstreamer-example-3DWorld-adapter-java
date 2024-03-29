    package com.croftsoft.core.math.quat;

    import com.croftsoft.core.math.axis.AxisAngleMut;
    import com.croftsoft.core.math.matrix.Matrix3x3Mut;
    
    /***********************************************************************
    * Accessor interface for a quaternion.
    * 
    * @version
    *   $Id: Quat.java,v 1.8 2008/05/09 18:35:56 croft Exp $
    * @since
    *   2008-05-02
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public interface  Quat
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    // accessor methods
      
    double  getW ( );
      
    double  getX ( );
    
    double  getY ( );
    
    double  getZ ( );
    
    // operand methods
    
    boolean  matches ( Quat  quat );
    
    boolean  matches (
               Quat    quat,
               double  tolerance );
    
    // calculation methods
    
    double  dotProduct ( Quat  quat );
    
    QuatMut  multiply ( Quat  quat );
    
    AxisAngleMut  toAxisAngle ( );
    
    Matrix3x3Mut  toRotationMatrix ( );
      
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }