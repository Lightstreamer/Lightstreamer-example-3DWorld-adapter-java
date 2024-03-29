    package com.croftsoft.core.math.quat;

    import com.croftsoft.core.math.axis.AxisAngleImp;
    import com.croftsoft.core.math.axis.AxisAngleMut;
    import com.croftsoft.core.math.matrix.Matrix3x3Imp;
    import com.croftsoft.core.math.matrix.Matrix3x3Mut;

    /***********************************************************************
    * A library of static methods to manipulate Quat objects.
    * 
    * @version
    *   $Id: QuatLib.java,v 1.10 2008/09/20 02:51:51 croft Exp $
    * @since
    *   2008-05-02
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public final class  QuatLib
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    public static double  dotProduct (
      final Quat  quat0,
      final Quat  quat1 )
    ////////////////////////////////////////////////////////////////////////
    {
      return quat0.getW ( ) * quat1.getW ( )
           * quat0.getX ( ) * quat1.getX ( )
           * quat0.getY ( ) * quat1.getY ( )
           * quat0.getZ ( ) * quat1.getZ ( );
    }
    
    public static QuatMut  fromEulerAngles (
      final double  degreesX,
      final double  degreesY,
      final double  degreesZ )
    ////////////////////////////////////////////////////////////////////////
    {
      final Quat  quatX = new AxisAngleImp ( degreesX, 0, 0, 1 ).toQuat ( );
      
      final Quat  quatY = new AxisAngleImp ( degreesY, 0, 1, 0 ).toQuat ( );
      
      final Quat  quatZ = new AxisAngleImp ( degreesZ, 0, 0, 1 ).toQuat ( );
      
      return quatZ.multiply ( quatY ).multiply ( quatX );
    }
    
    public static boolean  matches (
      final Quat  quat0,
      final Quat  quat1 )
    ////////////////////////////////////////////////////////////////////////
    {
      return quat0.getW ( ) == quat1.getW ( )
          && quat0.getX ( ) == quat1.getX ( )
          && quat0.getY ( ) == quat1.getY ( )
          && quat0.getZ ( ) == quat1.getZ ( );
    }
      
    public static boolean  matches (
      final Quat    quat0,
      final Quat    quat1,
      final double  tolerance )
    ////////////////////////////////////////////////////////////////////////
    {
      if ( tolerance < 0 )
      {
        throw new IllegalArgumentException ( "tolerance < 0" );
      }
      
      return Math.abs ( quat0.getW ( ) - quat1.getW ( ) ) <= tolerance
          && Math.abs ( quat0.getX ( ) - quat1.getX ( ) ) <= tolerance
          && Math.abs ( quat0.getY ( ) - quat1.getY ( ) ) <= tolerance
          && Math.abs ( quat0.getZ ( ) - quat1.getZ ( ) ) <= tolerance;
    }
      
    public static QuatMut  multiply (
      final Quat  quat0,
      final Quat  quat1 )
    ////////////////////////////////////////////////////////////////////////
    {
      final double  w0 = quat0.getW ( );
      
      final double  x0 = quat0.getX ( );
      
      final double  y0 = quat0.getY ( );
      
      final double  z0 = quat0.getZ ( );
      
      final double  w1 = quat1.getW ( );
      
      final double  x1 = quat1.getX ( );
      
      final double  y1 = quat1.getY ( );
      
      final double  z1 = quat1.getZ ( );
      
      return new QuatImp (
        w0 * w1 - x0 * x1 - y0 * y1 - z0 * z1,
        y0 * z1 - z0 * y1 + w0 * x1 + x0 * w1,
        z0 * x1 - x0 * z1 + w0 * y1 + y0 * w1,
        x0 * y1 - y0 * x1 + w0 * z1 + z0 * w1 );
    }
    
    public static AxisAngleMut  toAxisAngle ( final Quat  quat )
    ////////////////////////////////////////////////////////////////////////
    {
      final double  w = quat.getW ( );
      
      final double  sinThetaOver2Sq = 1 - w * w;
      
      if ( sinThetaOver2Sq <= 0 )
      {
        return new AxisAngleImp ( );
      }
      
      final double  oneOverSinThetaOver2
        = 1 / Math.sqrt ( sinThetaOver2Sq );
      
      return new AxisAngleImp (
        Math.toDegrees ( 2 * Math.acos ( w ) ),
        quat.getX ( ) * oneOverSinThetaOver2,
        quat.getY ( ) * oneOverSinThetaOver2,
        quat.getZ ( ) * oneOverSinThetaOver2 );
    }
    
    public static Matrix3x3Mut  toRotationMatrix ( final Quat  quat )
    ////////////////////////////////////////////////////////////////////////
    {
      final double  w = quat.getW ( );
      
      final double  x = quat.getX ( );
      
      final double  y = quat.getY ( );
      
      final double  z = quat.getZ ( );
      
      final double  wx = w * x;
      
      final double  wy = w * y;
      
      final double  wz = w * z;
      
      final double  xx = x * x;
      
      final double  xy = x * y;
      
      final double  xz = x * z;
      
      final double  yy = y * y;
      
      final double  yz = y * z;
      
      final double  zz = z * z;
      
      return new Matrix3x3Imp (
        1 - 2 * ( yy + zz ),
            2 * ( xy - wz ),
            2 * ( wy + xz ),
            2 * ( xy + wz ),
        1 - 2 * ( xx + zz ),
            2 * ( yz - wx ),
            2 * ( xz - wy ),
            2 * ( yz + wx ),
        1 - 2 * ( xx + yy ) );
    }
    
    public static String  toString ( final Quat  quat )
    ////////////////////////////////////////////////////////////////////////
    {
      return String.format (
        "%1$1.3f; %2$1.3f, %3$1.3f, %4$1.3f",
        new Double ( quat.getW ( ) ),
        new Double ( quat.getX ( ) ),
        new Double ( quat.getY ( ) ),
        new Double ( quat.getZ ( ) ) );
    }
    
    ////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////
    
    private  QuatLib ( )
    ////////////////////////////////////////////////////////////////////////
    {
      // empty
    }
    
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }