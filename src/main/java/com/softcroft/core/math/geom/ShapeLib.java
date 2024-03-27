     package com.croftsoft.core.math.geom;

     import java.awt.Shape;
     import java.awt.geom.RectangularShape;

     /*********************************************************************
     * A static method library for manipulating Shape instances.
     *
     * @version
     *   2003-04-17
     * @since
     *   2003-04-13
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ShapeLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static Point2DD  getCenter (
       Shape     shape,
       Point2DD  center )
     //////////////////////////////////////////////////////////////////////
     {
       if ( shape instanceof Circle )
       {
         Circle  circle = ( Circle ) shape;

         center.setXY ( circle.getCenter ( ) );
       }
       else if ( shape instanceof RectangularShape )
       {
         RectangularShape  rectangularShape = ( RectangularShape ) shape;

         center.setXY (
           rectangularShape.getCenterX ( ),
           rectangularShape.getCenterY ( ) );
       }
       else
       {
         getCenter ( shape.getBounds2D ( ), center );
       }

       return center;
     }

     public static boolean  intersects (
       Shape  aShape,
       Shape  bShape )
     //////////////////////////////////////////////////////////////////////
     {
       if ( aShape instanceof Circle )
       {
         return ( ( Circle ) aShape ).intersectsShape ( bShape );
       }
      
       if ( bShape instanceof Circle )
       {
         return ( ( Circle ) bShape ).intersectsShape ( aShape );
       }
      
       return aShape.intersects ( bShape.getBounds2D ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  ShapeLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }