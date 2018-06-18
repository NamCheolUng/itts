<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
function fn_select_user(uniqId){
	document.frm.uniqId.value = uniqId;
	document.frm.action = "<c:url value='/com/member/EgovUserSelectView.do'/>";
	document.frm.submit();
}

function fn_GotoSearch(){
	document.searchForm.pageIndex.value = 1;
	document.searchForm.action = "<c:url value='/com/member/EgovUserManage.do'/>";
	document.searchForm.submit();
}

function fn_egov_select_noticeList(pageNo) {
    document.searchForm.pageIndex.value = pageNo;
	document.searchForm.action = "<c:url value='${tmpPath}/com/member/EgovUserManage.do'/>";
	document.searchForm.submit();
}
</script>

<div id="subwrap">	
		<h1>사원현황</h1>
		<div class = "schgroup">
		<form name="searchForm" method="post">
			<input type="hidden" name="pageIndex" value="<c:out value='${userSearchVO.pageIndex}'/>" />
			<input type="hidden" name="pageUnit" value="${userSearchVO.pageUnit}" />
			<table class ="tablesch">
			<colgroup>
				<col class="width150">
				<col>
			</colgroup>
			<tbody>
			<tr>
			<th>검색</th>
			<td>
		부서: <select class="select width150" name="searchCondition">
			<option value="">전체</option>
		<c:forEach var="result" items="${depart_result}" varStatus="status">		
			<option value='<c:out value="${result.code}"/>' <c:if test="${userSearchVO.searchCondition == result.code}">selected</c:if> > <c:out value="${result.codeNm}"/></option>
		</c:forEach>	
		</select> 
		상태: <select class="select width150" name="searchCondition1">
			<option value="" selected>전체</option>
			<option value="0" <c:if test="${userSearchVO.searchCondition1 == '0'}">selected</c:if>>재직</option>
			<option value="1" <c:if test="${userSearchVO.searchCondition1 == '1'}">selected</c:if>>퇴사</option>
		</select>
		<button type="button" class="btn03" onclick="javascript:fn_GotoSearch();">검색</button>
		</td>
		</tr>
		 </tbody>
		</table>
		</form>
		</div>
		<form name="frm" method="post">
		<input type="hidden" name="uniqId" />
		<table class="tablelist">
			<thead>
				<tr>
					<th>사원번호</th>
					<th>성명</th>
					<th>주민등록번호</th>
					<th>부서</th>
					<th>직급</th>
					<th>입사일자</th>
					<th>계좌번호</th>
					<th>EMAIL</th>
					<th>상태</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
				<tr>
					<td><c:out value="${result.emplNo}"/></td>
					<td><a href="#LINK" onclick="javascript:fn_select_user('<c:out value='${result.uniqId}'/>')"><c:out value="${result.userNm}"/></a></td>
					<td><c:out value="${result.ihidnum}"/></td>
					<td><c:out value="${result.orgnztNm}"/></td>
					<td><c:out value="${result.ofcpsNm}"/></td>
					<td><c:set var= "updateDate" value="${fn:substring(result.emplyrBngde,0,4)}년
													   ${fn:substring(result.emplyrBngde,4,6)}월
													   ${fn:substring(result.emplyrBngde,6,8)}일" />
					<c:out value="${updateDate}"/></td>
					<td><c:out value="${result.bankAccountNum}"/></td>
					<td><c:out value="${result.emailAdres}"/></td>
					<c:if test="${not empty result.emplyrEndde}"><td>퇴사</td></c:if>
					<c:if test="${empty result.emplyrEndde}"><td>재직</td></c:if>				
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="pageNum">
		<div class="pagination">
			<ul class="pagination pagination-sm">
				<ui:pagination paginationInfo="${paginationInfo}" type="ieetu" jsFunction="fn_egov_select_noticeList" />
			</ul>
		</div>
	</div>
		</form>
		<br>
		<div class="btngroup fr">
			<button type="button" class="btn01" onclick="location.href='<c:url value='/com/member/EgovUserInsertView.do'/>' ">사원등록</button>
		</div>	
</div>
