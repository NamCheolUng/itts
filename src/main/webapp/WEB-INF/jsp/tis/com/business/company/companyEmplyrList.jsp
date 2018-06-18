<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">
function fn_GotoSearch(){
	document.searchForm.pageIndex.value = 1;
	document.searchForm.action = "<c:url value='/com/business/company/companyEmplyrList.do'/>";
	document.searchForm.submit();
}

function fn_GotoDetail(id){
	document.subForm.ncrdId.value = id;
	document.subForm.action = "<c:url value='/com/business/company/companyEmplyrView.do'/>";
	document.subForm.submit();
}

function fn_egov_select_noticeList(pageNo) {

	document.searchForm.pageIndex.value = pageNo;
	document.searchForm.action = "<c:url value='${tmpPath}/com/business/company/companyEmplyrList.do'/>";
	document.searchForm.submit();

}

</script>

<form name="subForm" method="post">
<input type="hidden" name="ncrdId">
</form>


<div id="subwrap">
<h1>관련기관/업체사원관리</h1>
	<div class="schgroup">
	<form name="searchForm" method="post">
		<input type="hidden" name="pageIndex" value="<c:out value='${ncrdVO.pageIndex}'/>" />
		<input type="hidden" name="pageUnit" value="${ncrdVO.pageUnit}" />
		<table class="tablesch">
			<colgroup>
				<col class="width150">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>검색</th>
					<td>	
						<select class="select width150" name="searchCnd">
							<option value="">전체</option>
							<option value="1" <c:if test="${ncrdVO.searchCnd == '1'}">selected</c:if> >구분</option>
							<option value="2" <c:if test="${ncrdVO.searchCnd == '2'}">selected</c:if> >소속기관</option>
							<option value="3" <c:if test="${ncrdVO.searchCnd == '3'}">selected</c:if> >이름</option>
						</select>
						<input type="text" name="searchWrd" placeholder="검색어 입력" class="k-textbox width10p" value='<c:out value="${ncrdVO.searchWrd }"/>' >
						<button type="button" class="btn03" onclick="javascript:fn_GotoSearch()">검색</button>
						
						<div class="btngroup fr">
							<button type="button" class="btn01" onclick="location.href='<c:url value='/com/business/company/companyEmplyrInsert.do'/>' ">등록</button>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>	
	</div>
	<div class="space20"></div>
	<div class="tabmenu">
		<ul>
			<li class="active"><a href="<c:url value='/com/business/company/companyEmplyrList.do'/>">업체사원관리</a></li>
			<li><a href="<c:url value='/com/business/company/companyList.do'/>">업체관리</a></li>
		</ul>
	</div>
	<table class="tablelist">
		<thead>
			<tr>
				<th>no</th>
				<th>구분</th>
				<th>소속기관</th>
				<th>부서</th>
				<th>직위</th>
				<th>이름</th>
				<th>연락처</th>
				<th>이메일</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td><c:out value="${paginationInfo.totalRecordCount - ((paginationInfo.currentPageNo-1) * paginationInfo.recordCountPerPage + status.index) }" /></td>
				<td><c:out value="${result.idntfcNm}"/></td>
				<td><c:out value="${result.cmpnyNm}"/></td>
				<td><c:out value="${result.deptNm}"/></td>
				<td><c:out value="${result.ofcpsNm}"/></td>
				<td><a href="javascript:fn_GotoDetail('<c:out value="${result.ncrdId}"/>');"><c:out value="${result.ncrdNm}"/></a></td>
				<td><c:out value="${result.telno}"/></td>
				<td><c:out value="${result.emailAdres}"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pageNum">
        <ul class="pagination pagination-sm">
			<ui:pagination paginationInfo="${paginationInfo}" type="ieetu" jsFunction="fn_egov_select_noticeList" />
		</ul>
    </div>
</div>
</body>
</html>