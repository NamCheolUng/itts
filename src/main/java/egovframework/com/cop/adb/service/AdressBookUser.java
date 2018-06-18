package egovframework.com.cop.adb.service;

import java.io.Serializable;

/**
 * 주소록구성원 관리를 위한 모델 클래스
 * @author 공통컴포넌트개발팀 윤성록
 * @since 2009.09.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.9.25  윤성록          최초 생성
 *
 * </pre>
 */
@SuppressWarnings("serial")
public class AdressBookUser implements Serializable{

    /** 주소록구성원 아이디 */
    private String adbkUserId = "";  
    
    /** 주소록 아이디 */
    private String adbkId = "";
    
    /** 사용자 아이디 */
    private String emplyrId = "";
    
    /** 명함 아이디 */
    private String ncrdId = "";
    
    /** 주소록구성원 이름 */
    private String nm = "";
    
    /** 이메일 주소  */
    private String emailAdres = "";
    
    /** 집 전화번호  */
    private String homeTelno = "";
    
    /** 폰 번호  */
    private String moblphonNo = "";
    
    /** 회사 번호  */
    private String offmTelno = "";
    
    /** 팩스 번호  */
    private String fxnum = "";
    
    //회사이름
    private String cmpnyNm = "";
    //호사구분코드
    private String entrprsSeCode = "";
    //사업자등록번호
    private String bizrno = ""; 
    //회사주소
    private String adres = ""; 
    //회사전봐번호
    private String houseTelno = "";
    //은행계좌번호
    private String bankAccountNum = "";
    //은행이름
    private String bankNm = "";
    //예금주명
    private String depositorNm = ""; 
    //비고
    private String rm = "";
    //첨부파일
    private String atchFileId = "";
    //회사구분이름
    private String entrprsSeNm = "";
    //대표자명
    private String ceoNm = "";
    //계산서 이메일
    private String accountEmail = "";
    //업태
    private String bizCondition = "";
    //종목
    private String bizDivision = "";
    
    
    /**
     * adbkUserId attribute를 리턴한다.
     * 
     * @return the adbkUserId
     */
    public String getAdbkUserId() {
        return adbkUserId;
    }
    
    /**
     * adbkUserId attribute 값을 설정한다.
     * 
     * @param adbkUserId
     *            the adbkUserId to set
     */
    public void setAdbkUserId(String adbkUserId) {
        this.adbkUserId = adbkUserId;
    }
    
    /**
     * adbkId attribute를 리턴한다.
     * 
     * @return the adbkId
     */
    public String getAdbkId() {
        return adbkId;
    }
    
    /**
     * adbkId attribute 값을 설정한다.
     * 
     * @param adbkId
     *            the adbkId to set
     */
    public void setAdbkId(String adbkId) {
        this.adbkId = adbkId;
    }
    
    /**
     * emplyrId attribute를 리턴한다.
     * 
     * @return the emplyrId
     */
    public String getEmplyrId() {
        return emplyrId;
    }
    
    /**
     * emplyrId attribute 값을 설정한다.
     * 
     * @param emplyrId
     *            the emplyrId to set
     */
    public void setEmplyrId(String emplyrId) {
        this.emplyrId = emplyrId;
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
     * nm attribute를 리턴한다.
     * 
     * @return the nm
     */
    public String getNm() {
        return nm;
    }
    
    /**
     * nm attribute 값을 설정한다.
     * 
     * @param nm
     *            the nm to set
     */
    public void setNm(String nm) {
        this.nm = nm;
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
     * homeTelno attribute를 리턴한다.
     * 
     * @return the homeTelno
     */
    public String getHomeTelno() {
        return homeTelno;
    }

    /**
     * homeTelno attribute 값을 설정한다.
     * 
     * @param homeTelno
     *            the homeTelno to set
     */
    public void setHomeTelno(String homeTelno) {
        this.homeTelno = homeTelno;
    }

    /**
     * moblphonNo attribute를 리턴한다.
     * 
     * @return the moblphonNo
     */
    public String getMoblphonNo() {
        return moblphonNo;
    }

    /**
     * moblphonNo attribute 값을 설정한다.
     * 
     * @param moblphonNo
     *            the moblphonNo to set
     */
    public void setMoblphonNo(String moblphonNo) {
        this.moblphonNo = moblphonNo;
    }

    /**
     * offmTelno attribute를 리턴한다.
     * 
     * @return the offmTelno
     */
    public String getOffmTelno() {
        return offmTelno;
    }

    /**
     * offmTelno attribute 값을 설정한다.
     * 
     * @param offmTelno
     *            the offmTelno to set
     */
    public void setOffmTelno(String offmTelno) {
        this.offmTelno = offmTelno;
    }

    /**
     * fxnum attribute를 리턴한다.
     * 
     * @return the fxnum
     */
    public String getFxnum() {
        return fxnum;
    }

    /**
     * fxnum attribute 값을 설정한다.
     * 
     * @param fxnum
     *            the fxnum to set
     */
    public void setFxnum(String fxnum) {
        this.fxnum = fxnum;
    }

	public String getCmpnyNm() {
		return cmpnyNm;
	}

	public void setCmpnyNm(String cmpnyNm) {
		this.cmpnyNm = cmpnyNm;
	}

	public String getEntrprsSeCode() {
		return entrprsSeCode;
	}

	public void setEntrprsSeCode(String entrprsSeCode) {
		this.entrprsSeCode = entrprsSeCode;
	}

	public String getBizrno() {
		return bizrno;
	}

	public void setBizrno(String bizrno) {
		this.bizrno = bizrno;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getHouseTelno() {
		return houseTelno;
	}

	public void setHouseTelno(String houseTelno) {
		this.houseTelno = houseTelno;
	}

	public String getBankAccountNum() {
		return bankAccountNum;
	}

	public void setBankAccountNum(String bankAccountNum) {
		this.bankAccountNum = bankAccountNum;
	}

	public String getBankNm() {
		return bankNm;
	}

	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}

	public String getDepositorNm() {
		return depositorNm;
	}

	public void setDepositorNm(String depositorNm) {
		this.depositorNm = depositorNm;
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

	public String getEntrprsSeNm() {
		return entrprsSeNm;
	}

	public void setEntrprsSeNm(String entrprsSeNm) {
		this.entrprsSeNm = entrprsSeNm;
	}

	public String getCeoNm() {
		return ceoNm;
	}

	public void setCeoNm(String ceoNm) {
		this.ceoNm = ceoNm;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

	public String getBizCondition() {
		return bizCondition;
	}

	public void setBizCondition(String bizCondition) {
		this.bizCondition = bizCondition;
	}

	public String getBizDivision() {
		return bizDivision;
	}

	public void setBizDivision(String bizDivision) {
		this.bizDivision = bizDivision;
	}
    
    
}
