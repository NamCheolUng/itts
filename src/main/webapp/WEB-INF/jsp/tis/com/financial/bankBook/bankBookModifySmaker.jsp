<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">
function fn_GotoUpdate(){
	document.subForm.action = "<c:url value='/com/financial/bankBook/bankBookUpdate.do'/>";
	document.subForm.submit();
}

function fn_GotoView(){
	document.subForm.action = "<c:url value='/com/financial/bankBook/bankBookView.do?affiliationId=S'/>";
	document.subForm.submit();
}

</script>


<div id="subwrap">
<h1>통장/통장관리 >수정</h1>
	<div class="tabmenu">
		<ul>
			<li class="active"><a href="<c:url value='/com/financial/bankBook/bankBook.do?affiliationId=S'/>">통장관리</a></li>
			<li><a href="<c:url value='/com/financial/bankBook/card.do?affiliationId=S'/>">카드관리</a></li>
		</ul>
	</div>
	<form name="subForm" method="post">
		<table class="tableview">
			<colgroup>
				<col class="width180">
				<col class="width600">
				<col class="width180">
				<col class="width600">
			</colgroup>
			<tbody>
				<tr>
					<th>계좌번호</th>
					<td><input type="text" class="k-textbox width100p" readonly="readonly" name="bankAccountNum" value='<c:out value="${tbBankbookmanageVO.bankAccountNum}"/>'></td>
					<th>은행명(계좌명)</th>
					<td><input type="text" class="k-textbox width100p" readonly="readonly" name="bankNm" value='<c:out value="${tbBankbookmanageVO.bankNm}"/>'></td>
				</tr>
				<tr>
					<th>예금주</th>
					<td><input type="text" class="k-textbox width100p" name="depositorNm" value='<c:out value="${tbBankbookmanageVO.depositorNm}"/>'></td>
					<th>잔액</th>
					<td><input type="text" class="k-textbox width100p" name="balance" value='<c:out value="${tbBankbookmanageVO.balance}"/>'></td>
				</tr>
				<tr>
					<th>계정명</th>
					<td><input type="text" class="k-textbox width100p" name="division" value='<c:out value="${tbBankbookmanageVO.division}"/>'></td>
					<th>사용여부</th>
					<td><input type="radio" name="useAt" value="Y" <c:if test="${tbBankbookmanageVO.useAt == 'Y'}">checked</c:if> > <label for="contactChoice1">사용</label>
						<input type="radio" name="useAt" value="N" <c:if test="${tbBankbookmanageVO.useAt != 'Y'}">checked</c:if> > <label for="contactChoice2">미사용</label></td>
				</tr>
				<tr>
					<th>회사구분</th>
					<td colspan="3"><input type="radio" name="cpCode" value="I" <c:if test="${tbBankbookmanageVO.cpCode == 'I'}">checked</c:if>> <label for="contactChoice1">이튜</label>
									<input type="radio" name="cpCode" value="S" <c:if test="${tbBankbookmanageVO.cpCode == 'S'}">checked</c:if>> <label for="contactChoice2">에스메이커</label></td>
				</tr>
				<tr>
					<th>비고</th>
					<td colspan="3"><textarea class="k-textbox width100p height100" name="rm"><c:out value="${tbBankbookmanageVO.rm}"/></textarea></td>
				</tr>
			</tbody>
		</table>
	</form>	
	<div class="space20"></div>
	<div class="btngroup fl">
		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/financial/bankBook/bankBook.do?affiliationId=S'/>'">목록</button>
	</div>
	<div class="btngroup fr">
		<button type="button" class="btn02" onclick="javascript:fn_GotoView();">취소</button>
		<button type="button" class="btn01" onclick="javascript:fn_GotoUpdate();">저장</button>
	</div>
	<div class="space20"></div>
	<!-- 관라지만 노출 //-->
</div>
</body>
</html>