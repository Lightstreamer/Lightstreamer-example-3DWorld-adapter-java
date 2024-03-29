     package com.croftsoft.core.math.geom;

     import java.io.Serializable;

     /*********************************************************************
     * A mutable point in three-dimensional integer space (x, y, and z).
     *
     * @version
     *   $Id: Point3DI.java,v 1.3 2008/09/20 02:51:51 croft Exp $
     * @since
     *   2001-03-07
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Point3DI
       implements Cloneable, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private int  x;

     private int  y;

     private int  z;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static boolean  equivalent (
       Point3DI  aPoint3DI,
       Point3DI  bPoint3DI )
     //////////////////////////////////////////////////////////////////////
     {
       if ( aPoint3DI == null )
       {
         return bPoint3DI == null;
       }
       
       return aPoint3DI.equals ( bPoint3DI );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Point3DI (
       int  x,
       int  y,
       int  z )
     //////////////////////////////////////////////////////////////////////
     {
       this.x = x;

       this.y = y;

       this.z = z;
     }

     public  Point3DI ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( 0, 0, 0 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  getX ( ) { return x; }

     public int  getY ( ) { return y; }

     public int  getZ ( ) { return z; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setX ( int  x ) { this.x = x; }

     public void  setY ( int  y ) { this.y = y; }

     public void  setZ ( int  z ) { this.z = z; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     @Override
     public boolean  equals ( Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       if ( other == null )
       {
         return false;
       }

       if ( !this.getClass ( ).equals ( other.getClass ( ) ) )
       {
         return false;
       }

       Point3DI  that = ( Point3DI ) other;

       return ( this.x == that.x )
         &&   ( this.y == that.y )
         &&   ( this.z == that.z );
     }

     @Override
     public int  hashCode ( )
     //////////////////////////////////////////////////////////////////////
     {
       // This might be a poor choice for a hash code algorithm.

       return x ^ y ^ z;
     }

     @Override
     public Object  clone ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return super.clone ( );
       }
       catch ( CloneNotSupportedException  ex )
       {
         // This will never happen.

         throw new RuntimeException ( );
       }
     }

     @Override
     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return "<Point3DI>"
            +   "<x>" + x + "</x>"
            +   "<y>" + y + "</y>"
            +   "<z>" + z + "</z>"
            + "</Point3DI>";
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
