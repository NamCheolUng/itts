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
		document.frm.action="<c:url value='/com/task/popup/schdulRelaCmngPopup.do'/>";
		document.frm.submit();
	}
	function fn_select(){
		if($("input[type=checkbox]:checked").length == 0 ){
			alert("<spring:message code='msg.select'/>");
				return;
			
		}
		var arr = new Array();
		var tmp ="";
		$("input[type=checkbox]").each(function(){
			if($(this).is(":checked")){						
				if($(this).attr("id") != 'allUser'){
					tmp = $(this).attr("ncrdId")+"||"+$(this).attr("nm")+"||"+$(this).attr("cmpnyNm");
					arr.push(tmp);
				}
			}
		});
		window.opener.fn_returnRelaCmng(arr);
		window.close();
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
	<form>
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
			</c:forEach>			
		</tbody>
	</table>
	</form>
	<button type="button" class="btn03" onclick="javascript:fn_select();">선택</button>
	<button type="button" class="btn03" onclick="javascript:fn_close();">닫기</button>