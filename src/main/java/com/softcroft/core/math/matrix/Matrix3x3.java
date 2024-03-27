    package com.croftsoft.core.math.matrix;
    
    /***********************************************************************
    * Accessor interface for a 3x3 matrix of doubles.
    * 
    * @version
    *   $Id: Matrix3x3.java,v 1.5 2008/05/09 19:48:45 croft Exp $
    * @since
    *   2008-04-25
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public interface  Matrix3x3
      extends Matrix
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    Matrix3x3Mut  multiply3x3 ( Matrix3x3  matrix3x3 );
    
    double [ ]  toEulerAngles ( );
    
    // toAxisAngle()
    
    // toQuat()
    
    Matrix3x3Mut  transpose3x3 ( );
      
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }