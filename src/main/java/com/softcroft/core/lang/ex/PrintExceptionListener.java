     package com.croftsoft.core.lang.ex;

     /*********************************************************************
     *
     * A concrete ExceptionListener that simply calls printStackTrace().
     *
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     * @version
     *   1998-12-05
     *********************************************************************/

     public class  PrintExceptionListener implements ExceptionListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  threwException ( Object  o, Exception  ex )
     //////////////////////////////////////////////////////////////////////
     {
       ex.printStackTrace ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
