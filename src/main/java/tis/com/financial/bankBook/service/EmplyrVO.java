package tis.com.financial.bankBook.service;

import java.io.Serializable;

public class EmplyrVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String emplNo;
	
	private String userNm;

	public String getEmplNo() {
		return emplNo;
	}

	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	
	
	
}
