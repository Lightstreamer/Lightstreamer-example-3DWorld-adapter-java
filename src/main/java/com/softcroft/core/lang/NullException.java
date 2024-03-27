    package com.croftsoft.core.lang;

    /**********************************************************************
    * A RuntimeException that is thrown when the arguments are null.
    * 
    * <p>
    * The static convenience method <i>check()</i> is a useful shorthand
    * notation for checking whether object constructor method arguments
    * are null:
    * <pre><code>
    * public  Book (
    *   final String  title,
    *   final String  author )
    * {
    *   NullException.check (
    *     this.title  = title,
    *     this.author = author );
    * }
    * </code></pre>
    * </p>
    * 
    * @version
    *   $Id: NullException.java,v 1.1 2008/02/10 22:53:21 croft Exp $
    * @since
    *   2008-02-10
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    **********************************************************************/

    public final class  NullException
      extends RuntimeException
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    {
       
    private static final long  serialVersionUID = 0L;

    ///////////////////////////////////////////////////////////////////////
    // public static methods
    ///////////////////////////////////////////////////////////////////////
     
    public static void  check ( Object...  args )
    ///////////////////////////////////////////////////////////////////////
    {
      for ( final Object  arg : args )
      {
        if ( arg == null )
        {
          throw new NullException ( );
        }
      }
    }

    ///////////////////////////////////////////////////////////////////////
    // public constructor methods
    ///////////////////////////////////////////////////////////////////////

    public  NullException ( )
    ///////////////////////////////////////////////////////////////////////
    {
      // empty
    }

    public  NullException ( String  detailMessage )
    ///////////////////////////////////////////////////////////////////////
    {
      super ( detailMessage );
    }

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    }