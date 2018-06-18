package tis.com.epay.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EpayExpCor implements Serializable{

	/**지출명세서(법인)ID*/
	private String expcorStatId;
	/**기안정보ID*/
	private String draftInfoId;
	/**기안일자*/
	private String draftDate;
	/**비고*/
	private String rm;
	/**첨부파일ID*/
	private String atchFileId;
	/**지출총액*/
	private String expcorTotPrice;
	/**회계등록여부*/
	private String acuntRegistYn;
	
	public String getExpcorStatId() {
		return expcorStatId;
	}
	public void setExpcorStatId(String expcorStatId) {
		this.expcorStatId = expcorStatId;
	}
	public String getDraftInfoId() {
		return draftInfoId;
	}
	public void setDraftInfoId(String draftInfoId) {
		this.draftInfoId = draftInfoId;
	}
	public String getDraftDate() {
		return draftDate;
	}
	public void setDraftDate(String draftDate) {
		this.draftDate = draftDate;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	public String getExpcorTotPrice() {
		return expcorTotPrice;
	}
	public void setExpcorTotPrice(String expcorTotPrice) {
		this.expcorTotPrice = expcorTotPrice;
	}
	public String getAcuntRegistYn() {
		return acuntRegistYn;
	}
	public void setAcuntRegistYn(String acuntRegistYn) {
		this.acuntRegistYn = acuntRegistYn;
	}
	
}
