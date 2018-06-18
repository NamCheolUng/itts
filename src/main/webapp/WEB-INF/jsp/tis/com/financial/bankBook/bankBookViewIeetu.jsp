<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">

function fn_GotoModify(){
	document.subForm.action = "<c:url value='/com/financial/bankBook/bankBookModify.do?affiliationId=I'/>";
	document.subForm.submit();
}

function fn_GotoDelete(){
	if(confirm("삭제 하시겠습니까?") == false)
		return;
	document.subForm.action = "<c:url value='/com/financial/bankBook/bankBookDelete.do?affiliationId=I'/>";
	document.subForm.submit();
}

</script>

<form name="subForm" method="post">
<input type="hidden" name="bankAccountNum" value="<c:out value="${tbBankbookmanageVO.bankAccountNum}"/>">
<input type="hidden" name="bankNm" value="<c:out value="${tbBankbookmanageVO.bankNm}"/>">
</form>


<div id="subwrap">
<h1>통장/통장관리 >상세보기</h1>
	<div class="tabmenu">
		<ul>
			<li class="active"><a href="<c:url value='/com/financial/bankBook/bankBook.do?affiliationId=I'/>">통장관리</a></li>
			<li><a href="<c:url value='/com/financial/bankBook/card.do?affiliationId=I'/>">카드관리</a></li>
		</ul>
	</div>
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
					<td><c:out value="${tbBankbookmanageVO.bankAccountNum}"/></td>
					<th>은행명(계좌명)</th>
					<td><c:out value="${tbBankbookmanageVO.bankNm}"/></td>
				</tr>
				<tr>
					<th>예금주</th>
					<td><c:out value="${tbBankbookmanageVO.depositorNm}"/></td>
					<th>잔액</th>
					<td><c:out value="${tbBankbookmanageVO.balance}"/></td>
				</tr>
				<tr>
					<th>계정명</th>
					<td><c:out value="${tbBankbookmanageVO.division}"/></td>
					<th>수정일</th>
					<td><fmt:formatDate value="${result.updtPnttm}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr>
					<th>회사구분</th>
					<c:forEach var="company" items="${company_result}" varStatus="status1">
						<c:if test="${company.code == tbBankbookmanageVO.cpCode}">
							<td><c:out value="${company.codeNm}"/></td>
						</c:if>				
					</c:forEach>
					<th>사용유무</th>
					<c:if test="${tbBankbookmanageVO.useAt == 'Y'}">
						<td>사용</td>
					</c:if>
					<c:if test="${tbBankbookmanageVO.useAt != 'Y'}">
						<td>미사용</td>
					</c:if>
				</tr>
				<tr>
					<th>비고</th>
					<td colspan="3"><c:out value="${tbBankbookmanageVO.rm}"/></td>
				</tr>
			</tbody>
		</table>
	<div class="space20"></div>
	<div class="btngroup fl">
		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/financial/bankBook/bankBook.do?affiliationId=I'/>'">목록</button>
	</div>
	<div class="btngroup fr">
		<button type="button" class="btn02" onclick="javascript:fn_GotoDelete();">삭제</button>
		<button type="button" class="btn01" onclick="location.href='javascript:fn_GotoModify();'">수정</button>
	</div>
	<div class="space20"></div>
	<!-- 관라지만 노출 //-->
</div>
</body>
</html>