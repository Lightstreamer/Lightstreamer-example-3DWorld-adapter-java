     package com.croftsoft.core.lang.lifecycle;

     /*********************************************************************
     * Strictly enforces the Lifecycle method calling order by frameworks.
     *
     * <p>
     * Methods throw an IllegalStateException if they are called out of
     * order.  The acceptable state transition sequence is as follows:
     * <code>
     * <pre>
     * init() --> ( start() --> stop() )* --> destroy().
     * </pre>
     * </code>
     * [* start() then stop() may be called zero or more times.]
     *
     * <p>
     * LifecycleEnforcer can be used via inheritance, by delegation, or as
     * a wrapper.
     * </p>
     *
     * <p>
     * When used via inheritance, LifecycleEnforcer is subclassed and
     * the subclass methods call the corresponding superclass methods
     * as their first actions.
     * </p>
     *
     * <p>
     * Inheritance example:
     * <code>
     * <pre>
     * public class  ActiveResource1
     *   extends LifecycleEnforcer
     * {
     *   public  ActiveResource1 ( )
     *   {
     *     super ( ); // use the zero argument superclass constructor
     *
     *     // insert subclass specific constructor code here
     *   }
     *
     *   public void  init ( )
     *   {
     *     super.init ( ); // may throw IllegalStateException
     *
     *     // insert subclass specific initialization here
     *   }
     *
     *   public void  start ( )
     *   {
     *     super.start ( ); // may throw IllegalStateException
     *
     *     // insert subclass specific start code here
     *   }
     *
     *   public void  stop ( )
     *   {
     *     super.stop ( ); // may throw IllegalStateException
     *
     *     // insert subclass specific stop code here
     *   }
     *
     *   public void  destroy ( )
     *   {
     *     super.destroy ( ); // may throw IllegalStateException
     *
     *     // insert subclass specific destroy code here
     *   }
     * }
     * </pre>
     * </code>
     * </p>
     *
     * <p>
     * When used via delegation, it is much like inheritance
     * except that a reference is maintained to a LifecycleEnforcer
     * instead of subclassing from it.  The containing Lifecycle
     * instance will delegate Lifecycle method calls to the delegate
     * LifecycleEnforcer as the first action.
     * </p>
     *
     * <p>
     * Delegation example:
     * <code>
     * <pre>
     * public class  ActiveResource2
     * {
     *   private final Lifecycle  lifecycleEnforcer;
     *
     *   public  ActiveResource1 ( )
     *   {
     *     lifecycleEnforcer = new LifecycleEnforcer ( );
     *   }
     *
     *   public void  init ( )
     *   {
     *     lifecycleEnforcer.init ( ); // may throw IllegalStateException
     *
     *     // insert subclass specific initialization here
     *   }
     *
     *   public void  start ( )
     *   {
     *     lifecycleEnforcer.start ( ); // may throw IllegalStateException
     *
     *     // insert subclass specific start code here
     *   }
     *
     *   public void  stop ( )
     *   {
     *     lifecycleEnforcer.stop ( ); // may throw IllegalStateException
     *
     *     // insert subclass specific stop code here
     *   }
     *
     *   public void  destroy ( )
     *   {
     *     lifecycleEnforcer.destroy ( ); // may throw IllegalStateException
     *
     *     // insert subclass specific destroy code here
     *   }
     * }
     * </pre>
     * </code>
     * </p>
     *
     * <p>
     * When used as a wrapper, LifecycleEnforcer acts as a
     * protective exterior around a private Lifecycle instance.
     * Calls to the lifecycle methods are delegated to the
     * private instance only after checking for proper state
     * transitions.  The wrapper
     * has the added benefit of effectively making all but the
     * lifecycle methods of the private instance inaccessible by
     * direct reference.
     * </p>
     *
     * <p>
     * Wrapper example:
     * <code>
     * <pre>
     * Lifecycle  unprotectedLifecycle = new ActiveResource3 ( );
     *
     * Lifecycle  protectedLifecycle
     *   = new LifecycleEnforcer ( unprotectedLifecycle );
     *
     * untrustedFramework.manageLifecycleObject ( protectedLifecycle );
     * </pre>
     * </code>
     * </p>
     *
     * @version
     *   2001-05-31
     * @since
     *   2001-03-08
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     *********************************************************************/

     public class  LifecycleEnforcer
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final int  STATE_UNINITIALIZED = 0;

     public static final int  STATE_INITIALIZED   = 1;

     public static final int  STATE_STARTED       = 2;

     public static final int  STATE_STOPPED       = 3;

     public static final int  STATE_DESTROYED     = 4;

     private final Lifecycle  lifecycle;

     private int  state = STATE_UNINITIALIZED;
     
       
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  LifecycleEnforcer ( Lifecycle  lifecycle )
     //////////////////////////////////////////////////////////////////////
     {
       this.lifecycle = lifecycle;
     }

     public  LifecycleEnforcer ( )
     //////////////////////////////////////////////////////////////////////
     {
       this.lifecycle = null;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized int  getState ( ) { return state; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       switch ( state )
       {
         case STATE_UNINITIALIZED:

           break;

         case STATE_INITIALIZED:

           // drop through

         case STATE_STARTED:

           // drop through

         case STATE_STOPPED:

           throw new IllegalStateException ( "already initialized" );

         case STATE_DESTROYED:

           throw new IllegalStateException ( "destroyed" );

         default:

           throw new IllegalStateException ( "illegal state:  " + state );
       }
       
       if ( lifecycle != null )
       {
         lifecycle.init ( );
       }

       state = STATE_INITIALIZED;
     }

     public synchronized void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       switch ( state )
       {
         case STATE_UNINITIALIZED:

           throw new IllegalStateException ( "not yet initialized" );

         case STATE_INITIALIZED:

           break;

         case STATE_STARTED:

           throw new IllegalStateException ( "already started" );

         case STATE_STOPPED:

           break;

         case STATE_DESTROYED:

           throw new IllegalStateException ( "destroyed" );

         default:

           throw new IllegalStateException ( "illegal state:  " + state );
       }
       
       if ( lifecycle != null )
       {
         lifecycle.start ( );
       }

       state = STATE_STARTED;
     }

     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       switch ( state )
       {
         case STATE_UNINITIALIZED:

           throw new IllegalStateException ( "not yet initialized" );

         case STATE_INITIALIZED:

           throw new IllegalStateException ( "not yet started" );

         case STATE_STARTED:

           break;

         case STATE_STOPPED:

           throw new IllegalStateException ( "already stopped" );

         case STATE_DESTROYED:

           throw new IllegalStateException ( "destroyed" );

         default:

           throw new IllegalStateException ( "illegal state:  " + state );
       }
       
       if ( lifecycle != null )
       {
         lifecycle.stop ( );
       }

       state = STATE_STOPPED;
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       switch ( state )
       {
         case STATE_UNINITIALIZED:

           throw new IllegalStateException ( "not yet initialized" );

         case STATE_INITIALIZED:

           break;

         case STATE_STARTED:

           throw new IllegalStateException ( "not stopped" );

         case STATE_STOPPED:

           break;

         case STATE_DESTROYED:

           throw new IllegalStateException ( "already destroyed" );

         default:

           throw new IllegalStateException ( "illegal state:  " + state );
       }
       
       if ( lifecycle != null )
       {
         lifecycle.destroy ( );
       }

       state = STATE_DESTROYED;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
