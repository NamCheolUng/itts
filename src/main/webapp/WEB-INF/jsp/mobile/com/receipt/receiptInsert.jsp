<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="session" value="${sessionScope.loginVO}" />
<c:import url="/EgovPageLink.do?link=mobile/com/inc/header" />
<script type="text/javascript">
var writeTp = false;
	$(document).ready(function() {
		$("#LoadImg").hide();	
		
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth() + 1;
		var yyyy = today.getFullYear();

		if (dd < 10) {
			dd = '0' + dd
		}

		if (mm < 10) {
			mm = '0' + mm
		}

		document.frm.expDate.value = yyyy + '/' + mm + '/' + dd;
	
		$(".file").kendoUpload({ "multiple": false });
	});
	
	function fn_GotoSave() {
		if (writeTp) {
			alert("저장 중입니다.");
			return;
		}

		if (document.frm.price.value == "") {
			document.frm.price.value = 0;
		}
		document.frm.action = "<c:url value='/mobile/com/receipt/receiptSave.do'/>";
		document.frm.submit();
		writeTp = true;
	}
	
    function fn_loadImg(value) {
    	$("#LoadImg").show();
        if(value.files && value.files[0]) {
             var reader = new FileReader();
             reader.onload = function (e) {
                  $('#LoadImg').attr('src', e.target.result);
             }
             reader.readAsDataURL(value.files[0]);
        }
   }
</script>
<body>
	<form name="frm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="emplNo" value="${session.emplyrNo }">
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
			<button type="button" class="button raised" onclick="location.href='/tis/mobile/com/receipt/receiptList.do'">목록보기</button>
			<button type="button" class="button raised bg-blue-500 color-white" onclick="fn_GotoSave()">저장하기</button>
		</div>
		<br>
		<br>
		<br>
		<table style="width: 100%">
			<tbody>
				<tr>
					<th>과제명:</th>
					<td><select class="select width150" name="taskId">
							<c:forEach var="taskList" items="${taskList}" varStatus="status">
								<option value="${taskList.taskId }">${taskList.taskNm }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>지출일자:</th>
					<td><input class="datepicker width200" name="expDate">
					</td>
				</tr>
				<tr>
					<th>계정과목:</th>
					<td><select class="select width150" name="expSubj">
							<c:forEach var="expSubjList" items="${expSubjList}" varStatus="status">
								<option value="${expSubjList.code }">${expSubjList.codeNm }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>지출처:</th>
					<td><input type="text" class="text-input" name="expPlace"></td>
				</tr>
				<tr>
					<th>금액:</th>
					<td><input type="number" class="text-input" name="price"></td>
				</tr>
				<tr>
					<th>비고:</th>
					<td><textarea class="text-input" name="rm"></textarea></td>
				</tr>
			</tbody>
		</table>
		<br>
		<div class="card rich-card" style="width: 100%">
			<img id="LoadImg" style="width:100%; height:200px">

			<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
				<div class="k-button k-upload-button" aria-label="찾아보기">
					<input type="file" class="file" name="files" aria-label="찾아보기" data-role="upload" autocomplete="off" onchange="fn_loadImg(this)">
					<span>카메라/갤러리</span>
				</div>
			</div>
		</div>
	</form>
</body>
</html>