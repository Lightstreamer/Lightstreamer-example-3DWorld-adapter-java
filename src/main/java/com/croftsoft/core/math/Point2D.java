     package com.croftsoft.core.math;

     import java.io.Serializable;

     /*********************************************************************
     * A point in the standard Cartesian coordinate system using doubles.
     *
     * <p>
     * This positive x-axis is to the right and the positive y-axis is up.
     * </p>
     *
     * @version
     *   2003-03-20
     *
     * @since
     *   1998-05-29
     *
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *
     * @deprecated
     *   Use Point2DD or java.awt.geom.Point2D.Double instead.
     *********************************************************************/

     public final class  Point2D
       implements Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     public double  x;

     public double  y;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Point2D (
       double  x,
       double  y )
     //////////////////////////////////////////////////////////////////////
     {
       this.x = x;

       this.y = y;
     }

     public  Point2D ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( 0.0, 0.0 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * The angle, in radians, from this point to the target point.
     * Note that the direction of 0 radians is along the positive x-axis
     * and PI/2 radians is along the positive y-axis.
     *********************************************************************/
     public double  angleTo ( Point2D  target )
     //////////////////////////////////////////////////////////////////////
     {
       return Math.atan2 ( target.y - this.y, target.x - this.x );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

