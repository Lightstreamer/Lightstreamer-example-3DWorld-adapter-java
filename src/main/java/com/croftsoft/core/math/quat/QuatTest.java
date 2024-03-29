    package com.croftsoft.core.math.quat;

    import com.croftsoft.core.math.axis.AxisAngle;

    /***********************************************************************
    * Quat test methods.
    * 
    * @version
    *   $Id: QuatTest.java,v 1.6 2008/09/20 04:12:46 croft Exp $
    * @since
    *   2008-05-02
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public final class  QuatTest
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
      return testMultiply ( );
    }
    
    public static boolean  testMultiply ( )
    ////////////////////////////////////////////////////////////////////////
    {
      final Quat  quatX90 = QuatLib.fromEulerAngles ( 90, 0, 0 );
      
      System.out.println ( "X90 ...........:  " + quatX90 );
      
      final Quat  quatX180 = QuatLib.fromEulerAngles ( 180, 0, 0 );
      
      System.out.println ( "X180 ..........:  " + quatX180 );
      
      final Quat  quatX90X90 = quatX90.multiply ( quatX90 );
      
      System.out.println ( "X90,X90 .......:  " + quatX90X90 );
      
      final Quat  quatY90 = QuatLib.fromEulerAngles ( 0, 90, 0 );
      
      System.out.println ( "Y90 ...........:  " + quatY90 );
      
      final Quat  quatZ90 = QuatLib.fromEulerAngles ( 0, 0, 90 );
      
      System.out.println ( "Z90 ...........:  " + quatZ90 );
      
      final Quat  quatX90Y90Z90 = QuatLib.fromEulerAngles ( 90, 90, 90 );
      
      System.out.println ( "X90,Y90,Z90 ...:  " + quatX90Y90Z90 );
      
      final AxisAngle  axisAngle = quatX90Y90Z90.toAxisAngle ( );
      
      System.out.printf (
        "rotation degrees = %1$1.3f\n",
        new Double ( axisAngle.getDegrees ( ) ) );
      
      System.out.printf (
        "rotation axis = %1$1.3f %2$1.3f %3$1.3f\n",
        new Double ( axisAngle.getX ( ) ),
        new Double ( axisAngle.getY ( ) ),
        new Double ( axisAngle.getZ ( ) ) );
      
      return quatX180.matches ( quatX90X90, 0.001 );
    }
    
    ////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////
    
    private  QuatTest ( )
    ////////////////////////////////////////////////////////////////////////
    {
      // empty
    }
    
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }