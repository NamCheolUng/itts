package tis.com.epay.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EpayApprLineVO implements Serializable{
	
	/**결재라인Id*/
	private String apprLineId;
	/**공통코드*/
	private String codeId;
	/**공통상세코드*/
	private String code;
	/**결재라인명*/
	private String apprLineNm;
	/**비고*/
	private String rm;
	
	public String getApprLineId() {
		return apprLineId;
	}
	public void setApprLineId(String apprLineId) {
		this.apprLineId = apprLineId;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getApprLineNm() {
		return apprLineNm;
	}
	public void setApprLineNm(String apprLineNm) {
		this.apprLineNm = apprLineNm;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		rm = rm;
	}
}
