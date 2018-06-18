<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

	$("#ymonth2").kendoDatePicker({
		start : "year",
		depth : "year",
		format : "yyyy/MM",
		value : toDayYM
	});
	
	$("#payDt").kendoDatePicker({
		format : "yyyy/MM/dd",
		value : toDayYMD
	});
	
	$("input[name=ymonth2]").attr("readonly",true);
	$("input[name=payDt]").attr("readonly",true);
	
	$("input[name='chkAffili']").change(function() {
		if($(this).val() == 'I') {
			location.href = "<c:url value='/com/financial/salary/salaryList.do?affiliationId=I'/>" 
		}else {
			location.href = "<c:url value='/com/financial/salary/salaryList.do?affiliationId=S'/>" 
		}
	});
	
    $("#checkall").click(function(){
        if($("#checkall").prop("checked")){
            $("input[name=box]").prop("checked",true);
        }else{
            $("input[name=box]").prop("checked",false);
        }
    })
    
    $("#selectListCnt").change(function(){
    	document.frm.pageIndex.value = 1;
      	document.frm.action = "<c:url value='${tmpPath}/com/financial/salary/salaryList.do?affiliationId=S'/>";
    	document.frm.submit();
    })
});


function fn_GotoSearch(pageNo){
	document.frm.pageIndex.value = pageNo;
	document.frm.action = "<c:url value='${tmpPath}/com/financial/salary/salaryList.do?affiliationId=S'/>";
	document.frm.submit();
}

function fn_searchSalary(){
	document.frm.startDate.value = $("#monthpicker2").val().replace("/","");
	document.frm.endDate.value = $("#monthpicker3").val().replace("/","");
	document.frm.pageIndex.value = 1;
	
  	document.frm.action = "<c:url value='${tmpPath}/com/financial/salary/salaryList.do?affiliationId=S'/>";
	document.frm.submit();
}

function fn_goSalaryDetail(ymonth, affiliationId){
	document.frm.ymonth.value = ymonth;
	
  	document.frm.action = "<c:url value='/com/financial/salary/salaryDetail.do?affiliationId=S'/>";
	document.frm.submit();
}

function fn_clickExcel()
{
	$("#excel").trigger("click"); 
}

function fn_insertExcel()
{
	document.frm.ymonth.value = document.frm.ymonth2.value;
	var thumbext = document.frm.excel.value;
	thumbext = thumbext.slice(thumbext.indexOf(".") + 1).toLowerCase();
	document.frm.extension.value = thumbext;
	document.frm.ymonth.value = document.frm.ymonth.value.replace("/","")

 	if(document.frm.excel.value == "")
	{
		alert("엑셀파일을 선택해주세요.");
		return;
	}
	
	if(document.frm.ymonth.value == "")
	{
		alert("급여일자를 입력해주세요.");
		return;
	}
	
	if(document.frm.payDt.value == "")
	{
		alert("지급일을 입력해주세요.");
		return;
	}
	
	if(confirm("엑셀업로드를 하시겠습니까?"))
	{
   		document.frm.action = "<c:url value='/com/financial/salary/salaryExcelUpload.do?affiliationId=S'/>";
		document.frm.submit();
	}
}

function fn_downloadExcel()
{
	if($("input[name=box]:checked").size() == 0){
		alert("선택된 건이 없습니다.");	
		return;
	}
	
	document.frm.ymonth.value = "";
	$("input[name=box]:checked").each(function() {
		var ymonth = $(this).val().replace("/","");
		document.frm.ymonth.value += ymonth+"/";
	});
		document.frm.action = "<c:url value='/com/financial/salary/downloadExcel.do?affiliationId=S'/>";
		document.frm.submit();
}

function fn_egov_select_noticeList(pageNo) {

	document.frm.pageIndex.value = pageNo;
	document.frm.action = "<c:url value='${tmpPath}/com/financial/salary/salaryList.do?affiliationId=S'/>";
	document.frm.submit();

}

function fn_formDown() {
	
	document.frm.action = "<c:url value='/com/epay/draft/excelFormDownload.do'/>";
	document.frm.submit();
}
</script>

<div id="subwrap">
<form name="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="extension">
	<input type="hidden" name="pageIndex" value="<c:out value='${salaryMasterVO.pageIndex}'/>" />
	<input type="hidden" name="pageUnit" value="${salaryMasterVO.pageUnit}" />
	<input type="hidden" name="startDate" value="${sdate}">
	<input type="hidden" name="endDate" value="${edate}">
	
	<h1>급여관리(에스메이커)</h1>
	<div class="btngroup fr">
		<label><input type="radio" name="chkAffili" id="affiliationId" value="I">이튜</label>
		<label><input type="radio" name="chkAffili" id="affiliationId" value="S" checked="checked">에스메이커</label>
	</div>
	<br><br>
	<div class="schgroup">
		<table class="tablesch">
			<colgroup>
				<col class="width100">
				<col>
				<col class="width100">
			</colgroup>
			<tbody>
				<tr>
					<th>검색</th>
					<td>	
						<input type="date" class="datepicker width200" id="monthpicker2">&nbsp;&nbsp;~&nbsp; 
						<input type="date" class="datepicker width200" id="monthpicker3">&nbsp;
						<button type="button" class="btn03" onclick="fn_searchSalary()">검색</button>

					</td>
					<th>업로드</th>
					<td>
							급여일자: <input type="hidden" class="width120" name="ymonth">
								   <input type="date" class="width120" name="ymonth2" id="ymonth2">&nbsp;&nbsp;&nbsp;
							지급일자: <input class="datepicker width120" name="payDt" id="payDt">
							&nbsp; &nbsp; &nbsp; 
							<input id="excel" type="file" class="form-control" name="excel" style="display:none" onchange="fn_insertExcel()"/> 	
							<button type="button" onclick="fn_clickExcel()" class="btn01">엑셀 업로드</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="space20"></div>
	<div class="btngroup fr">
		<button type="button" class="btn01" onclick="fn_downloadExcel()">엑셀 다운로드</button>
		<button type="button" onclick="fn_formDown()" class="btn01">엑셀양식 다운로드</button>
	</div>
	<select class="select" id="selectListCnt" name="selectListCnt">
		<option value="10" <c:if test="${paginationInfo.recordCountPerPage == 10 }">selected=selected</c:if> >10개 보기 </option>
		<option value="50" <c:if test="${paginationInfo.recordCountPerPage == 50 }">selected=selected</c:if> >50개 보기</option>
		<option value="100" <c:if test="${paginationInfo.recordCountPerPage == 100 }">selected=selected</c:if> >100개 보기</option>
		<option value="1" <c:if test="${paginationInfo.recordCountPerPage == 1 }">selected=selected</c:if> >전체 보기</option>
	</select>
	&nbsp; &nbsp; &nbsp; ※ 재 업로드 시, 기존의 급여내역은 삭제 됩니다. 주의해서 등록하세요.
	<table class="tablelist">
		<thead>
			<tr>
				<th>선택<input type="checkbox" id="checkall" />
				</th>
				<th>구분</th>
				<th>지급일</th>
				<th>지급총액</th>
				<th>공제총액</th>
				<th>실지급액</th>
			</tr>
		</thead>
		<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<tr>
						<td><input type="checkbox" name="box" class="checkSelect" value="${result.ymonth }"></td>
							<c:set var= "ymonthValue" value="${fn:substring(result.ymonth,0,4) }년 ${fn:substring(result.ymonth,4,6) }월" />
						<td><a href="#LINK" onclick="fn_goSalaryDetail('${result.ymonth }','${result.affiliationId }')">${ymonthValue } 급여</a></td>
						<td>${result.payDt }</td>
						<td>${result.totalIncrease }</td>
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
