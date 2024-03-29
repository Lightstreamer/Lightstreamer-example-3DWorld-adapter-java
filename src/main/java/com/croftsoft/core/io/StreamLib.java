     package com.croftsoft.core.io;

     import java.io.*;
     import java.util.*;

     /*********************************************************************
     *
     * A library of static methods for the manipulation of input and
     * output streams.
     *
     * <P>
     *
     * @version
     *   2000-04-30
     * @since
     *   1999-08-15
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     *********************************************************************/

     public final class  StreamLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private  StreamLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( System.getProperty ( "file.encoding" ) );
     }

     /*********************************************************************
     * Replaces bytes in a stream as it is being copied from the
     * InputStream to the OutputStream.
     *
     * <P>
     * 
     * Does not automatically close the streams after completion.
     * Maintains an internal buffer with the same size as oldBytes.
     *
     * @return
     *   Returns true if bytes were replaced.
     *********************************************************************/
     public static boolean  replaceBytes (
       InputStream   in,
       OutputStream  out,
       byte [ ]      oldBytes,
       byte [ ]      newBytes )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       byte [ ]  bufBytes = new byte [ oldBytes.length ];

       boolean  hasChanged = false;

       int  i;

       byte  b;

       int  index = 0;

       while ( ( i = in.read ( ) ) > -1 )
       {
         b = ( byte ) i;

         if ( b == oldBytes [ index ] )
         {
           if ( index + 1 < bufBytes.length )
           {
             bufBytes [ index++ ] = b;
           }
           else
           {
             out.write ( newBytes );

             hasChanged = true;

             index = 0;
           }
         }
         else
         {
           out.write ( bufBytes, 0, index );

           out.write ( b );

           index = 0;
         }
       }

       return hasChanged;
     }

     /*********************************************************************
     * Converts the bytes of the InputStream to a String.
     *
     * <P>
     * 
     * Does not automatically close the stream after completion.
     *********************************************************************/
     public static String  toString (
       InputStream  inputStream,
       String       encoding )
       throws IOException, UnsupportedEncodingException
     //////////////////////////////////////////////////////////////////////
     {
       ByteArrayOutputStream  byteArrayOutputStream
         = new ByteArrayOutputStream ( );

       int  i;

       while ( ( i = inputStream.read ( ) ) > -1 )
       {
         byteArrayOutputStream.write ( i );
       }

       return byteArrayOutputStream.toString ( encoding );
     }

     /*********************************************************************
     * Converts the bytes of the InputStream to a String.
     *
     * <P>
     * 
     * Does not automatically close the stream after completion.
     *********************************************************************/
     public static String  toString (
       InputStream  inputStream )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       ByteArrayOutputStream  byteArrayOutputStream
         = new ByteArrayOutputStream ( );

       int  i;

       while ( ( i = inputStream.read ( ) ) > -1 )
       {
         byteArrayOutputStream.write ( i );
       }

       return byteArrayOutputStream.toString ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
