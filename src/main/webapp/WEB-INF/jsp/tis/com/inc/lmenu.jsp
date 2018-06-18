<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="menuUrl" value="${fn:split(pageContext.request.requestURI, '/')}" />
<c:set var="menuLevel0" value="${menuUrl[fn:length(menuUrl)-4]}" />
<c:set var="menuLevel1" value="${menuUrl[fn:length(menuUrl)-3]}" />
<c:set var="menuLevel2" value="${menuUrl[fn:length(menuUrl)-2]}" />
<c:set var="menuLevel3" value="${menuUrl[fn:length(menuUrl)-1]}" />
<c:set var="menunotic" value="${fn:substring(menuLevel3, 0, 6)}" />
<c:set var="affiliationId" value="${sessionScope.affiliationId}" />

<aside class="sidebar" style="left: 0">
	<div class="systemlogo"><a href="<c:url value='/com/main.do'/>"><strong>(주)이튜</strong>통합정보시스템</a>
	</div>
	<!--<div class="memberinfo"><span class="mname">김수현 팀장</span><br><span class="mlog">최근접속: 16/06/08 17:16:34</span></div>-->
	<div id="leftside-navigation" class="nano">
		<ul class="nano-content">
			<li class="sub-menu <c:if test="${fn:contains(menuLevel3,'mypage') }">active open</c:if>" >
				<a href="<c:url value='/com/inc/myPage.do'/>"><i class="far fa-calendar-alt"></i><span>마이페이지</span></a>
			</li>
			<li id="1" class="sub-menu <c:if test="${menuLevel0 != 'manager' && menuLevel1 == 'epay'}">active open</c:if>">
				<a href="javascript:fnNvClickEvent(1);"><i class="fas fa-paste"></i><span>전자결재</span><i class="fas fa-angle-down pull-right"></i></a>
				<ul>
					<li <c:if test="${menuLevel2 == 'draft'}">class="active"</c:if>><a href="<c:url value='/com/epay/draft/epayDraftInfoList.do"'/>">기안함<!-- 진행중 문서가 있을경우만 표시 -->
					<c:import url="/com/epay/draft/draftBoxCnt.do" charEncoding="utf-8">
					<c:param name="leftmenu" value="draft" />
					</c:import>
					</a>
					</li>
					<li <c:if test="${menuLevel2 == 'appr' }">class="active"</c:if>><a href="<c:url value='/com/epay/appr/epayApprInfoList.do"'/>">결재함<!-- 결재할 문서가 있을경우만 표시 -->
					<c:import url="/com/epay/appr/apprBoxCnt.do" charEncoding="utf-8">
					<c:param name="leftmenu" value="appr" />
					</c:import>
					</a>
					</li>
				</ul>
			</li>
			<li class="<c:if test="${fn:contains(menuLevel2,'task') }">active open</c:if>">
				<a href="<c:url value='/com/task/taskList.do'/>"><i class="fas fa-folder-open"></i><span>과제관리</span></a>
			</li>
			<li id="2" class="sub-menu <c:if test="${menuLevel1 == 'business'}">active open</c:if>">
				<a href="javascript:fnNvClickEvent(2);"><i class="fas fa-tasks"></i><span>업무관리</span><i class="fas fa-angle-down pull-right"></i></a>
				<ul>
					<li <c:if test="${menuLevel2 == 'bbs' && menunotic == 'notice'}">class="active"</c:if>><a href="<c:url value='/com/business/bbs/noticeList.do'/>?bbsId=BBSMSTR_000000000001">공지사항<!-- 최근 24시간 사이 등록된 공지사항 수만 노출 -->
					<c:import url="/com/business/bbs/noticeCnt.do" charEncoding="utf-8">
					<c:param name="leftmenu" value="1" />
					</c:import>
					</a>
					</li>
					<li <c:if test="${menuLevel2 == 'attendance'}">class="active"</c:if>><a href="<c:url value='/com/business/attendance/attendanceList.do'/>">근태관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'holiday'}">class="active"</c:if>><a href="javascript:alert('시스템 준비 중입니다.');">연차관리</a>
					</li>
					<%-- <li <c:if test="${menuLevel2 == 'holiday'}">class="active"</c:if>><a href="<c:url value='/com/business/holiday/holidayList.do'/>">연차관리</a>
					</li> --%>
					<li <c:if test="${menuLevel2 == 'bbs' && menunotic != 'notice'}">class="active"</c:if>><a href="<c:url value='/com/business/bbs/emplyrRuleList.do'/>?bbsId=BBSMSTR_000000000011">노무</a>
					</li>
					<li <c:if test="${menuLevel2 == 'item'}">class="active"</c:if>><a href="<c:url value='/com/business/item/itemList.do'/>">공용품관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'company'}">class="active"</c:if>><a href="<c:url value='/com/business/company/companyEmplyrList.do'/>">기관/업체 관리</a>
					</li>
				</ul>
			</li>
			<c:if test="${loginVO.manageAt == 'Y' }">
			<li id="3" class="sub-menu <c:if test="${menuLevel1 == 'financial' && affiliationId == 'I'}">active open</c:if>">
				<a href="javascript:fnNvClickEvent(3);"><i class="fas fa-calculator"></i><span>회계관리(이튜)</span><i class="fas fa-angle-down pull-right"></i></a>
				<ul>
					<li <c:if test="${menuLevel2 == 'profitLoss' && affiliationId == 'I'}">class="active"</c:if>><a href="<c:url value='/com/financial/profitLoss/profitLossList.do?affiliationId=I'/>">과제손익관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'sales' && affiliationId == 'I'}">class="active"</c:if>><a href="<c:url value='/com/financial/sales/salesManageList.do?affiliationId=I'/>">매출관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'purchase' && affiliationId == 'I'}">class="active"</c:if>><a href="<c:url value='/com/financial/purchase/purchaseManageList.do?affiliationId=I'/>">매입관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'cost' && affiliationId == 'I'}">class="active"</c:if>><a href="<c:url value='/com/financial/cost/costManageList.do?affiliationId=I'/>">경비관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'nonOperProfit' && affiliationId == 'I'}">class="active"</c:if>><a href="<c:url value='/com/financial/nonOperProfit/nonOperProfitList.do?affiliationId=I'/>">영업외이익관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'salary' && affiliationId == 'I'}">class="active"</c:if>><a href="<c:url value='/com/financial/salary/salaryList.do?affiliationId=I'/>">급여관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'periodicSettle' && affiliationId == 'I'}">class="active"</c:if>><a href="<c:url value='/com/financial/periodicSettle/expense.do?affiliationId=I'/>">정기정산관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'laterSettle' && affiliationId == 'I'}">class="active"</c:if>><a href="<c:url value='/com/financial/laterSettle/foodExpense.do?affiliationId=I'/>">후불정산관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'deposit' && affiliationId == 'I'}">class="active"</c:if>><a href="<c:url value='/com/financial/deposit/deposit.do?affiliationId=I'/>">보증금관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'bankBook' && affiliationId == 'I'}">class="active"</c:if>><a href="<c:url value='/com/financial/bankBook/bankBook.do?affiliationId=I'/>">통장/카드관리</a>
					</li>
				</ul>
			</li>			
			<li id="4" class="sub-menu <c:if test="${menuLevel1 == 'financial' && affiliationId == 'S'}">active open</c:if>">
				<a href="javascript:fnNvClickEvent(4);"><i class="fas fa-calculator"></i><span>회계관리(에스메이커)</span><i class="fas fa-angle-down pull-right"></i></a>
				<ul>
					<li <c:if test="${menuLevel2 == 'profitLoss' && affiliationId == 'S'}">class="active"</c:if>><a href="<c:url value='/com/financial/profitLoss/profitLossList.do?affiliationId=S'/>">과제손익관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'sales' && affiliationId == 'S'}">class="active"</c:if>><a href="<c:url value='/com/financial/sales/salesManageList.do?affiliationId=S'/>">매출관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'purchase' && affiliationId == 'S'}">class="active"</c:if>><a href="<c:url value='/com/financial/purchase/purchaseManageList.do?affiliationId=S'/>">매입관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'cost' && affiliationId == 'S'}">class="active"</c:if>><a href="<c:url value='/com/financial/cost/costManageList.do?affiliationId=S'/>">경비관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'nonOperProfit' && affiliationId == 'S'}">class="active"</c:if>><a href="<c:url value='/com/financial/nonOperProfit/nonOperProfitList.do?affiliationId=S'/>">영업외이익관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'salary' && affiliationId == 'S'}">class="active"</c:if>><a href="<c:url value='/com/financial/salary/salaryList.do?affiliationId=S'/>">급여관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'periodicSettle' && affiliationId == 'S'}">class="active"</c:if>><a href="<c:url value='/com/financial/periodicSettle/expense.do?affiliationId=S'/>">정기정산관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'laterSettle' && affiliationId == 'S'}">class="active"</c:if>><a href="<c:url value='/com/financial/laterSettle/foodExpense.do?affiliationId=S'/>">후불정산관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'deposit' && affiliationId == 'S'}">class="active"</c:if>><a href="<c:url value='/com/financial/deposit/deposit.do?affiliationId=S'/>">보증금관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'bankBook' && affiliationId == 'S'}">class="active"</c:if>><a href="<c:url value='/com/financial/bankBook/bankBook.do?affiliationId=S'/>">통장/카드관리</a>
					</li>
				</ul>
			</li>			
			<li id="5" class="sub-menu <c:if test="${menuLevel1 == 'manager' || menuLevel1 == 'uss'}">active open</c:if>">
				<a href="javascript:fnNvClickEvent(5);"><i class="fas fa-user"></i><span>관리자</span><i class="fas fa-angle-down pull-right"></i></a>
				<ul>
					<li <c:if test="${menuLevel2 == 'personnelAppointment' || menuLevel2 == 'umt'}">class="active"</c:if>><a href="<c:url value='/com/member/EgovUserManage.do'/>">인사관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'managerAttendance'}">class="active"</c:if>><a href="<c:url value='/com/manager/managerAttendance/managerAttendanceList.do'/>">근태관리</a>
					</li>
					<%-- <li <c:if test="${menuLevel2 == 'managerHoliday'}">class="active"</c:if>><a href="<c:url value='/com/manager/managerHoliday/managerHolidayList.do'/>">연차관리</a>
					</li> --%>
					<li <c:if test="${menuLevel2 == 'managerHoliday'}">class="active"</c:if>><a href="javascript:alert('시스템 준비 중입니다.');">연차관리</a>
					</li>
					<li <c:if test="${menuLevel2 == 'epay'}">class="active"</c:if>><a href="<c:url value='/com/manager/epay/managerEpayDraftInfoList.do'/>">전자결제함</a>
					</li>
				</ul>
			</li>
			</c:if>
		</ul>
	</div>
</aside>
<script>
	$("#leftside-navigation .sub-menu > a").click(function(e){
			$("#leftside-navigation ul ul").slideUp(), $(this).next().is( ":visible" ) || $(this).next().slideDown(),
				e.stopPropagation()
	})	
		function fnNvClickEvent(v){
			
				if( !$("#"+v+"").hasClass("open")){
					$("#"+v+"").addClass('active').siblings().removeClass('active');
					$("#"+v+"").addClass('open').siblings().removeClass('open');
				}else{
					$("#"+v+"").removeClass('active');
					$("#"+v+"").removeClass('open');
				}
			}
</script>