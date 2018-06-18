<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
	function fn_GotoSave(){
		if(document.subForm.nttSj.value == ""){
			alert("제목을 입력하셔야 합니다.");
			return;
		}
		if(!confirm("<spring:message code='msg.save.confirm'/>")){
			return;
		}
		document.subForm.orgnztNm.value = $("#orgnztId option:selected").text();
		document.subForm.action = "<c:url value='/com/task/document/taskDocumentSave.do'/>";
		document.subForm.submit();
	}
	function fn_goList() {
		document.subForm.action = "<c:url value='/com/task/document/taskDocumentList.do'/>";
		document.subForm.submit();
	}
</script>
<div id="subwrap">
	<h1>문서도우미 > 수정</h1>
	<div class="space20"></div>
	<form name="subForm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="nttId" value='<c:out value="${boardVO.nttId}"/>'>
	<input type="hidden" name="bbsId" value='<c:out value="${boardVO.bbsId}"/>'>
	<input type="hidden" name="returnUrl" value="<c:url value='/com/task/document/taskDocumentModify.do'/>"/>
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
				<td colspan="3"><input type="text" name="nttSj" class="k-textbox width100p" value="<c:out value='${boardVO.nttSj }'/>"></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td colspan="3" ><input type="text" class="k-textbox width100p" value="<c:out value='${boardVO.frstRegisterNm }'/>" readonly></td>
			</tr>
			<tr>
				<th>부서</th>
				<td>
				<input type="hidden" name="orgnztNm"> 
					<select name="orgnztId" id="orgnztId" class="select width150">
						<c:forEach var="result" items="${depart_result}" varStatus="status">
							<option value="${result.code}" <c:if test="${result.codeNm == boardVO.orgnztNm}">selected</c:if> >${result.codeNm}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>비고</th>
				<td colspan="3"><input type="text" class="k-textbox width100p" name="nttCn2" value="<c:out value='${boardVO.nttCn2 }'/>"></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
						<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
							<div class="k-button k-upload-button" aria-label="찾아보기">
								<input type="file" class="files" name="files" aria-label="찾아보기" data-role="upload" multiple="multiple" autocomplete="off">
								<span>찾아보기</span>
							</div>
						</div> <c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
							<c:param name="param_atchFileId" value="${boardVO.atchFileId}" />
							<c:param name="param_colspan" value="3" />
						</c:import></td>
			</tr>
		</tbody>
	</table>
	</form>
	<div class="space20"></div>
	<div class="btngroup fr">
		<button type="button" class="btn02" onClick="javascript:fn_goList();">취소</button>
		<button type="button" class="btn01" onclick="javascript:fn_GotoSave();">저장</button>
	</div>
</div>
</body>
</html>