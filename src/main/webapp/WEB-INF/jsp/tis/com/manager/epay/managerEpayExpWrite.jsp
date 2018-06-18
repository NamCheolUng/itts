<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>

$(document).ready(function(){
	var f = document.frm;
	
	if("${epayDraftInfoVO.draftInfoId}" != "") {
		f.act.value = 'update';
	}
	
	
	var dataPickerS = $("#dpkrSdate").data("kendoDatePicker");
	
	var dataPickerE = $("#dpkrSdate").data("kendoDatePicker");
	
	
	$("#dpkrEdate").kendoDatePicker({
		numberOfMonths: 2,
		onSelect: function(selected) {
			$("#dpkrSdate").datepicker("option","maxDate", selected)
		}
	});
});

var dateEditor = function (container, options) {
	var input = $('<input />');

	input.appendTo(container)
	  .kendoDatePicker({
	    format: "dd.MM.yyyy"
	  });
	var datePicker = input.data("kendoDatePicker");
	  switch (options.field) {
	    case "startDate":
	      if (options.model.finishDate) {
	        datePicker.max(options.model.finishDate);
	      }
	      break;

	    case "finishDate":
	      if (options.model.startDate) {
	        datePicker.min(options.model.startDate);
	      }
	      break;
	  }
	};

function fn_requestDraftList(){
	document.frm.method.value = 'payment';
	document.frm.action = "<c:url value='/com/epay/draft/epayDraftInfoList.do'/>";
	document.frm.submit();
}

function btn_requestWriteCancel(){
	document.frm.method.value = 'payment';
	document.frm.action = "<c:url value='/com/epay/draft/epayExpWriteCancel.do'/>";
	document.frm.submit();
}

// 임시저장 버튼 클릭 이벤트 함수
function btn_tmpSave(){
	
	var f = document.frm;
	
	if(f.title.value=="") {
		alert("제목을 입력하세요.");
		f.title.focus();
		return false;
	}
	
	if($("#expHistTable tr").length <= 1){
		  alert("입력된 지출내역 정보가 없습니다.");
		  return;
	}
	
	if(confirm('임시저장 하시겠습니까?')){
		
		if(f.act.value=='write'){ // 신규 -> 임시저장
			f.action = "<c:url value='/com/epay/draft/epayExpWriteTemp.do'/>";
		}else { // 임시저장 -> 임시저장
			f.action = "<c:url value='/com/epay/draft/epayExpWriteTempUpdate.do'/>";
		}
		f.submit();
	}
}


function btn_submit(apprType){
		
	var f = document.frm;
	
	if(f.title.value=="") {
		alert("제목을 입력하세요.");
		f.title.focus();
		return false;
	}
	
	if($("#expHistTable tr").length <= 1){
		  alert("입력된 지출내역 정보가 없습니다.");
		  return;
	}
	
	if(confirm("<spring:message code='msg.update.confirm' />")){
		
		f.action = "<c:url value='/com/manager/epay/managerEpayExpWriteTempUpdate.do'/>";
		f.submit();
	}
}


function fn_egov_downFile(atchFileId, fileSn){
	window.open("<c:url value='/cmm/fms/FileDown.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>");
}

function fn_egov_deleteFile(atchFileId, fileSn) {
	forms = document.getElementsByTagName("form");

	if(confirm('삭제하시겠습니까?')){
		for (var i = 0; i < forms.length; i++) {
			if (typeof(forms[i].atchFileId) != "undefined" &&
					typeof(forms[i].fileSn) != "undefined" &&
					typeof(forms[i].fileListCnt) != "undefined") {
				form = forms[i];
			}
		}

		//form = document.forms[0];
		form.atchFileId.value = atchFileId;
		form.fileSn.value = fileSn;
		form.action = "<c:url value='/cmm/fms/deleteFileInfs.do'/>";
		form.submit();
	}
}

function fn_egov_check_file(flag) {
	if (flag=="Y") {
		document.getElementById('file_upload_posbl').style.display = "block";
		document.getElementById('file_upload_imposbl').style.display = "none";
	} else {
		document.getElementById('file_upload_posbl').style.display = "none";
		document.getElementById('file_upload_imposbl').style.display = "block";
	}
}

// 첨부파일 추가
function addAtchFile() {
	
	alert('addAtchFile');
	//if($('.atchFile').length) {
		var template = "<tr>";
		template += "<th>첨부파일</th>";
		template += "		<td>";
		template += "	<div class=\"inline\">";
		template += "			<input name=\"\" class=\"inputFile width400 atchFile8\" type=\"file\">";
		template += "			<button type=\"button\" class=\"btn2 fileplus\" onclick=\"deleteAtchFile(this)\"><i class=\"fa fa-minus\" aria-hidden=\"true\"></i>&nbsp;삭제</button>";
		template += "	</div>";
		template += "		</td>";
		template += "</tr>";
		$("#atchFile").append(template);
	//}
}

var idx = 0;

// 지출내역 동적 행 추가 문자열 생성 
function addExpHistRow(){
	idx++;
	// 기본 서식
	var template =	"<tr>";
	    template +=	"	<td><input class='datepicker'"+idx+" width120\" value='' placeholder='' title='''>";
	    template +=	"	</td>";
	    template +=	"	<td>";
	    template +=	"		<select class=\"select"+idx+" width400\">";
	    template +=	"			<option>과제명</option>";
	    template +=	"		</select>";
	    template +=	"	</td>";
	    template +=	"	<td>";
	    template +=	"		<select class=\"select width100p\">";
	    template +=	"			<option>식대비</option>";
	    template +=	"			<option>유류비</option>";
	    template +=	"			<option>통행비</option>";
	    template +=	"			<option>주차비</option>";
	    template +=	"			<option>배송비</option>";
	    template +=	"			<option>접대비</option>";
	    template +=	"		</select>";
	    template +=	"	</td>";
	    template +=	"	<td><input type=\"text\" class=\"k-textbox width100p\" value=\"경대식당\">";
	    template +=	"	</td>";
	    template +=	"	<td><input type=\"text\" class=\"k-textbox width100p\" value=\"석식 4명\">";
	    template +=	"	</td>";
	    template +=	"	<td><input type=\"text\" name=\'expPrice\' id='expPrice'"+idx+" class=\"k-textbox width100p expPrice\" value=\"19000\" onChange=\"javascript:fn_sumExpPrice();\">";
	    template +=	"	</td>";
	    template +=	"	<td><i class=\"fas fa-cloud-download-alt\"></i><a href=\"#\" onclick=\"return deleteRow(this);\"><i class=\"fas fa-window-close\"></i></a>";
	    template +=	"	</td>";
	    template +=	"</tr>";
	    
	    var template2 =	'<tr><td><input class="datepicker'+idx+' width120"></td><td><select class=\"select'+idx+' width400\"><option>과제명</option></select></td><td><select class=\"select'+idx+' width100p\"><option>식대비</option><option>유류비</option><option>통행비</option><option>주차비</option><option>배송비</option><option>접대비</option></select></td><td><input type=\"text\" class=\"k-textbox width100p\" value=\"경대식당\"></td><td><input type=\"text\" class=\"k-textbox width100p\" value=\"석식 4명\"></td><td><input type=\"text\" name=\'expPrice\' id=\'expPrice\'"+idx+" class=\"k-textbox width100p expPrice\" value=\"19000\" onChange=\"javascript:fn_sumExpPrice();\"></td><td><i class=\"fas fa-cloud-download-alt\"></i><a href=\"#\" onclick=\"return deleteRow(this);\"><i class=\"fas fa-window-close\"></i></a></td></tr>';
	    /* var template2 =	"<tr><td><input class=datepicker"+idx+" width120\" value='' placeholder='' title='''></td><td><select class=\"select"+idx+" width400\"><option>과제명</option></select></td><td><select class=\"select width100p\"><option>식대비</option><option>유류비</option><option>통행비</option><option>주차비</option><option>배송비</option><option>접대비</option></select></td><td><input type=\"text\" class=\"k-textbox width100p\" value=\"경대식당\"></td><td><input type=\"text\" class=\"k-textbox width100p\" value=\"석식 4명\"></td><td><input type=\"text\" class=\"k-textbox width100p\" value=\"19,000\"></td><td><i class=\"fas fa-cloud-download-alt\"></i><a href=\"#\" onclick=\"return deleteAtchFile(this);\"><i class=\"fas fa-window-close\"></i></a></td></tr>"; */
	    
	    /* var cel1StringTest = '<input class="datePicker'+idx+'" value="" placeholder="" title="">'; */
	    
	    
 	    $("#my-tbody").append(template2).trigger("create");
	    
 	   $(".datepicker"+idx).last().kendoDatePicker({
 		  format: "yyyy/MM/dd"
 	   });
 	   
 	    
	   /*  $(".datepicker"+idx).kendoDatePicker({
			value: "",
            min: new Date(2010, 0, 1),
            max: new Date(2100, 11, 31),
            format: "yyyy/MM/dd"
        });  */
	    
	    $(".select"+idx).kendoDropDownList();
        $(".select"+idx).css({
        	'text-align' : 'left'	
        });
        
        fn_sumExpPrice();
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
  
//체크한 첨부파일 삭제
function deleteRow(obj) {
	$(obj).parent().parent().remove();
	fn_sumExpPrice();
}

//파일 업로드 하기 전 이름 변경
function reNameAtchFile() {
	for (i = 0; i < $(".atchFile").length; i++) {
		$(".atchFile:eq(" + i + ")").attr("name", "file_" + i);
	}
}

// 기존 첨부파일 버튼의 대체 역할로 사용되는 버튼 클릭 이벤트 연동
$(function (){
    $('#filebtn').click(function (e) {
        e.preventDefault();
        $('#orgFile').click();
    });
});

// 지출내역 자동 합계 계산
function fn_sumExpPrice(){
	var totPrice = 0;
	
	$('.expPrice').each(function(){
		 totPrice += parseFloat($(this).val());
	});
	
	$('#expTotPrice').text(numberWithCommas(totPrice));
}

// 모바일내역 불러오기
 function fn_LoadMobileHist(){
	
	$("#my-tbody").empty();
	//alert('$("#expTotPrice").val : ' + $("#expTotPrice").val());
	$("#expTotPrice").empty();
	//alert('$("#expTotPrice").val : ' + $('#expTotPrice').val());
	
 	var sdate = $('[name="expSdate"]').val();
	var edate = $('[name="expEdate"]').val();
	
	//alert(sdate + edate); 
	
	$.ajax({
		type:"post",
		//url:"<c:url value='/com/epay/draft/epayExpWriteLoadData.do'/>",
		url:"<c:url value='/com/manager/epay/managerEpayExpWriteLoadData.do'/>",
		data:{
			//emplNo:	'<c:out value="${epayDraftInfoVO.emplNo}" />'
			//, expSdate:sdate
			//, expEdate:edate
			expStatId:	'<c:out value="${epayExpVO.expStatId}" />'
			},
		dataType : 'json',
		type : 'POST',
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		success:function(data){
			
			$.each(data.epayExpHistList, function(index, item){
			    var template2 =	'<tr>';
			    	/* template2 += 	'<input type="hidden" name="list['+idx+'].expHistId" value="<c:out value="' + item.expHistId +'" />">'; */			    	
			    	template2 += 	'<input type="hidden" name="list['+idx+'].expHistId" value="'+ item.expHistId +'">';
			    	if(item.atchFileId != "" && item.atchFileId != null){
			    		template2 += 	'<td><input type="checkbox"  atchFileId="'+ item.atchFileId +'" class="k-checkbox" id="checkbox_'+idx+'"><label class="k-checkbox-label" for="checkbox_'+idx+'"></label></td>';		
			    	}else{
			    		template2 += 	'<td><input type="checkbox"  atchFileId="'+ item.atchFileId +'" class="k-checkbox" id="checkbox_'+idx+'" disabled=\'disabled\'><label class="k-checkbox-label" for="checkbox_'+idx+'"></label></td>';
			    	}
			    	
			    	template2 +=	'<td><input name="list['+idx+'].expDate" class="datepicker'+idx+' width120\" value="' + item.expDate + '\">';
			    	template2 +=	'</td>';
			    	template2 +=	'<td>';
			    	
			    	template2 +=		'<select name="list['+idx+'].taskId" class="select'+idx+' width400" id='+'"taskSelect'+idx+'">';
			    	template2 += 			'<c:forEach var="task" items="${taskList}" varStatus="status">';	
			    	template2 +=				'<option value="${task.taskId}">${task.taskNm}</option>';
			    	template2 +=			'</c:forEach>';
			    	template2 += 		'</select>';
			    	
			    	template2 +=	'</td>';
			    	template2 +=	'<td>';
			    	template2 +=		'<select name="list['+idx+'].expSubj" id="selectExpSubj'+idx+'" class="select' + idx+ ' width100p">';
			    	template2 +=	    	'<option value="1">식대비</option>';
			    	template2 +=	    	'<option value="2">유류비</option>';
			    	template2 +=	    	'<option value="3">유류비(법인차)</option>';
			    	template2 +=	    	'<option value="4">통행비</option>';
			    	template2 +=	    	'<option value="5">주차비</option>';
			    	template2 +=	    	'<option value="6">배송비</option>';
			    	template2 +=	    	'<option value="7">접대비</option>';
			    	template2 +=	    	'<option value="8">기자재</option>';
			    	template2 +=		'</select>';
			    	template2 +=	'</td>';
			    	template2 +=	'<td>';
			    	template2 +=		'<input name="list['+idx+'].expPlace" type="text" class="k-textbox width100p" value="'+item.expPlace+'">';
			    	template2 +=	'</td>';
			    	template2 +=	'<td>';
			    	template2 +=		'<input name="list['+idx+'].rm" type="text" class="k-textbox width100p" value="'+item.rm+'">';
			    	template2 +=	'</td>';
			    	template2 +=	'<td>';
			    	template2 +=		'<input type="text" name="list['+idx+'].price" id=\'expPrice\'"+idx+" class="k-textbox width100p expPrice" value="'+item.price+'" onChange="javascript:fn_sumExpPrice();">';
			    	template2 +=	'</td>';			    	
			    	template2 +=	'<td>';
			    	template2 +=		fn_setFileColor(item.atchFileId);
			    	<c:if test="${epayExpVO.acuntRegistYn ne 'Y'}">
			    		template2 += 		'<a href="#" onclick="return deleteRow(this);"><i class="fas fa-window-close"></i></a>';
			    	</c:if>
			    	template2 +=	'</td>';
			    	template2 +='</tr>';
			    
			    $("#my-tbody").append(template2).trigger("create");
			    
			 	$(".datepicker"+idx).last().kendoDatePicker({
			 		format: "yyyy/MM/dd"			 		
			 	});
			 	
			    // select 컨트롤 과제명 값 설정
				    
			    // select 컨트롤 계정과목 값 설정
			    $("#selectExpSubj"+idx).val(item.expSubj).prop("selected", true);
			    $("#taskSelect"+idx).val(item.taskId).prop("selected",true)
			    // 스타일 재적용
			    $(".select"+idx).kendoDropDownList();
			    $(".select"+idx).css({
			    	'text-align' : 'left'	
			    });
			    <c:if test="${epayExpVO.acuntRegistYn eq 'Y'}">
			 	 	var datepicker = $(".datepicker"+idx).last().data("kendoDatePicker");
			 	 	var dropdown =  $("#taskSelect"+idx).data("kendoDropDownList");
			 	 	var dropdown2  = $("#selectExpSubj"+idx).data("kendoDropDownList");
			 	 	datepicker.readonly();
			 	 	dropdown.readonly();
			 	 	dropdown2.readonly();
			 	 	$("input[type=text]").each(function(){
				 		$(this).attr("readonly",true);
				 	});
				</c:if>
				fn_sumExpPrice();				
				idx++;
			});
			
			$("input[type=checkbox]").on("click",function(){
				if($(this).attr("id") == "checkboxAll"){
					if($(this).is(":checked")){
						$("input[type=checkbox]").each(function(){
							if(!$(this).prop("disabled")){
								$(this).prop("checked",true);
							}
							
						});
					}else{
						$("input[type=checkbox]").each(function(){
							$(this).prop("checked",false);
						});
					}
				}else{
					var cntAll = 0;
					var cntChk = 0;
					$("input[type=checkbox]").each(function(){						
						if($(this).attr("id") != "checkboxAll" && !$(this).attr("disabled")){
							cntAll++;
							if($(this).is(":checked")){
								cntChk++;
							}
						}
					});
					if(cntAll == cntChk){
						$("#checkboxAll").prop("checked",true);
					}else{
						$("#checkboxAll").prop("checked",false);
					}
				}
				
			});
		} // end success fucntion				
	});	 // end jquery

}
	

function fn_setFileColor(fileId){
	if(fileId == "" || fileId == null){
		return '<i class=\"fas fa-cloud-download-alt\"></i>';
	}
	else{
		return '<a href=\"#\" onclick=\"javascript:fn_egov_downFile(\''+fileId+'\',\''+0+'\')\"><i class=\"fas fa-cloud-download-alt\"></i></a>';
	}
}

function fn_accountRegist(){
	if(confirm("<spring:message code='msg.acunt.confirm'/>"))
	document.frm.action="<c:url value='/com/manager/epay/managerEpayAccountRegist.do'/>";
	document.frm.submit();
}

function fn_receiptPrint(){
	var atchFileId = "";
 	if($("input[name=print]:checked").val() == undefined){
		alert("<spring:message code='msg.select.prtRadio'/>");
		return;
	}
 	var fileIdList = new Array();
	if($("input[type=checkbox]:checked").length != 0){ 
		$("input[type=checkbox]").each(function(){
			if($(this).attr("id") != "checkboxAll" && $(this).is(":checked")){
				if($(this).attr("id") != 'null' && $(this).attr("atchFileId") != undefined){
					fileIdList.push($(this).attr("atchFileId"))
				}			
			}
		});
	}else{
		alert("<spring:message code = 'msg.select.prtCheckbox'/>");
		return;
	}
	if(fileIdList == ""){
		alert("<spring:message code = 'msg.select.prtCheckbox'/>");
		return;
	}
	var printGbn = "";
	if($("input[name=print]:checked").val() == "A"){
		printGbn = "A";
	}else{
		printGbn = "4";	
	} 

     window.open("", "printPopup","width=800px ,height=800px") ;
	  document.printForm.printGbn.value=printGbn;
      document.printForm.atchFileIdList.value=fileIdList;
	  document.printForm.target = "printPopup";
	  document.printForm.action = "<c:url value='/com/manager/epay/managerEpayReceiptPrint.do'/>";   
	  document.printForm.submit() ;
	
}
</script>
<form name="printForm" method="post">
	<input type="hidden" name="printGbn" />
	<input type="hidden" name="atchFileIdList"/>
</form>
<form name="frm" method="post">
	<input type="hidden" name="act" value="write">
	<input type="hidden" name="atchFileId" value="<c:out value='${atchFileId}' />">
	<input type="hidden" name="draftInfoId"  value='<c:out value="${epayDraftInfoVO.draftInfoId}"/>'>
	<input type="hidden" name="expStatId"  value='<c:out value="${epayExpVO.expStatId}"/>'>
	<input type="hidden" name="expSdate"  value='<c:out value="${epayExpVO.expSdate}"/>'>
	<input type="hidden" name="expEdate"  value='<c:out value="${epayExpVO.expEdate}"/>'>
	<input type="hidden" name="emplNo"  value='<c:out value="${epayDraftInfoVO.emplNo}" />'>
		<div id="subwrap">	
			<div class="btngroup fl">		
			<button type="button" class="btn04" onclick="location.href='<c:url value ='/com/manager/epay/managerEpayDraftInfoList.do?method=payment'/>'">목록</button>
		</div>
		<div class="btngroup fr">
			<c:if test="${epayExpVO.acuntRegistYn ne 'Y' }"><button type="button" class="btn02" onclick="javascript:fn_accountRegist();">회계등록</button></c:if>
		</div>
		<div class="space10"></div>
		<h4><i class="fas fa-dot-circle"></i>지출명세서(개인)</h4>
		<table class="tablewrite">
			<colgroup>
				<col class="width180">
				<col class="width600">
				<col class="width180">
				<col class="width600">
			</colgroup>
			<tbody>
				<tr>
					<th>기안일자</th>
					<td><input name="regDt" class="datepicker width200" value='<c:out value="${epayDraftInfoVO.regDt}"/>' placeholder="기안일자" title="기안일자" readonly>
					</td>
					<th>기안부서</th>
					<td><c:out value="${epayDraftInfoVO.orgnztNm}"/></td>
				</tr>
				<tr>
					<th>기안자</th>
					<td name="userNm" id="emplNo"><c:out value="${epayDraftInfoVO.userNm}"/></td>
					<th>지출총액</th>
					<td id="expTotPrice" name="expTotPrice"><c:out value="${epayExpVO.expTotPrice}"/></td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan="3"><input name="title" type="text" class="k-textbox width100p" value='<c:out value="${epayDraftInfoVO.title}"/>' >
					</td>
				</tr>
				<tr>
					<th>지출내역</th>
					<td colspan="3">
						<div>
							<input type="radio" id="printA"  name ="print" value="A"><label for="printA">기본인쇄</label>
							<input type="radio" id="print4"  name ="print" value="4"><label for="print4">모아찍기(4장씩)</label>
							<button type="button" class="btn04" onclick="javascript:fn_receiptPrint();">영수증인쇄</button>
						</div>
						<div>
						<table class="tablelist" id="expHistTable">
							<colgroup>
								<col class="width80">
								<col class="width120">
								<col class="width400">
								<col>
								<col>
								<col>
								<col class="width150">
								<col class="width100">
							</colgroup>
							<thead>
								<tr>
									<th><input type="checkbox" id="checkboxAll" class="k-checkbox"><label class="k-checkbox-label" for="checkboxAll"></label></th>
									<th>날짜</th>
									<th>과제명</th>
									<th>계정과목</th>
									<th>지출처</th>
									<th>비고</th>
									<th>금액</th>
									<th>관리</th>
								</tr>
							</thead>
							<tbody id='my-tbody'>

							</tbody>
						</table>
						</div>
					</td>
				</tr>
				<tr>
					<th>비고</th>
					<td colspan="3"><textarea name="rm" class="k-textbox width100p height100" <c:if test="${epayExpVO.acuntRegistYn eq 'Y'}">readonly="readonly"</c:if>><c:out value="${epayExpVO.rm}"/></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="space20"></div>
		<div class="btngroup fl">		
			<button type="button" class="btn04" onclick="location.href='<c:url value='/com/manager/epay/managerEpayDraftInfoList.do'/>'">목록</button>
		</div>
		<div class="btngroup fr">
			<c:if test="${epayExpVO.acuntRegistYn ne 'Y' }"><button type="button" class="btn" onclick="javascript:btn_submit();">수정</button></c:if>
		</div>
	</div>
</form>
</body>
</html>
<script>
$(document).ready( function() {
    // 자동 실행 할 javascript 함수 또는 코드를 넣는다.
    fn_LoadMobileHist();
});
</script>
