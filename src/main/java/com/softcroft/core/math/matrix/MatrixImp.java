    package com.croftsoft.core.math.matrix;
    
    /***********************************************************************
    * Implementation of interface MatrixMut.
    * 
    * @version
    *   $Id: MatrixImp.java,v 1.5 2008/05/09 18:35:56 croft Exp $
    * @since
    *   2008-04-25
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public class  MatrixImp
      implements MatrixMut
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    protected final int
      rowCount,
      columnCount;
      
    protected final double [ ] [ ]  values;
    
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    
    public  MatrixImp (
      final int  rowCount,
      final int  columnCount )
    ////////////////////////////////////////////////////////////////////////
    {
      if ( rowCount < 1 )
      {
        throw new IllegalArgumentException ( "rowCount < 1" );
      }
      
      if ( columnCount < 1 )
      {
        throw new IllegalArgumentException ( "columnCount < 1" );
      }
      
      this.rowCount = rowCount;
      
      this.columnCount = columnCount;
      
      values = new double [ rowCount ] [ columnCount ];
    }
    
    public  MatrixImp ( final Matrix  matrix )
    ////////////////////////////////////////////////////////////////////////
    {
      this ( matrix.getRowCount ( ), matrix.getColumnCount ( ) );
      
      for ( int  row = 0; row < rowCount; row++ )
      {
        for ( int  column = 0; column < columnCount; column++ )
        {
          set ( row, column, matrix.get ( row, column ) );
        }
      }
    }
    
    public  MatrixImp ( final double [ ] [ ]  values )
    ////////////////////////////////////////////////////////////////////////
    {
      rowCount = values.length;
      
      columnCount = values [ 0 ].length;

      if ( rowCount < 1 )
      {
        throw new IllegalArgumentException ( "rowCount < 1" );
      }
      
      if ( columnCount < 1 )
      {
        throw new IllegalArgumentException ( "columnCount < 1" );
      }
      
      this.values = new double [ rowCount ] [ columnCount ];
      
      for ( int  row = 0; row < rowCount; row++ )
      {
        if ( values [ row ].length != columnCount )
        {
          throw new IllegalArgumentException (
            "values[" + row + "].length != values[0].length" );
        }
        
        for ( int  column = 0; column < columnCount; column++ )
        {
          
          this.values [ row ] [ column ] = values [ row ] [ column ];
        }
      }
    }
    
    ////////////////////////////////////////////////////////////////////////
    // accessor methods
    ////////////////////////////////////////////////////////////////////////
    
    public double  get (
      final int  row,
      final int  column )
    ////////////////////////////////////////////////////////////////////////
    {
      return values [ row ] [ column ];
    }
    
    public int  getRowCount    ( ) { return rowCount; }
    
    public int  getColumnCount ( ) { return columnCount; }
    
    ////////////////////////////////////////////////////////////////////////
    // operand methods
    ////////////////////////////////////////////////////////////////////////
    
    public boolean  isIdentity ( )
    ////////////////////////////////////////////////////////////////////////
    {
      if ( rowCount != columnCount )
      {
        return false;
      }
      
      for ( int  row = 0; row < rowCount; row++ )
      {
        for ( int  column = 0; column < columnCount; column++ )
        {
          final double  value = values [ row ] [ column ];
          
          if ( row == column )
          {
            if ( value != 1 )
            {
              return false;
            }
          }
          else if ( value != 0 )
          {
            return false;
          }
        }
      }
      
      return true;
    }
    
    public boolean  isSquare ( )
    ////////////////////////////////////////////////////////////////////////
    {
      return rowCount == columnCount;
    }
    
    public boolean  matches ( final Matrix  matrix )
    ////////////////////////////////////////////////////////////////////////
    {
      return MatrixLib.matches ( this, matrix );
    }
    
    public boolean  matches (
      final Matrix  matrix,
      final double  tolerance )
    ////////////////////////////////////////////////////////////////////////
    {
      return MatrixLib.matches ( this, matrix, tolerance );
    }
    
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    
    public MatrixMut  copy ( )
    ////////////////////////////////////////////////////////////////////////
    {
      return new MatrixImp ( this );
    }
    
    public MatrixMut  multiply ( final Matrix  matrix )
    ////////////////////////////////////////////////////////////////////////
    {
      return MatrixLib.multiply ( this, matrix );
    }
    
    public MatrixMut  multiply ( final double  scalar )
    ////////////////////////////////////////////////////////////////////////
    {
      return MatrixLib.multiply ( this, scalar );
    }
    
    ////////////////////////////////////////////////////////////////////////
    // mutator methods
    ////////////////////////////////////////////////////////////////////////
    
    public void  copyToSelf ( final Matrix  copy )
    ////////////////////////////////////////////////////////////////////////
    {
      MatrixLib.copyToSelf ( this, copy );
    }
    
    public void  multiplyToSelf ( final double  scalar )
    ////////////////////////////////////////////////////////////////////////
    {
      for ( int  row = 0; row < rowCount; row++ )
      {
        for ( int  column = 0; column < columnCount; column++ )
        {
          values [ row ] [ column ] = values [ row ] [ column ] * scalar;
        }
      }
    }
    
    public void  multiplyToSelf ( final Matrix  matrix )
    ////////////////////////////////////////////////////////////////////////
    {
      MatrixLib.multiplyToSelf ( this, matrix );
    }
    
    public void  set (
      final int     row,
      final int     column,
      final double  value )
    ////////////////////////////////////////////////////////////////////////
    {
      values [ row ] [ column ] = value;
    }
    
    public void  setIdentity ( )
    ////////////////////////////////////////////////////////////////////////
    {
      if ( rowCount != columnCount )
      {
        throw new IllegalArgumentException ( "rowCount != columnCount" );
      }
      
      for ( int  row = 0; row < rowCount; row++ )
      {
        for ( int  column = 0; column < columnCount; column++ )
        {
          values [ row ] [ column ] = row == column ? 1 : 0;
        }
      }
    }
    
    public MatrixMut  transpose ( )
    ////////////////////////////////////////////////////////////////////////
    {
      return MatrixLib.transpose ( this );
    }
    
    public void  transposeSelf ( )
    ////////////////////////////////////////////////////////////////////////
    {
      MatrixLib.transposeSelf ( this );
    }
    
    ////////////////////////////////////////////////////////////////////////
    // overridden Object methods
    ////////////////////////////////////////////////////////////////////////
    
    @Override
    public String  toString ( )
    ////////////////////////////////////////////////////////////////////////
    {
      return MatrixLib.toString ( this );
    }
    
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }