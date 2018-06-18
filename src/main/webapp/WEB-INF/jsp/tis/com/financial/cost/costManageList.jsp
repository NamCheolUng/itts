<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script>
	var expTotPrice = new Array();
</script>

<div id="subwrap">
	<form name="frm" id="frm" method="post">
	<input type="hidden" name="costId" value="">
	
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
						<select name="searchPayAt" class="select width150">
							<option value="" <c:out value="${searchVO.searchPayAt == null ? 'selected' : ''}"/>>결제여부 전체</option>
							<option value="2" <c:out value="${searchVO.searchPayAt == '2' ? 'selected' : ''}"/>>완료</option>
							<option value="1" <c:out value="${searchVO.searchPayAt == '1' ? 'selected' : ''}"/>>미수</option>
						</select>
						&nbsp;&nbsp;
						사용일
						<input name="searchExpSdate" id="searchExpSdate" class="datepicker width120" value="${searchVO.searchExpSdate}">
						~
						<input name="searchExpEdate" id="searchExpEdate" class="datepicker width120" value="${searchVO.searchExpEdate}">
						&nbsp;&nbsp;
						<select name="searchCondition" class="select width150">
							<option value="" <c:out value="${searchVO.searchCondition == null || searchVO.searchCondition == '' ? 'selected' : ''}"/>>전체</option>
							<option value="taskNm" <c:out value="${searchVO.searchCondition == 'taskNm' ? 'selected' : ''}"/>>과제명(건명)</option>
							<option value="prdtNm" <c:out value="${searchVO.searchCondition == 'prdtNm' ? 'selected' : ''}"/>>상품/서비스명</option>
							<option value="prcsPlace" <c:out value="${searchVO.searchCondition == 'prcsPlace' ? 'selected' : ''}"/>>지출처</option>
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
	
	<button type="button" class="btn01 fr" onclick="javascript:fn_requestCostManageApprovalList();">경비정산</button>
	
	<h4><i class="fas fa-dot-circle"></i>경비현황</h4>
	
	<table class="tablelist">
		<thead>
			<tr>
				<th class="width200"></th>
				<th>공급가</th>
				<th>VAT</th>
				<th>Total</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>경비</td>
				<td id="expPrice">0</td>
				<td id="expSurtax">0</td>
				<td id="expTotPrice">0</td>
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
		</colgroup>
		<thead>
			<tr>
				<th><input type="checkbox" id="sel_all" class="k-checkbox" onclick="fn_checkOrUncheckAll();"><label class="k-checkbox-label" for="sel_all"></label></th>
				<th>사용일</th>
				<th>주매입부</th>
				<th>과제명(건명)</th>
				<th>담당자</th>
				<th>상품/서비스명</th>
				<th>지출처</th>
				<th>Total</th>
				<th>결제예정일</th>
				<th>결제일</th>
				<th>결제여부</th>
			</tr>
		</thead>
		<tbody>		
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td><input type="checkbox" name="item" id="sel_${status.index}" class="k-checkbox" onclick="fn_calcPrice();"><label class="k-checkbox-label" for="sel_${status.index}"></label></td>
				<td>${result.expDate}</td>
				<td><c:out value="${result.deptNm}"/></td>
				<td><c:out value="${result.taskNm}"/></td>
				<td>${result.userNm}</td>
				<td><a href="javascript:fn_requestCostManageView('${result.costId}')"><c:out value="${result.prdtNm}"/></a></td>
				<td><c:out value="${result.prcsPlace}"/></td>
				<td><fmt:formatNumber value="${result.expTotPrice}" pattern="#,###"/></td>
				<td>${result.payPlanDate}</td>
				<td>${result.payDate}</td>
				<td>${result.payAt}</td>
			</tr>
<script>
			expTotPrice[${status.index}] = ${result.expTotPrice > 0 ? result.expTotPrice : 0};
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
	document.frm.action = "<c:url value='/com/financial/cost/costManageList.do'/>";
	document.frm.submit();
}

function fn_requestCostManageView(costId) {
	document.frm.costId.value = costId;
	document.frm.action = "<c:url value='/com/financial/cost/costManageView.do'/>";
	document.frm.submit();
}

function fn_requestCostManageApprovalList() {
	document.frm.action = "<c:url value='/com/financial/cost/costManageApprovalList.do'/>";
	document.frm.submit();
}

function fn_searchKeyPressed(event) {
	if (event.keyCode==13) {
		fn_requestList('1');
	}
}

function fn_downloadExcel() {
	document.frm.action = "<c:url value='/com/financial/cost/costManageListExcelDownload.do'/>";
	document.frm.submit();
}

var checkedAll = false;

function fn_checkOrUncheckAll() {
	if(!checkedAll) {
		fn_checkAll()
	} else {
		fn_uncheckAll();
	}
}

function fn_checkAll() {
	var f = document.frm;
	
	if(f.item.length == null) { // item 이 한건 이면
		f.item.checked = true;
	} else { // item 이 두건 이상이면
		for(i=0; i < f.item.length; i++) {
			f.item[i].checked = true;
		}
	}
	checkedAll = true;
	
	fn_calcPrice();
}

function fn_uncheckAll() {
	var f = document.frm;
	
	if(f.item.length == null) { // item 이 한건 이면
		f.item.checked = false;
	} else { // item 이 두건 이상이면
		for(i=0; i < f.item.length; i++) {
			f.item[i].checked = false;
		}
	}
	checkedAll = false;
	
	fn_calcPrice();
}

function fn_calcPrice() {
	var prcsPlanPriceSum = 0;
	var prcsPlanSurtaxSum = 0;
	var expTotPriceSum = 0;
	
	var f = document.frm;
	if(f.item.length == null) { // item 이 한건 이면
		if(f.item.checked) {
			expTotPriceSum = expTotPrice[0];
		}
	} else { // item 이 두건 이상이면
		for(i=0; i < f.item.length; i++) {
			if(f.item[i].checked) {
				expTotPriceSum += expTotPrice[i];
			}
		}
	}
	
	var expPrice = Math.round(expTotPriceSum / 1.1);
	$('#expPrice').text(fn_numberWithCommas(expPrice));
	$('#expSurtax').text(fn_numberWithCommas(expTotPriceSum - expPrice));
	$('#expTotPrice').text(fn_numberWithCommas(expTotPriceSum));
}

function fn_numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


$(document).ready(function() {
	fn_initSearchDatePicker("searchExpSdate", "searchExpEdate");
});

</script>