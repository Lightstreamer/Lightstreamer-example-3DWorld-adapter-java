    package com.croftsoft.core.math;

    /***********************************************************************
    * A collection of constants to supplement java.lang.Math.
    *
    * @version
    *   $Id: MathConstants.java,v 1.4 2008/08/24 00:28:36 croft Exp $
    * @since
    *   2002-01-27
    * @author
    *   <a href="http://CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public interface  MathConstants
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {

    public static final long
      MILLISECONDS_PER_DAY        = 1000 * 60 * 60 * 24,
      MILLISECONDS_PER_SECOND     = 1000,
      NANOSECONDS_PER_MICROSECOND = 1000,
      NANOSECONDS_PER_MILLISECOND = 1000000,
      NANOSECONDS_PER_SECOND      = 1000000000;
     
    public static final double
      DEGREES_PER_RADIAN     = 360.0 / ( 2.0 * Math.PI ),
      GOLDEN_RATIO           = 1.618033988749894848204586,
      SECONDS_PER_NANOSECOND = 0.000000001,
      TWO_PI                 = 2 * Math.PI;

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }