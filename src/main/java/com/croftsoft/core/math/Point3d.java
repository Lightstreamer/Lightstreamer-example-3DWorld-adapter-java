     package com.croftsoft.core.math;

     import java.io.Serializable;

     /*********************************************************************
     * A mutable point in three-dimensional real space (x, y, and z).
     *
     * @version
     *   2003-03-30
     * @since
     *   2002-02-06
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     * @deprecated
     *   Use Point3DD instead.
     *********************************************************************/

     public final class  Point3d
       implements Cloneable, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     private double  x;

     private double  y;

     private double  z;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static boolean  equivalent (
       Point3d  aPoint3d,
       Point3d  bPoint3d )
     //////////////////////////////////////////////////////////////////////
     {
       if ( aPoint3d == null )
       {
         return bPoint3d == null;
       }
       else
       {
         return aPoint3d.equals ( bPoint3d );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Point3d (
       double  x,
       double  y,
       double  z )
     //////////////////////////////////////////////////////////////////////
     {
       this.x = x;

       this.y = y;

       this.z = z;
     }

     public  Point3d ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( 0.0, 0.0, 0.0 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public double  getX ( ) { return x; }

     public double  getY ( ) { return y; }

     public double  getZ ( ) { return z; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setX ( double  x ) { this.x = x; }

     public void  setY ( double  y ) { this.y = y; }

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

       if ( !this.getClass ( ).equals ( other.getClass ( ) ) )
       {
         return false;
       }

       Point3d  that = ( Point3d ) other;

       return ( this.x == that.x )
         &&   ( this.y == that.y )
         &&   ( this.z == that.z );
     }

     public int  hashCode ( )
     //////////////////////////////////////////////////////////////////////
     {
       // This might be a poor choice for a hash code algorithm.

       return new Double ( x ).hashCode ( )
            ^ new Double ( y ).hashCode ( )
            ^ new Double ( z ).hashCode ( );
     }

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

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return "<Point3d>"
            +   "<x>" + x + "</x>"
            +   "<y>" + y + "</y>"
            +   "<z>" + z + "</z>"
            + "</Point3d>";
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
