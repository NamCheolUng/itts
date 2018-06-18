<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script>
$(document).ready(function() {
	
	var sdate = document.frm.startDate.value;
	var edate = document.frm.endDate.value;
	
	var syear = sdate.substring(0,4);
	var smonth = sdate.substring(4,6);
	
	var knedoSDate = syear+"/"+smonth;
	
	var eyear = edate.substring(0,4);
	var emonth = edate.substring(4,6);
	
	var knedoEDate = eyear+"/"+emonth;
	
	var date = new Date();

	var y = date.getFullYear();
	var m = (date.getMonth() + 1);
	var d = date.getDate();
	
	var toDayYM = y +"/0"+ m;
	var toDayYMD = y +"/0"+ m +"/0"+ d;
		
	if(knedoSDate == "/"){
		
		$("#monthpicker2").kendoDatePicker({
			value: y+"/01"
		});
		$("#monthpicker3").kendoDatePicker({
			value: y+"/12"
		});
		
	}else{
		$("#monthpicker2").kendoDatePicker({
			value: knedoSDate
		});
		$("#monthpicker3").kendoDatePicker({
			value : knedoEDate
		});
	}
    
    $("#checkall").click(function(){
        if($("#checkall").prop("checked")){
            $("input[name=box]").prop("checked",true);
        }else{
            $("input[name=box]").prop("checked",false);
        }
    })
    
    $("#selectListCnt").change(function(){
    	document.frm.pageIndex.value = 1;
      	document.frm.action = "<c:url value='${tmpPath}/com/financial/salary/salaryDetailEmplyr.do?affili=I'/>";
    	document.frm.submit();
    })
});

function fn_downloadExcel()
{
	if($("input[name=box]:checked").size() == 0){
		alert("선택된 건이 없습니다.");	
		return;
	}
	
	document.frm.ymonth.value = "";
	$("input[name=box]:checked").each(function() {
		document.frm.ymonth.value += $(this).val()+"/";
	});
		document.frm.action = "<c:url value='/com/financial/salary/downloadDetailEmplExcel.do?affiliationId=I'/>";
		document.frm.submit();
}

function fn_egov_select_noticeList(pageNo) {

	document.frm.pageIndex.value = pageNo;
	document.frm.ymonth.value= '${salaryVO.ymonth }' ;
	document.frm.action = "<c:url value='/com/financial/salary/salaryDetailEmplyr.do?affili=I'/>";
	document.frm.submit();

}

function fn_searchSalary(){
	document.frm.startDate.value = $("#monthpicker2").val().replace("/","");
	document.frm.endDate.value = $("#monthpicker3").val().replace("/","");
	document.frm.pageIndex.value = 1;
	
  	document.frm.action = "<c:url value='${tmpPath}/com/financial/salary/salaryDetailEmplyr.do?affili=I'/>";
	document.frm.submit();
}

</script>
<div id="subwrap">
<form name="frm" method="post">
	<input type="hidden" name="ymonth">
	<input type="hidden" name="emplyrNo" value="${salaryVO.emplyrNo }">
	<input type="hidden" name="emplyrNm" value="${salaryVO.emplyrNm }">
	<input type="hidden" name="pageIndex" value="<c:out value='${salaryVO.pageIndex}'/>" />
	<input type="hidden" name="pageUnit" value="${salaryVO.pageUnit}" />
	<input type="hidden" name="startDate" value="${sdate}">
	<input type="hidden" name="endDate" value="${edate}">
	
	<h1>급여관리(이튜)</h1>
	<h3>${salaryVO.emplyrNm } (사원번호:${salaryVO.emplyrNo }) 급여내역</h3><br>
	
	<div class="schgroup">
		<table class="tablesch">
			<colgroup>
				<col class="width150">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>검색</th>
					<td>	
						<input type="date" class="datepicker width200" id="monthpicker2">&nbsp;&nbsp;~&nbsp; 
						<input type="date" class="datepicker width200" id="monthpicker3">&nbsp;
						<button type="button" class="btn03" onclick="fn_searchSalary()">검색</button>
						
						<div class="btngroup fr">
							<button type="button" class="btn04" onclick="location.href='<c:url value='/com/financial/salary/salaryList.do?affiliationId=I'/>'">목록</button>
							<button type="button" class="btn01" onclick="fn_downloadExcel()">엑셀 다운로드</button>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="space20"></div>
	<select class="select" id="selectListCnt" name="selectListCnt">
		<option value="10" <c:if test="${paginationInfo.recordCountPerPage == 10 }">selected=selected</c:if> >10개 보기 </option>
		<option value="50" <c:if test="${paginationInfo.recordCountPerPage == 50 }">selected=selected</c:if> >50개 보기</option>
		<option value="100" <c:if test="${paginationInfo.recordCountPerPage == 100 }">selected=selected</c:if> >100개 보기</option>
		<option value="1" <c:if test="${paginationInfo.recordCountPerPage == 1 }">selected=selected</c:if> >전체 보기</option>
	</select>
	<table class="tablelist">
		<thead>
			<tr>
				<th>선택<input type="checkbox" id="checkall" /></th>
				<th>구분</th>
				<th>기본<br>근무시간
				</th>
				<th>야간근무</th>
				<th>주말근무</th>
				<th>지급총액</th>
				<th>소득세</th>
				<th>주민세</th>
				<th>국민연금</th>
				<th>건강보험</th>
				<th>고용보험</th>
				<th>장기요양</th>
				<th>공제총액</th>
				<th>실지급액</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${resultList}" varStatus="status">
				<tr>
					<td><input type="checkbox" name="box" class="checkSelect" value="${result.ymonth }"></td>
					<td>${result.ymonth }</td>
					<td>${result.basicWorkingTime }</td>
					<td>${result.nightWorkingTime }</td>
					<td>${result.holidayWorkingTime }</td>
					<td>${result.totalIncrease }</td>
					<td>${result.incomeTax }</td>
					<td>${result.residenceTax }</td>
					<td>${result.nationalPension }</td>
					<td>${result.healthInsu }</td>
					<td>${result.umplInsu }</td>
					<td>${result.longCareInsu }</td>
					<td>${result.totalReduction }</td>
					<td>${result.totalPay }</td>
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
	<c:if test="${paginationInfo.recordCountPerPage != 1 }">
		 <div class="pageNum">
			<div class="pagination">
				 <ul class="pagination pagination-sm">
					<ui:pagination paginationInfo="${paginationInfo}" type="ieetu" jsFunction="fn_egov_select_noticeList" />
				</ul>
			</div>
		</div>
	</c:if>
</form>
</div>
<script>
$(document).ready(function() {
	fn_initSearchDatePickerYearMonth("monthpicker2", "monthpicker3");
});
</script>