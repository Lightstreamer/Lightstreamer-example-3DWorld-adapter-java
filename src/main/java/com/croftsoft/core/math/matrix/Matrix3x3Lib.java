    package com.croftsoft.core.math.matrix;
    
    /***********************************************************************
    * A library of static methods to manipulate Matrix3x3 objects.
    * 
    * @version
    *   $Id: Matrix3x3Lib.java,v 1.8 2008/05/09 19:48:45 croft Exp $
    * @since
    *   2008-04-25
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public final class  Matrix3x3Lib
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    public static Matrix3x3Mut  createRotationMatrix (
      final double  degreesX,
      final double  degreesY,
      final double  degreesZ )
    ////////////////////////////////////////////////////////////////////////
    {
      // Rotation matrices multiplied in this order:  R = Rz * Ry * Rx
      
      final double  cx = Math.cos ( Math.toRadians ( degreesX ) );
      
      final double  sx = Math.sin ( Math.toRadians ( degreesX ) );
      
      final double  cy = Math.cos ( Math.toRadians ( degreesY ) );
      
      final double  sy = Math.sin ( Math.toRadians ( degreesY ) );
      
      final double  cz = Math.cos ( Math.toRadians ( degreesZ ) );
      
      final double  sz = Math.sin ( Math.toRadians ( degreesZ ) );
      
      final Matrix3x3Mut  matrix3x3Mut = new Matrix3x3Imp (
        new double [ ] [ ] {
          {  cy * cz,
            -cx * sz + sx * sy * cz,
             sx * sz + cx * sy * cz },
          {  cy * sz,
             cx * cz + sx * sy * sz,
            -sx * cz + cx * sy * sz },
          { -sy,
             sx * cy,
             cx * cy } } );
      
      return matrix3x3Mut;
    }
      
    public static Matrix3x3Mut  createRotationMatrixX (
      final double  degrees )
    ////////////////////////////////////////////////////////////////////////
    {
      final double  cos = Math.cos ( Math.toRadians ( degrees ) );
      
      final double  sin = Math.sin ( Math.toRadians ( degrees ) );
      
      return new Matrix3x3Imp (
        1,   0,    0,
        0, cos, -sin,
        0, sin,  cos );
    }
      
    public static Matrix3x3Mut  createRotationMatrixY (
      final double  degrees )
    ////////////////////////////////////////////////////////////////////////
    {
      final double  cos = Math.cos ( Math.toRadians ( degrees ) );
      
      final double  sin = Math.sin ( Math.toRadians ( degrees ) );
      
      return new Matrix3x3Imp (
         cos, 0, sin,
           0, 1,   0,
        -sin, 0, cos );
    }
      
    public static Matrix3x3Mut  createRotationMatrixZ (
      final double  degrees )
    ////////////////////////////////////////////////////////////////////////
    {
      final double  cos = Math.cos ( Math.toRadians ( degrees ) );
      
      final double  sin = Math.sin ( Math.toRadians ( degrees ) );

      return new Matrix3x3Imp (
        cos, -sin, 0,
        sin,  cos, 0,
          0,    0, 1 );
    }
    
    public static Matrix3x3Mut  multiply3x3 (
      final Matrix3x3  matrix3x3a,
      final Matrix3x3  matrix3x3b )
    ////////////////////////////////////////////////////////////////////////
    {
      return new Matrix3x3Imp (
        MatrixLib.multiply ( matrix3x3a, matrix3x3b ) );
    }
    
    public static double [ ]  toEulerAngles ( final Matrix3x3  matrix3x3 )
    ////////////////////////////////////////////////////////////////////////
    {
      // Adapted from Dunn and Parberry, 3D Math Primer, 2002, page 204.

      double  sp = -matrix3x3.get ( 1, 2 );
       
      double  heading = 0.0;
       
      double  pitch   = 0.0;
       
      double  bank    = 0.0;
       
      if ( Math.abs ( sp ) > 0.99999 )
      {
        heading = Math.atan2 (
          -matrix3x3.get ( 2, 0 ),
           matrix3x3.get ( 0, 0 ) );
         
        pitch = ( Math.PI / 2.0 ) * sp;
         
        bank = 0.0;
      }
      else
      {
        heading = Math.atan2 (
          matrix3x3.get ( 0, 2 ),
          matrix3x3.get ( 2, 2 ) );
         
        pitch = Math.asin ( sp );
         
        bank = Math.atan2 (
          matrix3x3.get ( 1, 0 ),
          matrix3x3.get ( 1, 1 ) );
      }
      
//      final double [ ]  canonizedEulerAngles = canonizeEulerAngles (
//        new double [ ] { heading, pitch, bank } );
      
      // Change order from heading, pitch, bank to x, y, z
      
      return new double [ ] {
        Math.toDegrees ( pitch   ),
        Math.toDegrees ( heading ),
        Math.toDegrees ( bank    ) };
       
//      return new double [ ] {
//        Math.toDegrees ( canonizedEulerAngles [ 1 ] ),
//        Math.toDegrees ( canonizedEulerAngles [ 0 ] ),
//        Math.toDegrees ( canonizedEulerAngles [ 2 ] ) };
    }
    
    public static Matrix3x3Mut  transpose3x3 ( final Matrix3x3  matrix3x3 )
    ////////////////////////////////////////////////////////////////////////
    {
      return new Matrix3x3Imp (
        matrix3x3.get ( 0, 0 ),
        matrix3x3.get ( 1, 0 ),
        matrix3x3.get ( 2, 0 ),
        matrix3x3.get ( 0, 1 ),
        matrix3x3.get ( 1, 1 ),
        matrix3x3.get ( 2, 1 ),
        matrix3x3.get ( 0, 2 ),
        matrix3x3.get ( 1, 2 ),
        matrix3x3.get ( 2, 2 ) );
    }
      
    ////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////
    
//     private static double [ ]  canonizeEulerAngles (
//       final double [ ]  eulerAngles )
//     //////////////////////////////////////////////////////////////////////
//     {
//       // Adapted from Dunn and Parberry, 3D Math Primer, 2002, page 201.
//       
//       double  heading = eulerAngles [ 0 ];
//       
//       double  pitch   = wrapPi ( eulerAngles [ 1 ] );
//       
//       double  bank    = eulerAngles [ 2 ];
//       
//       if ( pitch < -( Math.PI / 2 ) )
//       {
//         pitch = -Math.PI - pitch;
//         
//         heading += Math.PI;
//         
//         bank += Math.PI;
//       }
//       else if ( pitch > ( Math.PI / 2 ) )
//       {
//         pitch = Math.PI - pitch;
//         
//         heading += Math.PI;
//         
//         bank += Math.PI;
//       }
//       
//       if ( Math.abs ( pitch ) > ( Math.PI / 2 ) - 1e-4 )
//       {
//         heading += bank;
//         
//         bank = 0;
//       }
//       else
//       {
//         bank = wrapPi ( bank );
//       }
//       
//       heading = wrapPi ( heading );
//       
//       return new double [ ] { heading, pitch, bank };       
//     }
//     
//     private static double  wrapPi ( double  theta )
//     //////////////////////////////////////////////////////////////////////
//     {
//       if ( theta > Math.PI )
//       {
//         while ( theta > Math.PI ) theta -= Math.PI;
//       }
//       else if ( theta < -Math.PI )
//       {
//         while ( theta < -Math.PI ) theta += Math.PI;
//       }
//       
//       return theta;
//     }
    
    private  Matrix3x3Lib ( )
    ////////////////////////////////////////////////////////////////////////
    {
      // empty
    }
    
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }