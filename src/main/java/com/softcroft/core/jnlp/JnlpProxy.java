     package com.croftsoft.core.jnlp;

     import java.lang.reflect.*;
     import java.net.*;

     /*********************************************************************
     * Uses reflection to access JNLP services.
     *
     * @see
     *   <a target="_blank" 
     *     href="http://croftsoft.com/library/tutorials/browser/">
     *   Launching a Browser from Java</a>
     *
     * @version
     *   2001-10-23
     * @since
     *   2001-08-31
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  JnlpProxy
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final Object  basicServiceObject
       = getBasicServiceObject ( );

     private static final Class   basicServiceClass
       = getBasicServiceClass ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       showDocument ( new URL ( args [ 0 ] ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static boolean  showDocument ( URL  url )
     //////////////////////////////////////////////////////////////////////
     {
       if ( basicServiceObject == null )
       {
         return false;
       }

       try
       {
         Method  method = basicServiceClass.getMethod (
           "showDocument", new Class [ ] { URL.class } );

         Boolean  resultBoolean = ( Boolean )
           method.invoke ( basicServiceObject, new Object [ ] { url } );

         return resultBoolean.booleanValue ( );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         throw new RuntimeException ( ex.getMessage ( ) );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private static Object  getBasicServiceObject ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         Class  serviceManagerClass
           = Class.forName ( "javax.jnlp.ServiceManager" );

         Method  lookupMethod = serviceManagerClass.getMethod ( "lookup",
           new Class [ ] { String.class } );

         return lookupMethod.invoke (
           null, new Object [ ] { "javax.jnlp.BasicService" } );
       }
       catch ( Exception  ex )
       {
         return null;
       }
     }

     private static Class  getBasicServiceClass ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return Class.forName ( "javax.jnlp.BasicService" );
       }
       catch ( Exception  ex )
       {
         return null;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  JnlpProxy ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
