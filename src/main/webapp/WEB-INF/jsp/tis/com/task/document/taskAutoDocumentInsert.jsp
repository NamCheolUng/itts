<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
	function fn_goList() {
		location.href = "<c:url value='${tmpPath}/com/task/document/taskDocumentList.do'/>";
	}
</script>
<div id="subwrap">
	<h1>과제관리 > 문서도우미 > 자동완성문서(견적서)</h1>
	<div class="space20"></div>
	<div class="btngroup fr">
		<button type="button" class="btn02" onClick="javascript:fn_goList();">취소</button>
		<button type="button" class="btn01">한글내보내기</button>
		<button type="button" class="btn01">저장</button>
	</div>
	<table class="tableview">
		<colgroup>
			<col class="width180">
			<col class="width600">
			<col class="width180">
			<col class="width600">
		</colgroup>
		<tbody>
			<tr>
				<th>수신</th>
				<td><input type="text" class="k-textbox width100p"></td>
				<th>견적일</th>
				<td><input class="datepicker width200"></td>
			</tr>
			<tr>
				<th>사업명</th>
				<td colspan="3"><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" class="k-textbox width100p"></td>
				<th>총액입력:</th>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
		</tbody>
	</table>
	<table class="tableview">
		<colgroup>
			<col class="width100">
			<col class="width200">
			<col class="width50">
			<col class="width50">
			<col class="width50">
			<col class="width100">
			<col class="width100">
			<col class="width100">
		</colgroup>
		<thead>
			<tr>
				<th>구분</th>
				<th>세부사항</th>
				<th>인원</th>
				<th>기술</th>
				<th>기간</th>
				<th>공급단가</th>
				<th>투입률</th>
				<th>비용</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan="14">서비스구축</td>
				<td>요구사항 분석 및 설계, 사업관리</td>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td>특급</td>
				<td><input type="text" class="k-textbox width100p" value="3"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<td rowspan="3">프로그램작업</td>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td>고급</td>
				<td><input type="text" class="k-textbox width100p" value="3"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<td><input type="text" class="k-textbox width100p" value="2"></td>
				<td>중급</td>
				<td><input type="text" class="k-textbox width100p" value="3"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td>초급</td>
				<td><input type="text" class="k-textbox width100p" value="3"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<td rowspan="3">디자인 시안 작업 및 퍼블리싱</td>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td>고급</td>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td>중급</td>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td>초급</td>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<td rowspan="2">HW/SW 세팅 지원</td>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td>고급</td>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td>초급</td>
				<td><input type="text" class="k-textbox width100p" value="1"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<td colspan="6"><center>소계</center></td>
				<td></td>
			</tr>
			<tr>
				<td>재경비</td>
				<td><input type="text" class="k-textbox width100p" value="100%"></td>
				<td colspan="4"><center>직접인건비 * 재경비율</center></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<td>기술료</td>
				<td><input type="text" class="k-textbox width100p" value="20%"></td>
				<td colspan="4"><center>(직접인건비+재경비) * 기술료율</center></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<td>부가세</td>
				<td></td>
				<td colspan="4"><center>(직접인건비+직접경비+재경비+기술료) * 10%</center></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
			<tr>
				<td colspan="6"><center>소계(십만단위미만 절삭)</center></td>
				<td><input type="text" class="k-textbox width100p"></td>
			</tr>
		</tbody>
	</table>
	<div class="space20"></div>
</div>
</body>
</html>