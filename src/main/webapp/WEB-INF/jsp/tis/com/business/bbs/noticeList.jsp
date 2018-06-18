<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script type="text/javascript">
function fn_GotoSearch(pageNo){
	document.subForm.pageIndex.value = pageNo;
	document.subForm.action = "<c:url value='${tmpPath}/com/business/bbs/noticeList.do'/>";
	document.subForm.submit();
}

function fn_GotoWrite(){
	
	document.subForm.action = "<c:url value='/com/business/bbs/noticeInsert.do'/>";
	document.subForm.submit();	
}

function fn_egov_inqire_notice(nttId, bbsId) {
	if (bbsId == "")
		return false; //20150508
	document.subForm.nttId.value = nttId;	
	document.subForm.bbsId.value = bbsId;

	document.subForm.action = "<c:url value='/com/business/bbs/noticeView.do'/>";
	document.subForm.submit();
}
function fn_egov_select_noticeList(pageNo) {
	document.subForm.pageIndex.value = pageNo;
	document.subForm.action = "<c:url value='${tmpPath}/com/business/bbs/noticeList.do'/>";
	document.subForm.submit();
}
</script>
<div id="subwrap">
	<form name="subForm" method="post">
		<input type="hidden" name="bbsId" value='<c:out value="${boardVO.bbsId}"/>'>
		<input type="hidden" name="nttId" value='<c:out value="${boardVO.nttId}"/>'>
		<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>" />
		<input type="hidden" name="pageUnit" value="${searchVO.pageUnit}">
	<div class="schgroup">
		<table class="tablesch">
			<colgroup>
				<col class="width194">
				<col>
			</colgroup>			
			<tbody>
				<tr>
					<th>문서검색</th>
					<td>
						<div class="formgroup">
							<select name="searchCnd" class="select width150">
						     	<option value="" <c:if test="${searchVO.searchCnd == ''}">selected="selected"</c:if>>전체</option>
								<option value="0" <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if>>제목</option>
								<option value="1" <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if>>내용</option>
						    </select>
							<input type="text" name="searchWrd" class="k-textbox width100p" value="${searchVO.searchWrd}" placeholder="검색어를 입력하세요">
							<button type="button" class="btn03"  onclick="javascript:fn_GotoSearch(1); return false;">검색</button>
							<c:if test="${loginVO.manageAt == 'Y' }">
								<div class="btngroup fr">
									<button type="button" class="btn01" onclick="fn_GotoWrite()">등록</button>
								</div>
							</c:if>
						</div>
					</td>
				</tr>
			</tbody>			
		</table>
	</div>
	<div class="space20"></div>
	<span class="boardnum02 fl"><strong>Total:</strong> <c:out value="${paginationInfo.totalRecordCount }" />, <strong>Page:</strong>
		<c:out value="${paginationInfo.currentPageNo }" />/<c:out value="${paginationInfo.totalPageCount }" /></span>
	<div class="space10"></div>
	<table class="tablelist">
		<colgroup>
			<col class="width80">
			<col>
			<col class="width150">
			<col class="width150">
			<col>
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td>${paginationInfo.totalRecordCount - ((paginationInfo.currentPageNo-1) * paginationInfo.recordCountPerPage + status.index) }</td>
				<td class="alignL"><a href="#LINK" onclick="javascript:fn_egov_inqire_notice('<c:out value='${result.nttId}'/>', '<c:out value='${result.bbsId}'/>')">
				<c:out value="${result.nttSj }" />
				<c:if test="${result.currentFileCnt > 0}">
				<i class="fas fa-save"></i>
				</c:if>
				</a></td>
				<td><c:out value="${result.frstRegisterNm }" /></td>
				<td><c:out value="${result.frstRegisterPnttm }" /></td>
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
	<div class="space20"></div>
	<div class="pageNum">
		<div class="pagination">
			 <ul class="pagination pagination-sm">
				<ui:pagination paginationInfo="${paginationInfo}" type="ieetu" jsFunction="fn_egov_select_noticeList" />
			</ul>
		</div>
	</div>
	</form>
</div>
</body>
</html>