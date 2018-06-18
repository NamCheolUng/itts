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
	function fn_chageDept(){
		document.frm.action="<c:url value='/com/task/popup/taskMngerPopup.do'/>";
		document.frm.submit();
	}
	
	function fn_save(){
		var maxVal=0;
		var cnt = 0;
		$(".mUser").each(function(){
			if(maxVal < $(this).attr("cnt")*1){
				maxVal = $(this).attr("cnt")*1;
			}
		});
		maxVal = maxVal+1;
		
		for(var i=0;i<maxVal;i++){
			if($("#role_"+i).val() == 'M'){
				cnt++;
			}
			if(cnt > 1){
				alert("과제담당자 2명 불가");
				return;
			}
		}
		if(!confirm("<spring:message code='msg.save.confirm'/>")){
			return;
		}
		$.ajax({
			url:"<c:url value='/com/task/popup/taskPersonSave.do'/>",
			type:"post",
			data:$("form[name=subForm]").serializeArray(),
			async:false,
			success:function(){
				window.close();
				window.opener.fn_reload();
			}
		});
	}
	
	function fn_insert(){
		$(".user").each(function(){			
			if($(this).is(":checked")){
				var maxVal=0;
				$(".mUser").each(function(){
					if(maxVal < $(this).attr("cnt")*1){
						maxVal = $(this).attr("cnt")*1;
					}
				});
				maxVal = maxVal+1;
				var tmp  = "<tr>";
				    tmp +=		"<td>";
				    tmp +=			"<input type='hidden' name='taskPersonVOList["+maxVal+"].emplNo' value='"+$(this).attr('emplNo')+"'/>"
				    tmp +=			"<input type='hidden' name='taskPersonVOList["+maxVal+"].taskPerId' value=''/>"
				    tmp +=			"<input type='checkbox' class='mUser' orgnztNm='"+$(this).attr('orgnztNm')+"' ofcpsNm='"+$(this).attr('ofcpsNm')+"' userNm='"+$(this).attr('userNm')+"' emplNo='"+$(this).attr('emplNo')+"' cnt='"+maxVal+"'/>"
				    tmp +=		"</td>"
				    tmp +=		"<td>"+$(this).attr('orgnztNm')+"</td>"
				    tmp +=		"<td>"+$(this).attr('ofcpsNm')+"</td>"
				    tmp +=		"<td>"+$(this).attr('userNm')+"</td>";
				    tmp +=		"<td>"
				    tmp +=			"<select class='select' name='taskPersonVOList["+maxVal+"].role' id='role_"+maxVal+"'>"
				    tmp +=				"<option value='M'>과제책임자</option>"
				    tmp +=				"<option value='P'>과제참여자</option>"
				    tmp +=			"</select>"
				    tmp +=		"</td>"
				    tmp +=		"<td></td>"
				    tmp +=		"<td>"
				    tmp +=			"<select class='select' name='taskPersonVOList["+maxVal+"].sttus' id='sttus_"+maxVal+"'>"
				    tmp +=				"<option value='1'>참여중</option>"
				    tmp +=				"<option value='2'>업무전환</option>"
				    tmp +=				"<option value='3'>퇴사</option>"
				    tmp +=			"</select>"
				    tmp +=		"</td>"
				    tmp +=		"<td></td>"
				    tmp +=	 "</tr>"
				   $(this).parent().parent().remove(); 
				$("#mngUserTable").append(tmp);
				  $("#role_"+maxVal).kendoDropDownList();
				  $("#sttus_"+maxVal).kendoDropDownList();
				  fn_userTableCnt();
			}
		});
		$("input[type=checkbox]").each(function(){
			$(this).prop("checked",false)
		});
	}
	function fn_userTableCnt(){
		var cnt = 1;
		$(".userTableCnt").each(function(){
			$(this).text(cnt);
			cnt++
		})
	}
	function fn_delete(){
		
		$(".mUser").each(function(){
			if($(this).is(":checked")){			
				var tmp = "<tr >"
					tmp+=   "<td>"
					tmp+=	"<input type='checkbox' class='user' orgnztNm='"+$(this).attr("orgnztNm")+"'"
					tmp+=										"ofcpsNm='"+$(this).attr("ofcpsNm")+"'" 
					tmp+=										"userNm='"+$(this).attr("userNm")+"'"
					tmp+=										"emplNo='"+$(this).attr("emplNo")+"'/>"				
					tmp+=	"</td>"
					tmp+=    "<td class='userTableCnt'></td>"
					tmp+=    "<td>"+$(this).attr("orgnztNm")+"</td>"
					tmp+=    "<td>"+$(this).attr("ofcpsNm")+"</td>"
					tmp+=    "<td>"+$(this).attr("userNm")+"</td>"
					tmp+=  "</tr>"
				$("#userTable").append(tmp);
				 $(this).parent().parent().remove(); 
				  fn_userTableCnt();
			}
		});
		$("input[type=checkbox]").each(function(){
			$(this).prop("checked",false)
		});
	}
	
	$(document).ready(function(){
		var flag = true;
		$("#allUser").on("click",function(){
			if($("#allUser").is(":checked")){
				$(".user").each(function(){
					$(this).prop("checked",true);
				});
			}else{
				$(".user").each(function(){
					$(this).prop("checked",false);
				});
			}
			
		});
		$(".user").on("click",function(){
			flag = true;
			$(".user").each(function(){
				if(!$(this).is(":checked")){
					flag = false;
				}
				
			});
			if(flag){
				$("#allUser").prop("checked",true);
			}else{
				$("#allUser").prop("checked",false);
			}
		});
		$(".mUser").on("click",function(){
			flag = true;
			$(".mUser").each(function(){
				if(!$(this).is(":checked")){
					flag = false;
				}				
			});
			if(flag){
				$("#mngUser").prop("checked",true);
			}else{
				$("#mngUser").prop("checked",false);
			}
		});
		$("#mngUser").on("click",function(){
			if($("#mngUser").is(":checked")){
				$(".mUser").each(function(){
					$(this).prop("checked",true);
				});
			}else{
				$(".mUser").each(function(){
					$(this).prop("checked",false);
				});
			}
		});
	});
	function fn_close(){
		window.close();
	//	window.opener.fn_reload();
	}
</script>
<form name="frm" method="post">
		<input type="hidden" name="taskId" value="${taskVO.taskId }"/>
		<div class="schgroup">
			<select class="select width150" name="orgnztId" onchange="javascript:fn_chageDept();">
					<option value="" <c:if test="${empty taskVO.orgnztId  }">selected="selected"</c:if>>부서선택</option>
				<c:forEach var="deptCodeList" items="${deptCodeList }">
					<option value="<c:out value='${deptCodeList.code }'/>"  <c:if test="${taskVO.orgnztId eq deptCodeList.code  }">selected="selected"</c:if>><c:out value='${deptCodeList.codeNm }'/></option>
				</c:forEach>			
			</select>
		</div>
	</form>
	<button type="button" class="btn01" onclick="javascript:fn_insert();">선택한담당자추가</button>		
<table class="tablelist" id="userTable">
		<colgroup>
			<col >
			<col >
			<col >
			<col>
			<col >
		</colgroup>
		<thead>
			<tr>
				<th><input type="checkbox" id="allUser"/></th>
				<th>No</th>
				<th>부서</th>
				<th>직위</th>				
				<th>성명</th>
			</tr>		
		</thead>
		<tbody>
			<c:set var="count" value="1"/>			
			<c:forEach var="result" items="${userList }" varStatus="status">
				<c:if test="${result.cnt eq 0 }">
					<tr >
						<td>
							<input type="checkbox" class="user" orgnztNm="<c:out value='${result.orgnztNm }'/>"
																ofcpsNm="<c:out value='${result.ofcpsNm }'/>" 
																userNm="<c:out value='${result.userNm }'/>"
																emplNo="<c:out value='${result.emplNo }'/>"
							/>
						</td>
						<td class="userTableCnt"><c:out value='${count }'/></td>
						<td><c:out value='${result.orgnztNm }'/></td>
						<td><c:out value='${result.ofcpsNm }'/></td>
						<td><c:out value='${result.userNm }'/></td>
					</tr>
					<c:set var="count" value="${count+1 }"/>	
				</c:if>	
			</c:forEach>					
		</tbody>
	</table>
	
	<h4><i class="fas fa-dot-circle"></i>과제담당자</h4>
	<button type="button" class="btn01" onclick="javascript:fn_delete();">담당자삭제</button>	
	<form name="subForm" method="post">
		<input type="hidden" name="taskId" value="<c:out value='${taskVO.taskId }'/>"/>
		<table class="tablelist" id="mngUserTable">
			<colgroup>
				<col >
				<col >
				<col >
				<col>
				<col >
				<col >
				<col >
			</colgroup>
			<thead>
				<tr>
					<th><input type="checkbox" id="mngUser"/></th>
					<th>부서</th>
					<th>직위</th>
					<th>성명</th>				
					<th>역할</th>
					<th>등록일</th>
					<th>상태</th>
					<th>상태변경일</th>
				</tr>		
			</thead>
			<tbody>
				<c:forEach var="result" items="${taskPersonList }" varStatus="status">
						<tr >
							<td>
								<input type="hidden" name="taskPersonVOList[${status.index }].emplNo" value="${result.emplNo }"/>
								<input type="hidden" name="taskPersonVOList[${status.index }].rate" value="${result.rate }"/>
								<input type="hidden" name="taskPersonVOList[${status.index }].taskPerId" value="${result.taskPerId }"/>
								<input type="hidden" class="mUser" orgnztNm="<c:out value='${result.orgnztNm }'/>"
																	ofcpsNm="<c:out value='${result.ofcpsNm }'/>" 
																	userNm="<c:out value='${result.userNm }'/>"
																	emplNo="<c:out value='${result.emplNo }'/>"
																	cnt="<c:out value='${status.index }'/>"
								/> 
							</td>
							<td><c:out value='${result.orgnztNm }'/></td>
							<td><c:out value='${result.ofcpsNm }'/></td>
							<td><c:out value='${result.userNm }'/></td>
							<td>
								<select class="select" name="taskPersonVOList[${status.index }].role" id="role_${status.index }">
									<option value="M" <c:if test="${result.role eq 'M' }">selected="selected"</c:if>>과제책임자</option>
									<option value="P" <c:if test="${result.role eq 'P' }">selected="selected"</c:if>>과제참여자</option>
								</select>
							</td>
							<td><c:out value='${result.regDt }'/></td>
							<td>
								<select class="select" name="taskPersonVOList[${status.index }].sttus">
									<option value="1" <c:if test="${result.sttus eq '1' }">selected="selected"</c:if>>참여중</option>
									<option value="2" <c:if test="${result.sttus eq '2' }">selected="selected"</c:if>>업무전환</option>
									<option value="3" <c:if test="${result.sttus eq '3' }">selected="selected"</c:if>>퇴사</option>
								</select>
							</td>
							<td><c:out value='${result.sttusChngDate }'/></td>
						</tr>
				</c:forEach>					
			</tbody>
		</table>
	</form>
	<button type="button" class="btn03" onclick="javascript:fn_save();">저장</button>
	<button type="button" class="btn03" onclick="javascript:fn_close();">닫기</button>