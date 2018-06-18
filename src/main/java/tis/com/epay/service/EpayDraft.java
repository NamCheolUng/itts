package tis.com.epay.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EpayDraft implements Serializable{
	/** 기안서 ID */
	private String draftId;
	/** 기안정보 ID*/
	private String draftInfoId;
	/** 과제 ID */
	private String taskId;
	/** 첨부파일 ID */
	private String atchFileId;
	/** 시행일자 */
	private String draftSDate;
	/** 내용 */
	private String draftContents;
	/** 비고 */
	private String rm;
	
	public String getDraftId() {
		return draftId;
	}
	public void setDraftId(String draftId) {
		this.draftId = draftId;
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
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	public String getDraftSDate() {
		return draftSDate;
	}
	public void setDraftSDate(String draftSDate) {
		this.draftSDate = draftSDate;
	}
	public String getDraftContents() {
		return draftContents;
	}
	public void setDraftContents(String draftContents) {
		this.draftContents = draftContents;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
}
