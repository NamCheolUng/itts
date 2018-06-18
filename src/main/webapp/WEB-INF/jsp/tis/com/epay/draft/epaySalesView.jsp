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
	
	function fn_sumPrice(){
		
		var f = document.frm;
		
		var salesPlanSurtax = 0;
		var salesPlanPrice = 0;
		var price = 0;
		
		
		$('.surtax').each(function(){
			salesPlanSurtax += parseFloat($(this).text());
		});
		
		$('.supplyPrice').each(function(){
			salesPlanPrice += parseFloat($(this).text());
		});
		
		$('#salesPlanSurtax').text(numberWithCommas(salesPlanSurtax));
		$('#salesPlanPrice').text(numberWithCommas(salesPlanPrice));
		$('#salesPlanTotPrice').text(numberWithCommas(salesPlanSurtax + salesPlanPrice));
	}
</script>
<form name="frm" method="post">
	<input type="hidden" name="draftInfoId" value="${draftInfoId}">
	
	<div id="subwrap">
		
		<div class="space10"></div>
		
		<h4>
			<i class="fas fa-dot-circle"></i>매출 내역
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
					<td><c:out value='${salesVO.orderDt}'/></td>
					<th>매출처</th>
					<td><c:out value='${salesVO.cmpnyNm}'/></td>
				</tr>
				<tr>
					<th>상품/서비스명</th>
					<td colspan="3"><c:out value="${salesVO.prdtNm}"/></td>
				</tr>
				<tr>
					<th>결제예정일</th>
					<td><c:out value="${salesVO.payPlanDate}"/></td>
					<th>거래구분</th>
					<td><c:out value="${salesVO.tradeSeptNm}"/></td>
				</tr>
				<tr>
					<th>결제조건</th>
					<td><c:out value="${salesVO.payPointNm}"/></td>
					<th>결제방식</th>
					<td><c:out value="${salesVO.payWayNm}"/></td>
				</tr>
				<tr>
					<th>공급가액 합계</th>
					<td id="salesPlanPrice">${salesVO.salesPlanPrice}</td>
					<th>세액 합계</th>
					<td id="salesPlanSurtax">${salesVO.salesPlanSurtax}</td>
				</tr>
				<tr>
					<th>Total</th>
					<td colspan="3" id="salesPlanTotPrice">${salesVO.salesPlanTotPrice}</td>
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
								<c:forEach var="salesDetail" items="${salesDetailVO}" varStatus="status">
									<tr>
										<td>${salesDetail.itemNm}</td>
										<td>${salesDetail.qty}</td>
										<td class="price">${salesDetail.price}</td>
										<td class="supplyPrice">${salesDetail.splyPrice}</td>
										<td class="surtax">${salesDetail.surtax}</td>
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
							<c:param name="param_atchFileId" value="${salesVO.atchFileId}" />
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