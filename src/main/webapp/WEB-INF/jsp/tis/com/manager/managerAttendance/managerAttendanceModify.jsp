<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
var writeTp = false;

	$(document).ready(function() {
        function startChange() {
            var startTime = start.value();

            if (startTime) {
                startTime = new Date(startTime);

                end.max(startTime);

                startTime.setMinutes(startTime.getMinutes() + this.options.interval);

                end.min(startTime);
                end.value(startTime);
            }
        }

        //init start timepicker
        var start = $("#wrkStartTime").kendoTimePicker({
        	format: "HH:mm",
            change: startChange
        }).data("kendoTimePicker");

        //init end timepicker
        var end = $("#wrkEndTime").kendoTimePicker({
        	format: "HH:mm",
        }).data("kendoTimePicker");

        //define min/max range
        start.min("08:00");
        start.max(end.value());

        //define min/max range
        end.min(start.value());
        

    	var start = $("#outWrkStime2").kendoDateTimePicker({
    		value: "${fn:substring(result.outWrkStime,0,4)}/${fn:substring(result.outWrkStime,5,7)}/${fn:substring(result.outWrkStime,8,10)} ${fn:substring(result.outWrkStime,11,16)}",
    		format: "yyyy/MM/dd HH:mm",
    		timeFormat:"HH:mm"
    	}).data("kendoDateTimePicker");
    	
    	var end = $("#outWrkEtime2").kendoDateTimePicker({
    		value: "${fn:substring(result.outWrkEtime,0,4)}/${fn:substring(result.outWrkEtime,5,7)}/${fn:substring(result.outWrkEtime,8,10)} ${fn:substring(result.outWrkEtime,11,16)}",
    		format: "yyyy/MM/dd HH:mm",
    		timeFormat:"HH:mm"
    	}).data("kendoDateTimePicker");
    	
    	end.min(start.value());
	});
	
	function fn_GotoSave(){
	
		if (writeTp) {
			alert("저장 중입니다.");
			return;
		}
		
		if(document.subForm.wrkStartTime.value == ""){
			alert("출근시간을 입력하세요");
			return;
		}
		
		if(document.subForm.wrkEndTime.value == ""){
			alert("퇴근시간을 입력하세요");
			return;
		}
		
		document.subForm.wrkStartTime.value = document.subForm.wrktDt.value + " " + document.subForm.wrkStartTime.value+":00";
		document.subForm.wrkEndTime.value = document.subForm.wrktDt.value + " " + document.subForm.wrkEndTime.value+":00";
		document.subForm.action = "<c:url value='/com/manager/managerAttendance/managerAttendanceSave.do'/>";
		document.subForm.submit();
		
		writeTp = true;
	}
	
	function fn_GotoDetail(){
		document.subForm.action = "<c:url value='/com/manager/managerAttendance/managerAttendanceDetail.do'/>";
		document.subForm.submit();
	}
	
</script>

<div id="subwrap">
	<h1>근태관리 > 상세 > 수정</h1>
	<form name="subForm" method="post">
	<input type="hidden" name="emplyrId" value='<c:out value="${userManageVO.uniqId}"/>'>
	<input type="hidden" name="wrktmId" value='<c:out value="${result.wrktmId}"/>'>
	<table class="tableview">
		<colgroup>
			<col class="width180">
			<col class="width600">
			<col class="width180">
			<col class="width600">
		</colgroup>
		<tbody>
			<tr>
				<th>성명</th>
				<td><input type="text" class="k-textbox width100p" readonly="readonly"  value='<c:out value='${userManageVO.emplyrNm}'/>'></td>
				<th>사원번호</th>
				<td><input type="text" class="k-textbox width100p" readonly="readonly" value='<c:out value='${userManageVO.emplNo}'/>'></td>
			</tr>
			<tr>
				<th>출근일</th>
				<td colspan="3"><input type="text" name="wrktDt" class="k-textbox width100p" readonly="readonly" value='<c:out value='${result.wrktDt}'/>'></td>				
			</tr>
			<tr>
				<th>출근시간</th>
				<td><input name="wrkStartTime" id="wrkStartTime" value='<c:out value='${result.wrkStartTime}'/>'></td>
				<th>퇴근시간</th>
				<td><input name="wrkEndTime" id="wrkEndTime" value='<c:out value='${result.wrkEndTime}'/>'></td>
			</tr>
			<c:if test="${result.outWrkPlace ne null }">
				<tr>
					<th>출장/외근지</th>
					<td><input type="text"  name="outWrkPlace" class="k-textbox width100p" value='<c:out value='${result.outWrkPlace}'/>'></td>
					<th>출장/외근 사유</th>
					<td><input type="text"  name="outRm" class="k-textbox width100p" value='<c:out value='${result.outRm}'/>'></td>
				</tr>
				<tr>
					<th>출장/외근 시작시간</th>
					<td><input type="text" id="outWrkStime2" name="outWrkStime" ></td>
					<th>출장/외근 종료시간</th>
					<td><input type="text" id="outWrkEtime2" name="outWrkEtime" ></td>
				</tr>
			</c:if>
			<tr>
				<th>퇴근사유</th>
				<td colspan="3"><input type="text"  name="rm" class="k-textbox width100p" value='<c:out value='${result.rm}'/>'></td>
			</tr>
			<tr>
				<th>수정사유</th>
				<td colspan="3">
					<c:choose>
						<c:when test="${result.modifyReason eq null}">
							<input type="text"  name="modifyReason" class="k-textbox width100p" value="[관리자 수정]" >
						</c:when>
						<c:otherwise>
							<input type="text"  name="modifyReason" class="k-textbox width100p" value='<c:out value='${result.modifyReason}'/>'>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</tbody>
	</table>
	</form>
	<div class="space20"></div>
	<div class="btngroup fr">
		<button type="button" class="btn02" onclick="javascript:fn_GotoDetail();">취소</button>
		<button type="button" class="btn01" onclick="javascript:fn_GotoSave();">저장</button>
	</div>
	
</div>
</body>

</html>