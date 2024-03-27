     package com.croftsoft.core.math;

     import java.awt.geom.Point2D;
     import java.util.*;

     /*********************************************************************
     * A collection of static methods to supplement java.lang.Math.
     *
     * @version
     *   $Id: MathLib.java,v 1.5 2008/08/09 02:10:33 croft Exp $
     * @since
     *   1998-12-27
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  MathLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( ) );
     }
     
     public static boolean  test ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ( greatestCommonFactor ( 6, 15 ) == 3 )
         && ( log (   1.0, 10.0 ) ==  0.0 )
         && ( log (  10.0, 10.0 ) ==  1.0 )
         && ( log ( 100.0, 10.0 ) ==  2.0 )
         && ( log (   0.5,  2.0 ) == -1.0 )
         && ( log (   1.0,  2.0 ) ==  0.0 )
         && ( log (   2.0,  2.0 ) ==  1.0 )
         && ( log (   4.0,  2.0 ) ==  2.0 )
         && ( wrap ( -190, -180, 360 ) ==  170 )
         && ( wrap ( -180, -180, 360 ) == -180 )
         && ( wrap (  180, -180, 360 ) == -180 )
         && ( wrap (  190, -180, 360 ) == -170 )
         && ( wrap (  370, -180, 360 ) ==   10 )
         && ( wrap (  -10,    0, 360 ) ==  350 )
         && ( wrap (    0,    0, 360 ) ==    0 )
         && ( wrap (   10,    0, 360 ) ==   10 )
         && ( wrap (  360,    0, 360 ) ==    0 )
         && ( wrap (  370,    0, 360 ) ==   10 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     
     public static double  clip (
       final double  value,
       final double  minimum,
       final double  maximum )
     //////////////////////////////////////////////////////////////////////
     {
       if ( minimum > maximum )
       {
         throw new IllegalArgumentException (
           "minimum > maximum:  " + minimum + ", " + maximum );
       }
       
       return
         value < minimum ? minimum : value > maximum ? maximum : value;
     }

     /*********************************************************************
     * Cumulative Distribution Function (CDF).
     * 
     * @see
     *   http://en.wikipedia.org/wiki/Exponential_distribution
     *   #Cumulative_distribution_function
     *********************************************************************/
     public static double  cumulative (
       double  x,
       double  lambda )
     //////////////////////////////////////////////////////////////////////
     {
       if ( x <= 0.0 )
       {
         return 0.0;
       }
       
       return 1.0 - Math.exp ( -lambda * x );
     }

     /*********************************************************************
     * return cumulative ( x, 1 );
     *********************************************************************/
     public static double  cumulative ( double  x )
     //////////////////////////////////////////////////////////////////////
     {
       return cumulative ( x, 1 );
     }

     @SuppressWarnings ( "all" ) // TODO:  fix argument assignment warning
     public static List<Integer>  factor ( int  n )
     //////////////////////////////////////////////////////////////////////
     {
       if ( n < 0 )
       {
         throw new IllegalArgumentException ( "n < 0" );
       }

       List<Integer>  primeList = new ArrayList<Integer> ( );

       if ( n == 0 )
       {
         primeList.add ( new Integer ( 0 ) );

         return primeList;
       }

       if ( n == 1 )
       {
         primeList.add ( new Integer ( 1 ) );

         return primeList;
       }

       for ( int  i = 2; i <= n; i++ )
       {
         if ( n % i == 0 )
         {
           primeList.add ( new Integer ( i ) );

           n = n / i;

           i = 1;
         }
       }

       return primeList;
     }

     public static int  greatestCommonFactor (
       int  n0,
       int  n1 )
     //////////////////////////////////////////////////////////////////////
     {
       int  gcf = 1;

       List<Integer>  primeList0 = factor ( n0 );

       List<Integer>  primeList1 = factor ( n1 );

       Integer [ ]  primeArray0
         = primeList0.toArray ( new Integer [ 0 ] );

       for ( int  i = 0; i < primeArray0.length; i++ )
       {
         Integer  j = primeArray0 [ i ];

         if ( primeList1.contains ( j ) )
         {
           gcf = gcf * j.intValue ( );

           primeList1.remove ( j );
         }
       }

       return gcf;
     }

     /*********************************************************************
     * Calculates the logarithm in the given base.
     *
     * <code><pre>return Math.log ( a ) / Math.log ( base );</pre></code>
     *********************************************************************/
     public static double  log (
       double  a,
       double  base )
     //////////////////////////////////////////////////////////////////////
     {
       return Math.log ( a ) / Math.log ( base );
     }

     /*********************************************************************
     * Also known as the "logistic function".
     *********************************************************************/
     public static double  sigmoid ( double  a )
     //////////////////////////////////////////////////////////////////////
     {
       return 1.0 / ( 1.0 + Math.exp ( -a ) );
     }

     /*********************************************************************
     * The derivative with respect to the argument.
     *********************************************************************/
     public static double  sigmoidDerivative ( double  a )
     //////////////////////////////////////////////////////////////////////
     {
       double  y = sigmoid ( a );
       return y * ( 1.0 - y );
     }

     /*********************************************************************
     * Returns +1 if positive, -1 if negative, otherwise 0.
     *********************************************************************/
     public static byte  signum ( long  l )
     //////////////////////////////////////////////////////////////////////
     {
       return ( byte ) ( l > 0 ? 1 : ( l < 0 ? -1 : 0 ) );
     }

     /*********************************************************************
     * hyperbolic tangent = 2 * sigmoid ( 2 * a ) - 1
     *********************************************************************/
     public static double  tanh ( double  a )
     //////////////////////////////////////////////////////////////////////
     {
       return 2.0 * sigmoid ( 2.0 * a ) - 1.0;
     }

     /*********************************************************************
     * Converts from polar to rectangular coordinates.
     *********************************************************************/
     public static void  toRectangular (
       double   radius,
       double   angle,
       Point2D  point2D )
     //////////////////////////////////////////////////////////////////////
     {
       point2D.setLocation (
         radius * Math.cos ( angle ),
         radius * Math.sin ( angle ) );
     }

     /*********************************************************************
     * Converts from polar to rectangular coordinates.
     *********************************************************************/
     public static Point2D  toRectangular (
       double  radius,
       double  angle )
     //////////////////////////////////////////////////////////////////////
     {
       Point2D  point2D = new Point2D.Double ( );

       toRectangular ( radius, angle, point2D );

       return point2D;
     }
     
     /*********************************************************************
     * Wraps the value to [minimum, minimum + range).
     * 
     * Example:  wrap ( -190, -180, 360 ) ==>  170
     * Example:  wrap (  190, -180, 360 ) ==>  -10
     * Example:  wrap (  360, -180, 360 ) ==> -180
     *********************************************************************/
     public static double  wrap (
       final double  value,
       final double  minimum,
       final double  range )
     //////////////////////////////////////////////////////////////////////
     {
       if ( range <= 0 )
       {
         throw new IllegalArgumentException ( "range <= 0:  " + range );
       }
       
       final double  maximum = minimum + range;
       
       if ( ( value >= minimum )
         && ( value <  maximum ) )
       {
         return value;
       }
       
       if ( value < minimum )
       {
         return value + Math.ceil ( ( minimum - value ) / range ) * range;
       }

       return value
         - ( 1 + Math.floor ( ( value - maximum ) / range ) ) * range;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  MathLib ( )
     //////////////////////////////////////////////////////////////////////
     {
       // empty
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
