package tis.com.financial.deposit.service;

import java.io.Serializable;

public class DepositVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*과제(건)ID*/
	private String depositId = "";
	
	/*과제(건)명*/
	private String depositNm = "";
	
	/*금액*/
	private int cost;
	
	/*지급일자*/
	private String payDt = "";
	
	/*환급일자*/
	private String refundDt = "";
	
	/*환급금액*/
	private int refundCost;
	
	/*비고*/
	private String rm = "";
	
	/*소속코드*/
	private String affiliationId = "";

	public String getDepositId() {
		return depositId;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	public String getDepositNm() {
		return depositNm;
	}

	public void setDepositNm(String depositNm) {
		this.depositNm = depositNm;
	}

	public String getPayDt() {
		return payDt;
	}

	public void setPayDt(String payDt) {
		this.payDt = payDt;
	}

	public String getRefundDt() {
		return refundDt;
	}

	public void setRefundDt(String refundDt) {
		this.refundDt = refundDt;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getAffiliationId() {
		return affiliationId;
	}

	public void setAffiliationId(String affiliationId) {
		this.affiliationId = affiliationId;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getRefundCost() {
		return refundCost;
	}

	public void setRefundCost(int refundCost) {
		this.refundCost = refundCost;
	}

}
