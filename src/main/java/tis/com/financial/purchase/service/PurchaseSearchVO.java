package tis.com.financial.purchase.service;

import tis.com.common.service.DefaultVO;


public class PurchaseSearchVO extends DefaultVO {
	
	/** 부서명 */
	private String searchDeptCode;
	
	/** 발주일-시작일 */
	private String searchOrderSdate;
	
	/** 발주일-종료일 */
	private String searchOrderEdate;

	/** 관련기관/업체명 */
	private String searchPrcsPlace;
	
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

	public String getSearchPrcsPlace() {
		return searchPrcsPlace;
	}

	public void setSearchPrcsPlace(String searchPrcsPlace) {
		this.searchPrcsPlace = searchPrcsPlace;
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
