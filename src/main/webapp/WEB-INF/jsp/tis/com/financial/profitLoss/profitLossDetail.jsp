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
	
	function fn_viewList() {
		document.frm.action = "<c:url value='profitLossList.do'/>";
		document.frm.submit();
	}

	function fn_viewTaskDetail() {
		document.frm.action = "<c:url value='/com/task/taskDetail.do'/>";
		document.frm.submit();
	}
</script>

<form name="frm" method="post">
	<input type="hidden" name="taskId" value="${taskVO.taskId}">
	<input type="hidden" name="pageIndex" value="${pageIndex}">
	<input type="hidden" name="affiliationId" value="${affiliationId}">
	
	<div id="subwrap">

		<div class="btngroup fl">
			<button type="button" class="btn04" onclick="javascript:fn_viewList();">목록</button>
		</div>
		
		<div class="space10"></div>
		
		<div class="btngroup fl">
			<h4><i class="fas fa-dot-circle"></i>과제개요</h4>
		</div>
		
		<div class="btngroup fr">
			<button type="button" class="btn03" onclick="javascript:fn_viewTaskDetail();">상세보기</button>
		</div>
		
		<table class="tablewrite">
			<colgroup>
				<col class="width180">
				<col class="width600">
				<col class="width180">
				<col class="width600">
			</colgroup>
			<tbody>
				<tr>
					<th>과제명</th>
					<td colspan="3"><c:out value='${taskVO.taskNm}'/></td>
				</tr>
				<tr>
					<th>발주처</th>
					<td><c:out value="${taskVO.cmpnyNm }"/></td>
					<th>사업기간</th>
					<td><c:out value="${taskVO.taskSdate }"/>~<c:out value="${taskVO.taskEdate }"/></td>
				</tr>
				<tr>
					<th>사업범위</th>
					<td colspan="3"><c:out value='${taskVO.chargerNm}'/></td>
				</tr>
			</tbody>
		</table>
		
		<div class="space20"></div>
		
		<h4><i class="fas fa-dot-circle"></i>손익계산</h4>
		
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
			</colgroup>
			<thead>
				<tr>
					<th colspan="4">이익</th>
					<th colspan="5">지출</th>
					<th rowspan="3">영업이익</th>
				</tr>
				<tr>
					<th colspan="2">매출</th>
					<th rowspan="2">영업외이익</th>
					<th rowspan="2">이익합계</th>
					<th colspan="2">매입</th>
					<th rowspan="2">인건비</th>
					<th rowspan="2">경비</th>
					<th rowspan="2">지출합계</th>
				</tr>
				<tr>
					<th>계획금액</th>
					<th>집행금액</th>
					<th>계획금액</th>
					<th>집행금액</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="businessProfitColor" value="000000"/>
				<c:if test="${profitLossList[0].businessProfit > 0}">
					<c:set var="businessProfitColor" value="FF0000"/>
				</c:if>
				<c:if test="${profitLossList[0].businessProfit < 0}">
					<c:set var="businessProfitColor" value="0000FF"/>
				</c:if>
			
				<tr>
					<td><fmt:formatNumber value="${profitLossList[0].salesPlanTotPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${profitLossList[0].salesExecTotPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${profitLossList[0].salesOthersPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${profitLossList[0].profitTotPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${profitLossList[0].prcsPlanTotPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${profitLossList[0].prcsExecTotPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${profitLossList[0].laborExecPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${profitLossList[0].outgoingsPrice}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${profitLossList[0].outgoingsTotPrice}" pattern="#,###"/></td>
					<td><font color="${businessProfitColor}"><fmt:formatNumber value="${profitLossList[0].businessProfit}" pattern="#,###"/></font></td>
				</tr>
			</tbody>
		</table>
		
		<div class="space20"></div>
		
		<h4><i class="fas fa-dot-circle"></i>매출내역</h4>
		
		<table class="tablelist">
			<colgroup>
				<col class="width50">
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
					<th rowspan="2">No</th>
					<th rowspan="2">발주일</th>
					<th rowspan="2">주매출부</th>
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
			<c:forEach var="result" items="${salesList}" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td><c:out value="${result.orderDt}"/></td>
					<td><c:out value="${result.deptNm}"/></td>
					<td><c:out value="${result.prdtNm}"/></td>
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
			</c:forEach>
			</tbody>
		</table>
				
		<div class="space20"></div>
		
		<h4><i class="fas fa-dot-circle"></i>매입내역</h4>
		
		<table class="tablelist">
			<colgroup>
				<col class="width50">
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
					<th rowspan="2">No</th>
					<th rowspan="2">발주일</th>
					<th rowspan="2">주매출부</th>
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
					<th>매입금액</th>
					<th>VAT</th>
					<th>Total</th>
					<th>매입금액</th>
					<th>VAT</th>
					<th>Total</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="result" items="${purchaseList}" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td><c:out value="${result.orderDt}"/></td>
					<td><c:out value="${result.deptNm}"/></td>
					<td><c:out value="${result.prdtNm}"/></td>
					<td><c:out value="${result.cmpnyNm}"/></td>
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
			</c:forEach>
			</tbody>
		</table>
						
		<div class="space20"></div>
		
		<h4><i class="fas fa-dot-circle"></i>경비내역</h4>
		
		<table class="tablelist">
			<colgroup>
				<col class="width50">
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
					<th>No</th>
					<th>사용일자</th>
					<th>과제명(건명)</th>
					<th>주매출부</th>
					<th>담당자</th>
					<th>상품/서비스명</th>
					<th>매입처</th>
					<th>지출금액</th>
					<th>결제예정일</th>
					<th>결제일</th>
					<th>결제여부</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="result" items="${costList}" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${result.expDate}</td>
					<td><c:out value="${result.deptNm}"/></td>
					<td><c:out value="${result.taskNm}"/></td>
					<td>${result.userNm}</td>
					<td><c:out value="${result.prdtNm}"/></td>
					<td><c:out value="${result.prcsPlace}"/></td>
					<td><fmt:formatNumber value="${result.expTotPrice}" pattern="#,###"/></td>
					<td>${result.payPlanDate}</td>
					<td>${result.payDate}</td>
					<td>${result.payAt}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
								
		<div class="space20"></div>
		
		<h4><i class="fas fa-dot-circle"></i>인건비내역</h4>
		
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
				<tr>
					<th rowspan="3">부서</th>
					<th rowspan="3">직위</th>
					<th rowspan="3">성명</th>
					<th rowspan="3">역할</th>
					<th colspan="3">근무시간</th>
					<th rowspan="3">업무지연</th>
					<th rowspan="3">투입인건비</th>
				</tr>
				<tr>
					<th rowspan="2">기본근무시간</th>
					<th colspan="2">초과근무시간</th>
				</tr>
				<tr>
					<th>야간근무</th>
					<th>주말근무</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="result" items="${laborCostList}" varStatus="status">
				<tr>
					<td><c:out value="${result.ofcpsNm}"/></td>
					<td><c:out value="${result.ofcpsNm}"/></td>
					<td><c:out value="${result.userNm}"/></td>
					<td>
						<c:choose>
							<c:when test="${taskPer.role eq 'M' }">
								과제담당자
							</c:when>
							<c:otherwise>
								과제참여자
							</c:otherwise>
						</c:choose>
					</td>
					<td>${result.workHour}</td>
					<td>${result.nightWork}</td>
					<td>${result.weekWork}</td>
					<td>${result.delay}</td>
					<td><fmt:formatNumber value="${result.taskPersonLaborCost}" pattern="#,###"/></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</form>

</body>
</html>