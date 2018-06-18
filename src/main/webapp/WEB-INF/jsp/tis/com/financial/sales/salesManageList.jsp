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
	<input type="hidden" name="salesId" value="">
	
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
							<c:forEach var="codeVO" items="${deptList}" varStatus="status">	
								<option value="${codeVO.code}" <c:out value="${codeVO.code == searchVO.searchDeptCode ? 'selected' : ''}"/>>${codeVO.codeNm}</option>
							</c:forEach>
						</select>
						<select name="searchAdbkId" class="select width150">
							<option value="" <c:out value="${searchVO.searchAdbkId == null ? 'selected' : ''}"/>>관련기관/업체 전체</option>
							<c:forEach var="codeVO" items="${adbkList}" varStatus="status">	
								<option value="${codeVO.adbkId}" <c:out value="${codeVO.adbkId == searchVO.searchAdbkId ? 'selected' : ''}"/>>${codeVO.cmpnyNm}</option>
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
							<option value="cmpnyNm" <c:out value="${searchVO.searchCondition == 'cmpnyNm' ? 'selected' : ''}"/>>매출처</option>
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
	
	<h4><i class="fas fa-dot-circle"></i>매출현황</h4>

	<table class="tablelist">
		<thead>
			<tr>
				<th class="width200"></th>
				<th>매출금액</th>
				<th>VAT</th>
				<th>Total</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>매출계획금액</td>
				<td id="salesPlanPrice">0</td>
				<td id="salesPlanSurtax">0</td>
				<td id="salesPlanTotPrice">0</td>
			</tr>
			<tr>
				<td>매출집행금액</td>
				<td id="salesExecPrice">0</td>
				<td id="salesExecSurtax">0</td>
				<td id="salesExecTotPrice">0</td>
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
			<col class="width200">
		</colgroup>
		<thead>
			<tr>
				<th rowspan="2"><input type="checkbox" id="sel_all" class="k-checkbox" onclick="fn_checkOrUncheckAll('sel_all');"><label class="k-checkbox-label" for="sel_all"></label></th>
				<th rowspan="2">발주일</th>
				<th rowspan="2">주매출부</th>
				<th rowspan="2">과제명(건명)</th>
				<th rowspan="2">상품/서비스명</th>
				<th rowspan="2">매출처</th>
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
				<td><a href="javascript:fn_requestSalesManageView('${result.salesId}')"><c:out value="${result.prdtNm}"/></a></td>
				<td><c:out value="${result.cmpnyNm}"/></td>
				<td><fmt:formatNumber value="${result.salesPlanPrice}" pattern="#,###"/></td>
				<td><fmt:formatNumber value="${result.salesPlanSurtax}" pattern="#,###"/></td>
				<td><fmt:formatNumber value="${result.salesPlanTotPrice}" pattern="#,###"/></td>
				<td><fmt:formatNumber value="${result.salesExecPrice}" pattern="#,###"/></td>
				<td><fmt:formatNumber value="${result.salesExecSurtax}" pattern="#,###"/></td>
				<td><fmt:formatNumber value="${result.salesExecTotPrice}" pattern="#,###"/></td>
				<td><c:out value="${result.payPlanDate}"/></td>
				<td><c:out value="${result.payDate}"/></td>
				<td><c:out value="${result.payAt}"/></td>
				<td><c:out value="${result.taxPlanDate}"/></td>
				<td><c:out value="${result.tradeSeptNm}"/></td>
			</tr>
<script>
			var mapData = new Map();
			
			mapData.put("salesPlanPrice", ${result.salesPlanPrice > 0 ? result.salesPlanPrice : 0});
			mapData.put("salesPlanSurtax", ${result.salesPlanSurtax > 0 ? result.salesPlanSurtax : 0});
			mapData.put("salesPlanTotPrice", ${result.salesPlanTotPrice > 0 ? result.salesPlanTotPrice : 0});
			
			mapData.put("salesExecPrice", ${result.salesExecPrice > 0 ? result.salesExecPrice : 0});
			mapData.put("salesExecSurtax", ${result.salesExecSurtax > 0 ? result.salesExecSurtax : 0});
			mapData.put("salesExecTotPrice", ${result.salesExecTotPrice > 0 ? result.salesExecTotPrice : 0});
			
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
	document.frm.action = "<c:url value='/com/financial/sales/salesManageList.do'/>";
	document.frm.submit();
}

function fn_requestSalesManageView(salesId) {
	document.frm.salesId.value = salesId;
	document.frm.action = "<c:url value='/com/financial/sales/salesManageView.do'/>";
	document.frm.submit();
}

function fn_searchKeyPressed(event) {
	if (event.keyCode==13) {
		fn_requestList('1');
	}
}

function fn_downloadExcel() {
	document.frm.action = "<c:url value='/com/financial/sales/salesManageListExcelDownload.do'/>";
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
	var salesPlanPriceSum = 0;
	var salesPlanSurtaxSum = 0;
	var salesPlanTotPriceSum = 0;
	
	var salesExecPriceSum = 0;
	var salesExecSurtaxSum = 0;
	var salesExecTotPriceSum = 0;
	
	$('input[id ^= "sel_item_"]').each(function() {
		var id = $(this)[0].id;
		var checkbox = $("#" + id)[0];
		if(checkbox.checked) {
			salesPlanPriceSum += map.get(id).get("salesPlanPrice");
			salesPlanSurtaxSum += map.get(id).get("salesPlanSurtax");
			salesPlanTotPriceSum += map.get(id).get("salesPlanTotPrice");
			
			salesExecPriceSum += map.get(id).get("salesExecPrice");
			salesExecSurtaxSum += map.get(id).get("salesExecSurtax");
			salesExecTotPriceSum += map.get(id).get("salesExecTotPrice");
		}
	});
	
	$('#salesPlanPrice').text(fn_numberWithCommas(salesPlanPriceSum));
	$('#salesPlanSurtax').text(fn_numberWithCommas(salesPlanSurtaxSum));
	$('#salesPlanTotPrice').text(fn_numberWithCommas(salesPlanTotPriceSum));
	
	$('#salesExecPrice').text(fn_numberWithCommas(salesExecPriceSum));
	$('#salesExecSurtax').text(fn_numberWithCommas(salesExecSurtaxSum));
	$('#salesExecTotPrice').text(fn_numberWithCommas(salesExecTotPriceSum));
}

function fn_numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

$(document).ready(function() {
	fn_initSearchDatePicker("searchOrderSdate", "searchOrderEdate");
});
</script>
