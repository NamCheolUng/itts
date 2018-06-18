<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />

<script>

	$(document).ready(function(){ 
	  //모든 웹페이지의 항목들이 로딩이 완료되었을때 처리해줄 내용
		var f = document.frm;
		if("${epayDraftInfoVO.draftInfoId}" != "") { // 신규 문서 여부 판단
			f.act.value = 'update';
		}
	});
	
	function btn_requestWriteCancel(){
		document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/epay/draft/epayCnsulWriteCancel.do'/>";
		document.frm.submit();
	}
	// 매출 등록 버튼 클릭 이벤트 함수
	function btn_requestCnsulSales(){
		document.frm.apprSttus.value = '3';
		document.frm.action = "<c:url value='/com/epay/draft/epaySalesWrite.do?method=payment'/>";
		document.frm.submit();		
	}
	// 매입 등록 버튼 클릭 이벤트 함수
	function btn_requestCnsulPurchase(){
		document.frm.apprSttus.value = '3';
		document.frm.action = "<c:url value='/com/epay/draft/epayPurchaseWrite.do?method=payment'/>";
		document.frm.submit();		
	}
	
	// 매출 및 매입 등록시 임시 저장 작업
	function fn_requestCnsulTempWrite(){
		
		if($('#draftInfoId').val() != ""){
			return;
		}
		
		$.ajax({
			type:"post",
			url:"<c:url value='/com/epay/draft/epayCnsulTemp.do'/>",
			data:$("form[name=frm]").serializeArray(),
			dataType : 'json',
			type : 'POST',
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				//alert(data.draftInfoId);
				
				alert('data.draftInfoId : ' + data.draftInfoId);
				document.frm.draftInfoId.value = data.draftInfoId;
				
				alert('document.frm.draftInfoId.value : ' + document.frm.draftInfoId.value);
				//draftInfoId = data.draftInfoId;
			}
		});
		
		//return draftInfoId;
	}
	
	// 기안 정보 목록 조회
	function fn_requestDraftList(){
		document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/epay/draft/epayDraftInfoList.do'/>";
		document.frm.submit();
	}
	
	function fn_requestDraftSalesUpdate(salesId){
		document.frm.salesId.value = salesId;
		document.frm.action = "<c:url value='/com/epay/draft/epaySalesUpdate.do'/>";
		document.frm.submit();
	}
	
	function fn_requestDraftPurchaseUpdate(purchaseId){
		document.frm.purchaseId.value = purchaseId;
		document.frm.action = "<c:url value='/com/epay/draft/epayPurchaseUpdate.do'/>";
		document.frm.submit();
	}
	
	// 임시저장 버튼 클릭 이벤트 함수
	function btn_tmpSave(){
		
		var f = document.frm;
		
		if(f.title.value=="") {
			alert("제목을 입력하세요.");
			f.title.focus();
			return false;
		}
		
		if(f.cnsulCn.value=="") {
			alert("내용을 입력하세요.");
			f.cnsulCn.focus();
			return false;
		}
		
		if(confirm('임시저장 하시겠습니까?')){
			
			if(f.act.value=='write'){ // 신규 -> 임시저장
				f.action = "<c:url value='/com/epay/draft/epayCnsulWriteTemp.do'/>";
			}else { // 임시저장 -> 임시저장
				f.action = "<c:url value='/com/epay/draft/epayCnsulWriteTempUpdate.do'/>";
			}
			f.submit();
		}
	}
	// 결재요청 버튼 클릭 이벤트 함수
	function btn_submit(){
	
		var f = document.frm;
		
		if(f.title.value=="") {
			alert("제목을 입력하세요.");
			f.title.focus();
			return false;
		}
		
		if(f.cnsulCn.value=="") {
			alert("내용을 입력하세요.");
			f.cnsulCn.focus();
			return false;
		}
		
		// 결재라인 선택 여부 검사
		var isApprLine = $("#inputApprLineList").children().length;
		
		if(isApprLine == 0){
			alert("선택된 결재라인이 없습니다.\n결재라인을 선택해주세요.");
			return false;
		}
		
		
		if(confirm('결재요청 하시겠습니까?')){
			
			if(f.act.value=='write'){
				f.action = "<c:url value='/com/epay/draft/epayCnsulWriteInsert.do'/>";
			}else {
				f.action = "<c:url value='/com/epay/draft/epayCnsulWriteTempInsert.do'/>";
			}
			f.submit();
		}
	}
	
	function fn_ApprLnPopup() {
		var newWindow = window.open("<c:url value='/com/epay/popup/epayApprLnPopup.do'/>","결재라인 설정 화면","scrollbars=yes,menubar=no,left=200,top=200,width=850,height=600, resizable=no,toolbar=no,location=no,status=no");
		newWindow.focus();
	}
	
	// 결재선지정 콜백 함수
	function fn_epayApprLnPopupCallback(retVal) {

		var apprLnNm = "";
		
		var apprLineList = "";
		
		$.each(retVal.apprLnList, function(i, value) {
			
			apprLnNm += '[' + value.position + ']' + value.userNm;
			
			if(i+1 < retVal.apprLnList.length){
				apprLnNm += ' ▶ ';
			}
			
			// 임시 저장용 문자열 생성
			apprLineList += '<input type="hidden" name="apprLnList['+i+'].emplNo" value="'+value.emplNo+'"/>';
			apprLineList += '<input type="hidden" name="apprLnList['+i+'].apprOrdr" value="'+value.apprOrdr+'"/>';
			apprLineList += '<input type="hidden" name="apprLnList['+i+'].apprTy" value="'+value.apprTy+'"/>';
			apprLineList += '<input type="hidden" name="apprLnList['+i+'].position" value="'+value.position+'"/>';
		});
		
		apprLnNm += " ";
		
		// 결재선 정보 화면 표시
		$("#inputApprLineNm").empty().text(apprLnNm);
		// 결재선 정보 임시 저장
		$("#apprLnList").attr('value',retVal);
	
	
		 $("#inputApprLineList").empty().append(apprLineList);
	}
	
	function fn_loadExistApprLn(){
		
		var draftInfoId = '${epayDraftInfoVO.draftInfoId}';
		
			$.ajax({    
    			url:'<c:url value="/com/epay/draft/getDraftApprLnCfgList.do"/>',
    			data:{draftInfoId:draftInfoId}, 
    			dataType : 'json',
    			type : 'POST',
    			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
    			success:function(data){
    				
    				fn_epayApprLnPopupCallback(data);
    			}
	        });
	}
	
</script>
<form name="frm" method="post">
	<input type="hidden" name="act" value="write">
	<input type="hidden" name="draftInfoId" id="draftInfoId" value='<c:out value="${epayDraftInfoVO.draftInfoId}"/>'>
	<input type="hidden" name="apprSttus" id="apprSttus" value='<c:out value="${epayDraftInfoVO.apprSttus}"/>'>
	<input type="hidden" name="epayApprLnCfgVO" id="apprLnList" value='<c:out value="${epayApprLnCfgVO}"/>'>
	<input type="hidden" name="salesId" value="">
	<input type="hidden" name="purchaseId" value="">
	<div id="subwrap">
		<div class="tabmenu">
			<ul>
				<li class="active"><a href="<c:url value='/com/epay/draft/epayCnsulWrite.do?method=payment'/>">품의서</a></li>
				<%-- <li><a href="<c:url value='/com/epay/draft/epayDraftWrite.do?method=payment'/>">기안서</a></li> --%>
				<li><a href="<c:url value='/com/epay/draft/epayExpWrite.do?method=payment'/>">지출명세서(개인)</a></li>
				<li><a href="<c:url value='/com/epay/draft/epayExpCorWrite.do?method=payment'/>">지출명세서(법인)</a></li>
				<li><a href="<c:url value='/com/epay/draft/epayHolidayWrite.do?method=payment'/>">휴가원</a></li>
			</ul>
		</div>
		<div><h4><i></i></h4></div>
		<div class="space10"></div>
		<h4><i class="fa fa-th-large"></i>품의서</h4>
		<table class="tablewrite">
			<colgroup>
				<col class="width180">
				<col class="width600">
				<col class="width180">
				<col class="width600">
			</colgroup>
			<tbody>
				<tr>
					<th>(*)과제명</th>
					<td colspan="3">
						<select name="taskId" class="select width100p">
							<c:forEach var="task" items="${taskList}" varStatus="status">
			    				<option value="${task.taskId}" <c:out value="${task.taskId == epayCnsulVO.taskId ? 'selected' : ''}"/>>${task.taskNm}</option>
			    			</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>기안일자</th>
					<td><input name="regDt" class="datepicker width200" value='<c:out value="${epayDraftInfoVO.regDt}"/>' placeholder="기안일자" title="기안일자" readonly></td>
					<th>시행일자</th>
					<td><input name="cnsulSdate" class="datepicker width200" value='<c:out value="${epayCnsulVO.cnsulSdate}"/>' placeholder="시행일자" title="시행일자" ></td>
				</tr>
				<tr>
					<th>결재라인</th>
					<td colspan="3">
						<div id="inputApprLineList"></div>
						<span id="inputApprLineNm">
							<%-- <c:if test="${empty epayApprLnCfgVO}">
								<script>fn_loadExistApprLn(epayApprLnCfgVO);</script>	   	          						 			   
							</c:if> --%>
						</span>
						<button type="button" class="btn01" onclick="fn_ApprLnPopup();">결재라인 지정</button>
					</td>
				</tr>
				<tr>
					<th>기안자</th>
					<td name="userNm" id="emplNo"><c:out value="${epayDraftInfoVO.userNm}"/></td>
					<th>기안부서</th>
					<td name="" id=""><c:out value="${epayDraftInfoVO.orgnztNm}"/></td>
				</tr>
				<tr>
					<th>매출합계</th>
					<td id="salesTotPrice">0</td>
					<th>매입합계</th>
					<td id="purchaseTotPrice">0</td>
				</tr>
				<tr>
					<th>(*)제목</th>
					<td colspan="3">
						<input name="title" type="text" class="k-textbox width100p" value='<c:out value="${epayDraftInfoVO.title}"/>'>
					</td>
				</tr>
				<tr>
					<th>(*)내용</th>
					<td colspan="3">
						<textarea name="cnsulCn" class="k-textbox width100p height300"><c:out value="${epayCnsulVO.cnsulCn}"/></textarea>
					</td>
				</tr>
				<tr>
					<th>비고</th>
					<td colspan="3">
						<textarea name="rm" class="k-textbox width100p height100"><c:out value="${epayCnsulVO.rm}"/></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		
		<div class="space20"></div>
		
		<div class="btngroup fl">
			<h4><i class="fas fa-dot-circle"></i>매출내역</h4>
		</div>
		<div class="btngroup fr">
			<button type="button" class="btn01 fr" onclick="javascript:btn_requestCnsulSales();">매출 등록</button>
		</div>
		
		<%-- <button type="button" class="btn01 fr" onclick="location.href='<c:url value='/com/epay/draft/epaySalesWrite.do?method=payment'/>'">매출 등록</button> --%>
		<!-- <button type="button" class="btn03" onclick="javascript:fn_modity();">매출 등록</button> -->
		<table class="tablelist">
			<colgroup>
				<col class="width120">
				<col class="width400">
				<col>
				<col>
				<col>
				<col class="width150">
				<col class="width100">
				<col class="width100">
			</colgroup>
			<thead>
				<tr>
					<th>발주일</th>
					<th>상품/서비스명</th>
					<th>매출처</th>
					<th>Total</th>
					<th>결제예정일</th>
					<th>거래구분</th>
					<th>결제조건</th>
					<th>결제방식</th>
				</tr>
			</thead>
			<c:forEach var="sales" items="${draftSalesList}" varStatus="status">
				<tbody>
					<tr>
						<td name="list[${status.index}].orderDt" id="orderDt${status.index}">${sales.orderDt}</td>
						<td name="list[${status.index}].salesId" id="salesId${status.index}"><a href="javascript:fn_requestDraftSalesUpdate('${sales.salesId}')">${sales.prdtNm}</a></td>
						<td>${sales.cmpnyNm}</td>
						<td class="salesPrice" name="list[${status.index}].salesPlanTotPrice" id="salesPlanTotPrice${status.index}"><fmt:formatNumber value="${sales.salesPlanTotPrice}" pattern="#,###"/></td>
						<td name="list[${status.index}].payPlanDate" id="payPlanDate${status.index}">${sales.payPlanDate}</td>
						<td>${sales.tradeSeptNm}</td>
						<td name="list[${status.index}].payPoint" id="payPoint${status.index}">${sales.payPoint}</td>
						<td name="list[${status.index}].payWay" id="payWay${status.index}"> ${sales.payWay}</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
		
		<div class="space20"></div>
		
		<div class="btngroup fl">
			<h4><i class="fas fa-dot-circle"></i>매입내역</h4>
		</div>
		<div class="btngroup fr">
			<button type="button" class="btn01 fr" onclick="javascript:btn_requestCnsulPurchase();">매입 등록</button>
		</div>

		<table class="tablelist">
			<colgroup>
				<col class="width120">
				<col class="width400">
				<col>
				<col>
				<col>
				<col class="width150">
				<col class="width100">
				<col class="width100">
			</colgroup>
			<thead>
				<tr>
					<th>발주일</th>
					<th>상품/서비스명</th>
					<th>매입처</th>
					<th>Total</th>
					<th>결제예정일</th>
					<th>거래구분</th>
					<th>결제조건</th>
					<th>결제방식</th>
				</tr>
			</thead>
			<c:forEach var="purchase" items="${draftPurchaseList}" varStatus="status">
				<tbody>
					<tr>
						<td name="list[${status.index}].orderDt">${purchase.orderDt}</td>
						<td name="list[${status.index}].purchaseId"><a href="javascript:fn_requestDraftPurchaseUpdate('${purchase.purchaseId}')">${purchase.prdtNm}</a></td>
						<td>${purchase.cmpnyNm}</td>
						<td class="purchasePrice" name="[${status.index}].prcsPlanTotPrice"><fmt:formatNumber value="${purchase.prcsPlanTotPrice}" pattern="#,###"/></td>
						<%-- <td><input class="purchasePrice" value="<fmt:formatNumber value="${purchase.prcsPlanTotPrice}" pattern="#,###"/>" readonly="readonly"/></td> --%>
						<td name="list[${status.index}].payPlanDate">${purchase.payPlanDate}</td>
						<td>${purchase.tradeSeptNm}</td>
						<td name="list[${status.index}].payPoint">${purchase.payPoint}</td>
						<td name="list[${status.index}].payWay">${purchase.payWay}</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>

		<div class="space20"></div>
				
		<div class="btngroup fl">		
			<button type="button" class="btn04" onclick="location.href='<c:url value='/com/epay/draft/epayDraftInfoList.do'/>'">목록</button>
		</div>
		<div class="btngroup fr">
			<c:if test="${epayDraftInfoVO.draftInfoId ne null}"><button type="button" class="btn02" onclick="javascript:btn_requestWriteCancel();">기안취소</button></c:if>
			<button type="button" class="btn04" onclick="javascript:btn_tmpSave();">임시저장</button>
			<button type="button" class="btn" onclick="javascript:btn_submit();">결재요청</button>	
		</div>
	</div>
</form>

</body>
</html>

<script>

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//콤마찍기
function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

//콤마풀기
function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}

// 매입 및 매출 합계 계산
function fn_sumTotPrice(){
	var totSalesPrice = 0;
	var totPurchasePrice = 0;
	
	$('.salesPrice').each(function(){
		totSalesPrice += parseFloat(uncomma($(this)[0].innerText));
	});
	
	$('.purchasePrice').each(function(){
		totPurchasePrice += parseFloat(uncomma($(this)[0].innerText));
	});
	
	$('#salesTotPrice').text(numberWithCommas(totSalesPrice));
	$('#purchaseTotPrice').text(numberWithCommas(totPurchasePrice));
}

$(document).ready(function(){ 
	  //모든 웹페이지의 항목들이 로딩이 완료되었을때 처리해줄 내용		
		fn_sumTotPrice(); // 매입/매출 합계액 계산
	
		if($("#draftInfoId").val() != ""){
			fn_loadExistApprLn();
		}
	});
</script>