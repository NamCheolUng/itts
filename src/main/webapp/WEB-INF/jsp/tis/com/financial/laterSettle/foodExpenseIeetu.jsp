<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="now" value="<%=new java.util.Date()%>" />

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
						
						$("#ymonth").kendoDatePicker({
							start : "year",
							depth : "year",
							format : "yyyy/MM"
						});
	});
	function fn_addUser() {
		var select = document.frm.ymonth.value;

		if (select == "") {
			alert("일자를 입력하셔야 합니다.");
			return;
		}
		var splitUserInfo = document.frm.selectUser.value.split(',');
		
			document.frm.emplNo.value = splitUserInfo[0];
			document.frm.emplNm.value = splitUserInfo[1];
			document.frm.emailAdres.value = splitUserInfo[2];
			document.frm.foodDt.value = select.replace('/','');

 		$.ajax({
					url : '<c:url value="/com/financial/laterSettle/chekUserDupl.do"/>',
					data : {
						emplNo : document.frm.emplNo.value,
						emplNm : document.frm.emplNm.value,
						foodDt : document.frm.foodDt.value,
						affiliationId : "I",
						emailAdres : document.frm.emailAdres.value
					},
					dataType : 'json',
					type : 'POST',
					contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
					success : function(data) {
						if (data == 1) {
							alert("중복된 사원입니다.");
							return false;
						} else {
							document.frm.action = "<c:url value='/com/financial/laterSettle/addEmplToFoodList.do?affiliationId=I'/>";
							document.frm.submit();
						}
					},
					error : function(xhr, status, error) {
						alert(error);
						console.log(error);
					}
				});
	}

	function fn_searchDate() {
		var splitUserInfo = document.frm.selectUser.value.split(',');
		
			document.frm.emplNo.value = splitUserInfo[0];
			document.frm.emplNm.value = splitUserInfo[1];
			document.frm.foodDt.value = document.frm.foodDtYear.value + document.frm.foodDtMonth.value;

		document.frm.action = "<c:url value='${tmpPath}/com/financial/laterSettle/foodExpense.do?affiliationId=I'/>";
		document.frm.submit();
	}

	function fn_updtFoodList() {
		var fields = $('#frm2').serializeArray();
		var foodArray = new Array();
		var foodInfo = new Object();

		jQuery.each(fields, function(i, field) {
			if (field.name == "jsonEmplNo") {
				foodInfo.jsonEmplNo = field.value;
			}
			if (field.name == "jsonEmplNm") {
				foodInfo.jsonEmplNm = field.value;
			}
			if (field.name == "foodCnt") {
				foodInfo.foodCnt = field.value;
			}
			if (field.name == "cost") {
				foodInfo.cost = field.value;
			}
			if (field.name == "paymentDt") {
				foodInfo.paymentDt = field.value.replace(/\//gi, "-");

			}
			if (field.name == "rm") {
				foodInfo.rm = field.value;
			}
			if (field.name == "rm") {
				foodArray.push(foodInfo);
				foodInfo = new Object();
			}
		});

		var jsonData = JSON.stringify(foodArray);
		var foodDt = document.frm.foodDtYear.value + document.frm.foodDtMonth.value;

		$.ajax({
					url : '<c:url value="/com/financial/laterSettle/updtFoodList.do"/>',
					data : {
						jsonData : jsonData,
						foodDt : foodDt,
						affiliationId : "I"
					},
					dataType : 'json',
					type : 'POST',
					contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
					success : function() {
						fn_searchDate();
					},
					error : function() {
						fn_searchDate();
					}
				});
	}

	function fn_deleteFoodList() {
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
							url : '<c:url value="/com/financial/laterSettle/FoodExpenseDelete.do"/>',
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
		location.href = "<c:url value='/com/financial/laterSettle/foodExpense.do?affiliationId=I'/>"
	}
	
	function sendPush() {
		var loadChk =false;
		if ($("input[name=box]:checked").size() == 0) {
			alert("선택된 건이 없습니다.");
			return;
		}
		
		if(loadChk){
			alert("전송중 입니다");
			return;
		}
		$("input[name=box]:checked").each(function() {
					var foodInfo = $(this).val().split(",");

					document.frm2.sMessage.value += "중식비: " + foodInfo[4]
							+ "원 _ (" + foodInfo[5] + " 정산)" + "/";
					document.frm2.sUserId.value += foodInfo[0] + "/";

					document.frm2.mailAddress.value += foodInfo[6] + "/";
					document.frm2.mailTitle.value += foodInfo[5]
							+ " 중식비 정산내역입니다." + "/";
					document.frm2.mailText.value += "횟수: " + foodInfo[7] + "번 "
							+ " 금액: " + foodInfo[4] + "/";

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

		document.frm.foodDt.value = "";
		document.frm.emplNo.value = "";
		$("input[name=box]:checked").each(function() {
			var splitVal = $(this).val().split(",");
			document.frm.foodDt.value = splitVal[2]
			document.frm.emplNo.value += splitVal[0] + "/";
		});
		document.frm.action = "<c:url value='/com/financial/laterSettle/downloadExcel.do?affiliationId=I'/>";
		document.frm.submit();
	}
</script>
<div id="subwrap">
	<form name="frm" method="post">
		<input type="hidden" name="emplNo"> <input type="hidden" name="emplNm"> <input type="hidden" name="emailAdres">
		<input type="hidden" name="foodDt"> <input type="hidden" name="info">

		<h1>후불정산관리(Ieetu)</h1>
		<div class="btngroup fr">
			<label><input type="radio" name="chkAffili" id="affiliationId" value="I" checked="checked">이튜</label> 
			<label><input type="radio" name="chkAffili" id="affiliationId" value="S">에스메이커</label>
		</div>
		<br><br>
		<div class="schgroup">
			<table class="tablesch">
				<colgroup>
					<col class="width150">
					<col class="width350">
					<col class="width150">
					<col>	
				</colgroup>
				<tbody>
					<tr>
						<th>검색</th>
						<td><c:choose>
								<c:when test="${foodExpensesVO.foodDt != ''}">
									<c:set var="foodDtyear"
										value="${fn:substring(foodExpensesVO.foodDt,0,4)}"></c:set>
									<c:set var="foodDtMonth"
										value="${fn:substring(foodExpensesVO.foodDt,4,6)}"></c:set>
								</c:when>
								<c:otherwise>
									<c:set var="foodDtyear">
										<fmt:formatDate value="${now}" pattern="yyyy" />
									</c:set>
									<c:set var="foodDtMonth">
										<fmt:formatDate value="${now}" pattern="MM" />
									</c:set>
								</c:otherwise>
							</c:choose> 
							<input type="text" style="display:none">
							년도:
							<select id="foodDtYear" class="select width80" name="foodDtYear">
								<c:forEach var="result" items="${yearListResult}"
									varStatus="status">
									<option value="${result.yearList }"
										<c:if test="${result.yearList == foodDtyear }">selected</c:if>>${result.yearList }</option>
								</c:forEach>
							</select>&nbsp;&nbsp;
							 월: 
							<select class="select width50" name="foodDtMonth" id="foodDtMonth">
								<option value="01"
									<c:if test="${foodDtMonth == '01'}"> selected </c:if>>1</option>
								<option value="02"
									<c:if test="${foodDtMonth == '02'}"> selected </c:if>>2</option>
								<option value="03"
									<c:if test="${foodDtMonth == '03'}"> selected </c:if>>3</option>
								<option value="04"
									<c:if test="${foodDtMonth == '04'}"> selected </c:if>>4</option>
								<option value="05"
									<c:if test="${foodDtMonth == '05'}"> selected </c:if>>5</option>
								<option value="06"
									<c:if test="${foodDtMonth == '06'}"> selected </c:if>>6</option>
								<option value="07"
									<c:if test="${foodDtMonth == '07'}"> selected </c:if>>7</option>
								<option value="08"
									<c:if test="${foodDtMonth == '08'}"> selected </c:if>>8</option>
								<option value="09"
									<c:if test="${foodDtMonth == '09'}"> selected </c:if>>9</option>
								<option value="10"
									<c:if test="${foodDtMonth == '10'}"> selected </c:if>>10</option>
								<option value="11"
									<c:if test="${foodDtMonth == '11'}"> selected </c:if>>11</option>
								<option value="12"
									<c:if test="${foodDtMonth == '12'}"> selected </c:if>>12</option>
							</select>&nbsp;&nbsp;
							<button type="button" class="btn03" onclick="fn_searchDate()">검색</button>
							</td>
							<th>사원추가</th>
							<td>
								<input type="date" class="width120" name="ymonth" id="ymonth">&nbsp;&nbsp;
								<select name="selectUser" class="select width100">
									<c:forEach var="userList" items="${userList}" varStatus="status">
									${userList.userNm }
									<option value="${userList.emplNo },${userList.userNm },${userList.emailAdres }">${userList.userNm }</option>
									</c:forEach>
								</select>
								<button type="button" class="btn01" onclick="fn_addUser()">추가</button>
								<div class="btngroup fr">
									<button type="button" class="btn01" onclick="fn_updtFoodList()">저장</button>
									<button type="button" class="btn02" onclick="fn_deleteFoodList()">삭제</button>
								</div>
							</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="space20"></div>
		<div class="tabmenu">
			<ul>
				<li class="active"><a href="<c:url value='/com/financial/laterSettle/foodExpense.do?affiliationId=I'/>">중식비</a></li>
				<li><a href="<c:url value='/com/financial/laterSettle/parkingExpense.do?affiliationId=I'/>">주차정기권</a></li>
			</ul>
		</div>
		<br><br><br> 
		<b style="float: right">(VAT포함)</b>
	</form>

	<form name="frm2" id="frm2" method="post">
		<table class="tablelist" id="foodList">
			<colgroup>
				<col class="width50">
				<col class="width80">
				<col class="width80">
				<col class="width80">
				<col class="width80">
				<col class="width150">
			</colgroup>
			<thead>
				<tr>
					<th>선택<input type="checkbox" id="checkall" /></th>
					<th>이름</th>
					<th>식사횟수</th>
					<th>금액(원)</th>
					<th>정산일</th>
					<th>비고</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${foodExpenseList}" varStatus="status">
					<tr id="trList">
						<td><input type="checkbox" name="box"
							value="${result.emplNo },${result.emplNm },${result.foodDt },${result.affiliationId },${result.cost },${result.paymentDt },${result.emailAdres},${result.foodCnt }"
							class="checkSelect"> <input type="hidden"
							name="jsonEmplNo" value="${result.emplNo }"> 
							<input type="hidden" name="jsonEmplNm" value="${result.emplNm }">
						</td>
						<td>${result.emplNm }</td>
						<td><input type="text" class="k-textbox width100p" name="foodCnt" value="${result.foodCnt }"></td>
						<td><input type="text" class="k-textbox width100p" name="cost" value="${result.cost }"></td>
						<td><input type="date" name="paymentDt" class="datepicker width100p" value="${result.paymentDt }"></td>
						<td><input type=text class="k-textbox width100p" name="rm" value="${result.rm }"></td>
					</tr>
				</c:forEach>
				<!-- 데이터가 없을때 화면에 메세지를 출력해준다 -->
				<c:if test="${empty foodExpenseList}">
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
			<input type="hidden" name="mailAddress"> <input type="hidden" name="mailTitle"> 
			<input type="hidden" name="mailText">
			<input type="hidden" name="sMessage"> 
			<input type="hidden" name="sUserId"> 
			<input type="hidden" name="foodCnt"> 
		</div>
		<div id="divLoader">
			<h2>전송중입니다. 잠시만 기다려주세요 <img class="item-icon" style="width:2%; height:2%" src="<c:url value='/images/tis/loader.gif'/>">
			</h2>
		</div>
	</form>
</div>