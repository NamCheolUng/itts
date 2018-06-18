package tis.com.financial.profitloss.service;

import tis.com.common.service.DefaultVO;


public class ProfitLossSearchVO extends DefaultVO {
	
	/** 주부서 */
	private String searchMainDept;
	
	/** 시작일 */
	private String searchTaskSdate;
	
	/** 종료일 */
	private String searchTaskEdate;
	
	/** 과제Id */
	private String searchTaskId;

	public String getSearchMainDept() {
		return searchMainDept;
	}

	public void setSearchMainDept(String searchMainDept) {
		this.searchMainDept = searchMainDept;
	}

	public String getSearchTaskSdate() {
		return searchTaskSdate;
	}

	public void setSearchTaskSdate(String searchTaskSdate) {
		this.searchTaskSdate = searchTaskSdate;
	}

	public String getSearchTaskEdate() {
		return searchTaskEdate;
	}

	public void setSearchTaskEdate(String searchTaskEdate) {
		this.searchTaskEdate = searchTaskEdate;
	}

	public String getSearchTaskId() {
		return searchTaskId;
	}

	public void setSearchTaskId(String searchTaskId) {
		this.searchTaskId = searchTaskId;
	}
}
