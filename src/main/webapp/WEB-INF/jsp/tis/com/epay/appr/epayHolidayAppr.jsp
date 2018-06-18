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
		document.frm.action = "<c:url value='/com/epay/draft/epayDraftInfoList.do'/>";
		document.frm.submit();
	}
	
	function fn_requestApprList(){
		
		location.href="<c:url value ='/com/epay/appr/epayApprInfoList.do?method=payment'/>";
		
	 	/* document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/epay/appr/epayApprInfoList.do'/>";
		document.frm.submit(); */
	}
	
	function fn_requestEpayApprAccept(){
		
		$.ajax({
			url:"<c:url value='/com/epay/appr/epayApprAccept.do'/>",
			type:"post",
			data:$("form[name=frm]").serializeArray(),
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				//alert(data);
				fn_requestApprList();
			}				
		});
	}
	
	function fn_requestEpayApprReturn(){
		
		$.ajax({
			url:"<c:url value='/com/epay/appr/epayApprReturn.do'/>",
			type:"post",
			data:$("form[name=frm]").serializeArray(),
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				fn_requestApprList();
			}				
		});
	}

	function fn_requestEpayApprDecide(){
		
		$.ajax({
			url:"<c:url value='/com/epay/appr/epayApprDecide.do'/>",
			type:"post",
			data:$("form[name=frm]").serializeArray(),
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				fn_requestApprList();
			}				
		});
	}
	
</script>

<form name="frm" method="post">
<input type="hidden" name="method" value="payment" />
<input type="hidden" name="draftInfoId" id="draftInfoId" value='<c:out value="${draftInfoId}"/>'>
<div id="subwrap">
	<div class="btngroup fl">		
		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/epay/appr/epayApprInfoList.do?method=payment'/>'">목록</button>
	</div>
	<div class="btngroup fr">
		<c:if test="${holiday.apprEmplNo eq loginEmplNo and apprState eq '0'}">
			<!-- <button type="button" class="btn01" data-toggle="modal" data-target="#modal05">전결</button> -->
			<button type="button" class="btn05" data-toggle="modal" data-target="#modal06holiday">승인</button>		
			<button type="button" class="btn02" data-toggle="modal" data-target="#modal07holiday">반려</button>
		</c:if>
	</div>
	<div class="clear"></div>
	<div class="space10"></div>
	<h4><i class="fa fa-th-large"></i>휴가원</h4>
	<table class="tableview">
		<colgroup>
			<col class="width180">
			<col class="width600">
			<col class="width180">
			<col class="width600">
		</colgroup>
		<tbody>
			<tr>
				<th>결재이력</th>
				<td colspan="3">
					<table class="tablelist">
						<colgroup>
							<col class="width300">
							<col class="width300">
							<col class="width300">
							<col class="width300">
						</colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th>성명</th>
								<th>상태</th>
								<th>결재일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${apprHistList}" varStatus="status">		
								<tr>
									<td><c:out value="${result.postn}"/></td>
									<td><c:out value="${result.approverNm}"/></td>
									<td>
										<c:if test="${fn:contains(result.apprState, '0')}"><span class="state02 bgcolor01">대기</span></c:if>
										<c:if test="${fn:contains(result.apprState, '1')}"><span class="state02 bgcolor02">승인</span></c:if>
										<c:if test="${fn:contains(result.apprState, '2')}"><span class="state02 bgcolor03">반려</span></c:if>
										<c:if test="${fn:contains(result.apprState, '3')}"><span class="state02 bgcolor04">임시저장</span></c:if>
									</td>
									<td><c:out value="${result.apprTm}"/>
									</td>
								</tr>
							</c:forEach>						
						
						</tbody>					
					</table>
				</td>
			</tr>
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
					<strong id="usingHoliday" name="holDay"><c:out value="${holiday.holDay}"/></strong>일간
					
					<%-- <input class="datepicker width200" name="holSdate" id="holSdate" value='<c:out value="${epayHolidayVO.holSdate}"/>' >&nbsp;&nbsp;~&nbsp;
					<input class="datepicker width200" name="holEdate" id="holEdate" value='<c:out value="${epayHolidayVO.holEdate}"/>' >&nbsp;&nbsp;
					<strong id="usingHoliday" name="holDay"><c:out value="${epayHolidayVO.holDay}"/></strong>일(잔여휴가 <strong id="existHolDay">0</strong>일) --%>
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
	<!-- 첨언이 있을경우 노출 -->	
	<div class="space20"></div>
	<h4><i class="fas fa-dot-circle"></i>첨언</h4>
	<table class="tableview">
		<colgroup>
			<col class="width194">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th>첨언</th>
				<td>
					<table class="tablelist">
						<colgroup>
							<col class="width300">
							<col class="width300">
							<col class="width300">
							<col class="width300">
							<col class="width300">
						</colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th>성명</th>
								<th>상태</th>
								<th>첨언</th>
								<th>결재일</th>								
							</tr>
						</thead>
						<tbody>	
							<c:forEach var="result" items="${apprHistList}" varStatus="status">
								<c:if test="${!empty result.cm}">
									<tr>
										<td><c:out value="${result.approverNm}"/></td>
										<td><c:out value="${result.postn}"/></td>
										<td>
											<c:if test="${fn:contains(result.apprState, '0')}"><span class="state02 bgcolor01">결재진행중</span></c:if>
											<c:if test="${fn:contains(result.apprState, '1')}"><span class="state02 bgcolor02">승인</span></c:if>
											<c:if test="${fn:contains(result.apprState, '2')}"><span class="state02 bgcolor03">반려</span></c:if>
											<c:if test="${fn:contains(result.apprState, '3')}"><span class="state02 bgcolor04">임시저장</span></c:if>
										</td>
										<td><a href="#" onClick="return false;" data-toggle="tooltip" data-placement="bottom" title="<c:out value="${result.cm}"/>"><c:out value="${result.cm}"/></a></td>
										<td><c:out value="${result.apprTm}"/></td>								
									</tr>
								</c:if>
								
							</c:forEach>						
						</tbody>					
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- 첨언이 있을경우 노출 //-->
	<!-- 반려의견이 있을경우 노출 -->
	<div class="space20"></div>
	<h4><i class="fas fa-dot-circle"></i>반려의견</h4>
	<table class="tableview">
		<colgroup>
			<col class="width194">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th>반려의견</th>
				<td>
					<table class="tablelist">
						<colgroup>
							<col class="width300">
							<col class="width300">
							<col class="width300">
							<col class="width300">
							<col class="width300">
						</colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th>성명</th>
								<th>상태</th>
								<th>사유</th>
								<th>결재일</th>								
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${apprHistList}" varStatus="status">
								<c:if test="${!empty result.returnCm}">
									<tr>
										<td><c:out value="${result.approverNm}"/></td>
										<td><c:out value="${result.postn}"/></td>
										<td>
											<c:if test="${fn:contains(result.apprState, '0')}"><span class="state02 bgcolor01">결재진행중</span></c:if>
											<c:if test="${fn:contains(result.apprState, '1')}"><span class="state02 bgcolor02">승인</span></c:if>
											<c:if test="${fn:contains(result.apprState, '2')}"><span class="state02 bgcolor03">반려</span></c:if>
											<c:if test="${fn:contains(result.apprState, '3')}"><span class="state02 bgcolor04">임시저장</span></c:if>
										</td>
										<td><a href="#" onClick="return false;" data-toggle="tooltip" data-placement="bottom" title="<c:out value="${result.returnCm}"/>"><c:out value="${result.returnCm}"/></a></td>
										<td><c:out value="${result.apprTm}"/></td>								
									</tr>
								</c:if>
							</c:forEach>		
												
						</tbody>					
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- 반려의견이 있을경우 노출 //-->
	<div class="space20"></div>
	<div class="btngroup fl">		
		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/epay/appr/epayApprInfoList.do?method=payment'/>'">목록</button>
	</div>
	<div class="btngroup fr">
	</div>
	<div class="space20"></div>
</div>

<!-- 결재함 전결 -->
	<div class="modal fade" id="modal05holiday" tabindex="-1" role="dialog" aria-labelledby="modal05Labelholiday" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="modal05Labelholiday">결재요청문서(전결)</h4>
				</div>
				<div class="modal-body">
					<textarea id="apprDecide" name="apprDecide"  class="k-textbox width100p height100"><c:out value="${apprDecide}"/></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn02" data-dismiss="modal">취소</button>
					<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_requestEpayApprDecide()">확인</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 결재함 승인 -->
	<div class="modal fade" id="modal06holiday" tabindex="-1" role="dialog" aria-labelledby="modal06Labelholiday" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="modal06Labelholiday">결재요청문서(승인)</h4>
				</div>
				<div class="modal-body">
					<textarea id="apprCm" name="apprCm" class="k-textbox width100p height100" placeholder="첨언을 입력하세요."><c:out value="${apprCm}"/></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn02" data-dismiss="modal">취소</button>
					<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_requestEpayApprAccept()">확인</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 결재함 반려 -->
	<div class="modal fade" id="modal07holiday" tabindex="-1" role="dialog" aria-labelledby="modal07Labelholiday" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="modal07Labelholiday">결재요청문서(반려)</h4>
				</div>
				<div class="modal-body">
					<textarea id="apprReturnCm" name="apprReturnCm" class="k-textbox width100p height100" placeholder="반려의견을 입력하세요."><c:out value="${apprReturnCm}"/></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn02" data-dismiss="modal">취소</button>
					<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_requestEpayApprReturn()">확인</button>
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