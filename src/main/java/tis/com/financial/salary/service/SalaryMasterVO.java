package tis.com.financial.salary.service;

import java.io.Serializable;

public class SalaryMasterVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*년월구분*/
	private String ymonth = "";
	
	/*지급일*/
	private String payDt = "";
	
	/*총증가액*/
	private int totalIncrease;

	/*총차감액*/
	private int totalReduction;

	/*총금액*/
	private int totalPay;

	/*비고*/
	private String rm = "";

	/*회사구분*/
	private String affiliationId = "";
	
	/*검색시작일*/
	private String startDate = "";
	
	/*검색종료일*/
	private String endDate = "";
	
    /** 현재페이지 */
    private int pageIndex = 1;

    /** 페이지갯수 */
    private int pageUnit = 10;

    /** 페이지사이즈 */
    private int pageSize = 10;
    
    /** 첫페이지 인덱스 */
    private int firstIndex = 1;

    /** 마지막페이지 인덱스 */
    private int lastIndex = 1;

    /** 페이지당 레코드 개수 */
    private int recordCountPerPage;
    
    /*db검색 개수*/
    private int salCnt;
	
	public String getYmonth() {
		return ymonth;
	}

	public void setYmonth(String ymonth) {
		this.ymonth = ymonth;
	}

	public String getPayDt() {
		return payDt;
	}

	public void setPayDt(String payDt) {
		this.payDt = payDt;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getAffiliationId() {
		return affiliationId;
	}

	public void setAffiliationId(String affiliationId) {
		this.affiliationId = affiliationId;
	}

	public int getTotalIncrease() {
		return totalIncrease;
	}

	public void setTotalIncrease(int totalIncrease) {
		this.totalIncrease = totalIncrease;
	}

	public int getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(int totalPay) {
		this.totalPay = totalPay;
	}

	public int getTotalReduction() {
		return totalReduction;
	}

	public void setTotalReduction(int totalReduction) {
		this.totalReduction = totalReduction;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public int getSalCnt() {
		return salCnt;
	}

	public void setSalCnt(int salCnt) {
		this.salCnt = salCnt;
	}
}
