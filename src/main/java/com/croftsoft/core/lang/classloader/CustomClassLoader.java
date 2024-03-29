     package com.croftsoft.core.lang.classloader;

     /*********************************************************************
     * <P>
     * Assumes that Java 1.1 is being used and classes are cached as the
     * default behavior as part of the defineClass() method.
     * <P>
     * This should be upgraded when the switch is made from Java 1.1 to
     * Java 1.2.
     * <P>
     * <B>
     * References
     * </B>
     * Scott Oaks, <U>Java Security</U>, O'Reilly, 1998.
     * <P>
     * @version
     *   1998-05-25
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public abstract class  CustomClassLoader extends ClassLoader
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Parses the package name out of a full class name.
     * Returns the empty string ("") if the class is not in a package.
     *********************************************************************/
     public static String  parsePackageName ( String  className )
     //////////////////////////////////////////////////////////////////////
     {
       String  packageName = "";
       int  i = className.lastIndexOf ( '.' );
       if ( i > -1 ) packageName = className.substring ( 0, i );
       return packageName;
     }

     /*********************************************************************
     * Returns the Class of the given name.
     * <P>
     * Calls loadClassData() if the class was not previously loaded and
     *   is not a system class.
     *********************************************************************/
     public Class  loadClass ( String  name,  boolean resolve )
     //////////////////////////////////////////////////////////////////////
     // See Ch. 3, "Java Class Loaders", "Implementing a Class Loader",
     // pp44-45.
     //////////////////////////////////////////////////////////////////////
     {
       // Step 1 -- Check for a previously loaded class
       Class  c = findLoadedClass ( name );
       if ( c != null ) return c;

       // Step 2 -- Check to make sure that we can access this class
       String  packageName = null;
       SecurityManager  securityManager = System.getSecurityManager ( );
       if ( securityManager != null )
       {
         packageName = parsePackageName ( name );
// What if in "default" package?
         securityManager.checkPackageAccess ( packageName );
       }

       // Step 3 -- Check for system class first
       try
       {
         c = findSystemClass ( name );
         if ( c != null ) return c;
       }
       catch ( ClassNotFoundException  ex ) { }

       // Step 4 -- Check to make sure that we can define this class
       if ( securityManager != null )
       {
         securityManager.checkPackageDefinition ( packageName );
       }

       // Step 5 -- Read in the class file
       byte [ ]  data = loadClassData ( name );
       if ( data == null ) return null;

       // Step 6 and 7 -- Define the class from the data;
       // this also passes the data through the bytecode verifier
       c = defineClass ( name, data, 0, data.length );

       // Step 8 -- Resolve the internal references of the class
       if ( resolve ) resolveClass ( c );

       return c;
     }

     /*********************************************************************
     * Implement to load the raw bytecode from an external source.
     * @return
     *   May return null on failure.
     *********************************************************************/
// Better to thrown an exception than to return null.
     protected abstract byte [ ]  loadClassData ( String  name );

     /*********************************************************************
     * Calls getSystemResource(name).
     *********************************************************************/
/*
     public URL  getResource ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       return getSystemResource ( name );
     }
*/

     /*********************************************************************
     * Calls getSystemResourceAsStream(name).
     *********************************************************************/
/*
     public InputStream  getResourceAsStream ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       return getSystemResourceAsStream ( name );
     }
*/

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
