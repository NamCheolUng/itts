package tis.com.epay.service;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class EpayApprLnCfgTmpVO implements Serializable{

	/** 임시저장용 데이터 식별을 위한 기안정보 ID*/
	private String draftInfoId;
	/** 결재순서 */
	private String apprOrdr;
	/** 결재자ID(사원번호) */
	private String emplNo;
	/** 결재유형 */
	private String apprTy;
	/**구분 */
	private String position;
	
	private String userNm;
	
	private List<EpayApprLnCfgTmpVO> apprLnList;
	
	public String getDraftInfoId() {
		return draftInfoId;
	}
	public void setDraftInfoId(String draftInfoId) {
		this.draftInfoId = draftInfoId;
	}
	public String getApprOrdr() {
		return apprOrdr;
	}
	public void setApprOrdr(String apprOrdr) {
		this.apprOrdr = apprOrdr;
	}
	public String getEmplNo() {
		return emplNo;
	}
	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}
	public String getApprTy() {
		return apprTy;
	}
	public void setApprTy(String apprTy) {
		this.apprTy = apprTy;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public List<EpayApprLnCfgTmpVO> getApprLnList() {
		return apprLnList;
	}
	public void setApprLnList(List<EpayApprLnCfgTmpVO> apprLnList) {
		this.apprLnList = apprLnList;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
}
