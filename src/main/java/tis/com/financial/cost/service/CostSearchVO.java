package tis.com.financial.cost.service;

import tis.com.common.service.DefaultVO;


public class CostSearchVO extends DefaultVO {
	
	/** 부서코드 */
	private String searchDeptCode;
	
	/** 결제여부 */
	private String searchPayAt;
	
	/** 사용일-시작일 */
	private String searchExpSdate;
	
	/** 사용일-종료일 */
	private String searchExpEdate;
	
	/** 과제Id */
	private String searchTaskId;

	public String getSearchDeptCode() {
		return searchDeptCode;
	}

	public void setSearchDeptCode(String searchDeptCode) {
		this.searchDeptCode = searchDeptCode;
	}

	public String getSearchPayAt() {
		return searchPayAt;
	}

	public void setSearchPayAt(String searchPayAt) {
		this.searchPayAt = searchPayAt;
	}

	public String getSearchExpSdate() {
		return searchExpSdate;
	}

	public void setSearchExpSdate(String searchExpSdate) {
		this.searchExpSdate = searchExpSdate;
	}

	public String getSearchExpEdate() {
		return searchExpEdate;
	}

	public void setSearchExpEdate(String searchExpEdate) {
		this.searchExpEdate = searchExpEdate;
	}

	public String getSearchTaskId() {
		return searchTaskId;
	}

	public void setSearchTaskId(String searchTaskId) {
		this.searchTaskId = searchTaskId;
	}
}
