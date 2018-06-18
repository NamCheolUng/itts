<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>

$(document).ready(function(){
	var f = document.frm;
	
	if("${epayDraftInfoVO.draftInfoId}" != "") {
		f.act.value = 'update';
	}
	
	$(".excelUpload").change(function(e){
		e.preventDefault();
		
		var form = $('form#frm')[0];
		var formData = new FormData(form);
			
		$.ajax({
			url: '<c:url value="/com/epay/draft/epayExpCorImportExcel.do"/>',
			processData: false,
			contentType: false,
			data: formData,
			type: 'POST',
			success: function(data){
				if(data.result == "success") {
					
					$("#my-tbody").empty(); // 기존 데이터 제거
					
					$.each(data.epayExpCorHistList, function(index, item){
						   
					    var template = '<tr>';
						    template += 	'<input type="hidden" name="list['+idx+'].expcorStatId" value="<c:out value="${expcorStatId}" />">';
						    template +=		'<td>';
						    template +=			'<select name="list['+idx+'].cardNum" id="selectCardNum'+idx+'" class="select'+idx+' width100p">';
						    template +=				'<c:forEach var="card" items="${tbBankcardmanageVOList}" varStatus="status">';	
						    template +=					'<option value="${card.cardNum}" <c:out value="${card.cardNum == '+item.cardNum+' ? \'selected\' : \'\'}"/>>${card.cardNum}</option>';
						    template +=				'</c:forEach>';
						    template +=			'</select>';
						    template +=		'</td>';
						    template +=		'<td><input name="list['+idx+'].accptDt" class="datepicker'+idx+' width120" value="'+item.accptDt+'" placeholder="" title="">';
						    template +=		'</td>';
						    template +=		'<td>';
						    template +=			'<select name="list['+idx+'].emplNo" class="select'+idx+' width100p" id="expcorHistEmplNo'+idx+'" onchange="javascript:fn_emplyrChanged('+idx+');">';
						    template +=				'<c:forEach var="emplyr" items="${emplyrList}" varStatus="status">';	
						    template +=					'<option value="${emplyr.emplNo}" <c:out value="${emplyr.emplNo == epayDraftInfoVO.emplNo ? \'selected\' : \'\'}"/>>${emplyr.userNm}</option>';
						    template +=				'</c:forEach>';
						    template +=			'</select>';
						    template +=		'</td>';
						    template +=		'<td>';
						    template +=			'<select name="list['+idx+'].taskId" id="selectTaskId'+idx+'" class="select'+idx+' width100p">';
						    template +=				'<c:forEach var="task" items="${taskList}" varStatus="status">';
						    template +=					'<option value="${task.taskId}">${task.taskNm}</option>';
						    template +=				'</c:forEach>';
						    template +=			'</select>';
						    template +=		'</td>';
						    template +=		'<td>';
						    template +=			'<select name="list['+idx+'].subjId" id="selectSubjId'+idx+'" class="select'+idx+' width100p">';  
						    template +=				'<c:forEach var="expcorSubj" items="${expcorSubjList}" varStatus="status">';	
						    template +=					'<option value="${expcorSubj.code}" <c:out value="${expcorSubj.code == '+item.subjId+' ? \'selected\' : \'\'}"/>>${expcorSubj.codeNm}</option>';
						    template +=				'</c:forEach>';
						    template +=			'</select>';
						    template +=		'</td>';
						    template +=		'<td><input name="list['+idx+'].expPlace" type="text" class="k-textbox width100p" value="'+item.expPlace+'">';
						    template +=		'</td>';
						    template +=		'<td><input name="list['+idx+'].rm" type="text" class="k-textbox width100p" value="'+item.rm+'">';
						    template +=		'</td>';
						    if(item.price != '')
						    	template +=		'<td><input name="list['+idx+'].price" id="expcorPrice'+idx+'" type="text" class="k-textbox width100p expcorPrice" value="'+item.price+'" onChange="javascript:fn_sumExpPrice();">';
						    else
						    	template +=		'<td><input name="list['+idx+'].price" id="expcorPrice'+idx+'" type="text" class="k-textbox width100p expcorPrice" value="0" onChange="javascript:fn_sumExpPrice();">';
						    template +=		'</td>';
						    template +=		'<td><a href="#" onclick="return deleteRow(this);"><i class="fas fa-window-close"></i></a>';
						    template +=		'</td>';
						    template +=	'</tr>';
					    
					    $("#my-tbody").append(template).trigger("create");
					    
					 	$(".datepicker"+idx).last().kendoDatePicker({
					 		format: "yyyy/MM/dd"
					 	});
					 	
					 	$("#selectCardNum"+idx).val(item.cardNum).prop("selected", true);
					    $("#selectSubjId"+idx).val(item.subjId).prop("selected", true);	    
					    
					    
					    
					 	// 스타일 재적용
					    $(".select"+idx).kendoDropDownList();
					    $(".select"+idx).css({
					    	'text-align' : 'left'	
					    }); 
					    
					    fn_sumExpPrice();
					    
						idx++;
					});
					
					//-----------------------
					alert("엑셀 불러오기 작업에 성공하였습니다.");
					//location.reload();
				} else {
					alert("엑셀 불러오기 작업에 실패하였습니다.");
				} 					
			}
		});
	});
});

function fn_requestDraftList(){
	document.frm.method.value = 'payment';
	document.frm.action = "<c:url value='/com/epay/draft/epayDraftInfoList.do'/>";
	document.frm.submit();
}

function btn_requestWriteCancel(){
	document.frm.method.value = 'payment';
	document.frm.action = "<c:url value='/com/epay/draft/epayExpCorWriteCancel.do'/>";
	document.frm.submit();
}

//임시저장 버튼 클릭 이벤트 함수
function btn_tmpSave(){
	
	var f = document.frm;
	
	$('#excelUpload').val('');
	
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
			f.action = "<c:url value='/com/epay/draft/epayExpCorWriteTemp.do'/>";
		}else { // 임시저장 -> 임시저장
			f.action = "<c:url value='/com/epay/draft/epayExpCorWriteTempUpdate.do'/>";
		}
		f.submit();
	}
}

function btn_submit(){
	
	var f = document.frm;
	
	$('#excelUpload').val('');
	
	if(f.title.value=="") {
		alert("제목을 입력하세요.");
		f.title.focus();
		return false;
	}
	
	if($("#expHistTable tr").length <= 1){
		  alert("입력된 지출내역 정보가 없습니다.");
		  return;
	}
	
	// 결재라인 선택 여부 검사
	var isApprLine = $("#inputApprLineList").children().length;
	
	if(isApprLine == 0){
		alert("선택된 결재라인이 없습니다.\n결재라인을 선택해주세요.");
		return false;
	}
	
	if(confirm('결재요청 하시겠습니까?')){
		
		if(f.act.value=='write'){// 신규 -> 결재요청시
			f.action = "<c:url value='/com/epay/draft/epayExpCorWriteInsert.do'/>";
		}else {// 임시저장 -> 결재요청
			f.action = "<c:url value='/com/epay/draft/epayExpCorWriteTempInsert.do'/>";
		}
		f.submit();
	}
}

function fn_egov_downFile(atchFileId, fileSn){
	window.open("<c:url value='/cmm/fms/FileDown.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>");
}

function fn_downloadExcel() {
	document.frm.action = "<c:url value='/com/epay/draft/epayExpCorHistListExcelDown.do'/>";
	document.frm.submit();
}

function fn_egov_deleteFile(atchFileId, fileSn) {
	forms = document.getElementsByTagName("frm");

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

function fn_renameFile(){
	for (i = 0; i < $(".files").length; i++) {
		$(".files:eq(" + i + ")").attr("name", "file_" + i);
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
	
	$('.expcorPrice').each(function(){
		 totPrice += parseFloat($(this).val());
	});
	
	$('#totPrice').text(numberWithCommas(totPrice));
}

// 엑셀 불러오기
 function fn_importExcel(){
	 $('#excelUpload').click();
}

// 사용인 변경시 해당 사용인이 참여한 과제 목록 중 최신 과제 선택 처리	
function fn_emplyrChanged(index){
	
	var currEmplNo = $("#expcorHistEmplNo"+index).find("option:selected").val();
	//alert(currEmplNo);
	$.ajax({
		type:"post",
		url:"<c:url value='/com/epay/draft/epayExpCorForTaskPerson.do'/>",
		data:{
			emplNo:currEmplNo
			},
		dataType : 'json',
		type : 'POST',
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		success:function(data){
			var ddl = $("#selectTaskId"+index).data("kendoDropDownList"); 
			ddl.value(data.taskInfoForPerson.taskId);
			
			$("#expcorTaskId"+index).kendoDropDownList();
		}
	});
}

function fn_LoadExpCorHistData(){
	
	$("#my-tbody").empty();
	
	$.ajax({
		type:"post",
		url:"<c:url value='/com/epay/draft/epayExpCorHistData.do'/>",
		data:{
			expcorStatId:'<c:out value="${epayExpCorVO.expcorStatId}"/>'
			},
		dataType : 'json',
		type : 'POST',
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		success:function(data){
			
			$.each(data.epayExpCorHistList, function(index, item){
				   
				var template = '<tr>';
			    template += 	'<input type="hidden" name="list['+idx+'].expcorStatId" value="<c:out value="${expcorStatId}" />">';
			    template +=		'<td>';
			    template +=			'<select name="list['+idx+'].cardNum" id="selectCardNum'+idx+'" class="select'+idx+' width100p">';
			    template +=				'<c:forEach var="card" items="${tbBankcardmanageVOList}" varStatus="status">';	
			    template +=					'<option value="${card.cardNum}" <c:out value="${card.cardNum == '+item.cardNum+' ? \'selected\' : \'\'}"/>>${card.cardNum}</option>';
			    template +=				'</c:forEach>';
			    template +=			'</select>';
			    template +=		'</td>';
			    template +=		'<td><input name="list['+idx+'].accptDt" class="datepicker'+idx+' width120" value="'+item.accptDt+'" placeholder="" title="">';
			    template +=		'</td>';
			    template +=		'<td>';
			    template +=			'<select name="list['+idx+'].emplNo" class="select'+idx+' width100p" id="expcorHistEmplNo'+idx+'" onchange="javascript:fn_emplyrChanged('+idx+');">';
			    template +=				'<c:forEach var="emplyr" items="${emplyrList}" varStatus="status">';	
			    template +=					'<option value="${emplyr.emplNo}" <c:out value="${emplyr.emplNo == epayDraftInfoVO.emplNo ? \'selected\' : \'\'}"/>>${emplyr.userNm}</option>';
			    template +=				'</c:forEach>';
			    template +=			'</select>';
			    template +=		'</td>';
			    template +=		'<td>';
			    template +=			'<select name="list['+idx+'].taskId" id="selectTaskId'+idx+'" class="select'+idx+' width100p">';
			    template +=				'<c:forEach var="task" items="${taskList}" varStatus="status">';
			    template +=					'<option value="${task.taskId}">${task.taskNm}</option>';
			    template +=				'</c:forEach>';
			    template +=			'</select>';
			    template +=		'</td>';
			    template +=		'<td>';
			    template +=			'<select name="list['+idx+'].subjId" id="selectSubjId'+idx+'" class="select'+idx+' width100p">';  
			    template +=				'<c:forEach var="expcorSubj" items="${expcorSubjList}" varStatus="status">';	
			    template +=					'<option value="${expcorSubj.code}" <c:out value="${expcorSubj.code == \'1\' ? \'selected\' : \'\'}"/>>${expcorSubj.codeNm}</option>';
			    template +=				'</c:forEach>';
			    template +=			'</select>';
			    template +=		'</td>';
			    template +=		'<td><input name="list['+idx+'].expPlace" type="text" class="k-textbox width100p" value="'+item.expPlace+'">';
			    template +=		'</td>';
			    template +=		'<td><input name="list['+idx+'].rm" type="text" class="k-textbox width100p" value="'+item.rm+'">';
			    template +=		'</td>';
			    template +=		'<td><input name="list['+idx+'].price" id="expcorPrice'+idx+'" type="text" class="k-textbox width100p expcorPrice" value="'+item.price+'" onChange="javascript:fn_sumExpPrice();">';
			    template +=		'</td>';
			    template +=		'<td><a href="#" onclick="return deleteRow(this);"><i class="fas fa-window-close"></i></a>';
			    template +=		'</td>';
			    template +=	'</tr>';
			    
			    $("#my-tbody").append(template).trigger("create");
			    
			 	$(".datepicker"+idx).last().kendoDatePicker({
			 		format: "yyyy/MM/dd"
			 	});
			 	
			 	$("#selectCardNum"+idx).val(item.cardNum).prop("selected", true);
			 	$("#expcorHistEmplNo"+idx).val(item.emplNo).prop("selected", true);
			 	$("#selectTaskId"+idx).val(item.taskId).prop("selected", true);
			    $("#selectSubjId"+idx).val(item.subjId).prop("selected", true);
			    
			 	// 스타일 재적용
			    $(".select"+idx).kendoDropDownList();
			    $(".select"+idx).css({
			    	'text-align' : 'left'	
			    }); 
				
				idx++;
			});
		    
			fn_sumExpPrice();
			
		} // end success
	});
}

function fn_addExpCorHistRow(){
	
	++idx;
	
	var template = '<tr>';
    template += 	'<input type="hidden" name="list['+idx+'].expcorStatId" value="<c:out value="${expcorStatId}" />">';
    template +=		'<td>';
    template +=			'<select name="list['+idx+'].cardNum" id="selectCardNum'+idx+'" class="select'+idx+' width100p">';
    template +=				'<c:forEach var="card" items="${tbBankcardmanageVOList}" varStatus="status">';	
    template +=					'<option value="${card.cardNum}" <c:out value="${card.cardNum == item.taskId ? \'selected\' : \'\'}"/>>${card.cardNum}</option>';
    template +=				'</c:forEach>';
    template +=			'</select>';
    template +=		'</td>';
    template +=		'<td><input name="list['+idx+'].accptDt" class="datepicker'+idx+' width120" value="" placeholder="" title="">';
    template +=		'</td>';
    template +=		'<td>';
    template +=			'<select name="list['+idx+'].emplNo" class="select'+idx+' width100p" id="expcorHistEmplNo'+idx+'" onchange="javascript:fn_emplyrChanged('+idx+');">';
    template +=				'<c:forEach var="emplyr" items="${emplyrList}" varStatus="status">';	
    template +=					'<option value="${emplyr.emplNo}" <c:out value="${emplyr.emplNo == epayDraftInfoVO.emplNo ? \'selected\' : \'\'}"/>>${emplyr.userNm}</option>';
    template +=				'</c:forEach>';
    template +=			'</select>';
    template +=		'</td>';
    template +=		'<td>';
    template +=			'<select name="list['+idx+'].taskId" id="selectTaskId'+idx+'" class="select'+idx+' width100p">';
    template +=				'<c:forEach var="task" items="${taskList}" varStatus="status">';
    template +=					'<option value="${task.taskId}">${task.taskNm}</option>';
    template +=				'</c:forEach>';
    template +=			'</select>';
    template +=		'</td>';
    template +=		'<td>';
    template +=			'<select name="list['+idx+'].subjId" id="selectSubjId'+idx+'" class="select'+idx+' width100p">';  
    template +=				'<c:forEach var="expcorSubj" items="${expcorSubjList}" varStatus="status">';	
    template +=					'<option value="${expcorSubj.code}" <c:out value="${expcorSubj.code == \'1\' ? \'selected\' : \'\'}"/>>${expcorSubj.codeNm}</option>';
    template +=				'</c:forEach>';
    template +=			'</select>';
    template +=		'</td>';
    template +=		'<td><input name="list['+idx+'].expPlace" type="text" class="k-textbox width100p" value="${expPlace}">';
    template +=		'</td>';
    template +=		'<td><input name="list['+idx+'].rm" type="text" class="k-textbox width100p" value="${rm}">';
    template +=		'</td>';
    template +=		'<td><input name="list['+idx+'].price" id="expcorPrice'+idx+'" type="text" class="k-textbox width100p expcorPrice" value="${price}" onChange="javascript:fn_sumExpPrice();">';
    template +=		'</td>';
    template +=		'<td><a href="#" onclick="return deleteRow(this);"><i class="fas fa-window-close"></i></a>';
    template +=		'</td>';
    template +=	'</tr>';

	$("#my-tbody").append(template).trigger("create");

	$(".datepicker"+idx).last().kendoDatePicker({
		format: "yyyy/MM/dd"
	});
	
	$("#expcorPrice"+idx).val("0");
	
	/* $("#selectCardNum"+idx).val(item.cardNum).prop("selected", true);
	$("#expcorHistEmplNo"+idx).val(item.emplNo).prop("selected", true);
	$("#selectTaskId"+idx).val(item.taskId).prop("selected", true);
	$("#selectSubjId"+idx).val(item.expSubj).prop("selected", true); */

	// 스타일 재적용
	$(".select"+idx).kendoDropDownList();
	$(".select"+idx).css({
		'text-align' : 'left'	
	}); 
}

function fn_ApprLnPopup() {
	var newWindow = window.open("<c:url value='/com/epay/popup/epayApprLnPopup.do'/>","결재라인 설정 화면","scrollbars=yes,menubar=no,left=200,top=200,width=850,height=600, resizable=no,toolbar=no,location=no,status=no");
	newWindow.focus();
}

//결재선지정 콜백 함수
function fn_epayApprLnPopupCallback(retVal) {

	var apprLnNm = "";
	
	var apprLineList = "";
	
	$.each(retVal.apprLnList, function(i, value) {
		
		apprLnNm += '[' + value.position + ']' + value.userNm;
		
		if(i+1 < retVal.apprLnList.length){
			apprLnNm += ' ▶ ';
		}
		
		// 임시 저장용 문자열 생성
		apprLineList += '<input type="hidden" name="apprLnList['+i+'].emplNo" value="'+value.emplNo+'"/>';
		apprLineList += '<input type="hidden" name="apprLnList['+i+'].apprOrdr" value="'+value.apprOrdr+'"/>';
		apprLineList += '<input type="hidden" name="apprLnList['+i+'].apprTy" value="'+value.apprTy+'"/>';
		apprLineList += '<input type="hidden" name="apprLnList['+i+'].position" value="'+value.position+'"/>';
	});
	
	apprLnNm += " ";
	
	// 결재선 정보 화면 표시
	$("#inputApprLineNm").text(apprLnNm);
	// 결재선 정보 임시 저장
	$("#apprLnList").attr('value',retVal);


	 $("#inputApprLineList").empty().append(apprLineList);
}

function fn_loadExistApprLn(){
	
	var draftInfoId = '${epayDraftInfoVO.draftInfoId}';
	
		$.ajax({    
			url:'<c:url value="/com/epay/draft/getDraftApprLnCfgList.do"/>',
			data:{draftInfoId:draftInfoId}, 
			dataType : 'json',
			type : 'POST',
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				
				fn_epayApprLnPopupCallback(data);
			}
        });
}
</script>

<form name="frm" id="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="act" value="write">
	<input type="hidden" name="draftInfoId"  value='<c:out value="${epayDraftInfoVO.draftInfoId}"/>'>
	<input type="hidden" name="expcorStatId"  value='<c:out value="${epayExpCorVO.expcorStatId}"/>'>
	<input type="hidden" name="epayApprLnCfgVO" id="apprLnList" value='<c:out value="${epayApprLnCfgVO}"/>'>
	<input type="hidden" name="returnUrl" value="<c:url value='/com/epay/draft/epayExpCorWrite.do'/>"/>
	<div id="subwrap">
		<div class="tabmenu">
			<ul>
				<li><a href="<c:url value='/com/epay/draft/epayCnsulWrite.do?method=payment'/>">품의서</a></li>
				<%-- <li><a href="<c:url value='/com/epay/draft/epayDraftWrite.do?method=paymen'/>">기안서</a></li> --%>
				<li><a href="<c:url value='/com/epay/draft/epayExpWrite.do?method=payment'/>">지출명세서(개인)</a></li>
				<li class="active"><a href="<c:url value='/com/epay/draft/epayExpCorWrite.do?method=payment'/>">지출명세서(법인)</a></li>
				<li><a href="<c:url value='/com/epay/draft/epayHolidayWrite.do?method=payment'/>">휴가원</a></li>
			</ul>
		</div>
		<div><h4><i></i></h4></div>
		<div class="space10"></div>
		<h4><i class="fa fa-th-large"></i>지출명세서(법인)</h4>
		<table class="tablewrite" id="expHistTable">
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
					<td name="" id=""><c:out value="${epayDraftInfoVO.orgnztNm}"/></td>
				</tr>
				<tr>
					<th>결재라인</th>
					<td colspan="3">
						<div id="inputApprLineList"></div>
						<span id="inputApprLineNm">
						</span>
						<button type="button" class="btn01" onclick="fn_ApprLnPopup();">결재라인 지정</button>
					</td>
				</tr>
				<tr>
					<th>기안자</th>
					<td id="drafterId"><c:out value="${epayDraftInfoVO.userNm}"/></td>
					<th>지출총액</th>
					<td id="totPrice" name="expcorTotPrice"><c:out value="${epayExpCorVO.expcorTotPrice}"/></td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan="3"><input name="title" type="text" class="k-textbox width100p" value='<c:out value="${epayDraftInfoVO.title}"/>'>
					</td>
				</tr>
				<tr>
					<th>지출내역</th>
					<td colspan="3">
						<div class="space10"></div>
						<div>
							<button type="button" class="btn04" onclick="location.href='javascript:fn_importExcel();'">엑셀 불러오기</button>
							<input type="file" multiple="multiple" id="excelUpload" name="excelUpload" class="excelUpload inputFile width400" style="display:none;" />
							<button type="button" class="btn04" onclick="javascript:fn_downloadExcel();">엑셀 양식받기</button>
							<button type="button" class="btn04" onclick="location.href='javascript:fn_addExpCorHistRow();'">지출내역 항목추가</button>
						</div>
						<div class="space10"></div>
						<div>
							※ 카드사에서 다운받은 엑셀 양식을 '엑셀 양식받기'의 양식에 맞게 변형하여 사용하세요.
						</div>
						<div>
							※ 엑셀 불러오기를 할 경우 기존의 지출내역들은 모두 삭제 됩니다. 주의해서 등록하세요.
						</div>
						<div class="space10"></div>
						<div>
						<table class="tablelist">
							<colgroup>
								<col class="width130">
								<col class="width120">
								<col class="width100">
								<col class="widthAuto">
								<col class="width110">
								<col class="width150">
								<col class="width150">
								<col class="width120">
								<col>
							</colgroup>
							<thead>
								<tr>
									<th>카드번호*(카드번호)</th>
									<th>날짜*(승인일)</th>
									<th>사용인</th>
									<th>과제명</th>
									<th>계정과목</th>
									<th>지출처</th>
									<th>비고*(금액)</th>
									<th>금액*(금액)</th>
									<th></th>
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
					<td colspan="3"><textarea name="rm" class="k-textbox width100p height100">${epayExpCorVO.rm}</textarea>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3">
						<input type="file" class="files" name="files" aria-label="찾아보기" value='<c:out value="${epayExpCorVO.atchFileId}"/>'>
						<c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
							<c:param name="param_atchFileId" value="${epayExpCorVO.atchFileId}" />
							<c:param name="param_colspan" value="3" />
						</c:import>
					</td>
					
				</tr>
			</tbody>
		</table>
		<div class="space20"></div>
		<div class="btngroup fl">		
			<button type="button" class="btn04" onclick="location.href='<c:url value='/com/epay/draft/epayDraftInfoList.do'/>'">목록</button>
		</div>
		<div class="btngroup fr">
			<c:if test="${epayDraftInfoVO.draftInfoId ne null && epayExpCorVO.expcorStatId ne null}"><button type="button" class="btn02" onclick="javascript:btn_requestWriteCancel();">기안취소</button></c:if>
			<button type="button" class="btn04" onclick="javascript:btn_tmpSave();">임시저장</button>
			<button type="button" class="btn" onclick="javascript:btn_submit();">결재요청</button>
		</div>
	</div>
</form>
</body>
</html>

<script>
	$(document).ready( function() {
	    // 자동 실행 할 javascript 함수 또는 코드를 넣는다.
	    fn_LoadExpCorHistData();
	    
	    if($("#draftInfoId").val() != ""){
			fn_loadExistApprLn();
		}
	});
</script>
