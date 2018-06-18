<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="session" value="${sessionScope.loginVO}"/>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script>
var writeTp = false;
function fn_egov_update(){
	if(writeTp)
	{
		alert("저장 중입니다.");
		return;
	}
	if(document.frm.nttSj.value == "")
	{
		alert("제목을 입력해 주세요.");
		document.frm.nttSj.focus();
		return;
	}
	if(document.frm.nttCn == "")
	{
		alert("내용을 입력해 주세요.");
		document.frm.nttCn.focus();
		return;
	}
	
	for (i = 0; i < $(".files").length; i++) {
		$(".files:eq(" + i + ")").attr("name", "file_" + i);
	}
	
	document.frm.action = "<c:url value='/com/business/bbs/noticeUpdate.do'/>";
	document.frm.submit();
	writeTp = true;
}

function fn_goList(){
	document.frm.action = "<c:url value='${tmpPath}/com/business/bbs/noticeView.do'/>";
	document.frm.submit();
}
</script>
<form:form commandName="board" name="frm" method="post" enctype="multipart/form-data">
	<!-- 첨부파일 삭제 후 리턴 URL -->
	<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>" />
	<input type="hidden" name="nttId" value="<c:out value="${result.nttId}"/>" />
	<input type="hidden" name="lastUpdusrId" value="<c:out value="${session.uniqId}"/>" />
	<input type="hidden" name="ntcrId" value=<c:out value="${result.ntcrId}"/> />
	<input type="hidden" name="ntcrNm" value=<c:out value="${result.ntcrNm}"/> />
	<input type="hidden" name="returnUrl" value="<c:url value='${tmpPath}/com/business/bbs/noticeModify.do'/>" />
	
<div id="subwrap">	
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
				<td colspan="3"><input type="text" name="nttSj" class="k-textbox width100p" value="${result.nttSj }"></td>
			</tr>
			<!-- 첨부파일 있을경우만 노출 -->
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
						<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
							<div class="k-button k-upload-button" aria-label="찾아보기">
								<input type="file" class="files" name="files" aria-label="찾아보기" data-role="upload" multiple="multiple" autocomplete="off">
								<span>찾아보기</span>
							</div>
						</div> <c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
							<c:param name="param_atchFileId" value="${result.atchFileId}" />
							<c:param name="param_colspan" value="3" />
						</c:import></td>
			</tr>
			<!-- 첨부파일 있을경우만 노출 //-->			
			<tr>
				<th>내용</th>
				<td colspan="3"><textarea class="k-textbox width100p height300" name="nttCn"><c:out value='${result.nttCn }' /></textarea></td>
			</tr>					
		</tbody>
	</table>
	<div class="space20"></div>
	<div class="btngroup fr">
		<button type="button" class="btn02" onClick="javascript:fn_goList();">취소</button>		
		<button type="button" class="btn01" onClick="javascript:fn_egov_update();">저장</button>		
	</div>
	<div class="space20"></div>
</div>
</form:form>
