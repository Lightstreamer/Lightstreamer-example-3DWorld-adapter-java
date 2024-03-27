     package com.croftsoft.core.io;

     import java.io.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * A FilterReader that echoes characters read to a Writer.
     *
     * <p />
     *
     * @version
     *   2002-09-19
     * @since
     *   2002-09-19
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  EchoReader
       extends FilterReader
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Writer  echoWriter;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *********************************************************************/
     public  EchoReader (
       Reader  in,
       Writer  echoWriter )
     //////////////////////////////////////////////////////////////////////
     {
       super ( in );

       NullArgumentException.check ( this.echoWriter = echoWriter );
     }

     /*********************************************************************
     * Echoes to the standard output.
     * <code><pre>this ( in, new PrintWriter ( System.out ) );</pre></code>
     *********************************************************************/
     public  EchoReader ( Reader  in )
     //////////////////////////////////////////////////////////////////////
     {
       this ( in, new PrintWriter ( System.out ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  read ( )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       int  ch = super.read ( );

       if ( ch > -1 )
       {
         echoWriter.write ( ch );
       }

       return ch;
     }

     public int  read (
       char [ ]  cbuf,
       int       off,
       int       len )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       int  count = super.read ( cbuf, off, len );

       if ( count > -1 )
       {
         echoWriter.write ( cbuf, off, count );
       }

       return count;
     }

     public int  read ( char [ ]  cbuf )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       int  count = super.read ( cbuf );

       if ( count > -1 )
       {
         echoWriter.write ( cbuf, 0, count );
       }

       return count;
     }

     /*********************************************************************
     * Flushes the echoWriter in addition to closing the input.
     *********************************************************************/
     public void  close ( )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       super.close ( );

       echoWriter.flush ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
