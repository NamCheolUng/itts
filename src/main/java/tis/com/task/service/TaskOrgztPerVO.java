package tis.com.task.service;

import java.util.List;

import tis.com.common.service.DefaultVO;

public class TaskOrgztPerVO extends DefaultVO{

	private String orgztPerId;
	
	private String ncrdId;
	
	private String taskId;
	
	private String sept;
	
	private String regDate;
	
	private String regstr;
	
	private String role;
	
	private String rm;
	
	private String sttus;
	
	private String adbkId;
	
	private String sttusChngDate;
	
	private String schdulId;
	
	private List<TaskOrgztPerVO> taskOrgztPerVOList;

	public String getOrgztPerId() {
		return orgztPerId;
	}

	public void setOrgztPerId(String orgztPerId) {
		this.orgztPerId = orgztPerId;
	}

	public String getNcrdId() {
		return ncrdId;
	}

	public void setNcrdId(String ncrdId) {
		this.ncrdId = ncrdId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getSept() {
		return sept;
	}

	public void setSept(String sept) {
		this.sept = sept;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegstr() {
		return regstr;
	}

	public void setRegstr(String regstr) {
		this.regstr = regstr;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
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

	public String getAdbkId() {
		return adbkId;
	}

	public void setAdbkId(String adbkId) {
		this.adbkId = adbkId;
	}

	public List<TaskOrgztPerVO> getTaskOrgztPerVOList() {
		return taskOrgztPerVOList;
	}

	public void setTaskOrgztPerVOList(List<TaskOrgztPerVO> taskOrgztPerVOList) {
		this.taskOrgztPerVOList = taskOrgztPerVOList;
	}

	public String getSchdulId() {
		return schdulId;
	}

	public void setSchdulId(String schdulId) {
		this.schdulId = schdulId;
	}
	
	

	
	
}
