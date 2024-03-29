    package com.croftsoft.core.lang;

    /***********************************************************************
    * An unknown Enum value was provided.
    * 
    * Throw this RuntimeException in the default case of an enum switch.
    *  
    * @version
    *   $Id: EnumUnknownException.java,v 1.1 2008/05/16 18:39:07 croft Exp $
    * @since
    *   2008-05-16
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public class  EnumUnknownException
      extends RuntimeException
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    private static final long serialVersionUID = 0L;

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    
    public  EnumUnknownException ( final Enum<?>  enumValue )
    ////////////////////////////////////////////////////////////////////////
    {
      super ( enumValue.name ( ) );
    }
     
    public  EnumUnknownException ( )
    ////////////////////////////////////////////////////////////////////////
    {
      // empty
    }
     
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }