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
	
	document.frm.action = "<c:url value='/com/business/bbs/emplyrRuleUpdate.do'/>";
	document.frm.submit();
	writeTp = true;
}

function fn_goList(){
	document.frm.action = "<c:url value='${tmpPath}/com/business/bbs/emplyrRuleList.do'/>";
	document.frm.submit();
}
</script>

<form:form commandName="board" name="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>" />
	<input type="hidden" name="lastUpdusrId" value="<c:out value="${session.uniqId}"/>" />
	<input type="hidden" name="ntcrId" value=<c:out value="${result.ntcrId}"/> />
	<input type="hidden" name="ntcrNm" value=<c:out value="${result.ntcrNm}"/> />
	<input type="hidden" name="returnUrl" value="<c:url value='${tmpPath}/com/business/bbs/emplyrRuleModify.do'/>" />
<div id="subwrap">
	<h1>노무 >취업규칙>수정</h1>
	<div class="tabmenu">
		<ul>
			<li class="active"><a href="<c:url value='/com/business/bbs/emplyrRuleList.do'/>?bbsId=BBSMSTR_000000000011">취업규칙</a></li>
			<li><a href="<c:url value='/com/business/bbs/sexualList.do'/>?bbsId=BBSMSTR_000000000021">성희롱예방교육</a></li>
		</ul>
	</div>
	<table class="tableview">
		<colgroup>
			<col class="width150">
			<col class="width520">
			<col class="width150">
			<col class="width520">
		</colgroup>
		<tbody>
			<tr>
				<th>제목</th>
				<td colspan="3"><input type="text" name="nttSj" class="k-textbox width100p" value="${result.nttSj }"></td>
			</tr>
			<tr>
				<th>담당부서</th>
				<td><select name="orgnztNm" id="orgnztId" class="select width150">
						<c:forEach var="dresult" items="${depart_result}" varStatus="status">
							<option value="${dresult.codeNm}"
								<c:if test="${dresult.codeNm == result.orgnztNm}">selected="selected"</c:if>>${dresult.codeNm}</option>
						</c:forEach>
					</select></td>
				<th>담당자</th>
				<td><input type="text" name="emplNm" class="k-textbox width100p" value="${result.emplNm }"></td>
			</tr>
			<tr>
				<th>취업규칙이란?</th>
				<td colspan="3"><textarea class="k-textbox width100p height100" name="nttCn"><c:out value='${result.nttCn }' /></textarea></td>
			</tr>
			<tr>
				<th>관련법률</th>
				<td colspan="3"><textarea class="k-textbox width100p height250" name="nttCn2"><c:out value='${result.nttCn2 }' /></textarea></td>
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
							<c:param name="param_atchFileId" value="${result.atchFileId}" />
							<c:param name="param_colspan" value="3" />
						</c:import></td>
			</tr>
		</tbody>
	</table>
	<br>
	<div class="btngroup fr">
		<button type="button" class="btn02" onClick="javascript:fn_goList();">취소</button>		
		<button type="button" class="btn01" onClick="javascript:fn_egov_update();">저장</button>	
	</div>
</div>
</form:form>
