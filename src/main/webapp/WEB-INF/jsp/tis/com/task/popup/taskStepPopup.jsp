<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<meta charset="utf-8">
<title>(주)이튜 - 통합정보시스템</title>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/common.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/layout.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/board.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/fontawesome-all.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/kendo.common.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/kendo.material.min.css'/>">

<script type="text/javascript" src="<c:url value='/js/tis/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/tis/kendo.all.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/tis/bootstrap.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/tis/common.js'/>"></script>
<script type="text/javascript">
 	function fn_changeStep(){
 		document.frm.action="<c:url value='/com/task/popup/taskStepPopup.do'/>";
 		document.frm.submit();
 	}
 	function fn_save(){
 		var flag= true
 		$(".datetimepicker").each(function(i){
			  var id = $(this).prop("id");
			  if(id == ''){
				  return true;
			  }
			  if($(this).val() == ''){
				  if(id.indexOf("dtpS") != -1){
					  alert("<spring:message code = 'msg.required' arguments='시작일'/>");
					  $(this).focus();
					  flag = false;
					  return false; 
				  }
				  if(id.indexOf("dtpE") != -1){
					  alert("<spring:message code = 'msg.required' arguments='종료일'/>");
					  $(this).focus();
					  flag = false;
					  return false; 
				  }
				  
			  }else{
				  if(id.indexOf("dtpE") != -1){
					  id= id.replace("dtpE","");
					  if(new Date($("#dtpS"+id).val())*1 > new Date($(this).val())*1){
						  alert("<spring:message code='msg.date.confirm'/>");
						  $(this).focus();
							flag = false;
							return false;
					  }
				  }
			  }
		  }); 
 		if(!flag){
 			return;
 		}
 		if(!confirm("<spring:message code='msg.save.confirm' />")){
 			return;
 		}
 		if(window.opener.document.frm.taskStepId.value != ''){
 			document.subForm.iUGbn.value = "U";	
 		}else{
 			document.subForm.iUGbn.value = "I";
 			window.opener.document.frm.taskStepId.value = $(":selected").val();
 		}
 		document.subForm.taskStepId.value = $(":selected").val();
 		document.subForm.taskId.value = window.opener.document.frm.taskId.value;

 		$.ajax({
 			url:"<c:url value='/com/task/popup/taskStepSave.do'/>",
 			type:"post",
 			data:$("form[name=subForm]").serializeArray(),
 			success:function(data){
 				if(data.result == "OK"){
 					alert("<spring:message code='msg.save'/>");
 					window.opener.fn_reload();
 					window.close();
 				}
 			}
 		});
 		
 	}

   	$(document).ready(function (){
 		$("input[type=checkbox]").on("click",function(){
 			if($(this).is(":checked")){
 				var no = $(this).attr("id").replace("check","");			
 	 			var datepickerS= $("#dtpS"+no).data("kendoDatePicker");
 	 			var datepickerE= $("#dtpE"+no).data("kendoDatePicker");
 	 			datepickerS.readonly(false);
 	 			datepickerE.readonly(false);
 			}else{
 				var no = $(this).attr("id").replace("check","");			
 	 			var datepickerS= $("#dtpS"+no).data("kendoDatePicker");
 	 			var datepickerE= $("#dtpE"+no).data("kendoDatePicker");
 	 			datepickerS.readonly();
 	 			datepickerE.readonly();
 			}
 			
 		});	
 		
 		
 		
 		$(".datetimepicker").on("blur",function(){
 			//alert($(this).val());
 		});
 	});  


</script>
<form name="frm" method="post">
	<input type="hidden" name="taskSdate" value="<c:out value='${taskVO.taskSdate }'/>"/>
	<input type="hidden" name="taskEdate" value="<c:out value='${taskVO.taskEdate }'/>"/>
	<select name="taskStepId"  class="select width150" onchange="javascript:fn_changeStep();" id="taskStepId" >
		<option value="">선택</option>
		<c:forEach var="step" items="${taskSetpMaster }" varStatus="status">
			<option value="<c:out value='${step.taskStepId }'/>" <c:if test="${taskVO.taskStepId eq step.taskStepId }">selected="selected"</c:if>><c:out value='${step.taskStepNm }'/></option>
		</c:forEach>
		
	</select>
</form>
<form name="subForm" method="post">
<input type="hidden" name="taskId"/>
<input type="hidden" name="iUGbn"/>
<input type="hidden" name="taskStepId"/>
<table class="tablelist">
		<colgroup>
			<col >
			<col >
			<col >
			<col >
			<col >
		</colgroup>
		<thead>	
			<tr>
				<th colspan="2">구분</th>
				<th>진행단계</th>
				<th>시작예정일</th>
				<th>완료예정일</th>
				<th style="display:none;" class="hiddenTd"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${resultList}" varStatus="status">
				<input type="hidden" name="taskStepVOList[${status.index }].taskStep" value="<c:out value='${result.taskStep }'/>"/>
				<input type="hidden" name="taskStepVOList[${status.index }].stepNm" value="<c:out value='${result.stepNm }'/>"/>
				<input type="hidden" name="taskStepVOList[${status.index }].ptaskStepId" value="<c:out value='${result.ptaskStepId }'/>"/>
				<input type="hidden" name="taskStepVOList[${status.index }].ordr" value="<c:out value='${result.ordr}'/>"/>			
				<tr>
					<td>
						<c:if test="${result.ptaskStepId eq '0' }">
							<c:out value="${result.ordr }"/>
						</c:if>
					</td>
					<td>
						<c:if test="${result.ptaskStepId ne '0'  }">
							<c:out value="${result.ordr }"/>
						</c:if>
					</td>
					<td>
						<c:out value="${result.stepNm }"/>
					</td>
					<td>
						<input class="datepicker width200" id="dtpS${status.index }" placeholder="시행일자" title="시행일자" name="taskStepVOList[${status.index }].planSdt" value="<c:out value='${result.planSdt }'/>" >						
					</td>
					<td>
						<input class="datepicker width200" id="dtpE${status.index }"  placeholder="시행일자" title="시행일자" name="taskStepVOList[${status.index }].planEdt" value="<c:out value='${result.planEdt }'/>" >
					</td>
					<td style="display:none;" class="hiddenTd"><input type="checkbox" id="check${status.index }" class="k-checkbox"><label class="k-checkbox-label" for="check${status.index }"></label></td>	
				</tr>
			</c:forEach>						
		</tbody>
	</table>
	</form>
	<button type="button" class="btn01" onclick="javascript:fn_save();">저장</button>		
	<button type="button" class="btn01" onclick="javascript:window.close();">닫기</button>	
	<script>
		
		  if(window.opener.document.frm.taskStepId.value != ''){
		 	  $(document).ready(function(){
				  var dropdownlist = $("#taskStepId").data("kendoDropDownList");
				  dropdownlist.readonly();
			 	   $(".datepicker").each(function(i){
					  var id = $(this).prop("id");
					  if(id == ''){
						  return true;
					  }
					 /*  var datepicker= $("#"+id).data("kendoDatePicker");
					  
					  datepicker.readonly(); */
					  $(this).attr("readonly",true);
				  }) 
				

	 			/* $(".hiddenTd").show();
	 			$("button").hide();  */
			  }) 
			  
 		}else{
 			$(document).ready(function(){
 				 $(".datepicker").each(function(i){
 					  var id = $(this).prop("id");
 					  if(id == ''){
 						  return true;
 					  }
 					  var datepicker= $("#"+id).data("kendoDatePicker");
 					  if(id.indexOf("dtpS") != -1){
 						  datepicker.value("<c:out value='${taskVO.taskSdate}'/>");  
 					  }
 					  if(id.indexOf("dtpE") != -1){
 						  datepicker.value("<c:out value='${taskVO.taskEdate}'/>");
 					  }
 					 $(this).attr("readonly",true);
 				  });
 			});
 			 
 		} 
	</script>