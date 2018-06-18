package tis.com.financial.salary.service;

import java.io.Serializable;

public class SalaryVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*사원번호*/
	private String emplyrNo = "";
	
	/*년월구분*/
	private String ymonth = "";
	
	/*급여형태*/
	private String payType = "";
	
	/*지급일*/
	private String payDt = "";
	
	/*기본근무시간*/
	private int basicWorkingTime;
	
	/*야간근무*/
	private int nightWorkingTime;
	
	/*주말근무*/
	private int holidayWorkingTime;
	
	/*기본월급*/
	private int basicmPay;
	
	/*기본일급*/
	private int basicdPay;
	
	/*기본시급*/
	private int basichPay;
	
	/*주휴수당*/
	private int weeklyHolidayPay;
	
	/*연장근로수당*/
	private int extendedPay;
	
	/*휴일근로수당*/
	private int holidayPay;
	
	/*야간근로수당*/
	private int nightWorkPay;
	
	/*상여금*/
	private int bonus;
	
	/*직급수당*/
	private int ofcpsPay;
	
	/*특근수당*/
	private int overtimePay;
	
	/*교통비*/
	private int transporExpe;
	
	/*차량유지비*/
	private int carMaintenExpe;
	
	/*양육비*/
	private int childRearingExpe;
	
	/*식대*/
	private int mealExpenses;
	
	/*성과급*/
	private int paymentResults;
	
	/*소득세*/
	private int incomeTax;
	
	/*주민세*/
	private int residenceTax;
	
	/*국민연금*/
	private int nationalPension;
	
	/*건강보험*/
	private int healthInsu;
	
	/*고용보험*/
	private int umplInsu;
	
	/*장기요양*/
	private int longCareInsu;	

	/*총증가액*/
	private int totalIncrease;

	/*총차감액*/
	private int totalReduction;
	
	/*총금액*/
	private int totalPay;
	
	/*회사구분*/
	private String affiliationId = "";
	
	/*비고*/
	private String rm = "";
	
	/*사원이름*/
	private String emplyrNm = "";
	
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
    private int recordCountPerPage = 10;
    
	/*검색시작일*/
	private String startDate = "";
	
	/*검색종료일*/
	private String endDate = "";
	
	public String getEmplyrNo() {
		return emplyrNo;
	}

	public void setEmplyrNo(String emplyrNo) {
		this.emplyrNo = emplyrNo;
	}

	public String getYmonth() {
		return ymonth;
	}

	public void setYmonth(String ymonth) {
		this.ymonth = ymonth;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayDt() {
		return payDt;
	}

	public void setPayDt(String payDt) {
		this.payDt = payDt;
	}

	public int getBasicWorkingTime() {
		return basicWorkingTime;
	}

	public void setBasicWorkingTime(int basicWorkingTime) {
		this.basicWorkingTime = basicWorkingTime;
	}

	public int getNightWorkingTime() {
		return nightWorkingTime;
	}

	public void setNightWorkingTime(int nightWorkingTime) {
		this.nightWorkingTime = nightWorkingTime;
	}

	public int getHolidayWorkingTime() {
		return holidayWorkingTime;
	}

	public void setHolidayWorkingTime(int holidayWorkingTime) {
		this.holidayWorkingTime = holidayWorkingTime;
	}

	public int getBasicmPay() {
		return basicmPay;
	}

	public void setBasicmPay(int basicmPay) {
		this.basicmPay = basicmPay;
	}

	public int getBasicdPay() {
		return basicdPay;
	}

	public void setBasicdPay(int basicdPay) {
		this.basicdPay = basicdPay;
	}

	public int getBasichPay() {
		return basichPay;
	}

	public void setBasichPay(int basichPay) {
		this.basichPay = basichPay;
	}

	public int getWeeklyHolidayPay() {
		return weeklyHolidayPay;
	}

	public void setWeeklyHolidayPay(int weeklyHolidayPay) {
		this.weeklyHolidayPay = weeklyHolidayPay;
	}

	public int getExtendedPay() {
		return extendedPay;
	}

	public void setExtendedPay(int extendedPay) {
		this.extendedPay = extendedPay;
	}

	public int getHolidayPay() {
		return holidayPay;
	}

	public void setHolidayPay(int holidayPay) {
		this.holidayPay = holidayPay;
	}

	public int getNightWorkPay() {
		return nightWorkPay;
	}

	public void setNightWorkPay(int nightWorkPay) {
		this.nightWorkPay = nightWorkPay;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public int getOfcpsPay() {
		return ofcpsPay;
	}

	public void setOfcpsPay(int ofcpsPay) {
		this.ofcpsPay = ofcpsPay;
	}

	public int getOvertimePay() {
		return overtimePay;
	}

	public void setOvertimePay(int overtimePay) {
		this.overtimePay = overtimePay;
	}

	public int getTransporExpe() {
		return transporExpe;
	}

	public void setTransporExpe(int transporExpe) {
		this.transporExpe = transporExpe;
	}

	public int getCarMaintenExpe() {
		return carMaintenExpe;
	}

	public void setCarMaintenExpe(int carMaintenExpe) {
		this.carMaintenExpe = carMaintenExpe;
	}

	public int getChildRearingExpe() {
		return childRearingExpe;
	}

	public void setChildRearingExpe(int childRearingExpe) {
		this.childRearingExpe = childRearingExpe;
	}

	public int getMealExpenses() {
		return mealExpenses;
	}

	public void setMealExpenses(int mealExpenses) {
		this.mealExpenses = mealExpenses;
	}

	public int getPaymentResults() {
		return paymentResults;
	}

	public void setPaymentResults(int paymentResults) {
		this.paymentResults = paymentResults;
	}

	public int getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(int incomeTax) {
		this.incomeTax = incomeTax;
	}

	public int getResidenceTax() {
		return residenceTax;
	}

	public void setResidenceTax(int residenceTax) {
		this.residenceTax = residenceTax;
	}

	public int getNationalPension() {
		return nationalPension;
	}

	public void setNationalPension(int nationalPension) {
		this.nationalPension = nationalPension;
	}

	public int getHealthInsu() {
		return healthInsu;
	}

	public void setHealthInsu(int healthInsu) {
		this.healthInsu = healthInsu;
	}

	public int getUmplInsu() {
		return umplInsu;
	}

	public void setUmplInsu(int umplInsu) {
		this.umplInsu = umplInsu;
	}

	public int getLongCareInsu() {
		return longCareInsu;
	}

	public void setLongCareInsu(int longCareInsu) {
		this.longCareInsu = longCareInsu;
	}

	public int getTotalIncrease() {
		return totalIncrease;
	}

	public void setTotalIncrease(int totalIncrease) {
		this.totalIncrease = totalIncrease;
	}

	public int getTotalReduction() {
		return totalReduction;
	}

	public void setTotalReduction(int totalReduction) {
		this.totalReduction = totalReduction;
	}

	public int getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(int totalPay) {
		this.totalPay = totalPay;
	}

	public String getAffiliationId() {
		return affiliationId;
	}

	public void setAffiliationId(String affiliationId) {
		this.affiliationId = affiliationId;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getEmplyrNm() {
		return emplyrNm;
	}

	public void setEmplyrNm(String emplyrNm) {
		this.emplyrNm = emplyrNm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
}
