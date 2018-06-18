package tis.com.epay.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EpayTaskPersonVO implements Serializable{

	/**과제담당자ID*/
	private String taskPerId;
	/**업무사용자코드(사원번호)*/
	private String emplNo;
	/**과제ID*/
	private String taskId;
	/**역할(과제책임자,과제참여자)*/
	private String role;
	/**참여율*/
	private String rate;
	/**등록일*/
	private String regDt;
	/**등록자*/
	private String regstrNm;
	/**상태(참여중:0, 업무전환:1, 퇴사:2)*/
	private String sttus;
	/**상태변경일*/
	private String sttusChngDate;
	
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
	
}
