<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>

	$(document).ready(function() {
		 //fn_initListHeader(this, document.frm, fn_requestDraftList, '1');
	});
	         
	function fn_requestDraftList(pageNo){
		
		document.frm.pageIndex.value = pageNo;
		document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/manager/epay/managerEpayDraftInfoList.do'/>";
		document.frm.submit();
	}
	
	function fn_DetailView(draftInfoId, docFormId, apprSttus){
		
		//alert('docFormId : ' + docFormId);
		var actionType;
		
		switch (docFormId) {
		  case 'DOCFORM_000000000000':
			document.frm.apprSttus.value = apprSttus;
			actionType = "<c:url value='/com/manager/epay/managerEpayCnsulView.do'/>";
		    //alert('품의서');
		    break;
		  case 'DOCFORM_000000000001' :			 
			  	  document.frm.apprSttus.value = apprSttus;
				  actionType = "<c:url value='/com/manager/epay/managerEpayExpWrite.do'/>";			 
		    //alert('지출명세서(개인)' + 'apprSttus : ' + apprSttus);
		    break;
		  case 'DOCFORM_000000000002' :
		    //alert('지출명세서(법인)');
			document.frm.apprSttus.value = apprSttus;
			actionType = "<c:url value='/com/manager/epay/managerEpayExpCorWrite.do'/>";
		    break;
		  case 'DOCFORM_000000000003' :
			  document.frm.apprSttus.value = apprSttus;
			  actionType = "<c:url value='/com/epay/epayDraftView.do'/>";
		    break;
		  case 'DOCFORM_000000000004' :
			  document.frm.apprSttus.value = apprSttus;
			  actionType = "<c:url value='/com/manager/epay/managerEpayHolidayView.do'/>";
		    break;
		  default :
		    alert('없는 기안서 양식입니다.');
		    break;
		}
		
		document.frm.method.value = 'payment';
		document.frm.draftInfoId.value = draftInfoId;
		document.frm.action = actionType;
		document.frm.submit();
	}

	
	function fn_DraftWrite(){
		document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/epay/draft/epayDraftWrite.do'/>";
		document.frm.submit();
	}
	
	function fn_CnsulWrite(){
		document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/epay/draft/epayCnsulWrite.do'/>";
		document.frm.submit();
	}
	
	function fn_searchKeyPressed(event){
		if(event.keyCode==13){
			fn_requestDraftList('1');
		}
	}
	
	function fn_recordCountChanged() {
		fn_requestDraftList('1');
	}
	
	function fn_getChkedDocFormIds(){
		var values = document.getElementsByName("searchDocForm");
		
		alert(values.length);
		
		for(var i=0;i<values.length;i++){
			if(values[i].checked){
				alert(values[i].value);
			}
		}
	}
</script>

<form name="frm" method="post">
	<input type="hidden" name="method" value="payment" />
	<input type="hidden" name="draftInfoId" value="" />
	<input type="hidden" name="apprSttus" value="" />
	<div id="subwrap">
		<div class="schgroup">
			<table class="tablesch">
				<colgroup>
					<col class="width180">
					<col>
					<col class="width180">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th>문서양식</th>
						<td>
							<input type="checkbox" name="searchDocFormAll" id="searchDocFormAll" class="k-checkbox" onchange="javascript:fn_docForm_allChk()" value="searchDocFormAll" <c:out value="${epayDraftInfoSearchVO.searchDocFormAll == 'searchDocFormAll' ? 'checked' : ''}"/>><label class="k-checkbox-label" for="searchDocFormAll">전체</label>
							<c:forEach var="result" items="${docFormList}" varStatus="status">
								<input type="checkbox" name="searchDocForm" id="searchDocForm${result.docFormId}" class="k-checkbox" value="'${result.docFormId}'" <c:if test="${fn:contains(epayDraftInfoSearchVO.searchDocForm, result.docFormId)}">checked</c:if>><label class="k-checkbox-label" for="searchDocForm${result.docFormId}">${result.formNm}</label>
								<%-- <input type="checkbox" name="docFormChk" id="checkbox0${status.index + 2}" class="k-checkbox" value="<c:out value="${result.docFormId}"/>" <c:out value="${result.checkedCnd == null || result.checkedCnd == '' ? '' : 'checked'}"/>><label class="k-checkbox-label" for="checkbox0${status.index + 2}"><c:out value="${result.formNm}"/></label> --%>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th>부서</th>
						<td>
							<input type="checkbox" name="searchDeptCodeAll" id="searchDeptCodeAll" class="k-checkbox" onchange="javascript:fn_deptForm_allChk()" value="searchDeptCodeAll" <c:out value="${epayDraftInfoSearchVO.searchDeptCodeAll == 'searchDeptCodeAll' ? 'checked' : ''}"/>><label class="k-checkbox-label" for="searchDeptCodeAll">전체</label>
							<c:forEach var="result" items="${deptList}" varStatus="status">
								<input type="checkbox" name="searchDeptCode" id="searchDeptCode${result.code}" class="k-checkbox" value="'${result.code}'" <c:if test="${fn:contains(epayDraftInfoSearchVO.searchDeptCode, result.code)}">checked</c:if>><label class="k-checkbox-label" for="searchDeptCode${result.code}">${result.codeNm}</label>
							</c:forEach>
						</td>
					</tr>				
					<tr>
						<th>문서상태</th>
						<td>
							<input type="checkbox" name="searchApprSttusAll" id="searchApprSttusAll" class="k-checkbox" onchange="javascript:fn_apprSttusForm_allChk()" value="searchApprSttusAll" <c:out value="${epayDraftInfoSearchVO.searchApprSttusAll == 'searchApprSttusAll' ? 'checked' : ''}"/>><label class="k-checkbox-label" for="searchApprSttusAll">전체</label>
							<c:forEach var="result" items="${apprSttusList}" varStatus="status">
								<input type="checkbox" name="searchApprSttus" id="searchApprSttus${result.code}" class="k-checkbox" value="'${result.code}'" <c:if test="${fn:contains(epayDraftInfoSearchVO.searchApprSttus, result.code)}">checked</c:if>><label class="k-checkbox-label" for="searchApprSttus${result.code}">${result.codeNm}<c:if test="${result.codeNm != result.codeDc}">(${result.codeDc})</c:if></label>
							</c:forEach>
						</td>
					</tr>				
					<tr>
						<th>문서검색</th>
						<td>
							<div class="formgroup">
							<!-- <input class="datepicker width130" value="2018/01/05" placeholder="시작일" title="시작일">&nbsp;&nbsp;~&nbsp;<input class="datepicker width130" value="2018/01/05" placeholder="종료일" title="종료일">&nbsp;&nbsp; -->
							<input name="searchSdate" id="searchSdate" class="datepicker width130" value="<c:out value="${epayDraftInfoSearchVO.searchSdate}"/>" placeholder="시작일" title="시작일">&nbsp;&nbsp;~&nbsp;
							<input name="searchEdate" id="searchEdate" class="datepicker width130" value="<c:out value="${epayDraftInfoSearchVO.searchEdate}"/>" placeholder="종료일" title="종료일">&nbsp;&nbsp;
							<select name="searchCnd" class="select width100">
								<%-- <option value="" <c:out value="${searchVO.searchCnd == null || searchVO.searchCnd == '' ? 'selected' : ''}"/>>전체</option> --%>
								<option value="title" <c:out value="${epayDraftInfoSearchVO.searchCnd == 'title' ? 'selected' : ''}"/>>제목</option>
								<!-- <option>내용</option> -->
							</select>
							<input name="searchWrd" type="text" class="k-textbox width100p" value="<c:out value="${epayDraftInfoSearchVO.searchCnd != 'none' ? epayDraftInfoSearchVO.searchWrd : ''}"/>" onkeypress="javascript:fn_searchKeyPressed(event);">
							<button type="button" class="btn03" onclick="javascript:fn_requestDraftList('1');">검색</button>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="space10"></div>
		<span class="boardnum02 fl"><strong>Total:</strong> <c:out value="${paginationInfo.totalRecordCount}"/>, <strong>Page:</strong> <c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></span>
		<div class="space10"></div>
		<table class="tablelist">
			<colgroup>
				<col class="width80">
				<col class="width150">
				<col class="width150">
				<col>
				<col class="width150">
				<col class="width150">
				<col class="width150">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>문서상태</th>
					<th>문서양식</th>
					<th>제목</th>
					<th>결재요청일</th>
					<th>작성자</th>
					<th>부서</th>
				</tr>		
			</thead>
			<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">		
						<tr>
							<td><c:out value="${paginationInfo.totalRecordCount - ((paginationInfo.currentPageNo-1) * paginationInfo.recordCountPerPage + status.index) }" /></td>
							<td>
								<c:forEach var="apprSttus" items="${apprSttusList}" varStatus="status">
									<c:if test="${apprSttus.code == result.apprSttus}"><span class="state bgcolor0${apprSttus.code+1}">${apprSttus.codeNm}<c:if test="${apprSttus.codeNm != apprSttus.codeDc}">(${apprSttus.codeDc})</c:if></span></c:if>
								</c:forEach>
							</td>
							<td><c:out value="${result.docformNm}"/></td>
							<td class="alignL"><a href="javascript:fn_DetailView('<c:out value='${result.draftInfoId}'/>', '<c:out value='${result.docFormId}'/>', '<c:out value='${result.apprSttus}'/>')"><c:out value="${result.title}"/></a></td>
							<td><c:out value="${result.regDt}"/></td>
							<td><c:out value="${result.userNm}"/></td>
							<td><c:out value="${result.orgnztNm}"/></td>
						</tr>
					</c:forEach>
			<!-- 데이터가 없을때 화면에 메세지를 출력해준다 -->	
			<c:if test="${empty resultList}">
				<tr> 
					<td colspan="100%">
						<spring:message code="common.nodata.msg" />
					</td>
				</tr>   	          				 			   
			</c:if>
			</tbody>
		</table>
		<div class="pageNum">
				<div class="pagination">
				<ul class="pagination pagination-sm">
					<li>
						<!-- <a href="#">&laquo;</a></li><li class="active"><a href="#">1</a></li><li><a href="#">2</a></li><li><a href="#">3</a></li><li><a href="#">4</a></li><li><a href="#">5</a></li><li><a href="#">6</a></li><li><a href="#">7</a></li><li><a href="#">8</a></li><li><a href="#">9</a></li><li><a href="#">10</a></li><li><a href="#">&raquo;</a></li> -->
						<ui:pagination paginationInfo="${paginationInfo}" type="ieetu" jsFunction="fn_requestDraftList"/>
					</ul>
				</div>
				<input name="pageIndex" type="hidden" value="<c:out value='${epayDraftInfoSearchVO.pageIndex}'/>"/>
			</div>
	</div>
</form>

<script type="text/javascript">
function fn_docForm_allChk(){
	if($('input:checkbox[id="searchDocFormAll"]').is(":checked") == true){
		$('input:checkbox[name="searchDocForm"]').not(":disabled").each(function() {
			this.checked = true;
		});
	}else {
		$('input:checkbox[name="searchDocForm"]').not(":disabled").each(function() {
			this.checked = false;
		});
	}
}

function fn_deptForm_allChk(){
	if($('input:checkbox[id="searchDeptCodeAll"]').is(":checked") == true){
		$('input:checkbox[name="searchDeptCode"]').not(":disabled").each(function() {
			this.checked = true;
		});
	}else {
		$('input:checkbox[name="searchDeptCode"]').not(":disabled").each(function() {
			this.checked = false;
		});
	}
}

function fn_apprSttusForm_allChk(){
	if($('input:checkbox[id="searchApprSttusAll"]').is(":checked") == true){
		$('input:checkbox[name="searchApprSttus"]').not(":disabled").each(function() {
			this.checked = true;
		});
	}else {
		$('input:checkbox[name="searchApprSttus"]').not(":disabled").each(function() {
			this.checked = false;
		});
	}
}

$(document).ready(function() {
	fn_initSearchDatePicker("searchSdate", "searchEdate");
});

</script>
