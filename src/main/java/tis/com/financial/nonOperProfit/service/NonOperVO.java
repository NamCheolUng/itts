package tis.com.financial.nonOperProfit.service;

import java.io.Serializable;

public class NonOperVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*영업외이익ID*/
	private String otdealId = "";
	
	/*과제ID*/
	private String taskId = "";
	
	/*영업외이익 명*/
	private String otdealNm = "";

	/*거래일시*/
	private String otdealDt = "";
	
	/*금액*/
	private int amount;
	
	/*비고*/
	private String rm = "";
	
	/*소속코드*/
	private String affiliationId = "";

	public String getOtdealId() {
		return otdealId;
	}

	public void setOtdealId(String otdealId) {
		this.otdealId = otdealId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOtdealNm() {
		return otdealNm;
	}

	public void setOtdealNm(String otdealNm) {
		this.otdealNm = otdealNm;
	}

	public String getOtdealDt() {
		return otdealDt;
	}

	public void setOtdealDt(String otdealDt) {
		this.otdealDt = otdealDt;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
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
	
}
