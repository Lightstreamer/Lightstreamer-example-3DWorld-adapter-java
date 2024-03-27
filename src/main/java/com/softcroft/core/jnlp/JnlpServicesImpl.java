     package com.croftsoft.core.jnlp;

     import java.io.*;
     import java.net.*;

     import javax.jnlp.*;

     import com.croftsoft.core.io.SerializableLib;

     /*********************************************************************
     * JNLP Services.
     *
     * @version
     *   $Date: 2008/04/19 21:27:13 $
     * @since
     *   2002-12-21
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  JnlpServicesImpl
       implements JnlpServices
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public URL  createFileContentsURL ( String  fileContentsSpec )
       throws MalformedURLException, UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       return new URL ( getCodeBase ( ), fileContentsSpec );
     }

     public URL  getCodeBase ( )
       throws UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         BasicService  basicService = ( BasicService )
           ServiceManager.lookup ( "javax.jnlp.BasicService" );

         return basicService.getCodeBase ( );
       }
       catch ( UnavailableServiceException  ex )
       {
         throw ( UnsupportedOperationException )
           new UnsupportedOperationException ( ).initCause ( ex );
       }
     }

     /*********************************************************************
     * Loads a byte array.
     *********************************************************************/
     public byte [ ]  loadBytesUsingPersistenceService (
       String  fileContentsSpec )
       throws IOException, UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       InputStream  inputStream = null;

       try
       {
         PersistenceService  persistenceService = ( PersistenceService )
           ServiceManager.lookup ( "javax.jnlp.PersistenceService" );

         FileContents  fileContents = persistenceService.get (
           createFileContentsURL ( fileContentsSpec ) );

         ByteArrayOutputStream  byteArrayOutputStream
           = new ByteArrayOutputStream ( );

         inputStream
           = new BufferedInputStream ( fileContents.getInputStream ( ) );

         int  i;

         while ( ( i = inputStream.read ( ) ) > -1 )
         {
           byteArrayOutputStream.write ( i );
         }

         return byteArrayOutputStream.toByteArray ( );
       }
       catch ( FileNotFoundException  ex )
       {
         return null;
       }
       catch ( UnavailableServiceException  ex )
       {
         throw ( UnsupportedOperationException )
           new UnsupportedOperationException ( ).initCause ( ex );
       }
       finally
       {
         if ( inputStream != null )
         {
           inputStream.close ( );
         }
       }
     }

     /*********************************************************************
     * Loads GZIP compressed data.
     *********************************************************************/
     public Serializable  loadSerializableUsingPersistenceService (
       String  fileContentsSpec )
       throws ClassNotFoundException, IOException,
         UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         PersistenceService  persistenceService = ( PersistenceService )
           ServiceManager.lookup ( "javax.jnlp.PersistenceService" );

         FileContents  fileContents = persistenceService.get (
           createFileContentsURL ( fileContentsSpec ) );

         return SerializableLib.load ( fileContents.getInputStream ( ) );
       }
       catch ( FileNotFoundException  ex )
       {
         return null;
       }
       catch ( UnavailableServiceException  ex )
       {
         throw ( UnsupportedOperationException )
           new UnsupportedOperationException ( ).initCause ( ex );
       }
     }

     public void  saveBytesUsingPersistenceService (
       String    fileContentsSpec,
       byte [ ]  bytes )
       throws IOException, UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       BufferedOutputStream  bufferedOutputStream = null;

       try
       {
         PersistenceService  persistenceService = ( PersistenceService )
           ServiceManager.lookup ( "javax.jnlp.PersistenceService" );
         
         URL  fileContentsURL
           = createFileContentsURL ( fileContentsSpec );

         try
         {
           persistenceService.delete ( fileContentsURL );
         }
         catch ( FileNotFoundException  ex )
         {
           // ignore
         }

         persistenceService.create ( fileContentsURL, bytes.length );
       
         FileContents  fileContents
           = persistenceService.get ( fileContentsURL );

         bufferedOutputStream = new BufferedOutputStream (
           fileContents.getOutputStream ( true ) );

         bufferedOutputStream.write ( bytes );
       }
       catch ( UnavailableServiceException  ex )
       {
         throw ( UnsupportedOperationException )
           new UnsupportedOperationException ( ).initCause ( ex );
       }
       finally
       {
         if ( bufferedOutputStream != null )
         {
           bufferedOutputStream.close ( );
         }
       }
     }

     /*********************************************************************
     * Saves data using GZIP compression.
     *********************************************************************/
     public void  saveSerializableUsingPersistenceService (
       String        fileContentsSpec,
       Serializable  serializable )
       throws IOException, UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       saveBytesUsingPersistenceService (
         fileContentsSpec,
         SerializableLib.compress ( serializable ) );
     }

     public boolean  showDocument ( URL  url )
       throws UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         BasicService  basicService = ( BasicService )
           ServiceManager.lookup ( "javax.jnlp.BasicService" );

         return basicService.showDocument ( url );
       }
       catch ( UnavailableServiceException  ex )
       {
         throw ( UnsupportedOperationException )
           new UnsupportedOperationException ( ).initCause ( ex );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }