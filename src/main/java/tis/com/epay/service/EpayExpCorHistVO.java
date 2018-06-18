package tis.com.epay.service;

import java.util.List;

public class EpayExpCorHistVO {

	/**지출명세서ID*/
	private String expcorStatId;
	/**카드번호*/
	private String cardNum;
	/**승인일*/
	private String accptDt;
	/**사용인*/
	private String emplNo;
	/**과제ID*/
	private String taskId;
	/**계정과목*/
	private String subjId;
	/**지출처*/
	private String expPlace;
	/**비고*/
	private String rm;
	/**금액*/
	private String price;
	/**회계등록 구분(매입/경비)*/
	private String acuntRegistType;
	
	private List<EpayExpCorHistVO> list;
	
	public String getExpcorStatId() {
		return expcorStatId;
	}
	public void setExpcorStatId(String expcorStatId) {
		this.expcorStatId = expcorStatId;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getAccptDt() {
		return accptDt;
	}
	public void setAccptDt(String accptDt) {
		this.accptDt = accptDt;
	}
	public String getEmplNo() {
		return emplNo;
	}
	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getSubjId() {
		return subjId;
	}
	public void setSubjId(String subjId) {
		this.subjId = subjId;
	}
	public String getExpPlace() {
		return expPlace;
	}
	public void setExpPlace(String expPlace) {
		this.expPlace = expPlace;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAcuntRegistType() {
		return acuntRegistType;
	}
	public void setAcuntRegistType(String acuntRegistType) {
		this.acuntRegistType = acuntRegistType;
	}
	public List<EpayExpCorHistVO> getList() {
		return list;
	}
	public void setList(List<EpayExpCorHistVO> list) {
		this.list = list;
	}
	
}
