     package com.croftsoft.core.lang.classloader;

     import java.awt.*;
     import java.io.*;
     import java.lang.reflect.*;
     import java.net.*;
     import java.util.*;

     /*********************************************************************
     * <P>
     * Upgrade this source code when the switch is made from Java 1.1 to
     * Java 1.2.
     * <P>
     * <B>
     * References
     * </B>
     * Scott Oaks, <U>Java Security</U>, O'Reilly, 1998.
     * <P>
     * @version
     *   1998-09-06
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  NetClassLoader extends CustomClassLoader
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected URL  codebaseURL;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Loads a remote class and launches its main() method.
     *
     * @param
     *   Command-line arguments:
     *   <OL>
     *   <LI> The URL for the codebase.
     *   <LI> The name of the class with the "main(args)" method.
     *   <LI> Subsequent arguments to be passed to the invoked main method.
     *   </OL>
     *   Example
     *********************************************************************/
     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       String [ ]  shiftedArgs = new String [ args.length - 2 ];
       for ( int  i = 2; i < args.length; i++ )
       {
         shiftedArgs [ i - 2 ] = args [ i ];
       }

       launchMain ( args [ 0 ], args [ 1 ], shiftedArgs );
     }

     /*********************************************************************
     * Loads a remote class and launches its main() method.
     *********************************************************************/
     public static void  launchMain (
       String      codebaseURLName,
       String      mainClassName,
       String [ ]  args )
       throws ClassNotFoundException,
              IllegalAccessException,
              MalformedURLException
     //////////////////////////////////////////////////////////////////////
     {
       URL  codebaseURL = new URL ( codebaseURLName );
       ClassLoader  classLoader = new NetClassLoader ( codebaseURL );
       Class  c = classLoader.loadClass ( mainClassName );
       invokeMain ( c, args );
     }

     public static void  invokeMain ( Class  c, String [ ]  args )
       throws IllegalAccessException
     //////////////////////////////////////////////////////////////////////
     {
       Method  method;

       try
       {
         method = c.getMethod ( "main",
           new Class [ ] { String [ ].class } );
       }
       catch ( NoSuchMethodException  ex )
       {
         System.err.println ( "No main() method in class \""
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

     /*********************************************************************
     * Ex:  "http://www.mysticmayhem.com/lib/"
     *********************************************************************/
     public  NetClassLoader ( URL  codebaseURL )
     //////////////////////////////////////////////////////////////////////
     {
       this.codebaseURL = codebaseURL;
     }

     protected byte [ ]  loadClassData ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         String  pathName = name.replace ( '.', '/' ) + ".class";
         BufferedInputStream  in = new BufferedInputStream (
           getResourceAsStream ( pathName ) );
         if ( in == null ) return null;
         ByteArrayOutputStream  out = new ByteArrayOutputStream ( );
         int  i;
         while ( ( i = in.read ( ) ) > -1 ) out.write ( i );
         in.close ( );
         return out.toByteArray ( );
       }
       catch ( Exception  ex ) { ex.printStackTrace ( ); return null; }
     }

     /*********************************************************************
     *********************************************************************/
     public URL  getResource ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return new URL ( codebaseURL, name );
       }
       catch ( Exception  ex ) { ex.printStackTrace ( ); return null; }
     }

     /*********************************************************************
     * Loads a stream from the URL given by getResource(name).
     * Does not use a cache.
     *********************************************************************/
     public InputStream  getResourceAsStream ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         URL  url = getResource ( name );
System.out.println ( "Loading \"" + url + "\"..." );
         URLConnection  urlConnection = url.openConnection ( );
         if ( urlConnection instanceof HttpURLConnection )
         {
           HttpURLConnection  httpURLConnection
             = ( HttpURLConnection ) urlConnection;
           httpURLConnection.setFollowRedirects ( true );
           httpURLConnection.setRequestMethod ( "GET" );
           int  responseCode = httpURLConnection.getResponseCode ( );
System.out.println (
           httpURLConnection.getResponseMessage ( )
  + ", " + httpURLConnection.getContentLength ( ) + " bytes"
  + ", " + new Date ( httpURLConnection.getDate ( ) )
  + ", " + new Date ( httpURLConnection.getLastModified ( ) ) );
           if ( responseCode != HttpURLConnection.HTTP_OK )
           {
             return null;
           }
         }
         return urlConnection.getInputStream ( );
       }
       catch ( Exception  ex ) { ex.printStackTrace ( ); return null; }
     }

     /*********************************************************************
     * Creates the image by calling getResourceAsStream(imageName).
     *********************************************************************/
     public Image  createImage ( String  imageName )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       InputStream  inputStream = getResourceAsStream ( imageName );
       BufferedInputStream  in = new BufferedInputStream ( inputStream );
       ByteArrayOutputStream  out = new ByteArrayOutputStream ( );
       int  i;
       while ( ( i = in.read ( ) ) > -1 ) out.write ( i );
       byte [ ]  imageData = out.toByteArray ( );
       out.close ( );
       in.close ( );
       return Toolkit.getDefaultToolkit ( ).createImage ( imageData );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
