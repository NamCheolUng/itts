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
/* 		$("input[name=taskSdate]").attr("readonly",true);
		$("input[name=taskEdate]").attr("readonly",true);  */
		var cntAll = 0;
		var cntChk = 0;
		$("input[type=checkbox]").on("click",function(){
			cntAll = 0;
			cntChk = 0;
			if($(this).attr("name") == "deptCode"){
				if($(this).val() == '00'){
					if($(this).is(":checked")){
						$("input[name=deptCode]").each(function(){							
							$(this).prop("checked",true);
							$("#checkbox12").prop("checked",false);
						});
					}else{
						$("input[name=deptCode]").each(function(){							
							$(this).prop("checked",false);							
						});
					}
				}else{					
					$("#checkbox12").prop("checked",false);
					$("input[name=deptCode]").each(function(){						
						if($(this).val() != '00'){
							cntAll++;
							if($(this).is(":checked")){
								cntChk++;
							}
						}
					});
					if(cntAll == cntChk){
						$("#checkbox00").prop("checked",true);
					}else{
						$("#checkbox00").prop("checked",false);
					}
				}	
			}else if($(this).attr("name") == 'myTaskChk'){
				if($(this).is(":checked")){
					$("input[name=deptCode]").each(function(){							
						$(this).prop("checked",false);
					});
				}
			}
			if($("input[name=deptCode]:checked").length == 0){
				document.frm.emptyChk.value=document.frm.emptyChk.value + "dept";
			}
			if($("input[name=taskStatusCd]:checked").length == 0){
				document.frm.emptyChk.value=document.frm.emptyChk.value + "status";
			}
			document.frm.action="<c:url value='/com/task/taskList.do'/>"
			document.frm.submit();
		});
		$("input[name=deptCode]").each(function(){						
			if($(this).val() != '00'){
				cntAll++;
				if($(this).is(":checked")){
					cntChk++;
				}
			}
		});
		if(cntAll == cntChk){
			$("#checkbox00").prop("checked",true);
		}else{
			$("#checkbox00").prop("checked",false);
		}
	});
	function fn_search(){
		if(document.frm.taskSdate.value != '' || document.frm.taskEdate.value != ''){					
			if(document.frm.taskSdate.value == ''){
				alert("<spring:message code='msg.required'  arguments='시작시간'/>");
				document.frm.taskSdate.focus();
				return;
			}
			if(document.frm.taskEdate.value == ''){
				alert("<spring:message code='msg.required'  arguments='종료시간'/>");
				document.frm.taskEdate.focus();
				return;
			}
		}	
		document.frm.action="<c:url value='/com/task/taskList.do'/>"
		document.frm.submit();
	}
	function fn_regist(){
		document.frm.action="<c:url value='/com/task/taskRegist.do'/>"
		document.frm.submit();
	}
	function fn_detail(id){
		document.frm.taskId.value=id;
		document.frm.action="<c:url value='/com/task/taskDetail.do'/>"
		document.frm.submit();
	}
	function fn_select_taskList(page){
		document.frm.pageIndex.value=page;
		document.frm.action="<c:url value='/com/task/taskList.do'/>"
		document.frm.submit();
	}
</script>
<form name="frm" method="post">
<input type="hidden" name="emptyChk" />
<div id="mainwrap">
	<div class="dashboard_side">			
		<div class="group_sc">
			<h3>
				<label><input type="checkbox" id="checkbox00" name="deptCode" value="00" <c:if test="${fn:contains(taskVO.deptCode,'00') }">checked="checked"</c:if>>전체일정</label>
			</h3>
			<div class="group_sc_list">
				<p class="gs1">
					<input type="checkbox" class="ch" id="checkbox01" name="deptCode" value="DEPT01" <c:if test="${fn:contains(taskVO.deptCode,'00')  or fn:contains(taskVO.deptCode,'DEPT01')  }">checked="checked"</c:if>> <label
						for="checkbox01">영업팀</label>
				</p>
				<p class="gs2">
					<input type="checkbox" class="ch" id="checkbox02" name="deptCode" value="DEPT02" <c:if test="${fn:contains(taskVO.deptCode,'00')  or fn:contains(taskVO.deptCode,'DEPT02')  }">checked="checked"</c:if>> <label
						for="checkbox02">경영지원팀</label>
				</p>
				<p class="gs3">
					<input type="checkbox" class="ch" id="checkbox03" name="deptCode" value="DEPT03" <c:if test="${fn:contains(taskVO.deptCode,'00')  or fn:contains(taskVO.deptCode,'DEPT03')  }">checked="checked"</c:if>> <label
						for="checkbox03">개발부</label>
				</p>
				<p class="gs4">
					<input type="checkbox" class="ch" id="checkbox04" name="deptCode" value="DEPT04" <c:if test="${fn:contains(taskVO.deptCode,'00')  or fn:contains(taskVO.deptCode,'DEPT04')  }">checked="checked"</c:if>> <label
						for="checkbox04">TS/솔루션팀</label>
				</p>
				<p class="gs5">
					<input type="checkbox" class="ch" id="checkbox05" name="deptCode" value="DEPT05" <c:if test="${fn:contains(taskVO.deptCode,'00')  or fn:contains(taskVO.deptCode,'DEPT05')  }">checked="checked"</c:if>> <label
						for="checkbox05">TS/인프라팀</label>
				</p>
			<%-- 	<p class="gs6">
					<input type="checkbox" class="ch" id="checkbox13" name="deptCode" value="DEPT06" <c:if test="${fn:contains(taskVO.deptCode,'00')  or fn:contains(taskVO.deptCode,'DEPT06')  }">checked="checked"</c:if>> <label
						for="checkbox13">개발팀</label>
				</p>
				<p class="gs7">
					<input type="checkbox" class="ch" id="checkbox14" name="deptCode" value="DEPT07" <c:if test="${fn:contains(taskVO.deptCode,'00')  or fn:contains(taskVO.deptCode,'DEPT07')  }">checked="checked"</c:if>> <label
						for="checkbox14">기획팀</label>
				</p>
				<p class="gs8">
					<input type="checkbox" class="ch" id="checkbox15" name="deptCode" value="DEPT08" <c:if test="${fn:contains(taskVO.deptCode,'00')  or fn:contains(taskVO.deptCode,'DEPT08')  }">checked="checked"</c:if>> <label
						for="checkbox15">디자인팀</label>
				</p> --%>
			</div>
			<h3 class="bh3">
				<label><input type="checkbox" id="checkbox12" name="myTaskChk" value="Y" <c:if test="${not empty taskVO.myTaskChk   }">checked="checked"</c:if>>나의 과제</label>
			</h3>
		</div>
		<div class="space05"></div>	
		<div class="group_sc">
			<h3>
				<label>과제진행상태</label>
			</h3>
			<div class="group_sc_list">
				<p class="gs7">
					<input type="checkbox" class="ch" id="checkbox06"  name="taskStatusCd" value="01" <c:if test="${fn:contains(taskVO.taskStatusCd,'01') }">checked="checked"</c:if>> <label
						for="checkbox06">진행전</label>
				</p>
				<p class="gs8">
					<input type="checkbox" class="ch" id="checkbox07" name="taskStatusCd" value="02" <c:if test="${fn:contains(taskVO.taskStatusCd,'02') }">checked="checked"</c:if>> <label
						for="checkbox07">진행중</label>
				</p>
				<p class="gs3">
					<input type="checkbox" class="ch" id="checkbox08" name="taskStatusCd" value="03" <c:if test="${fn:contains(taskVO.taskStatusCd,'03') }">checked="checked"</c:if>> <label
						for="checkbox08">완료</label>
				</p>
				<p class="gs9">
					<input type="checkbox" class="ch" id="checkbox09" name="taskStatusCd" value="04" <c:if test="${fn:contains(taskVO.taskStatusCd,'04') }">checked="checked"</c:if>> <label
						for="checkbox09">지연</label>
				</p>
				<p class="gs10">
					<input type="checkbox" class="ch" id="checkbox10" name="taskStatusCd" value="05" <c:if test="${fn:contains(taskVO.taskStatusCd,'05') }">checked="checked"</c:if>> <label
						for="checkbox10">지연완료</label>
				</p>
				<p class="gs11">
					<input type="checkbox" class="ch" id="checkbox11" name="taskStatusCd" value="06" <c:if test="${fn:contains(taskVO.taskStatusCd,'06') }">checked="checked"</c:if>> <label
						for="checkbox11">중단</label>
				</p>
			</div>
		</div>
	</div>

<div id="scheduler">	
		<input type="hidden" name="taskId" value=""/>
		<input type="hidden" name="pageIndex" value="<c:out value='${taskVO.pageIndex }'/>">
		<input type="hidden" name="pageUnit" value="<c:out value='${taskVO.pageUnit }'/>">
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
								<input type="text" class="k-textbox width100p" onkeypress="if( event.keyCode==13 ){fn_search();}" name="searchKeyword" value="<c:out value='${taskVO.searchKeyword }'/>">
								<button type="button" class="btn03" onclick="javascript:fn_search();">검색</button>
								<input class="datepicker width200" name="taskSdate" id="taskSdate" value="<c:out value='${taskVO.taskSdate }'/>" placeholder="시작시간" title="시작시간">
								<input class="datepicker width200" name="taskEdate" id="taskEdate" value="<c:out value='${taskVO.taskEdate }'/>" placeholder="종료시간" title="종료시간">
								<button type="button" class="btn" onclick="javascript:fn_regist();">과제등록</button>
							</div>
						</td>
					</tr>		
				</tbody>
			</table>
		</div>
	</form>
	<div class="space10"></div>	
	<span class="boardnum fl"><strong>Total:</strong> <c:out value='${paginationInfo.totalRecordCount }'/>, <strong>Page:</strong> <c:out value='${paginationInfo.currentPageNo }'/>/<c:out value='${paginationInfo.totalPageCount }'/></span>		
	<div class="space30"></div>
	<table class="tablelist">
		<colgroup>
			<col class="width80">
			<col class="width150">
			<col class="width150">
			<col>
			<col class="width100">
			<col class="width100">
			<col>
			<col class="width150">
			<col class="width150">
			<col class="width150">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>상태</th>
				<th>발주처</th>
				<th>과제명</th>
				<th>계약금액(천원)</th>
				<th>매입(천원)</th>
				<th>과제기간</th>
				<th>부서</th>
				<th>담당자</th>
				<th>등록일</th>
			</tr>		
		</thead>
		<tbody>
			<c:forEach var="result" items="${resultList }" varStatus="status">				
				<tr>
					<td>					
						<c:out value="${paginationInfo.totalRecordCount - ((paginationInfo.currentPageNo-1) * paginationInfo.recordCountPerPage + status.index) }" />
					</td>
					<td>
						<c:choose>
							<c:when test="${result.taskStatusCd eq '01' }">
								<span class="state bgcolor05">진행전</span>
							</c:when>
							<c:when test="${result.taskStatusCd eq '02' }">
								<span class="state bgcolor01">진행중</span>
							</c:when>
							<c:when test="${result.taskStatusCd eq '03' }">
								<span class="state bgcolor06">완료</span>
							</c:when>
							<c:when test="${result.taskStatusCd eq '04' }">
								<span class="state bgcolor03">지연</span>
							</c:when>
							<c:when test="${result.taskStatusCd eq '05' }">
								<span class="state bgcolor07">지연완료</span>
							</c:when>
							<c:when test="${result.taskStatusCd eq '06' }">
								<span class="state bgcolor08">중단</span>
							</c:when>
						</c:choose>
						
					</td>
					<td><c:out value='${result.cmpnyNm }'/></td>
					<td class="alignL"><a href="javascript:fn_detail('<c:out value='${result.taskId }'/>');"><c:out value='${result.taskNm }'/></a></td>
					<td><fmt:formatNumber value='${result.totSale }'/></td>
					<td><fmt:formatNumber value='${result.totPrcs }'/></td>
					<td><c:out value='${result.taskSdate }'/>&nbsp;~&nbsp;<c:out value='${result.taskEdate }'/></td>
					<td><c:out value='${result.orgnztNm }'/></td>
					<td><c:out value='${result.mngNm }'/><c:if test="${result.partiCnt > 0 }">외 <c:out value='${result.partiCnt }'/>명</c:if></td>
					<td><c:out value='${fn:substring(result.regDt,0,10) }'/></td>
				</tr>				
			</c:forEach>	
			<!-- 데이터가 없을때 화면에 메세지를 출력해준다 -->	
			<c:if test="${empty resultList}">
				<tr> 
					<td colspan="100%">
						<spring:message code="common.nodata.msg" />
					</td>
				</tr>   	          				 			   
			</c:if>				
		</tbody>
	</table>
	    <div class="pageNum">
     <ul class="pagination pagination-sm">
       	<ui:pagination paginationInfo="${paginationInfo}" type="ieetu" jsFunction="fn_select_taskList" />
      </ul>
  </div>
</div>

<script>
$(document).ready(function() {
	fn_initSearchDatePicker("taskSdate", "taskEdate");
});
</script>