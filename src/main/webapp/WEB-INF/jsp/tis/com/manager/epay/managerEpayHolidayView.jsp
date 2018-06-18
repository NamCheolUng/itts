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

	$(document).ready(function() {
		/* fn_initListHeader(this, document.frm, fn_requestDraftList, '1'); */
	});
	
	function fn_requestDraftList(){
		document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/manager/epay/managerEpayDraftInfoList.do'/>";
		document.frm.submit();
	}
	
	function fn_register() {
		var draftInfoId = '${draftInfoId}';
		
		$.ajax({
			url : '<c:url value='/com/manager/epay/managerEpayHolidayRegist.do'/>',
			data:$("form[name=frm]").serializeArray(),
			dataType : 'json',
			type : 'POST',
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success : function(data) {
				if(data.result == 'success'){
					alert("연차등록이 되었습니다.");
					window.location.reload(true);
		        }else{
		        	alert("연차등록이 실패하였습니다.");
		        }
				return;
			},
			error : function(request, status, error ) { 
				alert("연차등록 중 에러가 발생하였습니다.");
			},
		});
	}
	
</script>

<form name="frm" method="post">
<input type="hidden" name="method" value="payment" />
<input type="hidden" name="draftInfoId"  value='<c:out value="${epayDraftInfoVO.draftInfoId}"/>'>
	<input type="hidden" name="emplNo"  value='<c:out value="${epayDraftInfoVO.emplNo}"/>'>
	<input type="hidden" name="apprSttus"  value='<c:out value="${epayDraftInfoVO.apprSttus}"/>'>
<div id="subwrap">
	<div class="btngroup fl">		
		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/manager/epay/managerEpayDraftInfoList.do'/>'">목록</button>
	</div>
	<c:if test="${holiday.apprSttus == '1' || holiday.apprSttus == '4'}">
		<div class="btngroup fr">
			<button type="button" class="btn01" data-toggle="modal" data-target="#modal_holiday">연차등록</button>
		</div>
	</c:if>
	<div class="clear"></div>
	<div class="space10"></div>
	<h4><i class="fas fa-dot-circle"></i>휴가원</h4>
	<table class="tableview">
		<colgroup>
			<col class="width180">
			<col class="width600">
			<col class="width180">
			<col class="width600">
		</colgroup>
		<tbody>
			<tr>
				<th>기안일자</th>
				<td><c:out value="${holiday.regDt}"/></td>
				<th>기안부서</th>
				<td><c:out value="${holiday.orgnztNm}"/></td>
			</tr>
			<tr>
				<th>기안자</th>
				<td><c:out value="${holiday.userNm}"/></td>
				<th>(*)비상연락처</th>
				<td><c:out value="${holiday.emergNum}"/></td>
			</tr>
			<tr>
				<th>(*)기간</th>
				<td colspan="3">
					<input class="datepicker width200" value='<c:out value="${holiday.holSdate}"/>' readonly>&nbsp;&nbsp;~&nbsp;
					<input class="datepicker width200" value='<c:out value="${holiday.holEdate}"/>' readonly>&nbsp;&nbsp;
					<strong id="usingHoliday" name="holDay"><c:out value="${holiday.holDay}"/></strong> 일간
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3"><c:out value="${holiday.title}"/>
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
								<td><input type="checkbox" name="holTy" id="checkbox01" class="k-checkbox" value="0" disabled <c:if test="${fn:contains(holiday.holTy, '0')}">checked</c:if>><label class="k-checkbox-label" for="checkbox01"></label></td>
								<td><input type="checkbox" name="holTy" id="checkbox02" class="k-checkbox" value="1" disabled <c:if test="${fn:contains(holiday.holTy, '1')}">checked</c:if>><label class="k-checkbox-label" for="checkbox02"></label></td>
								<td><input type="checkbox" name="holTy" id="checkbox03" class="k-checkbox" value="2" disabled <c:if test="${fn:contains(holiday.holTy, '2')}">checked</c:if>><label class="k-checkbox-label" for="checkbox03"></label></td>
								<td><input type="checkbox" name="holTy" id="checkbox04" class="k-checkbox" value="3" disabled <c:if test="${fn:contains(holiday.holTy, '3')}">checked</c:if>><label class="k-checkbox-label" for="checkbox04"></label></td>
								<td><input type="checkbox" name="holTy" id="checkbox05" class="k-checkbox" value="4" disabled <c:if test="${fn:contains(holiday.holTy, '4')}">checked</c:if>><label class="k-checkbox-label" for="checkbox05"></label></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>	
			<tr>
				<th>(*)사유</th>
				<td colspan="3">
					<textarea class="k-textbox width100p height100p" readonly="readonly"><c:out value="${holiday.holRs}"/></textarea>
					<%-- <c:out value="${holiday.holRs}"/> --%>
				</td>
			</tr>
			<tr>
				<th>비고</th>
				<td colspan="3">
					<textarea class="k-textbox width100p height100p" readonly="readonly"><c:out value="${holiday.holRm}"/></textarea>
					<%-- <c:out value="${holiday.holRm}"/> --%>
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
					<c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
						<c:param name="param_atchFileId" value="${holiday.atchFileId}" />
						<c:param name="param_colspan" value="3" />
					</c:import>
				</td>			
			</tr>
			</tbody>			
		</tbody>
	</table>	
	
	<div class="space20"></div>
	<div class="btngroup fl">		
		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/manager/epay/managerEpayDraftInfoList.do'/>'">목록</button>
	</div>
	<div class="btngroup fr">
	</div>
	<div class="space20"></div>
	
	<!-- 소속 선택 -->
		<div class="modal fade" id="modal_holiday" tabindex="-1" role="dialog" aria-labelledby="modal02Label" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="modal02Label">연차등록</h4>
					</div>
					<div class="modal-body">
						연차를 등록하시겠습니까?
					</div>
					<div class="modal-footer">
						<button type="button" class="btn02" data-dismiss="modal">취소</button>
						<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_register();">등록</button>
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
	//window.open("<c:url value='/cmm/fms/getImage.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>","new","width=400, height=300, left=100, top=100,scrollbars=no,titlebar=no,status=no,resizable=no,fullscreen=no");
	window.open("<c:url value='/cmm/fms/FileDown.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>");
}
</script>

<script>

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
	fn_sumTotPrice(); // 매입/매출 합계액 계산
});
</script>