<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
    <style>
	   .k-scheduler-table td{height:116px; !important}
	   .k-scheduler-header-all-day td{height:auto; !important} 
    </style>
<script type="text/javascript">
	$(document).ready(function(){
		$("input[type=checkbox]").on("click",function(){
			if($(this).attr("id") == 'myTaskChk'){
				
				if($(this).is(":checked")){
					$("input[type=checkbox]").each(function(){
						if($(this).attr("id") != 'myTaskChk'){
							$(this).prop("checked",false);
						}
					});
				}
			}else{
				$("#myTaskChk").prop("checked",false);
				if($(this).attr("id") == 'checkAll'){
					if($(this).is(":checked")){
						$("input[type=checkbox]").each(function(){
							if($(this).attr("id") != 'myTaskChk'){
								$(this).prop("checked",true);
							}
						});
					}else{
						$("input[type=checkbox]").each(function(){
							if($(this).attr("id") != 'myTaskChk'){
								$(this).prop("checked",false);
							}
						});
					}
				}else{
					var cntAll = 0;
					var cntChk = 0;
					$("input[type=checkbox]").each(function(){	
						if($(this).attr("id") != 'myTaskChk' && $(this).attr("id") != 'checkAll' && $(this).attr("id") != 'check1'){
							cntAll++;
							if($(this).is(":checked")){
								cntChk++;
								$("#check1").prop("checked",true);
							}
						}
					});
					if(cntAll == cntChk){
						$("#checkAll").prop("checked",true);
					}else{
						$("#checkAll").prop("checked",false);
					}
					if(cntChk == 0){
						$("#check1").prop("checked",false);
					}
				}	
			}
			if($("input[type=checkbox]:checked").length == 0){
				$("input[name=searchKeyword]").val("Y");
			}
			document.frm.action="<c:url value='/com/inc/myPage.do'/>"
			document.frm.submit();
		});
		var cntAll = 0;
		var cntChk = 0;
		$("input[type=checkbox]").each(function(){	
			if($(this).attr("id") != 'myTaskChk' && $(this).attr("id") != 'checkAll' && $(this).attr("id") != 'check1'){
				cntAll++;
				if($(this).is(":checked")){
					cntChk++;
					$("#check1").prop("checked",true);
				}
			}
		});
		if(cntAll == cntChk){
			$("#checkAll").prop("checked",true);
		}else{
			$("#checkAll").prop("checked",false);
		}
		if(cntChk == 0){
			$("#check1").prop("checked",false);
		}
	});
	
	function fn_goDetail(sKey,tKey){
		document.subForm.schdulId.value=sKey;
		document.subForm.taskId.value=tKey;
		document.subForm.action="<c:url value='/com/task/taskScheduleModify.do'/>";
		document.subForm.submit();
	}
	
	function fn_egov_inqire_notice(nttId) {

		document.noticForm.nttId.value = nttId;	
		document.noticForm.bbsId.value = "BBSMSTR_000000000001";

		document.noticForm.action = "<c:url value='/com/business/bbs/noticeView.do'/>";
		document.noticForm.submit();
	}
	
</script>


<form name="noticForm" method="post">
<input type="hidden" name="nttId">
<input type="hidden" name="bbsId">
</form>

<form name="subForm" method="post">
	<input type="hidden" name="taskId" />
	<input type="hidden" name="schdulId" />
</form>
<div id="mainwrap">
	<div class="dashboard_side">
		<ul class="qlink">			
			<li><a href="<c:url value='/com/epay/draft/epayDraftInfoList.do'/>"><i class="far fa-clipboard"></i><br><span>진행중문서<br><c:out value='${allCnt.prgsDoc }'/>건</span></a>
			</li>
			<li><a href="<c:url value='/com/epay/appr/epayApprInfoList.do'/>"><i class="fas fa-paste"></i><br><span>결재할문서<br><c:out value='${allCnt.waitApproval }'/>건</span></a>
			</li>
			<li><a href="<c:url value='/com/task/taskList.do'/>"><i class="far fa-calendar-alt"></i><br><span>진행중일정<br><c:out value='${allCnt.prgsSchdul }'/>건</a>
				</span>
			</li>
			<li><a href="<c:url value='/com/task/taskList.do'/>"><i class="fas fa-folder-open"></i><br><span>진행중과제<br><c:out value='${allCnt.prgsTask }'/>건</a>
				</span>
			</li>
		</ul>
		<div class="clear"></div>
		<div class="notice_latest">
			<h3>공지사항</h3>
			<div class="notice_list">
			<c:forEach var="result" items="${boardList}" varStatus="status">
				<p><a href="javascript:fn_egov_inqire_notice('<c:out value='${result.nttId}'/>')"><c:out value="${result.nttSj }" /><span><c:out value="${result.frstRegisterPnttm }" /></span></a>
				</p>
			</c:forEach>
			</div>
		</div>
		<div class="main_sc">
			<h3>주요일정</h3>
			<div class="main_sc_list">
				<c:forEach var="list" items="${leftSchdulList }" varStatus="status">
					<c:choose>
						<c:when test="${list.schdulIpcrCode eq 'Y' }">
							<p onclick="javascript:fn_goDetail('<c:out value='${list.kValue }'/>','<c:out value='${list.taskId }'/>');"><span class="main_sc_color01"></span><c:out value="${list.nm }"/></p>
						</c:when>
						<c:when test="${list.mainDept eq 'DEPT01' }">
							<p onclick="javascript:fn_goDetail('<c:out value='${list.kValue }'/>','<c:out value='${list.taskId }'/>');"><span class="main_sc_color02"></span><c:out value="${list.nm }"/></p>
						</c:when>
						<c:when test="${list.mainDept eq 'DEPT02' }">
							<p onclick="javascript:fn_goDetail('<c:out value='${list.kValue }'/>','<c:out value='${list.taskId }'/>');"><span class="main_sc_color03"></span><c:out value="${list.nm }"/></p>
						</c:when>
						<c:when test="${list.mainDept eq 'DEPT03' }">
							<p onclick="javascript:fn_goDetail('<c:out value='${list.kValue }'/>','<c:out value='${list.taskId }'/>');"><span class="main_sc_color04"></span><c:out value="${list.nm }"/></p>
						</c:when>
						<c:when test="${list.mainDept eq 'DEPT04' }">
							<p onclick="javascript:fn_goDetail('<c:out value='${list.kValue }'/>','<c:out value='${list.taskId }'/>');"><span class="main_sc_color05"></span><c:out value="${list.nm }"/></p>
						</c:when>
						<c:when test="${list.mainDept eq 'DEPT05' }">
							<p onclick="javascript:fn_goDetail('<c:out value='${list.kValue }'/>','<c:out value='${list.taskId }'/>');"><span class="main_sc_color06"></span><c:out value="${list.nm }"/></p>
						</c:when>
						<c:when test="${list.mainDept eq 'DEPT06' }">
							<p onclick="javascript:fn_goDetail('<c:out value='${list.kValue }'/>','<c:out value='${list.taskId }'/>');"><span class="main_sc_color07"></span><c:out value="${list.nm }"/></p>
						</c:when>
						<c:when test="${list.mainDept eq 'DEPT07' }">
							<p onclick="javascript:fn_goDetail('<c:out value='${list.kValue }'/>','<c:out value='${list.taskId }'/>');"><span class="main_sc_color08"></span><c:out value="${list.nm }"/></p>
						</c:when>
						<c:when test="${list.mainDept eq 'DEPT08' }">
							<p onclick="javascript:fn_goDetail('<c:out value='${list.kValue }'/>','<c:out value='${list.taskId }'/>');"><span class="main_sc_color09"></span><c:out value="${list.nm }"/></p>
						</c:when>						
					</c:choose>
				</c:forEach>
			</div>
		</div>
		<form name="frm" method="post">
			<input type="hidden" name="searchKeyword" />
			<div class="group_sc">
				<h3><label><input type="checkbox" id="checkAll">전체일정</h3>
				<div class="group_sc_list">
					<p class="gs1"><input type="checkbox" class="ch" id="check1" name="searchCondition">
						<label for="check1">중요일정</label>
					</p>
					<p class="gs2">
						<input type="checkbox" class="ch" id="check2" name="searchCondition" value="DEPT01" <c:if test="${fn:contains(searchVO.searchCondition,'DEPT01') }">checked="checked"</c:if>>
						<label for="check2">영업팀</label>
					</p>
					<p class="gs3">
						<input type="checkbox" class="ch" id="check3" name="searchCondition" value="DEPT02" <c:if test="${fn:contains(searchVO.searchCondition,'DEPT02') }">checked="checked"</c:if>>
						<label for="check3">경영지원팀</label>
					</p>
					<p class="gs4">
						<input type="checkbox" class="ch" id="check4" name="searchCondition" value="DEPT03" <c:if test="${fn:contains(searchVO.searchCondition,'DEPT03') }">checked="checked"</c:if>>
						<label for="check4">개발부</label>
					</p>
					<p class="gs5">
						<input type="checkbox" class="ch" id="check5" name="searchCondition" value="DEPT04" <c:if test="${fn:contains(searchVO.searchCondition,'DEPT04') }">checked="checked"</c:if>>
						<label for="check5">TS/솔루션팀</label>
					</p>
					<p class="gs6">
						<input type="checkbox" class="ch" id="check6" name="searchCondition" value="DEPT05" <c:if test="${fn:contains(searchVO.searchCondition,'DEPT05') }">checked="checked"</c:if>>
						<label for="check6">TS/인프라팀</label>
					</p>
				</div>
				<h3 class="bh3"><label><input type="checkbox" name="searchCondition" id="myTaskChk" value="Y" <c:if test="${loginVO.emplyrNo eq  searchVO.searchCondition}">checked="checked"</c:if>>나의 과제</h3>
			</div>
		</form>
	</div>

	<div id="scheduler"></div>
	<script>
	 var MORE_BUTTON_TEMPLATE = kendo.template(
     '<div style="width:#=width#px;left:#=left#px;top:#=top#px" class="k-more-events k-button"><span style="font-size:8pt; margin-top: 0;"> #=getEventCountForRange(startSlot, endSlot, rowsCount)# 개 스케줄</span></div>');


 function getEventCountForRange(startSlot, endSlot, rowsCount) {
     var scheduler = $(startSlot.element).closest("[data-role=scheduler]").getKendoScheduler();

     var currentTimezoneOffset = kendo.date.MS_PER_MINUTE * new Date().getTimezoneOffset();
     var rangeStart = new Date(startSlot.start + currentTimezoneOffset);
     var rangeEnd = new Date(endSlot.end + currentTimezoneOffset);

     return scheduler.occurrencesInRange(rangeStart, rangeEnd).length - rowsCount + 1;
 }

 var CustomMonthView = kendo.ui.MonthView.extend({
     options: {
         name: "CustomMonthView",
         title: "Month Week"
     },
     name: "CustomMonthView",
     _positionEvent: function(slotRange, element, group) {
         var eventHeight = this.options.eventHeight;
         var startSlot = slotRange.start;

         if (slotRange.start.offsetLeft > slotRange.end.offsetLeft) {
             startSlot = slotRange.end;
         }

         var startIndex = slotRange.start.index;
         var endIndex = slotRange.end.index;
         var eventCount = startSlot.eventCount;
         var events = kendo.ui.SchedulerView.collidingEvents(slotRange.events(), startIndex, endIndex);
         var rightOffset = startIndex !== endIndex ? 5 : 4;

         events.push({
             element: element,
             start: startIndex,
             end: endIndex
         });

         var rows = kendo.ui.SchedulerView.createRows(events);

         for (var idx = 0, length = Math.min(rows.length, eventCount); idx < length; idx++) {
             var rowEvents = rows[idx].events;
             var eventTop = startSlot.offsetTop + startSlot.firstChildHeight + idx * eventHeight + 3 * idx + "px";

             for (var j = 0, eventLength = rowEvents.length; j < eventLength; j++) {
                 rowEvents[j].element[0].style.top = eventTop;
             }
         }


         if (rows.length > eventCount) {
             for (var slotIndex = startIndex; slotIndex <= endIndex; slotIndex++) {
                 var collection = slotRange.collection;

                 var slot = collection.at(slotIndex);

                 if (slot.more) {
                     return;
                 }

                 slot.more = $(MORE_BUTTON_TEMPLATE({
                     startSlot: slotRange.start,
                     endSlot: slotRange.end,
                     rowsCount: rows.length,
                     ns: kendo.ns,
                     start: slotIndex,
                     end: slotIndex,
                     width: slot.clientWidth - 2,
                     left: slot.offsetLeft + 2,
                     top: slot.offsetTop + slot.firstChildHeight + eventCount * eventHeight + 3 * eventCount
                 }));


                 this.content[0].appendChild(slot.more[0]);
             }

         } else {
             slotRange.addEvent({
                 element: element,
                 start: startIndex,
                 end: endIndex,
                 groupIndex: startSlot.groupIndex
             });

             element[0].style.width = slotRange.innerWidth() - rightOffset + "px";
             element[0].style.left = startSlot.offsetLeft + 2 + "px";
             element[0].style.height = eventHeight + "px";

             group._continuousEvents.push({
                 element: element,
                 uid: element.attr(kendo.attr("uid")),
                 start: slotRange.start,
                 end: slotRange.end
             });

             element.appendTo(this.content);
         }
     },
 });
		$( function () {
		    function scheduler_moveStart(e) {
		    	 e.preventDefault();
		    }

		    function scheduler_move(e) {
		    	 e.preventDefault();
		    }

		    function scheduler_moveEnd(e) {
		    	 e.preventDefault();
		    }
		    function scheduler_change(e) {
		    	  var start = e.start; //selection start date
		          var end = e.end; //selection end date
		          var slots = e.slots; //list of selected slots
		          var events = e.events; //list of selected Scheduler events
	        	  if(events.length != 0){
	        		  var key = events[events.length - 1].kValue;
			          var taskId = events[events.length - 1].taskId;
			          var gbn = events[events.length - 1].gbn;
	        	  }
	        	 	          
		          if (events.length) {
		        	  if(gbn == 'task'){
		        		  document.subForm.taskId.value=key;
		        		  document.subForm.action="<c:url value='/com/task/taskDetail.do'/>";
		        	  }
		        	  else if(gbn == 'schdul'){
		        		  document.subForm.schdulId.value=key;
		        		  document.subForm.taskId.value=taskId;
		        		  document.subForm.action="<c:url value='/com/task/taskScheduleModify.do'/>";
		        	  }
		        	  document.subForm.submit();
		              //e.preventDefault();
		          }

		    }
		    function scheduler_resizeStart(e) {
		    	e.preventDefault();
		    }

		    function scheduler_resize(e) {
		    	e.preventDefault();
		    }

		    function scheduler_resizeEnd(e) {
		    	e.preventDefault();
		    }
		    
		    function scheduler_navigate(e) {
		    	if(e.view == 'month'){

    			$('.k-more-events span').each(function(){
    				 
    	     			$(this).text('');
    	          }); 

     
                }
		    }
			$( "#scheduler" ).kendoScheduler( {
				dataSource:[
					     		           	
						 	<c:forEach var="result" items="${schdulList}" varStatus="status">                           
								{
									id:<c:out value='${status.count}'/>,
									title:"<c:out value='${result.nm}'/>",
									start:new Date("<c:out value='${result.st}'/>"),
									end:new Date("<c:out value='${result.en}'/>"),
									gbn:"<c:out value='${result.gbn}'/>",
									kValue:"<c:out value='${result.kValue}'/>",
									code:"<c:out value='${result.code}'/>",
									mainDept:"<c:out value='${result.mainDept}'/>",
									type:"<c:out value='${result.type}'/>",
									taskId:"<c:out value='${result.taskId}'/>"
									
								},
							</c:forEach> 
								
				],
				resources: [
				            {
				                field: "type",
				                dataSource: [
				                    {  value: "DEPT01", color: "#efad4d" },
				                    {  value: "DEPT02", color: "#5bb85b" },
				                    {  value: "DEPT03", color: "#347ab8" },
				                    {  value: "DEPT04", color: "#ad6cd5" },
				                    {  value: "DEPT05", color: "#9e7d50" },
				                    {  value: "DEPT06", color: "#9e7d50" },
				                    {  value: "schdul", color: "#d9544f" }
				                ]
				            }
				        ],
				height: 820,
				views: [
					"day", 
					"week",
				/* 	"month",
					{
						type: "month",
						selected: true
					}, */
					{
						type : "CustomMonthView",
						selected: true
						
					}
				],
				 selectable: true,
				 editable: false,
				//timezone: "Etc/UTC",
				moveStart: scheduler_moveStart,
		        move: scheduler_move,
		        moveEnd: scheduler_moveEnd,
		        change: scheduler_change,
		        resizeStart: scheduler_resizeStart,
		        resize: scheduler_resize,
		        resizeEnd: scheduler_resizeEnd,
		        navigate: scheduler_navigate,
			} );
		} );
	</script>
</div>

</body>
</html>