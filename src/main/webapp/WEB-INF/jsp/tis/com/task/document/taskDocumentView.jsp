<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
	function fn_goList() {
		document.subForm.action = "<c:url value='/com/task/document/taskDocumentList.do'/>";
		document.subForm.submit();
	}
	
	function fn_GotoModify(){
		document.subForm.action = "<c:url value='/com/task/document/taskDocumentModify.do'/>";
		document.subForm.submit();
	}
	
	function fn_delete(){
		if(!confirm("<spring:message code='msg.delete.confirm'/>")){
			return;
		}
		document.subForm.action = "<c:url value='/com/task/document/taskDocumentDelete.do'/>";
		document.subForm.submit();
		
	}
	
</script>
<form name="subForm" method="post">
<input type="hidden" name="nttId" value='<c:out value="${boardVO.nttId}"/>'>
<input type="hidden" name="bbsId" value='<c:out value="${boardVO.bbsId}"/>'>
</form>

<div id="subwrap">
	<h1>문서도우미 > 상세보기</h1>
	<div class="space20"></div>
	<table class="tableview">
		<colgroup>
			<col class="width180">
			<col class="width600">
			<col class="width180">
			<col class="width600">
		</colgroup>
		<tbody>
			<tr>
				<th>제목</th>
				<td colspan="3"><c:out value="${boardVO.nttSj }"/></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td colspan="3" ><c:out value="${boardVO.frstRegisterNm }"/></td>
			</tr>
			<tr>
				<th>부서</th>
				<td><c:out value="${boardVO.orgnztNm }"/></td>
				<%-- <th>구분</th>
				<td><c:out value="${boardVO.nttCn }"/></td> --%>
			</tr>
			<tr>
				<th>비고</th>
				<td colspan="3"><c:out value="${boardVO.nttCn2 }"/></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3"><c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
					<c:param name="param_atchFileId" value="${boardVO.atchFileId}" />
					<c:param name="param_colspan" value="3" />
				</c:import>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="space20"></div>
	<div class="btngroup fl">
		<button type="button" class="btn04" onClick="javascript:fn_delete();">삭제</button>
	</div>
	<div class="btngroup fr">
		<button type="button" class="btn04" onClick="javascript:fn_goList();">목록</button>
		<button type="button" class="btn01" onclick="javascript:fn_GotoModify();">수정</button>
	</div>
</div>
</body>
</html>