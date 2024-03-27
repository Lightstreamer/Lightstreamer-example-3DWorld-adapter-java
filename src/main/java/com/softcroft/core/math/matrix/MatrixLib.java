    package com.croftsoft.core.math.matrix;
    
    /***********************************************************************
    * A library of static methods to manipulate Matrix objects.
    * 
    * @version
    *   $Id: MatrixLib.java,v 1.4 2008/05/09 18:35:55 croft Exp $
    * @since
    *   2008-04-25
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public final class  MatrixLib
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    public static void  copyToSelf (
      final MatrixMut  matrixMut,
      final Matrix     matrix )
    ////////////////////////////////////////////////////////////////////////
    {
      final int  rowCount = matrixMut.getRowCount ( );
      
      final int  columnCount = matrixMut.getColumnCount ( );
      
      if ( rowCount != matrix.getRowCount ( ) )
      {
        throw new IllegalArgumentException ( "rowCount not equal" );
      }
      
      if ( columnCount != matrix.getColumnCount ( ) )
      {
        throw new IllegalArgumentException ( "columnCount not equal" );
      }
      
      for ( int  row = 0; row < rowCount; row++ )
      {
        for ( int  column = 0; column < columnCount; column++ )
        {
          matrixMut.set ( row, column, matrix.get ( row, column ) );
        }
      }
    }
    
    public static boolean  matches (
      final Matrix  matrix0,
      final Matrix  matrix1 )
    ////////////////////////////////////////////////////////////////////////
    {
      final int  rowCount = matrix0.getRowCount ( );
      
      if ( rowCount != matrix1.getRowCount ( ) )
      {
        return false;
      }
      
      final int  columnCount = matrix0.getColumnCount ( );
      
      if ( columnCount != matrix1.getColumnCount ( ) )
      {
        return false;
      }
      
      for ( int  row = 0; row < rowCount; row++ )
      {
        for ( int  column = 0; column < columnCount; column++ )
        {
          if ( matrix0.get ( row, column ) != matrix1.get ( row, column ) )
          {
            return false;
          }
        }
      }
      
      return true;
    }
      
    public static boolean  matches (
      final Matrix  matrix0,
      final Matrix  matrix1,
      final double  tolerance )
    ////////////////////////////////////////////////////////////////////////
    {
      if ( tolerance < 0 )
      {
        throw new IllegalArgumentException ( "tolerance < 0" );
      }
      
      final int  rowCount = matrix0.getRowCount ( );
      
      if ( rowCount != matrix1.getRowCount ( ) )
      {
        return false;
      }
      
      final int  columnCount = matrix0.getColumnCount ( );
      
      if ( columnCount != matrix1.getColumnCount ( ) )
      {
        return false;
      }
      
      for ( int  row = 0; row < rowCount; row++ )
      {
        for ( int  column = 0; column < columnCount; column++ )
        {
          if ( Math.abs ( matrix0.get ( row, column )
                        - matrix1.get ( row, column ) ) > tolerance )
          {
            return false;
          }
        }
      }
      
      return true;
    }
      
    public static MatrixMut  multiply (
      final Matrix  matrix,
      final double  scalar )
    ////////////////////////////////////////////////////////////////////////
    {
      final MatrixMut  scaledCopy = matrix.copy ( );
      
      final int  rowCount = matrix.getRowCount ( );
      
      final int  columnCount = matrix.getColumnCount ( );
      
      for ( int  row = 0; row < rowCount; row++ )
      {
        for ( int  column = 0; column < columnCount; column++ )
        {
          scaledCopy.set (
            row,
            column,
            matrix.get ( row, column ) * scalar );
        }
      }
      
      return scaledCopy;
    }
    
    public static MatrixMut  multiply (
      final Matrix  matrixAcc0,
      final Matrix  matrixAcc1 )
    ////////////////////////////////////////////////////////////////////////
    {
      final int  rowCount0    = matrixAcc0.getRowCount ( );
      
      final int  columnCount0 = matrixAcc0.getColumnCount ( );
      
      final int  rowCount1    = matrixAcc1.getRowCount ( );
      
      final int  columnCount1 = matrixAcc1.getColumnCount ( );
      
      if ( columnCount0 != rowCount1 )
      {
        throw new IllegalArgumentException ( "columnCount0 != rowCount1" );
      }
      
      final MatrixMut  matrixMut
        = new MatrixImp ( rowCount0, columnCount1 );
      
      for ( int  row = 0; row < rowCount0; row++ )
      {
        for ( int  column = 0; column < columnCount1; column++ )
        {
          double  value = 0;
          
          for ( int  i = 0; i < columnCount0; i++ )
          {
            value += matrixAcc0.get ( row, i )
                   * matrixAcc1.get ( i, column );
          }
          
          matrixMut.set ( row, column, value );
        }
      }
      
      return matrixMut;
    }
    
    public static void  multiplyToSelf (
      final MatrixMut  matrixMut,
      final Matrix     matrix )
    ////////////////////////////////////////////////////////////////////////
    {
      if ( !matrixMut.isSquare ( ) )
      {
        throw new IllegalArgumentException ( "matrixMut is not square" );
      }
      
      if ( !matrix.isSquare ( ) )
      {
        throw new IllegalArgumentException ( "matrix is not square" );
      }
      
      matrixMut.copyToSelf ( multiply ( matrixMut, matrix ) );
    }
    
    public static String  toString ( final Matrix  matrix )
    ////////////////////////////////////////////////////////////////////////
    {
      final StringBuilder  stringBuilder = new StringBuilder ( );
      
      final int  rowCount = matrix.getRowCount ( );
      
      final int  columnCount = matrix.getColumnCount ( );
      
      for ( int  row = 0; row < rowCount; row++ )
      {
        for ( int  column = 0; column < columnCount;  column++ )
        {
          stringBuilder.append (
            String.format (
              "%1$1.3f",
              new Double ( matrix.get ( row, column ) ) ) );
          
          if ( column != columnCount - 1 )
          {
            stringBuilder.append ( ", " );
          }
          else if ( row != rowCount - 1 )
          {
            stringBuilder.append ( "; " );
          }
        }
      }
      
      return stringBuilder.toString ( );
    }
    
    public static MatrixMut  transpose ( final Matrix  matrix )
    ////////////////////////////////////////////////////////////////////////
    {
      final int  rowCount = matrix.getRowCount ( );
      
      final int  columnCount = matrix.getColumnCount ( );
      
      final MatrixMut  matrixMut = new MatrixImp ( columnCount, rowCount );
      
      for ( int  row = 0; row < rowCount; row++ )
      {
        for ( int  column = 0; column < columnCount; column++ )
        {
          matrixMut.set ( column, row, matrix.get ( row, column ) );
        }
      }
      
      return matrixMut;
    }
    
    public static void  transposeSelf ( final MatrixMut  matrixMut )
    ////////////////////////////////////////////////////////////////////////
    {
      if ( !matrixMut.isSquare ( ) )
      {
        throw new IllegalArgumentException ( "matrix is not square" );
      }
      
      final int  rowCount = matrixMut.getRowCount ( );
      
      for ( int  row = 0; row < rowCount; row++ )
      {
        for ( int  column = 0; column < row; column++ )
        {
          final double  valueRC = matrixMut.get ( row, column );
            
          final double  valueCR = matrixMut.get ( column, row );
            
          matrixMut.set ( row, column, valueCR );
            
          matrixMut.set ( column, row, valueRC );
        }
      }
    }
    
    ////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////
    
    private  MatrixLib ( )
    ////////////////////////////////////////////////////////////////////////
    {
      // empty
    }
    
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }