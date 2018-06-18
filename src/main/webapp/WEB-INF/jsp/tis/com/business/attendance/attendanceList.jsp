<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">
$(document).ready(function(){
	$("#mowMM").kendoDatePicker({
		start : "year",
		depth : "year",
		format : "yyyy/MM"
	});
});

$("input[name=mowMM]").attr("readonly",true);

function fn_GotoSearch(){
	document.searchForm.wrktDt.value = document.searchForm.mowMM.value.replace(/\//gi, "");
	document.searchForm.action = "<c:url value='/com/business/attendance/attendanceList.do'/>";
	document.searchForm.submit();
}

</script>

<div id="subwrap">
	<h1>근태관리</h1>
	<br>
	<form name="searchForm" method="post">
	<input type="hidden" name="emplyrId" value='<c:out value="${loginVO.uniqId}"/>'>
	<input type="hidden" name="wrktDt"> 
		<table class="tablesch">
			<colgroup>
				<col class="width150">
				<col>
				<col class="width150">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>성명</th>
					<td><c:out value='${loginVO.name}'/></td>
					<th>일자</th>
					<td>
						<div class="formgroup">
							<input class="datepicker width150" id="mowMM" name="mowMM" value="<c:out value='${nowMM}'/>">&nbsp;	
							<button type="button" class="btn03" onclick="javascript:fn_GotoSearch();">검색</button>						
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<table class="tablelist">
		<thead>
			<tr>
				<th>일자</th>
				<th>출근시간</th>
				<th>퇴근시간</th>
				<th>구분</th>
				<th>사유</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td><c:out value="${result.wrktDt}"/></td>
				<td><c:out value="${result.wrkStartTime}"/></td>
				<td><c:out value="${result.wrkEndTime}"/></td>
				<td>
				<c:choose>
				<c:when test="${result.wrkStartStatus != null || result.wrkEndStatus != null }">
					<c:if test="${result.wrkStartStatus != '정상' }"><c:out value="${result.wrkStartStatus}"/></c:if> 
					<c:if test="${result.wrkEndStatus != '정상' }"><c:out value="${result.wrkEndStatus}"/></c:if>
				</c:when>
				<c:when test="${result.wrkStartTime == null && result.wrkEndTime == null}">
					결근
				</c:when>
				</c:choose>
				<c:if test="${result.outWrkStime != null}">출장 </c:if>
				</td>
				<td>
				<c:choose>
				<c:when test="${result.rm != null && not empty result.rm }">
					<c:out value="${result.rm}"/>
				</c:when>
				<c:when test="${result.dow == 7 || result.dow == 1 }">
					 휴일
				</c:when>
				<c:when test="${not empty result.vct}">
					<c:out value="${result.vct}"/>
				</c:when>
				</c:choose>
				</td>
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
</div>
</body>
</html>