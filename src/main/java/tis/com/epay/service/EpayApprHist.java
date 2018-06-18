package tis.com.epay.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EpayApprHist implements Serializable{

	/** 결재이력ID */
	private String apprHistId;
	/** 기안정보ID */
	private String draftInfoId;
	/** 결재일시 */
	private String apprTm;
	/** 결재자ID(사원번호) */
	private String emplNo;
	/** 결재상태 */
	private String apprState;
	/** 첨언 */
	private String cm;
	/** 반려의견 */
	private String returnCm;
	/** 결재순서 */
	private String apprOrdr;
	/** 결재유형 */
	private String apprTy;
	/** 결재라인구성구분 */
	private String postn;
	
	private String orgnztNm;
	
	private String userNm;
	
	public String getApprHistId() {
		return apprHistId;
	}
	public void setApprHistId(String apprHistId) {
		this.apprHistId = apprHistId;
	}
	public String getDraftInfoId() {
		return draftInfoId;
	}
	public void setDraftInfoId(String draftInfoId) {
		this.draftInfoId = draftInfoId;
	}
	public String getApprTm() {
		return apprTm;
	}
	public void setApprTm(String apprTm) {
		this.apprTm = apprTm;
	}
	public String getEmplNo() {
		return emplNo;
	}
	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}
	public String getApprState() {
		return apprState;
	}
	public void setApprState(String apprState) {
		this.apprState = apprState;
	}
	public String getCm() {
		return cm;
	}
	public void setComment(String cm) {
		this.cm = cm;
	}
	public String getReturnCm() {
		return returnCm;
	}
	public void setReturnCm(String returnCm) {
		this.returnCm = returnCm;
	}
	public String getApprOrdr() {
		return apprOrdr;
	}
	public void setApprOrdr(String apprOrdr) {
		this.apprOrdr = apprOrdr;
	}
	public String getApprTy() {
		return apprTy;
	}
	public void setApprTy(String apprTy) {
		this.apprTy = apprTy;
	}
	public String getPostn() {
		return postn;
	}
	public void setPostn(String postn) {
		this.postn = postn;
	}
	public String getOrgnztNm() {
		return orgnztNm;
	}
	public void setOrgnztNm(String orgnztNm) {
		this.orgnztNm = orgnztNm;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
}
