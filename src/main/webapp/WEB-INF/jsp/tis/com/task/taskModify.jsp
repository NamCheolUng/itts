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
<script type="text/javascript">
	$(document).ready(function(){
		$("input[name=taskSdate]").attr("readonly",true);
		$("input[name=taskEdate]").attr("readonly",true);
	});
	var flag = true;
	var taskNm = "<c:out value='${result.taskNm}'/>";
	function fn_save(){		
		if(!flag){
			alert("<spring:message code='msg.dupli.confirm' arguments='과제'/>");
			return;
		}else{
			if(taskNm != document.subForm.taskNm.value){
				alert("<spring:message code='msg.dupli.confirm' arguments='과제'/>");
				return;
			}			 
		}
		if($("#companyNm").val() == ''){
			alert("<spring:message code='msg.required'  arguments='발주처'/>");
			return;
		}
		if(document.subForm.taskSdate.value == ''){
			alert("<spring:message code='msg.required'  arguments='시행일자'/>");
			document.subForm.taskSdate.focus();
			return;
		}
		
		if(document.subForm.taskEdate.value == ''){
			alert("<spring:message code='msg.required'  arguments='시행일자'/>");
			document.subForm.taskEdate.focus();
			return;
		}
		if(new Date(document.subForm.taskSdate.value)*1 > new Date(document.subForm.taskEdate.value)*1){
			 alert("<spring:message code='msg.date.confirm'/>");
			 return;
		}
		if(document.subForm.mainDept.value == ''){
			alert("<spring:message code='msg.required'  arguments='주부서'/>");
			document.subForm.mainDept.focus();
			return;
		}
		if(!confirm("<spring:message code='msg.save.confirm' />")){
			return;
		}
		if($("#relTaskList span").length == 0 ){
			document.subForm.relaTaskId.value= "";
		}else{
			$("#relTaskList span").each(function (){
				document.subForm.relaTaskId.value= document.subForm.relaTaskId.value + "//" + $(this).children().val();
			});
		}
		
		
		document.subForm.action="<c:url value='/com/task/taskUpdate.do'/>";
		document.subForm.submit();
	}
	function fn_taskNmComapre(){
		var nm = document.subForm.taskNm.value;
		if(nm == ""){
			alert("<spring:message code='msg.required'  arguments='과제명'/>");
			return;
		}
		$.ajax({
			type:"post",
			url:"<c:url value='/com/task/taskNmCompare.do'/>",
			data:{taskNm:nm},
			success:function(data){
				if(data.cnt != 0){
					alert("<spring:message code='msg.dupli' />");
					flag = false;
				}else{
					alert("<spring:message code='msg.dupli.ok' />");
					flag = true;
					taskNm = nm;					
				}
			}				
		});
	}
	function fn_companyListPopup(){
		window.open("<c:url value='/com/task/popup/companyListPopup.do'/>","","width=800px,height=800px");
	}
	function fn_taskListPopup(){
		window.open("<c:url value='/com/task/popup/taskListPopup.do'/>","","width=800px,height=800px");
	}
	
	function fn_relaRemove(obj){
		$(obj).parent().remove();
	}
	
	function fn_returnTaskPopup(data){
		var tmp =  data.split("//");
		var exit = false;
		$("#relTaskList span").each(function (){
			if($(this).children().val() == tmp[0]){
				alert("'"+tmp[1]+"'" +" <spring:message code='msg.relaTask.dupli'/>");
				exit = true;
				return false;
				
			}
		});
	 	if(exit){
			return;
		} 
		var template ="";
		if($("#relTaskList span").length != 0){
			template += "<span>";
			template += ",";
		}else{
			template += "<span>";
		}
		    
			template += 	tmp[1] + "<input type='hidden' value='"+tmp[0]+"' >";
			template += 	"<button type='button' onclick='javascript:fn_relaRemove(this)'>X</button>";
			template += "</span>"  
				
		
		$("#relTaskList").append(template);	
		
		
	}
	
	
	function fn_returnCompanyPopup(id,nm){
		document.subForm.adbkId.value=id;
		$("#companyNm").val(nm);
	}
</script>
<div id="subwrap">
	<div class="tabmenu">
		
	</div>
	<div class="space10"></div>
	<form name="subForm" method="POST">
		<input type="hidden" name="taskId" value="${result.taskId }"/>
		<input type="hidden" value="N" name="delAt"/>
		<input type="hidden" value="" name="relaTaskId"/>	
		<input type="hidden"  name="taskStepId" value="<c:out value='${result.taskStepId }'/>"/>	
		<table class="tablewrite">
			<colgroup>
				<col class="width180">
				<col class="width600">
				<col class="width180">
				<col class="width600">
			</colgroup>
			<tbody>
				<tr>
					<th>과제명</th>
					<td colspan="3">
						<input type="text" class="k-textbox width100p" name="taskNm" value="<c:out value='${result.taskNm }'/>">
						<button type="button" class="btn03" onclick="javascript:fn_taskNmComapre();">중복검사</button>
					</td>
				</tr>
				<tr>
					<th>발주처</th>
					<td>
						<input type="text" class="k-textbox width100p" id="companyNm" value="<c:out value='${result.cmpnyNm }'/>" readonly="readonly">
						<input type="hidden" name="adbkId" value="<c:out value='${result.adbkId }'/>">
						<button type="button" class="btn03" onclick="javascript:fn_companyListPopup();">찾기</button>
					</td>
					<th>사업기간</th>
					<td>
						<input class="datepicker width200" name="taskSdate" placeholder="시행일자" title="시행일자" value="<c:out value='${result.taskSdate }'/>">~
						<input class="datepicker width200" name="taskEdate" placeholder="시행일자" title="시행일자" value="<c:out value='${result.taskEdate }'/>">
					</td>
				</tr>						
				<tr>
					<th>사업범위</th>
					<td colspan="3"><textarea class="k-textbox width100p height300" name="chargerNm" ><c:out value='${result.chargerNm }'/></textarea></td>
				</tr>
				<tr>
					<th>주요키워드</th>
					<td colspan="3"><input type="text" class="k-textbox width100p" name="keyword" value="<c:out value='${result.keyword }'/>"></td>
				</tr>
				<tr>
					<th>과제진행상태</th>
					<td colspan="3">
						<label for="radio01">진행전</label><input type="radio" id="radio01" class="" value="01" name="taskStatusCd" <c:if test="${result.taskStatusCd eq '01' }">checked="checked"</c:if>>
						<label for="radio02">진행중</label><input type="radio" id="radio02" class="" value="02" name="taskStatusCd" <c:if test="${result.taskStatusCd eq '02' }">checked="checked"</c:if>>
						<label for="radio03">완료</label><input type="radio" id="radio03" class=""  value="03" name="taskStatusCd" <c:if test="${result.taskStatusCd eq '03' }">checked="checked"</c:if>>
						<label for="radio04">지연</label><input type="radio" id="radio04" class=""  value="04" name="taskStatusCd" <c:if test="${result.taskStatusCd eq '04' }">checked="checked"</c:if>>
						<label for="radio05">지연완료</label><input type="radio" id="radio05" class="" value="05" name="taskStatusCd" <c:if test="${result.taskStatusCd eq '05' }">checked="checked"</c:if>>
						<label for="radio06">중단</label><input type="radio" id="radio06" class="" value="06" name="taskStatusCd" <c:if test="${result.taskStatusCd eq '06' }">checked="checked"</c:if>>
					</td>
				</tr>
				<tr>
				    <th>과제공유설정</th>
					<td colspan="3">
						<label for="radio07">공개</label><input type="radio" id="radio07" class="" value="Y" name="commonAt" <c:if test="${result.commonAt eq 'Y' }">checked="checked"</c:if>>
						<label for="radio08">비공개</label><input type="radio" id="radio08" class="" value="N" name="commonAt" <c:if test="${result.commonAt eq 'N' }">checked="checked"</c:if>>
					</td>
				</tr>
				<tr>
				    <th>관련과제</th>
					<td colspan="3">
						<div id="relTaskList">
							<c:forEach var="rela" items="${relaResult }" varStatus="status">
								<span>							
									<c:out value="${rela.taskNm }"/>
									<input type="hidden" value="${rela.relaTaskId }" class="relaTaskId"/>
									<button type="button" onclick="javascript:fn_relaRemove(this);">X</button>													
									<c:if test="${!status.last }">
										,
									</c:if>
								</span>
							</c:forEach>
						</div>
						<button type="button" class="btn03" onclick="javascript:fn_taskListPopup();">관리</button>
					</td>
				</tr>
				<tr>
				    <th>주부서</th>
					<td colspan="3">
						<select class="select width150" name="mainDept">
							<option value="">선택</option>
							<c:forEach var="dept" items="${deptCodeList }" varStatus="status">
								<option value="<c:out value='${dept.code }'/>" <c:if test="${dept.code eq result.mainDept }">selected="selected"</c:if>><c:out value='${dept.codeNm }'/></option>
							</c:forEach>
						</select>
					</td>
				</tr>			
			</tbody>
		</table>
	</form>
	<div class="space20"></div>
	<div class="btngroup fl">		
		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/task/taskList.do'/>'">목록</button>
	</div>
	<div class="btngroup fr">
		<button type="button" class="btn01" onclick="javascript:fn_save();">수정</button>		
	</div>
</div>
