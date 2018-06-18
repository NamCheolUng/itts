<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">

function fn_GotoView( accountnum, banknm){
	document.subForm.bankAccountNum.value = accountnum;
	document.subForm.bankNm.value = banknm;
	document.subForm.action = "<c:url value='/com/financial/bankBook/bankBookView.do?affiliationId=I'/>";
	document.subForm.submit();
}

$(document).ready(function(){
	document.bankForm.cpCode.value= "I";
	document.bankForm.cpCodeName.value = "이튜";
	
	$("input[name='chkAffili']").change(function() {
		if($(this).val() == 'I') {
			location.href = "<c:url value='/com/financial/bankBook/bankBook.do?affiliationId=I'/>" 
		}else {
			location.href = "<c:url value='/com/financial/bankBook/bankBook.do?affiliationId=S'/>" 
		}
	});
});
</script>

<form name="subForm" method="post">
<input type="hidden" name="bankAccountNum">
<input type="hidden" name="bankNm">
</form>

<div id="subwrap">
<h1>통장관리</h1>
	<div class="btngroup fr">
		<label><input type="radio" name="chkAffili" id="affiliationId" value="I" checked="checked">이튜</label>
		<label><input type="radio" name="chkAffili" id="affiliationId" value="S">에스메이커</label>
	</div>
	<br><br>
	<div class="tabmenu">
		<ul>
			<li class="active"><a href="<c:url value='/com/financial/bankBook/bankBook.do?affiliationId=I'/>">통장관리</a></li>
			<li><a href="<c:url value='/com/financial/bankBook/card.do?affiliationId=I'/>">카드관리</a></li>
		</ul>
	</div>
	<table class="tablelist">
		<thead>
			<tr>
				<th>계좌번호</th>
				<th>은행명(계좌명)</th>
				<th>잔액</th>
				<th>수정일</th>
				<th>계정명</th>
				<th>회사구분</th>
				<th>사용유무</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${result_list}" varStatus="status">
			<tr>
				<td><a href="javascript:fn_GotoView('<c:out value="${result.bankAccountNum}"/>', '<c:out value="${result.bankNm}"/>')"><c:out value="${result.bankAccountNum}"/></a></td>
				<td><c:out value="${result.bankNm}"/></td>
				<td><c:out value="${result.balance}"/></td>
				<td>
				<fmt:formatDate value="${result.updtPnttm}" pattern="yyyy-MM-dd"/>
				</td>
				<td><c:out value="${result.division}"/></td>
				<c:forEach var="company" items="${company_result}" varStatus="status1">
					<c:if test="${company.code == result.cpCode}">
						<td><c:out value="${company.codeNm}"/></td>
					</c:if>				
				</c:forEach>
				<c:if test="${result.useAt == 'Y'}">
				<td>사용</td>
				</c:if>
				<c:if test="${result.useAt != 'Y'}">
				<td>미사용</td>
				</c:if>				
				<td><c:out value="${result.rm}"/></td>
			</tr>
			</c:forEach>
			<!-- 데이터가 없을때 화면에 메세지를 출력해준다 -->	
			<c:if test="${empty result_list}">
				<tr> 
					<td colspan="100%">
						<spring:message code="common.nodata.msg" />
					</td>
				</tr>   	          				 			   
			</c:if>
		</tbody>
	</table>
	<div class="space20"></div>
	<div class="btngroup fr">
		<a href="#" data-toggle="modal" data-target="#modal11"><button type="button" class="btn01">신규</button></a>
	</div>
	<div class="space20"></div>
	<!-- 관라지만 노출 //-->
</div>
</body>
</html>