     package com.croftsoft.core.jnlp;

     import java.io.*;
     import java.net.*;

     /*********************************************************************
     * Static library methods for JNLP.
     *
     * @version
     *   2002-12-22
     * @since
     *   2002-12-22
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  JnlpLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final JnlpServices  JNLP_SERVICES
       = createJnlpServices ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static URL  createFileContentsURL ( String  fileContentsSpec )
       throws MalformedURLException, UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       check ( );

       return JNLP_SERVICES.createFileContentsURL ( fileContentsSpec );
     }

     public static URL  getCodeBase ( )
       throws UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       check ( );

       return JNLP_SERVICES.getCodeBase ( );
     }

     public static byte [ ]  loadBytesUsingPersistenceService (
       String  fileContentsSpec )
       throws IOException, UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       check ( );

       return JNLP_SERVICES.loadBytesUsingPersistenceService (
         fileContentsSpec );
     }

     public static Serializable  loadSerializableUsingPersistenceService (
       String  fileContentsSpec )
       throws ClassNotFoundException, IOException,
         UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       check ( );

       return JNLP_SERVICES.loadSerializableUsingPersistenceService (
         fileContentsSpec );
     }

     public static void  saveBytesUsingPersistenceService (
       String    fileContentsSpec,
       byte [ ]  bytes )
       throws IOException, UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       check ( );

       JNLP_SERVICES.saveBytesUsingPersistenceService (
         fileContentsSpec, bytes );
     }

     public static void  saveSerializableUsingPersistenceService (
       String        fileContentsSpec,
       Serializable  serializable )
       throws IOException, UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       check ( );

       JNLP_SERVICES.saveSerializableUsingPersistenceService (
         fileContentsSpec, serializable );
     }

     public static boolean  showDocument ( URL  url )
       throws UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       check ( );

       return JNLP_SERVICES.showDocument ( url );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private static void  check ( )
       throws UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       if ( JNLP_SERVICES == null )
       {
         throw new UnsupportedOperationException ( );
       }
     }

     private static JnlpServices  createJnlpServices ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return ( JnlpServices )
           Class.forName ( JnlpServices.IMPL_CLASS_NAME ).newInstance ( );
       }
       catch ( Exception  ex )
       {
         return null;
       }
       catch ( NoClassDefFoundError  er )
       {
         return null;
       }
     }

     private  JnlpLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }