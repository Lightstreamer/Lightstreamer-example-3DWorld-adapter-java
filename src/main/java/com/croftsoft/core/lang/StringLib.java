     package com.croftsoft.core.lang;

     import java.io.*;
     import java.util.*;

     /*********************************************************************
     * A collection of static methods to manipulate java.lang.String.
     * 
     * @version
     *   2001-08-15
     * @since
     *   1998-10-04
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  StringLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String [ ]  ZERO_LENGTH_STRING_ARRAY
       = new String [ ] { };

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( replace ( "0", "0", "12345" ) );
       System.out.println ( replace ( "1abc5", "abc", "234" ) );
       System.out.println ( replace ( "1a", "a", "2345" ) );
       System.out.println ( replace ( "1235", "23", "234" ) );
     }

     /*********************************************************************
     * @see #padRight
     *********************************************************************/
     public static String  padLeft ( String  s, char  c, int  length )
     //////////////////////////////////////////////////////////////////////
     {
       if ( s == null ) return null;
       if ( s.length ( ) >= length ) return new String ( s );
       while ( s.length ( ) < length ) s = c + s;
       return s;
     }

     /*********************************************************************
     * @see #padLeft
     *********************************************************************/
     public static String  padRight ( String  s, char  c, int  length )
     //////////////////////////////////////////////////////////////////////
     {
       if ( s == null ) return null;
       if ( s.length ( ) >= length ) return new String ( s );
       while ( s.length ( ) < length ) s = s + c;
       return s;
     }

     /*********************************************************************
     * Returns a String with the characters between
     * <I>fromIndex</I> and <I>toIndex</I> removed, inclusive.
     *********************************************************************/
     public static String  remove (
       String  s, int  fromIndex, int  toIndex )
     //////////////////////////////////////////////////////////////////////
     {
       return s.substring ( 0, fromIndex ) + s.substring ( toIndex + 1 );
     }

     /*********************************************************************
     * Replaces substrings in a String.
     * Replaces every instance of <I>oldSub</I> in <I>s</I> with
     *   <I>newSub</I>.
     *********************************************************************/
     public static String  replace (
       String  s, String  oldSub, String  newSub )
     //////////////////////////////////////////////////////////////////////
     // When Java 1.2 comes out, use StringBuffer.replace().
     //////////////////////////////////////////////////////////////////////
     {
       StringBuffer  sb = new StringBuffer ( );
       int  index;
       int  remnantIndex = 0;
       while ( ( index = s.indexOf ( oldSub, remnantIndex ) ) > -1 )
       {
         if ( index > remnantIndex )
         {
           sb.append ( s.substring ( remnantIndex, index ) );
         }
         sb.append ( newSub );
         remnantIndex = index + oldSub.length ( );
       }
       sb.append ( s.substring ( remnantIndex ) );
       return sb.toString ( );
     }

     /*********************************************************************
     * Breaks a String at the ends of lines.
     * The original text is broken into an array of String at the
     * end-of-line delimiters such as carriage return, linefeed, or the
     * carriage return/linefeed pair.
     * This is returned as an array of String without the delimiters.
     *********************************************************************/
     public static String [ ]  toStringArray ( String  text )
     //////////////////////////////////////////////////////////////////////
     {
       Vector  stringVector = new Vector ( );

       BufferedReader  bufferedReader
         = new BufferedReader ( new StringReader ( text ) );

       String  line;

       try
       {
         while ( ( line = bufferedReader.readLine ( ) ) != null )
         {
           stringVector.addElement ( line );
         }
       }
       catch ( IOException  ex )
       {
         // this should never happen
       }

       String [ ]  stringArray = new String [ stringVector.size ( ) ];

       stringVector.copyInto ( stringArray );

       return stringArray;
     }

     /*********************************************************************
     * Returns null if the trimming operation results in the empty String.
     *
     * @param  s
     *
     *   May be null.
     *
     * @return
     *
     *   Returns the argument trimmed or null if the argument is null
     *   or the argument trimmed is "".
     *********************************************************************/
     public static String  trimToNull ( String  s )
     //////////////////////////////////////////////////////////////////////
     {
       if ( s == null )
       {
         return null;
       }

       s = s.trim ( );

       return "".equals ( s ) ? null : s;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  StringLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
