package tis.com.epay.service;

@SuppressWarnings("serial")
public class EpayHolidayVO extends EpayHoliday{

	/** 기안정보 */
	private EpayDraftInfo epayDraftInfoVO;
	/** 기안자ID(사원번호) */
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
