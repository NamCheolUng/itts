<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>

$(document).ready(function(){
	var f = document.frm;
	
	if("${epayDraftInfoVO.draftInfoId}" != "") {
		f.act.value = 'update';
	}
	
	
	var dataPickerS = $("#dpkrSdate").data("kendoDatePicker");
	
	var dataPickerE = $("#dpkrSdate").data("kendoDatePicker");
	
	
	$("#dpkrEdate").kendoDatePicker({
		numberOfMonths: 2,
		onSelect: function(selected) {
			$("#dpkrSdate").datepicker("option","maxDate", selected)
		}
	});
});

var dateEditor = function (container, options) {
	var input = $('<input />');

	input.appendTo(container)
	  .kendoDatePicker({
	    format: "dd.MM.yyyy"
	  });
	var datePicker = input.data("kendoDatePicker");
	  switch (options.field) {
	    case "startDate":
	      if (options.model.finishDate) {
	        datePicker.max(options.model.finishDate);
	      }
	      break;

	    case "finishDate":
	      if (options.model.startDate) {
	        datePicker.min(options.model.startDate);
	      }
	      break;
	  }
	};

function fn_requestCostManageList(){
	document.frm.method.value = 'payment';
	document.frm.action = "<c:url value='/com/financial/cost/costManageList.do'/>";
	document.frm.submit();
}

function btn_submit(apprType){
		
	var f = document.frm;
	
	if(f.expDate.value=="") {
		alert("사용일을 입력하세요.");
		f.expDate.focus();
		return false;
	}
	
	if(f.prdtNm.value=="") {
		alert("상품/서비스명을 입력하세요.");
		f.prdtNm.focus();
		return false;
	}
	
	if(f.prcsPlace.value=="") {
		alert("지출처를 입력하세요.");
		f.prcsPlace.focus();
		return false;
	}
	
	if(f.expTotPrice.value=="") {
		alert("Total(금액)을 입력하세요.");
		f.expTotPrice.focus();
		return false;
	}
	
	f.action = "<c:url value='/com/financial/cost/costManageWriteUpdate.do'/>";
	f.submit();
}

function fn_cancel() {
	document.frm.action = "<c:url value='/com/financial/cost/costManageList.do'/>";
	document.frm.submit();
}
</script>

<form name="frm" method="post">
	<input type="hidden" name="costId" value="${costVO.costId}">
	<input type="hidden" name="affiliationId" value="${costVO.affiliationId}">
	
	<div id="subwrap">
		<div class="space10"></div>
		<h4><i class="fas fa-dot-circle"></i>경비현황 상세보기</h4>
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
					<td colspan="3"><c:out value="${costVO.taskNm}"/></td>
				</tr>
				<tr>
					<th>담당자</th>
					<td colspan="3">${costVO.userNm}</td>
				</tr>
				<tr>
					<th>사용일</th>
					<td><input name="expDate" class="datepicker width200" value='<c:out value="${costVO.expDate}"/>' placeholder="사용일" title="사용일">
					</td>
					<th>주매출부</th>
					<td>
						<select name="deptCode" class="select width150">
							<option value=""<c:out value="${costVO.deptCode == null ? 'selected' : ''}"/>>부서 전체</option>
							<c:forEach var="dept" items="${deptList}" varStatus="status">	
								<option value="${dept.code}" <c:out value="${dept.code == costVO.deptCode ? 'selected' : ''}"/>>${dept.codeNm}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>상품/서비스명</th>
					<td><input name="prdtNm" type="text" class="k-textbox width100p" value='<c:out value="${costVO.prdtNm}"/>'></td>
					<th>지출처</th>
					<td><input name="prcsPlace" type="text" class="k-textbox width100p" value='<c:out value="${costVO.prcsPlace}"/>'></td>
				</tr>
				<tr>
					<th>결제예정일</th>
					<td><input name="payPlanDate" class="datepicker width200" value='<c:out value="${costVO.payPlanDate}"/>' placeholder="결제예정일" title="결제예정일">
					</td>
					<th>결제일</th>
					<td><input name="payDate" class="datepicker width200" value='<c:out value="${costVO.payDate}"/>' placeholder="결제일" title="결제일">
					</td>
				</tr>
				<tr>
					<th>Total(금액)</th>
					<td><input name="expTotPrice" type="text" class="k-textbox width100p" value='<c:out value="${costVO.expTotPrice}"/>'></td>
					<th>결제여부</th>
					<td>
						<select name="payAt" class="select width150">
							<option value="1" <c:out value="${costVO.payAt != '1' ? 'selected' : ''}"/>>미수</option>
							<option value="2" <c:out value="${costVO.payAt == '2' ? 'selected' : ''}"/>>완료</option>
						</select>
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
<script>
$(document).ready( function() {
});
</script>
