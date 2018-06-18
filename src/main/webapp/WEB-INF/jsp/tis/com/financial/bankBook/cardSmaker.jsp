<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">

function fn_GotoView( num, nm){
	document.subForm.cardNum.value = num;
	document.subForm.cardNm.value = nm;
	document.subForm.action = "<c:url value='/com/financial/bankBook/cardView.do?affiliationId=S'/>";
	document.subForm.submit();
}

$(document).ready(function(){
	document.cardForm.cpCode.value= "S";
	document.cardForm.cpCodeName.value = "에스메이커";
	
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
<input type="hidden" name="cardNum">
<input type="hidden" name="cardNm">
</form>

<div id="subwrap">
<h1>통장/카드관리</h1>
	<div class="btngroup fr">
		<label><input type="radio" name="chkAffili" id="affiliationId" value="I">이튜</label>
		<label><input type="radio" name="chkAffili" id="affiliationId" value="S" checked="checked">에스메이커</label>
	</div>
	<br><br>
	<div class="tabmenu">
		<ul>
			<li><a href="<c:url value='/com/financial/bankBook/bankBook.do?affiliationId=S'/>">통장관리</a></li>
			<li class="active"><a href="<c:url value='/com/financial/bankBook/card.do?affiliationId=S'/>">카드관리</a></li>
		</ul>
	</div>
	<table class="tablelist">
		<thead>
			<tr>
				<th>카드번호</th>
				<th>담당자</th>
				<th>카드명</th>
				<th>결제계좌명</th>
				<th>회사구분</th>
				<th>사용여부</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="result" items="${result_list}" varStatus="status">
			<tr>
				<td><a href="javascript:fn_GotoView('<c:out value="${result.cardNum}"/>', '<c:out value="${result.cardNm}"/>');"><c:out value="${result.cardNum}"/></a></td>
				<td>
          		<c:forEach var="empresult" items="${emplyr_list}" varStatus="status">
					<c:if test="${empresult.emplNo == result.depositorNm}">${empresult.userNm}(${empresult.emplNo})</c:if>
				</c:forEach>
				</td>
				<td><c:out value="${result.cardNm}"/></td>
				<td><c:out value="${result.bankNm}"/></td>
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
		</tbody>			
			<!-- 데이터가 없을때 화면에 메세지를 출력해준다 -->	
			<c:if test="${empty result_list}">
				<tr> 
					<td colspan="100%">
						<spring:message code="common.nodata.msg" />
					</td>
				</tr>   	          				 			   
			</c:if>
	</table>
	<div class="space20"></div>
	<div class="btngroup fr">
		<a href="#" data-toggle="modal" data-target="#modal13"><button type="button" class="btn01">신규</button></a>
	</div>
	<div class="space20"></div>
	<!-- 관라지만 노출 //-->
</div>
</body>
</html>