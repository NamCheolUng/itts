<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/grouplist" />

<div id="subwrap">
	<form name="frm" id="frm" method="post">
	<input type="hidden" name="affiliationId" value="${searchVO.affiliationId}">
	
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
						발주일
						<input name="searchOrderSdate" id="searchOrderSdate" class="datepicker width120" value="${searchVO.searchOrderSdate}">
						~
						<input name="searchOrderEdate" id="searchOrderEdate" class="datepicker width120" value="${searchVO.searchOrderEdate}">
						&nbsp;&nbsp;
						<select name="searchCondition" class="select width150">
							<option value="PRCS_PLACE" <c:out value="${searchVO.searchCondition == 'PRCS_PLACE' || searchVO.searchCondition == null ? 'selected' : ''}"/>>매입처별</option>
							<option value="TASK_ID" <c:out value="${searchVO.searchCondition == 'TASK_ID' ? 'selected' : ''}"/>>과제별</option>
							<option value="MAIN_DEPT" <c:out value="${searchVO.searchCondition == 'MAIN_DEPT' ? 'selected' : ''}"/>>부서별</option>
						</select>
						<button type="button" class="btn03" onclick="javascript:fn_requestList()">검색</button>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="space10"></div>
	
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
				<td>매입집행금액</td>
				<td id="purchaseExecPrice">0</td>
				<td id="purchaseExecSurtax">0</td>
				<td><span id="purchaseExecTotPrice">0</span> (통장잔액 <span id="accountBalance"><fmt:formatNumber value="${accountBalance}" pattern="#,###"/></span>)</td>
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
		</colgroup>
		<thead>
			<c:if test="${searchVO.searchCondition == 'PRCS_PLACE'}">
				<tr>
					<th rowspan="2">매입처</th>
					<th rowspan="2">매입일</th>
					<th rowspan="2">과제명</th>
					<th rowspan="2">주매입부</th>
					<th rowspan="2">상품/서비스명</th>
					<th colspan="3">계획금액</th>
					<th colspan="3">집행금액</th>
					<th rowspan="2">결제 예정일</th>
					<th rowspan="2">결제일</th>
					<th rowspan="2">결제승인</th>
				</tr>
				<tr>
					<th>매입금액</th>
					<th>VAT</th>
					<th>Total</th>
					<th>매입금액</th>
					<th>VAT</th>
					<th>Total</th>
				</tr>
			</c:if>
			<c:if test="${searchVO.searchCondition == 'TASK_ID'}">
				<tr>
					<th rowspan="2">과제명</th>
					<th rowspan="2">매입처</th>
					<th rowspan="2">매입일</th>
					<th rowspan="2">주매입부</th>
					<th rowspan="2">상품/서비스명</th>
					<th colspan="3">계획금액</th>
					<th colspan="3">집행금액</th>
					<th rowspan="2">결제 예정일</th>
					<th rowspan="2">결제일</th>
					<th rowspan="2">결제승인</th>
				</tr>
				<tr>
					<th>매입금액</th>
					<th>VAT</th>
					<th>Total</th>
					<th>매입금액</th>
					<th>VAT</th>
					<th>Total</th>
				</tr>
			</c:if>
			<c:if test="${searchVO.searchCondition == 'MAIN_DEPT'}">
				<tr>
					<th rowspan="2">주매입부</th>
					<th rowspan="2">매입처</th>
					<th rowspan="2">매입일</th>
					<th rowspan="2">과제명</th>
					<th rowspan="2">상품/서비스명</th>
					<th colspan="3">계획금액</th>
					<th colspan="3">집행금액</th>
					<th rowspan="2">결제 예정일</th>
					<th rowspan="2">결제일</th>
					<th rowspan="2">결제승인</th>
				</tr>
				<tr>
					<th>매입금액</th>
					<th>VAT</th>
					<th>Total</th>
					<th>매입금액</th>
					<th>VAT</th>
					<th>Total</th>
				</tr>
			</c:if>
			<tr>
				<th colspan="5">총계</th>
				<th><fmt:formatNumber value="${resultList[0].prcsPlanPrice}" pattern="#,###"/></th>
				<th><fmt:formatNumber value="${resultList[0].prcsPlanSurtax}" pattern="#,###"/></th>
				<th><fmt:formatNumber value="${resultList[0].prcsPlanTotPrice}" pattern="#,###"/></th>
				<th><fmt:formatNumber value="${resultList[0].prcsExecPrice}" pattern="#,###"/></th>
				<th><fmt:formatNumber value="${resultList[0].prcsExecSurtax}" pattern="#,###"/></th>
				<th><fmt:formatNumber value="${resultList[0].prcsExecTotPrice}" pattern="#,###"/></th>
				<th></th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
<script>
	/* jsp 페이지 데이터 */
	var map = new Map();
</script>
			<c:set var="groupId" value="-1"/>
			<c:forEach var="result" items="${resultList}" varStatus="status">
				<c:if test="${result.orderDt == '9999/99/99'}"> <!-- 총계 -->
				</c:if>
				<c:if test="${result.orderDt == '0000/00/00'}"> <!-- 리스트 그룹 -->
					<c:set var="groupId" value="${groupId + 1}"/>
					<c:if test="${searchVO.searchCondition == 'PRCS_PLACE'}">
						<tr>
							<td style="font-weight: 800"><span id="group_list_${groupId}"><img src="<c:url value='/images/tis/control_close.png'/>" id="group_list_${groupId}_img" alt="" style="vertical-align: middle">&nbsp;<c:out value="${result.prcsPlace}"/></span></td>
							<td>-</td>
							<td>-</td>
							<td>-</td>
							<td>-</td>
							<td><fmt:formatNumber value="${result.prcsPlanPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanSurtax}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanTotPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecSurtax}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecTotPrice}" pattern="#,###"/></td>
							<td>-</td>
							<td>-</td>
							<td><input type="checkbox" name="group_list_all_check_${groupId}" id="group_list_all_check_${groupId}" class="k-checkbox" <c:out value="${result.payApproval == 'Y' ? 'checked' : ''}"/>><label class="k-checkbox-label" for="group_list_all_check_${groupId}"></label></td>
						</tr>
					</c:if>
					<c:if test="${searchVO.searchCondition == 'TASK_ID'}">
						<tr>
							<td style="font-weight: 800"><span id="group_list_${groupId}"><img src="<c:url value='/images/tis/control_close.png'/>" id="group_list_${groupId}_img" alt="" style="vertical-align: middle">&nbsp;<c:out value="${result.taskNm}"/></span></td>
							<td>-</td>
							<td>-</td>
							<td>-</td>
							<td>-</td>
							<td><fmt:formatNumber value="${result.prcsPlanPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanSurtax}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanTotPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecSurtax}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecTotPrice}" pattern="#,###"/></td>
							<td>-</td>
							<td>-</td>
							<td><input type="checkbox" name="group_list_all_check_${groupId}" id="group_list_all_check_${groupId}" class="k-checkbox" <c:out value="${result.payApproval == 'Y' ? 'checked' : ''}"/>><label class="k-checkbox-label" for="group_list_all_check_${groupId}"></label></td>
						</tr>
					</c:if>
					<c:if test="${searchVO.searchCondition == 'MAIN_DEPT'}">
						<tr>
							<td style="font-weight: 800"><span id="group_list_${groupId}"><img src="<c:url value='/images/tis/control_close.png'/>" id="group_list_${groupId}_img" alt="" style="vertical-align: middle">&nbsp;<c:out value="${result.deptNm}"/></span></td>
							<td>-</td>
							<td>-</td>
							<td>-</td>
							<td>-</td>
							<td><fmt:formatNumber value="${result.prcsPlanPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanSurtax}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanTotPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecSurtax}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecTotPrice}" pattern="#,###"/></td>
							<td>-</td>
							<td>-</td>
							<td><input type="checkbox" name="group_list_all_check_${groupId}" id="group_list_all_check_${groupId}" class="k-checkbox" <c:out value="${result.payApproval == 'Y' ? 'checked' : ''}"/>><label class="k-checkbox-label" for="group_list_all_check_${groupId}"></label></td>
						</tr>
					</c:if>
				</c:if>
				<c:if test="${result.orderDt != '0000/00/00'  && result.orderDt != '9999/99/99'}"> <!-- 리스트 아이템 -->
					<c:if test="${searchVO.searchCondition == 'PRCS_PLACE'}">
						<tr class="group_list_${groupId}">
							<td><c:out value="${result.prcsPlace}"/></td>
							<td><c:out value="${result.orderDt}"/></td>
							<td><c:out value="${result.taskNm}"/></td>
							<td><c:out value="${result.deptNm}"/></td>
							<td><c:out value="${result.prdtNm}"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanSurtax}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanTotPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecSurtax}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecTotPrice}" pattern="#,###"/></td>
							<td><c:out value="${result.payPlanDate}"/></td>
							<td><c:out value="${result.payDate}"/></td>
							<td><input type="checkbox" name="group_list_check_${groupId}_${result.purchaseId}" id="group_list_check_${groupId}_${result.purchaseId}" value="${result.purchaseId}" class="k-checkbox" <c:out value="${result.payApproval == 'Y' ? 'checked' : ''}"/>><label class="k-checkbox-label" for="group_list_check_${groupId}_${result.purchaseId}"></label></td>
						</tr>
					</c:if>
					<c:if test="${searchVO.searchCondition == 'TASK_ID'}">
						<tr class="group_list_${groupId}">
							<td><c:out value="${result.taskNm}"/></td>
							<td><c:out value="${result.prcsPlace}"/></td>
							<td><c:out value="${result.orderDt}"/></td>
							<td><c:out value="${result.deptNm}"/></td>
							<td><c:out value="${result.prdtNm}"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanSurtax}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanTotPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecSurtax}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecTotPrice}" pattern="#,###"/></td>
							<td><c:out value="${result.payPlanDate}"/></td>
							<td><c:out value="${result.payDate}"/></td>
							<td><input type="checkbox" name="group_list_check_${groupId}_${result.purchaseId}" id="group_list_check_${groupId}_${result.purchaseId}" value="${result.purchaseId}" class="k-checkbox" <c:out value="${result.payApproval == 'Y' ? 'checked' : ''}"/>><label class="k-checkbox-label" for="group_list_check_${groupId}_${result.purchaseId}"></label></td>
						</tr>
					</c:if>
					<c:if test="${searchVO.searchCondition == 'MAIN_DEPT'}">
						<tr class="group_list_${groupId}">
							<td><c:out value="${result.deptNm}"/></td>
							<td><c:out value="${result.prcsPlace}"/></td>
							<td><c:out value="${result.orderDt}"/></td>
							<td><c:out value="${result.taskNm}"/></td>
							<td><c:out value="${result.prdtNm}"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanSurtax}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsPlanTotPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecPrice}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecSurtax}" pattern="#,###"/></td>
							<td><fmt:formatNumber value="${result.prcsExecTotPrice}" pattern="#,###"/></td>
							<td><c:out value="${result.payPlanDate}"/></td>
							<td><c:out value="${result.payDate}"/></td>
							<td><input type="checkbox" name="group_list_check_${groupId}_${result.purchaseId}" id="group_list_check_${groupId}_${result.purchaseId}" value="${result.purchaseId}" class="k-checkbox" <c:out value="${result.payApproval == 'Y' ? 'checked' : ''}"/>><label class="k-checkbox-label" for="group_list_check_${groupId}_${result.purchaseId}"></label></td>
						</tr>
					</c:if>
<script>
	var mapData = new Map();
	mapData.put("price", ${result.prcsExecPrice});
	mapData.put("surtax", ${result.prcsExecSurtax});
	mapData.put("totPrice", ${result.prcsExecTotPrice});
	map.put("group_list_check_${groupId}_${result.purchaseId}", mapData);
</script>
				</c:if>
			</c:forEach>
			<c:if test="${fn:length(resultList) <= 1}">
				<tr> 
					<td colspan="100%">
						<spring:message code="common.nodata.msg" />
					</td>
				</tr>   	          				 			   
			</c:if>
		</tbody>
	</table>	
	<div class="space20"></div>
	
	</form>
</div>
</body>

<script>
	$(document).ready(function(){
		fn_setGroupToggleCallback(fn_groupToggleCallback);
		fn_setOneItemCheckedCallback(fn_oneItemChecked);
		fn_setAllItemCheckedCallback(fn_allItemChecked);
		fn_initGroupList();
		fn_calcPrice();
	});

	function fn_downloadExcel() {
		document.frm.action = "<c:url value='/com/financial/purchase/purchaseManageApprovalListExcelDownload.do'/>";
		document.frm.submit();
	}
	
	function fn_groupToggleCallback(groupId, visibility) {
		//alert("id=" + id + ", visibility=" +visibility);
		
		if(visibility) {
			$("#" + groupId + "_img").attr("src", "<c:url value='/images/tis/control_close.png'/>");
		} else {
			$("#" + groupId + "_img").attr("src", "<c:url value='/images/tis/control_open.png'/>");
		}
	}
	
	function fn_oneItemChecked(name, checked, value) {
		//alert("name=" + name + ", checked=" + checked + ", value=" + value);
		
		fn_calcPrice();
		fn_approval(value, checked);
	}
	
	function fn_allItemChecked(checked, value) {
		//alert("checked=" + checked + ", value=" + value);
		
		fn_calcPrice();
		fn_approval(value, checked);
	}
	
	function fn_calcPrice() {
		var priceSum = 0;
		var surtaxSum = 0;
		var totPriceSum = 0;
		
		$('input[id ^= "group_list_check_"]').each(function() {
			var id = $(this)[0].id;
			var checkbox = $("#" + id)[0];
			if(checkbox.checked) {
				priceSum += map.get(id).get("price");
				surtaxSum += map.get(id).get("surtax");
				totPriceSum += map.get(id).get("totPrice");
			}
		});
		
		$('#purchaseExecPrice').text(fn_numberWithCommas(priceSum));
		$('#purchaseExecSurtax').text(fn_numberWithCommas(surtaxSum));
		$('#purchaseExecTotPrice').text(fn_numberWithCommas(totPriceSum));
	}
	
	function fn_numberWithCommas(x) {
	    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
	
	function fn_requestList() {
		document.frm.action = "<c:url value='/com/financial/purchase/purchaseManageApprovalList.do'/>";
		document.frm.submit();
	}

	function fn_approval(purchaseIds, checked) {
		var payApproval = (checked == true) ? 'Y' : 'N';
		$.ajax({
			url : '<c:url value="/com/financial/purchase/purchaseManageApproval.do"/>',
			data : {purchaseId:purchaseIds, payApproval:payApproval},
			dataType : 'json',
			type : 'POST',
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success : function(data) {
			}
		});
	}
	
	$(document).ready(function() {
		fn_initSearchDatePicker("searchOrderSdate", "searchOrderEdate");
	});
</script>