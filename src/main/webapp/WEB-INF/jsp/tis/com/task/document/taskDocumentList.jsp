<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
	function fn_goList() {
		location.href = "<c:url value='${tmpPath}/com/task/taskDetail.do'/>";
	}
	
	function fn_GotoView(nttid){
		document.subForm.nttId.value = nttid;
		document.subForm.action = "<c:url value='/com/task/document/taskDocumentView.do'/>";
		document.subForm.submit();
	}
	
	function fn_goModify() {
		location.href = "<c:url value='${tmpPath}/com/task/document/taskDocumentModify.do'/>";
	}
	
	function fn_selectDept(){
		document.frm.action="<c:url value='/com/task/document/taskDocumentList.do'/>";
		document.frm.submit();
	}
	function fn_egov_downFile(atchFileId, fileSn){
		window.open("<c:url value='/cmm/fms/FileDown.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>");
	}
</script>
<style>
.wrapper table {
	width: 100%;
	text-align: center;
	border-collapse: collapse;
	table-layout: fixed;
}

.wrapper thead {
	margin: -3px 0 0 -1px;
	width: 98%;
	display: table;
	table-layout: fixed;
	position: absolute;
	background-color: #ffff1f;
	z-index: 200;
}

.wrapper tbody tr:first-child td {
	padding-top: 3.5%;
}
</style>

<form name="subForm" method="post">
<input type="hidden" name="nttId">
<input type="hidden" name="bbsId" value="BBSMSTR_000000000031">
</form>
<div id="subwrap">
	<h1>문서도우미</h1>
	<div class="schgroup">
		<form name="frm" method="post">
			<input type="hidden" name="bbsId" value="BBSMSTR_000000000031">
			<table class="tablesch">
				<colgroup>
					<col class="width150">
					<col>
					<col class="width150">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th>부서${board.orgnztNm }</th>
						<!-- 로그인한 사람 부서로 자동 선택 -->
						<td>
							<select class="select width150" onchange="javascript:fn_selectDept();" name="orgnztNm">
									<option value='' <c:if test="${empty searchVO.orgnztNm }">selected="selected"</c:if>>전체</option>
									<c:forEach var="code" items="${deptList }" varStatus="status">
										<option value="<c:out value='${code.codeNm }'/>" <c:if test="${code.codeNm eq searchVO.orgnztNm }">selected="selected"</c:if>><c:out value='${code.codeNm }'/></option>
									</c:forEach>							
							</select>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div class="btngroup fr">
		<!-- <button type="button" class="btn04" onClick="javascript:fn_goList();">목록</button> -->
		<button type="button" class="btn01" onclick="location.href='<c:url value='/com/task/document/taskDocumentInsert.do'/>?bbsId=BBSMSTR_000000000031'">등록</button>
	</div>
	<h4>
		<i class="fas fa-dot-circle"></i>일반문서양식
	</h4>
	<div class="wrapper">
		<div style="height: 250px; overflow-y: scroll;">
			<table class="tablelist">
				<thead>
					<tr>
						<th>no</th>
						<th>문서번호</th>
						<th>구분</th>
						<th>제목</th>
						<th>작성자</th>
						<th>파일명</th>
						<th>비고</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<tr>
						<td><c:out value="${status.index+1}" /></td>
						<td><c:out value='${result.docNo }'/></td>
						<td><c:out value="${result.orgnztNm}" /></td>
						<td><a href="javascript:fn_GotoView('<c:out value="${result.nttId}" />');"><c:out value="${result.nttSj}" /></a></td>
						<td><c:out value="${result.frstRegisterNm}" /></td>
						<td><a href="javascript:fn_egov_downFile('<c:out value="${result.atchFileId}"/>','0')" ><c:out value='${result.fileNm }'/></a></td>
						<td><c:out value="${result.nttCn2}" /></td>
					</tr>
				</c:forEach>	
				</tbody>
			</table>
		</div>
		<div style="height: 250px; overflow-y: scroll;">
			<h4>
				<i class="fas fa-dot-circle"></i>자동완성문서양식
			</h4>
			<table class="tablelist">
				<thead>
					<tr>
						<th>no</th>
						<th>문서번호</th>
						<th>구분</th>
						<th>제목</th>
						<th>작성자</th>
						<th>파일명</th>
						<th>비고</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>개발-1234</td>
						<td>개발부</td>
						<td><a href="<c:url value='/com/task/document/taskAutoDocumentInsert.do'/>?bbsId=BBSMSTR_000000000011">견적서</a></td>
						<td>홍길동</td>
						<td>견적서.hwp</td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>