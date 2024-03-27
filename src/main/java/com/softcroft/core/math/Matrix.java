     package com.croftsoft.core.math;

     /*********************************************************************
     *
     * A mathematical matrix class.
     *
     * @version
     *   1998-12-27
     * @author
     *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  Matrix
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public int             rows;
     public int             cols;
     public double [ ] [ ]  data;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Test/demo method.
     *********************************************************************/
     public static void  main ( )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix  left  = new Matrix ( 2, 3 );
       Matrix  right = new Matrix ( 3, 4 );

       for ( int index_row = 0; index_row < left.rows; index_row++ )
       for ( int index_col = 0; index_col < left.cols; index_col++ )
       {
         left.data [ index_row ] [ index_col ] = index_row + index_col;
       }

       for ( int index_row = 0; index_row < right.rows; index_row++ )
       for ( int index_col = 0; index_col < right.cols; index_col++ )
       {
         right.data [ index_row ] [ index_col ] = index_row + index_col;
       }

       left.display ( );
       System.out.println ( "" );
       right.display ( );
       System.out.println ( "" );
       Matrix  product = multiply ( left, right );
       product.display ( );
       System.out.println ( "" );
       Matrix  transposed = product.transpose ( );
       transposed.display ( );
       System.out.println ( "" );
       Matrix  sigmoided = transposed.sigmoid ( );
       sigmoided.display ( );
     }

     //////////////////////////////////////////////////////////////////////
     // Static methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Returns a square matrix with the diagonal values set to 1.0 and
     * all others set to 0.0.
     *
     * @param rows_cols
     *   The number of rows and columns on a side.
     *********************************************************************/
     public static Matrix  identity ( int  rows_cols )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix  m = new Matrix ( rows_cols, rows_cols );

       for ( int  i = 0; i < rows_cols; i++ )
       {
         m.data [ i ] [ i ] = 1.0;
       }

       return m;
     }

     public static Matrix  multiply ( Matrix  left, Matrix  right )
     //////////////////////////////////////////////////////////////////////
     {
       if ( left.cols != right.rows )
       {
         throw new ArrayIndexOutOfBoundsException (
           "Matrix.multiply:  left.cols != right.rows" );
       }

       Matrix  product = new Matrix ( left.rows, right.cols );

       for ( int index_row = 0; index_row < product.rows; index_row++ )
       for ( int index_col = 0; index_col < product.cols; index_col++ )
       {
         product.data [ index_row ] [ index_col ] = 0.0;
         for ( int index = 0; index < left.cols; index++ )
         {
           product.data [ index_row ] [ index_col ]
             += left.data [ index_row ] [ index ]
             * right.data [ index ] [ index_col ];
         }
       }

       return product;
     }

     public static Matrix  multiplyPairwise ( Matrix  a, Matrix  b )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ( a.rows != b.rows ) || ( a.cols != b.cols ) )
       {
         throw new ArrayIndexOutOfBoundsException (
           "Matrix.multiply_pairwise:  rows or columns not equal" );
       }

       Matrix  product = new Matrix ( a.rows, a.cols );

       for ( int index_row = 0; index_row < a.rows; index_row++ )
       for ( int index_col = 0; index_col < a.cols; index_col++ )
       {
         product.data [ index_row ] [ index_col ]
           = a.data [ index_row ] [ index_col ]
           * b.data [ index_row ] [ index_col ];
       }

       return product;
     }

     //////////////////////////////////////////////////////////////////////
     // Constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Constructs a Matrix with all of the element values set to zero.
     *********************************************************************/
     public  Matrix ( int  rows, int  cols )
     //////////////////////////////////////////////////////////////////////
     {
       this.rows = rows;
       this.cols = cols;
       this.data = new double [ rows ] [ cols ];
     }

     /*********************************************************************
     * Constructs a Matrix with all of the element values set to a
     * specified constant.
     *
     * @param value
     *   All of the element values will be set to this constant.
     *********************************************************************/
     public  Matrix ( int  rows, int  cols, double  value )
     //////////////////////////////////////////////////////////////////////
     {
       this ( rows, cols );

       for ( int  row = 0; row < rows; row++ )
       for ( int  col = 0; col < cols; col++ )
       {
         data [ row ] [ col ] = value;
       }
     }

     public Matrix ( Matrix  old )
     //////////////////////////////////////////////////////////////////////
     {
       this ( old.rows, old.cols );
       for ( int index_row = 0; index_row < this.rows; index_row++ )
       for ( int index_col = 0; index_col < this.cols; index_col++ )
       {
         this.data [ index_row ] [ index_col ]
           = old.data [ index_row ] [ index_col ];
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Matrix  add ( double  addend )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix  new_Matrix = new Matrix ( this );

       for ( int index_row = 0; index_row < this.rows; index_row++ )
       for ( int index_col = 0; index_col < this.cols; index_col++ )
       {
         new_Matrix.data [ index_row ] [ index_col ] += addend;
       }
       return new_Matrix;
     }

     public Matrix  add ( Matrix  addend )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ( this.rows!= addend.rows    )
         || ( this.cols != addend.cols ) )
       {
         throw new ArrayIndexOutOfBoundsException (
           "Matrix.add ( Matrix ):  columns and rows not equal" );
       }

       Matrix  new_Matrix = new Matrix ( this );

       for ( int index_row = 0; index_row < this.rows; index_row++ )
       for ( int index_col = 0; index_col < this.cols; index_col++ )
       {
         new_Matrix.data [ index_row ] [ index_col ]
           += addend.data [ index_row ] [ index_col ];
       }
       return new_Matrix;
     }

     public Matrix  clip ( double  min, double  max )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix  new_Matrix = new Matrix ( this );

       for ( int index_row = 0; index_row < this.rows; index_row++ )
       for ( int index_col = 0; index_col < this.cols; index_col++ )
       {
         if ( this.data [ index_row ] [ index_col ] < min )
           new_Matrix.data [ index_row ] [ index_col ] = min;
         else if ( this.data [ index_row ] [ index_col ] > max )
           new_Matrix.data [ index_row ] [ index_col ] = max;
       }
       return new_Matrix;
     }

     public void  display ( )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int index_row = 0; index_row < this.rows; index_row++ )
       {
         String  line_String = "";
         for ( int index_col = 0; index_col < this.cols; index_col++ )
         {
           line_String += this.data [ index_row ] [ index_col ] + " ";
         }
         System.out.println ( line_String );
       }
     }

     public Matrix  multiply ( double  factor )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix  new_Matrix = new Matrix ( this );

       for ( int index_row = 0; index_row < this.rows; index_row++ )
       for ( int index_col = 0; index_col < this.cols; index_col++ )
       {
         new_Matrix.data [ index_row ] [ index_col ] *= factor;
       }

       return new_Matrix;
     }

     public Matrix  multiply ( Matrix  right )
     //////////////////////////////////////////////////////////////////////
     {
       return multiply ( this, right );
     }

     public Matrix  randomizeUniform ( double  min, double  max )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix  new_Matrix = new Matrix ( this.rows, this.cols );

       for ( int index_row = 0; index_row < this.rows; index_row++ )
       for ( int index_col = 0; index_col < this.cols; index_col++ )
       {
         new_Matrix.data [ index_row ] [ index_col ]
           = RandomLib.uniform ( min, max );
       }

       return new_Matrix;
     }

     public Matrix  sigmoid ( )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix  new_Matrix = new Matrix ( this.rows, this.cols );

       for ( int index_row = 0; index_row < this.rows; index_row++ )
       for ( int index_col = 0; index_col < this.cols; index_col++ )
       {
         new_Matrix.data [ index_row ] [ index_col ]
           = MathLib.sigmoid ( this.data [ index_row ] [ index_col ] );
       }

       return new_Matrix;
     }

     public Matrix  sigmoidDerivative ( )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix  new_Matrix = new Matrix ( this.rows, this.cols );

       for ( int index_row = 0; index_row < this.rows; index_row++ )
       for ( int index_col = 0; index_col < this.cols; index_col++ )
       {
         new_Matrix.data [ index_row ] [ index_col ]
           = MathLib.sigmoidDerivative
           ( this.data [ index_row ] [ index_col ] );
       }

       return new_Matrix;
     }

     public Matrix  submatrix (
       int  row_start, int  row_end,
       int  col_start, int  col_end )
     //////////////////////////////////////////////////////////////////////
     //  Note that the first row is row 0.
     //////////////////////////////////////////////////////////////////////
     {
       Matrix  sub
         = new Matrix ( row_end - row_start + 1, col_end - col_start + 1 );
       for ( int index_row = 0; index_row < sub.rows; index_row++ )
       for ( int index_col = 0; index_col < sub.cols; index_col++ )
       {
         sub.data [ index_row ] [ index_col ]
           = this.data [ index_row + row_start ] [ index_col + col_start ];
       }

       return sub;
     }

     public Matrix  subtract ( Matrix  subtractor )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ( this.rows    != subtractor.rows    )
         || ( this.cols != subtractor.cols ) )
       {
         throw new ArrayIndexOutOfBoundsException (
           "Matrix.subtract ( Matrix ):  columns and rows not equal" );
       }

       Matrix  new_Matrix = new Matrix ( this );

       for ( int index_row = 0; index_row < this.rows; index_row++ )
       for ( int index_col = 0; index_col < this.cols; index_col++ )
       {
         new_Matrix.data [ index_row ] [ index_col ]
           -= subtractor.data [ index_row ] [ index_col ];
       }

       return new_Matrix;
     }

     public double  sum ( )
     //////////////////////////////////////////////////////////////////////
     {
       double  temp = 0.0;

       for ( int index_row = 0; index_row < this.rows; index_row++ )
       for ( int index_col = 0; index_col < this.cols; index_col++ )
       {
         temp += this.data [ index_row ] [ index_col ];
       }

       return temp;
     }

     public Matrix  transpose ( )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix  transposed = new Matrix ( this.cols, this.rows );

       for ( int index_row = 0;
                 index_row < transposed.rows;
                 index_row++ )
       for ( int index_col = 0;
                 index_col < transposed.cols;
                 index_col++ )
       {
         transposed.data [ index_row ] [ index_col ]
           = this.data [ index_col ] [ index_row ];
       }

       return transposed;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
