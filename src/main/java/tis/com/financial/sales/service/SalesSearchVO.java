package tis.com.financial.sales.service;

import tis.com.common.service.DefaultVO;


public class SalesSearchVO extends DefaultVO {
	
	/** 부서명 */
	private String searchDeptCode;
	
	/** 발주일-시작일 */
	private String searchOrderSdate;
	
	/** 발주일-종료일 */
	private String searchOrderEdate;

	/** 관련기관/업체명 */
	private String searchAdbkId;
	
	/** 결제여부 */
	private String searchPayAt;
	
	/** 과제Id */
	private String searchTaskId;

	public String getSearchDeptCode() {
		return searchDeptCode;
	}

	public void setSearchDeptCode(String searchDeptCode) {
		this.searchDeptCode = searchDeptCode;
	}

	public String getSearchOrderSdate() {
		return searchOrderSdate;
	}

	public void setSearchOrderSdate(String searchOrderSdate) {
		this.searchOrderSdate = searchOrderSdate;
	}

	public String getSearchOrderEdate() {
		return searchOrderEdate;
	}

	public void setSearchOrderEdate(String searchOrderEdate) {
		this.searchOrderEdate = searchOrderEdate;
	}

	public String getSearchAdbkId() {
		return searchAdbkId;
	}

	public void setSearchAdbkId(String searchAdbkId) {
		this.searchAdbkId = searchAdbkId;
	}

	public String getSearchPayAt() {
		return searchPayAt;
	}

	public void setSearchPayAt(String searchPayAt) {
		this.searchPayAt = searchPayAt;
	}

	public String getSearchTaskId() {
		return searchTaskId;
	}

	public void setSearchTaskId(String searchTaskId) {
		this.searchTaskId = searchTaskId;
	}
}
