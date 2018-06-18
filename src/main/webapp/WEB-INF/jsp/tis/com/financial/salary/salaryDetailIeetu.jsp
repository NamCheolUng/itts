<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script>
$(document).ready(function() {
	
	var sdate = document.frm.ymonth.value;
	
	var syear = sdate.substring(0,4);
	var smonth = sdate.substring(4,6);
	
	var knedoSDate = syear+"/"+smonth;
	
	$("#monthpicker2").kendoDatePicker({
		start : "year",
		depth : "year",
		format : "yyyy/MM",
		value: knedoSDate
	});

	$("input[name=monthpicker2]").attr("readonly",true);
	
    $("#checkall").click(function(){
        if($("#checkall").prop("checked")){
            $("input[name=box]").prop("checked",true);
        }else{
            $("input[name=box]").prop("checked",false);
        }
    })
});

function fn_goSalaryDetailEmplyr(emplyrNo, affiliationId, emplyrNm){

 	document.frm.emplyrNo.value = emplyrNo;
	document.frm.emplyrNm.value = emplyrNm;

  	document.frm.action = "<c:url value='/com/financial/salary/salaryDetailEmplyr.do?affili=I'/>";
	document.frm.submit();
}

function downloadExcel()
{
	if($("input[name=box]:checked").size() == 0){
		alert("선택된 건이 없습니다.");	
		return;
	}
	
	document.frm.emplyrNo.value = "";
	$("input[name=box]:checked").each(function() {
		document.frm.emplyrNo.value += $(this).val()+"/";
	});
 		document.frm.action = "<c:url value='/com/financial/salary/downloadDetailExcel.do?affiliationId=I'/>";
		document.frm.submit();
}

function downloadMonthExcel()
{
		document.frm.action = "<c:url value='/com/financial/salary/salaryMonthDownload.do?affiliationId=I'/>";
		document.frm.submit();
}

function fn_searchSalary(){
	document.frm.ymonth.value = $("#monthpicker2").val().replace("/","");
	document.frm.affiliationId.value = "I";
	
  	document.frm.action = "<c:url value='${tmpPath}/com/financial/salary/salaryDetail.do'/>";
	document.frm.submit();
}
</script>

<div id="subwrap">
<form name="frm" method="post">
	<input type="hidden" name="ymonth" value="${salaryVO.ymonth }">
	<input type="hidden" name="emplyrNo">
	<input type="hidden" name="emplyrNm">
	<input type="hidden" name="affiliationId">
	
	<h1>급여관리(이튜)</h1>
		<c:set var= "ymonthValue" value="${fn:substring(salaryVO.ymonth,0,4) }년 ${fn:substring(salaryVO.ymonth,4,6) }월" />
	<h3>${ymonthValue } 급여관리</h3>
	<br>
	<div class="schgroup">
		<table class="tablesch">
			<tbody>
				<tr>
					<th>검색</th>
					<td>	
						<input type="date" class="datepicker width200" id="monthpicker2" name="monthpicker2">&nbsp;
						<button type="button" class="btn03" onclick="fn_searchSalary()">검색</button>
						
						<div class="btngroup fr">
							<button type="button" class="btn04" onclick="location.href='<c:url value='/com/financial/salary/salaryList.do?affiliationId=I'/>'">목록</button>
							<button type="button" class="btn01" onclick="downloadExcel()">엑셀 다운로드</button>
							<button type="button" class="btn01" onclick="downloadMonthExcel()">엑셀전체 다운로드</button>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="space20"></div>
	<table class="tablelist">
		<thead>
			<tr>
				<th>선택<input type="checkbox" id="checkall" /></th>
				<th>사원번호</th>
				<th>사원명</th>
				<th>기본<br>근무시간</th>
				<th>야간근무</th>
				<th>주말근무</th>
				<th>지급총액</th>
				<th>공제총액</th>
				<th>실지급액</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="allPay" value="0"/>
			<c:forEach var="result" items="${salaryDetail}" varStatus="status">
					<tr>
						<td><input type="checkbox" name="box" class="checkSelect" value="${result.emplyrNo }"></td>
						<td>${result.emplyrNo }</td>
						<td><a href="#LINK" onclick="fn_goSalaryDetailEmplyr('${result.emplyrNo }','${result.affiliationId }','${result.emplyrNm }')">${result.emplyrNm }</a></td>
						<td>${result.basicWorkingTime }</td>
						<td>${result.nightWorkingTime }</td>
						<td>${result.holidayWorkingTime }</td>
						<td>${result.totalIncrease }</td>
						<td>${result.totalReduction }</td>
						<td>${result.totalPay }</td>
						<c:set var="allPay" value="${allPay + result.totalPay }"/>
						<td>${result.rm }</td>
					</tr>
			</c:forEach>
			<!-- 데이터가 없을때 화면에 메세지를 출력해준다 -->	
			<c:if test="${empty salaryDetail}">
				<tr> 
					<td colspan="100%">
						<spring:message code="common.nodata.msg" />
					</td>
				</tr>   	          				 			   
			</c:if>
		</tbody>
	</table>
	<div class="space20"></div>
	<div class="btngroup fr">
		<h2>총 지급액 : ${allPay }</h2>
	</div>
</form>
</div>