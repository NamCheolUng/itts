package tis.com.financial.bankBook.service;

import java.io.Serializable;

/**
 * @Class Name : TbBankcardmanageVO.java
 * @Description : TbBankcardmanage VO class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180126
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public class TbBankcardmanageVO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /** BANK_NM */
    private java.lang.String bankNm;
    
    /** BANK_ACCOUNT_NUM */
    private java.lang.String bankAccountNum;
    
    /** CARD_NUM */
    private java.lang.String cardNum;
    
    /** DEPOSITOR_NM */
    private java.lang.String depositorNm;
    
    /** CARD_NM */
    private java.lang.String cardNm;
    
    /** END_PNTTM */
    private java.lang.String endPnttm;
    
    /** USE_AT */
    private java.lang.String useAt;
    
    /** RM */
    private java.lang.String rm;
    
    /** CP_CODE */
    private java.lang.String cpCode;
    
    public java.lang.String getBankNm() {
        return this.bankNm;
    }
    
    public void setBankNm(java.lang.String bankNm) {
        this.bankNm = bankNm;
    }
    
    public java.lang.String getBankAccountNum() {
        return this.bankAccountNum;
    }
    
    public void setBankAccountNum(java.lang.String bankAccountNum) {
        this.bankAccountNum = bankAccountNum;
    }
    
    public java.lang.String getCardNum() {
        return this.cardNum;
    }
    
    public void setCardNum(java.lang.String cardNum) {
        this.cardNum = cardNum;
    }
    
    public java.lang.String getDepositorNm() {
        return this.depositorNm;
    }
    
    public void setDepositorNm(java.lang.String depositorNm) {
        this.depositorNm = depositorNm;
    }
    
    public java.lang.String getCardNm() {
        return this.cardNm;
    }
    
    public void setCardNm(java.lang.String cardNm) {
        this.cardNm = cardNm;
    }
    

    
    public java.lang.String getEndPnttm() {
		return endPnttm;
	}

	public void setEndPnttm(java.lang.String endPnttm) {
		this.endPnttm = endPnttm;
	}

    public java.lang.String getUseAt() {
        return this.useAt;
    }
    
    public void setUseAt(java.lang.String useAt) {
        this.useAt = useAt;
    }
    
    public java.lang.String getRm() {
        return this.rm;
    }
    
    public void setRm(java.lang.String rm) {
        this.rm = rm;
    }
    
    public java.lang.String getCpCode() {
        return this.cpCode;
    }
    
    public void setCpCode(java.lang.String cpCode) {
        this.cpCode = cpCode;
    }
    
}
