package tis.com.financial.bankBook.service;

import java.io.Serializable;

/**
 * @Class Name : TbBankbookmanageVO.java
 * @Description : TbBankbookmanage VO class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180125
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public class TbBankbookmanageVO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /** BANK_NM */
    private java.lang.String bankNm;
    
    /** BANK_ACCOUNT_NUM */
    private java.lang.String bankAccountNum;
    
    /** DEPOSITOR_NM */
    private java.lang.String depositorNm;
    
    /** BALANCE */
    private int balance;
    
    /** UPDT_PNTTM */
    private java.util.Date updtPnttm;
    
    /** DIV */
    private java.lang.String division;
    
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
    
    public java.lang.String getDepositorNm() {
        return this.depositorNm;
    }
    
    public void setDepositorNm(java.lang.String depositorNm) {
        this.depositorNm = depositorNm;
    }
    
    public int getBalance() {
        return this.balance;
    }
    
    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    public java.util.Date getUpdtPnttm() {
        return this.updtPnttm;
    }
    
    public void setUpdtPnttm(java.util.Date updtPnttm) {
        this.updtPnttm = updtPnttm;
    }
    
    public java.lang.String getDivision() {
        return this.division;
    }
    
    public void setDivision(java.lang.String division) {
        this.division = division;
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
