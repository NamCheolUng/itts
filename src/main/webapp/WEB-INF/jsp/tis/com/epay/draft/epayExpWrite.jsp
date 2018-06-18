
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
	
	
	function startChange() {
        var startDate = start.value(),
        endDate = end.value();

        if (startDate) {
            startDate = new Date(startDate);
            startDate.setDate(startDate.getDate());
            end.min(startDate);
        } else if (endDate) {
            start.max(new Date(endDate));
        } else {
            endDate = new Date();
            start.max(endDate);
            end.min(endDate);
        }
    }

    function endChange() {
        var endDate = end.value(),
        startDate = start.value();

        if (endDate) {
            endDate = new Date(endDate);
            endDate.setDate(endDate.getDate());
            start.max(endDate);
        } else if (startDate) {
            end.min(new Date(startDate));
        } else {
            endDate = new Date();
            start.max(endDate);
            end.min(endDate);
        }
    }

    var start = $("#searchSdate").kendoDatePicker({
        change: startChange,
        format: "yyyy/MM/dd"
    }).data("kendoDatePicker");

    var end = $("#searchEdate").kendoDatePicker({
        change: endChange,
        format: "yyyy/MM/dd"
    }).data("kendoDatePicker");

    start.max(end.value());
    end.min(start.value());
});

function fn_Validation(){
	re = "/^(\d{4})\/(\d{0,2})\/(\d{0,2})$/";
	
	if(document.frm.searchSdate.value != '' && !validateDate(document.frm.searchSdate.value)){
	      alert("검색 시작일이 날짜 형식에 맞지 않습니다.");
	      document.frm.searchSdate.focus();
	      return false;
	    }
	
	if(document.frm.searchEdate.value != '' && !validateDate(document.frm.searchEdate.value)){
		alert("검색 종료일이 날짜 형식에 맞지 않습니다.");
	      document.frm.searchEdate.focus();
	      return false;
	    }
	
	return true;
}

function validateDate(strDate) {
	  var t = /^(?=.+([\/.-])..\1)(?=.{10}$)(?:(\d{4}).|)(\d\d).(\d\d)(?:.(\d{4})|)$/;
	  strDate.replace(t, function($, _, y, m, d, y2) {
	    $ = new Date(y = y || y2, m, d);
	    t = $.getFullYear() != y || $.getMonth() != m || $.getDate() != d;
	  });
	  return !t;
}

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
	
	// 결재라인 선택 여부 검사
	var isApprLine = $("#inputApprLineList").children().length;
	
	if(isApprLine == 0){
		alert("선택된 결재라인이 없습니다.\n결재라인을 선택해주세요.");
		return false;
	}
	
	if(confirm('결재요청 하시겠습니까?')){
		
		if(f.act.value=='write'){// 신규 -> 결재요청시
			f.action = "<c:url value='/com/epay/draft/epayExpWriteInsert.do'/>";
		}else {// 임시저장 -> 결재요청
			f.action = "<c:url value='/com/epay/draft/epayExpWriteTempInsert.do'/>";
		}
		f.submit();
	}
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
	
	$('#expTotPrice').val(numberWithCommas(totPrice));
}

// 모바일내역 불러오기
 function fn_LoadMobileHist(loadType){
	
	 if(!fn_Validation()){
		 alert('test');
			return;
		}
	
	$("#my-tbody").empty();
	$("#expTotPrice").empty();
	
 	var sdate = $('[name="expSdate"]').val();
	var edate = $('[name="expEdate"]').val();
	
	$.ajax({
		type:"post",
		url:"<c:url value='/com/epay/draft/epayExpWriteLoadData.do'/>",
		data:{
			emplNo:	'<c:out value="${epayDraftInfoVO.emplNo}" />'
			, expSdate:sdate
			, expEdate:edate
			},
		dataType : 'json',
		type : 'POST',
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		success:function(data){
			
			if(data.epayExpHistList.length == 0 && loadType == 1){
				alert('불러올 자료가 없습니다.'); return;
			}
				
			$.each(data.epayExpHistList, function(index, item){
				
			    var template2 =	'<tr>';
			    	/* template2 += 	'<input type="hidden" name="list['+idx+'].expHistId" value="<c:out value="' + item.expHistId +'" />">'; */
			    	template2 += 	'<input type="hidden" name="list['+idx+'].expHistId" value="'+ item.expHistId +'">';
			    	template2 +=	'<td><input name="list['+idx+'].expDate" class="datepicker'+idx+' width120\" value="' + item.expDate + '\">';
			    	template2 +=	'</td>';
			    	template2 +=	'<td>';
			    	
			    	template2 +=		'<select name="list['+idx+'].taskId" id="selectTaskId'+idx+'" class="select'+idx+' width400">';
			    	template2 += 			'<c:forEach var="task" items="${taskList}" varStatus="status">';	
			    	template2 +=				'<option value="${task.taskId}" <c:out value="${task.taskId == '+item.taskId+' ? \'selected\' : ''}"/>>${task.taskNm}</option>';
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
			    	template2 +=		'<input type="text" name="list['+idx+'].price" id="expPrice"'+idx+'" class="k-textbox width100p expPrice" value="'+item.price+'" onChange="javascript:fn_sumExpPrice();">';
			    	template2 +=	'</td>';
			    	template2 +=	'<td>';
			    	template2 +=		fn_setFileColor(item.atchFileId);
			    	template2 += 		'<a href="#" onclick="return deleteRow(this);"><i class="fas fa-window-close"></i></a>';
			    	template2 +=	'</td>';
			    	template2 +='</tr>';
			    
			    $("#my-tbody").append(template2).trigger("create");
			    
			 	$(".datepicker"+idx).last().kendoDatePicker({
			 		format: "yyyy/MM/dd"
			 	});
			    
			    // select 컨트롤 과제명 값 설정
				$("#selectTaskId"+idx).val(item.taskId).prop("selected", true);    
			    // select 컨트롤 계정과목 값 설정
			    $("#selectExpSubj"+idx).val(item.expSubj).prop("selected", true);
			    
			    // 스타일 재적용
			    $(".select"+idx).kendoDropDownList();
			    $(".select"+idx).css({
			    	'text-align' : 'left'	
			    });
			    
				fn_sumExpPrice();
				
				idx++;
			});
			
		} // end success fucntion				
	});	 // end jquery
}


function fn_setFileColor(fileId){
	if(fileId == "" || fileId == null){
		return '<i class=\"fas fa-cloud-download-alt\"></i>';
	}
	else{
		
		var test = '<a href="javascript:fn_egov_downFile(\''+fileId+'\',0);"><i class=\"fas fa-cloud-download-alt\"></i></a>';
		return test;
	}
}

function fn_ApprLnPopup() {
	var newWindow = window.open("<c:url value='/com/epay/popup/epayApprLnPopup.do'/>","결재라인 설정 화면","scrollbars=yes,menubar=no,left=200,top=200,width=850,height=600, resizable=no,toolbar=no,location=no,status=no");
	newWindow.focus();
}

// 결재선지정 콜백 함수
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
	<input type="hidden" name="atchFileId" value="<c:out value='${atchFileId}' />">
	<input type="hidden" name="draftInfoId"  value='<c:out value="${epayDraftInfoVO.draftInfoId}"/>'>
	<input type="hidden" name="expStatId"  value='<c:out value="${epayExpVO.expStatId}"/>'>
	<input type="hidden" name="epayApprLnCfgVO" id="apprLnList" value='<c:out value="${epayApprLnCfgVO}"/>'>
	<div id="subwrap">         
		<div class="tabmenu">
			<ul>
				<li><a href="<c:url value='/com/epay/draft/epayCnsulWrite.do?method=payment'/>">품의서</a></li>
				<%-- <li><a href="<c:url value='/com/epay/draft/epayDraftWrite.do?method=paymen'/>">기안서</a></li> --%>
				<li class="active"><a href="<c:url value='/com/epay/draft/epayExpWrite.do?method=payment'/>">지출명세서(개인)</a></li>
				<li><a href="<c:url value='/com/epay/draft/epayExpCorWrite.do?method=payment'/>">지출명세서(법인)</a></li>
				<li><a href="<c:url value='/com/epay/draft/epayHolidayWrite.do?method=payment'/>">휴가원</a></li>
			</ul>
		</div>
		<div><h4><i></i></h4></div>
		<div class="space10"></div>
		<h4><i class="fa fa-th-large"></i>지출명세서(개인)</h4>
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
					<td name="" id=""><c:out value="${epayDraftInfoVO.orgnztNm}"/></td>
					<%-- <th>결재라인</th>
					<td>
						<c:out value="${epayDraftInfoVO.orgnztNm}"/>
						<select name="code" class="select width200">
			    			<c:forEach var="apprLine" items="${apprLineList}" varStatus="status">
			    				<option value="${apprLine.code}" <c:if test="${epayDraftInfoVO.code == apprLine.code}">selected="selected"</c:if>>${apprLine.apprLineNm}</option>
			    			</c:forEach>
			    		</select>
					</td> --%>
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
					<td name="userNm" id="emplNo"><c:out value="${epayDraftInfoVO.userNm}"/></td>
					<th>지출총액</th>
					<!-- <td id="expTotPrice" name="expTotPrice"> -->
					<td>
						<input type="text" name="expTotPrice" id="expTotPrice" class="k-textbox width100p" value='<c:out value="${epayExpVO.expTotPrice}"/>' readonly>
						<%-- <c:out value="${epayExpVO.expTotPrice}"/> --%>
					</td>
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
						<div>지출기간 : 
							<input class="datepicker width200" id="searchSdate" name="expSdate"  value='<c:out value="${epayExpVO.expSdate}"/>' placeholder="시작일" title="시작일">&nbsp;&nbsp;~&nbsp;
							<input class="datepicker width200" id="searchEdate" name="expEdate"  value='<c:out value="${epayExpVO.expEdate}"/>' placeholder="종료일" title="종료일">
							&nbsp;&nbsp;&nbsp;<button type="button" class="btn04" onclick="location.href='javascript:fn_LoadMobileHist(1);'">모바일내역 불러오기</button>
							<div class="space10"></div>
							<div>※ 모바일에서 지출명세서 영수증을 등록하세요. 지정한 지출기간에 따라 지출내역이 자동으로 등록됩니다.   %모바일 주소 혹은 앱 마켓 경로 명시%</div>
							<div class="space10"></div>
							 
						</div>
						<div>
						<table class="tablelist" id="expHistTable">
							<colgroup>
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
									<th>날짜</th>
									<th>과제명</th>
									<th>계정과목</th>
									<th>지출처</th>
									<th>비고</th>
									<th>금액</th>
									<th>관리</th>
								</tr>
							</thead>
							<tbody id='my-tbody'></tbody>
						</table>
						</div>
					</td>
				</tr>
				<tr>
					<th>비고</th>
					<td colspan="3"><textarea name="rm" class="k-textbox width100p height100" value='<c:out value="${epayExpVO.rm}"/>'></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="space20"></div>
		<div class="btngroup fl">		
			<button type="button" class="btn04" onclick="location.href='<c:url value='/com/epay/draft/epayDraftInfoList.do'/>'">목록</button>
		</div>
		<div class="btngroup fr">
			<c:if test="${epayDraftInfoVO.draftInfoId ne null && epayExpVO.expStatId ne null}"><button type="button" class="btn02" onclick="javascript:btn_requestWriteCancel();">기안취소</button></c:if>
			<!-- <button type="button" class="btn02" onclick="javascript:btn_requestWriteCancel();">기안취소</button> -->
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
	    fn_LoadMobileHist(0);
	    
	    if($("#draftInfoId").val() != ""){
			fn_loadExistApprLn();
		}
	});
		
	function fn_egov_downFile(atchFileId, fileSn){
		window.open("<c:url value='/cmm/fms/getImage.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>","new","width=400, height=300, left=100, top=100,scrollbars=no,titlebar=no,status=no,resizable=no,fullscreen=no");
	}
</script>
