     package com.croftsoft.core.lang;

     import java.io.*;

     /*********************************************************************
     * A collection of static methods to manipulate java.lang.Throwable.
     * 
     * @version
     *   2001-05-18
     * @since
     *   2001-05-18
     * @author
     *   <a href="http://www.CroftSoft.com/">David W. Croft</a>
     *********************************************************************/

     public final class  ThrowableLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( getStackTrace ( new RuntimeException ( ) ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Returns the stack trace as a String.
     *********************************************************************/
     public static String  getStackTrace ( Throwable  throwable )
     //////////////////////////////////////////////////////////////////////
     {
       StringWriter  stringWriter = new StringWriter ( );

       PrintWriter  printWriter = new PrintWriter ( stringWriter );

       throwable.printStackTrace ( printWriter );

       return stringWriter.toString ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  ThrowableLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
