package tis.com.epay.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EpayCnsulVO extends EpayCnsul{

	/** 기안정보 */
	private EpayDraftInfo epayDraftInfoVO;
	/** 과제명 */
	private String taskNm;
	/** 기안자ID */
	private String emplNo;
	/** 부서명 */
	private String orgnztNm;
	/** 기안자명 */
	private String drafterNm;
	/** 기안제목 */
	private String title;
	/** 기안문서번호 */
	private String docNo;
	/** 기안일자 */
	private String regDt;
	public EpayDraftInfo getEpayDraftInfoVO() {
		return epayDraftInfoVO;
	}
	public void setEpayDraftInfoVO(EpayDraftInfo epayDraftInfoVO) {
		this.epayDraftInfoVO = epayDraftInfoVO;
	}
	public String getTaskNm() {
		return taskNm;
	}
	public void setTaskNm(String taskNm) {
		this.taskNm = taskNm;
	}
	public String getEmplNo() {
		return emplNo;
	}
	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}
	public String getOrgnztNm() {
		return orgnztNm;
	}
	public void setOrgnztNm(String orgnztNm) {
		this.orgnztNm = orgnztNm;
	}
	public String getDrafterNm() {
		return drafterNm;
	}
	public void setDrafterNm(String drafterNm) {
		this.drafterNm = drafterNm;
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
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	
}
