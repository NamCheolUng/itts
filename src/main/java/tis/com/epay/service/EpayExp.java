package tis.com.epay.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EpayExp implements Serializable{

	/**지출명세서ID*/
	private String expStatId;
	/**기안정보ID*/
	private String draftInfoId;
	/**과제ID*/
	private String taskId;
	/**지출시작일*/
	private String expSdate;
	/**지출종료일*/
	private String expEdate;
	/**기안일자*/
	private String draftDate;
	/**비고*/
	private String rm;
	/**첨부파일ID*/
	private String atchFileId;
	/**지출총액*/
	private String expTotPrice;	
	/**회계등록여부*/
	private String acuntRegistYn;
	
	public String getExpStatId() {
		return expStatId;
	}
	public void setExpStatId(String expStatId) {
		this.expStatId = expStatId;
	}
	public String getDraftInfoId() {
		return draftInfoId;
	}
	public void setDraftInfoId(String draftInfoId) {
		this.draftInfoId = draftInfoId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getExpSdate() {
		return expSdate;
	}
	public void setExpSdate(String expSdate) {
		this.expSdate = expSdate;
	}
	public String getExpEdate() {
		return expEdate;
	}
	public void setExpEdate(String expEdate) {
		this.expEdate = expEdate;
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
	public String getExpTotPrice() {
		return expTotPrice;
	}
	public void setExpTotPrice(String expTotPrice) {
		this.expTotPrice = expTotPrice;
	}
	public String getAcuntRegistYn() {
		return acuntRegistYn;
	}
	public void setAcuntRegistYn(String acuntRegistYn) {
		this.acuntRegistYn = acuntRegistYn;
	}
	
}
