<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script type="text/javascript">

function fn_GotoSearch(){

	document.searchForm.wrktDt.value = document.searchForm.wrkDt.value.replace(/\//gi,"");
	document.searchForm.actin = "<c:url value='/com/manager/managerAttendance/managerAttendanceList.do'/>";
	document.searchForm.submit();
}

function fn_GotoDetail(id){
	document.subForm.emplyrId.value = id;
	document.subForm.action = "<c:url value='/com/manager/managerAttendance/managerAttendanceDetail.do'/>";
	document.subForm.submit();
}
</script>

<form name="subForm" method="post">
<input type="hidden" name="emplyrId">
<input type="hidden" name="wrktDt">
</form>

<div id="subwrap">
	<h1>근태관리</h1>
	<div class="schgroup">
	<form name="searchForm" method="post">
	<input type="hidden" name="wrktDt">
		<table class="tablesch">
			<colgroup>
				<col class="width150">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>검색</th>
					<td>
						<div class="formgroup">
							<input class="datepicker width150" name="wrkDt" id="wrkDt" value="<c:out value='${nowDate}'/>">
						 부서 
						<select class="select width150" name="orgnztId">
								<option value="">전체</option>
							<c:forEach var="result" items="${depart_result}" varStatus="status">
								<option value="${result.code}" <c:if test="${result.code == depart}"> selected </c:if>>${result.codeNm}</option>
							</c:forEach>
						</select>
						<button type="button" class="btn03" onclick="javascript:fn_GotoSearch();">검색</button>
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
				<th>번호</th>
				<th>일자</th>
				<th>사원명</th>
				<th>출근시간</th>
				<th>퇴근시간</th>
				<th>구분</th>
				<th>사유</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td><c:out value="${status.index +1}"/></td>
				<td><c:out value='${result.wrktDt}'/></td>
				<td><a href="javascript:fn_GotoDetail('<c:out value="${result.emplyrId}"/>', );"><c:out value="${result.userNm}"/></a></td>
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
<script>
$(document).ready(function() {
	$("input[name=wrkDt]").attr("readonly",true);
});
</script>
</html>