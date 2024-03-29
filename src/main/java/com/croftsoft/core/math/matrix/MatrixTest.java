    package com.croftsoft.core.math.matrix;

    /***********************************************************************
    * Matrix test methods.
    * 
    * @version
    *   $Id: MatrixTest.java,v 1.9 2008/05/03 02:48:38 croft Exp $
    * @since
    *   2008-04-19
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public final class  MatrixTest
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    public static void  main ( final String [ ]  args )
    ////////////////////////////////////////////////////////////////////////
    {
      System.out.println ( test ( ) );
    }
    
    public static boolean  test ( )
    ////////////////////////////////////////////////////////////////////////
    {
      System.out.println (
        Matrix3x3Lib.createRotationMatrixX (   0 ).toString ( ) );
      
      System.out.println ( 
        Matrix3x3Lib.createRotationMatrixY ( 180 ).toString ( ) );
      
      System.out.println ( 
        Matrix3x3Lib.createRotationMatrixZ ( 270 ).toString ( ) );
      
      System.out.println (
        Matrix3x3Lib.createRotationMatrix ( 0, 180, 270 ).toString ( ) );
      
      final MatrixMut  matrixB = new MatrixImp ( 2, 2 );
      
      matrixB.set ( 0, 0, 0 );
      
      matrixB.set ( 0, 1, 1 );
      
      matrixB.set ( 1, 0, -2 );
      
      matrixB.set ( 1, 1, 5 );
      
      final MatrixMut  matrixD = new MatrixImp (
        new double [ ] [ ] {
          { -1, 0, 3 },
          {  5, 7, 2 } } );
      
      System.out.println ( matrixB );
      
      System.out.println ( matrixD );
      
      System.out.println ( matrixB.multiply ( matrixD ) );
      
      final Matrix3x3  rotationMatrix
        = Matrix3x3Lib.createRotationMatrix ( 90, 90, 90 );
      
      final double [ ]  eulerAngles
        = Matrix3x3Lib.toEulerAngles ( rotationMatrix );
      
      for ( int  i = 0; i < 3; i++ )
      {
        System.out.print ( eulerAngles [ i ] + ", " );
      }
      
      System.out.println ( "" );
      
      return testTranspose ( )
        && testRotation (  90,   90,  90 )
        && testRotation (   0,    0,   0 )
        && testRotation ( 180,    0,   0 )
        && testRotation ( 360,  180, 270 )
        && testRotation ( -90, -180,   0 );
    }
    
    public static boolean  testRotation (
      final double  degreesX,
      final double  degreesY,
      final double  degreesZ )
    ////////////////////////////////////////////////////////////////////////
    {
      final double  cy = Math.cos ( Math.toRadians ( degreesY ) );
      
      final double  sy = Math.sin ( Math.toRadians ( degreesY ) );
      
      final double  cz = Math.cos ( Math.toRadians ( degreesZ ) );
      
      final double  sz = Math.sin ( Math.toRadians ( degreesZ ) );
      
      final Matrix3x3Mut  rotationZY0 = new Matrix3x3Imp (
        new double [ ] [ ] {
          {  cy * cz, -sz, sy * cz },
          {  cy * sz,  cz, sy * sz },
          {      -sy,   0,      cy } } );
      
      final Matrix  rotationZY1
        = Matrix3x3Lib.createRotationMatrixZ ( degreesZ ).multiply (
          Matrix3x3Lib.createRotationMatrixY ( degreesY ) );
      
      if ( !rotationZY0.matches ( rotationZY1 ) )
      {
        System.out.println ( "rotation test 1 failed" );
        
        return false;
      }
      
      final Matrix3x3Mut  rotationMatrix
        = Matrix3x3Lib.createRotationMatrixZ ( degreesZ );

      rotationMatrix.multiplyToSelf (
        Matrix3x3Lib.createRotationMatrixY ( degreesY ) );

      rotationMatrix.multiplyToSelf (
        Matrix3x3Lib.createRotationMatrixX ( degreesX ) );
      
      System.out.print ( "expected ...:  " );
      
      System.out.println ( rotationMatrix );
      
      System.out.print ( "actual .....:  " );
      
      final Matrix3x3  actualRotationMatrix
        = Matrix3x3Lib.createRotationMatrix (
          degreesX,
          degreesY,
          degreesZ );
      
      System.out.println ( actualRotationMatrix );
      
      if ( !rotationMatrix.matches ( actualRotationMatrix ) )
      {
        System.out.println ( "rotation test 2 failed" );
        
        return false;
      }
      
      return true;
    }
    
    public static boolean  testTranspose ( )
    ////////////////////////////////////////////////////////////////////////
    {
      final Matrix  matrix3x4 = new MatrixImp (
        new double [ ] [ ] {
          { 1,  2,  3,  4 },
          { 5,  6,  7,  8 },
          { 9, 10, 11, 12 } } );
      
      final Matrix  matrix4x3 = new MatrixImp (
        new double [ ] [ ] {
          { 1, 5,  9 },
          { 2, 6, 10 },
          { 3, 7, 11 },
          { 4, 8, 12 } } );
      
      if ( !matrix3x4.matches ( matrix4x3.transpose ( ) ) )
      {
        System.out.println ( "transpose test 1 failed" );
        
        return false;
      }
      
      final MatrixMut  matrix4x4Mut = new MatrixImp (
        new double [ ] [ ] {
          {  1,  2,  3,  4 },
          {  5,  6,  7,  8 },
          {  9, 10, 11, 12 },
          { 13, 14, 15, 16 } } );
      
      final Matrix  matrix4x4 = new MatrixImp (
        new double [ ] [ ] {
          { 1, 5,  9, 13 },
          { 2, 6, 10, 14 },
          { 3, 7, 11, 15 },
          { 4, 8, 12, 16 } } );
      
      if ( !matrix4x4Mut.transpose ( ).matches ( matrix4x4 ) )
      {
        System.out.println ( "transpose test 2 failed" );
        
        return false;
      }
      
      matrix4x4Mut.transposeSelf ( );
      
      if ( !matrix4x4Mut.matches ( matrix4x4 ) )
      {
        System.out.println ( "transpose test 3 failed" );
        
        return false;
      }
      
      return true;
    }
    
    ////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////
    
    private  MatrixTest ( )
    ////////////////////////////////////////////////////////////////////////
    {
      // empty
    }
    
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }