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
		var taskId ="";
		var iUGbn = "";
		if($("#taskId").val() != ''){
			taskId= $("#taskId").val();
			iUGbn = "U";
		}else{
			taskId = window.opener.document.frm.taskId.value;
			iUGbn = "I";
		}
		
		$.ajax({
			url:"<c:url value='/com/task/popup/taskDevelEnvirSave.do'/>",
			type:"POST",
			data:{taskId:taskId,
				  os:$("input[name=os]").val(),	
				  lang:$("input[name=lang]").val(),
				  rm:$("input[name=rm]").val(),
				  iUGbn:iUGbn
				  },
			success:function(data){
				if(data.result){
					//alert("<spring:message code='msg.save' />");
					window.opener.fn_taskEnvReturnVal($("input[name=os]").val(),$("input[name=lang]").val(),$("input[name=rm]").val());
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
		</colgroup>
		<tbody>			
			<tr >
				<th>운영체제</th>
				<td>
					<input type="text" class="k-textbox width100p" name="os" value="<c:out value='${result.os }'/>"/>
				</td>					
			</tr>
			<tr >
				<th>개발언어</th>
				<td>
					<input type="text" class="k-textbox width100p" name="lang" value="<c:out value='${result.lang }'/>"/>
				</td>					
			</tr>	
			<tr >
				<th>비고</th>
				<td>
					<input type="text" class="k-textbox width100p" name="rm" value="<c:out value='${result.rm }'/>"/>
				</td>					
			</tr>						
		</tbody>
	</table>
	<button type="button" class="btn01" onclick="javascript:fn_save();">저장</button>	
	<button type="button" class="btn01" onclick="javascript:window.close();">닫기</button>	