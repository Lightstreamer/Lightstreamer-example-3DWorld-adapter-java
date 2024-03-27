     package com.croftsoft.core.lang.classloader;

     import java.awt.*;
     import java.io.*;

     /*********************************************************************
     * <P>
     * @version
     *   1998-06-03
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  DialogClassLoader
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Loads a remote class and launches its main() method.
     *
     * @param
     *   Command-line arguments:
     *   <OL>
     *   <LI> The URL name for the codebase.
     *   <LI> The name of the class with the "main(args)" method.
     *   <LI> The name of the local persistent resource cache directory.
     *   <LI> Subsequent arguments to be passed to the invoked main method.
     *   </OL>
     *   Example
     *********************************************************************/
     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       if ( args.length >= 3 )
       {
         CacheClassLoader.main ( args );
         return;
       }

       ( new DialogClassLoader ( ) ).start ( );
     }

     public  DialogClassLoader ( )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( System.getProperty ( "java.class.path" ) );

// Run the user's classpath searching for "mayhem.class".
// When you find it, assume that that is the cache path.
// Look for a settings file in that directory.
// If there is no settings file in that directory, assume defaults and prompt.

//     System.out.println ( System.getProperty ( "user.home" ) );

       File  file0 = new File ( "." );
       try
       {
         System.out.println ( file0.getCanonicalPath ( ) );
       }
       catch ( Exception  ex ) { }

       Frame  frame = new Frame ( "Mystic Mayhem Installation" );
       FileDialog  fileDialog = new FileDialog (
         frame, "Mystic Mayhem - please choose the installation directory",
         FileDialog.SAVE );
       fileDialog.setFile ( "mayhem" );
       frame.setBounds ( 0, 0, 300, 300 );
       fileDialog.show ( );
       frame.show ( );
       // don't forget to dispose of both
       File  file = new File ( fileDialog.getDirectory ( )
         + File.separator + fileDialog.getFile ( ) );

       try
       {
         System.out.println ( file.getCanonicalPath ( ) );
       }
       catch ( Exception  ex ) { }

       fileDialog.dispose ( );
       frame.dispose ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
