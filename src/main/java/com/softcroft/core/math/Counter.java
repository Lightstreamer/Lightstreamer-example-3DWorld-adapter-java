     package com.croftsoft.core.math;

     import java.io.Serializable;
     import java.math.BigInteger;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * An incrementable integer counter with no maximum limit.
     *
     * <p />
     *    
     * @version
     *   2001-03-30
     * @since
     *   2001-03-30
     * @author
     *   <a href="http://www.CroftSoft.com/">David W. Croft</a>
     *********************************************************************/

     public final class  Counter
       implements Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     private BigInteger  count;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Counter ( BigInteger  count )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.count = count );
     }

     public  Counter ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( BigInteger.ZERO );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public BigInteger  getCount ( )
     //////////////////////////////////////////////////////////////////////
     {
       return count;
     }

     /*********************************************************************
     * Synchronized.
     *********************************************************************/
     public synchronized BigInteger  increment ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ( count = count.add ( BigInteger.ONE ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
