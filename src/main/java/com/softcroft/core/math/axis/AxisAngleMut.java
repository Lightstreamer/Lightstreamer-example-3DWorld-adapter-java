    package com.croftsoft.core.math.axis;
    
    /***********************************************************************
    * A mutator interface for an AxisAngle.
    * 
    * @version
    *   $Id: AxisAngleMut.java,v 1.1 2008/05/09 18:35:55 croft Exp $
    * @since
    *   2008-05-09
    * @author
    *   <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
    ***********************************************************************/

    public interface  AxisAngleMut
      extends AxisAngle
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    {
      
    void  copy ( AxisAngle  axisAngle );
    
    void  normalize ( );
    
    void  setDegrees ( double  degrees );
      
    void  setX ( double  x );
      
    void  setY ( double  y );
      
    void  setZ ( double  z );
      
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    }