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
	var returnVal = "";
	function fn_search(){
		document.frm.action="<c:url value='/com/task/popup/taskListPopup.do'/>"
		document.frm.submit();
	}

	$(document).ready(function(){
		$("#taskTable tr").click(function(){
			opener.fn_returnTaskPopup( returnVal+$(this).children().children().val()+"//"+$(this).children().children().next().val());
			window.close();
			
		});
	});
</script>
	<form name="frm" method="post">
		<input type="hidden" name="taskId" value=""/>
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
									<option value="00" <c:if test="${taskVO.searchCondition eq '00' }">selected="selected"</c:if>>전체</option>
									<option value="01" <c:if test="${taskVO.searchCondition eq '01' }">selected="selected"</c:if>>과제명</option>
									<option value="02" <c:if test="${taskVO.searchCondition eq '02' }">selected="selected"</c:if>>발주처</option>
									<option value="03" <c:if test="${taskVO.searchCondition eq '03' }">selected="selected"</c:if>>키워드</option>						
								</select>
								<input type="text" class="k-textbox width100p" name="searchKeyword" value="<c:out value='${taskVO.searchKeyword }'/>">
								<input class="datepicker width200" name="taskSdate" value="<c:out value='${taskVO.taskSdate }'/>" placeholder="시작시간" title="시작시간">
								<input class="datepicker width200" name="taskEdate" value="<c:out value='${taskVO.taskEdate }'/>" placeholder="시작시간" title="시작시간">
								<button type="button" class="btn03" onclick="javascript:fn_search();">검색</button>								
							</div>
						</td>
					</tr>					
				</tbody>
			</table>
		</div>
	</form>
	<div class="space10"></div>			
	<div class="space30"></div>
	<table class="tablelist" id="taskTable">
		<colgroup>
			<col >
			<col >
			<col >
			<col>
			<col >
		</colgroup>
		<thead>
			<tr>
				<th>발주처</th>
				<th>과제명</th>
				<th>과제기간</th>
				<th>부서</th>				
				<th>등록일</th>
			</tr>		
		</thead>
		<tbody>
			<c:forEach var="result" items="${resultList }" varStatus="status">
				<tr >
					<td>
						<input type="hidden" value="${result.taskId }">
						<input type="hidden" value="${result.taskNm }">
						<c:out value="${result.cmpnyNm }"/>
					</td>
					<td><c:out value="${result.taskNm }"/></td>
					<td><c:out value="${result.taskSdate }"/>~<c:out value="${result.taskEdate }"/></td>
					<td><c:out value="${result.orgnztNm }"/></td>
					<td><c:out value="${fn:substring(result.regDt,0,10) }"/></td>
				</tr>	
			</c:forEach>					
		</tbody>
	</table>
	<button type="button" class="btn03" onclick="javascript:window.close();">닫기</button>	   
