     package com.croftsoft.core.math.geom;

     import java.awt.Point;
     import java.awt.geom.Point2D;
     import java.io.*;

     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.lang.Testable;

     /*********************************************************************
     * A Point2D.Double extension implementing accessor interface PointXY.
     *
     * @version
     *   2003-04-13
     *
     * @since
     *   2003-03-20
     *
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  Point2DD
       extends Point2D.Double
       implements PointXY, Serializable, Testable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //////////////////////////////////////////////////////////////////////
     // static unit test methods
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
         Point2DD  point2DD1 = new Point2DD ( 0, 1 );

         byte [ ]  bytes = SerializableLib.compress ( point2DD1 );

         Point2DD  point2DD2 = ( Point2DD ) SerializableLib.load (
           new ByteArrayInputStream ( bytes ) );

         System.out.println ( point2DD2 );

         return point2DD2.equals ( point2DD1 );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }
     }

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  Point2DD (
       double  x,
       double  y )
     //////////////////////////////////////////////////////////////////////
     {
       this.x = x;

       this.y = y;
     }

     public  Point2DD ( PointXY  pointXY )
     //////////////////////////////////////////////////////////////////////
     {
       x = pointXY.getX ( );

       y = pointXY.getY ( );
     }

     public  Point2DD ( Point2D  point2D )
     //////////////////////////////////////////////////////////////////////
     {
       x = point2D.getX ( );

       y = point2D.getY ( );
     }

     public  Point2DD ( )
     //////////////////////////////////////////////////////////////////////
     {
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setX ( double  x ) { this.x = x; }

     public void  setY ( double  y ) { this.y = y; }

     public void  setXY ( PointXY  pointXY )
     //////////////////////////////////////////////////////////////////////
     {
       x = pointXY.getX ( );

       y = pointXY.getY ( );
     }

     public void  setXY (
       double  x,
       double  y )
     //////////////////////////////////////////////////////////////////////
     {
       this.x = x;

       this.y = y;
     }

     public void  setXY ( Point  point )
     //////////////////////////////////////////////////////////////////////
     {
       x = point.x;

       y = point.y;
     }

     //////////////////////////////////////////////////////////////////////
     // math methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * The angle, in radians, from this point to the other point.
     * Note that the direction of 0 radians is along the positive x-axis
     * and PI/2 radians is along the positive y-axis.
     *********************************************************************/
     public double  angleTo ( PointXY  otherPointXY )
     //////////////////////////////////////////////////////////////////////
     {
       return Math.atan2 (
         otherPointXY.getY ( ) - y,
         otherPointXY.getX ( ) - x );
     }

     public double  distanceXY ( PointXY  otherPointXY )
     //////////////////////////////////////////////////////////////////////
     {
       return distance ( otherPointXY.getX ( ), otherPointXY.getY ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     // overridden Object methods
     //////////////////////////////////////////////////////////////////////

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return "(" + x + "," + y + ")";
     }

     //////////////////////////////////////////////////////////////////////
     // private serialization methods
     //////////////////////////////////////////////////////////////////////

     private void  writeObject ( ObjectOutputStream  objectOutputStream )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       objectOutputStream.defaultWriteObject ( );

       objectOutputStream.writeDouble ( x );

       objectOutputStream.writeDouble ( y );
     }

     private void  readObject ( ObjectInputStream  objectInputStream )
       throws IOException, ClassNotFoundException
     //////////////////////////////////////////////////////////////////////
     {
       objectInputStream.defaultReadObject ( );

       x = objectInputStream.readDouble ( );

       y = objectInputStream.readDouble ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }