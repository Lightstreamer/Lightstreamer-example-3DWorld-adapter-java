     package com.croftsoft.core.math.geom;

     import java.io.*;

     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Testable;

     /*********************************************************************
     * A mutable point in three-dimensional real space (x, y, z).
     *
     * @version
     *   2003-04-13
     * @since
     *   2002-02-06
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  Point3DD
       extends Point2DD
       implements PointXYZ, Cloneable, Serializable, Testable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     public double  z;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( args ) );
     }

     public static boolean  test ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         Point3DD  point3DD1 = new Point3DD ( 0, 1, 2 );

         byte [ ]  bytes = SerializableLib.compress ( point3DD1 );

         Point3DD  point3DD2 = ( Point3DD ) SerializableLib.load (
           new ByteArrayInputStream ( bytes ) );

         System.out.println ( point3DD2 );

         return point3DD2.equals ( point3DD1 )
           && point3DD1.equals ( ( Point3DD ) point3DD1.clone ( ) );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Point3DD (
       double  x,
       double  y,
       double  z )
     //////////////////////////////////////////////////////////////////////
     {
       this.x = x;

       this.y = y;

       this.z = z;
     }

     public  Point3DD ( )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public  Point3DD ( PointXYZ  pointXYZ )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( pointXYZ );

       x = pointXYZ.getX ( );

       y = pointXYZ.getY ( );

       z = pointXYZ.getZ ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public double  getZ ( ) { return z; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setXYZ (
       double  x,
       double  y,
       double  z )
     //////////////////////////////////////////////////////////////////////
     {
       this.x = x;

       this.y = y;

       this.z = z;
     }

     public void  setXYZ ( PointXYZ  pointXYZ )
     //////////////////////////////////////////////////////////////////////
     {
       x = pointXYZ.getX ( );

       y = pointXYZ.getY ( );

       z = pointXYZ.getZ ( );
     }

     public void  setZ ( double  z ) { this.z = z; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  equals ( Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       if ( other == null )
       {
         return false;
       }

       if ( !other.getClass ( ).equals ( Point3DD.class ) )
       {
         return false;
       }

       Point3DD  that = ( Point3DD ) other;

       return ( this.x == that.x )
         &&   ( this.y == that.y )
         &&   ( this.z == that.z );
     }

     public int  hashCode ( )
     //////////////////////////////////////////////////////////////////////
     {
       // This might be a poor choice for a hash code algorithm.

       return new java.lang.Double ( x ).hashCode ( )
            ^ new java.lang.Double ( y ).hashCode ( )
            ^ new java.lang.Double ( z ).hashCode ( );
     }

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return "(" + x + "," + y + "," + z + ")";
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
