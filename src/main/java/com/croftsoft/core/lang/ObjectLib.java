     package com.croftsoft.core.lang;

     /*********************************************************************
     * A collection of static methods to manipulate java.lang.Object.
     * 
     * @version
     *   2002-02-06
     * @since
     *   2001-03-23
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ObjectLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final Object [ ]  EMPTY_OBJECT_ARRAY
       = new Object [ ] { };

     /*********************************************************************
     * Determines if the objects are equal or are both null.
     *
     * @return
     *   object1 == null ? object2 == null : object1.equals ( object2 );
     *********************************************************************/
     public static boolean  equivalent (
       Object  object1,
       Object  object2 )
     //////////////////////////////////////////////////////////////////////
     {
       return
         object1 == null ? object2 == null : object1.equals ( object2 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  ObjectLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
