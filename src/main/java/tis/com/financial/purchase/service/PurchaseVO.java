package tis.com.financial.purchase.service;

import tis.com.common.service.DefaultVO;


public class PurchaseVO extends DefaultVO {
	
	/** 매입ID */
	private String purchaseId;
	
	/** 과제ID */
	private String taskId;
	
	/** 과제진행단계 */
	private String taskStepId;
	
	/** 기안정보ID */
	private String draftInfoId;
	
	/** 발주일 */
	private String orderDt;
	
	/** 상품 및 서비스명 */
	private String prdtNm;
	
	/** 매입처ID */
	private String adbkId;
	
	/** 매입처 */
	private String prcsPlace;

	/** 결제예정일 */
	private String payPlanDate;
	
	/** 결제일 */
	private String payDate;
	
	/** 결제여부 */
	private String payAt;
	
	/** 결제승인 */
	private String payApproval;
	
	/** 세금계산서발행 */
	private String taxPlanDate;
	
	/** 거래구분 */
	private String tradeSept;
	
	/** 결제조건 */
	private String payPoint;
	
	/** 결제방식 */
	private String payWay;
	
	/** 회계등록여부 */
	private String payApprAt;
	
	/** 계획금액-공급가액 */
	private String prcsPlanPrice;
	
	/** 계획금액-부가세 */
	private String prcsPlanSurtax;
	
	/** 계획금액-전체 */
	private String prcsPlanTotPrice;
	
	/** 집행금액-공급가액 */
	private String prcsExecPrice;
	
	/** 집행금액-부가세 */
	private String prcsExecSurtax;
	
	/** 집행금액-전체 */
	private String prcsExecTotPrice;
	
	/** 첨부파일Id */
	private String atchFileId;

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskStepId() {
		return taskStepId;
	}

	public void setTaskStepId(String taskStepId) {
		this.taskStepId = taskStepId;
	}

	public String getDraftInfoId() {
		return draftInfoId;
	}

	public void setDraftInfoId(String draftInfoId) {
		this.draftInfoId = draftInfoId;
	}

	public String getOrderDt() {
		return orderDt;
	}

	public void setOrderDt(String orderDt) {
		this.orderDt = orderDt;
	}

	public String getPrdtNm() {
		return prdtNm;
	}

	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}

	public String getAdbkId() {
		return adbkId;
	}

	public void setAdbkId(String adbkId) {
		this.adbkId = adbkId;
	}

	public String getPrcsPlace() {
		return prcsPlace;
	}

	public void setPrcsPlace(String prcsPlace) {
		this.prcsPlace = prcsPlace;
	}

	public String getPayPlanDate() {
		return payPlanDate;
	}

	public void setPayPlanDate(String payPlanDate) {
		this.payPlanDate = payPlanDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getPayAt() {
		return payAt;
	}

	public void setPayAt(String payAt) {
		this.payAt = payAt;
	}

	public String getPayApproval() {
		return payApproval;
	}

	public void setPayApproval(String payApproval) {
		this.payApproval = payApproval;
	}

	public String getTaxPlanDate() {
		return taxPlanDate;
	}

	public void setTaxPlanDate(String taxPlanDate) {
		this.taxPlanDate = taxPlanDate;
	}

	public String getTradeSept() {
		return tradeSept;
	}

	public void setTradeSept(String tradeSept) {
		this.tradeSept = tradeSept;
	}

	public String getPayPoint() {
		return payPoint;
	}

	public void setPayPoint(String payPoint) {
		this.payPoint = payPoint;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getPayApprAt() {
		return payApprAt;
	}

	public void setPayApprAt(String payApprAt) {
		this.payApprAt = payApprAt;
	}

	public String getPrcsPlanPrice() {
		return prcsPlanPrice;
	}

	public void setPrcsPlanPrice(String prcsPlanPrice) {
		this.prcsPlanPrice = prcsPlanPrice;
	}

	public String getPrcsPlanSurtax() {
		return prcsPlanSurtax;
	}

	public void setPrcsPlanSurtax(String prcsPlanSurtax) {
		this.prcsPlanSurtax = prcsPlanSurtax;
	}

	public String getPrcsPlanTotPrice() {
		return prcsPlanTotPrice;
	}

	public void setPrcsPlanTotPrice(String prcsPlanTotPrice) {
		this.prcsPlanTotPrice = prcsPlanTotPrice;
	}

	public String getPrcsExecPrice() {
		return prcsExecPrice;
	}

	public void setPrcsExecPrice(String prcsExecPrice) {
		this.prcsExecPrice = prcsExecPrice;
	}

	public String getPrcsExecSurtax() {
		return prcsExecSurtax;
	}

	public void setPrcsExecSurtax(String prcsExecSurtax) {
		this.prcsExecSurtax = prcsExecSurtax;
	}

	public String getPrcsExecTotPrice() {
		return prcsExecTotPrice;
	}

	public void setPrcsExecTotPrice(String prcsExecTotPrice) {
		this.prcsExecTotPrice = prcsExecTotPrice;
	}

	public String getAtchFileId() {
		return atchFileId;
	}

	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
}
