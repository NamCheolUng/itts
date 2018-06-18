package tis.com.financial.profitloss.service;

import tis.com.common.service.DefaultVO;


public class ProfitLossSalaryVO extends DefaultVO {
	
	/** 과제Id */
	private String taskId;
	
	/** 과제시작일 */
	private String taskSdate;
	
	/** 과제종료일 */
	private String taskEdate;
	
	/** 사원번호 */
	private String emplNo;
	
	/** 기간 내 과제 일정 시간 */
	private int taskSchdulTime;
	
	/** 기간 내 전체 일정 시간 */
	private int totalSchdulTime;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskSdate() {
		return taskSdate;
	}

	public void setTaskSdate(String taskSdate) {
		this.taskSdate = taskSdate;
	}

	public String getTaskEdate() {
		return taskEdate;
	}

	public void setTaskEdate(String taskEdate) {
		this.taskEdate = taskEdate;
	}

	public String getEmplNo() {
		return emplNo;
	}

	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}

	public int getTaskSchdulTime() {
		return taskSchdulTime;
	}

	public void setTaskSchdulTime(int taskSchdulTime) {
		this.taskSchdulTime = taskSchdulTime;
	}

	public int getTotalSchdulTime() {
		return totalSchdulTime;
	}

	public void setTotalSchdulTime(int totalSchdulTime) {
		this.totalSchdulTime = totalSchdulTime;
	}
}
