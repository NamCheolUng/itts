package egovframework.com.cop.ncm.service;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * 명함정보 관리를 위한 모델 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.3.28  이삼섭          최초 생성
 *
 * </pre>
 */
@SuppressWarnings("serial")
public class NameCard implements Serializable {

    /** 주소 */
    private String adres = "";
    
    /** 지역번호 */
    private String areaNo = "";
    
    /** 직급명 */
    private String clsfNm = "";
    
    /** 회사명 */
    private String cmpnyNm = "";
    
    /** 부서명 */
    private String deptNm = "";
    
    /** 이메일주소 */
    private String emailAdres = "";
    
    /** 외부사용자여부 */
    private String extrlUserAt = "";
    
    /** 최초등록자 아이디 */
    private String frstRegisterId = "";
    
    /** 최초등록시점 */
    private String frstRegisterPnttm = "";
    
    /** 식별번호 */
    private String idntfcNo = "";
    
    /** 최종수정자 아이디 */
    private String lastUpdusrId = "";
    
    /** 최종수정시점 */
    private String lastUpdusrPnttm = "";
    
    /** 휴대폰번호 */
    private String mbtlnum = "";
    
    /** 국가번호 */
    private String nationNo = "";
    
    /** 명함아이디 */
    private String ncrdId = "";
    
    /** 명함대상자 아이디 */
    private String ncrdTrgterId = "";
    
    /** 이름 */
    private String ncrdNm = "";
    
    /** 직위명 */
    private String ofcpsNm = "";
    
    /** 공개여부 */
    private String othbcAt = "";
    
    /** 비고 */
    private String remark = "";
    
    /** 전화번호 */
    private String telno = "";

    /** 상세주소 */
    private String detailAdres = "";
    
    /** 우편번호 */
    private String zipCode = "";
    //fax번호
    private String fxnum = "";
    //비고
    private String rm = "";
    //첨부파일
    private String atchFileId = "";
    //성별
    private String sexdstnCode = "";
    //기업주소록ID
    private String adbkId = "";
    //업체구분이름
    private String idntfcNm = "";
    /**
     * adres attribute를 리턴한다.
     * 
     * @return the adres
     */
    public String getAdres() {
	return adres;
    }

    /**
     * adres attribute 값을 설정한다.
     * 
     * @param adres
     *            the adres to set
     */
    public void setAdres(String adres) {
	this.adres = adres;
    }

    /**
     * areaNo attribute를 리턴한다.
     * 
     * @return the areaNo
     */
    public String getAreaNo() {
	return areaNo;
    }

    /**
     * areaNo attribute 값을 설정한다.
     * 
     * @param areaNo
     *            the areaNo to set
     */
    public void setAreaNo(String areaNo) {
	this.areaNo = areaNo;
    }

    /**
     * clsfNm attribute를 리턴한다.
     * 
     * @return the clsfNm
     */
    public String getClsfNm() {
	return clsfNm;
    }

    /**
     * clsfNm attribute 값을 설정한다.
     * 
     * @param clsfNm
     *            the clsfNm to set
     */
    public void setClsfNm(String clsfNm) {
	this.clsfNm = clsfNm;
    }

    /**
     * cmpnyNm attribute를 리턴한다.
     * 
     * @return the cmpnyNm
     */
    public String getCmpnyNm() {
	return cmpnyNm;
    }

    /**
     * cmpnyNm attribute 값을 설정한다.
     * 
     * @param cmpnyNm
     *            the cmpnyNm to set
     */
    public void setCmpnyNm(String cmpnyNm) {
	this.cmpnyNm = cmpnyNm;
    }

    /**
     * deptNm attribute를 리턴한다.
     * 
     * @return the deptNm
     */
    public String getDeptNm() {
	return deptNm;
    }

    /**
     * deptNm attribute 값을 설정한다.
     * 
     * @param deptNm
     *            the deptNm to set
     */
    public void setDeptNm(String deptNm) {
	this.deptNm = deptNm;
    }

    /**
     * emailAdres attribute를 리턴한다.
     * 
     * @return the emailAdres
     */
    public String getEmailAdres() {
	return emailAdres;
    }

    /**
     * emailAdres attribute 값을 설정한다.
     * 
     * @param emailAdres
     *            the emailAdres to set
     */
    public void setEmailAdres(String emailAdres) {
	this.emailAdres = emailAdres;
    }

    /**
     * extrlUserAt attribute를 리턴한다.
     * 
     * @return the extrlUserAt
     */
    public String getExtrlUserAt() {
	return extrlUserAt;
    }

    /**
     * extrlUserAt attribute 값을 설정한다.
     * 
     * @param extrlUserAt
     *            the extrlUserAt to set
     */
    public void setExtrlUserAt(String extrlUserAt) {
	this.extrlUserAt = extrlUserAt;
    }

    /**
     * frstRegisterId attribute를 리턴한다.
     * 
     * @return the frstRegisterId
     */
    public String getFrstRegisterId() {
	return frstRegisterId;
    }

    /**
     * frstRegisterId attribute 값을 설정한다.
     * 
     * @param frstRegisterId
     *            the frstRegisterId to set
     */
    public void setFrstRegisterId(String frstRegisterId) {
	this.frstRegisterId = frstRegisterId;
    }

    /**
     * frstRegisterPnttm attribute를 리턴한다.
     * 
     * @return the frstRegisterPnttm
     */
    public String getFrstRegisterPnttm() {
	return frstRegisterPnttm;
    }

    /**
     * frstRegisterPnttm attribute 값을 설정한다.
     * 
     * @param frstRegisterPnttm
     *            the frstRegisterPnttm to set
     */
    public void setFrstRegisterPnttm(String frstRegisterPnttm) {
	this.frstRegisterPnttm = frstRegisterPnttm;
    }

    /**
     * idntfcNo attribute를 리턴한다.
     * 
     * @return the idntfcNo
     */
    public String getIdntfcNo() {
	return idntfcNo;
    }

    /**
     * idntfcNo attribute 값을 설정한다.
     * 
     * @param idntfcNo
     *            the idntfcNo to set
     */
    public void setIdntfcNo(String idntfcNo) {
	this.idntfcNo = idntfcNo;
    }

    /**
     * lastUpdusrId attribute를 리턴한다.
     * 
     * @return the lastUpdusrId
     */
    public String getLastUpdusrId() {
	return lastUpdusrId;
    }

    /**
     * lastUpdusrId attribute 값을 설정한다.
     * 
     * @param lastUpdusrId
     *            the lastUpdusrId to set
     */
    public void setLastUpdusrId(String lastUpdusrId) {
	this.lastUpdusrId = lastUpdusrId;
    }

    /**
     * lastUpdusrPnttm attribute를 리턴한다.
     * 
     * @return the lastUpdusrPnttm
     */
    public String getLastUpdusrPnttm() {
	return lastUpdusrPnttm;
    }

    /**
     * lastUpdusrPnttm attribute 값을 설정한다.
     * 
     * @param lastUpdusrPnttm
     *            the lastUpdusrPnttm to set
     */
    public void setLastUpdusrPnttm(String lastUpdusrPnttm) {
	this.lastUpdusrPnttm = lastUpdusrPnttm;
    }

    public String getMbtlnum() {
		return mbtlnum;
	}

	public void setMbtlnum(String mbtlnum) {
		this.mbtlnum = mbtlnum;
	}

	/**
     * nationNo attribute를 리턴한다.
     * 
     * @return the nationNo
     */
    public String getNationNo() {
	return nationNo;
    }

    /**
     * nationNo attribute 값을 설정한다.
     * 
     * @param nationNo
     *            the nationNo to set
     */
    public void setNationNo(String nationNo) {
	this.nationNo = nationNo;
    }

    /**
     * ncrdId attribute를 리턴한다.
     * 
     * @return the ncrdId
     */
    public String getNcrdId() {
	return ncrdId;
    }

    /**
     * ncrdId attribute 값을 설정한다.
     * 
     * @param ncrdId
     *            the ncrdId to set
     */
    public void setNcrdId(String ncrdId) {
	this.ncrdId = ncrdId;
    }

    /**
     * ncrdTrgterId attribute를 리턴한다.
     * 
     * @return the ncrdTrgterId
     */
    public String getNcrdTrgterId() {
	return ncrdTrgterId;
    }

    /**
     * ncrdTrgterId attribute 값을 설정한다.
     * 
     * @param ncrdTrgterId
     *            the ncrdTrgterId to set
     */
    public void setNcrdTrgterId(String ncrdTrgterId) {
	this.ncrdTrgterId = ncrdTrgterId;
    }

    /**
     * ncrdNm attribute를 리턴한다.
     * 
     * @return the ncrdNm
     */
    public String getNcrdNm() {
	return ncrdNm;
    }

    /**
     * ncrdNm attribute 값을 설정한다.
     * 
     * @param ncrdNm
     *            the ncrdNm to set
     */
    public void setNcrdNm(String ncrdNm) {
	this.ncrdNm = ncrdNm;
    }

    /**
     * ofcpsNm attribute를 리턴한다.
     * 
     * @return the ofcpsNm
     */
    public String getOfcpsNm() {
	return ofcpsNm;
    }

    /**
     * ofcpsNm attribute 값을 설정한다.
     * 
     * @param ofcpsNm
     *            the ofcpsNm to set
     */
    public void setOfcpsNm(String ofcpsNm) {
	this.ofcpsNm = ofcpsNm;
    }

    /**
     * othbcAt attribute를 리턴한다.
     * 
     * @return the othbcAt
     */
    public String getOthbcAt() {
	return othbcAt;
    }

    /**
     * othbcAt attribute 값을 설정한다.
     * 
     * @param othbcAt
     *            the othbcAt to set
     */
    public void setOthbcAt(String othbcAt) {
	this.othbcAt = othbcAt;
    }

    /**
     * remark attribute를 리턴한다.
     * 
     * @return the remark
     */
    public String getRemark() {
	return remark;
    }

    /**
     * remark attribute 값을 설정한다.
     * 
     * @param remark
     *            the remark to set
     */
    public void setRemark(String remark) {
	this.remark = remark;
    }

    /**
     * detailAdres attribute를 리턴한다.
     * 
     * @return the detailAdres
     */
    public String getDetailAdres() {
	return detailAdres;
    }

    /**
     * detailAdres attribute 값을 설정한다.
     * 
     * @param detailAdres
     *            the detailAdres to set
     */
    public void setDetailAdres(String detailAdres) {
	this.detailAdres = detailAdres;
    }

    /**
     * zipCode attribute를 리턴한다.
     * 
     * @return the zipCode
     */
    public String getZipCode() {
	return zipCode;
    }

    /**
     * zipCode attribute 값을 설정한다.
     * 
     * @param zipCode
     *            the zipCode to set
     */
    public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
    }

    
    
    public String getFxnum() {
		return fxnum;
	}

	public void setFxnum(String fxnum) {
		this.fxnum = fxnum;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getAtchFileId() {
		return atchFileId;
	}

	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}

	public String getSexdstnCode() {
		return sexdstnCode;
	}

	public void setSexdstnCode(String sexdstnCode) {
		this.sexdstnCode = sexdstnCode;
	}

	public String getAdbkId() {
		return adbkId;
	}

	public void setAdbkId(String adbkId) {
		this.adbkId = adbkId;
	}

	
	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getIdntfcNm() {
		return idntfcNm;
	}

	public void setIdntfcNm(String idntfcNm) {
		this.idntfcNm = idntfcNm;
	}

	/**
     * toString 메소드를 대치한다.
     */
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }
}
