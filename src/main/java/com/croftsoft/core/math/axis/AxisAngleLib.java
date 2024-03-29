    package com.croftsoft.core.math.axis;

    import com.croftsoft.core.math.matrix.Matrix3x3Imp;
    import com.croftsoft.core.math.matrix.Matrix3x3Mut;
    import com.croftsoft.core.math.quat.QuatImp;
    import com.croftsoft.core.math.quat.QuatMut;

    /***********************************************************************
    * A library of static methods to manipulate AxisAngle objects.
    * 
    * @version
    *   $Id: AxisAngleLib.java,v 1.1 2008/05/09 18:35:55 croft Exp $
    * @since
    *   2008-05-09
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public final class  AxisAngleLib
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    public static double  magnitude ( final AxisAngle  axisAngle )
    ////////////////////////////////////////////////////////////////////////
    {
      final double  x = axisAngle.getX ( );
      
      final double  y = axisAngle.getY ( );
      
      final double  z = axisAngle.getZ ( );
      
      return Math.sqrt ( x * x + y * y + z * z );
    }
    
    public static boolean  matches (
      final AxisAngle  axisAngle0,
      final AxisAngle  axisAngle1 )
    ////////////////////////////////////////////////////////////////////////
    {
      return axisAngle0.getDegrees ( ) == axisAngle1.getDegrees ( )
          && axisAngle0.getX ( ) == axisAngle1.getX ( )
          && axisAngle0.getY ( ) == axisAngle1.getY ( )
          && axisAngle0.getZ ( ) == axisAngle1.getZ ( );
    }
      
    public static boolean  matches (
      final AxisAngle    axisAngle0,
      final AxisAngle    axisAngle1,
      final double  tolerance )
    ////////////////////////////////////////////////////////////////////////
    {
      if ( tolerance < 0 )
      {
        throw new IllegalArgumentException ( "tolerance < 0" );
      }
      
      return Math.abs (
            axisAngle0.getDegrees ( ) - axisAngle1.getDegrees ( ) )
            <= tolerance
          && Math.abs ( axisAngle0.getX ( ) - axisAngle1.getX ( ) )
            <= tolerance
          && Math.abs ( axisAngle0.getY ( ) - axisAngle1.getY ( ) )
            <= tolerance
          && Math.abs ( axisAngle0.getZ ( ) - axisAngle1.getZ ( ) )
            <= tolerance;
    }
    
    public static void  normalize ( final AxisAngleMut  axisAngleMut )
    ////////////////////////////////////////////////////////////////////////
    {
      final double  magnitude = axisAngleMut.magnitude ( );
      
      axisAngleMut.setX ( axisAngleMut.getX ( ) / magnitude );
      
      axisAngleMut.setY ( axisAngleMut.getY ( ) / magnitude );
      
      axisAngleMut.setZ ( axisAngleMut.getZ ( ) / magnitude );
    }
      
    public static QuatMut  toQuat ( final AxisAngle  axisAngle )
    ////////////////////////////////////////////////////////////////////////
    {
      final double  halfRadians
        = Math.toRadians ( axisAngle.getDegrees ( ) / 2 );
      
      final double  sinHalfRadians = Math.sin ( halfRadians );
      
      return new QuatImp (
        Math.cos ( halfRadians ),
        sinHalfRadians * axisAngle.getX ( ),
        sinHalfRadians * axisAngle.getY ( ),
        sinHalfRadians * axisAngle.getZ ( ) );
    }
    
    public static Matrix3x3Mut  toRotationMatrix (
      final AxisAngle  axisAngle )
    ////////////////////////////////////////////////////////////////////////
    {
      // return toQuat ( axisAngle ).toRotationMatrix ( );
      
      final double  degrees = axisAngle.getDegrees ( );
      
      final double  x = axisAngle.getX ( );
      
      final double  y = axisAngle.getY ( );
      
      final double  z = axisAngle.getZ ( );
      
      final double  c = Math.cos ( Math.toRadians ( degrees ) );
      
      final double  s = Math.sin ( Math.toRadians ( degrees ) );
      
      // Lengyel, "Mathematics for 3D Game Programming & Computer Graphics",
      // Second Edition, p80, equation 3.22.
      
      return new Matrix3x3Imp (
        c + ( 1 - c ) * x * x,
        ( 1 - c ) * x * y - s * z,
        ( 1 - c ) * x * z + s * y,
        
        ( 1 - c ) * x * y + s * z,
        c + ( 1 - c ) * y * y,
        ( 1 - c ) * y * z - s * x,
        
        ( 1 - c ) * x * z - s * y,
        ( 1 - c ) * y * z + s * x,
        c + ( 1 - c ) * z * z );
    }
      
    public static String  toString ( final AxisAngle  axisAngle )
    ////////////////////////////////////////////////////////////////////////
    {
      return String.format (
        "%1$1.3f; %2$1.3f, %3$1.3f, %4$1.3f",
        new Double ( axisAngle.getDegrees ( ) ),
        new Double ( axisAngle.getX ( ) ),
        new Double ( axisAngle.getY ( ) ),
        new Double ( axisAngle.getZ ( ) ) );
    }
    
    ////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////
    
    private  AxisAngleLib ( )
    ////////////////////////////////////////////////////////////////////////
    {
      // empty
    }
    
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }