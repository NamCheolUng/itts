<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />


<script type="text/javascript">

function fn_GotoModify( ){
	document.subForm.action = "<c:url value='/com/financial/bankBook/cardModify.do?affiliationId=I'/>";
	document.subForm.submit();
}

function fn_GotoDelete(){
	if(confirm("삭제 하시겠습니까?") == false)
		return;
	document.subForm.action = "<c:url value='/com/financial/bankBook/cardDelete.do?affiliationId=I'/>";
	document.subForm.submit();
	
}

</script>

<form name="subForm" method="post">
<input type="hidden" name="cardNum" value='<c:out value="${tbBankcardmanageVO.cardNum}"/>'>
<input type="hidden" name="cardNm" value='<c:out value="${tbBankcardmanageVO.cardNm}"/>'>
</form>

<div id="subwrap">
<h1>통장/카드관리 >상세보기</h1>
	<div class="tabmenu">
		<ul>
			<li><a href="<c:url value='/com/financial/bankBook/bankBook.do?affiliationId=I'/>">통장관리</a></li>
			<li class="active"><a href="<c:url value='/com/financial/bankBook/card.do?affiliationId=I'/>">카드관리</a></li>
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
					<th>카드번호</th>
					<td><c:out value="${tbBankcardmanageVO.cardNum}"/></td>
					<th>담당자</th>
					<td>
					<c:forEach var="empresult" items="${emplyr_list}" varStatus="status">
						<c:if test="${empresult.emplNo == tbBankcardmanageVO.depositorNm}">${empresult.userNm}(${empresult.emplNo})</c:if>
					</c:forEach>
					</td>
				</tr>
				<tr>
					<th>카드명</th>
					<td><c:out value="${tbBankcardmanageVO.cardNm}"/></td>
					<th>결제계좌명</th>
					<td><c:out value="${tbBankcardmanageVO.bankNm}"/></td>
				</tr>
				<tr>
					<th>만료일자</th>
					<td><c:out value="${tbBankcardmanageVO.endPnttm}"/></td>
					<th>사용여부</th>
					<c:if test="${tbBankcardmanageVO.useAt == 'Y'}">
					<td>사용</td>
					</c:if>
					<c:if test="${tbBankcardmanageVO.useAt != 'Y'}">
					<td>미사용</td>
					</c:if>	
				</tr>
				<tr>
					<th>회사구분</th>
					<c:forEach var="company" items="${company_result}" varStatus="status1">
					<c:if test="${company.code == tbBankcardmanageVO.cpCode}">
						<td><c:out value="${company.codeNm}"/></td>
					</c:if>				
					</c:forEach>
				</tr>
				<tr>
					<th>비고</th>
					<td colspan="3"><c:out value="${tbBankcardmanageVO.rm}"/></td>
				</tr>
			</tbody>
		</table>
	<div class="space20"></div>
	<div class="btngroup fl">
		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/financial/bankBook/card.do?affiliationId=I'/>'">목록</button>
	</div>
	<div class="btngroup fr">
		<button type="button" class="btn02" onclick="javascript:fn_GotoDelete();">삭제</button>
		<button type="button" class="btn01" onclick="javascript:fn_GotoModify();">수정</button>
	</div>
	<div class="space20"></div>
	<!-- 관라지만 노출 //-->
</div>
</body>
</html>