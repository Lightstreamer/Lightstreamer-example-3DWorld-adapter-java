    package com.croftsoft.core.math.matrix;
    
    /***********************************************************************
    * Accessor interface for a matrix of doubles.
    * 
    * @version
    *   $Id: Matrix.java,v 1.3 2008/05/09 18:35:55 croft Exp $
    * @since
    *   2008-04-25
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public interface  Matrix
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    MatrixMut  copy ( );
      
    double     get ( int  row, int  column );
    
    int        getColumnCount ( );
      
    int        getRowCount ( );
    
    boolean    isIdentity ( );
    
    boolean    isSquare ( );
    
    boolean    matches ( Matrix  matrix );
    
    boolean    matches (
                 Matrix  matrix,
                 double  tolerance );
    
    MatrixMut  multiply ( Matrix  matrix );
    
    MatrixMut  multiply ( double  scalar );
    
    MatrixMut  transpose ( );
      
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }