<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
	$(document).ready(function() {
		$("#divLoader").hide();
						$("input[name='chkAffili']").change(function() {
							if ($(this).val() == 'I') {
							location.href = "<c:url value='/com/financial/laterSettle/foodExpense.do?affiliationId=I'/>"
							} else {
								location.href = "<c:url value='/com/financial/laterSettle/foodExpense.do?affiliationId=S'/>"
							}
						});

						$("#checkall").click(function() {
							if ($("#checkall").prop("checked")) {
								$("input[name=box]").prop("checked", true);
							} else {
								$("input[name=box]").prop("checked", false);
							}
						})
					})

	function fn_addUser() {
		document.frm.carNumb.value = document.frm.carNumbInput.value;

		var splitUserInfo = document.frm.selectUser.value.split(',');
		
			document.frm.emplNm.value = splitUserInfo[0];
			document.frm.emailAdres.value = splitUserInfo[1];
			document.frm.emplNo.value = splitUserInfo[2];

		$.ajax({
					url : '<c:url value="/com/financial/laterSettle/chekUserDuplParking.do"/>',
					data : {
						carNumb : document.frm.carNumb.value,
						emplNm : document.frm.emplNm.value,
						affiliationId : "S",
						emailAdres : document.frm.emailAdres.value
					},
					dataType : 'json',
					type : 'POST',
					contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
					success : function(data) {
						if (data == 1) {
							alert("중복된 사원차량입니다.");
							return false;
						} else {
							document.frm.action = "<c:url value='/com/financial/laterSettle/addparkingExpense.do?affiliationId=S'/>";
							document.frm.submit();
						}
					},
					error : function(xhr, status, error) {
						alert(error);
						console.log(error);
					}
				});
	}

	function fn_updtParkingList() {

		var fields = $('#frm2').serializeArray();
		var parkingArray = new Array();
		var parkingInfo = new Object();

		jQuery.each(fields, function(i, field) {
			if (field.name == "emplNm") {
				parkingInfo.emplNm = field.value;
			}
			if (field.name == "carNumb") {
				parkingInfo.carNumb = field.value;
			}
			if (field.name == "extensionDt") {
				parkingInfo.extensionDt = field.value.replace(/\//gi, "-");
			}
			if (field.name == "carDiv") {
				parkingInfo.carDiv = field.value;
			}
			if (field.name == "cost") {
				parkingInfo.cost = field.value;
			}
			if (field.name == "paymentDt") {
				parkingInfo.paymentDt = field.value.replace(/\//gi, "-");
			}
			if (field.name == "expirationDt") {
				parkingInfo.expirationDt = field.value.replace(/\//gi, "-");
			}
			if (field.name == "rm") {
				parkingInfo.rm = field.value;
			}
			if (field.name == "rm") {
				parkingArray.push(parkingInfo);
				parkingInfo = new Object();
			}
		});

		var jsonData = JSON.stringify(parkingArray);

		$.ajax({
					url : '<c:url value="/com/financial/laterSettle/updtParkingList.do"/>',
					data : {
						jsonData : jsonData,
						affiliationId : "S"
					},
					dataType : 'json',
					type : 'POST',
					contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
					success : function() {
						location.href = "<c:url value='/com/financial/laterSettle/parkingExpense.do?affiliationId=S'/>"
					},
					error : function() {
						location.href = "<c:url value='/com/financial/laterSettle/parkingExpense.do?affiliationId=S'/>"
					}
				});
	}

	function fn_deleteParkingList() {
		if ($("input[name=box]:checked").size() == 0) {
			alert("선택된 건이 없습니다.");
			return;
		}
		
		var send_array = Array();
		var send_cnt = 0;
		var chkbox = $(".checkSelect");

		for (i = 0; i < chkbox.length; i++) {
			if (chkbox[i].checked == true) {
				send_array[send_cnt] = chkbox[i].value;

				$.ajax({
							url : '<c:url value="/com/financial/laterSettle/parkingExpenseDelete.do"/>',
							data : {
								data : send_array[i]
							},
							dataType : 'json',
							type : 'POST',
							contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
							success : function() {
							},
							error : function() {
							}
						});
				send_cnt++;

			}
		}
		location.href = "<c:url value='/com/financial/laterSettle/parkingExpense.do?affiliationId=S'/>"
	}
	
	var loadChk =false;
	
	function sendPush() {
		if ($("input[name=box]:checked").size() == 0) {
			alert("선택된 건이 없습니다.");
			return;
		}
		
		if(loadChk){
			alert("전송중 입니다.");
			return;
		}
		$("input[name=box]:checked").each(function() {
					var parkingInfo = $(this).val().split(",");

					document.frm2.sMessage.value += "주차정기권: " + parkingInfo[3]
							+ "원 _ (" + parkingInfo[5] + " 만료예정)" + "/";
					document.frm2.sUserId.value += parkingInfo[4] + "/";

					document.frm2.mailAddress.value += parkingInfo[6] + "/";
					document.frm2.mailTitle.value += parkingInfo[8]
							+ " 주차정기권 정산내역입니다." + "/";
					document.frm2.mailText.value += "차량번호: " + parkingInfo[7]
							+ ", 만료예정일: " + parkingInfo[5] + ", 금액: "
							+ parkingInfo[3] + "원/";
				});
		
	$.ajax({
			url : "<c:url value='/mobile/com/inc/pushToMobile.do'/>",
			data : {
						sMessage: document.frm2.sMessage.value,
						sUserId: document.frm2.sUserId.value,
						mailAddress: document.frm2.mailAddress.value,
						mailTitle: document.frm2.mailTitle.value,
						mailText: document.frm2.mailText.value
			},
			type : 'POST',
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		    beforeSend:function(){
		    	$("#divLoader").show();
		    },
		    complete:function(){
		    	$("#divLoader").hide();
		    	alert("전송이 완료되었습니다.");
		    }
		});
	loadChk = true;
	}

	function fn_downloadExcel() {
		if ($("input[name=box]:checked").size() == 0) {
			alert("선택된 건이 없습니다.");
			return;
		}
		document.frm.carNumb.value = "";
		$("input[name=box]:checked").each(function() {
			var splitVal = $(this).val().split(",");
			document.frm.carNumb.value += splitVal[0] + "/";
		});
		document.frm.action = "<c:url value='/com/financial/laterSettle/parkingDownloadExcel.do?affiliationId=S'/>";
		document.frm.submit();
	}
</script>
<div id="subwrap">
	<form name="frm" method="post">
		<input type="hidden" name="carNumb"> 
		<input type="hidden" name="extensionDt"> 
		<input type="hidden" name="emplNm">
		<input type="hidden" name="emplNo"> 
		<input type="hidden" name="emailAdres">

		<h1>후불정산관리</h1>
		<div class="btngroup fr">
			<label><input type="radio" name="chkAffili" id="affiliationId" value="I">이튜</label> 
			<label><input type="radio" name="chkAffili" id="affiliationId" value="S" checked="checked">에스메이커</label>
		</div>
		<br><br>
		<div class="schgroup">
			<table class="tablesch">
				<colgroup>
					<col class="width150">
					<col>
				</colgroup>
				<tbody>
					<tr>
					<th>사원추가</th>
						<td>사원이름:&nbsp; 
						<select name="selectUser" class="select width150">
							<c:forEach var="userList" items="${userList}" varStatus="status">
								${userList.userNm }
								<option value="${userList.userNm },${userList.emailAdres },${userList.emplNo }">${userList.userNm }</option>
							</c:forEach>
						</select> 
						차량번호:&nbsp; 
						<input type=text class="k-textbox" name="carNumbInput">
						<input type="text" style="display:none">
							<button type="button" class="btn01" onclick="fn_addUser()">추가</button>
							<div class="btngroup fr">
								<button type="button" class="btn01" onclick="fn_updtParkingList()" >저장</button>
								<button type="button" class="btn02" onclick="fn_deleteParkingList()">삭제</button>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="space20"></div>

		<div class="tabmenu">
			<ul>
				<li><a href="<c:url value='/com/financial/laterSettle/foodExpense.do?affiliationId=S'/>">중식비</a></li>
				<li class="active"><a href="<c:url value='/com/financial/laterSettle/parkingExpense.do?affiliationId=S'/>">주차정기권</a></li>
			</ul>
		</div>
		<br><br><br>
		<b style="float: right">(VAT포함)</b>
	</form>

	<form name="frm2" id="frm2" method="post">
		<table class="tablelist" id="foodList">
			<colgroup>
				<col class="width50">
				<col class="width100">
				<col class="width100">
				<col class="width100">
				<col class="width100">
				<col class="width100">
				<col class="width100">
				<col class="width100">
				<col class="width150">
			</colgroup>
			<thead>
				<tr>
					<th>선택<input type="checkbox" id="checkall" /></th>
					<th>이름</th>
					<th>차량번호</th>
					<th>최근연장일자</th>
					<th>구분</th>
					<th>금액(원)</th>
					<th>최근정산일</th>
					<th>만료일</th>
					<th>비고</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${parkingList}" varStatus="status">
					<tr id="trList">
						<td><input type="checkbox" name="box" class="checkSelect"
							value="${result.carNumb },${result.emplNm },${result.affiliationId },${result.cost },${result.emplNo},${result.expirationDt},${result.emailAdres},${result.carNumb },${result.paymentDt}">
						</td>
						<td><input type="hidden" name="emplNm" value="${result.emplNm }"> ${result.emplNm }</td>
						<td><input type="text" class="k-textbox width100p" name="carNumb" value="${result.carNumb }"></td>
						<td><input type="date" name="extensionDt" class="datepicker width100p" value="${result.extensionDt }"></td>
						<td><input type="text" class="k-textbox width100p" name="carDiv" value="${result.carDiv }"></td>
						<td><input type=text class="k-textbox width100p" name="cost" value="${result.cost }"></td>
						<td><input type="date" name="paymentDt" class="datepicker width100p" value="${result.paymentDt }"></td>
						<td><input type="date" name="expirationDt" class="datepicker width100p" value="${result.expirationDt }"></td>
						<td><input type=text class="k-textbox width100p" name="rm" value="${result.rm }"></td>
					</tr>
				</c:forEach>
				<!-- 데이터가 없을때 화면에 메세지를 출력해준다 -->
				<c:if test="${empty parkingList}">
					<tr>
						<td colspan="100%"><spring:message code="common.nodata.msg" />
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>

		<div class="space20"></div>
		<div class="btngroup fr">
			<button type="button" class="btn01" value="Send" id="submitButton" onclick="sendPush()">정산내역 내보내기</button>
			<button type="button" class="btn01" onclick="fn_downloadExcel()">엑셀 다운로드</button>
			<input type="hidden" name="sMessage"> 
			<input type="hidden" name="sUserId"> 
			<input type="hidden" name="mailAddress"> 
			<input type="hidden" name="mailTitle"> 
			<input type="hidden" name="mailText"> 
			<input type="hidden" name="returnUrl" value="<c:url value='${tmpPath}com/financial/laterSettle/parkingExpense.do?affiliationId=S'/>">
		</div>
		<div id="divLoader">
			<h2>전송중입니다. 잠시만 기다려주세요 <img class="item-icon" style="width:2%; height:2%" src="<c:url value='/images/tis/loader.gif'/>">
			</h2>
		</div>
	</form>
</div>