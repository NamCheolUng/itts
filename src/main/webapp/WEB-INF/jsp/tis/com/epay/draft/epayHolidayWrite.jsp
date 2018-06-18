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
		if("${epayDraftInfoVO.draftInfoId}" != "") { // 신규 문서 여부 판단
			f.act.value = 'update';
		}
		
		$('input[type="checkbox"][name="holTy"]').click(function(){
			if($(this).prop('checked')){
				$('input[type="checkbox"][name="holTy"]').prop('checked', false);
				$(this).prop('checked', true);
			}
		});
		
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
	        
	        fn_getWorkDay();
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
	        
	        fn_getWorkDay();
	    }

	    var start = $("#holSdate").kendoDatePicker({
	        change: startChange,
	        format: "yyyy/MM/dd"
	    }).data("kendoDatePicker");

	    var end = $("#holEdate").kendoDatePicker({
	        change: endChange,
	        format: "yyyy/MM/dd"
	    }).data("kendoDatePicker");

	    start.max(end.value());
	    end.min(start.value());
	});
	
	
	
	function fn_requestDraftList(){
		document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/epay/draft/epayDraftInfoList.do'/>";
		document.frm.submit();
	}
	
	function fn_ApprLnPopup() {
		var newWindow = window.open("<c:url value='/com/epay/popup/epayApprLnPopup.do'/>","결재라인 설정 화면","scrollbars=yes,menubar=no,left=200,top=200,width=850,height=600, resizable=no,toolbar=no,location=no,status=no");
		newWindow.focus();
	}
	
	function btn_requestWriteCancel(){
		document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/epay/draft/epayHolidayWriteCancel.do'/>";
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
		
		/* if(f.cnsulCn.value=="") {
			alert("내용을 입력하세요.");
			f.cnsulCn.focus();
			return false;
		} */
		
		if(confirm('임시저장 하시겠습니까?')){
			
			if(f.act.value=='write'){ // 신규 -> 임시저장
				f.action = "<c:url value='/com/epay/draft/epayHolidayWriteTemp.do'/>";                        
			}else { // 임시저장 -> 임시저장
				f.action = "<c:url value='/com/epay/draft/epayHolidayWriteTempUpdate.do'/>";
			}
			f.submit();
		}
	}
	// 결재요청 버튼 클릭 이벤트 함수
	function btn_submit(){
	
		var f = document.frm;
		
		if(f.title.value=="") {
			alert("제목을 입력하세요.");
			f.title.focus();
			return false;
		}
		
		if(f.holRs.value=="") {
			alert("사유 내용을 입력하세요.");
			f.holRs.focus();
			return false;
		}
		
		if(f.holSdate.value=="") {
			alert("휴가기간 시작일을 선택하세요.");
			f.holSdate.focus();
			return false;
		}
		
		if(f.holEdate.value=="") {
			alert("휴가 기간 종료일을 선택하세요.");
			f.holEdate.focus();
			return false;
		}
		
		 if(!$("input:checkbox[name='holTy']").is(":checked")){
			 alert("휴가 구분을 선택하세요.");
				return false;
		 }
		
		// 결재라인 선택 여부 검사
		var isApprLine = $("#inputApprLineList").children().length;
		
		if(isApprLine == 0){
			alert("선택된 결재라인이 없습니다.\n결재라인을 선택해주세요.");
			return false;
		}
		
		
		if(confirm('결재요청 하시겠습니까?')){
			
			if(f.act.value=='write'){
				f.action = "<c:url value='/com/epay/draft/epayHolidayWriteInsert.do'/>";
			}else {
				f.action = "<c:url value='/com/epay/draft/epayHolidayWriteTempInsert.do'/>";
			}
			f.submit();
		}
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
		$("#inputApprLineNm").empty().text(apprLnNm);
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
	
	function fn_getWorkDay(){
		
		var emplNo, emplyrId;
		
		$.ajax({    
    		url:'<c:url value="/com/epay/draft/getWorkDayForepayHoliday.do"/>',
    		data:$("form[name=frm]").serializeArray(),
    		dataType : 'json',
    		type : 'POST',
    		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
    		success:function(data){
    			
    			if(data.message == "success"){
    				fn_getWorkDayCallback(data);
    			}
    		}
		});
	}
	
	function fn_getWorkDayCallback(data){
		$("#usingHoliday").html(data.workDay);
		$("#holDay").val(data.workDay);
	}
	
	
</script>
<form name="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="act" value="write">
	<input type="hidden" name="draftInfoId" id="draftInfoId" value='<c:out value="${epayDraftInfoVO.draftInfoId}"/>'>
	<input type="hidden" id="holDay" name="holDay" value='<c:out value="${epayHolidayVO.holDay}"/>'>
	<input type="hidden" name="apprSttus" id="apprSttus" value='<c:out value="${epayDraftInfoVO.apprSttus}"/>'>
	<input type="hidden" name="epayApprLnCfgVO" id="apprLnList" value='<c:out value="${epayApprLnCfgVO}"/>'>
	<input type="hidden" name="returnUrl" value="<c:url value='/com/epay/draft/epayHolidayWrite.do'/>"/>
	
<div id="subwrap">
	<div class="tabmenu">
		<ul>
			<li><a href="<c:url value='/com/epay/draft/epayCnsulWrite.do?method=payment'/>">품의서</a></li>
			<%-- <li><a href="<c:url value='/com/epay/draft/epayDraftWrite.do?method=payment'/>">기안서</a></li> --%>
			<li><a href="<c:url value='/com/epay/draft/epayExpWrite.do?method=payment'/>">지출명세서(개인)</a></li>
			<li><a href="<c:url value='/com/epay/draft/epayExpCorWrite.do?method=payment'/>">지출명세서(법인)</a></li>
			<li class="active"><a href="<c:url value='/com/epay/draft/epayHolidayWrite.do?method=payment'/>">휴가원</a></li>
		</ul>
	</div>
	<div><h4><i></i></h4></div>
	<div class="space10"></div>
	<h4><i class="fa fa-th-large"></i>휴가원</h4>
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
				<td><input name="regDt" class="datepicker width200" value='<c:out value="${epayDraftInfoVO.regDt}"/>' placeholder="기안일자" title="기안일자" readonly></td>
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
				<td name="userNm" id="emplNo"><c:out value="${epayDraftInfoVO.userNm}"/></td>
				<th>(*)비상연락처</th>
				<td id="emergNum">
					<input name="emergNum" type="text" class="k-textbox width100p" value='<c:out value="${epayHolidayVO.emergNum}"/>'>
				</td>
			</tr>
			<tr>
				<th>(*)기간</th>
				<td colspan="3">
					<input class="datepicker width200" name="holSdate" id="holSdate" value='<c:out value="${epayHolidayVO.holSdate}"/>' placeholder="시작일" title="시작일">&nbsp;&nbsp;~&nbsp;
					<input class="datepicker width200" name="holEdate" id="holEdate" value='<c:out value="${epayHolidayVO.holEdate}"/>' placeholder="종료일" title="종료일">&nbsp;&nbsp;
					<strong id="usingHoliday" name="holDay"><c:out value="${epayHolidayVO.holDay}"/></strong>일 (사용연차 : <strong><c:out value="${vcatnManageVO.useYrycCo}"/></strong>일 / 잔여연차 : <strong><c:out value="${vcatnManageVO.remndrYrycCo}"/></strong>일)
				</td>
			</tr>
			<tr>
				<th>(*)제목</th>
				<td colspan="3">
					<input name="title" type="text" class="k-textbox width100p" value='<c:out value="${epayDraftInfoVO.title}"/>'>
				</td>
			</tr>
			<tr>
				<th>구분</th>
				<td colspan="3">
					<table class="tablelist">
						<colgroup>
							<col class="width150">
							<col class="width150">
							<col class="width150">
							<col class="width150">
							<col class="width150">
						</colgroup>
						<thead>
							<tr>
								<th><label for="checkbox01">연차</label></th>
								<th><label for="checkbox02">반차</label></th>
								<th><label for="checkbox03">경조</label></th>
								<th><label for="checkbox04">공가</label></th>
								<th><label for="checkbox05">병결</label></th>
							</tr>
						</thead>
						<tbody>
							<tr class="labeln">
								<td><input type="checkbox" name="holTy" id="checkbox01" class="k-checkbox" value="0" <c:if test="${fn:contains(epayHolidayVO.holTy, '0')}">checked</c:if>><label class="k-checkbox-label" for="checkbox01"></label></td>
								<td><input type="checkbox" name="holTy" id="checkbox02" class="k-checkbox" value="1" <c:if test="${fn:contains(epayHolidayVO.holTy, '1')}">checked</c:if>><label class="k-checkbox-label" for="checkbox02"></label></td>
								<td><input type="checkbox" name="holTy" id="checkbox03" class="k-checkbox" value="2" <c:if test="${fn:contains(epayHolidayVO.holTy, '2')}">checked</c:if>><label class="k-checkbox-label" for="checkbox03"></label></td>
								<td><input type="checkbox" name="holTy" id="checkbox04" class="k-checkbox" value="3" <c:if test="${fn:contains(epayHolidayVO.holTy, '3')}">checked</c:if>><label class="k-checkbox-label" for="checkbox04"></label></td>
								<td><input type="checkbox" name="holTy" id="checkbox05" class="k-checkbox" value="4" <c:if test="${fn:contains(epayHolidayVO.holTy, '4')}">checked</c:if>><label class="k-checkbox-label" for="checkbox05"></label></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<th>(*)사유</th>
				<td colspan="3">
					<textarea name="holRs" class="k-textbox width100p height100"><c:out value="${epayHolidayVO.holRs}"/></textarea>
				</td>
			</tr>
			<tr>
				<th>비고</th>
				<td colspan="3">
					<textarea name="holRm" class="k-textbox width100p height100"><c:out value="${epayHolidayVO.holRm}"/></textarea>
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
					<input type="file" class="files" name="files" aria-label="찾아보기" value='<c:out value="${epayHolidayVO.atchFileId}"/>'>
					<c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
						<c:param name="param_atchFileId" value="${epayHolidayVO.atchFileId}" />
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
		<c:if test="${epayDraftInfoVO.draftInfoId ne null}"><button type="button" class="btn02" onclick="javascript:btn_requestWriteCancel();">기안취소</button></c:if>
		<button type="button" class="btn04" onclick="javascript:btn_tmpSave();">임시저장</button>
		<button type="button" class="btn" onclick="javascript:btn_submit();">결재요청</button>
	</div>
</div>
</form>
</body>
</html>

<script>

$(document).ready(function(){ 
	
	if($("#draftInfoId").val() != ""){
		fn_loadExistApprLn();
	}
});

</script>