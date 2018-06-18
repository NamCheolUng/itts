<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />


<script type="text/javascript">

$(document).ready(function() {
    
 // create ComboBox from select HTML element
   $("#itemNm2").kendoComboBox();
});


function fn_GotoUpdate( ){
	var select = $("#itemNm2").data("kendoComboBox");
	if(select.value() == ""){
		alert("공용품명을 입력하셔야 합니다.");
		return;
	}	
	document.subForm.itemName.value = select.value();
	document.subForm.action = "<c:url value='/com/business/item/itemUpdate.do'/>";
	document.subForm.submit();
}

function fn_GotoView(){
	document.subForm.action = "<c:url value='/com/business/item/itemView.do'/>";
	document.subForm.submit();
	
}

</script>

<div id="subwrap">
<h1>공용품관리 >상세보기</h1>
<form name="subForm" method="post">
<input type="hidden" name="emplyrNo" value='<c:out value="${tbItemmanageVO.emplyrNo}"/>'>
<input type="hidden" name="itemName">
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
					<td><input type="text" readonly="readonly" name="itemCode" class="k-textbox width100p" value='<c:out value="${tbItemmanageVO.itemCode}"/>'></td>
					<th>공용품명</th>
					<td>
					<select id="itemNm2" style="width: 100%;">
		          		<c:forEach var="result" items="${itemNmList}" varStatus="status">
		          			<option <c:if test="${result.itemName == tbItemmanageVO.itemName }">selected</c:if>><c:out value="${result.itemName}"/></option>
		          		</c:forEach> 
		            </select>
					</td>
				</tr>
				<tr>
					<th>등록자</th>
					<td colspan="3">
					<c:forEach var="empresult" items="${emplyr_list}" varStatus="status">
					<c:if test="${empresult.emplNo == tbItemmanageVO.emplyrNo }">
						<input type="text" readonly="readonly" name="userNm" class="k-textbox width100p" value='<c:out value="${empresult.userNm}"/>'>
					</c:if>
					</c:forEach>
					</td>
				</tr>
				<tr>
					<th>위치</th>
					<td colspan="3"><input type="text" name="location" class="k-textbox width100p" value='<c:out value="${tbItemmanageVO.location}"/>'></td>					
				</tr>
				<tr>
					<th>비고</th>
					<td colspan="3"><textarea class="k-textbox width100p height100" name="rm"><c:out value="${tbItemmanageVO.rm}"/></textarea></td>
				</tr>
			</tbody>			
		</table>
</form>		
	<div class="space20"></div>
	<div class="btngroup fl">
		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/business/item/itemList.do'/>'">목록</button>
	</div>
	<c:if test="${tbItemmanageVO.emplyrNo == loginVO.emplyrNo || loginVO.manageAt == 'Y' }">
	<div class="btngroup fr">
		<button type="button" class="btn02" onclick="javascript:fn_GotoView();">취소</button>
		<button type="button" class="btn01" onclick="javascript:fn_GotoUpdate();">저장</button>
	</div>
	</c:if>
	<div class="space20"></div>
	<!-- 관라지만 노출 //-->
</div>
</body>
</html>