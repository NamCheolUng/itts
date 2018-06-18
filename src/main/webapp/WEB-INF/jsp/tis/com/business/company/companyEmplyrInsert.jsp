<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="<c:url value='/js/tis/jquery.min.js'/>"></script>

<style>
.select .k-select .k-i-arrow-60-down {
	margin-top: 10;
}
.k-picker-wrap .k-icon {
	margin-top: 10;
}
</style>

<script type="text/javascript">
function setEmail(em) {
	var frm = $("input[name=EMAIL2]").val(em);

	var sltObj = document.getElementById("selectmail");
	var idx = sltObj.selectedIndex;

	var output = document.getElementById("mail");
	var outHtml = '';

	if (sltObj.options[idx].value == "di") {
		output.innerHTML = outHtml;
	} else {
		outHtml = "@";
		output.innerHTML = outHtml;
	}
}
</script>



<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">
$(document).ready(function() {
	// create ComboBox from select HTML element
	$("#adbkId").kendoComboBox();
	$(".file").kendoUpload({ "multiple": false });
	
	$("#tel1").kendoComboBox();
	$("#fax1").kendoComboBox();
});

function fn_GotoAdd(){
	var select = $("#adbkId").data("kendoComboBox");

	if(select.value() == ""){
		alert("소속기관명을 입력하셔야 합니다.");
		return;
	}
	if (document.subForm.ncrdNm.value == "") {
		alert("이름을 입력해 주세요.");
		document.subForm.ncrdNm.focus()
		return;
	}
	if (document.subForm.ofcpsNm.value == "") {
		alert("직위를 입력해 주세요.");
		document.subForm.ofcpsNm.focus()
		return;
	}
	if (document.subForm.deptNm.value == "") {
		alert("부서를 입력해 주세요.");
		document.subForm.deptNm.focus()
		return;
	}

	for (i = 0; i < $(".files").length; i++) {
		$(".files:eq(" + i + ")").attr("name", "file_" + i);
	}

	document.subForm.cmpnyNm.value = select.text();
	document.subForm.fxnum.value = document.subForm.fax1.value + "-" + document.subForm.fax2.value + "-" + document.subForm.fax3.value;

	//이메일	
	if(document.subForm.selectmail.value != 'di'){
		document.subForm.emailAdres.value = document.subForm.EMAIL1.value + "@" + document.subForm.selectmail.value;
	}else{
		document.subForm.emailAdres.value= document.subForm.EMAIL1.value;
	}
	
	//전화번호
	if(document.subForm.tel2.value != '' && document.subForm.tel3.value == ''){
		document.subForm.telno.value = document.subForm.tel1.value + "-" + document.subForm.tel2.value;
	}else if(document.subForm.tel2.value != '' && document.subForm.tel3.value != ''){
		document.subForm.telno.value = document.subForm.tel1.value + "-" + document.subForm.tel2.value + "-" + document.subForm.tel3.value;
	}else{
		document.subForm.telno.value='';
	}
	
	//핸드폰번호
	if(document.subForm.phone2.value != '' && document.subForm.phone3.value == ''){
		document.subForm.mbtlnum.value = document.subForm.phone1.value + "-" + document.subForm.phone2.value;
	}else if(document.subForm.phone2.value != '' && document.subForm.phone3.value != ''){
		document.subForm.mbtlnum.value = document.subForm.phone1.value + "-" + document.subForm.phone2.value + "-" + document.subForm.phone3.value;
	}else{
		document.subForm.mbtlnum.value='';
	}
	
	document.subForm.action = "<c:url value='/com/business/company/companyEmplyrAdd.do'/>";
	document.subForm.submit();
}

function fn_ChangeADBK(){
	var select = $("#adbkId").data("kendoComboBox");

	if(select.value() == "")
		return;
	
	$.ajax({
		url : '<c:url value="/com/business/company/companySearch.do"/>',
		data : {"adbkId":select.value()},
		dataType : 'json',
		type : 'POST',
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		success : function(data) {
			if(data.result != null){
				var size = $("#idntfcNo").data("kendoDropDownList");
				size.value(data.result);
	        }
		}
	});
}

</script>



<div id="mainwrap">
<h1>관련기관/업체사원관리  >등록</h1>
    <div class="essential fr">
	  <i class="fa fa-asterisk" aria-hidden="true"></i>필수입력항목
	</div>
	<form name="subForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="emailAdres">
		<input type="hidden" name="mbtlnum"> 
		<input type="hidden" name="telno">
		<input type="hidden" name="fxnum">
		<input type="hidden" name="cmpnyNm">
		<input type="hidden" name="ncrdId">
    <table class="tablewrite">
      <colgroup>
        <col class="width150">
        <col class="width520">
        <col class="width150">
        <col class="width520">
      </colgroup>
      <tbody>
        <tr>
          <th>소속기관<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td>
          <select id="adbkId" name="adbkId" placeholder="소속기관명을 입력 또는  선택하세요" style="width: 100%;" onchange="javascript:fn_ChangeADBK();" >
          		<option value=""></option>
          		<c:forEach var="result" items="${adbkcode}" varStatus="status">
          			<option value='<c:out value="${result.adbkId}"/>' ><c:out value="${result.cmpnyNm}"/></option>
          		</c:forEach> 
            </select>
          	</td>
          <th>구분<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td>
          <select class="select width100" id="idntfcNo" name="idntfcNo" >
          <option value="1" >연구계</option>
          <option value="2" >기관</option>
          <option value="3" >산업계</option>
          <option value="4" >학계</option>
          </select>
          </td>
        </tr>
        <tr>
          <th>부서<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td><input type="text" class="k-textbox width100p" name="deptNm"></td>
          <th>직위<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td><input type="text" class="k-textbox width100p" name="ofcpsNm"></td>
        </tr>
        <tr>
          <th>성명<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td><input type="text" class="k-textbox width100p" name="ncrdNm"></td>
          <th>성별</th>
          <td><input type="radio" name="sexdstnCode" value="M" checked>
	    	  <label for="contactChoice1">남자</label>
			  <input type="radio" name="sexdstnCode" value="W">
	  		  <label for="contactChoice2">여자</label></td>
        </tr>
        <tr>
          <th>전화번호</th>
          <td>
            <select id="tel1" class="width100" name="tel1">
              	<option title="02" value="02">02</option>
				<option title="031" value="031">031</option>
				<option title="032" value="032">032</option>
				<option title="033" value="033">033</option>
				<option title="041" value="041">041</option>
				<option title="042" value="042">042</option>
				<option title="043" value="043">043</option>
				<option title="044" value="044">044</option>
				<option title="051" value="051">051</option>
				<option title="052" value="052">052</option>
				<option title="053" value="053">053</option>
				<option title="054" value="054">054</option>
				<option title="055" value="055">055</option>
				<option title="061" value="061">061</option>
				<option title="062" value="062">062</option>
				<option title="063" value="063">063</option>
				<option title="064" value="064">064</option>
            </select>
            <span>-</span>
            <input type="text" class="k-textbox width20p" name="tel2" maxlength="4">
            <span>-</span>
            <input type="text" class="k-textbox width20p" name="tel3" maxlength="4">
          </td>
          <th>휴대전화번호</th>
          <td>
            <select class="select width100" name="phone1">
	            <option title="010" value="010">010</option>
				<option title="011" value="011">011</option>
				<option title="016" value="016">016</option>
				<option title="017" value="017">017</option>
				<option title="018" value="018">018</option>
				<option title="019" value="019">019</option>
            </select>
            <span>-</span>
            <input type="text" class="k-textbox width20p" name="phone2" maxlength="4">
            <span>-</span>
            <input type="text" class="k-textbox width20p" name="phone3" maxlength="4">
          </td>
        </tr>
        <tr>
          <th>팩스</th>
          <td>
            <select id="fax1" class="width100" name="fax1">
              	<option title="02" value="02">02</option>
				<option title="031" value="031">031</option>
				<option title="032" value="032">032</option>
				<option title="033" value="033">033</option>
				<option title="041" value="041">041</option>
				<option title="042" value="042">042</option>
				<option title="043" value="043">043</option>
				<option title="044" value="044">044</option>
				<option title="051" value="051">051</option>
				<option title="052" value="052">052</option>
				<option title="053" value="053">053</option>
				<option title="054" value="054">054</option>
				<option title="055" value="055">055</option>
				<option title="061" value="061">061</option>
				<option title="062" value="062">062</option>
				<option title="063" value="063">063</option>
				<option title="064" value="064">064</option>
            </select>
            <span>-</span>
            <input type="text" class="k-textbox width20p" name="fax2" maxlength="4">
            <span>-</span>
            <input type="text" class="k-textbox width20p" name="fax3" maxlength="4">
          </td>
          <th>이메일</th>
          <td>
            <input type="email" name="EMAIL1" class="k-textbox width50p">
            <span id="mail"></span>
            <select name="selectmail" id="selectmail" class="select width100" onchange="setEmail(this.value)">
              <option value="di" selected>직접 입력</option>
              <option value="naver.com">naver.com</option>
              <option value="empal.com">empal.com</option>
              <option value="yahoo.co.kr">yahoo.co.kr</option>
              <option value="nate.com">nate.com</option>
              <option value="lycos.co.kr">lycos.co.kr</option>
              <option value="dreamwiz.com">dreamwiz.com</option>
              <option value="hotmail.com">hotmail.com</option>
              <option value="icloud.com">icloud.com</option>
              <option value="gmail.com">gmail.com</option>
              <option value="hanmail.net">hanmail.net</option>
            </select>
          </td>
        </tr>
        <tr>         
          <th>비고</th>
          <td colspan="3"><textarea class="k-textbox width100p height100" name="rm"></textarea></td>
        </tr>
        <tr>
    	    <th>공유설정</th>
	          <td><input type="radio" name="othbcAt" value="Y" checked>
		    	  <label for="contactChoice1">공개</label>
				  <input type="radio" name="othbcAt" value="N">
		  		  <label for="contactChoice2">비공개</label></td>
        </tr>	
        <tr>
			<th rowspan="2">첨부파일</th>
			<td colspan="3">
				<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
					<div class="k-button k-upload-button" aria-label="찾아보기">
						<input type="file" class="file" name="files" aria-label="찾아보기" data-role="upload" autocomplete="off">
						<span>찾아보기</span>
					</div>
					<div class="k-button k-upload-button" aria-label="새로고침">
						<span>새로고침</span>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="3"> 
				※ 모바일 카메라로 증빙서류를 촬영하여 등록 할 수 잇습니다. %모바일 주소 혹은 앱 마켓 경로 명시%<br>
             	 ※ 모바일에서 등록한 사진이 보이지 않을 경우 새로고침을 해보세요. 
             </td>
		</tr>
        
        
      </tbody>
    </table>
    </form>
    <br>
    <div class="btngroup fr">
    	<button type="button" class="btn02" onclick="location.href='<c:url value='/com/business/company/companyEmplyrList.do'/>'">취소</button>
		<button type="button" class="btn01" onclick="javascript:fn_GotoAdd();">등록</button>		
	</div>
</div>