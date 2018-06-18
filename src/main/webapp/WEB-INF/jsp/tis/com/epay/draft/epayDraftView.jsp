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

	$(document).ready(function() {
		/* fn_initListHeader(this, document.frm, fn_requestDraftList, '1'); */
	});
	         
	function fn_requestDraftList(){
		document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/epay/draft/epayDraftInfoList.do'/>";
		document.frm.submit();
	}
	
	
</script>
<form name="frm" method="post">
<input type="hidden" name="method" value="payment" />
<div id="subwrap">
	<div class="btngroup fl">		
		<button type="button" class="btn04" onclick="location.href='<c:url value ='/com/epay/draft/epayDraftInfoList.do?method=payment'/>'">목록</button>
	</div>
	<div class="btngroup fr">
		<!-- <button type="button" class="btn01" onclick="location.href='/subpage/ecd-pg01-11.php?method=payment'">기안수정</button> -->
		<button type="button" class="btn01" onclick="location.href='<c:url value ='/com/epay/draft/epayCnsulWrite.do'/>?draftInfoId=${draftInfoId}'">기안수정</button>
		<button type="button" class="btn02" onclick="location.href='/subpage/ecd-pg01-01.php?method=payment'">기안취소</button>
	</div>
	<div class="clear"></div>
	<div class="space10"></div>
	<h4><i class="fas fa-dot-circle"></i>기안서</h4>
	<table class="tableview">
		<colgroup>
			<col class="width180">
			<col class="width600">
			<col class="width180">
			<col class="width600">
		</colgroup>
		<tbody>
			<tr>
				<th>결재이력</th>
				<td colspan="3">
					<table class="tablelist">
						<colgroup>
							<col class="width300">
							<col class="width300">
							<col class="width300">
							<col class="width300">
						</colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th>성명</th>
								<th>상태</th>
								<th>결재일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${apprHistList}" varStatus="status">		
								<tr>
									<td><c:out value="${result.postn}"/></td>
									<td><c:out value="${result.approverNm}"/></td>
									<td>
										<c:if test="${fn:contains(result.apprState, '0')}"><span class="state02 bgcolor01">결재진행중</span></c:if>
										<c:if test="${fn:contains(result.apprState, '1')}"><span class="state02 bgcolor02">승인</span></c:if>
										<c:if test="${fn:contains(result.apprState, '2')}"><span class="state02 bgcolor03">반려</span></c:if>
										<c:if test="${fn:contains(result.apprState, '3')}"><span class="state02 bgcolor04">임시저장</span></c:if>
									</td>
									<td><c:out value="${result.apprTm}"/></td>
									<%-- <td><fmt:formatDate value="${result.mDate}" pattern="yy/MM/dd HH:mm"/></td> --%>
								</tr>
							</c:forEach>
						</tbody>					
					</table>
				</td>
			</tr>
			<tr>
				<th>과제명</th>
				<td colspan="3"><c:out value="${draft.taskNm}"/></td>
			</tr>
			<tr>
				<th>기안일자</th>
				<td><c:out value="${draft.regDt}"/></td>
				<th>시행일자</th>
				<td><c:out value="${draft.draftSDate}"/></td> 
			</tr>
			<tr>
				<th>부서</th>
				<td><c:out value="${draft.orgnztNm}"/></td>
				<th>기안자</th>
				<td><c:out value="${draft.drafterNm}"/></td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3"><c:out value="${draft.title}"/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3"><c:out value="${draft.draftContents}"/></td>
				<!-- <td colspan="3">첨단정보통신융합산업기술원(iact)장비관리시스템 구축 용역 바코드프린터 라벨, 리본 구입<br>-구매내용<br>1.아트라벨(100mm x 60mm x 750매) * 11EA : 101,200원<br>2.아트라벨(100mm x 60mm x 750매) * 11EA : 101,200원<br>3.아트라벨(100mm x 60mm x 750매) * 11EA : 101,200원</td> -->
			</tr>
			<tr>
				<th>비고</th>
				<td colspan="3"><c:out value="${draft.rm}"/></td>
			</tr>
			<!-- 첨부파일 있을경우만 노출 -->
			<tr>
				<th>첨부파일</th>
					<td colspan="3">
						<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
							<div class="k-button k-upload-button" aria-label="찾아보기">
								<input type="file" class="files" name="files" aria-label="찾아보기" data-role="upload" multiple="multiple" autocomplete="off">
								<span>찾아보기</span>
							</div>
						</div> <c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
							<c:param name="param_atchFileId" value="${draft.atchFileId}" />
							<c:param name="param_colspan" value="3" />
						</c:import>
					</td>
				<%-- <td colspan="3"><a href="#"><c:out value="${draft.atchFileId}"/></a></td> --%>
			</tr>
			<!-- 첨부파일 있을경우만 노출 //-->			
		</tbody>
	</table>	
	<!-- 첨언이 있을경우 노출 -->	
	<div class="space20"></div>
	<h4><i class="fas fa-dot-circle"></i>첨언</h4>
	<table class="tableview">
		<colgroup>
			<col class="width194">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th>첨언</th>
				<td>
					<table class="tablelist">
						<colgroup>
							<col class="width300">
							<col class="width300">
							<col class="width300">
							<col class="width300">
							<col class="width300">
						</colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th>성명</th>
								<th>상태</th>
								<th>첨언</th>
								<th>결재일</th>								
							</tr>
						</thead>
						<tbody>	
							<c:forEach var="result" items="${apprHistList}" varStatus="status">
								<c:if test="${!empty result.cm}">
									<tr>
										<td><c:out value="${result.approverNm}"/></td>
										<td><c:out value="${result.postn}"/></td>
										<td>
											<c:if test="${fn:contains(result.apprState, '0')}"><span class="state02 bgcolor01">결재진행중</span></c:if>
											<c:if test="${fn:contains(result.apprState, '1')}"><span class="state02 bgcolor02">승인</span></c:if>
											<c:if test="${fn:contains(result.apprState, '2')}"><span class="state02 bgcolor03">반려</span></c:if>
											<c:if test="${fn:contains(result.apprState, '3')}"><span class="state02 bgcolor04">임시저장</span></c:if>
										</td>
										<td><a href="#" onClick="return false;" data-toggle="tooltip" data-placement="bottom" title="<c:out value="${result.cm}"/>"><c:out value="${result.cm}"/></a></td>
										<td><c:out value="${result.apprTm}"/></td>								
									</tr>
								</c:if>
								
							</c:forEach>	
												
							<!-- <tr>
								<td>팀장(결재자)</td>
								<td>이차장</td>
								<td><span class="state02 bgcolor02">승인</span></td>
								<td><a href="#" onClick="return false;" data-toggle="tooltip" data-placement="bottom" title="첨언 내용은 한줄만 표시하고 모든 내용은 툴팁에 표시해 주시면 됩니다. 툴팁의 기본 방향은 bottom으로 설정하였으며 다른 방향으로 변경 가능합니다.">첨언 내용은 한줄만 표시하고...</a></td>
								<td>2017.12.12</td>								
							</tr>							
							<tr>
								<td>총괄이사(결재자)</td>
								<td>최이사</td>
								<td><span class="state02 bgcolor02">승인</span></td>
								<td><a href="#" onClick="return false;" data-toggle="tooltip" data-placement="bottom" title="첨언 내용은 한줄만 표시하고 모든 내용은 툴팁에 표시해 주시면 됩니다. 툴팁의 기본 방향은 bottom으로 설정하였으며 다른 방향으로 변경 가능합니다.">툴팁으로 모든 내용을 표시...</a></td>
								<td>2017.12.12</td>								
							</tr> -->							
						</tbody>					
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- 첨언이 있을경우 노출 //-->
	<!-- 반려의견이 있을경우 노출 -->
	<div class="space20"></div>
	<h4><i class="fas fa-dot-circle"></i>반려의견</h4>
	<table class="tableview">
		<colgroup>
			<col class="width194">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th>반려의견</th>
				<td>
					<table class="tablelist">
						<colgroup>
							<col class="width300">
							<col class="width300">
							<col class="width300">
							<col class="width300">
							<col class="width300">
						</colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th>성명</th>
								<th>상태</th>
								<th>사유</th>
								<th>결재일</th>								
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${apprHistList}" varStatus="status">
								<c:if test="${!empty result.returnCm}">
									<tr>
										<td><c:out value="${result.approverNm}"/></td>
										<td><c:out value="${result.postn}"/></td>
										<td>
											<c:if test="${fn:contains(result.apprState, '0')}"><span class="state02 bgcolor01">결재진행중</span></c:if>
											<c:if test="${fn:contains(result.apprState, '1')}"><span class="state02 bgcolor02">승인</span></c:if>
											<c:if test="${fn:contains(result.apprState, '2')}"><span class="state02 bgcolor03">반려</span></c:if>
											<c:if test="${fn:contains(result.apprState, '3')}"><span class="state02 bgcolor04">임시저장</span></c:if>
										</td>
										<td><a href="#" onClick="return false;" data-toggle="tooltip" data-placement="bottom" title="<c:out value="${result.returnCm}"/>"><c:out value="${result.returnCm}"/></a></td>
										<td><c:out value="${result.apprTm}"/></td>								
									</tr>
								</c:if>
								
							</c:forEach>					
							<!-- <tr>
								<td>대표이사(결재자)</td>
								<td>강대표</td>
								<td><span class="state02 bgcolor03">반려</span></td>
								<td><a href="#" onClick="return false;" data-toggle="tooltip" data-placement="bottom" title="반려사유 역시 첨언과 같이 한줄만 노출하고 나머지 전체 내용은 툴팁으로 표시합니다.">반려사유 한줄만 표시하고...</a></td>
								<td>2017.12.12</td>								
							</tr> -->							
						</tbody>					
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- 반려의견이 있을경우 노출 //-->
	<div class="space20"></div>
	<div class="btngroup fl">		
		<button type="button" class="btn04" onclick="location.href='<c:url value ='/com/epay/draft/epayDraftInfoList.do?method=payment'/>'">목록</button>
	</div>
	<div class="btngroup fr">
		<%-- <button type="button" class="btn01" onclick="location.href='<c:url value ='/draft/draftModify.do?method=payment'/>'">기안수정</button> --%>
		<button type="button" class="btn01" onclick="location.href='<c:url value ='/com/epay/draft/epayCnsulWrite.do'/>?draftInfoId=${draftInfoId}'">기안수정</button>
		<button type="button" class="btn02" onclick="location.href='<c:url value ='/com/epay/draft/draftInfoList.do?method=payment'/>'">기안취소</button>
	</div>
	<div class="space20"></div>
</div>
</form>

</body>
</html>