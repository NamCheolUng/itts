<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">

function fn_GotoSearch(){

	document.searchForm.action = "<c:url value='/com/business/holiday/holidayList.do'/>";
	document.searchForm.submit();
}
</script>

<div id="subwrap">
	<h1><i class="fa fa-th-large"></i> 연차관리</h1>
	<div class="space10"></div>
	<h3>휴가사용일수 (<strong><c:out value="${vacCnt.useYrycCo}"/></strong> 일) / 휴가일수 (<strong><c:out value="${vacCnt.baseOccrrncCo + vacCnt.occrncYrycCo}"/></strong> 일)</h3>
	<br> 
	<form name="searchForm" method="post">
	<input type="hidden" name="usid" value='<c:out value="${vacCnt.usid}"/>'>
	<div class="schgroup">
		<table class="tablesch">
			<colgroup>
				<col class="width150">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>년도</th>
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
	</div>
	</form>
	<table class="tablelist">
	<c:set var = "sumremndr" value = "${vacCnt.baseOccrrncCo + vacCnt.occrncYrycCo}"/>
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
				<td>-<c:out value="${result.vcatnCnt}"/></td>
				<c:set var = "sumremndr" value = "${sumremndr - result.vcatnCnt}"/>
				<td><c:out value="${sumremndr}"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table><br>
	* 휴가일 산정기준은 업무관리/노무관리/취업규칙 확인
</div>
</body>
</html>