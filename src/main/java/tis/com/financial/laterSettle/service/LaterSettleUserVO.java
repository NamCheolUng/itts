package tis.com.financial.laterSettle.service;

import java.io.Serializable;
import java.util.List;

public class LaterSettleUserVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*사원번호*/
	private String emplNo = "";
	
	/*사원명*/
	private String userNm = "";

	/*회사구분*/
	private String affiliationId = "";
	
	/*이메일*/
	private String emailAdres = "";

	public String getEmplNo() {
		return emplNo;
	}

	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}

	public String getAffiliationId() {
		return affiliationId;
	}

	public void setAffiliationId(String affiliationId) {
		this.affiliationId = affiliationId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getEmailAdres() {
		return emailAdres;
	}

	public void setEmailAdres(String emailAdres) {
		this.emailAdres = emailAdres;
	}
}
