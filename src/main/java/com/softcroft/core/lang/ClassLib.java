     package com.croftsoft.core.lang;

     import java.awt.*;
     import java.io.*;

     /*********************************************************************
     * Static methods to complement the java.lang.Class methods.
     * 
     * @see
     *   java.lang.Class
     *
     * @version
     *   2002-10-29
     * @since
     *   2001-07-24
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ClassLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( args ) );
     }

     public static boolean  test ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       return "ClassLib".equals ( getShortName ( ClassLib.class ) );
     }

     /*********************************************************************
     * Loads a resource into memory as a String.
     *
     * <p>
     * Useful for loading a text file from a JAR on the classpath.
     * </p>
     *
     * <p>
     * Implemented using Class.getResourceAsStream().  Review of the
     * documentation of this method is advised in order to understand the
     * relative search path.
     * </p>
     *
     * @param  relativeClass
     *
     *   The Class used for the text file search.
     *
     * @param  filename
     *
     *   The path to the file, usually started by "/".
     *
     * @see
     *   java.lang.Class#getResourceAsStream(java.lang.String)
     *********************************************************************/
     public static String  getResourceAsText (
       Class   relativeClass,
       String  filename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( relativeClass );

       NullArgumentException.check ( filename );

       BufferedInputStream  bufferedInputStream = null;

       StringWriter out = null;

       try
       {
         bufferedInputStream = new BufferedInputStream (
           relativeClass.getResourceAsStream ( filename ) );

         out = new StringWriter ( );

         int  i;

         while ( ( i = bufferedInputStream.read ( ) ) > -1 )
         {
           out.write ( ( byte ) i );
         }

         out.flush ( );

         return out.toString ( );
       }
       finally
       {
         if ( bufferedInputStream != null )
         {
           bufferedInputStream.close ( );
         }

         if ( out != null )
         {
           out.close ( );
         }
       }
     }

     /*********************************************************************
     * Loads an Image as a class resource.
     *
     * <p>
     * Useful for when you need to load an image file from a JAR.
     * </p>
     *
     * @see
     *   "Zukowski,
     *   <a href="http://amazon.com/exec/obidos/ASIN/189311578X/croftsoft-20">
     *   Definitive Guide to Swing for Java 2</a>,
     *   Second Edition, 2000, p107."
     *********************************************************************/
     public static Image  getResourceAsImage (
       Class   relativeClass,
       String  filename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( relativeClass );

       NullArgumentException.check ( filename );

       BufferedInputStream  bufferedInputStream = null;

       ByteArrayOutputStream  byteArrayOutputStream = null;

       try
       {
         bufferedInputStream = new BufferedInputStream (
           relativeClass.getResourceAsStream ( filename ) );

         byteArrayOutputStream = new ByteArrayOutputStream ( );

         int  c;

         while ( ( c = bufferedInputStream.read ( ) ) > -1 )
         {
           byteArrayOutputStream.write ( c );
         }

         byteArrayOutputStream.flush ( );

         return Toolkit.getDefaultToolkit ( ).createImage (
           byteArrayOutputStream.toByteArray ( ) );
       }
       finally
       {
         if ( bufferedInputStream != null )
         {
           bufferedInputStream.close ( );
         }

         if ( byteArrayOutputStream != null )
         {
           byteArrayOutputStream.close ( );
         }
       }
     }

     /*********************************************************************
     * Returns the name of the Class without the package prefix.
     *
     * <p>
     * For example, this method would return "ClassLib" for
     * class com.croftsoft.core.lang.ClassLib.
     * </p>
     *
     * @see
     *   java.lang.Class#getName(java.lang.String)
     *********************************************************************/
     public static String  getShortName ( Class  c )
     //////////////////////////////////////////////////////////////////////
     {
       String  longName = c.getName ( );

       return longName.substring ( longName.lastIndexOf ( '.' ) + 1 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  ClassLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
