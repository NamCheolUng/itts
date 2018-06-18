<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script type="text/javascript">

function fn_userIdChk(){
	var id = document.subForm.emplyrId.value;

	if (id==""){
		document.subForm.idChk.value = '0';

		alert("아이디를 입력하세요.");
		return false;
	}

 	$.ajax({
		url : '<c:url value="/com/member/userIdChk.do"/>',
		data : {"checkId":id},
		dataType : 'json',
		type : 'POST',
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		success : function(data) {
			if(data == 1){
 	        	document.subForm.idChk.value = '0';
 	        	document.subForm.emplyrId.value="";
				alert("중복된 아이디입니다.");
				return false;
	        }else{
 				document.subForm.idChk.value = '1';
				alert("사용할 수 있는 아이디입니다.");
	        }
		}
		, error: function(xhr,status, error){
			alert(error);
	    	console.log(error);
	    }
	});
}

function setEmail(em) {
	var frm = $("input[name=EMAIL2]").val(em);

	var sltObj = document.getElementById("selectmail");
	var idx = sltObj.selectedIndex;

	var output = document.getElementById("mail");
	var outHtml = '';

	if(sltObj.options[idx].value == "di")
	{
		outHtml = "&nbsp;<input type='text' name='EMAIL2' id='EMAIL2' class='k-textbox width30p'>";
		output.innerHTML = outHtml;
		$("input[name=EMAIL2]").val(""); 
		$("input[name=EMAIL2]").focus();
	}
	else
	{
		outHtml = "<input type='hidden' name='EMAIL2' id='EMAIL2'>";
		output.innerHTML = outHtml;
		$("input[name=EMAIL2]").val(sltObj.options[idx].value);
	}
	}

	function fnGoList()
	{
		document.board.action = "<c:url value='/pohangTp/community/BoardControllView.do'/>";
		document.board.submit();
	}
	
	function fn_GotoWrite() {
		if (document.subForm.emplyrId.value == "") {
			alert("ID를 입력해 주세요.");
			document.subForm.emplyrId.focus();
			return;
		}
	    if(document.subForm.idChk.value == "0") {
	    	alert("아이디 중복확인을 해주세요.");
	    	document.subForm.emplyrId.focus();
	    	return;
	   	}
		if (document.subForm.emplyrNm.value == "") {
			alert("이름을 입력해 주세요.");
			document.subForm.emplyrNm.focus()
			return;
		}

		if (document.subForm.password.value == "" || document.subForm.password1.value == "" ) {
			alert("비밀번호를 입력해 주세요.");
			document.subForm.password.focus();
			return;
		}
		if (document.subForm.password.value != document.subForm.password1.value ) {
			alert("비밀번호가 서로 맞지 않습니다.");
			document.subForm.password.focus();
			return;
		}
		if (document.subForm.emplyrBngde1.value == "") {
			alert("입사일을 입력해 주세요.");
			document.subForm.emplyrBngde.focus()
			return;
		}

		for (i = 0; i < $(".files").length; i++) {
			$(".files:eq(" + i + ")").attr("name", "file_" + i);
		}
		document.subForm.orgnztNm.value = $("#orgnztId option:selected").text();
		document.subForm.ofcpsNm.value = $("#ofcpsId option:selected").text();
		document.subForm.emplyrBngde.value = document.subForm.emplyrBngde1.value.replace(/\//gi, "");
		if(document.subForm.ihidnum1.value != ""){
			document.subForm.ihidnum.value = document.subForm.ihidnum1.value + "-" + document.subForm.ihidnum2.value; 
		}
		if(document.subForm.EMAIL1.value != ""){
			document.subForm.emailAdres.value = document.subForm.EMAIL1.value + "@" + document.subForm.EMAIL2.value; 
		}
		if(document.subForm.tel1.value != "" && document.subForm.tel2.value != ""){
			document.subForm.hmtlnum.value = document.subForm.tel1.value + "-" + document.subForm.tel2.value + "-" + document.subForm.tel3.value;
		}
		if(document.subForm.phone1.value != "" && document.subForm.phone2.value != ""){
			document.subForm.moblphonNo.value = document.subForm.phone1.value + "-" + document.subForm.phone2.value + "-" + document.subForm.phone3.value;
		}
		
 		document.subForm.action = "<c:url value='/com/member/EgovUserInsert.do'/>";
		document.subForm.submit();
	}
	<c:if test="${!empty resultMsg}">alert("${resultMsg}");</c:if>
	
	function fn_refresh(){
		document.subForm.action = "<c:url value='${tmpPath}/com/member/EgovUserInsertView.do'/>";
		document.subForm.submit();
	}
</script>

<div id="mainwrap">
	<form name="subForm" method="post" enctype="multipart/form-data">
		<h3 class="fl">사원등록</h3>
		<div class="essential fr">
			<i class="fa fa-asterisk" aria-hidden="true"></i>필수입력항목
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
					<th>ID<i class="fa fa-asterisk" aria-hidden="true"></i></th>
					<td><input type="text" name="emplyrId" class="k-textbox width80p"> 
						<input type="hidden" name="idChk" value="0" />
						<button type="button" class="btn03" onclick="fn_userIdChk();">중복체크</button></td>
					<th>성명<i class="fa fa-asterisk" aria-hidden="true"></i></th>
					<td><input type="text" name="emplyrNm" class="k-textbox width100p"></td>
				</tr>
				<tr>
					<th>비밀번호<i class="fa fa-asterisk" aria-hidden="true"></i></th>
					<td><input type="password" name="password" class="k-textbox width100p"></td>
					<th>비밀번호확인<i class="fa fa-asterisk" aria-hidden="true"></i></th>
					<td><input type="password" name="password1" class="k-textbox width100p"></td>
				</tr>
				<tr>
					<th>부서<i class="fa fa-asterisk" aria-hidden="true"></i></th>
					<td><input type="hidden" name="orgnztNm"> 
					<select name="orgnztId" id="orgnztId" class="select width150">
							<c:forEach var="result" items="${depart_result}" varStatus="status">
								<option value="${result.code}">${result.codeNm}</option>
							</c:forEach>
					</select></td>
					<th>직위<i class="fa fa-asterisk" aria-hidden="true"></i></th>
					<td><input type="hidden" name="ofcpsNm"> 
					<select name="ofcpsId" id="ofcpsId" class="select width150">
							<c:forEach var="result" items="${postion_result}" varStatus="status">
								<option value="${result.code}">${result.codeNm}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>입사일자<i class="fa fa-asterisk" aria-hidden="true"></i></th>
					<td><input type="hidden" name="emplyrBngde"> 
						<input type="date" name="emplyrBngde1" id="emplyrBngde1" class="datepicker width200"></td>
					<th>회사구분<i class="fa fa-asterisk" aria-hidden="true"></i></th>
					<td><input type="radio" id="ieetu" name="affiliationId" value="I" checked> <label for="contactChoice1">이튜</label>
						<input type="radio" id="smaker" name="affiliationId" value="S">
						<label for="contactChoice2">에스메이커</label></td>
				</tr>
				<tr>
					<th>영문</th>
					<td><input type="text" name="emplyrNmEn" class="k-textbox width100p"></td>
					<th>한자</th>
					<td><input type="text" name="emplyrNmCn" class="k-textbox width100p"></td>
				</tr>
				<tr>
					<th>주민등록번호</th>
					<td><input type="hidden" name="ihidnum"> 
						<input type="text" name="ihidnum1" class="k-textbox width40p" maxlength="6"> 
						<span>-</span> 
						<input type="text" name="ihidnum2" class="k-textbox width40p" maxlength="7">
					</td>
					<th>이메일</th>
					<td><input type="hidden" name="emailAdres"> 
						<input type="email" name="EMAIL1" class="k-textbox width20p"> 
						<span>@</span>
						<span id="mail"> <input type="hidden" name="EMAIL2"> </span> 
					<select name="selectmail" id="selectmail" class="select width150" onchange="setEmail(this.value)">
							<option value="">::: 선택 :::</option>
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
							<option value="di">직접 입력</option>
					</select></td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td><input type="hidden" name="hmtlnum"> 
					<select name="tel1" class="select width100">
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
						<input type="text" name="tel2" class="k-textbox width20p" maxlength="4"> <span>-</span> 
						<input type="text" name="tel3" class="k-textbox width20p" maxlength="4"></td>

					<th>휴대전화번호</th>
					<td><input type="hidden" name="moblphonNo"> 
					<select name="phone1" class="select width100">
							<option title="010" value="010">010</option>
							<option title="011" value="011">011</option>
							<option title="016" value="016">016</option>
							<option title="017" value="017">017</option>
							<option title="018" value="018">018</option>
							<option title="019" value="019">019</option>
					</select> 
						<span>-</span> 
						<input type="text" name="phone2" class="k-textbox width20p" maxlength="4"> 
						<span>-</span> 
						<input type="text" name="phone3" class="k-textbox width20p" maxlength="4"></td>
				</tr>
				<tr>
					<th>주소</th>
					<td colspan="3"><input type="text" name="homeadres" class="k-textbox width100p"></td>
				</tr>
				<tr>
					<th>권한</th>
					<td colspan="3"><select name="manageAt" class="select width150">
							<option value="N">일반</option>
							<option value="Y">관리자</option>
					</select></td>
				</tr>
				<tr>
					<th>외부접속</th>
					<td colspan="3">
					<select name="externalAccess" class="select width150">
							<option value="N" selected="selected">허용안함</option>
							<option value="Y">허용함</option>
					</select></td>
				</tr>
				<tr>
					<th rowspan="2">계좌정보</th>
					<th>은행명</th>
					<th>예금주명</th>
					<th>계좌번호</th>
				</tr>
				<tr>
					<td><center>
							<input type="text" name="bankNm" class="k-textbox width100p">
						</center></td>
					<td><center>
							<input type="text" name="depositorNm" class="k-textbox width100p">
						</center></td>
					<td><center>
							<input type="text" name="bankAccountNum"
								class="k-textbox width100p">
						</center></td>
				</tr>
				<tr>
					<th rowspan="2">증빙서류 <br>1.통장사본<br> 2.주민등록등본
					</th>
					<td colspan="3">
						<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
							<div class="k-button k-upload-button" aria-label="찾아보기">
								<input type="file" class="files" name="files" aria-label="찾아보기" data-role="upload" multiple="multiple" autocomplete="off">
								<span>찾아보기</span>
							</div>
							<div class="k-button k-upload-button" aria-label="새로고침" onclick="fn_refresh()">
								<span>새로고침</span>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="3"><br> ※ 모바일 카메라로 증빙서류를 촬영하여 등록 할 수 잇습니다.
						%모바일 주소 혹은 앱 마켓 경로 명시%<br> ※ 모바일에서 등록한 사진이 보이지 않을 경우 새로고침을
						해보세요.</td>
				</tr>
			</tbody>
		</table>
		<br>
		<div class="btngroup fr">
			<button type="button" class="btn02" onclick="location.href='<c:url value='/com/member/EgovUserManage.do'/>' ">취소</button>
			<button type="button" class="btn01" onclick="javascript:fn_GotoWrite();">등록</button>
		</div>
	</form>
</div>
<script>
$(document).ready(function() {
	$("input[name=emplyrBngde1]").attr("readonly",true);
});
</script>