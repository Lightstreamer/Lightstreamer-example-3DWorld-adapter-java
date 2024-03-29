    package com.croftsoft.core.math.matrix;
    
    /***********************************************************************
    * A mutable matrix of doubles.
    * 
    * @version
    *   $Id: MatrixMut.java,v 1.2 2008/05/03 00:35:31 croft Exp $
    * @since
    *   2008-04-25
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public interface  MatrixMut
      extends Matrix
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    void  copyToSelf ( Matrix  matrix );
      
    void  multiplyToSelf ( double  scalar );
    
    void  multiplyToSelf ( Matrix  matrix );
      
    void  set (
      int     row,
      int     column,
      double  value );
    
    void  setIdentity ( );
    
    void  transposeSelf ( );
     
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }