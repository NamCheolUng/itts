<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="session" value="${sessionScope.loginVO}" />
<c:import url="/EgovPageLink.do?link=mobile/com/inc/header" />

<style>
.k-upload-status
{
    visibility: hidden;
}
</style>

<script type="text/javascript">
var uploadAt = false;
var writeTp = false;
	$(document).ready(function(){

		$("#ncrdId2").val(sessionStorage.ncrdId).prop("selected", true);
		$("#ncrdId2").kendoDropDownList();
		 
		$("#LoadImg").hide();		
		$(".file").kendoUpload({ "multiple": false });
		
		$("#img").empty();
 		var file = document.frm.ncrdId2.value.split(",");
 		file = file[1];
 		var test =file;
 			var path = "<img src='<c:url value='/cmm/fms/getImage.do?atchFileId="+file+"&fileSn=${fileVO.fileSn}' />' style='width:100%; height:250px'>";
 	
 		if(file){
			$("#img").append(path);
 		}

		$('#ncrdId2').change(function(){
			$("#img").empty();
	 		var file = document.frm.ncrdId2.value.split(",");
	 		file = file[1];
	 		var path = "<img src='<c:url value='/cmm/fms/getImage.do?atchFileId="+file+"&fileSn=${fileVO.fileSn}' />' style='width:100%; height:250px'>";
	 		
	 		if(file){
	 			$("#img").append(path);
	 		}
		});
	});

	function fn_GotoSave() {	
		if(writeTp)
		{
			alert("저장 중입니다.");
			return;
		}
 		var ncrdId2 = document.frm.ncrdId2.value.split(",");
 		
 		if(ncrdId2[0] == '' || ncrdId2[0] == null){
 			alert("담당자가 선택되지 않았습니다.");
 			return;
 		}
 		
 		if(uploadAt == false){
 			alert("파일이 선택되지 않았습니다.");
 			return;
 		}
 		
 		document.frm.ncrdId.value = ncrdId2[0];
 		document.frm.atchFileId.value =  ncrdId2[1];
		document.frm.action = "<c:url value='/mobile/com/callingCard/callingCardSave.do'/>";
		document.frm.submit();
		writeTp = true;
	}
	
	function fn_loadImg(value) {
		$("#LoadImg").show();
		uploadAt= true;
	    if(value.files && value.files[0]) {
	         var reader = new FileReader();
	         reader.onload = function (e) {
	              $('#LoadImg').attr('src', e.target.result);
	         }
	         reader.readAsDataURL(value.files[0]);
	    }
	}
	
	function test(ncrdId){
		sessionStorage.ncrdId = ncrdId.value;
	}
</script>

<body>
	<form:form commandName="callingCard" name="frm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="emplNo" value="${session.emplyrNo }">
		<input type="hidden" name="returnUrl" value="<c:url value='${tmpPath}/mobile/com/callingCard/callingCard.do'/>" />
		<input type="hidden" name="ncrdId">
		<input type="hidden" name="atchFileId">
		
		<div class="toolbar-group">
			<div class="toolbar bg-blue-500 color-white">
				<center><span class="toolbar-label">(주)이튜통합정보시스템</span></center>
			</div>
			<ul class="toolbar tabs bg-blue-500 color-white">
				<li><a href="<c:url value='/mobile/com/receipt/receiptInsert.do'/>">영수증</a></li>
				<li class="selected"><a href="<c:url value='/mobile/com/callingCard/callingCard.do'/>">명함</a></li>
				<li><a href="<c:url value='/mobile/com/document/documentList.do'/>">증빙서류</a></li>
			</ul>
		</div>
		<br>
		담당자명: 
 		<select class="width150" name="ncrdId2" id="ncrdId2" onchange="test(this)">
 			<c:forEach var="result" items="${ncrd}" varStatus="status">
				<option value='<c:out value="${result.ncrdId},${result.atchFileId}"/>'><c:out value="${result.ncrdNm}"/></option>
 			</c:forEach>
		</select>
		<div style="float: right">
			<button type="button" class="button raised bg-blue-500 color-white"  onclick="javascript:fn_GotoSave();" >저장하기</button>
		</div>
		<div class="card rich-card" style="width: 100%">
			<h2>기관/업체직원 명함을 등록하세요</h2>
			<div class="card-hero" style="width:100%; height:50%">
				<div id="img"></div>
			</div>
			<br><div class="divider"></div><br>
			<div class="card-hero" style="width:100%; height:50%">
				<center><img id="LoadImg" style="width:50%;height:100px;"></center>
			</div>
			<div class="divider"></div>
			<div class="card-footer">
				<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
					<div class="k-button k-upload-button" aria-label="카메라/갤러리">
						<input type="file" class="file" name="files" aria-label="카메라/갤러리" data-role="upload" autocomplete="off" onchange="fn_loadImg(this)"">
						<span>카메라/갤러리</span>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>