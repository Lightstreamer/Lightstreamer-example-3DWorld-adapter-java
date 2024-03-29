    package com.croftsoft.core.math.quat;
    
    /***********************************************************************
    * A mutator interface for a Quat.
    * 
    * @version
    *   $Id: QuatMut.java,v 1.1 2008/05/03 02:14:15 croft Exp $
    * @since
    *   2008-05-02
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public interface  QuatMut
      extends Quat
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    void  setW ( double  w );
      
    void  setX ( double  x );
      
    void  setY ( double  y );
      
    void  setZ ( double  z );
      
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }