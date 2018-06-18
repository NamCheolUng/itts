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

	var confirmFlag = false;

	function fn_requestDraftList(){
		document.frm.method.value = 'payment';
		document.frm.action = "<c:url value='/com/epay/draft/epayDraftInfoList.do'/>";
		document.frm.submit();
	}
	
	function btn_submit(){
		
		if(confirmFlag){
			alert("결재 요청 중입니다.");
			return;
		}
	
		var f = document.frm;
		
		if(f.title.value=="") {
			alert("제목을 입력하세요.");
			f.title.focus();
			return false;
		}
		
		if(f.draftContents.value=="") {
			alert("내용을 입력하세요.");
			f.draftContents.focus();
			return false;
		}
		
		if(confirm('결재요청 하시겠습니까?')){
			
			if(f.act.value=='write'){
				f.action = "<c:url value='/com/epay/draft/epayDraftWriteInsert.do'/>";
			}else {
				//f.action = "<c:url value='/setting/accessWriteUpdate.do'/>";
			}
			
			for (i = 0; i < $(".files").length; i++) {
				$(".files:eq(" + i + ")").attr("name", "file_" + i);
			}
			
			f.submit();
			
			confirmFlag = true;
		}
	}
</script>
<form name="frm" method="post">
	<input type="hidden" name="act" value="write">
	<input type="hidden" name="draftInfoId"  value='<c:out value="${epayDraftInfoVO.draftInfoId}"/>'>
	<div id="subwrap">
		<div class="tabmenu">
			<ul>
				<li><a href="<c:url value='/com/epay/draft/epayCnsulWrite.do?method=payment'/>">품의서</a></li>
				<li class="active"><a href="<c:url value='/com/epay/draft/epayDraftWrite.do?method=payment'/>">기안서</a></li>
				<li><a href="<c:url value='/com/epay/draft/epayExpWrite.do?method=payment'/>">지출명세서(개인)</a></li>
				<li><a href="<c:url value='/com/epay/draft/epayExpCorWrite.do?method=payment'/>">지출명세서(법인)</a></li>
				<li><a href="<c:url value='/com/epay/draft/epayHolidayWrite.do?method=payment'/>">휴가원</a></li>
			</ul>
		</div>
		<div><h4><i></i></h4></div>
		<div class="space10"></div>
		<h4><i class="fa fa-th-large"></i>기안서</h4>
		<table class="tablewrite">
			<colgroup>
				<col class="width180">
				<col class="width600">
				<col class="width180">
				<col class="width600">
			</colgroup>
			<tbody>
				<tr>
					<th>과제명</th>
					<td colspan="3">
						<select class="select width100p">
							<option>과제명</option>
							<option>지역특화육성사업</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>기안일자</th>
					<td><input name="regDt" class="datepicker width200" value='<c:out value="${epayDraftInfoVO.regDt}"/>' placeholder="기안일자" title="기안일자" readonly></td>
					<th>시행일자</th>
					<td><input name="draftSDate" class="datepicker width200" value='<c:out value="${epayDraftVO.draftSDate}"/>' placeholder="시행일자" title="시행일자" ></td>
				</tr>
				<tr>
					<th>부서</th>
					<td><c:out value="${epayDraftInfoVO.orgnztNm}"/></td>
					<th>기안자</th>
					<td><c:out value="${epayDraftInfoVO.userNm}"/></td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan="3"><input name="title" type="text" class="k-textbox width100p" value='<c:out value="${epayDraftInfoVO.title}"/>'></td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3"><textarea name="draftContents" class="k-textbox width100p height300" value='<c:out value="${epayDraftVO.draftContents}"/>'></textarea></td>
				</tr>
				<tr>
					<th>비고</th>
					<td colspan="3"><textarea name="rm" class="k-textbox width100p height100" value='<c:out value="${epayDraftVO.rm}"/>'></textarea></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3">
						<div class="k-widget k-upload k-header k-upload-sync k-upload-empty">
							<div class="k-button k-upload-button" aria-label="찾아보기">
								<input type="file" class="files" name="files" aria-label="찾아보기" data-role="upload" multiple="multiple" autocomplete="off">
								<span>찾아보기</span>
							</div>
						</div>
						<%-- <input  type="file" class="files" name="files" aria-label="찾아보기" value='<c:out value="${epayDraftVO.atchFileId}"/>'> --%>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="space20"></div>
		<div class="btngroup fl">		
			<button type="button" class="btn04" onclick="location.href='<c:url value='/com/epay/draft/epayDraftInfoList.do'/>'">목록</button>
		</div>
		<div class="btngroup fr">
			<button type="button" class="btn04" onclick="location.href='/subpage/ecd-pg01-01.php?method=payment'">임시저장</button>
			<button type="button" class="btn" onclick="javascript:btn_submit();">결재요청</button>		
		</div>
	</div>
</form>

</body>
</html>