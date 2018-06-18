package tis.com.business.item.service;

/**
 * @Class Name : TbItemmanageVO.java
 * @Description : TbItemmanage VO class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180130
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public class TbItemmanageVO extends TbItemmanageDefaultVO{
    private static final long serialVersionUID = 1L;
    
    /** ITEM_CODE */
    private java.lang.String itemCode;
    
    /** EMPLYR_NO */
    private java.lang.String emplyrNo;
    
    /** ITEM_NAME */
    private java.lang.String itemName;
    
    /** INSERT_DT */
    private java.util.Date insertDt;
    
    /** LOCATION */
    private java.lang.String location;
    
    /** RM */
    private java.lang.String rm;
    
    public java.lang.String getItemCode() {
        return this.itemCode;
    }
    
    public void setItemCode(java.lang.String itemCode) {
        this.itemCode = itemCode;
    }
    
    public java.lang.String getEmplyrNo() {
        return this.emplyrNo;
    }
    
    public void setEmplyrNo(java.lang.String emplyrNo) {
        this.emplyrNo = emplyrNo;
    }
    
    public java.lang.String getItemName() {
        return this.itemName;
    }
    
    public void setItemName(java.lang.String itemName) {
        this.itemName = itemName;
    }
    
    public java.util.Date getInsertDt() {
        return this.insertDt;
    }
    
    public void setInsertDt(java.util.Date insertDt) {
        this.insertDt = insertDt;
    }
    
    public java.lang.String getLocation() {
        return this.location;
    }
    
    public void setLocation(java.lang.String location) {
        this.location = location;
    }
    
    public java.lang.String getRm() {
        return this.rm;
    }
    
    public void setRm(java.lang.String rm) {
        this.rm = rm;
    }
    
}
