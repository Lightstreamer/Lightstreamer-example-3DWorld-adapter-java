     package com.croftsoft.core.lang;

     /*********************************************************************
     * Thrown to indicate that a method has been passed a null argument.
     *
     * <p>
     * The static convenience method <i>check()</i> is a useful shorthand
     * notation for checking whether object constructor method arguments
     * are null:
     * <pre><code>
     * public  Book ( String  title )
     * {
     *   NullArgumentException.check ( this.title = title, "null title" );
     * }
     * </code></pre>
     * </p>
     *
     * @version
     *   $Date: 2008/02/10 22:53:21 $
     * @since
     *   2001-02-16
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  NullArgumentException
       extends IllegalArgumentException
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {
       
     private static final long  serialVersionUID = 0L;

     //////////////////////////////////////////////////////////////////////
     // public static methods
     //////////////////////////////////////////////////////////////////////
     
     /*********************************************************************
     * Checks whether the argument is null.
     *
     * @throws NullArgumentException
     * 
     *   If the argument is null.
     *********************************************************************/
     public static void  check ( Object  argument )
     //////////////////////////////////////////////////////////////////////
     {
       if ( argument == null )
       {
         throw new NullArgumentException ( );
       }
     }

     /*********************************************************************
     * Checks whether the argument is null.
     *
     * @param  detailMessage
     * 
     *   The detail message provided if a NullArgumentExcepton is created.
     *   
     * @throws NullArgumentException
     * 
     *   If the argument is null.
     *********************************************************************/
     public static void  check (
       Object  argument,
       String  detailMessage )
     //////////////////////////////////////////////////////////////////////
     {
       if ( argument == null )
       {
         throw new NullArgumentException ( detailMessage );
       }
     }
     
     public static void  checkArgs ( Object...  args )
     //////////////////////////////////////////////////////////////////////
     {
       for ( Object  argument : args )
       {
         check ( argument );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // public constructor methods
     //////////////////////////////////////////////////////////////////////

     public  NullArgumentException ( )
     //////////////////////////////////////////////////////////////////////
     {
       // empty
     }

     public  NullArgumentException ( String  detailMessage )
     //////////////////////////////////////////////////////////////////////
     {
       super ( detailMessage );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
