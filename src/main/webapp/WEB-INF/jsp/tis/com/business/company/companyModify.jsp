<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">
function fn_GotoUpdate(){

	if (document.subForm.cmpnyNm.value == "") {
		alert("소속기관을 입력해 주세요.");
		document.subForm.cmpnyNm.focus()
		return;
	}

	for (i = 0; i < $(".files").length; i++) {
		$(".files:eq(" + i + ")").attr("name", "file_" + i);
	}	
	document.subForm.action = "<c:url value='/com/business/company/companyUpdate.do'/>";
	document.subForm.submit();
}

</script>

<div id="mainwrap">
<h1>관련기관/업체관리  >수정</h1>

	<div class="tabmenu">
		<ul>
			<li><a href="<c:url value='/com/business/company/companyEmplyrList.do'/>">업체사원관리</a></li>
			<li class="active"><a href="<c:url value='/com/business/company/companyList.do'/>">업체관리</a></li>
		</ul>
	</div>
    <div class="essential fr">
	  <i class="fa fa-asterisk" aria-hidden="true"></i>필수입력항목
	</div>
	<form enctype="multipart/form-data" name="subForm" method="post">
	<input type="hidden" name="adbkId" value='<c:out value="${adbkUserVO.adbkId}"/>'>
    <table class="tablewrite">
      <colgroup>
        <col class="width150">
        <col class="width520">
        <col class="width150">
        <col class="width520">
      </colgroup>
      <tbody>
        <tr>
          <th>업체명<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td><input type="text" name="cmpnyNm" class="k-textbox width80p" value='<c:out value="${adbkUserVO.cmpnyNm}"/>'>
          </td>
          <th>구분<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td>
          <select class="select width150" name="entrprsSeCode" >
          <c:forEach var="result" items="${seCode}" varStatus="status">
          	<option value='<c:out value="${result.code}"/>' <c:if test="${adbkUserVO.entrprsSeCode == result.code}">selected</c:if>  ><c:out value="${result.codeNm}"/></option>
          </c:forEach>
          </select>
          </td>
        </tr>
        <tr>
          <th>사업자등록번호</th>
          <td><input type="text" class="k-textbox width100p" name="bizrno" value='<c:out value="${adbkUserVO.bizrno}"/>'></td>
          <th>대표자명</th>
          <td><input type="text" class="k-textbox width100p" name="ceoNm" value='<c:out value="${adbkUserVO.ceoNm}"/>'></td>
        </tr>
        <tr>
          <th>업태</th>
          <td><input type="text" class="k-textbox width100p" name="bizCondition" value='<c:out value="${adbkUserVO.bizCondition}"/>'></td>
          <th>종목</th>
          <td><input type="text" class="k-textbox width100p" name="bizDivision" value='<c:out value="${adbkUserVO.bizDivision}"/>'></td>
        </tr>        
        <tr>
          <th>대표이메일</th>
          <td>
            <input type="text" class="k-textbox width100p" name="emailAdres" value='<c:out value="${adbkUserVO.emailAdres}"/>'>            
          </td>
          <th>계산서이메일</th>
          <td>
            <input type="text" class="k-textbox width100p" name="accountEmail" value='<c:out value="${adbkUserVO.accountEmail}"/>'>            
          </td>
        </tr>
        <tr>
          <th>전화번호</th>
          <td>
            <input type="text" class="k-textbox width100p" name="houseTelno" value='<c:out value="${adbkUserVO.houseTelno}"/>'>            
          </td>
          <th>팩스</th>
          <td>
            <input type="text" class="k-textbox width100p" name="fxnum" value='<c:out value="${adbkUserVO.fxnum}"/>'>            
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
			<td><center><input type="text" name="bankNm" class="k-textbox width100p" value='<c:out value="${adbkUserVO.bankNm}"/>'></center></td>
			<td><center><input type="text" name="depositorNm" class="k-textbox width100p" value='<c:out value="${adbkUserVO.depositorNm}"/>'></center></td>
			<td><center><input type="text" name="bankAccountNum" class="k-textbox width100p" value='<c:out value="${adbkUserVO.bankAccountNum}"/>'></center></td>
		</tr>
        <tr>
			<th>첨부파일</th>
			<td colspan="3">
				<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
					<div class="k-button k-upload-button" aria-label="찾아보기">
						<input type="file" class="files" name="files" id="upload" aria-label="찾아보기" data-role="upload" multiple="multiple" autocomplete="off">
							<span>찾아보기</span>
					</div>
				</div> 
				<c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
					<c:param name="param_atchFileId" value="${adbkUserVO.atchFileId}" />
					<c:param name="param_colspan" value="3" />
				</c:import>
			</td>
		</tr>
      </tbody>
    </table>
    </form>
    <br>
    <div class="btngroup fr">
   		<button type="button" class="btn02" onclick="location.href='<c:url value='/com/business/company/companyList.do'/>'">취소</button>
		<button type="button" class="btn01" onclick="javascript:fn_GotoUpdate();" >등록</button>		
	</div>
</div>