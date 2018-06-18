package tis.com.epay.service;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class EpayExpHistVO implements Serializable{

	/**지출내역ID*/
	private String expHistId;
	/**지출명세서ID*/
	private String expStatId;
	/**과제ID*/
	private String taskId;
	/**지출일자*/
	private String expDate;
	/**계정과목*/
	private String expSubj;
	/**지출처*/
	private String expPlace;
	/**비고*/
	private String rm;
	/**금액*/
	private String price;
	/**첨부파일ID*/
	private String atchFileId;
	/**사원번호*/
	private String emplNo;
	/**지출기간 검색 시작일*/
	private String searchSdate;
	/**지출기간 검색 종료일*/
	private String searchEdate;
	
	private List<EpayExpHistVO> list;
	
	public String getExpHistId() {
		return expHistId;
	}
	public void setExpHistId(String expHistId) {
		this.expHistId = expHistId;
	}
	public String getExpStatId() {
		return expStatId;
	}
	public void setExpStatId(String expStatId) {
		this.expStatId = expStatId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getExpSubj() {
		return expSubj;
	}
	public void setExpSubj(String expSubj) {
		this.expSubj = expSubj;
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
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	public String getEmplNo() {
		return emplNo;
	}
	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}
	public String getSearchSdate() {
		return searchSdate;
	}
	public void setSearchSdate(String searchSdate) {
		this.searchSdate = searchSdate;
	}
	public String getSearchEdate() {
		return searchEdate;
	}
	public void setSearchEdate(String searchEdate) {
		this.searchEdate = searchEdate;
	}
	public List<EpayExpHistVO> getList() {
		return list;
	}
	public void setList(List<EpayExpHistVO> list) {
		this.list = list;
	}
	
}
