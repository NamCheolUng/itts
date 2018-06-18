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
	document.searchForm.action = "<c:url value='/com/business/company/companyList.do'/>";
	document.searchForm.submit();
}

function fn_GotoView(id){
	document.subForm.adbkId.value = id;
	document.subForm.action = "<c:url value='/com/business/company/companyView.do'/>";
	document.subForm.submit();
}

function fn_egov_select_noticeList(pageNo) {

	document.searchForm.pageIndex.value = pageNo;
	document.searchForm.action = "<c:url value='${tmpPath}/com/business/company/companyList.do'/>";
	document.searchForm.submit();

}
</script>

<form name="subForm" method="post">
<input type="hidden" name="adbkId" >
</form>

<div id="subwrap">
<h1>관련기관/업체관리</h1>
	<div class="schgroup">
	<form name="searchForm" method="post">
		<input type="hidden" name="pageIndex" value="<c:out value='${adbkUserVO.pageIndex}'/>" />
		<input type="hidden" name="pageUnit" value="${adbkUserVO.pageUnit}" />
		<table class="tablesch">
			<colgroup>
				<col class="width194">
				<col>
			</colgroup>			
			<tbody>
				<tr>
					<th>검색</th>
					<td>
						<div class="formgroup">
							<select name="searchCnd" class="select width150">
						     	<option value="" <c:if test="${adbkUserVO.searchCnd == ''}">selected="selected"</c:if>>전체</option>
								<option value="0" <c:if test="${adbkUserVO.searchCnd == '0'}">selected="selected"</c:if>>구분</option>
								<option value="1" <c:if test="${adbkUserVO.searchCnd == '1'}">selected="selected"</c:if>>업체명</option>
						    </select>
							<input type="text" name="searchWrd" class="k-textbox width100p" value="${adbkUserVO.searchWrd}" placeholder="검색어를 입력하세요">
							<button type="button" class="btn03"  onclick="javascript:fn_GotoSearch(1); return false;">검색</button>
							
							<div class="btngroup fr">
								<button type="button" class="btn01" onclick="location.href='<c:url value='/com/business/company/companyInsert.do'/>' ">등록</button>
							</div>
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
			<li><a href="<c:url value='/com/business/company/companyEmplyrList.do'/>">업체사원관리</a></li>
			<li class="active"><a href="<c:url value='/com/business/company/companyList.do'/>">업체관리</a></li>
		</ul>
	</div>
	<table class="tablelist">
		<thead>
			<tr>
				<th>no</th>
				<th>구분</th>
				<th>업체명</th>
				<th>연락처</th>
				<th>팩스</th>
				<th>이메일</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td><c:out value="${paginationInfo.totalRecordCount - ((paginationInfo.currentPageNo-1) * paginationInfo.recordCountPerPage + status.index) }" /></td>
				<td><c:out value="${result.entrprsSeNm}"/></td>
				<td><a href="javascript:fn_GotoView('<c:out value="${result.adbkId}"/>')"><c:out value="${result.cmpnyNm}"/></a></td>
				<td><c:out value="${result.houseTelno}"/></td>
				<td><c:out value="${result.fxnum}"/></td>
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