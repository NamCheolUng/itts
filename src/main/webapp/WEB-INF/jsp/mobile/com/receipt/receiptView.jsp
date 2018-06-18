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
function fn_update_receipt(v){
	document.frm.expHistId.value = v; 
	document.frm.action = "<c:url value='/mobile/com/receipt/receiptModify.do'/>";
	document.frm.submit();
}
</script>
<body>
	<form name="frm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="expHistId">
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
			<button type="button" class="button raised" onclick="location.href='<c:url value='/mobile/com/receipt/receiptList.do'/>'">목록보기</button>
			<button type="button" class="button raised bg-blue-500 color-white" onclick="fn_update_receipt('${receiptVO.expHistId}')">수정하기</button>
		</div>
		<br>
		<br>
		<br>
		<table style="width: 100%;">
			<tbody>
				<tr>
					<th>과제명:</th>
					<td><c:forEach var="taskList" items="${taskList}" varStatus="status">
							<c:if test="${receiptVO.taskId == taskList.taskId}">
								<input type="text" class="text-input" name="taskId" value="${taskList.taskNm }" disabled>
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>지출일자:</th>
					<td><input type="text" class="text-input" name="expDate" value="${receiptVO.expDate}" disabled></td>
				</tr>
				<tr>
					<th>계정과목:</th>
					<td><c:forEach var="expSubjList" items="${expSubjList}" varStatus="status">
							<c:if test="${expSubjList.code == receiptVO.expSubj}">
								<input type="text" class="text-input" name=expSubj value="${expSubjList.codeNm }" disabled>
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>지출처:</th>
					<td><input type="text" class="text-input" name="expPlace" value="${receiptVO.expPlace }" disabled></td>
				</tr>
				<tr>
					<th>금액:</th>
					<td><input type="number" class="text-input" name="price" value="${receiptVO.price }" disabled></td>
				</tr>
				<tr>
					<th>비고:</th>
					<td><textarea class="text-input" name="rm" disabled><c:out value='${receiptVO.rm }' /></textarea></td>
				</tr>
			</tbody>
		</table>
		<br>
		<div class="card rich-card" style="width: 100%">
			<h2>지출명세서 영수증</h2>
			<div class="card-hero" style="height: 100%">
				<c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
					<c:param name="param_atchFileId" value="${receiptVO.atchFileId}" />
					<c:param name="param_colspan" value="3" />
				</c:import>
				<c:if test="${receiptVO.atchFileId != null }">
					<img src='<c:url value="/cmm/fms/getImage.do?atchFileId=${receiptVO.atchFileId}&fileSn=0" />' / style="width:100%; height:250px">
				</c:if>
			</div>
			<div class="divider"></div>
		</div>
	</form>
</body>
</html>