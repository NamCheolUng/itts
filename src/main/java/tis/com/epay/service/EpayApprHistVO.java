package tis.com.epay.service;

@SuppressWarnings("serial")
public class EpayApprHistVO extends EpayApprHist{

	/** 성명(결재자명) */
	private String approverNm;
	
	public String getApproverNm() {
		return approverNm;
	}
	public void setApproverNm(String approverNm) {
		this.approverNm = approverNm;
	}
}
