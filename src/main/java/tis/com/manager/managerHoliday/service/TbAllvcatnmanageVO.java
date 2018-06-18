package tis.com.manager.managerHoliday.service;

import java.io.Serializable;
import java.util.List;

/**
 * @Class Name : TbAllvcatnmanageVO.java
 * @Description : TbAllvcatnmanage VO class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180214
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public class TbAllvcatnmanageVO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /** APPLCNT_ID */
    private java.lang.String applcntId;
    
    /** VCATN_SE */
    private java.lang.String vcatnSe;
    
    /** BGNDE */
    private java.lang.String bgnde;
    
    /** ENDDE */
    private java.lang.String endde;
    
    /** OCCRRNC_YEAR */
    private java.lang.String occrrncYear;
    
    /** NOON_SE */
    private java.lang.String noonSe;
    
    /** VCATN_CNT */
    private java.math.BigDecimal vcatnCnt;
    
    /** VCATN_RESN */
    private java.lang.String vcatnResn;
    
    /** FRST_REGIST_PNTTM */
    private java.util.Date frstRegistPnttm;
    
    private String chk;
    
    private List<TbAllvcatnmanageVO> allvcatnmanageList;
    
    public java.lang.String getApplcntId() {
        return this.applcntId;
    }
    
    public void setApplcntId(java.lang.String applcntId) {
        this.applcntId = applcntId;
    }
    
    public java.lang.String getVcatnSe() {
        return this.vcatnSe;
    }
    
    public void setVcatnSe(java.lang.String vcatnSe) {
        this.vcatnSe = vcatnSe;
    }
    
    public java.lang.String getBgnde() {
        return this.bgnde;
    }
    
    public void setBgnde(java.lang.String bgnde) {
        this.bgnde = bgnde;
    }
    
    public java.lang.String getEndde() {
        return this.endde;
    }
    
    public void setEndde(java.lang.String endde) {
        this.endde = endde;
    }
    
    public java.lang.String getOccrrncYear() {
        return this.occrrncYear;
    }
    
    public void setOccrrncYear(java.lang.String occrrncYear) {
        this.occrrncYear = occrrncYear;
    }
    
    public java.lang.String getNoonSe() {
        return this.noonSe;
    }
    
    public void setNoonSe(java.lang.String noonSe) {
        this.noonSe = noonSe;
    }
    
    public java.math.BigDecimal getVcatnCnt() {
        return this.vcatnCnt;
    }
    
    public void setVcatnCnt(java.math.BigDecimal vcatnCnt) {
        this.vcatnCnt = vcatnCnt;
    }
    
    public java.lang.String getVcatnResn() {
        return this.vcatnResn;
    }
    
    public void setVcatnResn(java.lang.String vcatnResn) {
        this.vcatnResn = vcatnResn;
    }
    
    public java.util.Date getFrstRegistPnttm() {
        return this.frstRegistPnttm;
    }
    
    public void setFrstRegistPnttm(java.util.Date frstRegistPnttm) {
        this.frstRegistPnttm = frstRegistPnttm;
    }

	public String getChk() {
		return chk;
	}

	public void setChk(String chk) {
		this.chk = chk;
	}

	public List<TbAllvcatnmanageVO> getAllvcatnmanageList() {
		return allvcatnmanageList;
	}

	public void setAllvcatnmanageList(List<TbAllvcatnmanageVO> allvcatnmanageList) {
		this.allvcatnmanageList = allvcatnmanageList;
	}
    
    
}
