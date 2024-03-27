     package com.croftsoft.core.math.geom;

     import java.awt.Shape;

     /*********************************************************************
     * A read-only accessor interface for a Circle.
     *
     * @version
     *   2003-04-17
     * @since
     *   2003-04-17
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface CircleAccessor
       extends Shape
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public PointXY  getCenter  ( );

     public double   getCenterX ( );

     public double   getCenterY ( );

     public double   getRadius  ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  intersectsCircle ( CircleAccessor  circleAccessor );

     public boolean  intersectsShape ( Shape  shape );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }