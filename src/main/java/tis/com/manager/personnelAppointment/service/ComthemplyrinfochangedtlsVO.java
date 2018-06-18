package tis.com.manager.personnelAppointment.service;

import java.io.Serializable;

/**
 * @Class Name : ComthemplyrinfochangedtlsVO.java
 * @Description : Comthemplyrinfochangedtls VO class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180123
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public class ComthemplyrinfochangedtlsVO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /** EMPL_NO */
    private java.lang.String emplNo;
    
    /** CHANGE_DE */
    private java.math.BigDecimal changeDe;
    
    /** AF_ORGNZT_NM */
    private java.lang.String afOrgnztNm;
    
    /** AF_ORGNZT_ID */
    private java.lang.String afOrgnztId;
    
    /** AF_OFCPS_NM */
    private java.lang.String afOfcpsNm;
    
    /** RM */
    private java.lang.String rm;
    
    /** AF_OFCPS_ID */
    private java.lang.String afOfcpsId;
    
    /** EMPLYR_ENDDE */
    private java.math.BigDecimal emplyrEndde;
    
    public java.lang.String getEmplNo() {
        return this.emplNo;
    }
    
    public void setEmplNo(java.lang.String emplNo) {
        this.emplNo = emplNo;
    }
    
    public java.math.BigDecimal getChangeDe() {
        return this.changeDe;
    }
    
    public void setChangeDe(java.math.BigDecimal changeDe) {
        this.changeDe = changeDe;
    }
    
    public java.lang.String getAfOrgnztNm() {
        return this.afOrgnztNm;
    }
    
    public void setAfOrgnztNm(java.lang.String afOrgnztNm) {
        this.afOrgnztNm = afOrgnztNm;
    }
    
    public java.lang.String getAfOrgnztId() {
        return this.afOrgnztId;
    }
    
    public void setAfOrgnztId(java.lang.String afOrgnztId) {
        this.afOrgnztId = afOrgnztId;
    }
    
    public java.lang.String getAfOfcpsNm() {
        return this.afOfcpsNm;
    }
    
    public void setAfOfcpsNm(java.lang.String afOfcpsNm) {
        this.afOfcpsNm = afOfcpsNm;
    }
    
    public java.lang.String getRm() {
        return this.rm;
    }
    
    public void setRm(java.lang.String rm) {
        this.rm = rm;
    }
    
    public java.lang.String getAfOfcpsId() {
        return this.afOfcpsId;
    }
    
    public void setAfOfcpsId(java.lang.String afOfcpsId) {
        this.afOfcpsId = afOfcpsId;
    }
    
    public java.math.BigDecimal getEmplyrEndde() {
        return this.emplyrEndde;
    }
    
    public void setEmplyrEndde(java.math.BigDecimal emplyrEndde) {
        this.emplyrEndde = emplyrEndde;
    }
    
}
