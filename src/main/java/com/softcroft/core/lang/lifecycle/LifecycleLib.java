     package com.croftsoft.core.lang.lifecycle;

     /*********************************************************************
     * Convenience methods for Lifecycle objects.
     *
     * @version
     *   $Date: 2008/04/19 21:27:13 $
     * @since
     *   2002-02-15
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  LifecycleLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Initializes the initializable, catching and printing any Exception.
     *
     * @param  initializable
     *
     *   If null, does nothing.
     *********************************************************************/
     public static void  init ( Initializable  initializable )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         if ( initializable != null )
         {
           initializable.init ( );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     /*********************************************************************
     * Initializes the initializables, catching and printing any Exception.
     *
     * @param  initializables
     *
     *   If null, does nothing.
     *********************************************************************/
     public static void  init ( Initializable...  initializables )
     //////////////////////////////////////////////////////////////////////
     {
       if ( initializables != null )
       {
         for ( int  i = 0; i < initializables.length; i++ )
         {
           init ( initializables [ i ] );
         }
       }
     }

     /*********************************************************************
     * Starts the startable, catching and printing any Exception.
     *
     * @param  startable
     *
     *   If null, does nothing.
     *********************************************************************/
     public static void  start ( Startable  startable )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         if ( startable != null )
         {
           startable.start ( );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     /*********************************************************************
     * Starts the startables, catching and printing any Exception.
     *
     * @param  startables
     *
     *   If null, does nothing.
     *********************************************************************/
     public static void  start ( Startable...  startables )
     //////////////////////////////////////////////////////////////////////
     {
       if ( startables != null )
       {
         for ( int  i = 0; i < startables.length; i++ )
         {
           start ( startables [ i ] );
         }
       }
     }

     /*********************************************************************
     * Stops the stoppable, catching and printing any Exception.
     *
     * @param  stoppable
     *
     *   If null, does nothing.
     *********************************************************************/
     public static void  stop ( Stoppable  stoppable )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         if ( stoppable != null )
         {
           stoppable.stop ( );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     /*********************************************************************
     * Stops the stoppables, catching and printing any Exception.
     *
     * @param  stoppables
     *
     *   If null, does nothing.
     *********************************************************************/
     public static void  stop ( Stoppable...  stoppables )
     //////////////////////////////////////////////////////////////////////
     {
       if ( stoppables != null )
       {
         for ( int  i = 0; i < stoppables.length; i++ )
         {
           stop ( stoppables [ i ] );
         }
       }
     }

     /*********************************************************************
     * Destroys the destroyable, catching and printing any Exception.
     *
     * @param  destroyable
     *
     *   If null, does nothing.
     *********************************************************************/
     public static void  destroy ( Destroyable  destroyable )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         if ( destroyable != null )
         {
           destroyable.destroy ( );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     /*********************************************************************
     * Destroys the destroyables, catching and printing any Exception.
     *
     * @param  destroyables
     *
     *   If null, does nothing.
     *********************************************************************/
     public static void  destroy ( Destroyable...  destroyables )
     //////////////////////////////////////////////////////////////////////
     {
       if ( destroyables != null )
       {
         for ( int  i = 0; i < destroyables.length; i++ )
         {
           destroy ( destroyables [ i ] );
         }
       }
     }

     /*********************************************************************
     * Updates the updatable, catching and printing any Exception.
     *
     * @param  updatable
     *
     *   If null, does nothing.
     *********************************************************************/
     public static void  update ( Updatable  updatable )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         if ( updatable != null )
         {
           updatable.update ( );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     /*********************************************************************
     * Updates the updatables, catching and printing any Exception.
     *
     * @param  updatables
     *
     *   If null, does nothing.
     *********************************************************************/
     public static void  update ( Updatable...  updatables )
     //////////////////////////////////////////////////////////////////////
     {
       if ( updatables != null )
       {
         for ( int  i = 0; i < updatables.length; i++ )
         {
           update ( updatables [ i ] );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  LifecycleLib ( ) { }
       
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }