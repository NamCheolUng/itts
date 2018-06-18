<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script type="text/javascript">
var writeTp = false;
function fn_GotoSave(){
	if(writeTp)
	{
		alert("저장 중입니다.");
		return;
	}
	if(document.subForm.nttSj.value == "")
	{
		alert("제목을 입력해 주세요.");
		document.subForm.nttSj.focus();
		return;
	}
	
	for (i = 0; i < $(".files").length; i++) {
		$(".files:eq(" + i + ")").attr("name", "file_" + i);
	}
	
	document.subForm.action = "<c:url value='/com/business/bbs/noticeSave.do'/>";
	document.subForm.submit();
	writeTp = true;
}

</script>
<form:form commandName="board" name="subForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="bbsId" value="<c:out value='${bdMstr.bbsId}'/>" />
			<input type="hidden" name="bbsAttrbCode" value="<c:out value='${bdMstr.bbsAttrbCode}'/>" />
			<input type="hidden" name="bbsTyCode" value="<c:out value='${bdMstr.bbsTyCode}'/>" />
			<input type="hidden" name="replyPosblAt" value="<c:out value='${bdMstr.replyPosblAt}'/>" />
			<input type="hidden" name="fileAtchPosblAt" value="<c:out value='${bdMstr.fileAtchPosblAt}'/>" />
			<input type="hidden" name="posblAtchFileNumber" value="<c:out value='${bdMstr.posblAtchFileNumber}'/>" />
			<input type="hidden" name="posblAtchFileSize" value="<c:out value='${bdMstr.posblAtchFileSize}'/>" />
			<input type="hidden" name="tmplatId" value="<c:out value='${bdMstr.tmplatId}'/>" />
			<input type="hidden" name="authFlag" value="<c:out value='${bdMstr.authFlag}'/>" />
			<input id="ntceBgnde" name="ntceBgnde" type="hidden" value="10000101">
			<input id="ntceEndde" name="ntceEndde" type="hidden"value="99991231">
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
					<td colspan="3"><input type="text" class="k-textbox width100p" name="nttSj"></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3">						
						<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
							<div class="k-button k-upload-button" aria-label="찾아보기">
								<input type="file" class="files" name="files" aria-label="찾아보기" data-role="upload" multiple="multiple" autocomplete="off">
								<span>찾아보기</span>
							</div>
						</div></td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3"><textarea class="k-textbox width100p height300" name="nttCn"></textarea></td>
				</tr>
			</tbody>
	</table>
	<div class="space20"></div>
	<div class="btngroup fr">
		<button type="button" class="btn02" onclick="location.href='<c:url value='/com/business/bbs/noticeList.do?bbsId=BBSMSTR_000000000001'/>'">취소</button>
		<button type="button" class="btn01" onclick="javascript:fn_GotoSave();">저장</button>
	</div>
	<div class="space20"></div>
</div>
</form:form>
