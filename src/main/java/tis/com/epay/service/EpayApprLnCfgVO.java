package tis.com.epay.service;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class EpayApprLnCfgVO implements Serializable{

	/** 결재라인구성ID */
	private String apprLnCfgId; 
	/** 결재라인ID */
	private String apprLineId;
	/** 결재순서 */
	private String apprOrdr;
	/** 결재자ID(사원번호) */
	private String emplNo;
	/** 결재유형 */
	private String apprTy;
	/**구분 */
	private String position;
	
	private String userNm;
	
	private List<EpayApprLnCfgVO> apprLnList;
	
	public String getApprLnCfgId() {
		return apprLnCfgId;
	}
	public void setApprLnCfgId(String apprLnCfgId) {
		this.apprLnCfgId = apprLnCfgId;
	}
	public String getApprLineId() {
		return apprLineId;
	}
	public void setApprLineId(String apprLineId) {
		this.apprLineId = apprLineId;
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
	public List<EpayApprLnCfgVO> getApprLnList() {
		return apprLnList;
	}
	public void setApprLnList(List<EpayApprLnCfgVO> apprLnList) {
		this.apprLnList = apprLnList;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
}
