<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<%-- <script type="text/javascript" src="<c:url value='/js/tis/jquery.form.min.js'/>"></script> --%>
<script type="text/javascript">
	function fn_egov_downFile(atchFileId, fileSn){
		window.open("<c:url value='/cmm/fms/FileDown.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>");
	}
	
	function fn_egov_deleteFile(atchFileId, fileSn,obj){
		if(!confirm("<spring:message code='msg.delete.confirm'/>")){
			return;
		}
		$.ajax({
			url:"<c:url value='/com/task/taskScheduleDiaryDeleteFile.do'/>",
			data:{atchFileId:atchFileId,
				fileSn:fileSn				
			},
			type:"post",
			success:function(){
				$(obj).parent().next().remove();
				$(obj).parent().remove();
				fn_diaryReload();
			}
		});
	}
	function fn_save(){
		if($("select[name=taskStep]").val() == ''){
			alert("<spring:message code='msg.required'  arguments='진행단계'/>");
			$("select[name=taskStep]").focus();
			return;
		}
		if(document.frm.schdulNm.value == ''){
			alert("<spring:message code='msg.required'  arguments='일정명'/>");
			document.frm.schdulNm.focus();
			return;
		}
		if(document.frm.schdulSe.value == ''){
			alert("<spring:message code='msg.required'  arguments='일정구분'/>");
			return;
		}
		if(document.frm.schdulSdt.value == ''){
			alert("<spring:message code='msg.required'  arguments='시작일'/>");
			document.frm.schdulSdt.focus();
			return;
		}
		if(document.frm.schdulEdt.value == ''){
			alert("<spring:message code='msg.required'  arguments='종료일'/>");
			document.frm.schdulEdt.focus();
			return;
		}
		if(new Date(document.frm.schdulSdt.value)*1 > new Date(document.frm.schdulEdt.value)*1){
			alert("<spring:message code='msg.date.confirm'/>");
			document.frm.schdulSdt.focus();
			return;
		}
		if($("input[name=schdulPrgrsSttus]:checked").val() == '3' || $("input[name=schdulPrgrsSttus]:checked").val() == '5'){
			if(document.frm.schdulFinDt.value == ''){
				alert("<spring:message code='msg.required' arguments='완료일시'/>");
				document.frm.schdulFinDt.focus();
				return;
			}
			if($("input[name=schdulPrgrsSttus]:checked").val() == '5'){
				if(new Date(document.frm.schdulEdt.value)*1 >= new Date(document.frm.schdulFinDt.value)*1){
					alert("<spring:message code='msg.complete.comfirm'/>");
					document.frm.schdulFinDt.focus();
					return;
				}
			}
			
		}
		if($("input[name=schdulPrgrsSttus]:checked").val() == '4' || $("input[name=schdulPrgrsSttus]:checked").val() == '5'){
			if(document.frm.schdulLateReson.value == ''){
				alert("<spring:message code='msg.required' arguments='지연사유'/>");
				document.frm.schdulLateReson.focus();
				return;
			}
		}
		if(!confirm("<spring:message code='msg.update.confirm'/>")){
			return;
		}
		if($("input[type=checkbox]").is(":checked")){
			document.frm.schdulIpcrCode.value = 'Y'
		}else{
			document.frm.schdulIpcrCode.value = 'N'
		}
		$("#relCmngList span").each(function(){
			document.frm.ncrdId.value = document.frm.ncrdId.value + "//" + $(this).attr("ncrdId");
		});
		document.frm.action="<c:url value='/com/task/taskScheduleUpdate.do'/>";
		document.frm.submit();
	}
	
	function fn_diaryInsert(){
		if(!confirm("<spring:message code='msg.save.confirm'/>")){
			return;
		}
	 	var formData = new FormData($("#schdulDiaryForm")[0]);
		for (i = 0; i < $(".files").length; i++) {
			$(".files:eq(" + i + ")").attr("name", "file_" + i);
			$(".files:eq(" + i + ")").attr("id", "file_" + i);
			var files = document.getElementById("file_"+i).files;
			for(j=0;j<files.length;j++){
				formData.append("file_" + i +"_" + j ,files[j]);
			}		
		}
 		formData.append("diarySection" ,$("#diarySection").val());
		formData.append("diaryNm" ,$("#diaryNm").val());
		formData.append("diaryContens" ,$("#diaryContens").val()); 
        $.ajax({
            type : 'post',
            url : "<c:url value='/com/task/taskScheduleDiaryInsert.do'/>",
            data : formData,
            enctype : "multipart/form-data",
            processData : false,
            contentType : false,
            success : function() {  
            	fn_diaryTableReload();
            	fn_diaryReload();
            },
            error : function(error) {
               
            }
        }); 
	}
	function fn_diaryDelete(){		
		var diaryId = ""
		$(".chk").each(function(){
			if($(this).is(":checked")){
				diaryId += $(this).val()+","	
			}
			
		});
		if(diaryId == ""){
			alert("선택");
			return;
		}
		if(!confirm("<spring:message code='msg.delete.confirm'/>")){
			return;
		}
        $.ajax({
            type : 'post',
            url : "<c:url value='/com/task/taskScheduleDiaryDelete.do'/>",
            data : {diaryId:diaryId},
            success : function() {
            	fn_diaryTableReload();
            	fn_diaryReload();
            },
            error : function(error) {
               
            }
        }); 
	}
	function fn_diarySelect(obj){
		var atchFileId = $(obj).attr("atchFileId");
		var dropdownlist = $("#diarySection").data("kendoDropDownList");
		$("#diarySection").val($(obj).attr("diarySection"));
		dropdownlist.select(($(obj).attr("diarySection")*1)-1);
		$("#diaryNm").val($(obj).attr("diaryNm"));
		$("#diaryContens").val($(obj).attr("diaryContens"));
		$("#updateDiaryId").val($(obj).attr("diaryId"));
		$("#updateAtchFileId").val(atchFileId);
		$("#fileTd >span").remove();
		$("#fileTd >br").remove();
		$("#saveBtn").hide();
		$("#updateBtn").show();
		if(atchFileId != ''){
		  $.ajax({
            type : 'post',
            url : "<c:url value='/com/task/taskScheduleDiarySelectFile.do'/>",
            data : {atchFileId:atchFileId},
            success : function(data) {            
            	$(data.result).each(function(i){
            		var tmp = "<span><a href='javascript:fn_egov_downFile(\""+data.result[i].atchFileId+"\",\""+data.result[i].fileSn+"\")'>";
    				    tmp+=     data.result[i].orignlFileNm+"&nbsp;["+data.result[i].fileMg+"&nbsp;byte]";
    				    tmp+= "</a>";
    				    <c:if test="${result.schdulPrgrsSttus ne '3' and result.schdulPrgrsSttus ne '5' and result.schdulPrgrsSttus ne '6'}">
	    				    tmp+= "	<img src='<c:url value='/images/egovframework/com/cmm/icon/bu_icon_delete.gif' />' ";
	    				    tmp+="	width='19' height='18' onClick='fn_egov_deleteFile(\""+data.result[i].atchFileId+"\",\""+data.result[i].fileSn+"\",this);' alt='파일삭제'></span><br>";
    				    </c:if>
    				  $("#fileTd").append(tmp);  
            	});
            	
            },
            error : function(error) {
               
            }
        });  
		}
     
	}
	function fn_diaryTableReload(){
		$("#diaryTable").remove();
		var tmp = "<table class='tablewrite' id='diaryTable'>";
			tmp +=  "<colgroup>"
			tmp +=  	"<col class='width180'>"
			tmp +=  	"<col>"
			tmp +=  "</colgroup>"
		    tmp	+=	"<tbody>";
		    tmp	+=	   "<tr>";
		    tmp	+= 			"<th>구분</th>";
		    tmp	+=			"<td>";
		    tmp	+=				"<select class='select' id='diarySection'>";
		    tmp	+=					"<option value='1'>일반업무</option>"
		    tmp	+=						"<option value='2'>제약사항</option>"
		    tmp	+=						"<option value='3'>특수사항</option>"
		    tmp	+=					"</select>"
		    tmp	+=				"</td>"																		
		    tmp	+=			"</tr>"
		   	tmp	+=			"<tr>"
		    tmp	+=				"<th>제목</th>"
		   	tmp	+=				"<td colspan='3'><input type='text' class='k-textbox width100p' id='diaryNm'>"
		   	tmp	+=								"<input type='hidden' id='updateDiaryId'>"
		   	tmp	+=								"<input type='hidden' id='updateAtchFileId'></td>"
		    tmp	+=			"</tr>"
		    tmp	+=			"<tr>"
			tmp	+=		"<th>내용</th>"
			tmp	+=		"<td colspan='3'>"
			tmp	+=			"<textarea class='k-textbox width100p height200' cols='' id='diaryContens'></textarea>"										
			tmp	+=		"</td>"
			tmp	+=	"</tr>"
			tmp	+=	"<tr>"
			tmp	+=		"<th>첨부파일</th>"
			tmp	+=		"<td colspan='3' id='fileTd'>"
			tmp	+=			"<div class='k-widget k-upload k-header k-upload-sync k-upload-empty'>"
			tmp	+=				"<div class='k-button k-upload-button' aria-label='찾아보기'>"
			tmp	+=					"<input type='file' id='diaryFile' class='files' aria-label='찾아보기' >"
			tmp	+=					"<span>찾아보기</span>"
			tmp	+=				"</div>"
			tmp	+=			"</div>"									
			tmp	+=		"</td>"
			tmp	+=	"</tr>"
			tmp	+="<tr>"
			tmp	+=	"<td colspan='4'>"
			tmp	+=					"<button type='button' class='btn03' id='saveBtn' onclick='javascript:fn_diaryInsert();'>저장</button>"
			tmp	+=					"<button type='button' class='btn03' id='updateBtn' onclick='javascript:fn_diaryUpdate();'style='display:none;'>저장</button>"
			tmp	+=				"</td>"
			tmp	+=			"</tr>"							
			tmp	+=		"</tbody>"
			tmp	+=	"</table>"
			$("#diaryListTable").after(tmp);
		    $("#diarySection").kendoDropDownList();
		    $("#diaryFile").kendoUpload();
					
	}
	
	function fn_diaryReload(){
		$.ajax({
			url:"<c:url value='/com/task/taskScheduleDiaryList.do'/>",
			type:"post",
			data:{schdulId:document.schdulDiaryForm.schdulId.value},
			success:function(data){
				$("#diaryListTable tr:not(:first)").remove();
				$(data.result).each(function(i){
					 var tmp = "<tr onclick='javascript:fn_diarySelect(this);' diaryId='"+data.result[i].diaryId+"' atchFileId='"+data.result[i].atchFileId+"' diaryNm='"+data.result[i].diaryNm+"' diaryContens='"+data.result[i].diaryContens+"' diarySection='"+data.result[i].diarySection+"'>";
					    tmp+=   "<td><input type='checkbox' class='chk' value='"+data.result[i].diaryId+"'/></td>";
					    tmp+=   "<td>";
					    if(data.result[i].diarySection == "1"){
					    	tmp+= "일반업무</td>"	
					    }else if(data.result[i].diarySection == "2"){
					    	tmp+= "제악사항</td>"	
					    }else if(data.result[i].diarySection == "3"){
					    	tmp+= "특수사항</td>"	
					    }else{
					    	tmp+= "</td>"	
					    }		
					    tmp+= "<td>"+data.result[i].diaryNm+"</td>";
					    tmp+= "<td>"+data.result[i].frstRegisterPnttm.substring(0,10)+"</td>";
					    if(data.result[i].cnt > 0){
					    	 tmp+= "<td><i class='fas fa-save'></i></td>"
					    }else{
					    	tmp+= "<td></td>"
					    }
					    tmp+= "</tr>"
				$("#diaryListTable").append(tmp); 
				})
			}
		});
	}
	
	
	function fn_diaryUpdate(){
		if(!confirm("<spring:message code='msg.save.confirm'/>")){
			return;
		}
	 	var formData = new FormData($("#schdulDiaryForm")[0]);
	 	for (i = 0; i < $(".files").length; i++) {
			$(".files:eq(" + i + ")").attr("name", "file_" + i);
			$(".files:eq(" + i + ")").attr("id", "file_" + i);
			var files = document.getElementById("file_"+i).files;
			for(j=0;j<files.length;j++){
				formData.append("file_" + i +"_" + j ,files[j]);
			}		
		}
 		formData.append("diarySection" ,$("#diarySection").val());
		formData.append("diaryNm" ,$("#diaryNm").val());
		formData.append("diaryContens" ,$("#diaryContens").val());
		formData.append("diaryId" ,$("#updateDiaryId").val());
		formData.append("atchFileId" ,$("#updateAtchFileId").val());
		
		 $.ajax({
	            type : 'post',
	            url:"<c:url value='/com/task/taskScheduleDiaryUpdate.do'/>",
	            data : formData,
	            enctype : "multipart/form-data",
	            processData : false,
	            contentType : false,
	            success : function() {  
	            	fn_diaryTableReload();
	            	fn_diaryReload();
	            },
	            error : function(error) {
	               
	            }
	        }); 
	}
	
	function fn_goDetail(){
		document.frm.action="<c:url value='/com/task/taskDetail.do'/>";
		document.frm.submit();
	}
	
	$(document).ready(function(){
		$("input[name=schdulPrgrsSttus]").on("click",function(){
			if($(this).val() == '4' || $(this).val() == '5'){
				$("input[name=schdulLateReson]").attr("readonly",false);
			}else{
				$("input[name=schdulLateReson]").attr("readonly",true);
			}
		});
		
		$("input[name=schdulPrgrsSttus]").on("click",function(){
			var datetimepicker  = $("input[name=schdulFinDt]").data("kendoDateTimePicker")
			if($(this).val() == '3' || $(this).val() == '5'){
				 datetimepicker.enable();
			}else{
				 datetimepicker.enable(false);
			}
		});
		
		var wrId = "<c:out value='${result.frstRegisterId }'/>";
		var loginId = "<c:out value='${loginVO.emplyrNo }'/>";
		var mngYn = "<c:out value='${loginVO.manageAt }'/>"
		var status = "<c:out value='${result.schdulPrgrsSttus }'/>"
		if(wrId != loginId && mngYn != 'Y'){
			var dropdownlist = $("select[name=taskStep]").data("kendoDropDownList");
			var dropdownlist2 = $("select[name=schdulPrgrs]").data("kendoDropDownList");		
			var dropdownlist3 = $("#diarySection").data("kendoDropDownList");		
			
			dropdownlist.readonly();
			dropdownlist2.readonly();
			dropdownlist3.readonly();
			$("input[name=schdulSe]").prop("disabled",true);
			$("input").prop("disabled",true);
			$("textarea").prop("disabled",true);
			$("input[name=taskId]").prop("disabled",false);
			$("#cMngFormNcrdId").attr("disabled",false);
			
		}
	});
/* 	function fn_selectFileClear(){
		var upload = $(".files").data("kendoUpload");
	      upload.clearAllFiles();
	} */
	
	function fn_returnRelaCmng(data){ 
		var flag = false;
	 	for(i = 0 ;i<data.length;i++){
	 		var tmp = data[i].split("||");
	 		flag =false;
	 		var tmp = data[i].split("||");
	 		$("#relCmngList span").each(function(){
	 			if(tmp[0] == $(this).attr("ncrdId")){
		 			alert(tmp[1]+"  "+"<spring:message code='msg.relaCmng.dupli'/>");
		 			flag = true;
		 			return false;
		 		}
	 		});
	 		if(flag){
	 			continue;
	 		}
	 		var template = "";
	 		if($("#relCmngList span").length != 0){
	 			template  += "<span ncrdId="+tmp[0]+">,"+tmp[1]+"("+tmp[2]+")" 				
			}else{
				template  += "<span ncrdId="+tmp[0]+">"+tmp[1]+"("+tmp[2]+")" 
			}
	 		
 		    template += 	"<button type='button' onclick='javascript:fn_relaRemove(this)'>X</button>";
 		    template +=   "</span>" 
	 		$("#relCmngList").append(template);			
		}  
		
	}
	function fn_relaRemove(obj){
		$(obj).parent().remove();
	}
	
	function fn_openRelaCmngPopup(){
		window.open("<c:url value='/com/task/popup/schdulRelaCmngPopup.do'/>","","width=1100px,height=800px");
	}
	
	function fn_goCmngDetail(ncrdId){
		document.cMngForm.ncrdId.value=ncrdId;
		document.cMngForm.action="<c:url value='/com/business/company/companyEmplyrView.do'/>";
		document.cMngForm.submit();
	}
</script>
<div id="subwrap">
<form name="cMngForm" method="post">
	<input type="hidden" name="ncrdId" value="" id="cMngFormNcrdId"/>
</form>
<form name="schdulDiaryForm" enctype="multipart/form-data" method="post" id="schdulDiaryForm">
	<input type="hidden" name="taskId" value="<c:out value='${indvdlSchdulManageVO.taskId }'/>"/>
	<input type="hidden" name="taskStepId" value="<c:out value='${indvdlSchdulManageVO.taskStepId }'/>"/>
	<input type="hidden" name="schdulId" value="<c:out value='${indvdlSchdulManageVO.schdulId }'/>"/>
</form>
<form name="frm" method="post">
		<input type="hidden" name="ncrdId" value=""/>
		<input type="hidden" name="schdulIpcrCode" value="<c:out value='${result.schdulIpcrCode }'/>"/>
		<input type="hidden" name="taskId" value="<c:out value='${indvdlSchdulManageVO.taskId }'/>"/>
		<input type="hidden" name="taskStepId" value="<c:out value='${indvdlSchdulManageVO.taskStepId }'/>"/>
		<input type="hidden" name="schdulId" value="<c:out value='${indvdlSchdulManageVO.schdulId }'/>"/>
		<table class="tablewrite">
			<colgroup>
				<col class="width100">
				<col class="width600">
			</colgroup>
			<tbody>
				<tr>
					<th>연결된과제</th>
					<td>
						과제명  :<c:out value="${taskStep[0].taskNm }"/><br>
						진행단계
						<select class="select" name="taskStep" <c:if test="${loginVO.manageAt ne 'Y' and( result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6' )}">disabled="disabled"</c:if>>
							<c:forEach var="step" items="${taskStep }" varStatus="status">
								<c:if test="${step.ptaskStepId ne '0' or (step.ptaskStepId eq '0' and step.cnt eq '0')}">
									<option value="${step.taskStep }" <c:if test="${result.taskStep eq  step.taskStep }">selected="selected"</c:if>><c:out value="${step.stepNm }"/></option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>일정명</th>
					<td>
						<input type="text" class="k-textbox" name="schdulNm" value="<c:out value='${result.schdulNm }'/>" <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6') }">readonly="readonly"</c:if>/>
						<input type="checkbox" class="k-checkbox" id="checkbox" <c:if test="${result.schdulIpcrCode eq 'Y' }">checked="checked"</c:if><c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6' )}">disabled="disabled"</c:if>><label class="k-checkbox-label" for="checkbox" >중요일정</label>
					</td>				
				</tr>
				<tr>
					<th>일정구분</th>
					<td>
						<c:forEach var="code" items="${codeList }" varStatus="status">
							<input type="radio" class="" id="radio304${status.index }" name="schdulSe" value="<c:out value='${code.code }'/>" <c:if test="${result.schdulSe eq code.code }">checked="checked"</c:if> <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6' )}">disabled="disabled"</c:if>/><label for="radio304${status.index }"><c:out value='${code.codeNm }'/></label>	
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>일정기간</th>
					<td>
						<fmt:parseDate var="sdt" value='${result.schdulSdt }' pattern="yyyy/MM/dd HH:mm"/>
						<fmt:parseDate var="edt" value='${result.schdulEdt }' pattern="yyyy/MM/dd HH:mm"/>
						시작<input class="datetimepicker width200"  <c:if test="${loginVO.manageAt ne 'Y' }">readonly="readonly"</c:if> name="schdulSdt" placeholder="시행일자" title="시행일자" value="<fmt:formatDate value='${sdt }' pattern="yyyy/MM/dd HH:mm"/>" >~
						종료<input class="datetimepicker width200"  <c:if test="${loginVO.manageAt ne 'Y' }">readonly="readonly"</c:if> name="schdulEdt" placeholder="시행일자" title="시행일자" value="<fmt:formatDate value='${edt }' pattern="yyyy/MM/dd HH:mm"/>">
						<input type="radio" class=""  name="schdulType" id="schdulType1" value="1" <c:if test="${loginVO.manageAt ne 'Y' }">disabled="disabled"</c:if> <c:if test="${result.schdulType eq '1' }">checked="checked"</c:if>/><label for="schdulType1">일반</label>
						<input type="radio" class=""  name="schdulType" id="schdulType2" value="2" <c:if test="${loginVO.manageAt ne 'Y' }">disabled="disabled"</c:if> <c:if test="${result.schdulType eq '2' }">checked="checked"</c:if>/><label for="schdulType2">야간</label>
						<input type="radio" class=""  name="schdulType" id="schdulType3" value="3" <c:if test="${loginVO.manageAt ne 'Y' }">disabled="disabled"</c:if> <c:if test="${result.schdulType eq '3' }">checked="checked"</c:if>/><label for="schdulType3">주말</label>
					</td>
				</tr>			
				<tr>
					<th>일정진행상태</th>
					<td>
						<input type="radio" class="" id="radio1" name="schdulPrgrsSttus" value="1" <c:if test="${result.schdulPrgrsSttus eq '1' }">checked="checked" </c:if> <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '4' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6') }">disabled="disabled"</c:if>/><label for="radio1">진행전</label>
						<input type="radio" class="" id="radio2" name="schdulPrgrsSttus" value="2" <c:if test="${result.schdulPrgrsSttus eq '2' }">checked="checked" </c:if> <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '4' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6') }">disabled="disabled"</c:if>/><label for="radio2">진행중</label>
						<select class="select" name="schdulPrgrs" <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6') }">disabled="disabled"</c:if>>
							<c:forEach begin="0" end="10" varStatus="status" >
								<option value="${status.index*10 }" <c:if test="${result.schdulPrgrs eq  status.index*10}">selected="selected"</c:if>><c:out value='${status.index*10 }'/>%</option>
							</c:forEach>
						</select>
						<input type="radio" class="" id="radio3" name="schdulPrgrsSttus" value="3" <c:if test="${result.schdulPrgrsSttus eq '3' }">checked="checked" </c:if> <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '4' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6') }">disabled="disabled"</c:if>/><label for="radio3">완료</label>
						<input type="radio" class="" id="radio4" name="schdulPrgrsSttus" value="4" <c:if test="${result.schdulPrgrsSttus eq '4' }">checked="checked" </c:if> <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or  result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6') }">disabled="disabled"</c:if>/><label for="radio4">지연</label>
						<input type="radio" class="" id="radio5" name="schdulPrgrsSttus" value="5" <c:if test="${result.schdulPrgrsSttus eq '5' }">checked="checked" </c:if> <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or  result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6') }">disabled="disabled"</c:if>/><label for="radio5">지연완료</label>
						<input type="radio" class="" id="radio6" name="schdulPrgrsSttus" value="6" <c:if test="${result.schdulPrgrsSttus eq '6' }">checked="checked" </c:if> <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or  result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6') }">disabled="disabled"</c:if>/><label for="radio6">중단</label>
						완료일시<input class="datetimepicker width200" name="schdulFinDt" placeholder="시행일자" title="시행일자" value="<c:out value="${result.schdulFinDt }"/>"<%--  <c:if test="${result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '4' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6' }">disabled="disabled"</c:if> --%>disabled="disabled">
						<input type="text" class="k-textbox" name="schdulLateReson" value="<c:out value='${result.schdulLateReson }'/>" <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6') }">disabled="disabled"</c:if>/>
					</td>
				</tr>
					<tr>
					<th>관련업체사원</th>
					<td>
						<div id="relCmngList">
							<c:forEach var="relaCmng" items="${relaCmng }" varStatus="status">
								<c:choose>
									<c:when test="${status.first }">
										<span ncrdId="<c:out value='${relaCmng.ncrdId }'/>">
											<a href="#" onclick="javascript:fn_goCmngDetail('<c:out value='${relaCmng.ncrdId }'/>'); return false;"><c:out value='${relaCmng.nm }'/>(<c:out value='${relaCmng.cmpnyNm }'/>)</a>
											<c:if test="${loginVO.manageAt eq 'Y' or( result.schdulPrgrsSttus ne '3' and result.schdulPrgrsSttus ne '5' and result.schdulPrgrsSttus ne '6' and result.frstRegisterId eq loginVO.emplyrNo)}">
												<button type='button' onclick='javascript:fn_relaRemove(this)'>X</button>
											</c:if> 
										</span>
									</c:when>
									<c:otherwise>
										 <span ncrdId="<c:out value='${relaCmng.ncrdId }'/>">
											,<a href="#" onclick="javascript:fn_goCmngDetail('<c:out value='${relaCmng.ncrdId }'/>'); return false;"><c:out value='${relaCmng.nm }'/>(<c:out value='${relaCmng.cmpnyNm }'/>)</a>
											<c:if test="${loginVO.manageAt eq 'Y' or( result.schdulPrgrsSttus ne '3' and result.schdulPrgrsSttus ne '5' and result.schdulPrgrsSttus ne '6' and result.frstRegisterId eq loginVO.emplyrNo)}">
												<button type='button' onclick='javascript:fn_relaRemove(this)'>X</button>
											</c:if>
										</span>
									</c:otherwise>
								</c:choose>								
							</c:forEach>
						</div>
						<c:if test="${loginVO.manageAt eq 'Y' or( result.schdulPrgrsSttus ne '3' and result.schdulPrgrsSttus ne '5' and result.schdulPrgrsSttus ne '6' and result.frstRegisterId eq loginVO.emplyrNo)}">
							<button type="button" class="btn03" onclick="javascript:fn_openRelaCmngPopup();">선택</button>
						</c:if>
					</td>
				</tr>
				<tr>
					<th>업무일지</th>
					<td>
						<c:if test="${loginVO.manageAt eq 'Y' or (result.schdulPrgrsSttus ne '3' and result.schdulPrgrsSttus ne '5' and result.schdulPrgrsSttus ne '6' and result.frstRegisterId eq loginVO.emplyrNo)}">
							<button type="button" class="btn03" onclick="javascript:fn_diaryTableReload();">추가</button>
						<!-- 	<button type="button" class="btn03" onclick="javascript:fn_diaryModify();">수정</button> -->
							<button type="button" class="btn03" onclick="javascript:fn_diaryDelete();">삭제</button>
						</c:if>
						<table class="tablewrite" id="diaryListTable">
							<colgroup>
								<col class="width80">
								<col class="width100">
								<col>
								<col>
								<col class="width150">								
								<col class="width80">
							</colgroup>
							<thead>
								<tr>
									<th></th>
									<th>구분</th>
									<th>제목</th>
									<th>작성일자</th>
									<th>첨부파일</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${empty diary }">
										<tr>
											<td colspan="5"></td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="diary" items="${diary }" varStatus="status">
											<tr onclick="javascript:fn_diarySelect(this);" diaryId="<c:out value='${diary.diaryId }'/>" atchFileId="<c:out value='${diary.atchFileId }'/>" diaryNm="<c:out value='${diary.diaryNm }'/>" diaryContens="<c:out value='${diary.diaryContens }'/>" diarySection="<c:out value='${diary.diarySection }'/>">
												<td>
													<input type="checkbox" class="chk" value="<c:out value='${diary.diaryId }'/>"/>													
												</td>
												<td>
													<c:choose>
														<c:when test="${diary.diarySection eq '1'}">
															일반업무
														</c:when>
														<c:when test="${diary.diarySection eq '2'}">
															제약사항
														</c:when>
														<c:when test="${diary.diarySection eq '3'}">
															특수사항
														</c:when>
													</c:choose>
												</td>
												<td>
													<c:out value="${diary.diaryNm }"/>
												</td>
												<td>
													<c:out value="${fn:substring(diary.frstRegisterPnttm,0,10) }"/>
												</td>
												<td>
													<c:if test="${diary.cnt > 0 }">
														<i class="fas fa-save"></i>
													</c:if>													
												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
								
							</tbody>
						</table>
						<%-- <c:if test="${result.schdulPrgrsSttus ne '3' and result.schdulPrgrsSttus ne '5' and result.schdulPrgrsSttus ne '6'}"> --%>
							<table class="tablewrite" id="diaryTable">
								<colgroup>
									<col class="width180">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th>구분</th>
										<td>
											<select class="select" id="diarySection" <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6')}">disabled="disabled"</c:if>>
												<option value="1">일반업무</option>
												<option value="2">제약사항</option>
												<option value="3">특수사항</option>
											</select>
										</td>																		
									</tr>
									<tr>
										<th>제목</th>
										<td colspan="3">
											<input type="text" class="k-textbox width100p" id="diaryNm" <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6')}">readonly="readonly"</c:if>>
											<input type="hidden" id="updateDiaryId">
											<input type="hidden" id="updateAtchFileId">
										</td>
									</tr>
									<tr>
										<th>내용</th>
										<td colspan="3">
											<textarea  class="k-textbox width100p height200" id="diaryContens" <c:if test="${loginVO.manageAt ne 'Y' and (result.schdulPrgrsSttus eq '3' or result.schdulPrgrsSttus eq '5' or result.schdulPrgrsSttus eq '6')}">readonly="readonly"</c:if>></textarea>										
										</td>
									</tr>
									<tr>
										<th>첨부파일</th>
										<td colspan="3" id="fileTd">
											<c:if test="${loginVO.manageAt eq 'Y' or (result.schdulPrgrsSttus ne '3' and result.schdulPrgrsSttus ne '5' and result.schdulPrgrsSttus ne '6' and result.frstRegisterId eq loginVO.emplyrNo)}">
												<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
													<div class="k-button k-upload-button" aria-label="찾아보기">
														<input type="file" class="files" aria-label="찾아보기" >
														<span>찾아보기</span>
													</div>
												</div> 
											</c:if>										
										</td>
									</tr>
									<c:if test="${loginVO.manageAt eq  'Y' or (result.schdulPrgrsSttus ne '3' and result.schdulPrgrsSttus ne '5' and result.schdulPrgrsSttus ne '6' and result.frstRegisterId eq loginVO.emplyrNo)}">
										<tr>
											<td colspan="4">
												<button type="button" class="btn03" id="saveBtn" onclick="javascript:fn_diaryInsert();">저장</button>
												<button type="button" class="btn03" id="updateBtn" onclick="javascript:fn_diaryUpdate();" style="display:none;">저장</button>
											</td>
										</tr>
									</c:if>								
								</tbody>
							</table>
						<%-- </c:if> --%>
					</td>
				</tr>
													
			</tbody>
		</table>
</form>
		<button type="button" class="btn03" onclick="javascript:fn_goDetail();">취소</button>
	<c:if test="${loginVO.manageAt eq 'Y' or( result.schdulPrgrsSttus ne '3' and result.schdulPrgrsSttus ne '5' and result.schdulPrgrsSttus ne '6' and result.frstRegisterId eq loginVO.emplyrNo)}"><button type="button" class="btn03" onclick="javascript:fn_save();">저장</button></c:if>
</div>