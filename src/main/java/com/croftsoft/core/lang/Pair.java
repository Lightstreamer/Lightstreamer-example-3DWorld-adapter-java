     package com.croftsoft.core.lang;

     import java.io.Serializable;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * An immutable name-value pair where name is never null.
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-09-13
     * @since
     *   2001-06-07
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Pair
       implements Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     public final String  name;

     public final String  value;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getName  ( ) { return name;  }

     public String  getValue ( ) { return value; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Converts an array of names to an array of Pairs with null values.
     *********************************************************************/
     public static Pair [ ]  toPairs ( String [ ]  names )
     //////////////////////////////////////////////////////////////////////
     {
       Pair [ ]  pairs = new Pair [ names.length ];

       for ( int  i = 0; i < names.length; i++ )
       {
         pairs [ i ] = new Pair ( names [ i ], null );
       }

       return pairs;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Constructor method.
     *
     * @param  name
     *
     *   Must not be null.
     *
     * @param  value
     *
     *   May be null.
     *
     * @throws NullArgumentException
     *
     *   If name is null.
     *********************************************************************/
     public  Pair (
       String  name,
       String  value )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.name = name );

       this.value = value;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
