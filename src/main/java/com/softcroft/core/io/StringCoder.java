     package com.croftsoft.core.io;

     import java.io.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * A String Encoder and Parser.
     *
     * @version
     *   2003-06-02
     * @since
     *   2003-06-01
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  StringCoder
       implements Encoder, Parser
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  US_ASCII = "US-ASCII";

     public static final String  UTF_8    = "UTF-8";

     //

     private final String  charSetName;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  StringCoder ( String  charSetName )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.charSetName = charSetName );       
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public byte [ ]  encode ( Object  object )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return object.toString ( ).getBytes ( charSetName );
     }

     public Object  parse (
       InputStream  inputStream,
       int          contentLength )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return StreamLib.toString ( inputStream, charSetName );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
