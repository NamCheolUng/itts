<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
    $(document).ready(function() {
        $("#monthyearpicker").kendoDatePicker({
            start: "year",
            depth: "year",
            format: "MM/yyyy",
            value: '<c:out value="${tbBankcardmanageVO.endPnttm}"/>',
            dateInput: true
        });
    });
    
    function fn_GotoUpdate(){
    	document.subForm.bankNm.value =  $("#bankselect option:selected").text();
    	document.subForm.action = "<c:url value='/com/financial/bankBook/cardUpdate.do?affiliationId=S'/>";
    	document.subForm.submit();
    }
    
    function fn_GotoView(){
    	document.subForm.action = "<c:url value='/com/financial/bankBook/cardView.do?affiliationId=S'/>";
    	document.subForm.submit();
    }
</script>



<div id="subwrap">
<h1>통장/카드관리 >수정</h1>
	<div class="tabmenu">
		<ul>
			<li><a href="<c:url value='/com/financial/bankBook/bankBook.do?affiliationId=S'/>">통장관리</a></li>
			<li class="active"><a href="<c:url value='/com/financial/bankBook/card.do?affiliationId=S'/>">카드관리</a></li>
		</ul>
	</div>
<form name="subForm" method="post">	
	<input type="hidden" name="bankNm">
		<table class="tableview">
			<colgroup>
				<col class="width180">
				<col class="width600">
				<col class="width180">
				<col class="width600">
			</colgroup>
			<tbody>
				<tr>
					<th>카드번호</th>
					<td><input type="text" readonly="readonly" name="cardNum" class="k-textbox width100p" value='<c:out value="${tbBankcardmanageVO.cardNum}"/>'></td>
					<th>담당자</th>
					<td>
					<select name="depositorNm" class="select width150">
          				<c:forEach var="result" items="${emplyr_list}" varStatus="status">
							<option value="${result.emplNo}" <c:if test="${result.emplNo == tbBankcardmanageVO.depositorNm}">selected</c:if> >${result.userNm}(${result.emplNo})</option>
						</c:forEach>
          			</select>					
					</td>
				</tr>
				<tr>
					<th>카드명</th>
					<td><input type="text" readonly="readonly" class="k-textbox width100p" name="cardNm" value='<c:out value="${tbBankcardmanageVO.cardNm}"/>'></td>
					<th>결제계좌명</th>
					<td>
					<select class="select width150" id="bankselect" name="bankAccountNum">
		          	<c:forEach var="result" items="${bank_list}" varStatus="status">
						<option value="${result.bankAccountNum}" <c:if test="${result.bankAccountNum == tbBankcardmanageVO.bankAccountNum}">selected</c:if> >${result.bankNm}</option>
					</c:forEach>
		          </select>
					</td>
				</tr>
				<tr>
					<th>만료일자</th>
					<td><input id="monthyearpicker" class="datepicker width200" name="endPnttm" value='<c:out value="${tbBankcardmanageVO.endPnttm}"/>'></td>
					<th>사용여부</th>
					<td><input type="radio" name="useAt" value="Y" <c:if test="${tbBankcardmanageVO.useAt == 'Y'}">checked</c:if>> <label for="contactChoice1">사용</label>
						<input type="radio" name="useAt" value="N" <c:if test="${tbBankcardmanageVO.useAt != 'Y'}">checked</c:if>> <label for="contactChoice2">미사용</label></td>
				</tr>
				<tr>
					<th>회사구분</th>
					<td colspan="3"><input type="radio" name="cpCode" value="I" <c:if test="${tbBankcardmanageVO.cpCode == 'I'}">checked</c:if>> <label for="contactChoice1">이튜</label>
									<input type="radio" name="cpCode" value="S" <c:if test="${tbBankcardmanageVO.cpCode == 'S'}">checked</c:if>> <label for="contactChoice2">에스메이커</label></td>
				</tr>
				<tr>
					<th>비고</th>
					<td colspan="3"><textarea class="k-textbox width100p height100" name="rm"><c:out value="${tbBankcardmanageVO.rm}"/></textarea></td>
				</tr>
			</tbody>
		</table>
</form>		
	<div class="space20"></div>
	<div class="btngroup fl">
		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/financial/bankBook/card.do?affiliationId=S'/>'">목록</button>
	</div>
	<div class="btngroup fr">
		<button type="button" class="btn02" onclick="javascript:fn_GotoView();">취소</button>
		<button type="button" class="btn01" onclick="javascript:fn_GotoUpdate();" >저장</button>
	</div>
	<div class="space20"></div>
	<!-- 관라지만 노출 //-->
</div>


</body>
</html>