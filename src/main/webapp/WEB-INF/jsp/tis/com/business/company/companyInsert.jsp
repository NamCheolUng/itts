<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script type="text/javascript">
function fn_GotoAdd(){

	if (document.subForm.cmpnyNm.value == "") {
		alert("소속기관을 입력해 주세요.");
		document.subForm.cmpnyNm.focus()
		return;
	}

	for (i = 0; i < $(".files").length; i++) {
		$(".files:eq(" + i + ")").attr("name", "file_" + i);
	}	
	document.subForm.action = "<c:url value='/com/business/company/companyAdd.do'/>";
	document.subForm.submit();
}

</script>

<div id="mainwrap">
<h1>관련기관/업체관리  >등록</h1>
    <div class="essential fr">
	  <i class="fa fa-asterisk" aria-hidden="true"></i>필수입력항목
	</div>
	<form enctype="multipart/form-data" name="subForm" method="post">
	<input type="hidden" name="adbkId">
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
          <td><input type="text" name="cmpnyNm" class="k-textbox width80p">
          </td>
          <th>구분<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td>
          <select class="select width150" name="entrprsSeCode" >
          <c:forEach var="result" items="${seCode}" varStatus="status">
          	<option value='<c:out value="${result.code}"/>' ><c:out value="${result.codeNm}"/></option>
          </c:forEach>
          </select>
          </td>
        </tr>
        <tr>
          <th>사업자등록번호</th>
          <td><input type="text" class="k-textbox width100p" name="bizrno" ></td>
          <th>대표자명</th>
          <td><input type="text" class="k-textbox width100p" name="ceoNm" ></td>
        </tr>
        <tr>
          <th>업태</th>
          <td><input type="text" class="k-textbox width100p" name="bizCondition" ></td>
          <th>종목</th>
          <td><input type="text" class="k-textbox width100p" name="bizDivision" ></td>
        </tr>        
        <tr>
          <th>대표이메일</th>
          <td>
            <input type="text" class="k-textbox width100p" name="emailAdres" >            
          </td>
          <th>계산서이메일</th>
          <td>
            <input type="text" class="k-textbox width100p" name="accountEmail" >            
          </td>
        </tr>
        <tr>
          <th>전화번호</th>
          <td>
            <input type="text" class="k-textbox width100p" name="houseTelno" >            
          </td>
          <th>팩스</th>
          <td>
            <input type="text" class="k-textbox width100p" name="fxnum" >            
          </td>
        </tr>
        <tr>         
          <th>비고</th>
          <td colspan="3"><textarea class="k-textbox width100p height100" name="rm"></textarea></td>
        </tr>
        <tr>
        	<th rowspan="2">계좌정보</th>
			<th>은행명</th>
			<th>예금주명</th>
			<th>계좌번호</th>
		</tr>	
		<tr>
			<td><center><input type="text" name="bankNm" class="k-textbox width100p"></center></td>
			<td><center><input type="text" name="depositorNm" class="k-textbox width100p"></center></td>
			<td><center><input type="text" name="bankAccountNum" class="k-textbox width100p"></center></td>
		</tr>
		<tr>
			<th>첨부파일
			</th>
			<td colspan="3">
				<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
					<div class="k-button k-upload-button" aria-label="찾아보기">
						<input type="file" class="files" name="files" aria-label="찾아보기" data-role="upload" multiple="multiple" autocomplete="off">
						<span>찾아보기</span>
					</div>
					<div class="k-button k-upload-button" aria-label="새로고침">
						<span>새로고침</span>
					</div>
				</div>
			</td>
		</tr>        
      </tbody>
    </table>
    </form>
    <br>
    <div class="btngroup fr">
    	<button type="button" class="btn02" onclick="location.href='<c:url value='/com/business/company/companyList.do'/>'">취소</button>
		<button type="button" class="btn01" onclick="javascript:fn_GotoAdd();">등록</button>		
	</div>
</div>