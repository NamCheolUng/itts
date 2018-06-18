package tis.com.epay.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EpayHoliday implements Serializable{

	/** 휴가원정보ID */
	private String holidayId;
	/** 기안정보ID */
	private String draftInfoId;
	/** 휴가 시작일 */
	private String holSdate;
	/** 휴가 종료일 */
	private String holEdate;
	/** 휴가일수 */
	private String holDay;
	/** 휴가구분 */
	private String holTy;
	/** 사유 */
	private String holRs;
	/** 비고 */
	private String holRm;
	/** 비상연락처 */
	private String emergNum;
	/** 첨부파일ID*/
	private String atchFileId;
	
	public String getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}
	public String getDraftInfoId() {
		return draftInfoId;
	}
	public void setDraftInfoId(String draftInfoId) {
		this.draftInfoId = draftInfoId;
	}
	public String getHolSdate() {
		return holSdate;
	}
	public void setHolSdate(String holSdate) {
		this.holSdate = holSdate;
	}
	public String getHolEdate() {
		return holEdate;
	}
	public void setHolEdate(String holEdate) {
		this.holEdate = holEdate;
	}
	public String getHolDay() {
		return holDay;
	}
	public void setHolDay(String holDay) {
		this.holDay = holDay;
	}
	public String getHolTy() {
		return holTy;
	}
	public void setHolTy(String holTy) {
		this.holTy = holTy;
	}
	public String getHolRs() {
		return holRs;
	}
	public void setHolRs(String holRs) {
		this.holRs = holRs;
	}
	public String getHolRm() {
		return holRm;
	}
	public void setHolRm(String holRm) {
		this.holRm = holRm;
	}
	public String getEmergNum() {
		return emergNum;
	}
	public void setEmergNum(String emergNum) {
		this.emergNum = emergNum;
	}
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	
}
