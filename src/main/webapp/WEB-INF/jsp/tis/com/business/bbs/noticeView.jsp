<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="session" value="${sessionScope.loginVO}" />

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script>
function fn_egov_update_notice() {
	document.frm.action = "<c:url value='/com/business/bbs/noticeModify.do'/>";
	document.frm.submit();
}

function fn_egov_delete_notice() {

	if (confirm('<spring:message code="common.delete.msg" />')) {
 		document.frm.action = "<c:url value='/com/business/bbs/noticeDelete.do'/>";
		document.frm.submit(); 
	}
}
</script>
<form name="frm" method="post" action="">
	<input type="hidden" name="bbsId" value='<c:out value="${boardVO.bbsId}"/>'> 
	<input type="hidden" name="nttId" value='<c:out value="${boardVO.nttId}"/>'>
	<input type="hidden" name="lastUpdusrId" value="<c:out value="${session.uniqId}"/>" />
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
					<td>${boardVO.nttSj}</td>
					<th>작성일</th>
					<td>${boardVO.frstRegisterPnttm}</td>
				</tr>
				<!-- 첨부파일 있을경우만 노출 -->
				<tr>
					<th>첨부파일</th>
					<td colspan="3">
					<c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
							<c:param name="param_atchFileId" value="${boardVO.atchFileId}" />
							<c:param name="param_colspan" value="3" />
						</c:import>
						</td>
				</tr>
				<!-- 첨부파일 있을경우만 노출 //-->
				<tr>
					<th>내용</th>
					<td colspan="3">${boardVO.nttCn}</td>
				</tr>
			</tbody>
		</table>
		<div class="space20"></div>
		<div class="btngroup fl">
			<button type="button" class="btn04" onclick="location.href='<c:url value='/com/business/bbs/noticeList.do?bbsId=BBSMSTR_000000000001'/>'">목록</button>
		</div>
		<c:if test="${loginVO.manageAt == 'Y' }">
			<div class="btngroup fr">
				<button type="button" class="btn01" onclick="fn_egov_update_notice()">수정</button>
				<button type="button" class="btn02" onclick="fn_egov_delete_notice()">삭제</button>
			</div>
			<div class="space20"></div>
		</c:if>
	</div>
</form>
