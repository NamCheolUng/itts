<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
	function fn_goList() {
		location.href = "<c:url value='${tmpPath}/com/manager/managerHoliday/managerHolidayDetail.do'/>";
	}
</script>
<div id="subwrap">
	<h1>연차관리 > 상세 > 수정</h1>
	<h3>이하나 ( I-1704 ) 2017년도 연차</h3>
	<div class="btngroup fr">
		<button type="button" class="btn02" onClick="javascript:fn_goList();">취소</button>
		<button type="button" class="btn01">저장</button>
	</div>
	<table class="tablelist">
		<colgroup>
			<col class="width80">
			<col class="width100">
			<col class="width80">
			<col class="width80">
			<col class="width80">
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
				<td><input type="text" class="k-textbox width100p" value="2017-01-01"></td>
				<td><input type="text" class="k-textbox width100p" value="기본휴가"></td>
				<td><input type="text" class="k-textbox width100p" value="+15.0"></td>
				<td><input type="text" class="k-textbox width100p" value="0.0"></td>
				<td><input type="text" class="k-textbox width100p" value="0.0"></td>
				<td><input type="text" class="k-textbox width100p" value="15.0" readonly></td>
			</tr>
			<tr>
				<td><input type="text" class="k-textbox width100p" value="2017-01-01"></td>
				<td><input type="text" class="k-textbox width100p" value="근속가산휴가"></td>
				<td><input type="text" class="k-textbox width100p" value="+3.0"></td>
				<td><input type="text" class="k-textbox width100p" value="0.0"></td>
				<td><input type="text" class="k-textbox width100p" value="0.0"></td>
				<td><input type="text" class="k-textbox width100p" value="18.0" readonly></td>
			</tr>
			<tr>
				<td><input type="text" class="k-textbox width100p" value="2017-01-20"></td>
				<td><input type="text" class="k-textbox width100p" value="연차"></td>
				<td><input type="text" class="k-textbox width100p" value="0.0"></td>
				<td><input type="text" class="k-textbox width100p" value="1.0"></td>
				<td><input type="text" class="k-textbox width100p" value="-1.0"></td>
				<td><input type="text" class="k-textbox width100p" value="17.0" readonly></td>
			</tr>
		</tbody>
	</table>
</div>
</body>
</html>