<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">
function fn_GotoView(item){
	document.searchForm.itemCode.value = item;
	document.searchForm.action = "<c:url value='/com/business/item/itemView.do'/>";
	document.searchForm.submit();
}

function fn_GotoSearch(){
	document.searchForm.pageIndex.value = 1;
	document.searchForm.action = "<c:url value='${tmpPath}/com/business/item/itemList.do'/>";
	document.searchForm.submit();
}
function fn_egov_select_noticeList(pageNo) {
	document.searchForm.pageIndex.value = pageNo;
	document.searchForm.action = "<c:url value='${tmpPath}/com/business/item/itemList.do'/>";
	document.searchForm.submit();
}
</script>

<div id="subwrap">
<h1>공용품관리</h1>
	<div class="schgroup">
	<form name="searchForm" method="post">
		<input type="hidden" name="itemCode">
		<input type="hidden" name="pageIndex" value="<c:out value='${tbItemmanageVO.pageIndex}'/>" />
		<input type="hidden" name="pageUnit" value="${tbItemmanageVO.pageUnit}">
		<table class="tablesch">
			<colgroup>
				<col class="width150">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>검색</th>
					<td>	
						<select class="select width150" name="searchCondition">
							<option value="">전체</option>
							<option value="0" <c:if test="${tbItemmanageVO.searchCondition == '0'}">selected</c:if> >공용품명</option>
							<option value="1" <c:if test="${tbItemmanageVO.searchCondition == '1'}">selected</c:if> >물품코드</option>
						</select>
						등록일자: <input name="bgndt" id="bgndt" class="datepicker width200" value='<c:out value="${bgndt}"/>'><span> ~ </span>
						<input name="enddt" id="enddt" class="datepicker width200" value='<c:out value="${enddt}"/>'>&nbsp;&nbsp;&nbsp;
						<input type="text" placeholder="검색어 입력" class="k-textbox width10p" name="searchKeyword" value='<c:out value="${tbItemmanageVO.searchKeyword}"/>'>
						<button type="button" class="btn03" onclick="javascript:fn_GotoSearch();">검색</button>
						<div class="btngroup fr">		
							<a href="#" data-toggle="modal" data-target="#modal09"><button type="button" class="btn01">신규</button></a>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	</div>
	<table class="tablelist">
		<thead>
			<tr>
				<th>물품코드</th>
				<th>공용품명</th>
				<th>등록자</th>
				<th>등록일</th>
				<th>위치</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td><a href="javascript:fn_GotoView('<c:out value="${result.itemCode}"/>')"><c:out value="${result.itemCode}"/></a></td>
				<td><c:out value="${result.itemName}"/></td>
				<td>
				<c:forEach var="empresult" items="${emplyr_list}" varStatus="status">
				<c:if test="${empresult.emplNo == result.emplyrNo }">
					<c:out value="${empresult.userNm}"/>
				</c:if>
				</c:forEach>
				</td>
				<td><c:set var= "insertDate" value="${fn:substring(result.insertDt,0,10) }" />
				<c:out value="${insertDate}"/></td>
				<td><c:out value="${result.location}"/></td>
				<td><c:out value="${result.rm}"/></td>
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
	<div class="pageNum">
		<div class="pagination">
			<ul class="pagination pagination-sm">
				<ui:pagination paginationInfo="${paginationInfo}" type="ieetu" jsFunction="fn_egov_select_noticeList" />
			</ul>
		</div>
	</div>
</div>
</body>
<script>
$(document).ready(function() {
	fn_initSearchDatePicker("bgndt", "enddt");
});
</script>
</html>