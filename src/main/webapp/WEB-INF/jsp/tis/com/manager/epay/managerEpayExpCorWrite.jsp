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
	
});

function fn_accountRegist(){
	if(confirm("<spring:message code='msg.acunt.confirm'/>")){
		
	}
	document.frm.action="<c:url value='/com/manager/epay/managerEpayExpCorAccountRegist.do'/>";
	document.frm.submit();
}


function fn_registerFinancial() {
	var draftInfoId = '${draftInfoId}';
	var affiliationId = document.frm.affiliationId.value;
	
	$.ajax({
		url : '<c:url value='/com/manager/epay/managerEpayExpCorAccountRegist.do'/>',
		data:$("form[name=frm]").serializeArray(),
		dataType : 'json',
		type : 'POST',
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		success : function(data) {
			if(data.result == 'success'){
				alert("회계등록이 되었습니다.");
				window.location.reload(true);
	        }else{
	        	alert("회계등록이 실패하였습니다.");
	        }
			return;
		},
		error : function(request, status, error ) { 
			//console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			alert("회계등록 중 에러가 발생하였습니다.");
		},
	});
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
	
	if(confirm('결재요청 하시겠습니까?')){
		
		if(f.act.value=='write'){// 신규 -> 결재요청시
			f.action = "<c:url value='/com/epay/draft/epayExpCorWriteInsert.do'/>";
		}else {// 임시저장 -> 결재요청
			f.action = "<c:url value='/com/epay/draft/epayExpCorWriteTempInsert.do'/>";
		}
		f.submit();
	}
}


// 지출내역 자동 합계 계산
function fn_sumExpPrice(){
	var totPrice = 0;
	
	$('.expcorPrice').each(function(){
		 totPrice += parseFloat($(this).val());
	});
	
	$('#totPrice').text(numberWithCommas(totPrice));
}
	
</script>

<form name="frm" id="frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="act" value="write">
	<input type="hidden" name="draftInfoId"  value='<c:out value="${epayDraftInfoVO.draftInfoId}"/>'>
	<input type="hidden" name="emplNo"  value='<c:out value="${epayDraftInfoVO.emplNo}"/>'>
	<input type="hidden" name="orgnztId"  value='<c:out value="${epayDraftInfoVO.orgnztId}"/>'>
	<input type="hidden" name="expcorStatId"  value='<c:out value="${epayExpCorVO.expcorStatId}"/>'>
	<input type="hidden" name="apprSttus"  value='<c:out value="${epayDraftInfoVO.apprSttus}"/>'>
	<div id="subwrap">
		<div class="tabmenu">
			<div class="btngroup fl">		
			<button type="button" class="btn04" onclick="location.href='<c:url value ='/com/manager/epay/managerEpayDraftInfoList.do?method=payment'/>'">목록</button>
		</div>
		<div class="btngroup fr">
			<c:if test="${epayExpCorVO.acuntRegistYn ne 'Y' }"><button type="button" class="btn02" data-toggle="modal" data-target="#modal_affiliation">회계등록</button></c:if>
		</div>
		
		<div class="space10"></div>
		<h4><i class="fas fa-dot-circle"></i>지출명세서(법인)</h4>
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
					<td><c:out value="${epayDraftInfoVO.regDt}"/></td>
					<th>기안부서</th>
					<td><c:out value="${epayDraftInfoVO.orgnztNm}"/></td>
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
							<table class="tablelist">
								<colgroup>
									<col class="width150">
									<col class="width100">
									<col>
									<col>
									<col>
									<col>
									<col>
									<col>
									<col class="width50">
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
										<th>비고*(비고)</th>
										<th>금액*(금액)</th>
										<th>구분</th>
										<th></th>
						
									</tr>
								</thead>
								<tbody id='my-tbody'>
									<%-- <c:forEach var="result" items="${epayExpCorHistList}" varStatus="status">
										<tr>
			    							<input type="hidden" name="list[${status.index}].expcorStatId" value="<c:out value="${expcorStatId}" />">
			    							<td>
			    								<select name="list[${status.index}].cardNum" id="selectCardNum${status.index}" class="select${status.index} width100p">
			    									<c:forEach var="card" items="${tbBankcardmanageVOList}" varStatus="status">	
			    										<option value="${card.cardNum}" <c:out value="${card.cardNum == 'result.cardNum' ? 'selected' : ''}"/>>${card.cardNum}</option>
			    									</c:forEach>
			    								</select>
			    							</td>
			    							<td><input name="list[${status.index}].accptDt" class="datepicker${status.index}width120" value="result.accptDt" placeholder="" title="">
			    							</td>
			    							<td>
			    								<select name="list[${status.index}].emplNo" class="select${status.index} width100p" id="expcorHistEmplNo${status.index}" onchange="javascript:fn_emplyrChanged(${status.index});">
			    									<c:forEach var="emplyr" items="${emplyrList}" varStatus="status">
			    										<option value="${emplyr.emplNo}" <c:out value="${emplyr.emplNo == epayDraftInfoVO.emplNo ? 'selected' : ''}"/>>${emplyr.userNm}</option>
			    									</c:forEach>
			    								</select>
			    							</td>
			    							<td>
			    								<select name="list[${status.index}].taskId" id="selectTaskId${status.index}" class="select${status.index} width100p">
			    									<c:forEach var="task" items="${taskList}" varStatus="status">
			    										<option value="${task.taskId}">${task.taskNm}</option>
			    									</c:forEach>
			    								</select>
			    							</td>
			    							<td>
			    								<select name="list[${status.index}].subjId" id="selectSubjId${status.index}" class="select${status.index} width100p"> 
			    									<c:forEach var="expcorSubj" items="${expcorSubjList}" varStatus="status">';	
			    										<option value="${expcorSubj.code}" <c:out value="${expcorSubj.code == '1' ? 'selected' : ''}"/>>${expcorSubj.codeNm}</option>
			    									</c:forEach>
			    								</select>
			    							</td>
			    							<td><input name="list[${status.index}].expPlace" type="text" class="k-textbox width100p" value="result.expPlace">
			    							</td>
			    							<td><input name="list[${status.index}].rm" type="text" class="k-textbox width100p" value="result.rm">
			    							</td>
			    							<td><input name="list[${status.index}].price" id="expcorPrice" type="text" class="k-textbox width100p expcorPrice" value="result.price" onChange="javascript:fn_sumExpPrice();">
			    							</td>
			    							<td>
			    								<select name="list[${status.index}].acuntRegistType" class="select${status.index} width100p">
			    									<option selected>경비</option>
			    									<option>매입</option>
			    								</select>
			    							</td>
			    							<td><a href="#" onclick="return deleteRow(this);"><i class="fas fa-window-close"></i></a>
			    							</td>
			    						</tr>
									</c:forEach> --%>
				   
				
									<%-- <c:forEach var="result" items="${epayExpCorHistList}" varStatus="status">
										<tr>
											<td><c:out value="${result.cardNum}"/></td>
											<td><c:out value="${result.accptDt}"/></td>
											<td><c:out value="${result.userNm}"/></td>
											<td><c:out value="${result.taskNm}"/></td>
											<td><c:out value="${result.expSubjNm}"/></td>
											<td><c:out value="${result.expPlace}"/></td>
											<td><c:out value="${result.rm}"/></td>
											<td class="price"><fmt:formatNumber value="${result.price}" pattern="#,###"/></td>
											<td>
												<select name="payPoint" class="select width100p">
													<option selected>경비</option>
													<option>매입</option>
												</select>
											</td>
										</tr>
									</c:forEach> --%>
								</tbody>
							</table>
					</td>
				</tr>
				<tr>
					<th>비고</th>
					<td colspan="3">
						<textarea class="k-textbox width100p height100p" readonly="readonly"><c:out value="${payExpCorVO.rm}"/></textarea>
						<%-- <c:out value="${epayExpCorVO.rm}"/> --%>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3">
						<c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
							<c:param name="param_atchFileId" value="${epayExpCorVO.atchFileId}" />
							<c:param name="param_colspan" value="3" />
						</c:import>
					</td>
					
					<%-- <td colspan="3">
						<input type="file" class="files" name="files" aria-label="찾아보기" value='<c:out value="${epayExpCorVO.atchFileId}"/>'>
						<c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
							<c:param name="param_atchFileId" value="${epayExpCorVO.atchFileId}" />
							<c:param name="param_colspan" value="3" />
						</c:import>
					</td> --%>
					
				</tr>
			</tbody>
		</table>
		<div class="space20"></div>
		<div class="btngroup fl">		
			<button type="button" class="btn04" onclick="location.href='<c:url value='/com/manager/epay/managerEpayDraftInfoList.do'/>'">목록</button>
		</div>
		<div class="btngroup fr">
		</div>
		<!-- 소속 선택 -->
		<div class="modal fade" id="modal_affiliation" tabindex="-1" role="dialog" aria-labelledby="modal02Label" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="modal02Label">회계등록</h4>
					</div>
					<div class="modal-body">
						<table class="tablewrite">
							<colgroup>
								<col>
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th>등록할 소속</th>
									<td>
										<select name="affiliationId" class="select width150">
											<option value="I">이튜</option>
											<option value="S">에스메이커</option>
										</select>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn02" data-dismiss="modal">취소</button>
						<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_registerFinancial();">저장</button>
					</div>
				</div>
			</div>
		</div>

	</div>
</form>
</body>
</html>

<script>

function fn_egov_downFile(atchFileId, fileSn){
	window.open("<c:url value='/cmm/fms/FileDown.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>");
}

//체크한 첨부파일 삭제
function deleteRow(obj) {
	$(obj).parent().parent().remove();
	fn_sumExpPrice();
}

//사용인 변경시 해당 사용인이 참여한 과제 목록 중 최신 과제 선택 처리	
function fn_emplyrChanged(index){
	
	var currEmplNo = $("#expcorHistEmplNo"+index).find("option:selected").val();
	
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
			
			$.each(data.epayExpCorHistList, function(idx, item){
				   
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
			    template +=		'<td><input name="list['+idx+'].price" id="expcorPrice" type="text" class="k-textbox width100p expcorPrice" value="'+item.price+'" onChange="javascript:fn_sumExpPrice();">';
			    template +=		'</td>';
			    
			    template +=     '<td>';
			    template += 		'<select name="list['+idx+'].acuntRegistType" id="selectAcuntRegistType'+idx+'" class="select'+idx+' width100p">';
		 	    template +=				'<option value="1" <c:out value="${'+item.acuntRegistType+' == \'1\' ? \'selected\' : \'\'}"/>>경비</option>';
			    template +=				'<option value="2" <c:out value="${'+item.acuntRegistType+' == \'2\' ? \'selected\' : \'\'}"/>>매입</option>';
		 	    template +=			'</select>';
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
			    $("#selectAcuntRegistType"+idx).val(item.acuntRegistType).prop("selected", true);
			    
			 	// 스타일 재적용
			    $(".select"+idx).kendoDropDownList();
			    $(".select"+idx).css({
			    	'text-align' : 'left'	
			    });  
				
				//idx++;
			});
		    
			fn_sumExpPrice();
			
		} // end success
	});
}

function numberWithCommas(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//콤마찍기
function comma(str) {
	str = String(str);
	return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

//콤마풀기
function uncomma(str) {
	str = String(str);
	return str.replace(/[^\d]+/g, '');
}

// 매입 및 매출 합계 계산
function fn_sumTotPrice(){
	var totPrice = 0;
	
	$('.price').each(function(){
		totPrice += parseFloat(uncomma($(this)[0].innerText));
	});
	
	$('#totPrice').text(numberWithCommas(totPrice));
}

$(document).ready(function(){ 
	//모든 웹페이지의 항목들이 로딩이 완료되었을때 처리해줄 내용
	fn_LoadExpCorHistData();
	
	//fn_sumTotPrice(); // 매입/매출 합계액 계산
});
</script>