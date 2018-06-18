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
	});
	
	function btn_submit(){
	
		var f = document.frm;
		
		if(f.orderDt.value=="") {
			alert("발주일을 선택하세요.");
			f.orderDt.focus();
			return false;
		}
		
		if(f.adbkId.value=="") {
			alert("매출처를 선택하세요.");
			return false;
		}
		
		if(f.prdtNm.value=="") {
			alert("상품/서비스명을 입력하세요.");
			f.prdtNm.focus();
			return false;
		}
		
		if(f.payPlanDate.value=="") {
			alert("결제예정일 선택하세요.");
			f.payPlanDate.focus();
			return false;
		}
		
		if(confirm('수정 하시겠습니까?')) {
			f.action = "<c:url value='/com/financial/sales/salesManageWriteUpdate.do'/>";
			f.submit();
		}
	}
	
	function fn_cancel() {
		document.frm.action = "<c:url value='/com/financial/sales/salesManageList.do'/>";
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
			salesPlanSurtax += parseFloat($(this).val());
		});
		
		$('.supplyPrice').each(function(){
			salesPlanPrice += parseFloat($(this).val());
		});
		
		
		$('#salesPlanSurtax').text(numberWithCommas(salesPlanSurtax));
		$('#salesPlanPrice').text(numberWithCommas(salesPlanPrice));
		$('#salesPlanTotPrice').text(numberWithCommas(salesPlanSurtax + salesPlanPrice));
		
		f.salesPlanSurtax.value = salesPlanSurtax;
		f.salesPlanPrice.value = salesPlanPrice;
		f.salesPlanTotPrice.value = salesPlanSurtax + salesPlanPrice;
	}
	
	var idx = 0;
	
	function fn_calPrice(index) {
		var qty = $('#qty' + index)[0].value;
		var price = $('#price' + index)[0].value;
		var supplyPrice = qty * price;
		
		$('#supplyPrice' + index)[0].value = supplyPrice;
		//$('#surtax' + index)[0].value = supplyPrice * 0.1;
		
		fn_sumPrice();
	}

	// 매출 세부항목 동적 행 추가 및 금액 필드 자동 계산 처리
	function addSalesDetailRow() {
		var template =	'<tr>';
			template +=	'<td><input type="text" name="list['+idx+'].itemNm" class="k-textbox width100p" value=""></td>';
			template +=	'<td><input type="text" name="list['+idx+'].qty" id="qty'+idx+'" class="k-textbox width100p" value="0" onChange="javascript:fn_calPrice(' + idx + ');"></td>';
			template +=	'<td><input type="text" name="list['+idx+'].price" id="price'+idx+'" class="k-textbox width100p price" value="0" onChange="javascript:fn_calPrice(' + idx + ');"></td>';
			template +=	'<td><input type="text" name="list['+idx+'].splyPrice" id="supplyPrice'+idx+'" class="k-textbox width100p supplyPrice" value="0" onChange="javascript:fn_sumPrice();" readonly></td>';
			template +=	'<td><input type="text" name="list['+idx+'].surtax" id="surtax'+idx+'" class="k-textbox width100p surtax" value="0" onChange="javascript:fn_sumPrice();"></td>';
			template +=	'<td><a href="#" onclick="return deleteRow(this);"><i class="fas fa-window-close"></i></a></td>';
			template +=	'</tr>';
	    
 	    $("#my-tbody").append(template).trigger("create");
	    
        fn_sumPrice();
        
        idx++;
	}
	
	function deleteRow(obj) {
		$(obj).parent().parent().remove();
		fn_sumPrice();
	}
	
	function fn_companyListPopup(){
		window.open("<c:url value='/com/task/popup/companyListPopup.do'/>","","width=800px,height=800px");
	}
	
	function fn_calcOnlyNumber(event){
		event = event || window.event;
		var keyID = (event.which) ? event.which : event.keyCode;
		if ( (keyID >= 48 && keyID <= 57)
				|| (keyID >= 96 && keyID <= 105)
				|| keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39
				|| keyID == 86 )
			return;
		else
			return false;
	}
	
	function fn_calcRemoveChar(event) {
		event = event || window.event;
		var keyID = (event.which) ? event.which : event.keyCode;
		if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) {
			if ( keyID == 8 ) fn_calcPrice();
			return;
		}
		else
			event.target.value = event.target.value.replace(/[^0-9]/g, "");
		
		fn_calcPrice();
	}
	
	function fn_calcPrice() {
		document.frm.calc_price.placeholder = '';
		document.frm.calc_vat.placeholder = '';
		
		document.frm.calc_price.value = Math.round(document.frm.calc_tot_price.value / 1.1);
		document.frm.calc_vat.value = document.frm.calc_tot_price.value - document.frm.calc_price.value;
	}
	
	function fn_returnCompanyPopup(id,nm){
		document.frm.adbkId.value=id;
		$("#companyNm").val(nm);
	}
</script>
<form name="frm" method="post">
	<input type="hidden" name="salesId" value="${salesVO.salesId}">
	
	<div id="subwrap">
		<div class="schgroup">
			<table class="tablesch">
				<colgroup>
					<col class="width180">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th>세액계산기</th>
						<td>
							<input type="text" name="calc_tot_price" class="k-textbox width30p" placeholder="단가(VAT포함)" title="단가(VAT포함)" onkeydown='return fn_calcOnlyNumber(event)' onkeyup='fn_calcRemoveChar(event)' style='ime-mode:disabled;'>
							=
							<input type="text" name="calc_price" class="k-textbox width30p" placeholder="단가" title="단가" readonly>
							+
							<input type="text" name="calc_vat" class="k-textbox width30p" placeholder="VAT" title="VAT" readonly>
						</td>
					</tr>				
				</tbody>
			</table>
		</div>
		
		<div class="space10"></div>
		
		<h4><i class="fas fa-dot-circle"></i>매출현황 상세보기</h4>
		
		(*) 필수입력항목
		
		<table class="tablewrite">
			<colgroup>
				<col class="width180">
				<col class="width600">
				<col class="width180">
				<col class="width600">
			</colgroup>
			<tbody>
				<tr>
					<th>(*)발주일</th>
					<td><input name="orderDt" class="datepicker width200" value="<c:out value='${salesVO.orderDt}'/>" placeholder="발주일" title="발주일"></td>
					<th>(*)매출처</th>
					<td>
						<input type="text" id="companyNm" class="k-textbox width80p" value="<c:out value='${salesVO.cmpnyNm}'/>" placeholder="매출처" title="매출처" readonly>
						<input type="hidden" name="adbkId" value="${salesVO.adbkId}">
						<button type="button" class="btn03" onclick="javascript:fn_companyListPopup();">찾기</button>
					</td>
				</tr>
				<tr>
					<th>(*)상품/서비스명</th>
					<td><input name="prdtNm" type="text" class="k-textbox width100p" value='<c:out value="${salesVO.prdtNm}"/>'></td>
					<th>(*)거래구분</th>
					<td>
						<select name="tradeSept" class="select width40p">
							<c:forEach var="codeVO" items="${tradeSeptList}" varStatus="status">
								<option value="${codeVO.code}" <c:out value="${salesVO.tradeSept == codeVO.code ? 'selected' : ''}"/>>${codeVO.codeNm}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>(*)결제예정일</th>
					<td><input name="payPlanDate" class="datepicker width200" value='<c:out value="${salesVO.payPlanDate}"/>' placeholder="결제예정일" title="결제예정일"></td>
					<th>(*)결제방식</th>
					<td>
						<select name="payWay" class="select width40p">
							<c:forEach var="codeVO" items="${payWayList}" varStatus="status">
								<option value="${codeVO.code}" <c:out value="${salesVO.payWay == codeVO.code ? 'selected' : ''}"/>>${codeVO.codeNm}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>(*)결제조건</th>
					<td>
						<select name="payPoint" class="select width40p">
							<c:forEach var="codeVO" items="${payPointList}" varStatus="status">
								<option value="${codeVO.code}" <c:out value="${salesVO.payPoint == codeVO.code ? 'selected' : ''}"/>>${codeVO.codeNm}</option>
							</c:forEach>
						</select>
					</td>
					<th>(*)결제여부</th>
					<td>
						<select name="payAt" class="select width40p">
						<option value="1" <c:out value="${purchaseVO.payAt == '1' ? 'selected' : ''}"/>>미수</option>
						<option value="2" <c:out value="${purchaseVO.payAt == '2' ? 'selected' : ''}"/>>완료</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>공급가액 합계</th>
					<td id="salesPlanPrice"></td>
					<input type="hidden" name="salesPlanPrice">
					<th>세액 합계</th>
					<td id="salesPlanSurtax">-</td>
					<input type="hidden" name="salesPlanSurtax">
				</tr>
				<tr>
					<th>Total</th>
					<td colspan="3" id="salesPlanTotPrice"><c:out value="${salesVO.orgnztNm}" /></td>
					<input type="hidden" name="salesPlanTotPrice">
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
									<th>
										<button type="button" class="btn" onclick="javascript:addSalesDetailRow();">추가</i></button>
									</th>
								</tr>
							</thead>
							<tbody id='my-tbody'>
								<c:set var="cnt" value="0" scope="page"/>
								<c:forEach var="salesDetail" items="${salesDetailVO}" varStatus="status">
									<tr>
										<td><input type="text" name="list[${status.index}].itemNm" id="itemNm${status.index}" class="k-textbox width100p" value="${salesDetail.itemNm}"></td>
										<td><input type="text" name="list[${status.index}].qty" id="qty${status.index}" class="k-textbox width100p" value="${salesDetail.qty}" onChange="javascript:fn_calPrice('${status.index}');"></td>
										<td><input type="text" name="list[${status.index}].price" id="price${status.index}" class="k-textbox width100p price" value="${salesDetail.price}" onChange="javascript:fn_calPrice('${status.index}');"></td>
										<td><input type="text" name="list[${status.index}].splyPrice" id="supplyPrice${status.index}" class="k-textbox width100p supplyPrice" value="${salesDetail.splyPrice}" onChange="javascript:fn_sumPrice();" readonly></td>
										<td><input type="text" name="list[${status.index}].surtax" id="surtax${status.index}" class="k-textbox width100p surtax" value="${salesDetail.surtax}" onChange="javascript:fn_sumPrice();"></td>
										<td><a href="#" onclick="return deleteRow(this);"><i class="fas fa-window-close"></i></a></td>
									</tr>
									<c:set var="cnt" value="${status.count}" scope="page"/>
								</c:forEach>
								<script>idx = ${cnt}; fn_sumPrice();</script>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<th>공급가액 합계(집행금액)</th>
					<td><input type="text" name="salesExecPrice" class="k-textbox width100p" value="${salesVO.salesExecPrice != null ? salesVO.salesExecPrice : '0'}"></td>
					<th>세액 합계(집행금액)</th>
					<td><input type="text" name="salesExecSurtax" class="k-textbox width100p" value="${salesVO.salesExecSurtax != null ? salesVO.salesExecSurtax : '0'}"></td>
				</tr>
				<tr>
					<th>Total(집행금액)</th>
					<td colspan="3"><input type="text" name="salesExecTotPrice" class="k-textbox width100p" value="${salesVO.salesExecTotPrice != null ? salesVO.salesExecTotPrice : '0'}"></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3">
						* 견적서, 발주서 등의 첨부파일을 등록하세요.
						<input type="file" class="files" name="files" aria-label="찾아보기" value='<c:out value="${salesVO.atchFileId}"/>'>
						<c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
							<c:param name="param_atchFileId" value="${salesVO.atchFileId}" />
							<c:param name="param_colspan" value="3" />
						</c:import>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="space20"></div>
		<div class="btngroup fr">
			<button type="button" class="btn01" onclick="javascript:btn_submit();">수정</button>
			<button type="button" class="btn02" onclick="javascript:fn_cancel();">취소</button>
		</div>
	</div>
</form>

</body>
</html>