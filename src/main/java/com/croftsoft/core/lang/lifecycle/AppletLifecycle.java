     package com.croftsoft.core.lang.lifecycle;

     import java.applet.Applet;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Wraps the Lifecycle interface around an Applet.
     *
     * @version
     *   2003-06-04
     * @since
     *   2003-06-04
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AppletLifecycle
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Applet  applet;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AppletLifecycle ( Applet  applet )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.applet = applet );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init    ( ) { applet.init    ( ); }
       
     public void  start   ( ) { applet.start   ( ); }
       
     public void  stop    ( ) { applet.stop    ( ); }
       
     public void  destroy ( ) { applet.destroy ( ); }
       
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }