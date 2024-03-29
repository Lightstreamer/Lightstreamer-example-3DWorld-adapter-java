     package com.croftsoft.core.lang.lifecycle;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * A Lifecycle using interface composition.
     *
     * @version
     *   $Id: CompositeLifecycle.java,v 1.1 2006/01/03 19:40:08 croft Exp $
     * @since
     *   2006-01-03
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  CompositeLifecycle
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Initializable [ ]  initializables;
     
     private final Startable     [ ]  startables;
     
     private final Stoppable     [ ]  stoppables;
     
     private final Destroyable   [ ]  destroyables;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  CompositeLifecycle (
       final Initializable [ ]  initializables,       
       final Startable     [ ]  startables,       
       final Stoppable     [ ]  stoppables,       
       final Destroyable   [ ]  destroyables )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.initializables = initializables );
       
       NullArgumentException.check ( this.startables   = startables   );
       
       NullArgumentException.check ( this.stoppables   = stoppables   );
       
       NullArgumentException.check ( this.destroyables = destroyables );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init    ( ) { LifecycleLib.init   ( initializables ); }
       
     public void  start   ( ) { LifecycleLib.start   ( startables    ); }
       
     public void  stop    ( ) { LifecycleLib.stop    ( stoppables    ); }
       
     public void  destroy ( ) { LifecycleLib.destroy ( destroyables  ); }
       
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }