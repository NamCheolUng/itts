package tis.com.epay.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EpayDraftInfo implements Serializable{

	/** 기안정보 ID */
	private String draftInfoId;
	
	/** 문서양식 ID */
	private String docFormId;
	
	/** 기안일자 */
	private String regDt;
	
	/** 소속코드 */
	private String affiliationId;
	
	/** 삭제여부 */
	private String delAt;
	
	/** 기안자 ID(사원번호) */
	private String emplNo;
	
	/** 기안제목 */
	private String title;
	
	/** 문서번호 */
	private String docNo;
	
	/** 문서번호생성년도 */
	private String noYear;
	
	/** 문서일련번호 */
	private String noId;
	
	/** 기안상태 */
	private String apprSttus;
	
	/**결재라인부서코드*/
	private String code;
	
	/**결재할 사원번호*/
	private String apprEmplNo;

	public String getDraftInfoId() {
		return draftInfoId;
	}

	public void setDraftInfoId(String draftInfoId) {
		this.draftInfoId = draftInfoId;
	}

	public String getDocFormId() {
		return docFormId;
	}

	public void setDocFormId(String docFormId) {
		this.docFormId = docFormId;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getAffiliationId() {
		return affiliationId;
	}

	public void setAffiliationId(String affiliationId) {
		this.affiliationId = affiliationId;
	}

	public String getDelAt() {
		return delAt;
	}

	public void setDelAt(String delAt) {
		this.delAt = delAt;
	}

	public String getEmplNo() {
		return emplNo;
	}

	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getNoYear() {
		return noYear;
	}

	public void setNoYear(String noYear) {
		this.noYear = noYear;
	}

	public String getNoId() {
		return noId;
	}

	public void setNoId(String noId) {
		this.noId = noId;
	}
	
	public String getApprSttus() {
		return apprSttus;
	}

	public void setApprSttus(String apprSttus) {
		this.apprSttus = apprSttus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getApprEmplNo() {
		return apprEmplNo;
	}

	public void setApprEmplNo(String apprEmplNo) {
		this.apprEmplNo = apprEmplNo;
	}
	
}
