     package com.croftsoft.core.math;

     import java.util.Random;

     /*********************************************************************
     * Random number generation methods.
     *
     * @version
     *   2003-04-21
     * @since
     *   1999-01-31
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  RandomLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /** Seeded off of the current time. */
     private static Random  random = new Random ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Test method; prints roll(arg0, arg1, arg2).
     *********************************************************************/
     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       long  multiplier = Long.parseLong ( args [ 0 ] );

       long  base       = Long.parseLong ( args [ 1 ] );

       long  offset     = Long.parseLong ( args [ 2 ] );

       System.out.println ( roll ( multiplier, base, offset ) );
     }

     /*********************************************************************
     * Returns a long between 0 (inclusive) and n (exclusive).
     *
     * @throws IllegalArgumentException
     *
     *   If n is negative.
     *********************************************************************/
     public static long  nextLong ( long  n )
     //////////////////////////////////////////////////////////////////////
     {
       if ( n < 0 )
       {
         throw new IllegalArgumentException ( "negative n:  " + n );
       }

       // Suppose n = 9.
       // Numbers to be generated are 0 to 8 inclusive.
       // Suppose Long.MAX_VALUE is 22.
       // Factor is then 22/9 = 2.
       // Max is then 2*9 = 18.
       // Rolling will generate numbers between 0 and 17.
       // Returned values will be between 0 and 8 inclusive.

       long  factor = Long.MAX_VALUE / n;

       long  max = factor * n;

       long  roll = -1;

       while ( ( roll <  0   )
            || ( roll >= max ) )
       {
         roll = random.nextLong ( );
       }

       return roll % n;
     }

     /*********************************************************************
     * Rolls an n-sided die a specified number of times and adds an offset.
     * For example, to roll a 6-sided die 3 times and add 4 (3d6+4),
     * the multiplier would be 3, the base 6, and the offset 4.
     *
     * @param  multiplier
     *   The number of times to roll the die.  Must be non-negative.
     * @param  base
     *   The number of sides on the die, e.g., six.
     * @param  offset
     *   A final adjustment to add to the sum after all rolls have been
     *   made.
     * @return
     *   Returns the sum of the rolls plus the offset.
     *   The overflow condition is not handled.
     *********************************************************************/
     public static long  roll (
       long  multiplier,
       long  base,
       long  offset )
     //////////////////////////////////////////////////////////////////////
     {
       if ( multiplier < 0 )
       {
         throw new IllegalArgumentException (
           "negative multiplier:  " + multiplier );
       }

       long  sum = 0;

       for ( long  i = 0; i < multiplier; i++ )
       {
         sum += nextLong ( base ) + 1;
       }

       return sum + offset;
     }

     /*********************************************************************
     * Returns a double uniformly distributed between min (inclusive) and
     * max (exclusive).
     *********************************************************************/
     public static double  uniform ( double  min, double  max )
     //////////////////////////////////////////////////////////////////////
     {
       return min + ( max - min ) * random.nextDouble ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
