<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">
function fn_select_user(uniqId){
	document.userManageVO.uniqId.value = uniqId;
	document.userManageVO.action = "<c:url value='/com/member/EgovUserSelectUpdtView.do'/>";
	document.userManageVO.submit();
}

function fn_GotoAppointment(){
	document.userManageVO.action = "<c:url value='/com/manager/personnelHistory.do'/>";
	document.userManageVO.submit();
}
</script>

<div id="mainwrap">
   <h1>사원현황 > 상세보기</h1>
	<form name="userManageVO" method="post" enctype="multipart/form-data">
		<input type="hidden" name="uniqId" value='<c:out value="${userManageVO.uniqId }"/>'/>
		<input type="hidden" name="emplyrNm" value='<c:out value="${userManageVO.emplyrNm }"/>'/>
		<input type="hidden" name="emplNo" value='<c:out value="${userManageVO.emplNo }"/>' />
		
		<table class="tablewrite">
			<colgroup>
				<col class="width150">
				<col class="width520">
				<col class="width150">
				<col class="width520">
			</colgroup>
			<tbody>
				<tr>
					<th>사원번호</th>
					<td colspan="3">${userManageVO.emplNo }</td>
				</tr>
				<tr>
					<th>ID</th>
					<td>${userManageVO.emplyrId }</td>
					<th>성명</th>
					<td>${userManageVO.emplyrNm }</td>
				</tr>
				<tr>
					<th>부서</th>
					<td>${userManageVO.orgnztNm }</td>
					<th>직위</th>
					<td>${userManageVO.ofcpsNm }</td>
				</tr>
				<tr>
					<th>입사일자</th>
					<c:set var="Bngde"
						value="${fn:substring(userManageVO.emplyrBngde,0,4)}/${fn:substring(userManageVO.emplyrBngde,4,6)}/${fn:substring(userManageVO.emplyrBngde,6,8)}" />
					<td>${Bngde }</td>
					<th>회사구분</th>
					<td><c:if test="${userManageVO.affiliationId eq 'I' }">이튜</c:if>
						<c:if test="${userManageVO.affiliationId eq 'S' }">에스메이커</c:if></td>
				</tr>
				<tr>
					<th>영문</th>
					<td>${userManageVO.emplyrNmEn }</td>
					<th>한자</th>
					<td>${userManageVO.emplyrNmCn }</td>
				</tr>
				<tr>
					<th>주민등록번호</th>
					<td>${userManageVO.ihidnum }</td>
					<th>이메일</th>
					<td>${userManageVO.emailAdres }</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>${userManageVO.hmtlnum }</td>
					<th>휴대전화번호</th>
					<td>${userManageVO.moblphonNo }</td>
				</tr>
				<tr>
					<th>주소</th>
					<td colspan="3">${userManageVO.homeadres }</td>
				</tr>
				<tr>
					<th>권한</th>
					<td><c:if test="${userManageVO.manageAt eq 'N' }">일반</c:if> 
						<c:if test="${userManageVO.manageAt eq 'Y' }">관리자</c:if></td>
					<th>상태</th>
					<c:set var="Endde"
						value="${fn:substring(userManageVO.emplyrEndde,0,4)}/${fn:substring(userManageVO.emplyrEndde,4,6)}/${fn:substring(userManageVO.emplyrEndde,6,8)}" />
					<td><c:if test="${userManageVO.emplyrEndde ne null }">퇴사 ( ${Endde } )</c:if>
						<c:if test="${userManageVO.emplyrEndde eq null }">재직</c:if></td>
				</tr>
				<tr>
					<th>외부접속</th>
					<td colspan="3">
						<c:if test="${userManageVO.externalAccess == null || userManageVO.externalAccess eq 'N' }">허용안함</c:if>
						<c:if test="${userManageVO.externalAccess eq 'Y' }">허용함</c:if> 
					</td>
				</tr>
				<tr>
					<th rowspan="2">계좌정보</th>
					<th>은행명</th>
					<th>예금주명</th>
					<th>계좌번호</th>
				</tr>
				<tr>
					<td><center>${userManageVO.bankNm }</center></td>
					<td><center>${userManageVO.depositorNm }</center></td>
					<td><center>${userManageVO.bankAccountNum }</center></td>
				</tr>
				<tr>
					<th>증빙서류 <br>1.통장사본<br> 2.주민등록등본
					</th>
					<td colspan="3"><c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
							<c:param name="param_atchFileId" value="${userManageVO.atchFileId}" />
							<c:param name="param_colspan" value="3" />
						</c:import></td>
				</tr>
			</tbody>
		</table>
		<br>
		<div class="btngroup fr">
			<button type="button" class="btn04" onclick="location.href='<c:url value='/com/member/EgovUserManage.do'/>' ">목록</button>
			<button type="button" class="btn01" onclick="javascript:fn_GotoAppointment()">인사이력</button>
			<a href="#LINK" onclick="javascript:fn_select_user('<c:out value='${userManageVO.uniqId}'/>')"><button type="button" class="btn01">정보수정</button></a>
		</div>
	</form>
</div>