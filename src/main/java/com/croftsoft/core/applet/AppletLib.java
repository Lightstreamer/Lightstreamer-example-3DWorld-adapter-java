     package com.croftsoft.core.applet;

     import java.applet.*;
     import java.io.*;

     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Static library methods for manipulating Applets.
     *
     * @version
     *   $Id: AppletLib.java,v 1.3 2008/09/28 21:50:42 croft Exp $
     * @since
     *   2002-12-22
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AppletLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Loads GZIP compressed data.
     *********************************************************************/
     public static Serializable  loadSerializableUsingAppletPersistence (
       Applet  applet,
       String  key )
       throws ClassNotFoundException, IOException,
         UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( applet, "null applet" );

       NullArgumentException.check ( key, "null key" );

       AppletContext  appletContext = null;

       try
       {
         appletContext = applet.getAppletContext ( );
       }
       catch ( NullPointerException  ex )
       {
         // ignore
       }

       if ( appletContext == null )
       {
         throw new UnsupportedOperationException ( "null AppletContext" );
       }

       InputStream  inputStream = appletContext.getStream ( key );

       if ( inputStream == null )
       {
         return null;
       }

       return SerializableLib.load ( inputStream );
     }

     /*********************************************************************
     * Saves data using GZIP compression.
     *********************************************************************/
     public static void  saveSerializableUsingAppletPersistence (
       Applet        applet,
       String        key,
       Serializable  serializable )
       throws IOException, UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( applet, "null applet" );

       NullArgumentException.check ( key, "null key" );

       NullArgumentException.check ( serializable, "null serializable" );

       AppletContext  appletContext = null;

       try
       {
         appletContext = applet.getAppletContext ( );
       }
       catch ( NullPointerException  ex )
       {
         // ignore
       }

       if ( appletContext == null )
       {
         throw new UnsupportedOperationException ( "null AppletContext" );
       }

       InputStream  inputStream = new ByteArrayInputStream (
         SerializableLib.compress ( serializable ) );

       appletContext.setStream ( key, inputStream );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  AppletLib ( ) { /* empty */ }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }