<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<title>(주)이튜 - 통합정보시스템</title>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/layout.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/board.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/common.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/fontawesome-all.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/kendo.common.min.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/kendo.material.min.css'/>">
	
	<script type="text/javascript" src="<c:url value='/js/tis/jquery.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/jszip.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/kendo.all.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/bootstrap.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/common.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/common.tis.kendo.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/common.map.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/common.currency.js'/>"></script>
</head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#monthpicker").kendoDatePicker({
			start : "year",
			depth : "year",
			format : "yyyy/MM",
			dateInput : true
		});
		// create ComboBox from select HTML element
		$("#itemNm").kendoComboBox();
	});

	function fn_GotoUserModify() {
		document.userModifyForm.action = "<c:url value='/com/member/EgovUserSelectUpdtView.do'/>";
		document.userModifyForm.submit();
	}

	function fn_WrkEnd() {
		$.ajax({
			url : "<c:url value='/uss/cmt/EgovCmtWrkEndInsert.do'/>",
			type : "post",
			data : $("form[name=wrkEndForm]").serializeArray(),
			success : function(data) {
				if (data.result == "OK") {
					alert('<c:out value="${loginVO.name}"/>님! 퇴근 확인 되었습니다.');
				} else {
					alert('이미 퇴근 처리되었습니다.');
				}
			}
		});
	}

	function fn_GotoOutWork() {
		if (document.outWrkForm.outWrkPlace.value == "") {
			alert("출장지를 입력하셔야 합니다.");
			return;
 		}
		if (document.outWrkForm.outWrkStime.value == "") {
			alert("출장 시작시간을 입력하셔야 합니다.");
			return;
		}
		if (document.outWrkForm.outWrkEtime.value == "") {
			alert("출장 종료시간을 입력하셔야 합니다.");
			return;
		}
		outWrkEnd
		if($("input[name=outWrkEnd]:checked").size() != 0){
			document.outWrkForm.outEndWrk.value = "Y"
		}else{
			document.outWrkForm.outEndWrk.value = "N"
		}
		document.outWrkForm.outWrkStime.value = document.outWrkForm.outWrkStime.value.replace(/\//gi, "-");
		document.outWrkForm.outWrkEtime.value = document.outWrkForm.outWrkEtime.value.replace(/\//gi, "-");

		$.ajax({
			url : "<c:url value='/uss/cmt/EgovCmtOutWrkInsert.do'/>",
			type : "post",
			data : $("form[name=outWrkForm]").serializeArray(),
			success : function(data) {
				if (data.result == "OK") {
					alert('<c:out value="${loginVO.name}"/>님! 출장 등록 되었습니다.');
				} else {
					alert('내부 아이피가 아니거나 출장시간 입력에 문제가 있습니다.');
				}
			}
		});
	}

	function fn_GotoVctAdd() {
		if (document.allvctForm.bgnde1.value == "") {
			alert("시작일을 입력하셔야 합니다.");
			return;
		}
		if (document.allvctForm.endde1.value == "") {
			alert("종료일을 입력하셔야 합니다.");
			return;
		}
		document.allvctForm.bgnde.value = document.allvctForm.bgnde1.value.replace(/\//gi, "");
		document.allvctForm.endde.value = document.allvctForm.endde1.value.replace(/\//gi, "");
		document.allvctForm.action = "<c:url value='/com/manager/managerHoliday/managerAllHolidayAdd.do'/>";
		document.allvctForm.submit();
	}

	function fn_WrkStart() {
		$.ajax({
			url : "<c:url value='/uss/cmt/EgovCmtWrkStartInsert.do'/>",
			type : "post",
			dataType : 'json',
			async : true,
			success : function(data) {
				if (data.result == "OK")
					$("#modal01").modal('show');
				else if (data.result == "Error")
					$("#modal99").modal('show');
				else
					$("#modal00").modal('show');
			}
		});
	}

	function fn_GotoSaveAppoint() {
		document.appointForm.changeDe.value = document.appointForm.de.value.replace(/\//gi, "");
		document.appointForm.afOrgnztNm.value = $("#afOrgnztId option:selected").text();
		document.appointForm.afOfcpsNm.value = $("#afOfcpsId option:selected").text();
		document.appointForm.action = "<c:url value='/com/manager/appointmentSave.do'/>";
		document.appointForm.submit();

	}

	function fn_GotoSaveBank() {
		if (document.bankForm.bankAccountNum.value == "") {
			alert("계좌 번호를 입력하셔야 합니다.");
			return;
		}
		if (document.bankForm.bankNm.value == "") {
			alert("은행명을 입력하셔야 합니다.");
			return;
		}
		if (document.bankForm.balance.value == "") {
			alert("잔액을 입력하셔야 합니다.");
			return;
		}
		if (document.bankForm.depositorNm.value == "") {
			alert("예금주 입력하셔야 합니다.");
			return;
		}
		if (document.bankForm.useAt.value == "") {
			alert("사용여부를 선택하셔야 합니다.");
			return;
		}
		
		document.bankForm.action = "<c:url value='/com/financial/bankBook/bankBookAdd.do'/>";
		document.bankForm.submit();
	}

	function fn_GotoSaveCard() {

		if (document.cardForm.cardNum.value == "") {
			alert("카드 번호를 입력하셔야 합니다.");
			return;
		}
		if (document.cardForm.cardNm.value == "") {
			alert("카드명을 입력하셔야 합니다.");
			return;
		}
		if (document.cardForm.depositorNm.value == "") {
			alert("담당자를 선택하셔야 합니다.");
			return;
		}
		if (document.cardForm.bankNm.value == "") {
			alert("결제계좌명을 입력하셔야 합니다.");
			return;
		}
		if (document.cardForm.useAt.value == "") {
			alert("사용여부를 선택하셔야 합니다.");
			return;
		}
		
		document.cardForm.action = "<c:url value='/com/financial/bankBook/cardAdd.do'/>";
		document.cardForm.submit();
	}

	function fn_selectBank(obj) {
		document.cardForm.bankAccountNum.value = obj.value;
		document.cardForm.bankNm.value = $("#bankselect option:selected")
				.text();
	}

	function fn_GotoItemAdd() {
		var select = $("#itemNm").data("kendoComboBox");
		if (select.value() == "") {
			alert("공용품명을 입력하셔야 합니다.");
			return;
		}
		document.itemForm.itemName.value = select.value();
		document.itemForm.action = "<c:url value='/com/business/item/itemAdd.do'/>";
		document.itemForm.submit();
	}
	
	var header = new function() {
		this.fn_onFormSubmit = function () {
			if(fn_currencyToNumner != null) fn_currencyToNumner();
		}
	}
</script>

<form name="userModifyForm" method="post">
	<input type="hidden" name="uniqId" value='<c:out value="${loginVO.uniqId }"/>' /> 
	<input type="hidden" name="emplyrNm" value='<c:out value="${loginVO.name }"/>' /> 
	<input type="hidden" name="emplNo" value='<c:out value="${loginVO.emplyrNo }"/>' /> 
	<input type="hidden" name="returnView" value="/com/main.do" />
</form>

<header class="header">
	<ul>
		<li class="">
			<a href="<c:url value='/com/business/bbs/noticeList.do'/>?bbsId=BBSMSTR_000000000001">
				<i class="far fa-bell"></i>
				<span>공지</span>
				<!-- 최근 24시간이내 공지사항 있을 경우 노출 -->
				 <c:import url="/com/business/bbs/noticeCnt.do" charEncoding="utf-8">
				 <c:param name="leftmenu" value="0" /></c:import> 
				<!-- 최근 24시간이내 공지사항 있을 경우 노출 //-->
			</a>
		</li>
		<li><a href="javascript:fn_WrkStart();"><i class="fas fa-toggle-on"></i><span>출근</span></a>
		</li>
		<li><a href="#" data-toggle="modal" data-target="#modal02"><i class="fas fa-street-view"></i><span>출장/외근</span></a></li>
		<li><a href="#" data-toggle="modal" data-target="#modal03"><i class="fas fa-toggle-off"></i><span>퇴근</span></a></li>
		<li><a href="javascript:fn_GotoUserModify();"><i class="far fa-id-card"></i><span>정보수정</span></a></li>
		<li><a href="<c:url value='/com/inc/actionLogout.do'/>"><i class="fas fa-sign-in-alt"></i><span>로그아웃</span></a></li>
	</ul>
</header>
<body>