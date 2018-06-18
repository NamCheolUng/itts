<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script>
function fn_GotoModify(){
	
	document.subForm.action = "<c:url value='/com/business/bbs/sexualModify.do'/>";
	document.subForm.submit();	
}
</script>

<div id="subwrap">
	<form name="subForm" method="post">
		<input type="hidden" name="bbsId" value='<c:out value="${boardVO.bbsId}"/>'>
		<input type="hidden" name="nttId" value='<c:out value="${boardVO.nttId}"/>'>
	<h1>노무 >성희롱예방교육</h1>
	
	<div class="tabmenu">
		<ul>
			<li><a href="<c:url value='/com/business/bbs/emplyrRuleList.do'/>?bbsId=BBSMSTR_000000000011">취업규칙</a></li>
			<li class="active"><a href="<c:url value='/com/business/bbs/sexualList.do'/>?bbsId=BBSMSTR_000000000021">성희롱예방교육</a></li>
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
				<td colspan="3">${boardVO.nttSj}</td>
			</tr>
			<tr>
				<th>담당부서</th>
				<td>${boardVO.orgnztNm}</td>
				<th>담당자</th>
				<td>${boardVO.emplNm}</td>
			</tr>
			<tr>
				<th>교육일시</th>
				<td colspan="3"><textarea class="k-textbox width100p height100" readonly>${boardVO.nttCn}</textarea></td>
			</tr>
			<tr>
				<th>관련법령<br>확인하기</th>
				<td colspan="3"><textarea class="k-textbox width100p height100" readonly>${boardVO.nttCn2}</textarea></td>
			</tr>
			<tr>
				<th>최신사례<br>찾아보기</th>
				<td colspan="3"><textarea class="k-textbox width100p height100" readonly>${boardVO.nttCn3}</textarea></td>
			</tr>
			<tr>
				<th>관련콘텐츠<br>활용</th>
				<td colspan="3"><textarea class="k-textbox width100p height100" readonly>${boardVO.nttCn4}</textarea></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3"><c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
					<c:param name="param_atchFileId" value="${boardVO.atchFileId}" />
					<c:param name="param_colspan" value="3" />
				</c:import></td>
			</tr>
		</tbody>
	</table>
	<br>
	<c:if test="${loginVO.manageAt == 'Y' }">
		<div class="btngroup fr">
					<c:choose>
				<c:when test="${empty boardVO }">
					<button type="button" class="btn01" onclick="location.href='<c:url value='/com/business/bbs/sexualInsert.do?bbsId=BBSMSTR_000000000021'/>' ">등록</button>
				</c:when>
				<c:otherwise>
					<button type="button" class="btn01" onclick="fn_GotoModify()">수정</button>
				</c:otherwise>
			</c:choose>
		</div>
	</c:if>
	</form>
</div>
