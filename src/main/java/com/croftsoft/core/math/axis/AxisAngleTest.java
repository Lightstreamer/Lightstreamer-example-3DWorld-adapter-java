    package com.croftsoft.core.math.axis;

    import com.croftsoft.core.math.matrix.Matrix3x3;

    /***********************************************************************
    * AxisAngle test methods.
    * 
    * @version
    *   $Id: AxisAngleTest.java,v 1.2 2008/09/20 04:12:46 croft Exp $
    * @since
    *   2008-05-09
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public final class  AxisAngleTest
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
      return testToRotationMatrix ( );
    }
    
    public static boolean  testToRotationMatrix ( )
    ////////////////////////////////////////////////////////////////////////
    {
      return testToRotationMatrix ( new AxisAngleImp (   0, 1, 0, 0 ) )
          && testToRotationMatrix ( new AxisAngleImp ( 180, 0, 1, 0 ) )
          && testToRotationMatrix ( new AxisAngleImp (  90, 0, 0, 1 ) )
          && testToRotationMatrix ( new AxisAngleImp (  37, 1, 0, 0 ) )
          && testToRotationMatrix ( new AxisAngleImp (  37, 0, 1, 0 ) )
          && testToRotationMatrix ( new AxisAngleImp (  37, 0, 0, 1 ) );
    }
    
    public static boolean  testToRotationMatrix (
      final AxisAngle  axisAngle )
    ////////////////////////////////////////////////////////////////////////
    {
      final Matrix3x3  rotationMatrix0 = axisAngle.toRotationMatrix ( );
      
      final Matrix3x3  rotationMatrix1
        = axisAngle.toQuat ( ).toRotationMatrix ( );
      
      System.out.println ( rotationMatrix0.toString ( ) );
      
      System.out.println ( rotationMatrix1.toString ( ) );
      
      return rotationMatrix0.matches ( rotationMatrix1, 0.001 );
    }
    
    ////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////
    
    private  AxisAngleTest ( )
    ////////////////////////////////////////////////////////////////////////
    {
      // empty
    }
    
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }