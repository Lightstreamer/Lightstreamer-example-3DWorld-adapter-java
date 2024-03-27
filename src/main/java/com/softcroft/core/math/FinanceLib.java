     package com.croftsoft.core.math;

import java.io.*;

     /*********************************************************************
     * Financial calculations.
     *
     * @version
     *   2001-10-10
     * @since
     *   1999-08-15
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  FinanceLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final void  testRetire (
       double  desiredSavingsInterestIncome,
       double  savingsInterestRate,
       double  inflationRate,
       double  taxRate,
       double  investmentInterestRate,
       double  yearsOfSaving )
     //////////////////////////////////////////////////////////////////////
     {
     
//     desiredSavingsInterestIncome
//       = savings
//       * ( savingsInterestRate * ( 1.0 - taxRate ) - inflationRate )
//       / ( 1.0 + inflationRate );
     
       double  savings
         = desiredSavingsInterestIncome * ( 1.0 + inflationRate )
         / ( savingsInterestRate * ( 1.0 - taxRate ) - inflationRate );

       double  futureValueSavings
         = savings * Math.pow ( 1.0 + inflationRate, yearsOfSaving );

       double  annualSavings = annualSavingsNeeded (
         futureValueSavings, investmentInterestRate, yearsOfSaving );

       System.out.println ( savings );
       System.out.println ( futureValueSavings );
       System.out.println ( annualSavings );
       System.out.println ( futureValueAnnuity (
         annualSavings, investmentInterestRate, yearsOfSaving ) );
       System.out.println ( presentValueAnnuity (
         annualSavings, investmentInterestRate, yearsOfSaving ) );
     }

     /*********************************************************************
     * Test method.  Prints the future value of an annuity followed by
     * the present value calculated three different ways.
     *
     * @param c Annual cash income starting one year from today.
     * @param r Annual interest earned on income.
     * @param t Number of years of cash income.
     *********************************************************************/
     public static final void  testAnnuity (
       double  C,
       double  r,
       double  T )
     //////////////////////////////////////////////////////////////////////
     {
       double  FV = futureValueAnnuity ( C, r, T );
       System.out.println ( FV );
       System.out.println ( presentValue ( FV, r, T ) );
       System.out.println ( presentValueAnnuity ( C, r, T ) );
       double [ ]  c = new double [ ( int ) T ];
       for ( int  i = 0; i < T; i++ ) c [ i ] = C;
       System.out.println ( presentValue ( c, r ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * The future value of a cash flow received today.
     *
     * @param c Cash flow today.
     * @param r Inflation rate.
     * @param t Number of years from today when the value is evaluated.
     *********************************************************************/
     public static final double  futureValue (
       double  c,
       double  r,
       double  t )
     //////////////////////////////////////////////////////////////////////
     {
       return c * Math.pow ( 1.0 + r, t );
     }

     /*********************************************************************
     * Calculates the future value of an annuity.
     *
     * @param c Annual cash income starting one year from today.
     * @param r Annual interest earned on income.
     * @param t Number of years of cash income.
     *********************************************************************/
     public static final double  futureValueAnnuity (
       double  c,
       double  r,
       double  t )
     //////////////////////////////////////////////////////////////////////
     {
       return c * ( ( Math.pow ( 1.0 + r, t ) - 1.0 ) / r );
     }

     /*********************************************************************
     * The calculated discount rate where the net present value is 0.
     *
     * @param  irrEstimate
     *
     *   The initial estimated value for the IRR (e.g., 0.10 for 10%).
     *
     * @param  cashFlows
     *
     *   Array of cash flows received in the future, indexed from time = 0.
     *********************************************************************/
     public static final double  internalRateOfReturn (
       double      irrEstimate,
       double [ ]  cashFlows )
     //////////////////////////////////////////////////////////////////////
     {
       double  irr = irrEstimate;

       double  delta = -irr * 0.1;

       double  oldNpv = 0.0;

       while ( true )
       {
         double  npv = netPresentValue ( irr, cashFlows );

         if ( npv == 0.0 )
         {
           return irr;
         }

         if ( oldNpv < 0.0 )
         {
           if ( npv > 0.0 )
           {
             delta *= -0.9;
           }
           else if ( npv > oldNpv )
           {
             delta *= 1.1;
           }
           else if ( npv < oldNpv )
           {
             delta = -delta;
           }
           else
           {
             delta = 0.0;
           }
         }
         else if ( oldNpv > 0.0 )
         {
           if ( npv < 0.0 )
           {
             delta *= -0.9;
           }
           else if ( npv < oldNpv )
           {
             delta *= 1.1;
           }
           else if ( npv > oldNpv )
           {
             delta = -delta;
           }
           else
           {
             delta = 0.0;
           }
         }

/*
System.out.println (  "irr = " + irr + ", oldNpv = " + oldNpv + ", npv = " + npv + ", delta = " + delta );

try{
new BufferedReader ( new InputStreamReader ( System.in ) ).readLine ( );
}catch (Exception x ) { }
*/

         if ( delta == 0.0 )
         {
           return irr;
         }

         irr += delta;

         oldNpv = npv;
       }
     }

     /*********************************************************************
     * The discounted value of multiple cash flows received in the future.
     *
     * @param  discountRate
     *
     *   The discount rate or cost of capital (e.g., 0.10 for 10%).
     *
     * @param  cashFlows
     *
     *   Array of cash flows received in the future, indexed from time = 0.
     *********************************************************************/
     public static final double  netPresentValue (
       double      discountRate,
       double [ ]  cashFlows )
     //////////////////////////////////////////////////////////////////////
     {
       double  npv = 0.0;

       for ( int  i = 0; i < cashFlows.length; i++ )
       {
         npv += cashFlows [ i ] / Math.pow ( 1.0 + discountRate, i );
       }

       return npv;
     }

     /*********************************************************************
     * The discounted value of a single cash flow received in the future.
     *
     * @param c Cash flow received in the future
     * @param r Inflation or annual interest.
     * @param t Number of years from today when the cash flow is received.
     *********************************************************************/
     public static final double  presentValue (
       double  c,
       double  r,
       double  t )
     //////////////////////////////////////////////////////////////////////
     {
       return c / Math.pow ( 1.0 + r, t );
     }

     /*********************************************************************
     * The discounted value of varying annual cash flows.
     *
     * @param c Array of annual cash income starting one year from today.
     * @param r Annual interest earned on income.
     *********************************************************************/
     public static final double  presentValue (
       double [ ]  c,
       double      r )
     //////////////////////////////////////////////////////////////////////
     {
       double  sum = 0.0;
       for ( int  i = 0; i < c.length; i++ )
       {
         sum += presentValue ( c [ i ], r, i + 1 );
       }
       return sum;
     }

     /*********************************************************************
     * Calculates the present value of an annuity.
     *
     * @param c Annual cash income starting one year from today.
     * @param r Inflation or annual interest.
     * @param t Number of years of cash income.
     *********************************************************************/
     public static final double  presentValueAnnuity (
       double  c,
       double  r,
       double  t )
     //////////////////////////////////////////////////////////////////////
     {
       return c * ( 1.0 - 1.0 / Math.pow ( 1.0 + r, t ) ) / r;
     }

     /*********************************************************************
     * Calculates the annual savings necessary to accumulate a specified
     * value in the future.
     *
     * @param f Future value desired.
     * @param r Annual interest.
     * @param t Number of years of savings.
     *********************************************************************/
     public static final double  annualSavingsNeeded (
       double  f,
       double  r,
       double  t )
     //////////////////////////////////////////////////////////////////////
     {
       return f * r / ( Math.pow ( 1.0 + r, t ) - 1.0 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  FinanceLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

