<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="session" value="${sessionScope.loginVO}" />
<c:import url="/EgovPageLink.do?link=mobile/com/inc/header" />
<script type="text/javascript">
var writeTp = false;
function fn_GotoSave(fileType){
	if(document.frm.files.value == ""){
		alert("tt");
		return;
	}
	if(writeTp)
	{
		alert("저장 중입니다.");
		return;
	}

	if(document.frm.bankBookAtchFileId){
	document.frm.selectAtchFileId.value = document.frm.bankBookAtchFileId.value;
	}
	document.frm.action = "<c:url value='/mobile/com/document/documentSave.do'/>";
	document.frm.submit();
	writeTp = true;
}

function fn_GotoUptEmplyr(){
	document.frm.action = "<c:url value='/mobile/com/document/documentUptEmplyr.do'/>";
	document.frm.submit();
	alert("저장 완료");
}
</script>

<body>
	<form:form commandName="document" name="frm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="emplNo" value="${session.emplyrNo }">
		<input type="hidden" name="selectAtchFileId">
		<input type="hidden" name="returnUrl" value="<c:url value='${tmpPath}/mobile/com/document/certified.do'/>" />
		<input type="hidden" name="fileType" value="certified">
		<div class="toolbar-group">
			<div class="toolbar bg-blue-500 color-white">
				<center><span class="toolbar-label">(주)이튜통합정보시스템</span></center>
			</div>
			<ul class="toolbar tabs bg-blue-500 color-white">
				<li><a href="<c:url value='/mobile/com/receipt/receiptInsert.do'/>">영수증</a></li>
				<li><a href="<c:url value='/mobile/com/callingCard/callingCard.do'/>">명함</a></li>
				<li class="selected"><a href="<c:url value='/mobile/com/document/certified.do'/>">증빙서류</a></li>
			</ul>
		</div>
		<br>
		<button type="button" class="button raised" onclick="location.href='<c:url value ='/mobile/com/document/bankBook.do'/>'">통장</button>
		<button type="button" class="button raised bg-blue-500 color-white" onclick="location.href='<c:url value ='/mobile/com/document/certified.do'/>'">등본</button>
		<div style="float: right">	
			<button type="button" class="button raised bg-blue-500 color-white" style="width100" onclick="javascript:fn_GotoUptEmplyr();" >저장하기</button>
		</div>
		<div class="card rich-card" style="width: 100%">
			<h2>회원가입 증빙서류</h2>
			<div class="card-hero">				
					<input type="hidden" name="bankBookAtchFileId" value="${result.atchFileId}">
					<c:import url="/cmm/fms/moblieSelectFileInfsNoDel.do" charEncoding="utf-8">
						<c:param name="param_atchFileId" value="${result.atchFileId}" />
						<c:param name="param_colspan" value="3" />
					</c:import>
					<c:if test="${result != null }">
					<img src='<c:url value="/cmm/fms/getImage.do?atchFileId=${result.atchFileId}&fileSn=0" />' style="width:100%; height:90%">
					</c:if>
			</div>
			<div class="divider"></div>
			<div class="card-footer">
				<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
					<div class="k-button k-upload-button" aria-label="카메라/갤러리">
						<input type="file" class="files" name="files" aria-label="카메라/갤러리" data-role="upload" multiple="multiple" autocomplete="off" onchange="javascript:fn_GotoSave();">
						<span>카메라/갤러리</span>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>