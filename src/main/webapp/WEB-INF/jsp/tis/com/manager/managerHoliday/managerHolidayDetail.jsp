<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
$(document).ready(function() {
$("#yearpicker").kendoDatePicker({
    start: "decade",
    depth: "decade",
    format: "yyyy",
    dateInput: true
});
});
function fn_goList() {
	location.href = "<c:url value='${tmpPath}/com/manager/managerHoliday/managerHolidayList.do'/>";
}

function fn_GotoSearch(){	
	document.searchForm.action = "<c:url value='/com/manager/managerHoliday/managerHolidayDetail.do'/>";
	document.searchForm.submit();
}

</script>
<div id="subwrap">
	<h1>연차관리 > 상세</h1>
	<div class="schgroup">
	<form name="searchForm" method="post">
	<input type="hidden" name="usid" value='<c:out value="${vacCnt.usid}"/>'>
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
					<td><c:out value="${vacCnt.applcntNm}"/></td>
					<th>일자</th>
					<td>
						<select class="select width150" name="occrrncYear">
							<c:forEach var="result" items="${yearList}" varStatus="status">
							<option value='<c:out value="${result.occrrncYear}"/>' <c:if test="${result.occrrncYear == nowYear}"> selected </c:if> ><c:out value="${result.occrrncYear}"/>년</option>
							</c:forEach>
						</select> 
						<button type="button" class="btn03" onclick="javascript:fn_GotoSearch();">검색</button>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	</div>
	<div class="space20"></div>
	<div class="btngroup fr">
		<button type="button" class="btn04" onClick="javascript:fn_goList();">목록</button>		
	</div>
	<table class="tablelist">
	<c:set var = "sumvcattotal" value = "${0}"/>
	<c:set var = "sumvcatcnt" value = "${0}"/>
	<c:set var = "sumremndr" value = "${vacCnt.baseOccrrncCo + vacCnt.occrncYrycCo}"/>
		<colgroup>
			<col class="width50">
			<col class="width100">
			<col class="width100">
			<col class="width100">
			<col class="width100">
			<col class="width200">
		</colgroup>
		<thead>
			<tr>
				<th>일자</th>
				<th>구분</th>
				<th>휴가가산일수</th>
				<th>휴가일수</th>
				<th>휴가차감일수</th>
				<th>휴가잔여일수</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><c:out value="${vacCnt.frstRegistPnttm}"/></td>
				<td>기본휴가</td>
				<td><c:out value="${vacCnt.baseOccrrncCo}"/></td>
				<td>0.0</td>
				<td>0.0</td>
				<td><c:out value="${vacCnt.baseOccrrncCo}"/></td>
			</tr>
			<tr>
				<td>
				<c:if test="${vacCnt.baseOccrrncCo == 0}">
					<c:out value="${vacCnt.lastUpdtPnttm}"/>
				</c:if>
				<c:if test="${vacCnt.baseOccrrncCo != 0}">
					<c:out value="${vacCnt.frstRegistPnttm}"/>
				</c:if>
				</td>
				<td>근속가산휴가</td>
				<td><c:out value="${vacCnt.occrncYrycCo}"/></td>
				<td>0.0</td>
				<td>0.0</td>
				<td><c:out value="${vacCnt.baseOccrrncCo + vacCnt.occrncYrycCo}"/></td>
			</tr>
			<c:forEach var="result" items="${vacList}" varStatus="status">
			<tr>
				<td><c:out value="${result.bgnde}"/></td>
				<td>
				<c:forEach var="vse" items="${vcatnSeCode}">
					<c:if test="${vse.code == result.vcatnSe }">
						<c:out value="${ vse.codeNm }"/>
					</c:if>				
				</c:forEach>
				</td>
				<td>0.0</td>
				<td><c:out value="${result.vcatnTotalcnt}"/></td>
				<c:set var = "sumvcattotal" value = "${sumvcattotal + result.vcatnTotalcnt}"/>
				<td>-<c:out value="${result.vcatnCnt}"/></td>
				<c:set var = "sumvcatcnt" value = "${sumvcatcnt - result.vcatnCnt}"/>
				<c:set var = "sumremndr" value = "${sumremndr - result.vcatnCnt}"/>
				<td><c:out value="${sumremndr}"/></td>
			</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td></td>
				<td>합계</td>
				<td><c:out value="${vacCnt.baseOccrrncCo + vacCnt.occrncYrycCo}"/></td>
				<td><c:out value="${sumvcattotal}"/></td>
				<td><c:out value="${sumvcatcnt}"/></td>
				<td><c:out value="${sumremndr}"/></td>
			</tr>
		</tfoot>
	</table>
</div>
</body>
</html>