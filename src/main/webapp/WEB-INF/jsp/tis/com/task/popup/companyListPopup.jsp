<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<meta charset="utf-8">
<title>(주)이튜 - 통합정보시스템</title>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/common.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/layout.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/board.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/fontawesome-all.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/kendo.common.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/kendo.material.min.css'/>">

<script type="text/javascript" src="<c:url value='/js/tis/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/tis/kendo.all.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/tis/bootstrap.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/tis/common.js'/>"></script>
<script type="text/javascript">
	function fn_search(){
		document.frm.action="<c:url value='/com/task/popup/companyListPopup.do'/>";
		document.frm.submit();
	}
	
	function fn_selectCompany(id,nm){
		window.opener.fn_returnCompanyPopup(id,nm);
		window.close();
	}
</script>
<form name="frm" method="post">
	
	<div class="schgroup">
		<select class="select" name="searchCondition">
			<option value="" <c:if test="${empty taskVO.searchCondition }">selected="selected"</c:if>>선택</option>
			<option value="1" <c:if test="${taskVO.searchCondition eq '1'}">selected="selected"</c:if>>소속기관</option>
			<option value="2" <c:if test="${taskVO.searchCondition eq '2'}">selected="selected"</c:if>>사업자등록번호</option>
		</select>
		<input type="text" class="k-textbox" name="searchKeyword" value="<c:out value='${taskVO.searchKeyword }'/>"/>
		<button type="button" class="btn03" onclick="javascript:fn_search();">검색</button>
	</div>
</form>
<div class="space10"></div>	
<%-- <span class="boardnum fl"><strong>Total:</strong> <c:out value='${paginationInfo.totalRecordCount }'/>, <strong>Page:</strong> <c:out value='${paginationInfo.currentPageNo }'/>/<c:out value='${paginationInfo.totalPageCount }'/></span> --%>		
<div class="space30"></div>
<table class="tablelist">
	<colgroup>
		<col class="width80">
		<col class="width150">
		<col class="width150">
		<col>
		<col class="width150">
	</colgroup>
	<thead>
		<tr>
			<th>No</th>
			<th>구분</th>
			<th>기관/업체</th>
			<th>사업자등록번호</th>
			<th>비고</th>				
		</tr>		
	</thead>
	<tbody>
		<c:forEach var="result" items="${resultList }" varStatus="status">
			<tr onclick="javascript:fn_selectCompany('<c:out value='${result.adbkId }'/>','<c:out value='${result.cmpnyNm }'/>');">
				<td><c:out value='${status.count }'/></td>
				<td>
					<c:choose>
						<c:when test="${result.entrprsSeCode eq '1'}">
							연구계
						</c:when>
						<c:when test="${result.entrprsSeCode eq '2'}">
							기관
						</c:when>
						<c:when test="${result.entrprsSeCode eq '3'}">
							산업계
						</c:when>
						<c:when test="${result.entrprsSeCode eq '4'}">
							학계
						</c:when>
					</c:choose>
				</td>
				<td><c:out value='${result.cmpnyNm }'/></td>
				<td><c:out value='${result.bizrno }'/></td>
				<td><c:out value='${result.rm }'/></td>
			</tr>
		</c:forEach>
				
	</tbody>
</table>	   
