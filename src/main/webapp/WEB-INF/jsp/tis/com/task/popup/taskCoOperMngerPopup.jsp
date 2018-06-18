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
	function fn_search(){
		document.frm.action="<c:url value='/com/task/popup/taskCoOperMngerPopup.do'/>";
		document.frm.submit();
	}
	
	function fn_save(){
		if(!confirm("<spring:message code='msg.save.confirm'/>")){
			return;
		}
		$.ajax({
			url:"<c:url value='/com/task/popup/taskCoOperMngerSave.do'/>",
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
				var flag = false;
				$(".mUser").each(function(){
					flag = true;
					if(maxVal < $(this).attr("cnt")*1){
						maxVal = $(this).attr("cnt")*1;						
					}
				});
				if(maxVal ==0 && !flag){
					maxVal = 0;
				}else if(maxVal ==0 && flag){
					maxVal = maxVal+1;
				}else{
					maxVal = maxVal+1;
				}
				
				var tmp = "<tr>";
					tmp+=	"<td>";			
					tmp+=      "<input type='hidden' name='taskOrgztPerVOList["+maxVal+"].orgztPerId' value=''/>";
					tmp+=      "<input type='hidden' name='taskOrgztPerVOList["+maxVal+"].ncrdId' value='"+$(this).attr("ncrdId")+"'/>";					
					tmp+=      "<input type='hidden' class='mUser' cnt = '"+maxVal+"'";								                                  
					tmp+=        "/>"; 
					tmp+=		"<input type='checkbox' class='mUser' ncrdId='"+$(this).attr('ncrdId')+"' cmpnyNm='"+$(this).attr('cmpnyNm')+"' deptNm='"+$(this).attr('deptNm')+"' ofcpsNm='"+$(this).attr('ofcpsNm')+"' nm='"+$(this).attr('nm')+"' idntfcNo='"+$(this).attr('idntfcNo')+"' rm='"+$(this).attr('rm')+"' cnt='"+maxVal+"'/>"
					tmp+=   "</td>";
					tmp+=   "<td>"+$(this).attr('cmpnyNm')+"</td>";
					tmp+=   "<td>"+$(this).attr('deptNm')+"</td>";
					tmp+=   "<td>"+$(this).attr('ofcpsNm')+"</td>";
					tmp+=   "<td>"+$(this).attr('nm')+"</td>";
					tmp+=   "<td>"
					tmp+=		"<select class='select' name='taskOrgztPerVOList["+maxVal+"].role' id='role_"+maxVal+"'>";
					tmp+=			"<option value='M'>과제책임자</option>";
					tmp+=			"<option value='P'>과제참여자</option>";
					tmp+=		"</select>"
					tmp+=	"</td>"
					tmp+=   "<td></td>";
					tmp+=   "<td>";
					tmp+=       "<select class='select' name='taskOrgztPerVOList["+maxVal+"].sttus' id='sttus_"+maxVal+"'>";
					tmp+=	        "<option value='1'>참여중</option>";
					tmp+=				"<option value='2'>업무전환</option>";
					tmp+=				"<option value='3'>퇴사</option>";
					tmp+=		"</select>";
					tmp+=   "</td>";
					tmp+=   "<td></td>";
					tmp+="</tr>"
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
	
	function fn_delete(){
		$(".mUser").each(function(){
			if($(this).is(":checked")){			
				var tmp = "<tr >"
					tmp+=   "<td>"
					tmp+=	"<input type='checkbox' class='user' ncrdId='"+$(this).attr("ncrdId")+"'"
					tmp+=										"deptNm='"+$(this).attr("deptNm")+"'" 
					tmp+=										"ofcpsNm='"+$(this).attr("ofcpsNm")+"'"
					tmp+=										"idntfcNo='"+$(this).attr("idntfcNo")+"'"
					tmp+=										"rm='"+$(this).attr("rm")+"'"
					tmp+=										"cmpnyNm='"+$(this).attr("cmpnyNm")+"'"
					tmp+=										"nm='"+$(this).attr("nm")+"'/>"				
					tmp+=	"</td>"
					tmp+=    "<td class='userTableCnt'></td>"
					if($(this).attr("idntfcNo") == '1'){
						tmp+=    "<td>연구계</td>"
					}else if($(this).attr("idntfcNo") == '2'){
						tmp+=    "<td>기관</td>"
					}else if($(this).attr("idntfcNo") == '3'){
						tmp+=    "<td>산업계</td>"
					}else if($(this).attr("idntfcNo") == '4'){
						tmp+=    "<td>학계</td>"
					}else{
						tmp+=    "<td></td>"
					}
					tmp+=    "<td>"+$(this).attr("cmpnyNm")+"</td>"
					tmp+=    "<td>"+$(this).attr("deptNm")+"</td>"
					tmp+=    "<td>"+$(this).attr("ofcpsNm")+"</td>"
					tmp+=    "<td>"+$(this).attr("nm")+"</td>"
					tmp+=    "<td>"+$(this).attr("rm")+"</td>"
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
		//window.opener.fn_reload();
	}
	
	function fn_userTableCnt(){
		var cnt = 1;
		$(".userTableCnt").each(function(){
			$(this).text(cnt);
			cnt++
		})
	}
</script>
<form name="frm" method="post">
		<input type="hidden" name="taskId" value="<c:out value='${taskOrgztPerVO.taskId }'/>"/>
		<input type="hidden" name="adbkId" value="<c:out value='${taskOrgztPerVO.adbkId }'/>"/>
		<input type="hidden" name="sept" value="1"/>
		<div class="schgroup">
			<table class="tablesch">
				<colgroup>
					<col class="width80">
					<col class="width180">
					<col class="width80">
					<col class="width80">
					<col class="width180">
				</colgroup>
				<tbody>		
					<tr>				
						<td colspan="5">
							<div class="formgroup">
								<select class="select width150" name="searchCondition">
									<option value="" <c:if test="${empty taskOrgztPerVO.searchCondition  }">selected="selected"</c:if>>선택</option>
									<option value="1" <c:if test="${taskOrgztPerVO.searchCondition eq '1' }">selected="selected"</c:if>>소속기관</option>
									<option value="2" <c:if test="${taskOrgztPerVO.searchCondition eq '2' }">selected="selected"</c:if>>성명</option>
								</select>
								<input type="text" class="k-textbox width100p" name="searchKeyword" value="<c:out value='${taskOrgztPerVO.searchKeyword }'/>">								
								<button type="button" class="btn03" onclick="javascript:fn_search();">검색</button>								
							</div>
						</td>
					</tr>					
				</tbody>
			</table>
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
				<th>구분</th>
				<th>기관/업체</th>
				<th>부서</th>
				<th>직위</th>				
				<th>성명</th>
				<th>비고</th>
			</tr>		
		</thead>
		<tbody>
			<c:set var="count" value="1"/>			
			<c:forEach var="result" items="${cmpnyMngerList }" varStatus="status">
				<c:if test="${result.cnt eq 0 }">
					<tr>
						<td>
							<input type="checkbox" class="user" ncrdId="<c:out value='${result.ncrdId }'/>"																
																cmpnyNm="<c:out value='${result.cmpnyNm }'/>"
																deptNm="<c:out value='${result.deptNm }'/>"
																ofcpsNm="<c:out value='${result.ofcpsNm }'/>"
																nm="<c:out value='${result.nm }'/>"
																idntfcNo="<c:out value='${result.idntfcNo }'/>"
																rm="<c:out value='${result.rm }'/>"
							/>
						</td>
						<td class="userTableCnt"><c:out value="${count }"/></td>
						<td>
							<c:choose>
								<c:when test="${result.idntfcNo eq '1'}">
									연구계
								</c:when>
								<c:when test="${result.idntfcNo eq '2'}">
									기관
								</c:when>
								<c:when test="${result.idntfcNo eq '3'}">
									산업계
								</c:when>
								<c:when test="${result.idntfcNo eq '4'}">
									학계
								</c:when>
							</c:choose>
						</td>
						<td><c:out value="${result.cmpnyNm }"/></td>
						<td><c:out value="${result.deptNm }"/></td>
						<td><c:out value="${result.ofcpsNm }"/></td>
						<td><c:out value="${result.nm }"/></td>
						<td><c:out value="${result.rm }"/></td>
					</tr>
					<c:set var="count" value="${count+1 }"/>
				</c:if>
			</c:forEach>			
		</tbody>
	</table>
	
	<h4><i class="fas fa-dot-circle"></i>과제발주처담당자</h4>
	<button type="button" class="btn01" onclick="javascript:fn_delete();">담당자삭제</button>	
	<form name="subForm" method="post">
		<input type="hidden" name="taskId" value="<c:out value='${taskOrgztPerVO.taskId }'/>"/>
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
					<th>기관/업체</th>
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
				<c:forEach var="result" items="${opList }" varStatus="status">
						<tr >
							<td>			
								<input type="hidden" name="taskOrgztPerVOList[${status.index }].orgztPerId" value="<c:out value='${result.orgztPerId }'/>"/>
								<input type="hidden" name="taskOrgztPerVOList[${status.index }].ncrdId" value="<c:out value='${result.ncrdId }'/>"/>					
								<input type="hidden" class="mUser" cnt = "<c:out value='${status.index }'/>"								                                  
								/> 
							</td>
							<td><c:out value='${result.cmpnyNm }'/></td>
							<td><c:out value='${result.deptNm }'/></td>
							<td><c:out value='${result.ofcpsNm }'/></td>
							<td><c:out value='${result.nm }'/></td>
							<td>
								<select class="select" name="taskOrgztPerVOList[${status.index }].role">
									<option value="M" <c:if test="${result.role eq 'M' }">selected="selected"</c:if>>과제책임자</option>
									<option value="P" <c:if test="${result.role eq 'P' }">selected="selected"</c:if>>과제참여자</option>
								</select>
							</td>
							<td><c:out value='${result.regDate }'/></td>
							<td>
								<select class="select" name="taskOrgztPerVOList[${status.index }].sttus">
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