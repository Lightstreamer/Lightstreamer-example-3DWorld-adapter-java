     package com.croftsoft.core.jnlp;

     import java.io.*;
     import java.net.*;

     /*********************************************************************
     * Interface for JnlpServicesImpl.
     *
     * @version
     *   2002-12-22
     * @since
     *   2002-12-21
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  JnlpServices
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  IMPL_CLASS_NAME
       = "com.croftsoft.core.jnlp.JnlpServicesImpl";

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public URL  createFileContentsURL ( String  fileContentsSpec )
       throws MalformedURLException, UnsupportedOperationException;

     public URL  getCodeBase ( )
       throws UnsupportedOperationException;

     public byte [ ]  loadBytesUsingPersistenceService (
       String  fileContentsSpec )
       throws IOException, UnsupportedOperationException;

     public Serializable  loadSerializableUsingPersistenceService (
       String  fileContentsSpec )
       throws ClassNotFoundException, IOException,
         UnsupportedOperationException;

     public void  saveBytesUsingPersistenceService (
       String    fileContentsSpec,
       byte [ ]  bytes )
       throws IOException, UnsupportedOperationException;

     public void  saveSerializableUsingPersistenceService (
       String        fileContentsSpec,
       Serializable  serializable )
       throws IOException, UnsupportedOperationException;

     public boolean  showDocument ( URL  url )
       throws UnsupportedOperationException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
