package tis.com.epay.service;

@SuppressWarnings("serial")
public class EpayApprInfoVO extends EpayDraftInfo{

	private String searchDocForm;

	/** 문서양식조건*/
	private String searchDocFormCnd;
	
	/** 부서조건*/
	private String searchDeptCode;
	
	/** 결재상태(문서상태)조건*/
	private String searchApprSttusCnd;
	
	/** 결재상태(문서상태)조건*/
	private String searchApprSttus;
	
	/** 검색조건 */
	private String searchCnd = "";
	
	/** 검색키워드 */
	private String searchWrd = "";
	
	/** 시작검색일 */
	private String searchSdate;
	
	/** 종료검색일 */
	private String searchEdate;

	/** 페이지갯수 */
	private int pageUnit = 20;
	
	/** 페이지사이즈 */
	private int pageSize = 10;
	
	/** 현재페이지 */
	private int pageIndex = 1;
	
	/** firstIndex */
	private int firstIndex = 1;
	
	/** lastIndex */
	private int lastIndex = 1;
	
	/** recodeCountPerPage */
	private int recordCountPerPage = 10;
	
	/** 정렬컬럼 */
    private String sortColumn = "";

	/** 정렬순서 */
    private String sortOrderBy = "";
    
    // --------------------------------
    /** 결재상태 */
    /*private String apprSttus;*/
	/** 문서양식명 */
    private String docFormNm;
    /** 제목 */
    private String title;
    /** 결재요청일 */
    private String regDt;
    /** 작성자명 */
    private String userNm;
    /** 부서명 */
    private String orgnztNm;
    /**결재라인부서코드*/
    private String code;
    /**결재라인부서코드명*/
    private String codeNm;
    
    private String emplNo;
	
    public String getSearchDocForm() {
		return searchDocForm;
	}

	public void setSearchDocForm(String searchDocForm) {
		this.searchDocForm = searchDocForm;
	}

	public String getSearchDocFormCnd() {
		return searchDocFormCnd;
	}

	public void setSearchDocFormCnd(String searchDocFormCnd) {
		this.searchDocFormCnd = searchDocFormCnd;
	}

	public String getSearchDeptCode() {
		return searchDeptCode;
	}

	public void setSearchDeptCode(String searchDeptCode) {
		this.searchDeptCode = searchDeptCode;
	}

	public String getSearchApprSttusCnd() {
		return searchApprSttusCnd;
	}

	public void setSearchApprSttusCnd(String searchApprSttusCnd) {
		this.searchApprSttusCnd = searchApprSttusCnd;
	}

	public String getSearchApprSttus() {
		return searchApprSttus;
	}

	public void setSearchApprSttus(String searchApprSttus) {
		this.searchApprSttus = searchApprSttus;
	}

	public String getSearchCnd() {
		return searchCnd;
	}

	public void setSearchCnd(String searchCnd) {
		this.searchCnd = searchCnd;
	}

	public String getSearchWrd() {
		return searchWrd;
	}

	public void setSearchWrd(String searchWrd) {
		this.searchWrd = searchWrd;
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

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
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
	
	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrderBy() {
		return sortOrderBy;
	}

	public void setSortOrderBy(String sortOrderBy) {
		this.sortOrderBy = sortOrderBy;
	}
	/*public String getApprSttus() {
		return apprSttus;
	}

	public void setApprSttus(String apprSttus) {
		this.apprSttus = apprSttus;
	}*/

	public String getDocFormNm() {
		return docFormNm;
	}

	public void setDocFormNm(String docFormNm) {
		this.docFormNm = docFormNm;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getOrgnztNm() {
		return orgnztNm;
	}

	public void setOrgnztNm(String orgnztNm) {
		this.orgnztNm = orgnztNm;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCodeNm() {
		return codeNm;
	}

	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}

	public String getEmplNo() {
		return emplNo;
	}

	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}
}
