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
		 
		 $(function () {
                    var container = $("#frm");
                    kendo.init(container);
                    container.kendoValidator({
                        rules: {
                            greaterdate: function (input) {
                                if (input.is("[data-greaterdate-msg]") && input.val() != "") {                                    
                                    var date = kendo.parseDate(input.val()),
                                        otherDate = kendo.parseDate($("[name='" + input.data("greaterdateField") + "']").val());
                                    return otherDate == null || otherDate.getTime() < date.getTime();
                                }

                                return true;
                            }
                        }
                    });
                });
		 
		function startChange() {
            var startDate = start.value(),
            endDate = end.value();

            if (startDate) {
                startDate = new Date(startDate);
                startDate.setDate(startDate.getDate());
                end.min(startDate);
            } else if (endDate) {
                start.max(new Date(endDate));
            } else {
                endDate = new Date();
                start.max(endDate);
                end.min(endDate);
            }
        }

        function endChange() {
            var endDate = end.value(),
            startDate = start.value();

            if (endDate) {
                endDate = new Date(endDate);
                endDate.setDate(endDate.getDate());
                start.max(endDate);
            } else if (startDate) {
                end.min(new Date(startDate));
            } else {
                endDate = new Date();
                start.max(endDate);
                end.min(endDate);
            }
        }

        var start = $("#searchSdate").kendoDatePicker({
            change: startChange,
            format: "yyyy/MM/dd"
        }).data("kendoDatePicker");

        var end = $("#searchEdate").kendoDatePicker({
            change: endChange,
            format: "yyyy/MM/dd"
        }).data("kendoDatePicker");

        start.max(end.value());
        end.min(start.value());
        
	});
	         
	function fn_requestDraftList(pageNo){
		
		if(!fn_Validation()){
			return;
		}
		
		document.frm.pageIndex.value = pageNo;
		document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/epay/draft/epayDraftInfoList.do'/>";
		document.frm.submit();
	}
	
	function fn_Validation(){
		re = "/^(\d{4})\/(\d{0,2})\/(\d{0,2})$/";
		
		if(document.frm.searchSdate.value != '' && !validateDate(document.frm.searchSdate.value)){
		      alert("검색 시작일이 날짜 형식에 맞지 않습니다.");
		      document.frm.searchSdate.focus();
		      return false;
		    }
		
		if(document.frm.searchEdate.value != '' && !validateDate(document.frm.searchEdate.value)){
			alert("검색 종료일이 날짜 형식에 맞지 않습니다.");
		      document.frm.searchEdate.focus();
		      return false;
		    }
		
		return true;
	}
	
	function validateDate(strDate) {
		  var t = /^(?=.+([\/.-])..\1)(?=.{10}$)(?:(\d{4}).|)(\d\d).(\d\d)(?:.(\d{4})|)$/;
		  strDate.replace(t, function($, _, y, m, d, y2) {
		    $ = new Date(y = y || y2, m, d);
		    t = $.getFullYear() != y || $.getMonth() != m || $.getDate() != d;
		  });
		  return !t;
	}
	
	function fn_DetailView(draftInfoId, docFormId, apprSttus){
		
		var actionType;
		
		switch (docFormId) {
		  case 'DOCFORM_000000000000':
			  if(apprSttus == "3"){ // 임시저장상태 일 경우 
				  actionType = "<c:url value='/com/epay/draft/epayCnsulWrite.do'/>";
			  }
			  else{
				  actionType = "<c:url value='/com/epay/draft/epayCnsulView.do'/>";
			  }
		    break;
		  case 'DOCFORM_000000000001' :
			  if(apprSttus == "3"){ // 임시저장상태 일 경우 
				  actionType = "<c:url value='/com/epay/draft/epayExpWrite.do'/>";
			  }
			  else{
				  actionType = "<c:url value='/com/epay/draft/epayExpView.do'/>";
			  }
		    //alert('지출명세서(개인)' + 'apprSttus : ' + apprSttus);
		    break;
		  case 'DOCFORM_000000000002' :
		    //alert('지출명세서(법인)');
			  if(apprSttus == "3"){ // 임시저장상태 일 경우 
				  actionType = "<c:url value='/com/epay/draft/epayExpCorWrite.do'/>";
			  }
			  else{
				  actionType = "<c:url value='/com/epay/draft/epayExpCorView.do'/>";
			  }
		    break;
		  case 'DOCFORM_000000000003' :
			  actionType = "<c:url value='/com/epay/draft/epayDraftView.do'/>";
		    break;
		  case 'DOCFORM_000000000004' :
			  if(apprSttus == "3"){ // 임시저장상태 일 경우
				  actionType = "<c:url value='/com/epay/draft/epayHolidayWrite.do'/>";
			  }
			  else{
				  actionType = "<c:url value='/com/epay/draft/epayHolidayView.do'/>";
			  }
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
		var values = document.getElementsByName("docFormChk");
		
		alert(values.length);
		
		for(var i=0;i<values.length;i++){
			if(values[i].checked){
				alert(values[i].value);
			}
		}
	}
</script>

<form name="frm" id="frm" method="post" data-role="validator" novalidate="novalidate">
	<input type="hidden" name="method" value="payment" />
	<input type="hidden" name="draftInfoId" value="" />
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
							<input type="checkbox" name="docFormChkTot" id="checkbox01" class="k-checkbox" onchange="javascript:fn_docForm_allChk()" value="docFormChkTot" <c:if test="${fn:contains(docFormTot[0], 'docFormChkTot')}">checked</c:if>><label class="k-checkbox-label" for="checkbox01">전체</label>
										
							<c:forEach var="result" items="${docFormList}" varStatus="status">
							<input type="checkbox" name="docFormChk" id="checkbox0${status.index + 2}" class="k-checkbox" value="<c:out value="${result.docFormId}"/>" <c:forEach var="result2" items="${arrDocForm}" varStatus="status2"><c:if test="${fn:contains(result2, result.docFormId)}">checked</c:if></c:forEach>><label class="k-checkbox-label" for="checkbox0${status.index + 2}"><c:out value="${result.formNm}"/></label>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th>결재상태</th>
						<td>
							<input type="checkbox" name="apprSttusChkTot" id="checkbox10" class="k-checkbox" onchange="javascript:fn_apprSttusForm_allChk()" value="apprSttusChkTot" <c:if test="${fn:contains(apprSttusTot[0], 'apprSttusChkTot')}">checked</c:if>><label class="k-checkbox-label" for="checkbox10">전체</label>
							<input type="checkbox" name="apprSttusChk" id="checkbox11" class="k-checkbox" value="0" <c:forEach var="result" items="${arrApprSttus}" varStatus="status"><c:if test="${fn:contains(result, '0')}">checked</c:if></c:forEach>><label class="k-checkbox-label" for="checkbox11">결재진행중</label>
							<input type="checkbox" name="apprSttusChk" id="checkbox12" class="k-checkbox" value="1" <c:forEach var="result" items="${arrApprSttus}" varStatus="status"><c:if test="${fn:contains(result, '1')}">checked</c:if></c:forEach>><label class="k-checkbox-label" for="checkbox12">승인</label>
							<input type="checkbox" name="apprSttusChk" id="checkbox13" class="k-checkbox" value="2" <c:forEach var="result" items="${arrApprSttus}" varStatus="status"><c:if test="${fn:contains(result, '2')}">checked</c:if></c:forEach>><label class="k-checkbox-label" for="checkbox13">반려</label>
							<input type="checkbox" name="apprSttusChk" id="checkbox14" class="k-checkbox" value="3" <c:forEach var="result" items="${arrApprSttus}" varStatus="status"><c:if test="${fn:contains(result, '3')}">checked</c:if></c:forEach>><label class="k-checkbox-label" for="checkbox14">임시저장</label>
							<input type="checkbox" name="apprSttusChk" id="checkbox15" class="k-checkbox" value="4" <c:forEach var="result" items="${arrApprSttus}" varStatus="status"><c:if test="${fn:contains(result, '4')}">checked</c:if></c:forEach>><label class="k-checkbox-label" for="checkbox15">문서검토</label>
							<input type="checkbox" name="apprSttusChk" id="checkbox16" class="k-checkbox" value="5" <c:forEach var="result" items="${arrApprSttus}" varStatus="status"><c:if test="${fn:contains(result, '5')}">checked</c:if></c:forEach>><label class="k-checkbox-label" for="checkbox16">문서등록</label>
						</td>
					</tr>				
					<tr>
						<th>문서검색</th>
						<td colspan="3">
							<div class="formgroup">
							<!-- <input class="datepicker width130" value="2018/01/05" placeholder="시작일" title="시작일">&nbsp;&nbsp;~&nbsp;<input class="datepicker width130" value="2018/01/05" placeholder="종료일" title="종료일">&nbsp;&nbsp; -->
							<input id="searchSdate" name="searchSdate" class="datepicker width130" value="<c:out value="${epayDraftInfoSearchVO.searchSdate}"/>" placeholder="시작일" title="시작일">&nbsp;&nbsp;~&nbsp;
							<input id="searchEdate" name="searchEdate" class="datepicker width130" value="<c:out value="${epayDraftInfoSearchVO.searchEdate}"/>" placeholder="종료일" title="종료일">&nbsp;&nbsp;
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
		<%--	
		<span class="boardnum fl"><strong>Total:</strong> 100, <strong>Page:</strong> 1/5</span>
		 --%>
		<span class="boardnum fl"><strong>Total:</strong> <c:out value="${paginationInfo.totalRecordCount}"/>, <strong>Page:</strong> <c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></span>
		<button type="button" class="btn01 fr" onclick="location.href='<c:url value='/com/epay/draft/epayCnsulWrite.do?method=payment'/>'">기안작성</button>			
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
					<th>결재상태</th>
					<th>문서양식</th>
					<th>제목</th>
					<th>결재요청일</th>
					<!-- <th>작성자</th>
					<th>부서</th> -->
				</tr>		
			</thead>
			<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">		
						<tr>
							<td><c:out value="${paginationInfo.totalRecordCount - ((paginationInfo.currentPageNo-1) * paginationInfo.recordCountPerPage + status.index) }" /></td>
							<td>
								<c:if test="${fn:contains(result.apprSttus, '0')}"><span class="state bgcolor01">결재진행중</span></c:if>
								<c:if test="${fn:contains(result.apprSttus, '1')}"><span class="state bgcolor02">승인</span></c:if>
								<c:if test="${fn:contains(result.apprSttus, '2')}"><span class="state bgcolor03">반려</span></c:if>
								<c:if test="${fn:contains(result.apprSttus, '3')}"><span class="state bgcolor04">임시저장</span></c:if>
								<c:if test="${fn:contains(result.apprSttus, '4')}"><span class="state bgcolor05">문서검토</span></c:if>
								<c:if test="${fn:contains(result.apprSttus, '5')}"><span class="state bgcolor06">문서등록</span></c:if>
							</td>
							<td><c:out value="${result.docFormNm}"/></td>
							<td class="alignL"><a href="javascript:fn_DetailView('<c:out value='${result.draftInfoId}'/>', '<c:out value='${result.docFormId}'/>', '<c:out value='${result.apprSttus}'/>')"><c:out value="${result.title}"/></a></td>
							<%-- <td class="alignL"><a href="<c:url value='/draft/draftView.do?method=payment&draftInfoId=${result.draftInfoId}'/>"><c:out value="${result.title}"/></a></td> --%>
							<td><c:out value="${result.regDt}"/></td>
							<%-- <td><c:out value="${result.userNm}"/></td>
							<td><c:out value="${result.orgnztNm}"/></td> --%>
							<%-- <td><fmt:formatDate value="${result.mDate}" pattern="yy/MM/dd HH:mm"/></td> --%>
						</tr>
					</c:forEach>
					<!-- 데이터가 없을때 화면에 메세지를 출력해준다 -->	
				<c:if test="${empty resultList}">
					<tr> 
						<td colspan="7">
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
	if($('input:checkbox[id="checkbox01"]').is(":checked") == true){
		$('input:checkbox[name="docFormChk"]').not(":disabled").each(function() {
			this.checked = true;
		});
	}else {
		$('input:checkbox[name="docFormChk"]').not(":disabled").each(function() {
			this.checked = false;
		});
	}
}

function fn_apprSttusForm_allChk(){
	if($('input:checkbox[id="checkbox10"]').is(":checked") == true){
		$('input:checkbox[name="apprSttusChk"]').not(":disabled").each(function() {
			this.checked = true;
		});
	}else {
		$('input:checkbox[name="apprSttusChk"]').not(":disabled").each(function() {
			this.checked = false;
		});
	}
}

</script>

            