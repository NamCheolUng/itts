<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	pageContext.setAttribute("br", "<br/>");
	pageContext.setAttribute("cn", "\n");
%>
<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<style>
.k-button{display: none !important;}
.k-task-dot {display: none !important;}
.k-i-close{display: none !important;}
</style>
<script type="text/javascript">
	function fn_modity(){
		document.frm.action="<c:url value='/com/task/taskModify.do'/>";
		document.frm.submit();
	}
	function fn_detail(taskId){
		document.frm.taskId.value=taskId;
		document.frm.action="<c:url value='/com/task/taskDetail.do'/>";
		document.frm.submit();
	}
	
	function fn_taskMngerPopup(){
		window.open("<c:url value='/com/task/popup/taskMngerPopup.do'/>?taskId="+document.frm.taskId.value,"","width=800px,height=800px");
	}
	
	function fn_taskDevelEnvirPopup(){
		window.open("<c:url value='/com/task/popup/taskDevelEnvirPopup.do'/>?taskId="+document.frm.taskId.value,"","width=800px,height=800px");
	}
	
	function fn_taskDevelEnvirSubPopup(){
		window.open("<c:url value='/com/task/popup/taskDevelEnvirSubPopup.do'/>?taskId="+document.frm.taskId.value,"","width=800px,height=800px");
	}
	
	function fn_taskStepPopup(){
		var params = ""
		if(document.frm.taskStepId.value != ''){
			params = "?taskId="+document.frm.taskId.value+"&taskStepId="+document.frm.taskStepId.value;
		}else{
			params = "?taskSdate=<c:out value='${result.taskSdate}'/>&taskEdate=<c:out value='${result.taskEdate}'/>"
		}
		window.open("<c:url value='/com/task/popup/taskStepPopup.do'/>"+params,"","width=800px,height=800px");
	}
	
	function fn_taskEnvReturnVal(os,lang,rm){
		/* $("#os").text("");
		$("#os").text(os);
		$("#lang").text("");
		$("#lang").text(lang);
		$("#rm_m").text("");
		$("#rm_m").text(rm); */
		location.reload();

	}
	
	function fn_taskEnvSubReturnVal(sept,nm,expln,rm){
	/* var template  = "<tr>"
		template +=    "<td>";
		if(sept == '1'){
			template += "툴</td>"
		}else if(sept == '2'){
			template += "프레임워크</td>"
		}else if(sept == '3'){
			template += "컴포넌트</td>"
		}else if(sept == '4'){
			template += "서비스</td>"
		}else{
			return;	
		}
		template +=	"<td>"+nm+"</td>";
		template +=	"<td>"+expln+"</td>";
		template +=	"<td>"+rm+"</td>";
		template +=	"<td><button type='button' onclick='javascript:fn_taskEnvSubRemove(this)'/>X</button></td>";
		template +=	"</tr>";

		$("#envTh").attr("rowspan",($("#envTh").attr("rowspan")*1)+1);
			
		$("#envMTr").before(template);		 */
		location.reload();

	}
	function fn_taskEnvSubRemove(obj,seq){
		if(!confirm("<spring:message code='msg.delete.confirm' />")){
			return;
		}
		$.ajax({
			url:"<c:url value='/com/task/popup/taskDevelEnvirSubDelete.do'/>",
			type:"POST",
			data:{taskId:document.frm.taskId.value,
				  tbTaskSubenvId:seq},
			success:function(data){
				if(data.result == 'OK'){
					alert("<spring:message code='msg.delete' />");
				}
			}
		});
		$(obj).parent().parent().remove();
		$("#envTh").attr("rowspan",($("#envTh").attr("rowspan")*1)-1);
		
	}
	
	function fn_scheduleRegist(){
		document.frm.action="<c:url value='/com/task/taskScheduleRegist.do'/>";
		document.frm.submit();
	}
	
	function fn_reload(){
		location.reload();
	}
	
	function fn_deptMnger(){
		window.open("<c:url value='/com/task/popup/taskDeptMngerPopup.do'/>?adbkId="+document.frm.adbkId.value+"&taskId="+document.frm.taskId.value+"&sept=1","","width=1100px,height=800px");
	}
	
	function fn_coOperMnger(){
		window.open("<c:url value='/com/task/popup/taskCoOperMngerPopup.do'/>?taskId="+document.frm.taskId.value+"&sept=2","","width=1100px,height=800px");
	}
	
	function fn_diarySelect(obj,gbn){
		var atchFileId = $(obj).attr("atchFileId");	
		$("#diaryNm"+gbn).val($(obj).attr("diaryNm"));
		$("#diaryContens"+gbn).val($(obj).attr("diaryContens"));		
		$("#fileTd"+gbn+" >span").remove();
		$("#fileTd"+gbn+" >br").remove();
		if(atchFileId != ''){
		  $.ajax({
            type : 'post',
            url : "<c:url value='/com/task/taskScheduleDiarySelectFile.do'/>",
            data : {atchFileId:atchFileId},
            success : function(data) {            
            	$(data.result).each(function(i){
            		var tmp = "<span><a href='javascript:fn_egov_downFile(\""+data.result[i].atchFileId+"\",\""+data.result[i].fileSn+"\")'>";
    				    tmp+=     data.result[i].orignlFileNm+"&nbsp;["+data.result[i].fileMg+"&nbsp;byte]";
    				    tmp+= "</a>";
    				    tmp+="</span><br>";
    				  $("#fileTd"+gbn).append(tmp);  
            	});
            	
            },
            error : function(error) {
               
            }
        });  
		}
     
	}
	
	function fn_egov_downFile(atchFileId, fileSn){
		window.open("<c:url value='/cmm/fms/FileDown.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>");
	}
	
	function fn_taskProfitLoss(){
		document.frm.action="<c:url value='/com/task/taskProfitLoss.do'/>"
		document.frm.submit();
	}
	
	function fn_taskReport(){
		//document.reportForm.action="<c:url value='/com/task/taskReport.do'/>";
		//document.reportForm.submit();
		alert("<spring:message code='msg.prepare'/>");
		return;
	}
	function fn_docMng(){
		alert("<spring:message code='msg.prepare'/>");
		return;
	}
	function fn_schCngHis(){
		alert("<spring:message code='msg.prepare'/>");
		return;
	}
	function fn_diarySection(param){
		$.ajax({
			type:"post",
			url:"<c:url value='/com/task/taskSchduleDiary.do'/>",
			data:{
				taskId:"<c:out value='${result.taskId }'/>",
				diarySection:$("#diarySection").val(),
				frstRegisterId:$("#taskMngSelete").val(),
				searchStdt:$("#searchStdt").val(),
				searchEndt:$("#searchEndt").val()
			},
			success:function(data){
				$("#diaryListTable tr:not(:first)").remove();
				$("#diaryNm2").val('');
				$("#diaryContens2").val('');
				$("#fileTd2 >span").remove();
				if(data.result.length == 0){
					if(param != "Y"){
						alert("<spring:message code='msg.noSearchData'/>");
						return;
					}
					
				}
				$(data.result).each(function(i){
					 var tmp = "<tr onclick='javascript:fn_diarySelect(this,\""+"2\");' diaryId='"+data.result[i].diaryId+"' atchFileId='"+data.result[i].atchFileId+"' diaryNm='"+data.result[i].diaryNm+"' diaryContens='"+data.result[i].diaryContens+"' diarySection='"+data.result[i].diarySection+"'>";					   	
					    tmp+= "<td>"+data.result[i].diaryNm+"</td>";
					    tmp+= "<td>"+data.result[i].frstRegisterPnttm.substring(0,10)+"</td>";
					    if(data.result[i].cnt > 0){
					    	 tmp+= "<td><i class='fas fa-save'></i></td>"
					    }else{
					    	tmp+= "<td></td>"
					    }
					    tmp+= "</tr>"
					$("#diaryListTable").append(tmp); 
				});
				
			}
		})
	}
	$(document).ready(function(){
		var date = new Date();
		var year = date.getFullYear();
		var mon = date.getMonth()+1;
		if(mon.toString().length == 1){
			mon="0"+mon;
		}
		var day = date.getDate();
		if(day.toString().length == 1){
			day="0"+day;
		}
		$("#searchStdt").val(year+"/"+mon+"/"+day);
		$("#searchEndt").val(year+"/"+mon+"/"+day);
		fn_diarySection("Y");
	});
</script>
<form name="reportForm" method="post">
</form>
<form name="frm" method="post">
	<input type=hidden name="taskId" value="${result.taskId }"/>
	<input type=hidden name="taskStepId" value="${result.taskStepId }"/>
	<input type=hidden name="adbkId" value="${result.adbkId }"/>
</form>
<form name="schdulForm" method="post">
	<input type=hidden name="schdulId" />
	<input type=hidden name="taskId" value="${result.taskId }"/>
	<input type=hidden name="taskStepId" value="${result.taskStepId }"/>
</form>
<div id="subwrap">	
	<div class="btngroup fl">	
		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/task/taskList.do'/>'">목록</button>
		<c:if test="${(not empty loginVO.emplyrNo  and fn:contains(particiEmplNo, loginVO.emplyrNo)) or loginVO.manageAt eq 'Y' }">
			<button type="button" class="btn01" onclick="javascript:fn_modity();">과제수정</button>
			<button type="button" class="btn03" onclick="javascript:fn_taskReport();">과제보고서</button>
			<button type="button" class="btn" onclick="javascript:fn_docMng();"<%-- onclick="location.href='<c:url value='/com/task/document/taskDocumentList.do'/>?bbsId=BBSMSTR_000000000031'" --%>>문서도우미</button>
		</c:if>
	</div>
	<c:if test="${(not empty loginVO.emplyrNo  and fn:contains(particiEmplNo, loginVO.emplyrNo)) or loginVO.manageAt eq 'Y'}">
		<div class="btngroup fr">			
			<button type="button" class="btn" onclick="javascript:fn_taskProfitLoss();">회계관리</button>
		</div>
	</c:if>
	<div class="clear"></div>
	<div class="space10"></div>	
	<h4><i class="fas fa-dot-circle"></i>과제정보</h4>
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
				<td><c:out value="${result.taskNm }"/></td>
				<th>진행상태</th>
				<td>
					<c:choose>
						<c:when test="${result.taskStatusCd eq '01' }">
							<span class="state bgcolor01">진행전</span>
						</c:when>
						<c:when test="${result.taskStatusCd eq '02' }">
							<span class="state bgcolor01">진행중</span>
						</c:when>
						<c:when test="${result.taskStatusCd eq '03' }">
							<span class="state bgcolor01">완료</span>
						</c:when>
						<c:when test="${result.taskStatusCd eq '04' }">
							<span class="state bgcolor01">지연</span>
						</c:when>
						<c:when test="${result.taskStatusCd eq '05' }">
							<span class="state bgcolor01">지연완료</span>
						</c:when>
						<c:when test="${result.taskStatusCd eq '06' }">
							<span class="state bgcolor03">중단</span>
						</c:when>
					</c:choose>
					
				</td>
			</tr>
			<tr>
				<th>발주처</th>
				<td><c:out value="${result.cmpnyNm }"/></td>
				<th>사업기간</th>
				<td>
					<c:out value="${result.taskSdate }"/>~<c:out value="${result.taskEdate }"/>
				</td>
			</tr>										
		</tbody>
	</table>
	<div class="space20"></div>
	<c:if test="${(not empty loginVO.emplyrNo  and fn:contains(particiEmplNo, loginVO.emplyrNo)) or loginVO.manageAt eq 'Y' }">
		<div class="btngroup fr">
			<button type="button" class="btn" onclick="javascript:fn_schCngHis();">일정변경이력</button>
			<button type="button" class="btn" onclick="javascript:fn_taskStepPopup();">진행단계관리</button>
			<button type="button" class="btn" onclick="javascript:fn_scheduleRegist();">상세일정등륵</button>
		</div>
	</c:if>
	<div class="space10"></div>
	<div class="clear"></div>	
	 <div id="gantt"></div>
	
	  <script>
	  function onChange(e) {
		  var gantt = e.sender;
          var selection = gantt.select();

          if (selection.length) {
        	  var dataItem = gantt.dataItem(selection);        	  
        	  if(dataItem.schdulId != undefined && dataItem.schdulId != ''){
        		  document.schdulForm.schdulId.value = dataItem.schdulId;
        		  document.schdulForm.action="<c:url value='/com/task/taskScheduleModify.do'/>";
            	  document.schdulForm.submit(); 
        	  }
        	  
        	  
          }
		 
      }

      function onAdd(e) {
    	  e.preventDefault();
      }

      function onEdit(e) {
    	  e.preventDefault();   		
      }

      function onCancel(e) {
         
      }

      function onRemove(e) {
    	  e.preventDefault();
      }

      function onSave(e) {
    	  e.preventDefault();
      }

      function onDataBound(e) {
    	 
      }

      function onDataBinding() {
    	 
      }

      function onNavigate(e) {
      
      }

      function onMoveStart(e) {
          
      }

      function onMove(e) {
    	  e.preventDefault();
      }

      function onMoveEnd(e) {
         
      }

      function onResizeStart(e) {
          
      }

      function onResize(e) {
         
      }

      function onResizeEnd(e) {
         
      }

      $(document).ready(function() {        
    	  var dataSource = new kendo.data.GanttDataSource({
    		    data: [
    		           	{id:0,
							 title:"<c:out value='${result.taskNm}'/>",
							 manager:"",
							 start:new Date("<c:out value='${result.taskSdate}'/> 09:00"),
							 end:new Date("<c:out value='${result.taskEdate}'/> 18:00"),							
						 	 orderId: 0,
					         parentId: null,
					         summary: true,  
					         expanded: true,					        
							 percentComplete : <c:out value='${totPercent}'/>,      
							 },
						<c:forEach var="step" items="${taskStepList}" varStatus="status">                           
							{id:<c:out value='${step.taskStep}'/>,
							 title:"<c:out value='${step.stepNm}'/>",
							 manager:"",
							 start:new Date("<c:out value='${step.planSdt}'/>"),
							 end:new Date("<c:out value='${step.planEdt}'/>"),
							 percentComplete : <c:out value='${step.percent}'/>,
							 <c:choose>
							 	<c:when test="${step.ptaskStepId eq '0'}">
								 	orderId: 0,
							        parentId: 0,
							        summary: true, 
							        expanded: true,
							 	</c:when>
								<c:when test="${step.ptaskStepId ne '0' and not empty step.cnt and empty step.schdulId}">
								 	orderId: <c:out value='${step.ordr}'/>,
							        parentId: <c:out value='${step.ptaskStepId}'/>,
							        summary: true,  
							        expanded: true,
						 		</c:when>
							 	<c:otherwise>
							 		<c:choose>
							 			<c:when test="${empty step.ordr}">
							 				orderId: <c:out value='${status.index}'/>,
							 			</c:when>
							 			<c:otherwise>
							 				orderId: <c:out value='${step.ordr}'/>,
							 			</c:otherwise>	
							 		</c:choose>							 												 	
							        parentId: <c:out value='${step.ptaskStepId}'/>
							        <c:if test="${not empty step.schdulId}">
							        	,schdulId: "<c:out value='${step.schdulId}'/>"
							        	,manager: "<c:out value='${step.mngNm}'/>"
							        </c:if>
							        
							 	</c:otherwise>
							 </c:choose>
							},
						</c:forEach>
    		    ]
    		  });

       

          var gantt = $("#gantt").kendoGantt({ 
              dataSource: dataSource,
              views: [
                  "day",
                  { type: "week", selected: true },
                  "month"
              ],
              columns: [
                
                  { field: "title", title: "진행단계일정명", editable: true, sortable: true },
                  { field: "manager", title: "담당자", editable: true, sortable: true },
                  { field: "start", title: "시작", format: "{0:yy.MM.dd}", width: 100, editable: true, sortable: true },
                  { field: "end", title: "종료", format: "{0:yy.MM.dd}", width: 100, editable: true, sortable: true }
              ],
              height: 700,

              showWorkHours: false,
              showWorkDays: false,

              dataBinding: onDataBinding,
              dataBound: onDataBound,
              add: onAdd,
              edit: onEdit,
              cancel: onCancel,
              change: onChange,
              remove: onRemove,
              save: onSave,
              navigate: onNavigate,
              moveStart: onMoveStart,
              move: onMove,
              moveEnd: onMoveEnd,
              resizeStart: onResizeStart,
              resize: onResize,
              resizeEnd: onResizeEnd
          }).data("kendoGantt");

          $(document).bind("kendo:skinChange", function() {
              gantt.refresh();
          });
      });
        </script>
    <div class="space20"></div>
    <div class="clear"></div>
    <div class="titlebtn">
    	<h4><i class="fas fa-dot-circle"></i>과제상세정보</h4>
    	<c:if test="${(not empty loginVO.emplyrNo  and fn:contains(particiEmplNo, loginVO.emplyrNo)) or loginVO.manageAt eq 'Y' }">
    		<button type="button" class="btn" onclick="javascript:fn_modity();">과제정보수정</button>
    	</c:if>
    </div>	
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
				<td colspan="3"><c:out value="${result.taskNm }"/></td>
			</tr>
			<tr>	
				<th>발주처</th>
				<td><c:out value="${result.cmpnyNm }"/></td>
				<th>사업기간</th>
				<td>
					<c:out value="${result.taskSdate }"/>~<c:out value="${result.taskEdate }"/>
				</td>
			</tr>
			<tr>
				<th>사업범위</th>
				<td colspan="3"><c:out value="${fn:replace(result.chargerNm,cn,br) }" escapeXml="false"/></td>
			</tr>
			<tr>
				<th>업무일지</th>
				<td colspan="3">
				<select class="select" id="diarySection">
					<option value="" <c:if test="${empty diaryManageVO.diarySection }">selected="selected"</c:if>>전체</option>
					<option value="1" <c:if test="${diaryManageVO.diarySection eq '1'}">selected="selected"</c:if>>일반업무</option>
					<option value="2" <c:if test="${diaryManageVO.diarySection eq '2' }">selected="selected"</c:if>>제약사항</option>
					<option value="3" <c:if test="${diaryManageVO.diarySection eq '3'}">selected="selected"</c:if>>특수사항</option>
				</select>
				<select class="select" id="taskMngSelete">
					<option value="">전체</option>
					<c:forEach var="mngSelete" items="${taskPersonList }" varStatus="status"> 
						<option value="<c:out value='${mngSelete.emplNo }'/>"><c:out value='${mngSelete.userNm }'/></option>
					</c:forEach>
				</select>
					<input class="datepicker width200"  id="searchStdt" value="" placeholder="시작시간" title="시작시간"> &nbsp;~&nbsp;
					<input class="datepicker width200"  id="searchEndt" value="" placeholder="시작시간" title="시작시간">	
					<button type="button" class="btn03" onclick="location.href='javascript:fn_diarySection(\'N\');'">검색</button>
					<div style="height:5px;"></div>
					<table class="tablewrite" id="diaryListTable">
							<colgroup>
								<col>								
								<col class="width150">
								<col class="width80">
							</colgroup>
							<thead>
								<tr>
									<th>제목</th>
									<th>작성일자</th>
									<th>첨부파일</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${empty diary }">
										<tr>
											<td colspan="5"></td>
										</tr>
									</c:when>
									<c:otherwise>										
										<c:forEach var="diary" items="${diary }" varStatus="status">
											<%-- <c:if test="${diary.diarySection eq '2' }"> --%>
												<tr onclick="javascript:fn_diarySelect(this,'2');" diaryId="<c:out value='${diary.diaryId }'/>" atchFileId="<c:out value='${diary.atchFileId }'/>" diaryNm="<c:out value='${diary.diaryNm }'/>" diaryContens="<c:out value='${diary.diaryContens }'/>" diarySection="<c:out value='${diary.diarySection }'/>">											
													<td>
														<c:out value="${diary.diaryNm }"/>
													</td>
													<td>
														<c:out value="${fn:substring(diary.frstRegisterPnttm,0,10) }"/>
													</td>
													<td>
														<c:if test="${diary.cnt > 0 }">
															<i class="fas fa-save"></i>
														</c:if>													
													</td>
												</tr>
											<%-- </c:if> --%>
										</c:forEach>
									</c:otherwise>
								</c:choose>
								
							</tbody>
						</table>
						<table class="tablewrite" id="diaryTable2">
						<colgroup>
						<col class="width180">
						<col>
						</colgroup>
							<tbody>								
								<tr>
									<th>제목</th>
									<td>
										<input type="text" class="k-textbox width100p" id="diaryNm2" readonly="readonly">
									</td>
								</tr>
								<tr>
									<th>내용</th>
									<td>
										<textarea class="k-textbox width100p height200" id="diaryContens2" readonly="readonly"></textarea>										
									</td>
								</tr>
								<tr>
									<th>첨부파일</th>
									<td id="fileTd2">																		
									</td>
								</tr>																
							</tbody>
						</table>
				</td>
			</tr>
<%-- 			<tr>
				<th>특수사항</th>
				<td colspan="3">
					<table class="tablewrite" id="diaryListTable">
							<colgroup>
								<col>
								<col class="width150">								
								<col class="width80">
							</colgroup>
							<thead>
								<tr>
									<th>제목</th>
									<th>작성일자</th>
									<th>첨부파일</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${empty diary }">
										<tr>
											<td colspan="5"></td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="diary" items="${diary }" varStatus="status">
											<c:if test="${diary.diarySection eq '3' }">
												<tr onclick="javascript:fn_diarySelect(this,'3');" diaryId="<c:out value='${diary.diaryId }'/>" atchFileId="<c:out value='${diary.atchFileId }'/>" diaryNm="<c:out value='${diary.diaryNm }'/>" diaryContens="<c:out value='${diary.diaryContens }'/>" diarySection="<c:out value='${diary.diarySection }'/>">											
													<td>
														<c:out value="${diary.diaryNm }"/>
													</td>
													<td>
														<c:out value="${fn:substring(diary.frstRegisterPnttm,0,10) }"/>
													</td>
													<td>
														<c:if test="${diary.cnt > 0 }">
															<i class="fas fa-save"></i>
														</c:if>													
													</td>
												</tr>
											</c:if>
										</c:forEach>
									</c:otherwise>
								</c:choose>
								
							</tbody>
						</table>
						<table class="tablewrite" id="diaryTable3">
							<colgroup>
								<col class="width180">
								<col>
							</colgroup>
							<tbody>								
								<tr>
									<th>제목</th>
									<td>
										<input type="text" class="k-textbox width100p" id="diaryNm3" readonly="readonly">
									</td>
								</tr>
								<tr>
									<th>내용</th>
									<td>
										<textarea class="k-textbox width100p height200" id="diaryContens3" readonly="readonly"></textarea>										
									</td>
								</tr>
								<tr>
									<th>첨부파일</th>
									<td id="fileTd3">
																		
									</td>
								</tr>																
							</tbody>
						</table>
				</td>
			</tr> --%>
			<tr>
				<th>주요키워드</th>
				<td colspan="3"><c:out value="${result.keyword }"/></td>
			</tr>
			<tr>
				<th>관련과제</th>
				<td colspan="3">
					<c:forEach var="rela" items="${relaResult }" varStatus="status">
						<a href="#" onclick="javascript:fn_detail('<c:out value='${rela.relaTaskId }'/>');" ><c:out value="${rela.taskNm}"/></a>
						<c:if test="${!status.last }">
							,
						</c:if>
					</c:forEach>
					
				</td>
			</tr>									
		</tbody>
	</table>
	<div class="space20"></div>
	<div class="titlebtn">
	<h4><i class="fas fa-dot-circle"></i>과제담당자</h4>
	<c:if test="${(not empty loginVO.emplyrNo  and fn:contains(particiEmplNo, loginVO.emplyrNo)) or loginVO.manageAt eq 'Y' }">
		<button type="button" class="btn" onclick="javascript:fn_taskMngerPopup();">관리</button>
	</c:if>
	</div>
	<table class="tablewrite">
		<colgroup>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
		</colgroup>
		<thead>
			<tr>
				<th rowspan="3">부서</th>
				<th rowspan="3">직위</th>
				<th rowspan="3">성명</th>
				<th rowspan="3">역할</th>
				<th colspan="3">근로시간</th>			
			    <th rowspan="3">업무지연</th>
				<th rowspan="3">등록일</th>				
			</tr>
			<tr>				
				<th rowspan="2">기본근무시간</th>
				<th colspan="2">초과근무시간</th>				
			</tr>
			<tr>			
				<th>야간근무</th>
				<th>주말근무</th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach var="taskPer" items="${taskPersonList }" varStatus="status"> 
				<tr>
					<td><c:out value='${taskPer.orgnztNm }'/></td>
					<td><c:out value='${taskPer.ofcpsNm }'/></td>
					<td><c:out value='${taskPer.userNm }'/></td>
					<td>
						<c:choose>
							<c:when test="${taskPer.role eq 'M' }">
								과제담당자
							</c:when>
							<c:otherwise>
								과제참여자
							</c:otherwise>
						</c:choose>
					</td>
					<td><c:out value='${taskPer.nomalWork }'/></td>
					<td><c:out value='${taskPer.nightWork }'/></td>
					<td><c:out value='${taskPer.weekWork }'/></td>
					<td><c:out value='${taskPer.delay }'/></td>
					<td><c:out value='${taskPer.regDt }'/></td>
				</tr>
			</c:forEach>						
		</tbody>
	</table>
	<div class="space20"></div>
	<div class="titlebtn">
	<h4><i class="fas fa-dot-circle"></i>발주처담당자</h4>
	<c:if test="${(not empty loginVO.emplyrNo  and fn:contains(particiEmplNo, loginVO.emplyrNo)) or loginVO.manageAt eq 'Y' }">
		<button type="button" class="btn" onclick="javascript:fn_deptMnger();">관리</button>
	</c:if>
	</div>
	<table class="tablewrite">
		<colgroup>
			<col class="width180">
			<col class="width180">
			<col class="width180">
			<col class="width180">
			<col class="width180">
			<col class="width180">
		</colgroup>
		<thead>
			<tr>
				<th>발주처명</th>
				<th>부서</th>
				<th>직위</th>
				<th>성명</th>
				<th>역할</th>
				<th>등록일</th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach var="opList" items="${opList }" varStatus="status">
				<c:if test="${opList.sept eq '1' }">
				<tr>
					<td><c:out value='${opList.cmpnyNm }'/></td>
					<td><c:out value='${opList.deptNm }'/></td>
					<td><c:out value='${opList.ofcpsNm }'/></td>
					<td><c:out value='${opList.nm }'/></td>
					<td>
						<c:if test="${opList.role eq 'M' }">
							과제책임자
						</c:if>
						<c:if test="${opList.role eq 'P' }">
							과제참여자
						</c:if>
					</td>
					<td><c:out value='${opList.regDate }'/></td>					
				</tr>
				</c:if>
			</c:forEach>						
		</tbody>
	</table>
	<div class="space20"></div>
	<div class="titlebtn">
	<h4><i class="fas fa-dot-circle"></i>협력업체담당자</h4>
	<c:if test="${(not empty loginVO.emplyrNo  and fn:contains(particiEmplNo, loginVO.emplyrNo)) or loginVO.manageAt eq 'Y' }">
		<button type="button" class="btn" onclick="javascript:fn_coOperMnger();">관리</button>
	</c:if>
	</div>
	<table class="tablewrite">
		<colgroup>
			<col class="width180">
			<col class="width180">
			<col class="width180">
			<col class="width180">
			<col class="width180">
			<col class="width180">
		</colgroup>
		<thead>
			<tr>
				<th>협력업체명</th>
				<th>부서</th>
				<th>직위</th>
				<th>성명</th>
				<th>역할</th>
				<th>등록일</th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach var="opList" items="${opList }" varStatus="status">
				<c:if test="${opList.sept eq '2' }">
				<tr>
					<td><c:out value='${opList.cmpnyNm }'/></td>
					<td><c:out value='${opList.deptNm }'/></td>
					<td><c:out value='${opList.ofcpsNm }'/></td>
					<td><c:out value='${opList.nm }'/></td>
					<td>
						<c:if test="${opList.role eq 'M' }">
							과제책임자
						</c:if>
						<c:if test="${opList.role eq 'P' }">
							과제참여자
						</c:if>
					</td>
					<td><c:out value='${opList.regDate }'/></td>					
				</tr>
				</c:if>
			</c:forEach>						
		</tbody>
	</table>
	<div class="space20"></div>
	<div class="titlebtn">
	<h4><i class="fas fa-dot-circle"></i>개발환경</h4>
	<c:if test="${(not empty loginVO.emplyrNo  and fn:contains(particiEmplNo, loginVO.emplyrNo)) or loginVO.manageAt eq 'Y' }">
		<button type="button" class="btn" onclick="javascript:fn_taskDevelEnvirPopup();">관리</button>
	</c:if>
	</div>
	<table class="tablewrite">
		<colgroup>
	 		<col >
			<col >
			<col >
			<col >
			<col >
			<col > 
		</colgroup>

		<tbody>
			<tr>
				<th>운영체제</th>
				<td colspan="2" id="os"><c:out value="${envM.os }"/></td>
				<th>개발언어</th>
				<td colspan="2" id="lang"><c:out value="${envM.lang }"/></td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${empty envS }">
						<th rowspan="2" id="envTh">부가정보</th>
					</c:when>
					<c:otherwise>
						<th rowspan="<c:out value='${fn:length(envS)+1 }'/>" id="envTh">부가정보</th>
					</c:otherwise>
				</c:choose>
				
				<th>구분</th>
				<th>이름</th>
				<th>설명</th>
				<th>비고</th>
				<th><c:if test="${(not empty loginVO.emplyrNo  and fn:contains(particiEmplNo, loginVO.emplyrNo)) or loginVO.manageAt eq 'Y' }"><button type="button" class="btn03" onclick="javascript:fn_taskDevelEnvirSubPopup();">추가</button></c:if></th>		
		   </tr>
		   <c:choose>
		       <c:when test="${empty envS }">
		       		 <tr id="removeTr">
		   				<td colspan="5"></td>
		   			</tr>
		       </c:when>
		       <c:otherwise>
		       	 	<c:forEach var="envS" items="${envS}" varStatus="status">
				    	<tr>
				    		<td>
				    			<c:choose>
				    				<c:when test="${envS.sept eq '1' }">
				    					툴
				    				</c:when>
				    				<c:when test="${envS.sept eq '2' }">
				    					프레임워크
				    				</c:when>
				    				<c:when test="${envS.sept eq '3' }">
				    					컴포넌트
				    				</c:when>
				    				<c:when test="${envS.sept eq '4' }">
				    					서비스
				    				</c:when>
				    			</c:choose>
				    		</td>
				    		<td><c:out value="${envS.nm }"/></td>
				    		<td><c:out value="${envS.expln }"/></td>
				    		<td><c:out value="${envS.rm }"/></td>
				    		<td><c:if test="${fn:contains(particiEmplNo, loginVO.emplyrNo) }"><button type="button" onclick="javascript:fn_taskEnvSubRemove(this,'<c:out value='${envS.tbTaskSubenvId }'/>')"/>X</button></c:if></td>
				   		</tr>
				   </c:forEach>
		       </c:otherwise>
		   </c:choose>
		   <tr id="envMTr">
		   	<th>비고</th>
		   	<td colspan="5" id="rm_m"><c:out value="${envM.rm }"/></td>
		   </tr>					
		</tbody>
	</table>
	<div class="space20"></div>	
		<div class="btngroup fl">	
			<button type="button" class="btn04" onclick="location.href='<c:url value='/com/task/taskList.do'/>'">목록</button>
			<c:if test="${(not empty loginVO.emplyrNo  and fn:contains(particiEmplNo, loginVO.emplyrNo)) or loginVO.manageAt eq 'Y' }">
				<button type="button" class="btn01" onclick="javascript:fn_modity();">과제수정</button>
				<button type="button" class="btn03" onclick="javascript:fn_taskReport();">과제보고서</button>
				<button type="button" class="btn" onclick="javascript:fn_docMng();"<%-- onclick="location.href='<c:url value='/com/task/document/taskDocumentList.do'/>?bbsId=BBSMSTR_000000000031'" --%>>문서도우미</button>
			</c:if>
		</div>
	<c:if test="${(not empty loginVO.emplyrNo  and fn:contains(particiEmplNo, loginVO.emplyrNo)) or loginVO.manageAt eq 'Y' }">	
		<div class="btngroup fr">			
			<button type="button" class="btn" onclick="javascript:fn_taskProfitLoss();">회계관리</button>
		</div>
	</c:if>
	<div class="space20"></div>	
</div>