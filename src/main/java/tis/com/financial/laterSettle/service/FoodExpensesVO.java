package tis.com.financial.laterSettle.service;

import java.io.Serializable;
import java.util.List;

public class FoodExpensesVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*사원번호*/
	private String emplNo = "";
	
	/*식사일자*/
	private String foodDt = "";

	/*사원명*/
	private String emplNm = "";

	/*정산일*/
	private String paymentDt = "";

	/*식사횟수*/
	private int foodCnt;

	/*금액*/
	private int cost;
	
	/*회사구분*/
	private String affiliationId = "";

	/*비고*/
	private String rm = "";

	/*이메일*/
	private String emailAdres = "";
	
	/*다중삭제*/
	private List<FoodExpensesVO> foodExpensesList;
	
	private String chk;
	
	public String getEmplNo() {
		return emplNo;
	}

	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}

	public String getFoodDt() {
		return foodDt;
	}

	public void setFoodDt(String foodDt) {
		this.foodDt = foodDt;
	}

	public String getEmplNm() {
		return emplNm;
	}

	public void setEmplNm(String emplNm) {
		this.emplNm = emplNm;
	}

	public String getPaymentDt() {
		return paymentDt;
	}

	public void setPaymentDt(String paymentDt) {
		this.paymentDt = paymentDt;
	}

	public int getFoodCnt() {
		return foodCnt;
	}

	public void setFoodCnt(int foodCnt) {
		this.foodCnt = foodCnt;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
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

	public List<FoodExpensesVO> getFoodExpensesList() {
		return foodExpensesList;
	}

	public void setFoodExpensesList(List<FoodExpensesVO> foodExpensesList) {
		this.foodExpensesList = foodExpensesList;
	}

	public String getChk() {
		return chk;
	}

	public void setChk(String chk) {
		this.chk = chk;
	}

	public String getEmailAdres() {
		return emailAdres;
	}

	public void setEmailAdres(String emailAdres) {
		this.emailAdres = emailAdres;
	}
}
