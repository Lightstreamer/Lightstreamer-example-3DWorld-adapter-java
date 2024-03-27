     package com.croftsoft.core.math.geom;

     import java.awt.*;
     import java.awt.geom.*;
     import java.io.*;

     /*********************************************************************
     * A circle Shape.
     *
     * Currently extends Ellipse2D.Double as an implementation convenience
     * but this may change in the future.
     *
     * @version
     *   2003-05-13
     * @since
     *   2003-04-13
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  Circle
       extends Ellipse2D.Double
       implements CircleAccessor, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final Point2DD  center;

     //

     private double  radius;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Circle (
       double  centerX,
       double  centerY,
       double  radius )
     //////////////////////////////////////////////////////////////////////
     {
       center = new Point2DD ( centerX, centerY );

       setRadius ( radius );
     }

     public  Circle ( CircleAccessor  circleAccessor )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         circleAccessor.getCenterX ( ),
         circleAccessor.getCenterY ( ),
         circleAccessor.getRadius  ( ) );
     }

     public  Circle ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( 0.0, 0.0, 0.0 );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public PointXY  getCenter  ( ) { return center;   }

     public double   getCenterX ( ) { return center.x; }

     public double   getCenterY ( ) { return center.y; }

     public double   getRadius  ( ) { return radius;   }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setCenter (
       double  centerX,
       double  centerY )
     //////////////////////////////////////////////////////////////////////
     {
       center.setXY ( centerX, centerY );

       x = center.x - radius;

       y = center.y - radius;
     }

     public void  setCenter ( PointXY  pointXY )
     //////////////////////////////////////////////////////////////////////
     {
       setCenter ( pointXY.getX ( ), pointXY.getY ( ) );
     }

     public void  setRadius ( double  radius )
     //////////////////////////////////////////////////////////////////////
     {
       if ( radius < 0.0 )
       {
         throw new IllegalArgumentException ( "radius < 0.0:  " + radius );
       }

       this.radius = radius;

       x = center.x - radius;

       y = center.y - radius;

       width  = 2 * radius;

       height = 2 * radius;
     }

     //////////////////////////////////////////////////////////////////////
     // overridden Ellipse2D.Double methods
     //////////////////////////////////////////////////////////////////////

     public boolean  contains (
       double  x,
       double  y )
     //////////////////////////////////////////////////////////////////////
     {
       return center.distance ( x, y ) <= radius;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  intersectsCircle ( CircleAccessor  circleAccessor )
     //////////////////////////////////////////////////////////////////////
     {
       double  distance
         = center.distanceXY ( circleAccessor.getCenter ( ) );

       return distance <= radius + circleAccessor.getRadius ( );
     }

     public boolean  intersectsShape ( Shape  shape )
     //////////////////////////////////////////////////////////////////////
     {
       if ( shape instanceof CircleAccessor )
       {
         return intersectsCircle ( ( CircleAccessor ) shape );
       }

       if ( radius == 0.0 )
       {
         return shape.contains ( center.x, center.y );
       }

       return intersects ( shape.getBounds2D ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return "(" + center.x + "," + center.y + "," + radius + ")";
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

       objectOutputStream.writeDouble ( width );

       objectOutputStream.writeDouble ( height );
     }

     private void  readObject ( ObjectInputStream  objectInputStream )
       throws IOException, ClassNotFoundException
     //////////////////////////////////////////////////////////////////////
     {
       objectInputStream.defaultReadObject ( );

       x      = objectInputStream.readDouble ( );

       y      = objectInputStream.readDouble ( );

       width  = objectInputStream.readDouble ( );

       height = objectInputStream.readDouble ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }