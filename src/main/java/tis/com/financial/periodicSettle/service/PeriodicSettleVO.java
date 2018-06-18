package tis.com.financial.periodicSettle.service;

import java.io.Serializable;

public class PeriodicSettleVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*과제(건)ID*/
	private String calculId = "";
	
	/*과제(건)명*/
	private String calculNm = "";

	/*일시*/
	private String calculDe = "";

	/*월/년/분기 구분*/
	private String divNm = "";

	/*거래처 명*/
	private String cmpnyNm = "";

	/*금액*/
	private int cost;

	/*결제(예정)일*/
	private String paymentScDt = "";

	/*부서명*/
	private String orgnztNm = "";

	/*지출수입구분*/
	private String typeAt = "";

	/*소속코드*/
	private String affiliationId = "";
	
	/*비고*/
	private String rm = "";
	
	public String getCalculId() {
		return calculId;
	}

	public void setCalculId(String calculId) {
		this.calculId = calculId;
	}

	public String getCalculNm() {
		return calculNm;
	}

	public void setCalculNm(String calculNm) {
		this.calculNm = calculNm;
	}

	public String getCalculDe() {
		return calculDe;
	}

	public void setCalculDe(String calculDe) {
		this.calculDe = calculDe;
	}

	public String getCmpnyNm() {
		return cmpnyNm;
	}

	public void setCmpnyNm(String cmpnyNm) {
		this.cmpnyNm = cmpnyNm;
	}

	public String getPaymentScDt() {
		return paymentScDt;
	}

	public void setPaymentScDt(String paymentScDt) {
		this.paymentScDt = paymentScDt;
	}

	public String getOrgnztNm() {
		return orgnztNm;
	}

	public void setOrgnztNm(String orgnztNm) {
		this.orgnztNm = orgnztNm;
	}

	public String getTypeAt() {
		return typeAt;
	}

	public void setTypeAt(String typeAt) {
		this.typeAt = typeAt;
	}

	public String getDivNm() {
		return divNm;
	}

	public void setDivNm(String divNm) {
		this.divNm = divNm;
	}

	public String getAffiliationId() {
		return affiliationId;
	}

	public void setAffiliationId(String affiliationId) {
		this.affiliationId = affiliationId;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}


}
