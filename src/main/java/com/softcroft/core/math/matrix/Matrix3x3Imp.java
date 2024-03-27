    package com.croftsoft.core.math.matrix;
    
    /***********************************************************************
    * Implementation of interface Matrix3x3Mut.
    * 
    * @version
    *   $Id: Matrix3x3Imp.java,v 1.5 2008/05/09 19:48:45 croft Exp $
    * @since
    *   2008-04-25
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public final class  Matrix3x3Imp
      extends MatrixImp
      implements Matrix3x3Mut
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    public  Matrix3x3Imp ( )
    ////////////////////////////////////////////////////////////////////////
    {
      super ( 3, 3 );
    }
    
    public  Matrix3x3Imp ( final Matrix  matrix )
    ////////////////////////////////////////////////////////////////////////
    {
      super ( 3, 3 );
      
      if ( matrix.getRowCount ( ) != 3 )
      {
        throw new IllegalArgumentException ( "rowCount != 3" );
      }
      
      if ( matrix.getColumnCount ( ) != 3 )
      {
        throw new IllegalArgumentException ( "columnCount != 3" );
      }
      
      for ( int  row = 0; row < 3; row++ )
      {
        for ( int  column = 0; column < 3; column++ )
        {
          set ( row, column, matrix.get ( row, column ) );
        }
      }
    }
    
    public  Matrix3x3Imp ( final double [ ] [ ]  values )
    ////////////////////////////////////////////////////////////////////////
    {
      super ( values );
      
      if ( rowCount != 3 )
      {
        throw new IllegalArgumentException ( "rowCount != 3" );
      }
      
      if ( columnCount != 3 )
      {
        throw new IllegalArgumentException ( "columnCount != 3" );
      }
    }
    
    public  Matrix3x3Imp (
      final double  v00,
      final double  v01,
      final double  v02,
      final double  v10,
      final double  v11,
      final double  v12,
      final double  v20,
      final double  v21,
      final double  v22 )
    ////////////////////////////////////////////////////////////////////////
    {
      super ( 3, 3 );
      
      set ( 0, 0, v00 );
      
      set ( 0, 1, v01 );
      
      set ( 0, 2, v02 );
      
      set ( 1, 0, v10 );
      
      set ( 1, 1, v11 );
      
      set ( 1, 2, v12 );
      
      set ( 2, 0, v20 );
      
      set ( 2, 1, v21 );
      
      set ( 2, 2, v22 );
    }
    
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    
    public Matrix3x3Mut  multiply3x3 ( final Matrix3x3  matrix3x3 )
    ////////////////////////////////////////////////////////////////////////
    {
      return Matrix3x3Lib.multiply3x3 ( this, matrix3x3 );
    }
    
    public double [ ]  toEulerAngles ( )
    ////////////////////////////////////////////////////////////////////////
    {
      return Matrix3x3Lib.toEulerAngles ( this );
    }
    
    public Matrix3x3Mut  transpose3x3 ( )
    ////////////////////////////////////////////////////////////////////////
    {
      return Matrix3x3Lib.transpose3x3 ( this );
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }