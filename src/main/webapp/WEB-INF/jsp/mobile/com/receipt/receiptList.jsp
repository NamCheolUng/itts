<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=mobile/com/inc/header" />
<script>
function fn_reciptView(v){
	document.frm.expHistId.value = v; 
	document.frm.action = "<c:url value='/mobile/com/receipt/receiptView.do'/>";
	document.frm.submit();
}

function fn_serchDate(v){
	document.frm.dateDiv.value = v; 
	document.frm.action = "<c:url value='/mobile/com/receipt/receiptList.do'/>";
	document.frm.submit();
}
function fn_delete(v1,v2){
	if (confirm("정말 삭제하시겠습니까??") == true){
		document.frm.expHistId.value = v1; 
		if(v2 != null){
		document.frm.atchFileId.value = v2; 
		}
 		document.frm.action = "<c:url value='/mobile/com/receipt/receiptDelete.do'/>";
		document.frm.submit(); 
	}else{
		return;
	}
}
</script>
<body>
	<form name="frm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="dateDiv"> <input type="hidden" name="expHistId"> <input type="hidden" name="atchFileId">
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
			<button type="button" class="button raised bg-blue-500 color-white" onclick="location.href='<c:url value='/mobile/com/receipt/receiptInsert.do'/>'">새로작성</button>
		</div>
		<select class="select width150" onchange="fn_serchDate(this.value)">
			<option value="0" <c:if test="${receiptVO.dateDiv == 0}">selected="selected"</c:if>>전체</option>
			<option value="1" <c:if test="${receiptVO.dateDiv == 1}">selected="selected"</c:if>>최근 일주일</option>
			<option value="2" <c:if test="${receiptVO.dateDiv == 2}">selected="selected"</c:if>>최근 1개월</option>
			<option value="3" <c:if test="${receiptVO.dateDiv == 3}">selected="selected"</c:if>>최근 3개월</option>
		</select> <br>
		<br>
		<ul class="list">
			<c:forEach var="result" items="${result}" varStatus="status">
				<li>
					<span class="item-text" onclick="fn_reciptView('${result.expHistId}')"> 
					<c:forEach var="taskList" items="${taskList}" varStatus="status">
						<c:if test="${result.taskId == taskList.taskId}">${taskList.taskNm}</c:if>
					</c:forEach> 
					<small>${result.expDate}
					<c:if test="${result.atchFileId != null}">
						<img class="item-icon" style="width:6%; height:6%" src="<c:url value='/images/tis/Attach-icon.png'/>"></c:if>
					</small> 
					<span class="secondary-text">
					<c:forEach var="expSubjList" items="${expSubjList}" varStatus="status">
						<c:if test="${expSubjList.code == result.expSubj}">${expSubjList.codeNm}</c:if>
					</c:forEach>
					</span>
				</span> 
				<i class="icon-highlight-remove" onclick="fn_delete('${result.expHistId}','${result.atchFileId }')" style="width: 30px; height: 30px"></i></li>
				<li class="divider"></li>
			</c:forEach>
		</ul>
	</form>
</body>
</html>