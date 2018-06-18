package tis.com.financial.laterSettle.service;

import java.io.Serializable;

public class ParkingVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*사원번호*/
	private String emplNo = "";
	
	/*차량번호*/
	private String carNumb = "";
	
	/*최근연장일자*/
	private String extensionDt = "";
	
	/*사원명*/
	private String emplNm = "";
	
	/*정산일*/
	private String paymentDt = "";
	
	/*만료일*/
	private String expirationDt = "";
	
	/*구분*/
	private String carDiv = "";
	
	/*금액*/
	private int cost;
	
	/*비고*/
	private String rm = "";

	/*회사구분*/
	private String affiliationId = "";
	
	/*이메일*/
	private String emailAdres = "";

	public String getCarNumb() {
		return carNumb;
	}

	public void setCarNumb(String carNumb) {
		this.carNumb = carNumb;
	}

	public String getExtensionDt() {
		return extensionDt;
	}

	public void setExtensionDt(String extensionDt) {
		this.extensionDt = extensionDt;
	}

	public String getPaymentDt() {
		return paymentDt;
	}

	public void setPaymentDt(String paymentDt) {
		this.paymentDt = paymentDt;
	}

	public String getExpirationDt() {
		return expirationDt;
	}

	public void setExpirationDt(String expirationDt) {
		this.expirationDt = expirationDt;
	}

	public String getCarDiv() {
		return carDiv;
	}

	public void setCarDiv(String carDiv) {
		this.carDiv = carDiv;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
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

	public String getEmplNm() {
		return emplNm;
	}

	public void setEmplNm(String emplNm) {
		this.emplNm = emplNm;
	}

	public String getEmplNo() {
		return emplNo;
	}

	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}

	public String getEmailAdres() {
		return emailAdres;
	}

	public void setEmailAdres(String emailAdres) {
		this.emailAdres = emailAdres;
	}
}
