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
		//alert(${apprState});
		//alert('apprState : ' + ${apprState} + ' cnsul.apprEmplNo : ' + ${cnsul.apprEmplNo} + ' loginEmplNo : ' + ${loginEmplNo});
	});
	
	function goApprList() { location.href="<c:url value ='/com/epay/appr/epayApprInfoList.do?method=payment'/>"; }
	
	         
	function fn_requestApprList(){
		document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/epay/appr/epayApprInfoList.do'/>";
		document.frm.submit();
	}
	
	function fn_requestDraftSalesView(salesId){
		document.frm.salesId.value = salesId;
		document.frm.action = "<c:url value='/com/epay/appr/epayCnsulApprSalesView.do'/>";
		document.frm.submit();
	}
	
	function fn_requestDraftPurchaseView(purchaseId){
		document.frm.purchaseId.value = purchaseId;
		document.frm.action = "<c:url value='/com/epay/appr/epayCnsulApprPurchaseView.do'/>";
		document.frm.submit();
	}
	
	function fn_requestEpayApprAccept(){
		var comment = ''; // 첨언
		
		$.ajax({
			url:"<c:url value='/com/epay/appr/epayApprAccept.do'/>",
			type:"post",
			data:$("form[name=frm]").serializeArray(),
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				//alert(data);
				//fn_requestApprList();
				goApprList();
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
				//alert(data);
				//fn_requestApprList();
				goApprList();
			}				
		});
	}

	function fn_requestEpayApprDecide(){
		
		$.ajax({
			url:"<c:url value='/com/epay/appr/epayCnsulApprDecide.do'/>",
			type:"post",
			data:$("form[name=frm]").serializeArray(),
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				//alert(data);
				fn_requestApprList();
			}				
		});
		
		alert('전결 처리 완료');
	}
	
</script>

<form name="frm" method="post">
<input type="hidden" name="method" value="payment" />
<input type="hidden" name="draftInfoId" id="draftInfoId" value='<c:out value="${draftInfoId}"/>'>
<input type="hidden" name="salesId" value="">
<input type="hidden" name="purchaseId" value="">
<input type="hidden" name="apprSttus" value="${cnsul.apprSttus}">
<input type="hidden" name="apprState" value="${apprState}">
<div id="subwrap">
	<div class="btngroup fl">		
		<button type="button" class="btn04" onclick="location.href='<c:url value ='/com/epay/appr/epayApprInfoList.do?method=payment'/>'">목록</button>
	</div>
	<div class="btngroup fr">
		<c:if test="${cnsul.apprEmplNo eq loginEmplNo and apprState eq '0'}">
			<!-- <button type="button" class="btn01" data-toggle="modal" data-target="#modal05">전결</button> -->
			<button type="button" class="btn05" data-toggle="modal" data-target="#modal06cnsul">승인</button>		
			<button type="button" class="btn02" data-toggle="modal" data-target="#modal07cnsul">반려</button>
		</c:if>
	</div>
	<div class="clear"></div>
	<div class="space10"></div>
	<h4><i class="fas fa-dot-circle"></i>품의서</h4>
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
									<input type="hidden" name="List[${status.index}].apprHistId" id="apprHistId" value='<c:out value="${result.apprHistId}"/>'>
									<td name="List[${status.index}].postn"><c:out value="${result.postn}"/></td>
									<td name="List[${status.index}].approverNm"><c:out value="${result.approverNm}"/></td>
									<td name="List[${status.index}].apprState">
										<c:if test="${fn:contains(result.apprState, '0')}"><span class="state02 bgcolor01">대기</span></c:if>
										<c:if test="${fn:contains(result.apprState, '1')}"><span class="state02 bgcolor02">승인</span></c:if>
										<c:if test="${fn:contains(result.apprState, '2')}"><span class="state02 bgcolor03">반려</span></c:if>
										<c:if test="${fn:contains(result.apprState, '3')}"><span class="state02 bgcolor04">임시저장</span></c:if>
									</td>
									<td name="List[${status.index}].apprTm"><c:out value="${result.apprTm}"/></td>
									<%-- <td><fmt:formatDate value="${result.mDate}" pattern="yy/MM/dd HH:mm"/></td> --%>
								</tr>
							</c:forEach>			
						</tbody>					
					</table>
				</td>
			</tr>
			<tr>
				<th>과제명</th>
				<td colspan="3" name=""><c:out value="${cnsul.taskNm}"/></td>
			</tr>
			<tr>
				<th>기안일자</th>
				<td name="regDt"><c:out value="${cnsul.regDt}"/></td>
				<th>시행일자</th>
				<td><c:out value="${cnsul.cnsulSdate}"/></td> 
			</tr>
			<tr>
				<th>기안부서</th>
				<td><c:out value="${cnsul.orgnztNm}"/></td>
				<th>기안자</th>
				<td name="userNm"><c:out value="${cnsul.userNm}"/></td>
			</tr>
			<tr>
				<th>매출합계</th>
				<td id="salesTotPrice">0</td>
				<th>매입합계</th>
				<td id="purchaseTotPrice">0</td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3"><c:out value="${cnsul.title}"/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<textarea class="k-textbox width100p height100p" readonly="readonly"><c:out value="${cnsul.cnsulCn}"/></textarea>
					<%-- <c:out value="${cnsul.cnsulCn}"/> --%>
				</td>
			</tr>
			<tr>
				<th>비고</th>
				<td colspan="3">
					<textarea class="k-textbox width100p height100p" readonly="readonly"><c:out value="${cnsul.rm}"/></textarea>
					<%-- <c:out value="${cnsul.rm}"/> --%>
				</td>
			</tr>		
		</tbody>
	</table>
	
<div class="space20"></div>
		<h4><i class="fas fa-dot-circle"></i>매출내역</h4>
		<table class="tablelist">
			<colgroup>
				<col class="width120">
				<col class="width400">
				<col>
				<col>
				<col>
				<col class="width150">
				<col class="width100">
				<col class="width100">
			</colgroup>
			<thead>
				<tr>
					<th>발주일</th>
					<th>상품/서비스명</th>
					<th>매출처</th>
					<th>Total</th>
					<th>결제예정일</th>
					<th>거래구분</th>
					<th>결제조건</th>
					<th>결제방식</th>
				</tr>
			</thead>
			<c:forEach var="sales" items="${draftSalesList}" varStatus="status">
				<tbody>
					<tr>
						<td>${sales.orderDt}</td>
						<td><a href="javascript:fn_requestDraftSalesView('${sales.salesId}')">${sales.prdtNm}</a></td>
						<td>${sales.cmpnyNm}</td>
						<td class="salesPrice"><fmt:formatNumber value="${sales.salesPlanTotPrice}" pattern="#,###"/></td>
						<td>${sales.payPlanDate}</td>
						<td>${sales.tradeSeptNm}</td>
						<td>${sales.payPoint}</td>
						<td>${sales.payWay}</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
		
		<div class="space20"></div>
		

		<h4><i class="fas fa-dot-circle"></i>매입내역</h4>

		
		<table class="tablelist">
			<colgroup>
				<col class="width120">
				<col class="width400">
				<col>
				<col>
				<col>
				<col class="width150">
				<col class="width100">
				<col class="width100">
			</colgroup>
			<thead>
				<tr>
					<th>발주일</th>
					<th>상품/서비스명</th>
					<th>매입처</th>
					<th>Total</th>
					<th>결제예정일</th>
					<th>거래구분</th>
					<th>결제조건</th>
					<th>결제방식</th>
				</tr>
			</thead>
			<c:forEach var="purchase" items="${draftPurchaseList}" varStatus="status">
				<tbody>
					<tr>
						<td>${purchase.orderDt}</td>
						<td><a href="javascript:fn_requestDraftPurchaseView('${purchase.purchaseId}')">${purchase.prdtNm}</a></td>
						<td>${purchase.cmpnyNm}</td>
						<td class="purchasePrice"><fmt:formatNumber value="${purchase.prcsPlanTotPrice}" pattern="#,###"/></td>
						<td>${purchase.payPlanDate}</td>
						<td>${purchase.tradeSeptNm}</td>
						<td>${purchase.payPoint}</td>
						<td>${purchase.payWay}</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>

		<div class="space20"></div>
		
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
										<td>
											<a href="#" onClick="return false;" data-toggle="tooltip" data-placement="bottom" title="<c:out value="${result.returnCm}"/>"><c:out value="${result.returnCm}"/></a>
										</td>
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
		<button type="button" class="btn04" onclick="location.href='<c:url value ='/com/epay/appr/epayApprInfoList.do?method=payment'/>'">목록</button>
	</div>
	<div class="btngroup fr">
		<c:if test="${cnsul.apprEmplNo eq loginEmplNo and apprState eq '0'}">
			<!-- <button type="button" class="btn01" data-toggle="modal" data-target="#modal05">전결</button> -->
			<button type="button" class="btn05" data-toggle="modal" data-target="#modal06cnsul">승인</button>		
			<button type="button" class="btn02" data-toggle="modal" data-target="#modal07cnsul">반려</button>
		</c:if>
	</div>
	<div class="space20"></div>
	
	
</div>

	<!-- 결재함 전결 -->
	<div class="modal fade" id="modal05cnsul" tabindex="-1" role="dialog" aria-labelledby="modal05Labelcnsul" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="modal05Labelcnsul">결재요청문서(전결)</h4>
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
	<div class="modal fade" id="modal06cnsul" tabindex="-1" role="dialog" aria-labelledby="modal06Labelcnsul" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="modal06Labelcnsul">결재요청문서(승인)</h4>
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
	<div class="modal fade" id="modal07cnsul" tabindex="-1" role="dialog" aria-labelledby="modal07Labelcnsul" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="modal07Labelcnsul">결재요청문서(반려)</h4>
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
	var totSalesPrice = 0;
	var totPurchasePrice = 0;
	
	$('.salesPrice').each(function(){
		totSalesPrice += parseFloat(uncomma($(this)[0].innerText));
	});
	
	$('.purchasePrice').each(function(){
		totPurchasePrice += parseFloat(uncomma($(this)[0].innerText));
	});
	
	$('#salesTotPrice').text(numberWithCommas(totSalesPrice));
	$('#purchaseTotPrice').text(numberWithCommas(totPurchasePrice));
}

$(document).ready(function(){ 
	  //모든 웹페이지의 항목들이 로딩이 완료되었을때 처리해줄 내용
		
		fn_sumTotPrice(); // 매입/매출 합계액 계산
	
	});
</script>