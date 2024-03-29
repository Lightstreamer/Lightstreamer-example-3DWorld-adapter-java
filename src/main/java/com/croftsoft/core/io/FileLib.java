     package com.croftsoft.core.io;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * A library of static methods to manipulate File objects.
     *
     * <p />
     * 
     * @version
     *   2001-06-10
     * @since
     *   1999-08-15
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  FileLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Test method.
     *********************************************************************/
     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       File  file = findFile ( args [ 0 ], args [ 1 ], true );
       System.out.println ( file );
     }

     /*********************************************************************
     * Returns null if the file does not already exist.
     *********************************************************************/
     public static File  findFile (
       String  dirname, String  filename, boolean  ignoreFilenameCase )
     //////////////////////////////////////////////////////////////////////
     {
       File  file = new File ( dirname, filename );
       if ( file.exists ( ) ) return file;
       if ( !ignoreFilenameCase ) return null;

       File  dir = null;

       if ( dirname != null )
       {
         dir = new File ( dirname );
         if ( !dir.exists ( ) ) return null;
         if ( !dir.isDirectory ( ) )
         {
           throw new IllegalArgumentException (
           "\"" + dirname + "\" is not a directory." );
         }
       }

       if ( dir == null ) dir = new File ( "." );

       String [ ]  files = dir.list ( );
       for ( int  i = 0; i < files.length; i++ )
       {
         if ( files [ i ].equalsIgnoreCase ( filename ) )
         {
           return new File ( dir, files [ i ] );
         }
       }

       return null;
     }

     /*********************************************************************
     * Parses out the file name extension.
     * <P>
     * @return
     *   Returns null if file name does not have a period;<BR>
     *   returns the empty String ("") if the file name has only one period
     *     and it is the last character;<BR>
     *   otherwise returns everything after the first period.
     *********************************************************************/
     public static String  getExtension ( File  file )
     //////////////////////////////////////////////////////////////////////
     {
       String  name = file.getName ( );
       int  i = name.indexOf ( '.' );
       if ( i < 0 ) return null;
       return name.substring ( i + 1, name.length ( ) - i - 1 );
     }

     /*********************************************************************
     * Creates the parent directories for a file specified by a combined
     *   path and filename String.
     * The name after the last separator in pathFilename is not created
     *   as a directory and is typically a filename.
     * Used when the normal File.mkdirs() operation would mistakenly create
     *   directories for the parent directories plus a directory with the
     *   same name as the filename instead of just directories for the
     *   parent directories when given the combined path and filename.
     * The pathFilename may be just a filename without a path or a
     *   a file in the root directory; in these cases, no directory will
     *   be created.
     *********************************************************************/
     public static boolean  makeParents ( String  pathFilename )
     //////////////////////////////////////////////////////////////////////
     {
       File  file = new File ( pathFilename );

       String  parent = file.getParent ( );

       if ( parent == null )
       {
         return false;
       }

       File  dir = new File ( parent );

       return dir.mkdirs ( );
     }

     /*********************************************************************
     * Replaces instances of oldString with newString in the inFile.
     * <P>
     * Creates a temporary file (inFile + ".tmp") in the process.
     *
     * @return
     *   Returns true if the file was updated.
     *********************************************************************/
     public static boolean  replaceStrings (
       File  file, String  oldString, String  newString )
       throws FileNotFoundException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       byte [ ]  oldBytes = oldString.getBytes ( );
       byte [ ]  newBytes = newString.getBytes ( );
       return replaceBytes ( file, oldBytes, newBytes );
     }

     /*********************************************************************
     * Replaces instances of oldBytes with newBytes in the inFile.
     * <P>
     * Creates a temporary file (inFile + ".tmp") in the process.
     *
     * @return
     *   Returns true if the file was updated.
     *********************************************************************/
     public static boolean  replaceBytes (
       File  file, byte [ ]  oldBytes, byte [ ]  newBytes )
       throws FileNotFoundException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       String  fileName = file.getCanonicalPath ( );
       String  tmpFileName = fileName + ".tmp";
       File  tmpFile = new File ( tmpFileName );
       if ( tmpFile.exists ( ) )
       {
         throw new IOException ( "Temporary file \"" + tmpFileName
           + "\" already exists." );
       }
       try
       {
         boolean  hasChanged
           = replaceBytes ( file, tmpFile, oldBytes, newBytes );
         if ( !hasChanged ) return false;
         if ( !file.delete ( ) )
         {
           throw new IOException ( "Unable to delete to replace \""
             + fileName + "\"." );
         }
         if ( !tmpFile.renameTo ( new File ( fileName ) ) )
         {
           throw new IOException ( "Original file deleted but unable"
             + " to rename the updated file to the original name (\""
             + fileName + "\", \"" + tmpFileName + "\")." );
         }
       }
       finally
       {
         tmpFile.delete ( );
       }
       return true;
     }

     /*********************************************************************
     * Copies inFile to outFile with the replacement of occurrences of
     * oldString with newString.
     * <P>
     * Any pre-existing outFile is overwritten.
     *
     * @return
     *   Returns true if outFile differs from inFile.
     *********************************************************************/
     public static boolean  replaceStrings (
       File  inFile, File  outFile, String  oldString, String  newString )
       throws FileNotFoundException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       byte [ ]  oldBytes = oldString.getBytes ( );
       byte [ ]  newBytes = newString.getBytes ( );
       return replaceBytes ( inFile, outFile, oldBytes, newBytes );
     }

     /*********************************************************************
     * Copies inFile to outFile with the replacement of occurrences of
     * oldBytes with newBytes.
     * <P>
     * Any pre-existing outFile is overwritten.
     *
     * @return
     *   Returns true if outFile differs from inFile.
     *********************************************************************/
     public static boolean  replaceBytes (
       File      inFile,
       File      outFile,
       byte [ ]  oldBytes,
       byte [ ]  newBytes )
       throws FileNotFoundException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       if ( ( inFile   == null )
         || ( outFile  == null )
         || ( oldBytes == null )
         || ( newBytes == null ) )
       {
         throw new IllegalArgumentException ( "null argument" );
       }

       if ( !inFile.isFile ( ) )
       {
         throw new IllegalArgumentException (
           "\"" + inFile.getCanonicalPath ( ) + "\" is not a file." );
       }
/*
       if ( !outFile.isFile ( ) )
       {
         throw new IllegalArgumentException (
           "\"" + outFile.getCanonicalPath ( ) + "\" is not a file." );
       }
*/

       BufferedInputStream  in = new BufferedInputStream (
         new FileInputStream ( inFile ) );
       BufferedOutputStream  out = new BufferedOutputStream (
         new FileOutputStream ( outFile ) );

       boolean  hasChanged
         = StreamLib.replaceBytes ( in, out, oldBytes, newBytes );

       out.close ( );
       in.close ( );

       return hasChanged;
     }

     /*********************************************************************
     * Loads the text file content into a String.
     *********************************************************************/
     public static String  loadTextFile ( String  filename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       if ( filename == null )
       {
         throw new IllegalArgumentException ( "null filename" );
       }

       FileReader   in  = null;

       StringWriter out = null;

       try
       {
         in  = new FileReader ( filename );

         out = new StringWriter ( );

         int  i;

         while ( ( i = in.read ( ) ) > -1 )
         {
           out.write ( ( byte ) i );
         }

         out.flush ( );

         return out.toString ( );
       }
       finally
       {
         try { in.close  ( ); } catch ( Exception  ex ) { }

         try { out.close ( ); } catch ( Exception  ex ) { }
       }
     }

     /*********************************************************************
     * Strips off the file name extension.
     *********************************************************************/
     public static String  pareExtension ( String  fileName )
     //////////////////////////////////////////////////////////////////////
     {
       if ( fileName == null )
       {
         throw new IllegalArgumentException ( "null" );
       }

       int  index = fileName.lastIndexOf ( '.' );

       if ( index < 0 )
       {
         return fileName;
       }

       return fileName.substring ( 0, index );
     }

     /*********************************************************************
     * Saves text to a file.
     *********************************************************************/
     public static void  saveTextFile (
       String  filename,
       String  text )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( filename );

       NullArgumentException.check ( text     );

       FileWriter  fileWriter = null;

       try
       {
         fileWriter = new FileWriter ( filename );

         fileWriter.write ( text );
       }
       finally
       {
         if ( fileWriter != null )
         {
           fileWriter.close ( );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  FileLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
