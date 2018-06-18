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
		document.frm.action = "<c:url value='/com/manager/epay/managerEpayDraftInfoList.do'/>";
		document.frm.submit();
	}
	
	function fn_requestDraftSalesUpdate(salesId){
		document.frm.salesId.value = salesId;
		document.frm.action = "<c:url value='/com/manager/epay/managerEpaySalesUpdate.do'/>";
		document.frm.submit();
	}
	
	function fn_requestDraftPurchaseUpdate(purchaseId){
		document.frm.purchaseId.value = purchaseId;
		document.frm.action = "<c:url value='/com/manager/epay/managerEpayPurchaseUpdate.do'/>";
		document.frm.submit();
	}
	
	function fn_registerFinancial() {
		var draftInfoId = '${draftInfoId}';
		var affiliationId = document.frm.affiliationId.value;
		var apprSttus = document.frm.apprSttus.value;
		
		$.ajax({
			url : '<c:url value="/com/manager/epay/managerFinancialRegister.do"/>',
			data : {draftInfoId: draftInfoId, affiliationId:affiliationId, apprSttus:apprSttus},
			dataType : 'json',
			type : 'POST',
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success : function(data) {
				if(data.result == 'success'){
					alert("회계등록이 되었습니다.");
					window.location.reload(true);
		        }else{
		        	alert("회계등록이 실패하였습니다.");
		        }
				return;
			},
			error : function(request, status, error ) { 
				//console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				alert("회계등록 중 에러가 발생하였습니다.");
			},
		});
	}
</script>
<form name="frm" method="post">
<input type="hidden" name="draftInfoId" value="${draftInfoId}"/>
<input type="hidden" name="salesId" value=""/>
<input type="hidden" name="purchaseId" value=""/>
<input type="hidden" name="apprSttus"  value='<c:out value="${cnsul.apprSttus}"/>'>

<!-- 소속 선택 -->
<div class="modal fade" id="modal_affiliation" tabindex="-1" role="dialog" aria-labelledby="modal02Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="modal02Label">회계등록</h4>
			</div>
			<div class="modal-body">
				<table class="tablewrite">
					<colgroup>
						<col>
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th>등록할 소속</th>
							<td>
								<select name="affiliationId" class="select width150">
									<option value="I">이튜</option>
									<option value="S">에스메이커</option>
								</select>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn02" data-dismiss="modal">취소</button>
				<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_registerFinancial();">저장</button>
			</div>
		</div>
	</div>
</div>

<div id="subwrap">
	<div class="btngroup fl">		
		<button type="button" class="btn04" onclick="javascript:fn_requestDraftList();">목록</button>
	</div>
	<c:if test="${cnsul.apprSttus == '1' || cnsul.apprSttus == '4'}">
		<div class="btngroup fr">
			<%-- <button type="button" class="btn01" onclick="javascript:fn_registerFinancial('${draftInfoId}')">회계등록</button> --%>
			<button type="button" class="btn01" data-toggle="modal" data-target="#modal_affiliation">회계등록</button>
		</div>
	</c:if>
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
					<td><a href="javascript:fn_requestDraftSalesUpdate('${sales.salesId}')">${sales.prdtNm}</a></td>
					<td>${sales.cmpnyNm}</td>
					<%-- <td><input class="salesPrice" value="<fmt:formatNumber value="${sales.salesPlanTotPrice}" pattern="#,###"/>" readonly/></td> --%>
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
					<td><a href="javascript:fn_requestDraftPurchaseUpdate('${purchase.purchaseId}')">${purchase.prdtNm}</a></td>
					<td>${purchase.cmpnyNm}</td>
					<td class="purchasePrice"><fmt:formatNumber value="${purchase.prcsPlanTotPrice}" pattern="#,###"/></td>
					<%-- <td><input class="purchasePrice" value="<fmt:formatNumber value="${purchase.prcsPlanTotPrice}" pattern="#,###"/>" readonly="readonly"/></td> --%>
					<td>${purchase.payPlanDate}</td>
					<td>${purchase.tradeSeptNm}</td>
					<td>${purchase.payPoint}</td>
					<td>${purchase.payWay}</td>
				</tr>
			</tbody>
		</c:forEach>
	</table>

	<div class="space20"></div>
		
	<div class="space20"></div>
	<div class="btngroup fl">		
		<button type="button" class="btn04" onclick="javascript:fn_requestDraftList();">목록</button>
	</div>
	<div class="btngroup fr">
		<%-- <button type="button" class="btn01" onclick="location.href='<c:url value ='/draft/draftModify.do?method=payment'/>'">기안수정</button> --%>
		<%-- <button type="button" class="btn01" onclick="location.href='<c:url value ='/com/epay/draft/epayCnsulWrite.do'/>?draftInfoId=${draftInfoId}'">기안수정</button>
		<button type="button" class="btn02" onclick="location.href='<c:url value ='/draft/draftInfoList.do?method=payment'/>'">기안취소</button> --%>
	</div>
	<div class="space20"></div>
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