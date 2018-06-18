package tis.com.financial.sales.service;

import tis.com.common.service.DefaultVO;


/**
 * @author kit.lee
 *
 */
public class SalesVO extends DefaultVO {
	
	/** 매출ID */
	private String salesId;
	
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
	
	/** 매출처ID */
	private String adbkId;
	
	/** 매출처 */
	private String salesPlace;

	/** 결제예정일 */
	private String payPlanDate;
	
	/** 거래구분 */
	private String tradeSept;
	
	/** 결제조건 */
	private String payPoint;
	
	/** 결제방식 */
	private String payWay;
	
	/** 결제여부 */
	private String payAt;
	
	/** 회계등록여부 */
	private String payApprAt;
	
	/** 계획금액-공급가액 */
	private String salesPlanPrice;
	
	/** 계획금액-부가세 */
	private String salesPlanSurtax;
	
	/** 계획금액-전체 */
	private String salesPlanTotPrice;
	
	/** 집행금액-공급가액 */
	private String salesExecPrice;
	
	/** 집행금액-부가세 */
	private String salesExecSurtax;
	
	/** 집행금액-전체 */
	private String salesExecTotPrice;
	
	/** 첨부파일Id */
	private String atchFileId;

	public String getSalesId() {
		return salesId;
	}

	public void setSalesId(String salesId) {
		this.salesId = salesId;
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

	public String getSalesPlace() {
		return salesPlace;
	}

	public void setSalesPlace(String salesPlace) {
		this.salesPlace = salesPlace;
	}

	public String getPayPlanDate() {
		return payPlanDate;
	}

	public void setPayPlanDate(String payPlanDate) {
		this.payPlanDate = payPlanDate;
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

	public String getPayAt() {
		return payAt;
	}

	public void setPayAt(String payAt) {
		this.payAt = payAt;
	}

	public String getPayApprAt() {
		return payApprAt;
	}

	public void setPayApprAt(String payApprAt) {
		this.payApprAt = payApprAt;
	}

	public String getSalesPlanPrice() {
		return salesPlanPrice;
	}

	public void setSalesPlanPrice(String salesPlanPrice) {
		this.salesPlanPrice = salesPlanPrice;
	}

	public String getSalesPlanSurtax() {
		return salesPlanSurtax;
	}

	public void setSalesPlanSurtax(String salesPlanSurtax) {
		this.salesPlanSurtax = salesPlanSurtax;
	}

	public String getSalesPlanTotPrice() {
		return salesPlanTotPrice;
	}

	public void setSalesPlanTotPrice(String salesPlanTotPrice) {
		this.salesPlanTotPrice = salesPlanTotPrice;
	}

	public String getSalesExecPrice() {
		return salesExecPrice;
	}

	public void setSalesExecPrice(String salesExecPrice) {
		this.salesExecPrice = salesExecPrice;
	}

	public String getSalesExecSurtax() {
		return salesExecSurtax;
	}

	public void setSalesExecSurtax(String salesExecSurtax) {
		this.salesExecSurtax = salesExecSurtax;
	}

	public String getSalesExecTotPrice() {
		return salesExecTotPrice;
	}

	public void setSalesExecTotPrice(String salesExecTotPrice) {
		this.salesExecTotPrice = salesExecTotPrice;
	}

	public String getAtchFileId() {
		return atchFileId;
	}

	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
}
