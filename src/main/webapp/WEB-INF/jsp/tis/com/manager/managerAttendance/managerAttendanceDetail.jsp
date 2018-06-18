<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
	$(document).ready(function() {
		$("#monthpicker2").kendoDatePicker({
			start : "year",
			depth : "year",
			format : "yyyy/MM",
			dateInput : true
		});
	});
	
	function fn_GotoSearch(){		
		document.searchForm.wrktDt.value = document.searchForm.mowMM.value.replace(/\//gi, "");
		document.searchForm.action = "<c:url value='/com/manager/managerAttendance/managerAttendanceDetail.do'/>";
		document.searchForm.submit();
	}

	function fn_GotoModify(dt){		
		document.subForm.wrktDt.value = dt;
		document.subForm.action = "<c:url value='/com/manager/managerAttendance/managerAttendanceModify.do'/>";
		document.subForm.submit();
	}	
	
</script>

<form name="subForm" method="post">
	<input type="hidden" name="emplyrId" value='<c:out value="${userManageVO.uniqId}"/>'>
	<input type="hidden" name="wrktDt">
</form>


<div id="subwrap">
	<h1>근태관리 > 상세</h1>
	<div class="schgroup">
	<form name="searchForm" method="post">
	<input type="hidden" name="emplyrId" value='<c:out value="${userManageVO.uniqId}"/>'>
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
					<td><c:out value='${userManageVO.emplyrNm}'/></td>
					<th>일자</th>
					<td>
						<div class="formgroup">
							<input class="datepicker width150" id="monthpicker2" name="mowMM" value="<c:out value='${nowMM}'/>">&nbsp;	
							<button type="button" class="btn03" onclick="javascript:fn_GotoSearch();">검색</button>						
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	</div>
	<table class="tablelist">
		<colgroup>
			<col class="width80">
			<col class="width250">
			<col>
			<col>
			<col class="width150">
			<col class="width150">
			<col class="width150">
			<col class="width400">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>일자</th>
				<th>출근시간</th>
				<th>퇴근시간</th>
				<th>구분</th>
				<th>사유</th>
				<th>수정일</th>
				<th>수정사유</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td><c:out value="${status.index +1}"/></td>
				<td><a href="javascript:fn_GotoModify('<c:out value="${result.wrktDt}"/>');"><c:out value="${result.wrktDt}"/></a></td>
				<td><c:out value="${result.wrkStartTime}"/></td>
				<td><c:out value="${result.wrkEndTime}"/></td>
				<td>
				<c:choose>
				<c:when test="${result.wrkStartStatus != null || result.wrkEndStatus != null }">
					<c:out value="${result.wrkStartStatus}"/>
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
				<td><c:set var= "updateDate" value="${fn:substring(result.lastUpdtPnttn,0,10) }" />
				<c:out value="${updateDate}"/></td>
				<td><c:out value="${result.modifyReason}"/></td>
			</tr>
		</c:forEach>	
		</tbody>
					<!-- 데이터가 없을때 화면에 메세지를 출력해준다 -->	
			<c:if test="${empty resultList}">
				<tr> 
					<td colspan="100%">
						<spring:message code="common.nodata.msg" />
					</td>
				</tr>   	          				 			   
			</c:if>
	</table>
</div>
<script>
$(document).ready(function() {
	$("input[name=mowMM]").attr("readonly",true);
});
</script>
</body>
</html>