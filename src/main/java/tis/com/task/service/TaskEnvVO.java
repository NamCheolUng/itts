package tis.com.task.service;

import java.util.List;

public class TaskEnvVO {
	private String taskId;
	
	private String os;
	
	private String lang;
	
	private String rm;
	
	private String tbTaskSubenvId;
	
	private String sept;
	
	private String nm;
	
	private String expln;
	
	private List<TaskEnvVO> taskEnvListVO;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getTbTaskSubenvId() {
		return tbTaskSubenvId;
	}

	public void setTbTaskSubenvId(String tbTaskSubenvId) {
		this.tbTaskSubenvId = tbTaskSubenvId;
	}

	public String getSept() {
		return sept;
	}

	public void setSept(String sept) {
		this.sept = sept;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getExpln() {
		return expln;
	}

	public void setExpln(String expln) {
		this.expln = expln;
	}

	public List<TaskEnvVO> getTaskEnvListVO() {
		return taskEnvListVO;
	}

	public void setTaskEnvListVO(List<TaskEnvVO> taskEnvListVO) {
		this.taskEnvListVO = taskEnvListVO;
	}
	
	
	
	
}
