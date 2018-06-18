package tis.com.epay.service;

public class EpayDraftInfoSearchVO {

	/** 검색 - 업체구분 */
	private String searchComFlag;
	
	/** 문서양식조건*/
	private String searchDocFormCnd;
	
	/** 문서양식조건*/
	private String searchDocForm;
	
	/** 문서양식조건(전체)*/
	private String searchDocFormAll;
	
	/** 결재상태조건*/
	private String searchApprSttusCnd;
	
	/** 결재상태조건*/
	private String searchApprSttus;
	
	/** 결재상태조건(전체)*/
	private String searchApprSttusAll;

	/** 결재부서조건*/
	private String searchDeptCode;
	
	/** 결재부서조건(전체)*/
	private String searchDeptCodeAll;
	
	/** 검색조건 */
	private String searchCnd;
	
	/** 검색단어 */
	private String searchWrd;
	
	/** 시작검색일 */
	private String searchSdate;
	
	/** 종료검색일 */
	private String searchEdate;
	
    /** 현재페이지 */
    private int pageIndex = 1;
    
    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;
    
    /** 정렬컬럼 */
    private String sortColumn = "A.REG_DT";
    
    /** 정렬순서 */
    private String sortOrderBy = "DESC";
	
	public String getSearchComFlag() {
		return searchComFlag;
	}

	public void setSearchComFlag(String searchComFlag) {
		this.searchComFlag = searchComFlag;
	}

	public String getSearchDocFormCnd() {
		return searchDocFormCnd;
	}

	public void setSearchDocFormCnd(String searchDocFormCnd) {
		this.searchDocFormCnd = searchDocFormCnd;
	}

	public String getSearchDocForm() {
		return searchDocForm;
	}

	public void setSearchDocForm(String searchDocForm) {
		this.searchDocForm = searchDocForm;
	}

	public String getSearchDocFormAll() {
		return searchDocFormAll;
	}

	public void setSearchDocFormAll(String searchDocFormAll) {
		this.searchDocFormAll = searchDocFormAll;
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

	public String getSearchApprSttusAll() {
		return searchApprSttusAll;
	}

	public void setSearchApprSttusAll(String searchApprSttusAll) {
		this.searchApprSttusAll = searchApprSttusAll;
	}

	public String getSearchDeptCode() {
		return searchDeptCode;
	}

	public void setSearchDeptCode(String searchDeptCode) {
		this.searchDeptCode = searchDeptCode;
	}

	public String getSearchDeptCodeAll() {
		return searchDeptCodeAll;
	}

	public void setSearchDeptCodeAll(String searchDeptCodeAll) {
		this.searchDeptCodeAll = searchDeptCodeAll;
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

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
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
}
