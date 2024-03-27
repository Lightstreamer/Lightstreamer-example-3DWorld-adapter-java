     package com.croftsoft.core.math.geom;

     /*********************************************************************
     * A read-only accessor interface for double precision x,y coordinates.
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

     public interface  PointXY
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public double  getX ( );

     public double  getY ( );

     //

     public double  angleTo  ( PointXY  otherPointXY );

     public double  distanceXY ( PointXY  otherPointXY );

     public double  distance (
       double  otherX,
       double  otherY );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

