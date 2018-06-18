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
	function fn_save(){
		if($("select[name=taskStep]").val() == ''){
			alert("<spring:message code='msg.required'  arguments='진행단계'/>");
			$("select[name=taskStep]").focus();
			return;
		}
		if(document.frm.schdulNm.value == ''){
			alert("<spring:message code='msg.required'  arguments='일정명'/>");
			document.frm.schdulNm.focus();
			return;
		}
		if(document.frm.schdulSe.value == ''){
			alert("<spring:message code='msg.required'  arguments='일정구분'/>");
			return;
		}
		if(document.frm.schdulSdt.value == ''){
			alert("<spring:message code='msg.required'  arguments='시작일'/>");
			document.frm.schdulSdt.focus();
			return;
		}
		if(document.frm.schdulEdt.value == ''){
			alert("<spring:message code='msg.required'  arguments='종료일'/>");
			document.frm.schdulEdt.focus();
			return;
		}
		if(new Date(document.frm.schdulSdt.value)*1 > new Date(document.frm.schdulEdt.value)*1){
			alert("<spring:message code='msg.date.confirm'/>");
			document.frm.schdulSdt.focus();
			return;
		}
		if(!confirm("<spring:message code='msg.save.confirm'/>")){
			return;
		}
		if($("input[type=checkbox]").is(":checked")){
			document.frm.schdulIpcrCode.value = 'Y'
		}else{
			document.frm.schdulIpcrCode.value = 'N'
		}
		$("#relCmngList span").each(function(){
			document.frm.ncrdId.value = document.frm.ncrdId.value + "//" + $(this).attr("ncrdId");
		});
		document.frm.action="<c:url value='/com/task/taskScheduleInsert.do'/>";
		document.frm.submit();
	}
	$(document).ready(function(){
		fn_changeType();
		var datetimepicker  = $("input[name=schdulFinDt]").data("kendoDateTimePicker");
		$("input[name=schdulLateReson]").attr("readonly",true);
		datetimepicker.enable(false);
		$("input[name=schdulPrgrsSttus]").on("click",function(){
			if($(this).val() == '4' || $(this).val() == '5'){
				$("input[name=schdulLateReson]").attr("readonly",false);
			}else{
				$("input[name=schdulLateReson]").attr("readonly",true);
			}
		});
		
		$("input[name=schdulPrgrsSttus]").on("click",function(){
			var datetimepicker  = $("input[name=schdulFinDt]").data("kendoDateTimePicker")
			if($(this).val() == '3' || $(this).val() == '5'){
				 datetimepicker.enable();
			}else{
				 datetimepicker.enable(false);
			}
		});
		
		$("input[name=schdulType]").click(function(){
			//if($(this).val() != 2){
				fn_changeType($(this).val());
			//}			
		});
		
/* 		$("input[name=schdulSdt]").blur(function(){
			if($("input[name=schdulType]:checked").val() == 2){
				if(18 <=new Date($("input[name=schdulSdt]").val()).getHours() && new Date($("input[name=schdulSdt]").val()).getHours() <=0){
					
				}
			}
		}); */
	});
	
	function fn_changeType(type){
		$("input[name=schdulSdt]").val('');
		$("input[name=schdulEdt]").val('');	
		(function($) {
	        var dateTimePicker = kendo.ui.DateTimePicker;

	        var MyWidget = dateTimePicker.extend({

	            init: function(element, options) {
	                dateTimePicker.fn.init.call(this, element, options);
	            },
	            startTime: function(startTime) {
	                var timeViewOptions = this.timeView.options;
	                timeViewOptions.min = startTime;
	                this.timeView.setOptions(timeViewOptions);
	            },
	            endTime: function(endTime) {
	                var timeViewOptions = this.timeView.options;
	                timeViewOptions.max = endTime;
	                this.timeView.setOptions(timeViewOptions);
	            },
	            options: {
	                name: "CustomDateTimePicker",
	                interval : 60,
	                format: "yyyy/MM/dd HH:mm"
	            }
	        });

	        kendo.ui.plugin(MyWidget);

	    })(jQuery);
		var datetimepickerS  = $("input[name=schdulSdt]").kendoCustomDateTimePicker().data("kendoCustomDateTimePicker");
		var datetimepickerE  = $("input[name=schdulEdt]").kendoCustomDateTimePicker().data("kendoCustomDateTimePicker");
		var now  = new Date();
		var dd = now.getDate();
		var mm = now.getMonth(); //January is 0!
		var yyyy = now.getFullYear();
		if(type == '1'){
			datetimepickerS.startTime("09:00");
			datetimepickerS.endTime("18:00");
			datetimepickerE.startTime("09:00");
			datetimepickerE.endTime("18:00");
		}else if(type == '2'){
			if(18<=new Date().getHours() && new Date().getHours()<0){;
				datetimepickerS.min(new Date(yyyy,mm,dd,18));
				datetimepickerS.max(new Date(yyyy,mm,dd,23));
				//datetimepickerS.startTime("18:00");
				//datetimepickerS.endTime("00:00");
				datetimepickerE.min(new Date(yyyy,mm,dd,18));
				datetimepickerE.max(new Date(yyyy,mm,dd,24));
				datetimepickerE.startTime("18:00");
				datetimepickerE.endTime("00:00");
			}else if(0<=new Date().getHours() && new Date().getHours()<9){
				datetimepickerS.min(new Date(yyyy,mm,dd,0));
				datetimepickerS.max(new Date(yyyy,mm,dd,9));
				datetimepickerE.min(new Date(yyyy,mm,dd,0));
				datetimepickerE.max(new Date(yyyy,mm,dd,9));
			}else{				
				datetimepickerS.min(new Date(yyyy,mm,dd,18));
				datetimepickerS.max(new Date(yyyy,mm,dd,23));
				//datetimepickerS.startTime("18:00");
				//datetimepickerS.endTime("00:00");
				datetimepickerE.min(new Date(yyyy,mm,dd,18));
				datetimepickerE.max(new Date(yyyy,mm,dd+1,0));
				//datetimepickerE.startTime("18:00");
				//datetimepickerE.endTime("00:00");
				
			}  
		}else if(type == '3'){

		}else{
			datetimepickerS.startTime("09:00");
			datetimepickerS.endTime("18:00");
			datetimepickerE.startTime("09:00");
			datetimepickerE.endTime("18:00");
		}
		
	}
	
	function fn_openRelaCmngPopup(){
		window.open("<c:url value='/com/task/popup/schdulRelaCmngPopup.do'/>","","width=1100px,height=800px");
	}
	
	function fn_returnRelaCmng(data){ 
		var flag = false;
	 	for(i = 0 ;i<data.length;i++){
	 		flag =false;
	 		var tmp = data[i].split("||");
	 		$("#relCmngList span").each(function(){
	 			if(tmp[0] == $(this).attr("ncrdId")){
		 			alert(tmp[1]+"  "+"<spring:message code='msg.relaCmng.dupli'/>");
		 			flag = true;
		 			return false;
		 		}
	 		});
	 		if(flag){
	 			continue;
	 		}
	 		
	 		var template = "";
	 		if($("#relCmngList span").length != 0){
	 			template  += "<span ncrdId="+tmp[0]+">,"+tmp[1]+"("+tmp[2]+")" 				
			}else{
				template  += "<span ncrdId="+tmp[0]+">"+tmp[1]+"("+tmp[2]+")" 
			}
	 		
 		    template += 	"<button type='button' onclick='javascript:fn_relaRemove(this)'>X</button>";
 		    template +=   "</span>" 
	 		$("#relCmngList").append(template);			
		}  
		
	}
	function fn_relaRemove(obj){
		$(obj).parent().remove();
	}
</script>
<div id="subwrap">
	<form name="frm" method="post">
		<input type="hidden" name="ncrdId" value=""/>
		<input type="hidden" name="schdulIpcrCode"/>
		<input type="hidden" name="taskId" value="<c:out value='${taskVO.taskId }'/>"/>
		<input type="hidden" name="taskStepId" value="<c:out value='${taskVO.taskStepId }'/>"/>
		<table class="tablewrite">
			<colgroup>
				<col class="width100">
				<col class="width600">
			</colgroup>
			<tbody>
				<tr>
					<th>연결된과제</th>
					<td>
						과제명  :<c:out value="${taskStep[0].taskNm }"/><br>
						진행단계
						<select class="select" name="taskStep">
							<option value="">선택</option>
							<c:forEach var="step" items="${taskStep }" varStatus="status">
								<c:if test="${step.ptaskStepId ne '0' or (step.ptaskStepId eq '0' and step.cnt eq '0')}">
									<option value="${step.taskStep }"><c:out value="${step.stepNm }"/></option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>일정명</th>
					<td>
						<input type="text" class="k-textbox" name="schdulNm"/>
						<input type="checkbox" class="k-checkbox" id="checkbox"><label class="k-checkbox-label" for="checkbox">중요일정</label>
					</td>				
				</tr>
				<tr>
					<th>일정구분</th>
					<td>
						<c:forEach var="code" items="${codeList }" varStatus="status">
							<input type="radio" class="" id="radio304${status.index }" name="schdulSe" value="<c:out value='${code.code }'/>"/><label for="radio304${status.index }"><c:out value='${code.codeNm }'/></label>	
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>일정기간</th>
					<td>
						시작<input class="datetimepicker width200" name="schdulSdt" placeholder="시행일자" title="시행일자">~
						종료<input class="datetimepicker width200" name="schdulEdt" placeholder="시행일자" title="시행일자">
						<input type="radio" class=""  name="schdulType" id="schdulType1" value="1" checked="checked"/><label for="schdulType1">일반</label>
						<input type="radio" class=""  name="schdulType" id="schdulType2" value="2"/><label for="schdulType2">야간</label>
						<input type="radio" class=""  name="schdulType" id="schdulType3" value="3"/><label for="schdulType3">주말</label>
					</td>
				</tr>
				<tr>
					<th>일정진행상태</th>
					<td>
						<input type="radio" class="" id="radio1" name="schdulPrgrsSttus" value="1" checked="checked"/><label for="radio1">진행전</label>
						<input type="radio" class="" id="radio2" name="schdulPrgrsSttus" value="2"/><label for="radio2">진행중</label>
						<select class="select" name="schdulPrgrs">
							<c:forEach begin="0" end="10" varStatus="status" >
								<option value="${status.index*10 }"><c:out value='${status.index*10 }'/>%</option>
							</c:forEach>
						</select>
						<input type="radio" class="" id="radio3" name="schdulPrgrsSttus" value="3"/><label for="radio3">완료</label>
						<input type="radio" class="" id="radio4" name="schdulPrgrsSttus" value="4"/><label for="radio4">지연</label>
						<input type="radio" class="" id="radio5" name="schdulPrgrsSttus" value="5"/><label for="radio5">지연완료</label>
						<input type="radio" class="" id="radio6" name="schdulPrgrsSttus" value="6"/><label for="radio6">중단</label>
						완료일시<input class="datetimepicker width200" name="schdulFinDt" placeholder="시행일자" title="시행일자">
						<input type="text" class="k-textbox" name="schdulLateReson"/>
					</td>
				</tr>
				<tr>
					<th>관련업체사원</th>
					<td>
						<div id="relCmngList">
						</div>
						<button type="button" class="btn03" onclick="javascript:fn_openRelaCmngPopup();">선택</button>
					</td>
				</tr>
			</tbody>
			</table>
		</form>
		<div class="space20"></div>
		<button type="button" class="btn03" onclick="javascript:history.back()">취소</button>
		<button type="button" class="btn03" onclick="javascript:fn_save();">저장</button>
</div>