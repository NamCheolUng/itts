<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script>
	var map = new Map();
</script>

<div id="subwrap">
	<form name="frm" id="frm" method="post">
	<input type="hidden" name="purchaseId" value="">
	<input type="hidden" name="searchOrderSdateForApproval" value="${searchVO.searchOrderSdate}">
	<input type="hidden" name="searchOrderEdateForApproval" value="${searchVO.searchOrderEdate}">
	
		<div class="schgroup">
		<table class="tablesch">
			<colgroup>
				<col class="width180">
				<col>
				<col class="width180">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>검색</th>
					<td>
						<div class="formgroup">
						<select name="searchDeptCode" class="select width150">
							<option value=""<c:out value="${searchVO.searchDeptCode == null ? 'selected' : ''}"/>>부서 전체</option>
							<c:forEach var="dept" items="${deptList}" varStatus="status">	
								<option value="${dept.code}" <c:out value="${dept.code == searchVO.searchDeptCode ? 'selected' : ''}"/>>${dept.codeNm}</option>
							</c:forEach>
						</select>
						<select name="searchPrcsPlace" class="select width150">
							<option value="" <c:out value="${searchVO.searchPrcsPlace == null ? 'selected' : ''}"/>>관련기관/업체 전체</option>
							<c:forEach var="adbk" items="${adbkList}" varStatus="status">	
								<option value="${adbk.cmpnyNm}" <c:out value="${adbk.cmpnyNm == searchVO.searchPrcsPlace ? 'selected' : ''}"/>>${adbk.cmpnyNm}</option>
							</c:forEach>
						</select>
						<select name="searchPayAt" class="select width150">
							<option value="" <c:out value="${searchVO.searchPayAt == null ? 'selected' : ''}"/>>결제여부 전체</option>
							<option value="2" <c:out value="${searchVO.searchPayAt == '2' ? 'selected' : ''}"/>>완료</option>
							<option value="1" <c:out value="${searchVO.searchPayAt == '1' ? 'selected' : ''}"/>>미수</option>
						</select>
						&nbsp;&nbsp;
						발주일
						<input name="searchOrderSdate" id="searchOrderSdate" class="datepicker width120" value="${searchVO.searchOrderSdate}">
						~
						<input name="searchOrderEdate" id="searchOrderEdate" class="datepicker width120" value="${searchVO.searchOrderEdate}">
						&nbsp;&nbsp;
						<select name="searchCondition" class="select width150">
							<option value="" <c:out value="${searchVO.searchCondition == null || searchVO.searchCondition == '' ? 'selected' : ''}"/>>전체</option>
							<option value="taskNm" <c:out value="${searchVO.searchCondition == 'taskNm' ? 'selected' : ''}"/>>과제명(건명)</option>
							<option value="prdtNm" <c:out value="${searchVO.searchCondition == 'prdtNm' ? 'selected' : ''}"/>>상품/서비스명</option>
							<option value="prcsPlace" <c:out value="${searchVO.searchCondition == 'searchPrcsPlace' ? 'selected' : ''}"/>>매입처</option>
						</select>
						<input type="text" name="searchKeyword" class="k-textbox width100p"  value="<c:out value="${searchVO.searchCondition != '' ? searchVO.searchKeyword : ''}"/>" onkeypress="javascript:fn_searchKeyPressed(event);" placeholder="검색어 입력">
						<button type="button" class="btn03" onclick="javascript:fn_requestList()">검색</button>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
		
	<div class="space10"></div>
	
	<button type="button" class="btn01 fr" onclick="javascript:fn_requestPurchaseManageApprovalList();">매입정산</button>
	
	<h4><i class="fas fa-dot-circle"></i>매입현황</h4>
	
	<table class="tablelist">
		<thead>
			<tr>
				<th class="width200"></th>
				<th>매입금액</th>
				<th>VAT</th>
				<th>Total</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>매입계획금액</td>
				<td id="prcsPlanPrice">0</td>
				<td id="prcsPlanSurtax">0</td>
				<td id="prcsPlanTotPrice">0</td>
			</tr>
			<tr>
				<td>매입집행금액</td>
				<td id="prcsExecPrice">0</td>
				<td id="prcsExecSurtax">0</td>
				<td id="prcsExecTotPrice">0</td>
			</tr>
		</tbody>
	</table>
	
	<div class="space10"></div>
	
	<button type="button" class="btn01 fr" onclick="javascript:fn_downloadExcel();">엑셀내보내기</button>			

	<div class="space10"></div>
	
	<table class="tablelist">
		<colgroup>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
		</colgroup>
		<thead>
			<tr>
				<th rowspan="2"><input type="checkbox" id="sel_all" class="k-checkbox" onclick="fn_checkOrUncheckAll('sel_all');"><label class="k-checkbox-label" for="sel_all"></label></th>
				<th rowspan="2">발주일</th>
				<th rowspan="2">주매입부</th>
				<th rowspan="2">과제명(건명)</th>
				<th rowspan="2">상품/서비스명</th>
				<th rowspan="2">매입처</th>
				<th colspan="3">계획금액</th>
				<th colspan="3">집행금액</th>
				<th rowspan="2">결제예정일</th>
				<th rowspan="2">결제일</th>
				<th rowspan="2">결제여부</th>
				<th rowspan="2">세금계산서발행일</th>
				<th rowspan="2">거래구분</th>
			</tr>
			<tr>
				<th>매출금액</th>
				<th>VAT</th>
				<th>Total</th>
				<th>매출금액</th>
				<th>VAT</th>
				<th>Total</th>
			</tr>
		</thead>
		<tbody>		
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td><input type="checkbox" name="item" id="sel_item_${status.index}" class="k-checkbox" onclick="fn_calcPrice();"><label class="k-checkbox-label" for="sel_item_${status.index}"></label></td>
				<td><c:out value="${result.orderDt}"/></td>
				<td><c:out value="${result.deptNm}"/></td>
				<td><c:out value="${result.taskNm}"/></td>
				<td><a href="javascript:fn_requestPurchaseManageView('${result.purchaseId}')"><c:out value="${result.prdtNm}"/></a></td>
				<td><c:out value="${result.prcsPlace}"/></td>
				<td><fmt:formatNumber value="${result.prcsPlanPrice}" pattern="#,###"/></td>
				<td><fmt:formatNumber value="${result.prcsPlanSurtax}" pattern="#,###"/></td>
				<td><fmt:formatNumber value="${result.prcsPlanTotPrice}" pattern="#,###"/></td>
				<td><fmt:formatNumber value="${result.prcsExecPrice}" pattern="#,###"/></td>
				<td><fmt:formatNumber value="${result.prcsExecSurtax}" pattern="#,###"/></td>
				<td><fmt:formatNumber value="${result.prcsExecTotPrice}" pattern="#,###"/></td>
				<td><c:out value="${result.payPlanDate}"/></td>
				<td><c:out value="${result.payDate}"/></td>
				<td><c:out value="${result.payAt}"/></td>
				<td><c:out value="${result.taxPlanDate}"/></td>
				<td><c:out value="${result.tradeSeptNm}"/></td>
			</tr>
<script>
			var mapData = new Map();
			
			mapData.put("prcsPlanPrice", ${result.prcsPlanPrice > 0 ? result.prcsPlanPrice : 0});
			mapData.put("prcsPlanSurtax", ${result.prcsPlanSurtax > 0 ? result.prcsPlanSurtax : 0});
			mapData.put("prcsPlanTotPrice", ${result.prcsPlanTotPrice > 0 ? result.prcsPlanTotPrice : 0});
			
			mapData.put("prcsExecPrice", ${result.prcsExecPrice > 0 ? result.prcsExecPrice : 0});
			mapData.put("prcsExecSurtax", ${result.prcsExecSurtax > 0 ? result.prcsExecSurtax : 0});
			mapData.put("prcsExecTotPrice", ${result.prcsExecTotPrice > 0 ? result.prcsExecTotPrice : 0});
			
			map.put("sel_item_${status.index}", mapData);
</script>
		</c:forEach>
			<!-- 데이터가 없을때 화면에 메세지를 출력해준다 -->	
			<c:if test="${empty resultList}">
				<tr> 
					<td colspan="100%">
						<spring:message code="common.nodata.msg" />
					</td>
				</tr>   	          				 			   
			</c:if>
		</tbody>
	</table>
	
	</form>
</div>
</body>

<script>
function fn_requestList() {
	document.frm.action = "<c:url value='/com/financial/purchase/purchaseManageList.do'/>";
	document.frm.submit();
}

function fn_requestPurchaseManageView(purchaseId) {
	document.frm.purchaseId.value = purchaseId;
	document.frm.action = "<c:url value='/com/financial/purchase/purchaseManageView.do'/>";
	document.frm.submit();
}

function fn_requestPurchaseManageApprovalList() {
	document.frm.searchOrderSdate.value = document.frm.searchOrderSdateForApproval.value;
	document.frm.searchOrderEdate.value = document.frm.searchOrderEdateForApproval.value;
	document.frm.action = "<c:url value='/com/financial/purchase/purchaseManageApprovalList.do'/>";
	document.frm.submit();
}

function fn_searchKeyPressed(event) {
	if (event.keyCode==13) {
		fn_requestList('1');
	}
}

function fn_downloadExcel() {
	document.frm.action = "<c:url value='/com/financial/purchase/purchaseManageListExcelDownload.do'/>";
	document.frm.submit();
}

function fn_checkOrUncheckAll(checkAllId) {
	var checked = $("#" + checkAllId)[0].checked;
	
	$('input[id ^= "sel_item_"]').each(function() {
		var id = $(this)[0].id;
		var checkbox = $("#" + id)[0];
		checkbox.checked = checked;
	});
	
	fn_calcPrice();
}

function fn_calcPrice() {
	var prcsPlanPriceSum = 0;
	var prcsPlanSurtaxSum = 0;
	var prcsPlanTotPriceSum = 0;
	
	var prcsExecPriceSum = 0;
	var prcsExecSurtaxSum = 0;
	var prcsExecTotPriceSum = 0;
	
	$('input[id ^= "sel_item_"]').each(function() {
		var id = $(this)[0].id;
		var checkbox = $("#" + id)[0];
		if(checkbox.checked) {
			prcsPlanPriceSum += map.get(id).get("prcsPlanPrice");
			prcsPlanSurtaxSum += map.get(id).get("prcsPlanSurtax");
			prcsPlanTotPriceSum += map.get(id).get("prcsPlanTotPrice");
			
			prcsExecPriceSum += map.get(id).get("prcsExecPrice");
			prcsExecSurtaxSum += map.get(id).get("prcsExecSurtax");
			prcsExecTotPriceSum += map.get(id).get("prcsExecTotPrice");
		}
	});
	
	$('#prcsPlanPrice').text(fn_numberWithCommas(prcsPlanPriceSum));
	$('#prcsPlanSurtax').text(fn_numberWithCommas(prcsPlanSurtaxSum));
	$('#prcsPlanTotPrice').text(fn_numberWithCommas(prcsPlanTotPriceSum));
	
	$('#prcsExecPrice').text(fn_numberWithCommas(prcsExecPriceSum));
	$('#prcsExecSurtax').text(fn_numberWithCommas(prcsExecSurtaxSum));
	$('#prcsExecTotPrice').text(fn_numberWithCommas(prcsExecTotPriceSum));
}

function fn_numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

$(document).ready(function() {
	fn_initSearchDatePicker("searchOrderSdate", "searchOrderEdate");
});
</script>