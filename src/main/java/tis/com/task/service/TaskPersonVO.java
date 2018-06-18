package tis.com.task.service;

import java.util.List;

public class TaskPersonVO {
	
	private String taskPerId;
	
	private String emplNo;
	
	private String taskId;
	
	private String role;
	
	private String rate;
	
	private String regDt;
	
	private String regstrNm;
	
	private String sttus;
	
	private String sttusChngDate;
	
	private List<TaskPersonVO> taskPersonVOList;

	public String getTaskPerId() {
		return taskPerId;
	}

	public void setTaskPerId(String taskPerId) {
		this.taskPerId = taskPerId;
	}

	public String getEmplNo() {
		return emplNo;
	}

	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getRegstrNm() {
		return regstrNm;
	}

	public void setRegstrNm(String regstrNm) {
		this.regstrNm = regstrNm;
	}

	public String getSttus() {
		return sttus;
	}

	public void setSttus(String sttus) {
		this.sttus = sttus;
	}

	public String getSttusChngDate() {
		return sttusChngDate;
	}

	public void setSttusChngDate(String sttusChngDate) {
		this.sttusChngDate = sttusChngDate;
	}

	public List<TaskPersonVO> getTaskPersonVOList() {
		return taskPersonVOList;
	}

	public void setTaskPersonVOList(List<TaskPersonVO> taskPersonVOList) {
		this.taskPersonVOList = taskPersonVOList;
	}
	
	
	
}
