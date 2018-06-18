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
		
		//alert(${apprState});
		//alert('apprState : ' + ${apprState} + ' exp.apprEmplNo : ' + ${exp.apprEmplNo} + ' loginEmplNo : ' + ${loginEmplNo});
		//alert('${apprSttus} : ' + <c:out value="${apprSttus}"/>);
		//alert('${exp.apprEmplNo}');
		//alert('${loginEmplNo} : ' + <c:out value="${loginEmplNo}"/>);
		/* fn_initListHeader(this, document.frm, fn_requestDraftList, '1'); */
	});
	
	function fn_setFileColor(fileId){
		if(fileId == "" || fileId == null){
			return '<i class=\"fas fa-cloud-download-alt\"></i>';
		}
		else{
			return '<a href="javascript:fn_egov_downFile(\''+fileId+'\',0);"><i class=\"fas fa-cloud-download-alt\"></i></a>';
		}
	}
	
	function fn_requestApprList(){
		
		location.href="<c:url value ='/com/epay/appr/epayApprInfoList.do?method=payment'/>";
		
		/* document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/epay/appr/epayApprInfoList.do'/>";
		document.frm.submit(); */
	}
	         
	function fn_requestEpayApprAccept(){
		
		$.ajax({
			url:"<c:url value='/com/epay/appr/epayApprAccept.do'/>",
			type:"post",
			data:$("form[name=frm]").serializeArray(),
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				//alert(data);
				fn_requestApprList();
			}				
		});
	}
	
	function fn_requestEpayApprReturn(){
		
		$.ajax({
			url:"<c:url value='/com/epay/appr/epayApprReturn.do'/>",
			type:"post",
			data:$("form[name=frm]").serializeArray(),
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				//alert(data);
				fn_requestApprList();
			}				
		});
	}

	function fn_requestEpayApprDecide(){
		//alert('전결 처리 시작');
		
		$.ajax({
			url:"<c:url value='/com/epay/appr/epayApprDecide.do'/>",
			type:"post",
			data:$("form[name=frm]").serializeArray(),
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				alert(data);
				fn_requestApprList();
			}				
		});
	}
	
</script>
<form name="frm" method="post">
<input type="hidden" name="method" value="payment" />
<input type="hidden" name="draftInfoId" id="draftInfoId" value='<c:out value="${draftInfoId}"/>'>
<div id="subwrap">
	<div class="btngroup fl">		
		<button type="button" class="btn04" onclick="location.href='<c:url value ='/com/epay/appr/epayApprInfoList.do?method=payment'/>'">목록</button>
	</div>
	<div class="btngroup fr">
		<%-- <c:if test="${apprState eq '0' and exp.apprEmplNo eq loginEmplNo}"> --%>
		<c:if test="${exp.apprEmplNo eq loginEmplNo and apprState eq '0'}">
			<!-- <button type="button" class="btn01" data-toggle="modal" data-target="#modal05">전결</button> -->
			<button type="button" class="btn05" data-toggle="modal" data-target="#modal06exp">승인</button>		
			<button type="button" class="btn02" data-toggle="modal" data-target="#modal07exp">반려</button>
		</c:if>
	</div>
	<div class="clear"></div>
	<div class="space10"></div>
	<h4><i class="fas fa-dot-circle"></i>지출명세서(개인)</h4>
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
										<c:if test="${fn:contains(result.apprState, '0')}"><span class="state02 bgcolor01">대기</span></c:if>
										<c:if test="${fn:contains(result.apprState, '1')}"><span class="state02 bgcolor02">승인</span></c:if>
										<c:if test="${fn:contains(result.apprState, '2')}"><span class="state02 bgcolor03">반려</span></c:if>
										<c:if test="${fn:contains(result.apprState, '3')}"><span class="state02 bgcolor04">임시저장</span></c:if>
									</td>
									<td><c:out value="${result.apprTm}"/></td>
									<%-- <td><fmt:formatDate value="${result.apprTm}" pattern="yy/MM/dd HH:mm"/></td> --%>
								</tr>
							</c:forEach>						
						
						</tbody>					
					</table>
				</td>
			</tr>
				<tr>
					<th>기안일자</th>
					<td><c:out value="${exp.regDt}"/></td>
					<th>기안부서</th>
					<td>
						<c:out value="${exp.orgnztNm}"/>
					</td>
				</tr>
				<tr>
					<th>기안자</th>
					<td id="emplNo"><c:out value="${exp.userNm}"/></td>
					<th>지출총액</th>
					<td id="expTotPrice"><c:out value="${exp.expTotPrice}"/></td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan="3"><c:out value="${exp.title}"/>
					</td>
				</tr>
				<tr>
					<th>지출내역</th>
					<td colspan="3">
						<div>
						<table class="tablelist" id="expHistTable">
							<colgroup>
								<col class="width120">
								<col class="width400">
								<col>
								<col>
								<col>
								<col class="width150">
								<col class="width100">
							</colgroup>
							<thead>
								<tr>
									<th>날짜</th>
									<th>과제명</th>
									<th>계정과목</th>
									<th>지출처</th>
									<th>비고</th>
									<th>금액</th>
									<th>관리</th>
								</tr>
							</thead>
							<tbody id='my-tbody'>
								<c:forEach var="result" items="${expHistList}" varStatus="status">		
									<tr>
										<td><c:out value="${result.expDate}"/></td>
										<td><c:out value="${result.taskNm}"/></td>
										<td><c:out value="${result.expSubjNm}"/></td>
										<td><c:out value="${result.expPlace}"/></td>
										<td><c:out value="${result.rm}"/></td>
										<td><c:out value="${result.price}"/></td>
										<td>
											<c:choose>
											  <c:when test="${result.atchFileId == '' or result.atchFileId == null}">
											     <i class="fas fa-cloud-download-alt"></i>
											  </c:when>
											  <c:when test="${result.atchFileId!='' and result.atchFileId!=null}">
											     <!-- <a href="#"><i class="fas fa-cloud-download-alt"></i></a> -->
											     <a href="javascript:fn_egov_downFile('${result.atchFileId}',0);"><i class="fas fa-cloud-download-alt"></i></a>
											  </c:when>
											</c:choose>
										</td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
						</div>
					</td>
				</tr>
				<tr>
					<th>비고</th>
					<td colspan="3">
						<textarea class="k-textbox width100p height100p" readonly="readonly"><c:out value="${exp.rm}"/></textarea>
						<%-- <textarea class="k-textbox width100p height100" value='<c:out value="${exp.rm}"/>'></textarea> --%>
					</td>
				</tr>
			</tbody>			
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
						</tbody>					
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- 반려의견이 있을경우 노출 //-->
	<div class="space20"></div>
	<div class="btngroup fl">		
		<button type="button" class="btn04" onclick="location.href='<c:url value ='/com/epay/appr/epayApprInfoList.do?method=payment'/>'">목록</button>
	</div>
	<div class="btngroup fr">
		<c:if test="${apprState eq '0' and epayDraftInfoVO.apprEmplNo eq loginEmplNo}">
			<!-- <button type="button" class="btn01" data-toggle="modal" data-target="#modal05">전결</button> -->
			<button type="button" class="btn05" data-toggle="modal" data-target="#modal06exp">승인</button>		
			<button type="button" class="btn02" data-toggle="modal" data-target="#modal07exp">반려</button>
		</c:if>
	</div>
	<div class="space20"></div>

</div>

	<!-- 결재함 전결 -->
	<div class="modal fade" id="modal05exp" tabindex="-1" role="dialog" aria-labelledby="modal05Labelexp" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="modal05Labelexp">결재요청문서(전결)</h4>
				</div>
				<div class="modal-body">
					<textarea id="apprDecide" name="apprDecide"  class="k-textbox width100p height100"><c:out value="${apprDecide}"/></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn02" data-dismiss="modal">취소</button>
					<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_requestEpayApprDecide()">확인</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 결재함 승인 -->
	<div class="modal fade" id="modal06exp" tabindex="-1" role="dialog" aria-labelledby="modal06Labelexp" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="modal06Labelexp">결재요청문서(승인)</h4>
				</div>
				<div class="modal-body">
					<textarea id="apprCm" name="apprCm" class="k-textbox width100p height100" placeholder="첨언을 입력하세요."><c:out value="${apprCm}"/></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn02" data-dismiss="modal">취소</button>
					<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_requestEpayApprAccept()">확인</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 결재함 반려 -->
	<div class="modal fade" id="modal07exp" tabindex="-1" role="dialog" aria-labelledby="modal07Labelexp" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="modal07Labelexp">결재요청문서(반려)</h4>
				</div>
				<div class="modal-body">
					<textarea id="apprReturnCm" name="apprReturnCm" class="k-textbox width100p height100" placeholder="반려의견을 입력하세요."><c:out value="${apprReturnCm}"/></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn02" data-dismiss="modal">취소</button>
					<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_requestEpayApprReturn()">확인</button>
				</div>
			</div>
		</div>
	</div>
	
</form>

</body>
</html>

<script>
function fn_egov_downFile(atchFileId, fileSn){
	window.open("<c:url value='/cmm/fms/getImage.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>","new","width=400, height=300, left=100, top=100,scrollbars=no,titlebar=no,status=no,resizable=no,fullscreen=no");
}
</script>