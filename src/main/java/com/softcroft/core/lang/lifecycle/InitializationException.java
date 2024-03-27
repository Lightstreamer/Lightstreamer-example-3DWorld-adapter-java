     package com.croftsoft.core.lang.lifecycle;

     /*********************************************************************
     * Indicates lifecycle object initialization failure.
     *
     * <p>
     * After this RuntimeException subclass is thrown by the init() method,
     * the lifecycle object should be discarded.  No methods, including the
     * destroy() method, should be called as initialization was
     * unsuccessful.
     * </p>
     *
     * @version
     *   2001-03-22
     * @version
     *   2001-02-28
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     *********************************************************************/

     public final class  InitializationException extends RuntimeException
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     private String  rootExceptionString;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  InitializationException (
       String     message,
       Exception  rootException )
     //////////////////////////////////////////////////////////////////////
     {
       super ( message );

       if ( rootException != null )
       {
         this.rootExceptionString = rootException.toString ( );
       }
       else
       {
         this.rootExceptionString = null;
       }
     }
       
     public  InitializationException ( Exception  rootException )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         rootException != null ? rootException.getMessage ( ) : null,
         rootException );
     }
       
     public  InitializationException ( String  message )
     //////////////////////////////////////////////////////////////////////
     {
       this ( message, null );
     }

     public  InitializationException ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getRootExceptionString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return rootExceptionString;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
