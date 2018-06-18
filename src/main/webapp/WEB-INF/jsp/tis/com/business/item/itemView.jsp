<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />


<script type="text/javascript">

function fn_GotoModify( ){
	document.subForm.action = "<c:url value='/com/business/item/itemModify.do'/>";
	document.subForm.submit();
}

function fn_GotoDelete(){
	if(confirm("삭제 하시겠습니까?") == false)
		return;
	document.subForm.action = "<c:url value='/com/business/item/itemDelete.do'/>";
	document.subForm.submit();
	
}

</script>

<form name="subForm" method="post">
<input type="hidden" name="itemCode" value='<c:out value="${tbItemmanageVO.itemCode}"/>'>

</form>

<div id="subwrap">
<h1>공용품관리 >상세보기</h1>
		<table class="tableview">
			<colgroup>
				<col class="width180">
				<col class="width600">
				<col class="width180">
				<col class="width600">
			</colgroup>
			<tbody>
				<tr>
					<th>물품코드</th>
					<td><c:out value="${tbItemmanageVO.itemCode}"/></td>
					<th>공용품명</th>
					<td><c:out value="${tbItemmanageVO.itemName}"/>					
					</td>
				</tr>
				<tr>
					<th>등록자</th>
					<td>
					<c:forEach var="empresult" items="${emplyr_list}" varStatus="status">
					<c:if test="${empresult.emplNo == tbItemmanageVO.emplyrNo }">
						<c:out value="${empresult.userNm}"/>
					</c:if>
					</c:forEach>
					</td>
					<th>등록일</th>
					<td>
					<fmt:formatDate value="${tbItemmanageVO.insertDt }" pattern="yyyy-MM-dd" var="insertDate"/> 
					<c:out value="${insertDate}"/></td>
				</tr>
				<tr>
					<th>위치</th>
					<td colspan="3"><c:out value="${tbItemmanageVO.location}"/></td>					
				</tr>
				<tr>
					<th>비고</th>
					<td colspan="3"><c:out value="${tbItemmanageVO.rm}"/></td>
				</tr>
			</tbody>
		</table>
	<div class="space20"></div>
	<div class="btngroup fl">
		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/business/item/itemList.do'/>'">목록</button>
	</div>
	<c:if test="${tbItemmanageVO.emplyrNo == loginVO.emplyrNo || loginVO.manageAt == 'Y' }">
	<div class="btngroup fr">
		<button type="button" class="btn02" onclick="javascript:fn_GotoDelete();">삭제</button>
		<button type="button" class="btn01" onclick="javascript:fn_GotoModify();">수정</button>
	</div>
	</c:if>
	<div class="space20"></div>
	<!-- 관라지만 노출 //-->
</div>
</body>
</html>