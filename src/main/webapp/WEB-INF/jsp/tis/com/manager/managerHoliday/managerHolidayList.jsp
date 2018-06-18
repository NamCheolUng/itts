<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
	
	function fn_GotoSearch(){
		document.searchForm.actin = "<c:url value='/com/manager/managerHoliday/managerHolidayList.do'/>";
		document.searchForm.submit();
	}
	function fn_GotoDetail(id){
		document.subForm.usid.value = id;
		document.subForm.action = "<c:url value='/com/manager/managerHoliday/managerHolidayDetail.do'/>";
		document.subForm.submit();
	}
</script>
<form name="subForm" method="post">
<input type="hidden" name="usid">
<input type="hidden" name="occrrncYear" value='<c:out value="${nowYear}"></c:out>'>
</form>

<div id="subwrap">
	<h1>연차관리</h1>
	<div class="schgroup">
	<form name="searchForm" method="post">
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
							<select class="select width150" name="occrrncYear">
							<c:forEach var="result" items="${yearList}" varStatus="status">
								<option value='<c:out value="${result.occrrncYear}"/>' <c:if test="${result.occrrncYear == nowYear}"> selected </c:if> ><c:out value="${result.occrrncYear}"/>년</option>
							</c:forEach>
							</select>							
							
							<select class="select width150" name="orgnztId">
								<option value="">전체</option>
							<c:forEach var="result" items="${depart_result}" varStatus="status">
								<option value="${result.code}" <c:if test="${result.code == depart}"> selected </c:if>>${result.codeNm}</option>
							</c:forEach>
							</select>
							<button type="button" class="btn03" onclick="javascript:fn_GotoSearch();">검색</button>
							<div class="btngroup fr">
								<button type="button" class="btn01" onclick="location.href='<c:url value='/com/manager/managerHoliday/managerAllHoliday.do'/>'">전체사원 연차관리</button>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		</form>
	</div>
	<table class="tablelist">
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
				<th>번호</th>
				<th>사원번호</th>
				<th>사원명</th>
				<th>휴가사용/휴가일수</th>
				<th>출근일수/근태일수</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td><c:out value="${status.index +1}"/></td>
				<td><c:out value="${result.emplNo}"/></td>
				<td><a href="javascript:fn_GotoDetail('<c:out value="${result.usid}"/>');"><c:out value="${result.userNm}"/></a></td>
				<td><c:out value="${result.useYrycCo}"/>/<c:out value="${result.occrncYrycCo + result.baseOccrrncCo }"/> </td>
				<td>200/200</td>
				<td></td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
</div>
</body>
</html>