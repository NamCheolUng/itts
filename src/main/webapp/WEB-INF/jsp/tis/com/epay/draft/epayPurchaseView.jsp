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
		fn_sumPrice();
	});
	
	function fn_back(){
		document.frm.action = "<c:url value='/com/epay/draft/epayCnsulView.do'/>";
		document.frm.submit();
	}
	
	function numberWithCommas(x) {
	    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
	
	// 지출내역 자동 합계 계산
	function fn_sumPrice(){
		
		var f = document.frm;
		
		var prcsPlanSurtax = 0;
		var prcsPlanPrice = 0;
		var price = 0;
		
		
		$('.surtax').each(function(){
			prcsPlanSurtax += parseFloat($(this).text());
		});
		
		$('.supplyPrice').each(function(){
			prcsPlanPrice += parseFloat($(this).text());
		});
		
		$('#prcsPlanSurtax').text(numberWithCommas(prcsPlanSurtax));
		$('#prcsPlanPrice').text(numberWithCommas(prcsPlanPrice));
		$('#prcsPlanTotPrice').text(numberWithCommas(prcsPlanSurtax + prcsPlanPrice));
	}
</script>
<form name="frm" method="post">
	<input type="hidden" name="purchaseId" value="${purchaseVO.purchaseId}">
	<input type="hidden" name="draftInfoId" value="${draftInfoId}">
	<input type="hidden" name="apprSttus" value="${apprSttus}">
	<input type="hidden" name="apprState" value="${apprState}">
	
	<div id="subwrap">
		
		<div class="space10"></div>
		
		<h4>
			<i class="fas fa-dot-circle"></i>매입 내역
		</h4>
		<table class="tableview">
			<colgroup>
				<col class="width180">
				<col class="width600">
				<col class="width180">
				<col class="width600">
			</colgroup>
			<tbody>
				<tr>
					<th>발주일</th>
					<td><c:out value='${purchaseVO.orderDt}'/></td>
					<th>매출처</th>
					<td><c:out value='${purchaseVO.cmpnyNm}'/></td>
				</tr>
				<tr>
					<th>상품/서비스명</th>
					<td colspan="3"><c:out value="${purchaseVO.prdtNm}"/></td>
				</tr>
				<tr>
					<th>결제예정일</th>
					<td><c:out value="${purchaseVO.payPlanDate}"/></td>
					<th>거래구분</th>
					<td><c:out value="${purchaseVO.tradeSeptNm}"/></td>
				</tr>
				<tr>
					<th>결제조건</th>
					<td><c:out value="${purchaseVO.payPointNm}"/></td>
					<th>결제방식</th>
					<td><c:out value="${purchaseVO.payWayNm}"/></td>
				</tr>
				<tr>
					<th>공급가액 합계</th>
					<td id="prcsPlanPrice">${purchaseVO.prcsPlanPrice}</td>
					<th>세액 합계</th>
					<td id="prcsPlanSurtax">${purchaseVO.prcsPlanSurprcsPlan}</td>
				</tr>
				<tr>
					<th>Total</th>
					<td colspan="3" id="prcsPlanTotPrice">${purchaseVO.prcsPlanTotPrice}</td>
				</tr>
				<tr>
					<th>매출세부항목</th>
					<td colspan="3">
						<table class="tablelist">
							<colgroup>
								<col class="width300">
								<col class="width100">
								<col class="width150">
								<col class="width150">
								<col class="width150">
								<col class="width80">
							</colgroup>
							<thead>
								<tr>
									<th>품명</th>
									<th>수량</th>
									<th>단가</th>
									<th>공급가액</th>
									<th>세액</th>
								</tr>
							</thead>
							<tbody id='my-tbody'>
								<c:forEach var="purchaseDetail" items="${purchaseDetailVO}" varStatus="status">
									<tr>
										<td>${purchaseDetail.itemNm}</td>
										<td>${purchaseDetail.qty}</td>
										<td class="price">${purchaseDetail.price}</td>
										<td class="supplyPrice">${purchaseDetail.splyPrice}</td>
										<td class="surtax">${purchaseDetail.surtax}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3">
						<c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
							<c:param name="param_atchFileId" value="${purchaseVO.atchFileId}" />
							<c:param name="param_colspan" value="3" />
						</c:import>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="space20"></div>
		<div class="btngroup fl">		
			<button type="button" class="btn04" onclick="javascript:fn_back();">이전</button>
		</div>
	</div>
</form>

</body>
</html>