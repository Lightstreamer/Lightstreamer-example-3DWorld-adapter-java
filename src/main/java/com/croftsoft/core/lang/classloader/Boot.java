     package com.croftsoft.core.lang.classloader;

     import java.io.*;
     import java.lang.reflect.*;
     import java.net.*;

     /*********************************************************************
     * The Boot ClassLoader bootstraps a main class downloaded directly off
     * of a web site.  The argument to this program is the URL of the
     * main class bytecode file.  Any additional arguments are passed to
     * the downloaded class.
     *
     * <P>
     *
     * The Boot class can be readily distributed to a wide audience since
     * it is small and simple.  With a little customization, it can be
     * hard-coded to download from a particular URL.  It can also be used
     * as a "seed" class by having it invoke a persistent resource loader.
     *
     * <P>
     *
     * @version
     *   1999-11-27
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  Boot extends ClassLoader
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       if ( args.length < 1 )
       {
         System.out.println (
           "\nBoot (1999-11-27) David Wallace Croft (croft@orbs.com)" );
         System.out.println (
           "Updates available from \"http://www.orbs.com/\".\n" );
         System.out.println (
           "Bootstraps the main(args) method of a class available "
           + "from a web site." );
         System.out.println (
           "Arguments:  URL [other...]" );
         System.out.println ( "Example:  java -jar boot.jar "
           + "http://www.orbs.com/lib/Main.class username password" );
         return;
       }

       byte [ ]  data = downloadBytes ( new URL ( args [ 0 ] ) );
       if ( data == null )
       {
         System.out.println (
           "Unable to download \"" + args [ 0 ] + "\"." );
         return;
       }

       String [ ]  shiftedArgs = new String [ args.length - 1 ];
       for ( int  i = 0; i < shiftedArgs.length; i++ )
       {
         shiftedArgs [ i ] = args [ i + 1 ];
       }

       new Boot ( ).bootstrap ( data, shiftedArgs );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Returns null upon failure.
     *********************************************************************/
     public static byte [ ]  downloadBytes ( URL  url )
     //////////////////////////////////////////////////////////////////////
     {
       InputStream            inputStream = null;
       BufferedInputStream    in          = null;
       ByteArrayOutputStream  out         = null;

       try
       {
         inputStream = downloadStream ( url );
         if ( inputStream == null ) return null;
         in = new BufferedInputStream ( inputStream );
         out = new ByteArrayOutputStream ( );
         int  i;
         while ( ( i = in.read ( ) ) > -1 ) out.write ( i );
         out.close ( );
         in.close ( );
         return out.toByteArray ( );
       }
       catch ( Exception  ex )
       {
         try { in.close          ( ); } catch ( Exception  ex1 ) { }
         try { inputStream.close ( ); } catch ( Exception  ex1 ) { }
         try { out.close         ( ); } catch ( Exception  ex1 ) { }
         return null;
       }
     }

     /*********************************************************************
     * Returns null upon failure.
     *********************************************************************/
     public static InputStream  downloadStream ( URL  url )
     //////////////////////////////////////////////////////////////////////
     {
       InputStream  inputStream = null;
       try
       {
         URLConnection  urlConnection = url.openConnection ( );
         if ( urlConnection instanceof HttpURLConnection )
         {
           HttpURLConnection  httpURLConnection
             = ( HttpURLConnection ) urlConnection;
           httpURLConnection.setFollowRedirects ( true );
           httpURLConnection.setRequestMethod ( "GET" );
           int  responseCode = httpURLConnection.getResponseCode ( );
           if ( responseCode != HttpURLConnection.HTTP_OK ) return null;
         }
         return urlConnection.getInputStream ( );
       }
       catch ( Exception  ex )
       {
         try { inputStream.close ( ); } catch ( Exception  ex1 ) { }
         return null;
       }
     }

     /*********************************************************************
     * Uses reflection to invoke the main(args) method of a class.
     *********************************************************************/
     public static void  invokeMain ( Class  c, String [ ]  args )
       throws IllegalAccessException
     //////////////////////////////////////////////////////////////////////
     {
       Method  method;
       try
       {
         method = c.getMethod (
           "main", new Class [ ] { String [ ].class } );
       }
       catch ( NoSuchMethodException  ex )
       {
         System.out.println ( "No \"main(args)\" method in class \""
           + c.getName ( ) + "\"." );
         return;
       }
       try
       {
         method.invoke ( null, new Object [ ] { args } );
       }
       catch ( InvocationTargetException  ex )
       {
         Throwable  t = ex.getTargetException ( );
         System.err.println (
           "\"main()\" method exited with exception \"" + t + "\"." );
         t.printStackTrace ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Converts the data to a Class and then invokes its main(args) method.
     *********************************************************************/
     public void  bootstrap ( byte [ ]  data, String [ ]  args )
       throws IllegalAccessException
     //////////////////////////////////////////////////////////////////////
     {
       Class  c = defineClass ( data, 0, data.length );
       invokeMain ( c, args );
     }

     /*********************************************************************
     * Returns the Class of the given name.
     *********************************************************************/
     public Class  loadClass ( String  name,  boolean resolve )
     //////////////////////////////////////////////////////////////////////
     {
       Class  c = findLoadedClass ( name );
       if ( c != null ) return c;

       try
       {
         return findSystemClass ( name );
       }
       catch ( ClassNotFoundException  ex )
       {
         return null;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
