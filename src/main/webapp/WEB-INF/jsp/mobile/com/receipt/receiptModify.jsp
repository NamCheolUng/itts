<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=mobile/com/inc/header" />
<style>
.card-hero:not (.side ) {
	height: 7em;
	width: 100%;
	background-size: 100% auto;
}
</style>
<script>
var writeTp = false;
	$(document).ready(function(){
		$(".file").kendoUpload({ "multiple": false });
	});

	function fn_goList() {
		document.frm.action = "<c:url value='${tmpPath}/mobile/com/receipt/receiptView.do'/>";
		document.frm.submit();
	}

	function fn_update() {
		
		if (writeTp) {
			alert("저장 중입니다.");
			return;
		}
		document.frm.action = "<c:url value='/mobile/com/receipt/receiptUpdate.do'/>";
		document.frm.submit();
		writeTp = true;
	}
</script>
<body>
	<form name="frm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="returnUrl" value="<c:url value='${tmpPath}/mobile/com/receipt/receiptModify.do'/>" />
		<input type="hidden" name="expHistId" value="${receiptVO.expHistId }" />
		<div class="toolbar-group">
			<div class="toolbar bg-blue-500 color-white">
				<center><span class="toolbar-label">(주)이튜통합정보시스템</span></center>
			</div>
			<ul class="toolbar tabs bg-blue-500 color-white">
				<li class="selected"><a href="<c:url value='/mobile/com/receipt/receiptInsert.do'/>">영수증</a></li>
				<li><a href="<c:url value='/mobile/com/callingCard/callingCard.do'/>">명함</a></li>
				<li><a href="<c:url value='/mobile/com/document/documentList.do'/>">증빙서류</a></li>
			</ul>
		</div>
		<br>
		<div style="float: right">
			<button type="button" class="button raised bg-red-500 color-white" onclick="fn_goList()">취소</button>
			<button type="button" class="button raised bg-blue-500 color-white" onclick="fn_update()">저장하기</button>
		</div>
		<br>
		<br>
		<br>
		<table style="width: 100%;">
			<tbody>
				<tr>
					<th>과제명:</th>
					<td><select class="select width150" name="taskId">
							<c:forEach var="taskList" items="${taskList}" varStatus="status">
								<option value="${taskList.taskId }"
									<c:if test="${receiptVO.taskId == taskList.taskId}">selected="selected"</c:if>>${taskList.taskNm }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>지출일자:</th>
					<td><input class="datepicker width200" name="expDate" value="${receiptVO.expDate}"></td>
				</tr>
				<tr>
					<th>계정과목:</th>
					<td><select class="select width150" name="expSubj">
							<c:forEach var="expSubjList" items="${expSubjList}" varStatus="status">
								<option value="${expSubjList.code }"
									<c:if test="${expSubjList.code == receiptVO.expSubj}">selected="selected"</c:if>>${expSubjList.codeNm }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>지출처:</th>
					<td><input type="text" class="text-input" name="expPlace" value="${receiptVO.expPlace }"></td>
				</tr>
				<tr>
					<th>금액:</th>
					<td><input type="number" class="text-input" name="price" value="${receiptVO.price }"></td>
				</tr>
				<tr>
					<th>비고:</th>
					<td><textarea class="text-input" name="rm"><c:out value='${receiptVO.rm }' /></textarea></td>
				</tr>
			</tbody>
		</table>
		<br>
		<div class="card rich-card" style="width: 100%">
			<h2>지출명세서 영수증</h2>
			<div class="card-hero" style="height: 100%">
				<c:import url="/cmm/fms/selectReceiptFileInfsForUpdate.do" charEncoding="utf-8">
					<c:param name="param_atchFileId" value="${receiptVO.atchFileId}" />
					<c:param name="param_colspan" value="3" />
				</c:import>
				<c:if test="${receiptVO.atchFileId != null }">
					<img src='<c:url value="/cmm/fms/getImage.do?atchFileId=${receiptVO.atchFileId}&fileSn=0" />' / style="width:100%;height:200px">
				</c:if>
			</div>
			<div class="divider"></div>
			<div class="card-footer">
				<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
					<div class="k-button k-upload-button" aria-label="찾아보기">
						<input type="file" class="file" name="files" aria-label="찾아보기"
							data-role="upload" multiple="multiple" autocomplete="off">
						<span>카메라/갤러리</span>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>