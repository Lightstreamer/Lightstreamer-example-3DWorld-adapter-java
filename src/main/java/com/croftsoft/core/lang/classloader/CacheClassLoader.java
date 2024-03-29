     package com.croftsoft.core.lang.classloader;

     import java.io.*;
     import java.lang.reflect.*;
     import java.net.*;
     import java.util.*;

     /*********************************************************************
     * <P>
     * @version
     *   1998-05-30
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  CacheClassLoader extends NetClassLoader
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /** A list of pathnames of cached files keyed by remote URL name. */
     protected Hashtable  cacheHashtable = new Hashtable ( );

     protected File  cacheDir;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Loads a remote class and launches its main() method.
     *
     * @param
     *   Command-line arguments:
     *   <OL>
     *   <LI> The URL name for the codebase.
     *   <LI> The name of the class with the "main(args)" method.
     *   <LI> The name of the local persistent resource cache directory.
     *   <LI> Subsequent arguments to be passed to the invoked main method.
     *   </OL>
     *   Example
     *********************************************************************/
     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       String [ ]  shiftedArgs = new String [ args.length - 3 ];
       for ( int  i = 3; i < args.length; i++ )
       {
         shiftedArgs [ i - 3 ] = args [ i ];
       }

       launchMain ( args [ 0 ], args [ 1 ], args [ 2 ], shiftedArgs );
     }

     /*********************************************************************
     * Loads a remote class and launches its main() method.
     *********************************************************************/
     public static void  launchMain (
       String      codebaseURLName,
       String      mainClassName,
       String      cacheDirName,
       String [ ]  args )
       throws ClassNotFoundException,
              IllegalAccessException,
              MalformedURLException
     //////////////////////////////////////////////////////////////////////
     {
       URL  codebaseURL = new URL ( codebaseURLName );
       File  cacheDir = new File ( cacheDirName );
       CacheClassLoader  cacheClassLoader
         = new CacheClassLoader ( codebaseURL, cacheDir );
       Class  c = cacheClassLoader.loadClass ( mainClassName );
       cacheClassLoader.invokeMain ( c, args );
     }

     /*********************************************************************
     * Ex:  "http://www.mysticmayhem.com/lib/",
     *      "C:\jcache\www.mysticmayhem.com\"
     *********************************************************************/
     public  CacheClassLoader ( URL  codebaseURL, File  cacheDir )
     //////////////////////////////////////////////////////////////////////
     {
       super ( codebaseURL );

       if ( !cacheDir.exists ( ) )
       {
         throw new IllegalArgumentException ( "directory \"" + cacheDir
           + "\" does not exist" );
       }
       if ( !cacheDir.isDirectory ( ) )
       {
         throw new IllegalArgumentException ( "cacheDir \"" + cacheDir
           + "\" must be a directory" );
       }
       this.cacheDir = cacheDir;
     }

     /*********************************************************************
     *********************************************************************/
     public synchronized InputStream  getResourceAsStream ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       InputStream           inputStream = null;
       BufferedInputStream   in          = null;
       BufferedOutputStream  out         = null;

       try
       {
         URL  url = getResource ( name );
         String  remoteName = url.toExternalForm ( );
         String  localName = ( String ) cacheHashtable.get ( remoteName );
         if ( localName != null )
         {
System.out.println ( "Retrieving \"" + localName + "\"..." );
           return new FileInputStream ( localName );
         }

         String  host = url.getHost ( );
         String  prot = url.getProtocol ( );
         int  port = url.getPort ( );

         File  cacheFile = new File ( cacheDir, prot + File.separator
           + host + File.separator + "port"
           + ( port == -1 ? "" : Integer.toString ( port ) )
           + File.separator + name );
         cacheFile = new File ( cacheFile.getCanonicalPath ( ) );
         localName = cacheFile.getCanonicalPath ( );

System.out.println ( "Comparing \"" + localName + "\"..." );
         URLConnection  urlConnection = url.openConnection ( );
         if ( cacheFile.exists ( ) )
         {
           urlConnection.setIfModifiedSince (
             cacheFile.lastModified ( ) );
         }

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

         inputStream = urlConnection.getInputStream ( );
         if ( inputStream == null ) return null;

         if ( cacheFile.exists ( ) )
         {
           long  lastModified = urlConnection.getLastModified ( );
//         long  date = urlConnection.getDate ( );
//         long  remoteDelta = date - lastModified;
//         long  cacheDelta
//           = new Date ( ).getTime ( ) - cacheFile.lastModified ( );
//         long  length = urlConnection.getContentLength ( );

//         if ( ( date         >   0 )
//           && ( lastModified >   0 )
//           && ( length       >  -1 )
//           && ( length       == cacheFile.length ( ) )
//           && ( remoteDelta  >= cacheDelta ) )

           if ( ( lastModified > 0 )
             && ( lastModified < cacheFile.lastModified ( ) ) )
           {
             inputStream.close ( );
             cacheHashtable.put ( remoteName, localName );
System.out.println ( "Retrieving \"" + localName + "\"..." );
             return new FileInputStream ( cacheFile );
           }
         }

         File  parentFile = new File ( cacheFile.getParent ( ) );
         parentFile.mkdirs ( );

         localName = cacheFile.getCanonicalPath ( );
System.out.println ( "CACHING \"" + localName + "\"..." );

         in = new BufferedInputStream ( inputStream );
         out = new BufferedOutputStream ( new FileOutputStream (
           cacheFile ) );

         int  i;
         while ( ( i = in.read ( ) ) > -1 ) out.write ( i );

         out.close ( );
         in.close ( );

         cacheHashtable.put ( remoteName, localName );

System.out.println ( "Retrieving \"" + localName + "\"..." );
         return new FileInputStream ( localName );
       }
       catch ( Exception  ex )
       {
         try { inputStream.close  ( ); } catch ( Exception  ex1 ) { }
         try { in.close           ( ); } catch ( Exception  ex1 ) { }
         try { out.close          ( ); } catch ( Exception  ex1 ) { }
         ex.printStackTrace ( );
         return null;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
