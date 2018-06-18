package tis.com.task.service;

import java.util.List;

public class TaskStepVO {
	
	private String taskId;
	
	private String taskStepId;
	
	private String taskStep;
	
	private String stepNm;
	
	private String planSdt;
	
	private String planEdt;
	
	private String ptaskStepId;
	
	private String ordr;
	
	private List<TaskStepVO> taskStepVOList;

	public String getTaskStepId() {
		return taskStepId;
	}

	public void setTaskStepId(String taskStepId) {
		this.taskStepId = taskStepId;
	}

	public String getTaskStep() {
		return taskStep;
	}

	public void setTaskStep(String taskStep) {
		this.taskStep = taskStep;
	}

	public String getStepNm() {
		return stepNm;
	}

	public void setStepNm(String stepNm) {
		this.stepNm = stepNm;
	}

	public String getPlanSdt() {
		return planSdt;
	}

	public void setPlanSdt(String planSdt) {
		this.planSdt = planSdt;
	}

	public String getPlanEdt() {
		return planEdt;
	}

	public void setPlanEdt(String planEdt) {
		this.planEdt = planEdt;
	}

	public String getPtaskStepId() {
		return ptaskStepId;
	}

	public void setPtaskStepId(String ptaskStepId) {
		this.ptaskStepId = ptaskStepId;
	}

	public String getOrdr() {
		return ordr;
	}

	public void setOrdr(String ordr) {
		this.ordr = ordr;
	}

	public List<TaskStepVO> getTaskStepVOList() {
		return taskStepVOList;
	}

	public void setTaskStepVOList(List<TaskStepVO> taskStepVOList) {
		this.taskStepVOList = taskStepVOList;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
}
