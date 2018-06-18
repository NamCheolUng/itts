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
	function fn_save(){
		if(!confirm("<spring:message code='msg.save.confirm' />")){
			return;
		}
		var taskId = window.opener.document.frm.taskId.value;


		$.ajax({
			url:"<c:url value='/com/task/popup/taskDevelEnvirSubSave.do'/>",
			type:"POST",
			data:{taskId:taskId,
				  sept:$(":selected").val(),	
				  nm:$("input[name=nm]").val(),
				  expln:$("input[name=expln]").val(),
				  rm:$("input[name=rm]").val()
				  },
			success:function(data){
				if(data.result){
					//alert("<spring:message code='msg.save' />");
					window.opener.fn_taskEnvSubReturnVal($(":selected").val(),$("input[name=nm]").val(),$("input[name=expln]").val(),$("input[name=rm]").val());
					window.close();
				}
			}
		});
	}
		
	
</script>
<input type="hidden" value="<c:out value='${result.taskId }'/>" id="taskId"/>
<table class="tablelist">
		<colgroup>
			<col >
			<col >
			<col >
			<col >
		</colgroup>
		<tbody>			
			<tr >
				<th>구분</th>
				<td>
					<select name="sept" class="select width150">
						<option value="0">선택</option>
						<option value="1">주요툴</option>
						<option value="2">주요프레임워크</option>
						<option value="3">주요컴포넌트</option>
						<option value="4">서비스</option>
					</select>
				</td>
				<th>이름</th>
				<td colspan="3">
					<input type="text" class="k-textbox width100p" name="nm"/>
				</td>					
			</tr>
			<tr >
				<th>설명</th>
				<td colspan="3">
					<input type="text" class="k-textbox width100p" name="expln"/>
				</td>					
			</tr>	
			<tr >
				<th>비고</th>
				<td colspan="3">
					<input type="text" class="k-textbox width100p" name="rm"/>
				</td>					
			</tr>						
		</tbody>
	</table>
	<button type="button" class="btn01" onclick="javascript:fn_save();">저장</button>	
	<button type="button" class="btn01" onclick="javascript:window.close();">닫기</button>	