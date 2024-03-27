     package com.croftsoft.core.lang;

     /*********************************************************************
     * Indicates an Exception while accessing an external resource.
     *
     * <p>
     * Useful for propagating an SQLException or RemoteException through
     * a Data Access Object (DAO) interface.  Define the data accessor
     * interface methods so that they throw ExternalAccessException instead
     * of SQLException, RemoteException, IOException, or any other
     * Exception indicating external access failure.  Since the calling
     * application no longer needs to be hard-coded to handle an Exception
     * subclass specific to the data access mechanism, the mechanism can be
     * easily replaced, at run-time if desired, simply by instantiating a
     * different concrete implementation of the data accessor interface.
     * </p>
     *
     * <p>
     * The original Exception is passed into the ExternalAccessException as
     * a constructor argument.  The Exception stack trace is stored as a
     * String within the ExternalAccessException in order to facilitate
     * serialization and persistence without the need to transport class
     * files.
     * </p>
     *
     * @see
     *   <a href="http://developer.java.sun.com/developer/restricted/patterns/DataAccessObject.html">
     *   Sun Java Center J2EE Patterns:  Data Access Object</a>
     *
     * @see
     *   <a href="http://www.sun.com/tech/techrep/1994/abstract-29.html">
     *   "A Note on Distributed Computing"</a>
     *
     * @version
     *   2001-06-21
     * @since
     *   2001-02-28
     * @author
     *   <a target="_blank" href="http://croftsoft.com/">David W. Croft</a>
     *********************************************************************/

     public final class  ExternalAccessException
       extends Exception
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     private String  rootExceptionStackTrace;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  ExternalAccessException (
       String     message,
       Exception  rootException )
     //////////////////////////////////////////////////////////////////////
     {
       super ( message );

       if ( rootException != null )
       {
         this.rootExceptionStackTrace
           = ThrowableLib.getStackTrace ( rootException );
       }
       else
       {
         this.rootExceptionStackTrace = null;
       }
     }
       
     public  ExternalAccessException ( Exception  rootException )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         rootException != null ? rootException.getMessage ( ) : null,
         rootException );
     }
       
     public  ExternalAccessException ( String  message )
     //////////////////////////////////////////////////////////////////////
     {
       this ( message, null );
     }

     public  ExternalAccessException ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getRootExceptionStackTrace ( )
     //////////////////////////////////////////////////////////////////////
     {
       return rootExceptionStackTrace;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }