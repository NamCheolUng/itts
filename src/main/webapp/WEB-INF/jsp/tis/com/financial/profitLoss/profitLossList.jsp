<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<body>
<div id="subwrap">
	<form name="frm" id="frm" method="post">
	<input type="hidden" name="taskId" value="${taskVO.taskId}">
	<input type="hidden" name="affiliationId" value="${searchVO.affiliationId}">
		
	<div class="schgroup">
		<table class="tablesch">
			<colgroup>
				<col class="width180">
				<col>
				<col class="width180">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>검색</th>
					<td>
						<div class="formgroup">
						<select name="searchMainDept" class="select width150">
							<option value="">전체</option>
							<c:forEach var="dept" items="${deptList}" varStatus="status">
								<option value="${dept.code}" <c:out value="${dept.code == searchVO.searchMainDept ? 'selected' : ''}"/>>${dept.codeNm}</option>
							</c:forEach>
						</select>
						과제시작일
						<input name="searchTaskSdate" id="searchTaskSdate" class="datepicker width120" value="${searchVO.searchTaskSdate}">
						~
						<input name="searchTaskEdate" id="searchTaskEdate" class="datepicker width120" value="${searchVO.searchTaskEdate}">
						&nbsp;&nbsp;
						<select name="searchCondition" class="select width150">
							<option value="" <c:out value="${searchVO.searchCondition == null || searchVO.searchCondition == '' ? 'selected' : ''}"/>>전체</option>
							<option value="taskNm" <c:out value="${searchVO.searchCondition == 'taskNm' ? 'selected' : ''}"/>>과제명</option>
							<option value="cmpnyNm">발주처</option>
						</select>
						<input type="text" name="searchKeyword" class="k-textbox width100p"  value="<c:out value="${searchVO.searchKeyword}"/>" onkeypress="javascript:fn_searchKeyPressed(event);" placeholder="검색어 입력">
						<button type="button" class="btn03" onclick="javascript:fn_requestList(1)">검색</button>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="space10"></div>
	
	<span class="boardnum02 fl"><strong>Total:</strong> ${paginationInfo.totalRecordCount},
	<strong>Page:</strong> ${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}</span>
	
	<div class="space10"></div>

	<table class="tablelist">
		<colgroup>
			<col class="width80">
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col class="width200">
		</colgroup>
		<thead>
			<tr>
				<th rowspan="2">번호</th>
				<th rowspan="2">사업기간</th>
				<th rowspan="2">과제명(건명)</th>
				<th rowspan="2">부서</th>
				<th rowspan="2">발주처</th>
				<th colspan="3">이익</th>
				<th colspan="4">지출</th>
				<th rowspan="2">영업이익</th>
			</tr>
			<tr>
				<th>매출</th>
				<th>영업외이익</th>
				<th>이익합계</th>
				<th>매입</th>
				<th>인건비</th>
				<th>경비</th>
				<th>지출합계</th>
			</tr>
		</thead>
		<tbody>		
			<c:forEach var="result" items="${resultList}" varStatus="status">
				<c:set var="businessProfitColor" value="000000"/>
				<c:if test="${result.businessProfit > 0}">
					<c:set var="businessProfitColor" value="FF0000"/>
				</c:if>
				<c:if test="${result.businessProfit < 0}">
					<c:set var="businessProfitColor" value="0000FF"/>
				</c:if>
			
				<tr>
					<td><c:out value="${paginationInfo.totalRecordCount - ((paginationInfo.currentPageNo-1) * paginationInfo.recordCountPerPage + status.index) }" /></td>
					<td><c:out value="${result.taskSdate}"/> ~ <c:out value="${result.taskEdate}"/></td>
					<td><a href="javascript:fn_viewDetail('${result.taskId}');"><c:out value="${result.taskNm}"/></a></td>
					<td><c:out value="${result.deptNm}"/></td>
					<td><c:out value="${result.cmpnyNm}"/></td>
					<td><fmt:formatNumber value="${result.salesExecTotPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${result.salesOthersPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${result.profitTotPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${result.prcsExecTotPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${result.laborExecPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${result.outgoingsPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${result.outgoingsTotPrice}" pattern="#,###"/></td>
					<td><font color="${businessProfitColor}"><fmt:formatNumber value="${result.businessProfit}" pattern="#,###"/></font></td>
				</tr>
			</c:forEach>
			<!-- 데이터가 없을때 화면에 메세지를 출력해준다 -->	
			<c:if test="${empty resultList}">
				<tr> 
					<td colspan="100%">
						<spring:message code="common.nodata.msg" />
					</td>
				</tr>   	          				 			   
			</c:if>
		</tbody>
	</table>
	
	<div class="space20"></div>
	
	<div class="pageNum">
		<div class="pagination">
			<ul class="pagination pagination-sm">
				<ui:pagination paginationInfo="${paginationInfo}" type="ieetu" jsFunction="fn_requestList" />
			</ul>
		</div>
		<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
	</div>
	
	</form>
</div>
</body>

<script>
$(document).ready(function() {
	fn_initSearchDatePicker("searchTaskSdate", "searchTaskEdate");
});

function fn_requestList(pageNo) {
	document.frm.pageIndex.value = pageNo;
	document.frm.action = "<c:url value='/com/financial/profitLoss/profitLossList.do'/>";
	document.frm.submit();
}

function fn_searchKeyPressed(event) {
	if (event.keyCode==13) {
		fn_requestList(1);
	}
}

function fn_viewDetail(taskId) {
	document.frm.taskId.value = taskId;
	document.frm.action = "<c:url value='/com/financial/profitLoss/profitLossDetail.do'/>";
	document.frm.submit();
}
</script>