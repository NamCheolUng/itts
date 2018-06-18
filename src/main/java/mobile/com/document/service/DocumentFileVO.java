package mobile.com.document.service;

import java.io.Serializable;

public class DocumentFileVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*첨부파일 아이디*/
	private String atchFileId = "";

	/*사원번호*/
	private String emplNo = "";
	
	/*생성일자*/
	private String createDt = "";
	
	/*파일타입구분*/
	private String fileType = "";
	//오리지날 파일아이디
	private String orginFileId = "";
	
	
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

	public String getCreateDt() {
		return createDt;
	}

	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getOrginFileId() {
		return orginFileId;
	}

	public void setOrginFileId(String orginFileId) {
		this.orginFileId = orginFileId;
	}

	
}
