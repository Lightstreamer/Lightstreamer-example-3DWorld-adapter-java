     package com.croftsoft.core.io;

     import java.io.*;

     /*********************************************************************
     * An Encoder and Parser implementation that uses object serialization.
     *
     * @version
     *   2003-05-28
     * @since
     *   2003-05-13
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SerializableCoder
       implements Encoder, Parser
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final SerializableCoder  INSTANCE
       = new SerializableCoder ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public byte [ ]  encode ( Object  object )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return SerializableLib.compress ( ( Serializable ) object );
     }

     public Object  parse (
       InputStream  inputStream,
       int          contentLength )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return SerializableLib.load ( inputStream );
       }
       catch ( ClassNotFoundException  ex )
       {
         throw ( IOException ) new IOException ( ).initCause ( ex );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  SerializableCoder ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
