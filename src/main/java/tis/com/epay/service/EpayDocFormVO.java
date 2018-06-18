package tis.com.epay.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EpayDocFormVO implements Serializable{

	private String docFormId;
	
	private String formNm;

	private String formRm;
	
	private String checkedCnd;

	public String getDocFormId() {
		return docFormId;
	}

	public void setDocFormId(String docFormId) {
		this.docFormId = docFormId;
	}

	public String getFormNm() {
		return formNm;
	}

	public void setFormNm(String formNm) {
		this.formNm = formNm;
	}

	public String getFormRm() {
		return formRm;
	}

	public void setFormRm(String formRm) {
		this.formRm = formRm;
	}
	
	public String getCheckedCnd() {
		return checkedCnd;
	}

	public void setCheckedCnd(String checkedCnd) {
		this.checkedCnd = checkedCnd;
	}
}
