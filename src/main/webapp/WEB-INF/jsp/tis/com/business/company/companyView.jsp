<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">
function fn_GotoModify(){
	document.subForm.action = "<c:url value='/com/business/company/companyModify.do'/>";
	document.subForm.submit();
}
</script>


<form name="subForm" method="post">
<input type="hidden" name="adbkId" value='<c:out value="${adbkUserVO.adbkId }"/>'>
</form>

<div id="mainwrap">
<h1>관련기관/업체관리  >상세</h1>
	<div class="tabmenu">
		<ul>
			<li><a href="<c:url value='/com/business/company/companyEmplyrList.do'/>">업체사원관리</a></li>
			<li class="active"><a href="<c:url value='/com/business/company/companyList.do'/>">업체관리</a></li>
		</ul>
	</div>
    <table class="tablewrite">
      <colgroup>
        <col class="width150">
        <col class="width520">
        <col class="width150">
        <col class="width520">
      </colgroup>
      <tbody>
        <tr>
          <th>업체명</th>
          <td><c:out value="${adbkUserVO.cmpnyNm }"/></td>
          <th>구분</th>
          <td><c:out value="${adbkUserVO.entrprsSeNm }"/></td>
        </tr>
        <tr>
          <th>사업자등록번호</th>
          <td><c:out value="${adbkUserVO.bizrno}"/></td>
          <th>대표자명</th>
          <td><c:out value="${adbkUserVO.ceoNm}"/></td>
        </tr>
        <tr>
          <th>업태</th>
          <td><c:out value="${adbkUserVO.bizCondition}"/></td>
          <th>종목</th>
          <td><c:out value="${adbkUserVO.bizDivision}"/></td>
        </tr>        
        <tr>
          <th>대표이메일</th>
          <td>
            <c:out value="${adbkUserVO.emailAdres}"/>            
          </td>
          <th>계산서이메일</th>
          <td>
            <c:out value="${adbkUserVO.accountEmail}"/>           
          </td>
        </tr>
        <tr>
          <th>전화번호</th>
          <td>
            <c:out value="${adbkUserVO.houseTelno}"/>           
          </td>
          <th>팩스</th>
          <td>
            <c:out value="${adbkUserVO.fxnum}"/>            
          </td>
        </tr>
        <tr>         
          <th>비고</th>
          <td colspan="3"><textarea class="k-textbox width100p height100" name="rm"><c:out value="${adbkUserVO.rm}"/></textarea></td>
        </tr>
        <tr>
        	<th rowspan="2">계좌정보</th>
			<th>은행명</th>
			<th>예금주명</th>
			<th>계좌번호</th>
		</tr>	
		<tr>
			<td><center><c:out value="${adbkUserVO.bankNm }"/></center></td>
			<td><center><c:out value="${adbkUserVO.depositorNm }"/><center></td>
			<td><center><c:out value="${adbkUserVO.bankAccountNum }"/></center></td>
		</tr>
        <tr>
          <th>첨부파일</th>
          <td colspan="3">
          		<c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
					<c:param name="param_atchFileId" value="${adbkUserVO.atchFileId}" />
					<c:param name="param_colspan" value="3" />
				</c:import>  
          </td>
        </tr>
      </tbody>
    </table>
    <br>
    <div class="btngroup fr">
   		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/business/company/companyList.do'/>'">목록</button>	
   		<button type="button" class="btn01" onclick="location.href='javascript:fn_GotoModify();'">수정</button>
	</div>
</div>