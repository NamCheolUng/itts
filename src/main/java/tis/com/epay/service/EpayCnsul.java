package tis.com.epay.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EpayCnsul implements Serializable{

	/**품의서ID*/
	private String cnsulId;
	/**기안정보ID*/
	private String draftInfoId;
	/**과제ID*/
	private String taskId;
	/**매출관리ID*/
	private String salesId;
	/**매입관리ID*/
	private String purchaseId;
	/**내용*/
	private String cnsulCn;
	/**비고*/
	private String rm;
	/**시행일자*/
	private String cnsulSdate;
	public String getCnsulId() {
		return cnsulId;
	}
	public void setCnsulId(String cnsulId) {
		this.cnsulId = cnsulId;
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
	public String getSalesId() {
		return salesId;
	}
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	public String getCnsulCn() {
		return cnsulCn;
	}
	public void setCnsulCn(String cnsulCn) {
		this.cnsulCn = cnsulCn;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
	public String getCnsulSdate() {
		return cnsulSdate;
	}
	public void setCnsulSdate(String cnsulSdate) {
		this.cnsulSdate = cnsulSdate;
	}
}
