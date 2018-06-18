<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
	function fn_goList() {
		location.href = "<c:url value='${tmpPath}/com/manager/managerHoliday/managerHolidayList.do'/>";
	}
	function fn_GotoDelete(){
		document.subForm.action = "<c:url value='/com/manager/managerHoliday/managerAllHolidayDelete.do'/>";
		document.subForm.submit();
	}
</script>
<div id="subwrap">
	<h1>연차관리 > 전체사원연차관리</h1>
	<div class="schgroup">
		<table class="tablesch">
			<colgroup>
				<col class="width150">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>검색</th>
					<td>	
						<select class="select width150" name="occrrncYear">
							<c:forEach var="result" items="${yearList}" varStatus="status">
								<option value='<c:out value="${result.occrrncYear}"/>' <c:if test="${result.occrrncYear == nowYear}"> selected </c:if> ><c:out value="${result.occrrncYear}"/>년</option>
							</c:forEach>
						</select> 
						<button type="button" class="btn03">검색</button>
						<div class="btngroup fr">
							<button type="button" class="btn04" onClick="javascript:fn_goList();">목록</button>
							<button type="button" class="btn02" onclick="javascript:fn_GotoDelete();">삭제</button>
							<a href="#" data-toggle="modal" data-target="#modal14"><button type="button" class="btn01" onclick="test()">추가</button></a>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="space20"></div>
	<h2>연차리스트 <small>(총: 3일)</small></h2>
	<table class="tablelist">
		<colgroup>
			<col class="width50">
			<col class="width150">
			<col class="width150">
			<col class="width150">
			<col class="width200">
		</colgroup>
		<thead>
			<tr>
				<th>선택</th>
				<th>시작일</th>
				<th>종료일</th>
				<th>휴가일수</th>
				<th>등록일</th>
				<th>휴가사유</th>
			</tr>
		</thead>
		<tbody>
		<form name="subForm" id="subForm" method="post">
			<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
			<input type="hidden" name="allvcatnmanageList[${status.index}].bgnde" value="<c:out value='${result.bgnde}'/>" />
			<input type="hidden" name="allvcatnmanageList[${status.index}].endde" value="<c:out value='${result.endde}'/>" />
				<td><input type="checkbox" id="chk" name="allvcatnmanageList[${status.index}].chk"><label for="id값"></label></td>
				<td><c:out value="${result.bgnde}"/></td>
				<td><c:out value="${result.endde}"/></td>
				<td><c:out value="${result.vcatnCnt}"/></td>
				<td><c:out value="${result.frstRegistPnttm}"/></td>
				<td><c:out value="${result.vcatnResn}"/></td>
			</tr>
			</c:forEach>
		</form>
		</tbody>
	</table>
</div>
</body>
</html>