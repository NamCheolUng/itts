<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<jsp:useBean id="currentTime" class="java.util.Date"/>

<c:set var="session" value="${sessionScope.loginVO}"/>

<!-- 근태관리 출근 -->
<div class="modal fade" id="modal01" tabindex="-1" role="dialog" aria-labelledby="modal01Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="modal01Label">근태관리(출근)</h4>
			</div>
			<div class="modal-body">
				<fmt:formatDate value="${currentTime }" pattern="yyyy-MM-dd HH:mm" var="currentTime"/> 
				<strong><c:out value="${session.name }"/></strong>님!<br><c:out value="${currentTime }"/> 출근 확인 되었습니다.
			</div>
			<div class="modal-footer">
				<button type="button" class="btn01" data-dismiss="modal">확인</button>
			</div>
		</div>
	</div>
</div>

<!-- 중복 출근 -->
<div class="modal fade" id="modal00" tabindex="-1" role="dialog" aria-labelledby="modal00Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="modal01Label">근태관리(출근)</h4>
			</div>
			<div class="modal-body">
				<strong><c:out value="${session.name }"/></strong>님!<br> 이미 출근 확인 되었습니다.
			</div>
			<div class="modal-footer">
				<button type="button" class="btn01" data-dismiss="modal">확인</button>
			</div>
		</div>
	</div>
</div>

<!-- IP오류 -->
<div class="modal fade" id="modal99" tabindex="-1" role="dialog" aria-labelledby="modal00Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="modal01Label">근태관리(출근)</h4>
			</div>
			<div class="modal-body">
				<strong>사내 IP가 아닙니다.</strong>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn01" data-dismiss="modal">확인</button>
			</div>
		</div>
	</div>
</div>

<!-- 근태관리 출장 -->
<form name="outWrkForm" method="post">
<input type="hidden" name="wrktmId">
<input type="hidden" name="outEndWrk">
<div class="modal fade" id="modal02" tabindex="-1" role="dialog" aria-labelledby="modal02Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="modal02Label">근태관리(출장/외근)</h4>
			</div>
			<div class="modal-body">
				<table class="tablewrite">
					<colgroup>
						<col>
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th>츨장/외근지</th>
							<td><input type="text" name="outWrkPlace" class="k-textbox width100p"></td>
						</tr>
						<tr>
							<th>시작시간</th>
							<td><input name="outWrkStime" id="outWrkStime" placeholder="시작시간" title="시작시간"></td>				
						</tr>
						<tr>
							<th>종료시간</th>
							<td><input name="outWrkEtime" id="outWrkEtime" placeholder="종료시간" title="종료시간">&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="checkbox" id="outWrkEnd" name="outWrkEnd" class="k-checkbox"><label class="k-checkbox-label" for="outWrkEnd">근무지 퇴근</label>
							</td>			
						</tr>
						<tr>
							<th>사유</th>
							<td><input type="text" name="outRm" class="k-textbox width100p"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn02" data-dismiss="modal">취소</button>
				<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_GotoOutWork();">저장</button>
			</div>
		</div>
	</div>
</div>
</form>

<!-- 근태관리 퇴근 -->
<form name="wrkEndForm" method="post">
<input type="hidden" name="wrktmId">
<div class="modal fade" id="modal03" tabindex="-1" role="dialog" aria-labelledby="modal03Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="modal03Label">근태관리(퇴근)</h4>
			</div>
			<div class="modal-body">
				<table class="tablewrite">
					<colgroup>
						<col>
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th>사원명</th>
							<td>
							<input type="text" name="userNmString" class="k-textbox width100p" value='<c:out value="${loginVO.name}"/>'>
							</td>
						</tr>
						<tr>
							<th>사유</th>
							<td><input type="text" name="rm" class="k-textbox width100p"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn01" data-dismiss="modal" onClick="fn_WrkEnd()">확인</button>
			</div>
		</div>
	</div>
</div>
</form>
	<!-- 결재함 전결 -->
	<div class="modal fade" id="modal05" tabindex="-1" role="dialog" aria-labelledby="modal05Label" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="modal05Label">결재요청문서(전결)</h4>
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
	<div class="modal fade" id="modal06" tabindex="-1" role="dialog" aria-labelledby="modal06Label" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="modal06Label">결재요청문서(승인)</h4>
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
	<div class="modal fade" id="modal07" tabindex="-1" role="dialog" aria-labelledby="modal07Label" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="modal07Label">결재요청문서(반려)</h4>
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

<!-- 인사발령 -->
<form name="appointForm" method="post">
<input type="hidden" name="uniqId" value='<c:out value="${userManageVO.uniqId }"/>'/>
<input type="hidden" name="changeDe">
<input type="hidden" name="afOrgnztNm">
<input type="hidden" name="afOfcpsNm">
<input type="hidden" name="emplNo" value='<c:out value="${userManageVO.emplNo}"/>'>
<div class="modal fade" id="modal08" tabindex="-1" role="dialog" aria-labelledby="modal08Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="modal08Label">인사관리>인사발령</h4>
			</div>
			<div class="modal-body">
    <table class="tablewrite">
      <colgroup>
    	<col>
      	<col class="width150">
      	<col>
      	<col class="width150">
      </colgroup>
      <tbody>
        <tr>
          <th>사원번호</th>
          <td ><input type="text" class="k-textbox width100p" readonly="readonly" value='<c:out value="${userManageVO.emplNo}"/>'></td>
          <th>성명</th>
          <td><input type="text" name="emplyrNm" class="k-textbox width100p" readonly="readonly" value='<c:out value="${userManageVO.emplyrNm}"/>'></td>
        </tr>
        <tr>
          <th>기존부서</th>
          <td>
          <input type="text" class="k-textbox width100p" readonly="readonly" value='<c:out value="${userManageVO.orgnztNm}"/>'>
		  </td>
          <th>발령부서</th>
          <td>
			<select name="afOrgnztId" id="afOrgnztId" class="select width150">
		  	<c:forEach var="result" items="${depart_result}" varStatus="status">
				<option value="${result.code}">${result.codeNm}</option>
			</c:forEach>
		  </select>
		</td>
        </tr>
        <tr>
          <th>기존직급</th>
          <td>
			<input type="text" class="k-textbox width100p" readonly="readonly" value='<c:out value="${userManageVO.ofcpsNm}"/>'>
		  </td>
          <th>발령직급</th>
          <td>
          	<select name="afOfcpsId" id="afOfcpsId" class="select width150">
				<c:forEach var="result" items="${postion_result}" varStatus="status">
				<option value="${result.code}">${result.codeNm}</option>
				</c:forEach>
			</select></td>
          </td>
        </tr>
        <tr>
          <th>비고</th>
          <td colspan="3"><input type="text" name="rm" class="k-textbox width100p"></td>
        </tr>
        <tr>
          <th>발령일자</th>
          <td colspan="3"><input type="date" name="de" id="de" class="datepicker width200"></td>
        </tr>
      </tbody>
    </table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn02" data-dismiss="modal">취소</button>
				<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_GotoSaveAppoint();">저장</button>
			</div>
		</div>
	</div>
</div>
</form>
<!-- 아이템등록 -->
<form name="itemForm" method="post">
<input type="hidden" name="itemCode">
<input type="hidden" name="itemName">
<input type="hidden" name="emplyrNo" value='<c:out value="${loginVO.emplyrNo}"/>'>
<div class="modal fade" id="modal09" tabindex="-1" role="dialog" aria-labelledby="modal09Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="moda109Label">공용품등록>신규</h4>
				<div class="essential fr">
				  <i class="fa fa-asterisk" aria-hidden="true"></i>필수입력항목
				</div>
			</div>
			<div class="modal-body">
    <table class="tablewrite">
      <colgroup>
    	<col>
      	<col class="width150">
      	<col>
      	<col class="width150">
      </colgroup>
      <tbody>
        <tr>          
          <th>공용품명<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td colspan="3">
          	<select id="itemNm" placeholder="공통품명을 입력 또는  선택하세요" style="width: 100%;" >
          		<c:forEach var="result" items="${itemNmList}" varStatus="status">
          			<option><c:out value="${result.itemName}"/></option>
          		</c:forEach> 
            </select>
          </td>
        </tr>
        <tr>
          <th>위치</th>
          <td colspan="3"><input type="text" name="location" class="k-textbox width100p"></td>
        </tr>
        <tr>
          <th>비고</th>
          <td colspan="3"><textarea class="k-textbox width100p height100" name="rm"></textarea></td>
        </tr>
      </tbody>
    </table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn02" data-dismiss="modal">취소</button>
				<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_GotoItemAdd();">저장</button>
			</div>
		</div>
	</div>
</div>
</form>

<!-- 아이템수정 -->
<div class="modal fade" id="modal10" tabindex="-1" role="dialog" aria-labelledby="modal10Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="moda110Label">공용품등록>수정</h4>
				<div class="essential fr">
				  <i class="fa fa-asterisk" aria-hidden="true"></i>필수입력항목
				</div>
			</div>
			<div class="modal-body">
    <table class="tablewrite">
      <colgroup>
    	<col>
      	<col class="width150">
      	<col>
      	<col class="width150">
      </colgroup>
      <tbody>
        <tr>
          <th>물품코드<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td ><input type="text" class="k-textbox width100p"></td>
          <th>공용품명<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td><input type="text" class="k-textbox width100p"></td>
        </tr>
        <tr>
          <th>등록자</th>
          <td><input type="text" class="k-textbox width100p"></td>
          <th>등록일</th>
          <td><input type="date" class="datepicker width200"></td>
        </tr>
        <tr>
          <th>위치</th>
          <td colspan="3"><input type="text" class="k-textbox width100p"></td>
        </tr>
        <tr>
          <th>비고</th>
          <td colspan="3"><textarea class="k-textbox width100p height100"></textarea></td>
        </tr>
      </tbody>
    </table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn02" data-dismiss="modal">취소</button>
				<button type="button" class="btn01" data-dismiss="modal">저장</button>
			</div>
		</div>
	</div>
</div>

<!-- 신규통장 -->
<form name="bankForm" method="post">
<div class="modal fade" id="modal11" tabindex="-1" role="dialog" aria-labelledby="modal11Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="modal11Label">통장등록</h4>
				<div class="essential fr">
				  <i class="fa fa-asterisk" aria-hidden="true"></i>필수입력항목
				</div>
			</div>
			<div class="modal-body">
    <table class="tablewrite">
      <colgroup>
    	<col>
      	<col class="width150">
      	<col>
      	<col class="width150">
      </colgroup>
      <tbody>
        <tr>
          <th>계좌번호<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td ><input type="text" name="bankAccountNum" class="k-textbox width100p"></td>
          <th>잔액<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td><input type="text" name="balance" class="k-textbox width100p"></td>
        </tr>
        <tr>
          <th>은행명(계좌명)<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td><input type="text" name="bankNm" class="k-textbox width100p"></td>
          <th>예금주<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td><input type="text" name="depositorNm" class="k-textbox width100p"></td>
        </tr>
        <tr>
          <th>계정명</th>
          <td><input type="text"  name="division" class="k-textbox width100p"></td>
          <th>사용여부<i class="fa fa-asterisk" aria-hidden="true"></i></th>
	  	  <td><input type="radio" name="useAt" value="Y" checked="">
	    	  <label for="contactChoice1">사용</label>
			  <input type="radio" name="useAt" value="N">
	  		  <label for="contactChoice2">미사용</label></td>
        </tr>
        <tr>
          <th>회사구분</th>
          <td colspan="3">
			<input type="hidden" name="cpCode">
			<input type="text" name="cpCodeName" class="k-textbox width100p" readonly>
			</td>
        </tr>
        <tr>
          <th>비고</th>
          <td colspan="3"><textarea name="rm" class="k-textbox width100p height100"></textarea></td>
        </tr>
      </tbody>
    </table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn02" data-dismiss="modal">취소</button>
				<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_GotoSaveBank()">저장</button>
			</div>
		</div>
	</div>
</div>
</form>

<!-- 통장수정 -->
<div class="modal fade" id="modal12" tabindex="-1" role="dialog" aria-labelledby="modal12Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="moda112Label">통장수정</h4>
				<div class="essential fr">
				  <i class="fa fa-asterisk" aria-hidden="true"></i>필수입력항목
				</div>
			</div>
			<div class="modal-body">
    <table class="tablewrite">
      <colgroup>
    	<col>
      	<col class="width150">
      	<col>
      	<col class="width150">
      </colgroup>
      <tbody>
        <tr>
          <th>계좌번호<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td ><input type="text" class="k-textbox width100p"></td>
          <th>잔액<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td><input type="text" class="k-textbox width100p"></td>
        </tr>
        <tr>
          <th>계좌명</th>
          <td><input type="text" class="k-textbox width100p"></td>
          <th>예금주</th>
          <td><input type="text" class="k-textbox width100p"></td>
        </tr>
        <tr>
          <th>구분</th>
          <td><input type="text" class="k-textbox width100p"></td>
          <th>사용여부</th>
	  	  <td><input type="radio" name="bankBookUpdtRadio" value="Y" checked="">
	    	  <label for="contactChoice1">사용</label>
			  <input type="radio" name="bankBookUpdtRadio" value="Y">
	  		  <label for="contactChoice2">미사용</label></td>
        </tr>
        <tr>
          <th>비고</th>
          <td colspan="3"><textarea class="k-textbox width100p height100"></textarea></td>
        </tr>
      </tbody>
    </table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn02" data-dismiss="modal">취소</button>
				<button type="button" class="btn01" data-dismiss="modal">저장</button>
			</div>
		</div>
	</div>
</div>

<!-- 신규카드 -->
<form name="cardForm" method="post">
<input type="hidden" name="bankAccountNum">
<input type="hidden" name="bankNm">
<div class="modal fade" id="modal13" tabindex="-1" role="dialog" aria-labelledby="modal13Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="modal13Label">카드등록</h4>
				<div class="essential fr">
				  <i class="fa fa-asterisk" aria-hidden="true"></i>필수입력항목
				</div>
			</div>
			<div class="modal-body">
    <table class="tablewrite">
      <colgroup>
    	<col>
      	<col class="width150">
      	<col>
      	<col class="width150">
      </colgroup>
      <tbody>
        <tr>
          <th>카드번호<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td><input type="text" class="k-textbox width100p" name="cardNum" ></td>
          <th>담당자<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td>
          <select name="depositorNm" class="select width150">
          <c:forEach var="result" items="${emplyr_list}" varStatus="status">
				<option value="${result.emplNo}">${result.userNm}(${result.emplNo})</option>
			</c:forEach>
          </select>
          </td>
        </tr>
        <tr>
          <th>카드명<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td><input type="text" class="k-textbox width100p" name="cardNm"></td>
          <th>결제계좌명<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td>
          <select class="select width150" id="bankselect" onchange="javascript:fn_selectBank(this)">
          	<option selected value=''>--선택하세요--</option>
          	<c:forEach var="result" items="${bank_list}" varStatus="status">
				<option value="${result.bankAccountNum}">${result.bankNm}</option>
			</c:forEach>
          </select>
          </td>
        </tr>
        <tr>
          <th>만료일자</th>
          <td><input id="monthpicker" class="datepicker width200" name="endPnttm"></td>
          <th>사용여부<i class="fa fa-asterisk" aria-hidden="true"></i></th>
	  	  <td><input type="radio" name="useAt" value="Y" checked="">
	    	  <label for="contactChoice1">사용</label>
			  <input type="radio" name="useAt" value="N">
	  		  <label for="contactChoice2">미사용</label></td>
        </tr>
        <tr>
          <th>회사구분</th>
          <td colspan="3">
          <input type="hidden" name="cpCode">
          <input type="text" name="cpCodeName" class="k-textbox width100p" readonly>
          </td>
        </tr>
        <tr>
          <th>비고</th>
          <td colspan="3"><textarea class="k-textbox width100p height100" name="rm"></textarea></td>
        </tr>
      </tbody>
    </table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn02" data-dismiss="modal">취소</button>
				<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_GotoSaveCard();">저장</button>
			</div>
		</div>
	</div>
</div>
</form>

<!-- 전체사원연차관리(추가) -->
<form name="allvctForm" method="post">
<input type="hidden" name="endde">
<input type="hidden" name="bgnde">

<div class="modal fade" id="modal14" tabindex="-1" role="dialog" aria-labelledby="modal14Label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="moda112Label">전체사원 연차추가</h4>
				<div class="essential fr">
				  <i class="fa fa-asterisk" aria-hidden="true"></i>필수입력항목
				</div>
			</div>
			<div class="modal-body">
    <table class="tablewrite">
      <colgroup>
    	<col>
      	<col class="width150">
      	<col>
      	<col class="width150">
      </colgroup>
      <tbody>
        <tr>
          <th>시작일<i class="fa fa-asterisk" aria-hidden="true"></i></th>
          <td><input class="datepicker width200" name="bgnde1" id="bgnde1"></td>
          <th>종료일<i class="fa fa-asterisk" aria-hidden="true"></i></th>
           <td><input class="datepicker width200" name="endde1" id="endde1"></td>
        </tr>
        <tr>
          <th>휴가사유</th>
          <td colspan="3"><textarea class="k-textbox width100p" name="vcatnResn"></textarea></td>
        </tr>
      </tbody>
    </table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn02" data-dismiss="modal">취소</button>
				<button type="button" class="btn01" data-dismiss="modal" onclick="javascript:fn_GotoVctAdd();">저장</button>
			</div>
		</div>
	</div>
</div>
</form>

<div aria-hidden="true" class="k-calendar-container k-popup k-group k-reset" data-role="popup" style="display: none; position: absolute;"></div>
<div class="k-list-container k-list-scroller k-popup k-group k-reset" unselectable="on" data-role="popup" style="display: none; position: absolute;">
	<ul tabindex="-1" role="listbox" aria-hidden="true" unselectable="on" class="k-list k-reset"></ul>
</div>

<div aria-hidden="true" class="k-calendar-container k-popup k-group k-reset" data-role="popup" style="display: none; position: absolute;"></div>
<div class="k-list-container k-list-scroller k-popup k-group k-reset" unselectable="on" data-role="popup" style="display: none; position: absolute;">
	<ul tabindex="-1" role="listbox" aria-hidden="true" unselectable="on" class="k-list k-reset"></ul>
</div>

<div aria-hidden="true" class="k-calendar-container k-popup k-group k-reset" data-role="popup" style="display: none; position: absolute;"></div>
<div class="k-list-container k-list-scroller k-popup k-group k-reset" unselectable="on" data-role="popup" style="display: none; position: absolute;">
	<ul tabindex="-1" role="listbox" aria-hidden="true" unselectable="on" class="k-list k-reset"></ul>
</div>	

<script>
$(document).ready(function() {
	$("#de").attr("readonly", true);
	$("#monthpicker").attr("readonly", true);
	fn_initSearchDatePickerTime("outWrkStime", "outWrkEtime");
	fn_initSearchDatePicker("bgnde1", "endde1");
});
</script>