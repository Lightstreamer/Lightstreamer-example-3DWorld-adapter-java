     package com.croftsoft.core.io;

     import java.applet.*;
     import java.io.*;
     import java.util.zip.*;

     import com.croftsoft.core.applet.AppletLib;
     import com.croftsoft.core.jnlp.JnlpLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Testable;

     /*********************************************************************
     * Saves and loads Serializable objects using GZIP compression.
     *
     * @version
     *   2003-06-13
     * @since
     *   2001-04-25
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SerializableLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  PROPERTY_USER_HOME = "user.home";

     //////////////////////////////////////////////////////////////////////
     // test methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Displays the result of test().
     *********************************************************************/
     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( args ) );
     }

     public static boolean  test ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         final String  TEST = "CroftSoft";

         if ( !TEST.equals (
           load ( new ByteArrayInputStream ( compress ( TEST ) ) ) ) )
         {
           return false;
         }

         String  testCopy = ( String ) copy ( TEST );

         if ( ( testCopy == TEST )
           || !testCopy.equals ( TEST ) )
         {
           return false;
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static byte [ ]  compress ( Serializable  serializable )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( serializable );

       ByteArrayOutputStream  byteArrayOutputStream
         = new ByteArrayOutputStream ( );

       save ( serializable, byteArrayOutputStream );

       return byteArrayOutputStream.toByteArray ( );
     }

     public static Serializable  copy ( Serializable  serializable )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       ByteArrayOutputStream  byteArrayOutputStream
         = new ByteArrayOutputStream ( );

       ObjectOutputStream  objectOutputStream
         = new ObjectOutputStream ( byteArrayOutputStream );

       objectOutputStream.writeObject ( serializable );

       byte [ ]  bytes = byteArrayOutputStream.toByteArray ( );

       ByteArrayInputStream  byteArrayInputStream
         = new ByteArrayInputStream ( bytes );

       ObjectInputStream  objectInputStream
         = new ObjectInputStream ( byteArrayInputStream );

       try
       {
         return ( Serializable ) objectInputStream.readObject ( );
       }
       catch ( ClassNotFoundException  ex )
       {
         // This should never happen since the same classes are used.

         throw new RuntimeException ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // load methods
     //////////////////////////////////////////////////////////////////////

     public static Serializable  load ( InputStream  inputStream )
       throws ClassNotFoundException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( inputStream );

       ObjectInputStream  objectInputStream = null;

       try
       {
         objectInputStream
           = new ObjectInputStream (
               new GZIPInputStream (
                 new BufferedInputStream ( inputStream ) ) );

         return ( Serializable ) objectInputStream.readObject ( );
       }
       finally
       {
         if ( objectInputStream != null )
         {
           objectInputStream.close ( );
         }
         else
         {
           inputStream.close ( );
         }
       }
     }

     public static Serializable  load ( String  filename )
       throws ClassNotFoundException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( filename );

       return load ( new FileInputStream ( filename ) );
     }

     public static Serializable  load (
       String  primaryFilename,
       String  fallbackFilename )
       throws ClassNotFoundException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( primaryFilename );

       if ( fallbackFilename == null )
       {
         return load ( primaryFilename );
       }

       try
       {
         return load ( primaryFilename );
       }
       catch ( FileNotFoundException  ex )
       {
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }

       return load ( fallbackFilename );
     }

     public static Serializable  load (
       ClassLoader  classLoader,
       String       filename )
       throws ClassNotFoundException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( classLoader );

       NullArgumentException.check ( filename    );

       InputStream  inputStream
         = classLoader.getResourceAsStream ( filename );

       if ( inputStream == null )
       {
         return null;
       }

       return load ( inputStream );
     }

     public static Serializable  load (
       String       primaryFilename,
       String       fallbackFilename,
       String       fileContentsSpec,
       Applet       applet,
       String       persistenceKey,
       ClassLoader  classLoader,
       String       resourcePathFilename )
       throws ClassNotFoundException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       Serializable  serializable = null;

       if ( primaryFilename != null )
       {
         try
         {
           String  userHomeDir = System.getProperty ( PROPERTY_USER_HOME );

           String  primaryPath
             = userHomeDir + File.separator + primaryFilename;

           if ( fallbackFilename != null )
           {
             String  fallbackPath
               = userHomeDir + File.separator + fallbackFilename;

             serializable
               = ( Serializable ) load ( primaryPath, fallbackPath );
           }
           else
           {
             serializable = ( Serializable ) load ( primaryPath );
           }
         }
         catch ( FileNotFoundException  ex )
         {
         }
         catch ( SecurityException  ex )
         {
         }
       }

       if ( ( serializable     == null )
         && ( fileContentsSpec != null ) )
       {
         try
         {
           serializable = ( Serializable )
             JnlpLib.loadSerializableUsingPersistenceService (
             fileContentsSpec );
         }
         catch ( UnsupportedOperationException  ex )
         {
         }
       }

       if ( ( serializable   == null )
         && ( applet         != null )
         && ( persistenceKey != null ) )
       {
         try
         {
           serializable = ( Serializable )
             AppletLib.loadSerializableUsingAppletPersistence (
             applet, persistenceKey );
         }
         catch ( UnsupportedOperationException  ex )
         {
         }
       }

       if ( ( serializable         == null )
         && ( classLoader          != null )
         && ( resourcePathFilename != null ) )
       {
         serializable
           = ( Serializable ) load ( classLoader, resourcePathFilename );
       }

       return serializable;
     }

     //////////////////////////////////////////////////////////////////////
     // save methods
     //////////////////////////////////////////////////////////////////////

     public static void  save (
       Serializable  serializable,
       OutputStream  outputStream )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( serializable );

       NullArgumentException.check ( outputStream );

       ObjectOutputStream  objectOutputStream = null;

       try
       {
         objectOutputStream = new ObjectOutputStream (
           new GZIPOutputStream (
             new BufferedOutputStream ( outputStream ) ) );

         objectOutputStream.writeObject ( serializable );
       }
       finally
       {
         if ( objectOutputStream != null )
         {
           objectOutputStream.close ( );
         }
         else
         {
           outputStream.close ( );
         }
       }
     }

     public static void  save (
       Serializable  serializable,
       String        filename,
       boolean       makeDirs )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( serializable );

       NullArgumentException.check ( filename     );

       if ( makeDirs )
       {
         FileLib.makeParents ( filename );
       }

       save ( serializable, new FileOutputStream ( filename ) );
     }

     public static void  save (
       Serializable  serializable,
       String        filename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       save ( serializable, filename, true );
     }

     public static void  save (
       Serializable  serializable,
       String        latestFilename,
       String        backupFilename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( serializable );

       NullArgumentException.check ( latestFilename );

       NullArgumentException.check ( backupFilename );

       File  latestFile = new File ( latestFilename );

       if ( latestFile.exists ( ) )
       {
         File  backupFile = new File ( backupFilename );

         if ( backupFile.exists ( ) )
         {
           backupFile.delete ( );
         }
         else
         {
           FileLib.makeParents ( backupFilename );
         }

         latestFile.renameTo ( backupFile );
       }      

       save ( serializable, latestFilename );
     }

     public static boolean  save (
       Serializable  serializable,
       String        latestFilename,
       String        backupFilename,
       String        fileContentsSpec,
       Applet        applet,
       String        persistenceKey )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( serializable );

       if ( latestFilename != null )
       {
         try
         {
           String  userHomeDir = System.getProperty ( PROPERTY_USER_HOME );

           String  latestPath
             = userHomeDir + File.separator + latestFilename;

           if ( backupFilename != null )
           {
             String  backupPath
               = userHomeDir + File.separator + backupFilename;

             save ( serializable, latestPath, backupPath );
           }
           else
           {
             save ( serializable, latestPath );
           }

           return true;
         }
         catch ( SecurityException  ex )
         {
         }
       }

       if ( fileContentsSpec != null )
       {
         try
         {
           JnlpLib.saveSerializableUsingPersistenceService (
             fileContentsSpec, serializable );

           return true;
         }
         catch ( UnsupportedOperationException  ex )
         {
         }
       }

       if ( ( applet         != null )
         && ( persistenceKey != null ) )
       {
         try
         {
           AppletLib.saveSerializableUsingAppletPersistence (
             applet, persistenceKey, serializable );

           return true;
         }
         catch ( UnsupportedOperationException  ex )
         {
         }
       }

       return false;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  SerializableLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
