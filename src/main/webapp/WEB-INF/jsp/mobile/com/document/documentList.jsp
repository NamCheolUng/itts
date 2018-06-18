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
	$("#LoadImg").hide();	
	$(".file").kendoUpload({ "multiple": false });
	
	$("#content-slider").lightSlider({
        loop:true,
        keyPress:true
   });
   
	$('#image-gallery').lightSlider({
        item:1,
        thumbItem:9,
        onSliderLoad: function() {
          $('#image-gallery').removeClass('cS-hidden');
        }  
   });
})

function fn_GotoSave(){
	if(writeTp)
	{
		alert("저장 중입니다.");
		return;
	}

	if(uploadAt == false){
 		alert("파일이 선택되지 않았습니다.");
 		return;
 	}
		
	document.frm.action = "<c:url value='/mobile/com/document/documentSave.do'/>";
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
</script>

<body>
	<form:form commandName="document" name="frm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="emplNo" value="${session.emplyrNo }">
		<input type="hidden" name="returnUrl" value="<c:url value='${tmpPath}/mobile/com/document/documentList.do'/>" />
		<input type="hidden" name="fileType" value="bankBook">
		<div class="toolbar-group">
			<div class="toolbar bg-blue-500 color-white">
				<center><span class="toolbar-label">(주)이튜통합정보시스템</span></center>
			</div>
			<ul class="toolbar tabs bg-blue-500 color-white">
				<li><a href="<c:url value='/mobile/com/receipt/receiptInsert.do'/>">영수증</a></li>
				<li><a href="<c:url value='/mobile/com/callingCard/callingCard.do'/>">명함</a></li>
				<li class="selected"><a href="<c:url value='/mobile/com/document/documentList.do'/>">증빙서류</a></li>
			</ul>
		</div>
		<br>
		<div style="float: right">
			<button type="button" class="button raised bg-blue-500 color-white" style="width100" onclick="javascript:fn_GotoSave();" >저장하기</button>
		</div>
		<div class="card rich-card" style="width: 100%">
			<h2>통장사본 및 주민등록등본을 등록하세요</h2>
			<div class="card-hero" style="width:100%; height:50%">
					<c:import url="/cmm/fms/moblieSelectFileInfsForUpdate.do" charEncoding="utf-8">
						<c:param name="param_atchFileId" value="${userManageVO.atchFileId}" />
						<c:param name="param_colspan" value="3" />
					</c:import>
			</div>
			<div class="divider"></div><br>
			<div class="card-hero" style="width:100%; height:50%">
				<center><img id="LoadImg" style="width:50%;height:100px"></center>
			</div>
			<div class="card-footer">
				<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
					<div class="k-button k-upload-button" aria-label="카메라/갤러리">
						<input type="file" class="file" name="files" aria-label="카메라/갤러리" data-role="upload" autocomplete="off" onchange="fn_loadImg(this)">
						<span>카메라/갤러리</span>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>