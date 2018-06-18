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
<script type="text/javascript">
function fn_GotoModify(){
	document.subForm.action = "<c:url value='/com/business/company/companyEmplyrModify.do'/>";
	document.subForm.submit();
}

function fn_diarySection(){
	$.ajax({
		type:"post",
		url:"<c:url value='/com/business/company/taskSchduleDiary.do'/>",
		data:{
			ncrdId:"<c:out value="${ncrdVO.ncrdId}"/>",
			diarySection:$("#diarySection").val(),
			searchEndt:$("#searchEndt").val(),
			searchStdt:$("#searchStdt").val()
		},
		success:function(data){
			$("#diaryListTable tr:not(:first)").remove();
			$("#diaryNm").val('');
			$("#diaryContens").val('');
			$("#fileTd >span").remove();
			if(data.result.length == 0){
				alert("<spring:message code='msg.noSearchData'/>");
				return;
			}
			$(data.result).each(function(i){
				var date = new Date(data.result[i].frstRegistPnttm);
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate(); 
				if(month*1 < 10){
					month ="0"+month;
				}
				if(day*1 < 10){
					day = "0"+day
				}
				 var tmp = "<tr onclick='javascript:fn_diarySelect(this);' diaryId='"+data.result[i].diaryId+"' atchFileId='"+data.result[i].atchFileId+"' diaryNm='"+data.result[i].diaryNm+"' diaryContens='"+data.result[i].diaryContens+"' diarySection='"+data.result[i].diarySection+"'>";				   	
				    tmp+= "<td>"+data.result[i].diaryNm+"</td>";
				    tmp+= "<td>"+data.result[i].taskNm+"</td>";
				    tmp+= "<td>"+year+"-"+month+"-"+day+"</td>";
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

function fn_diarySelect(obj){
	var atchFileId = $(obj).attr("atchFileId");	
	$("#diaryNm").val($(obj).attr("diaryNm"));
	$("#diaryContens").val($(obj).attr("diaryContens"));		
	$("#fileTd >span").remove();
	$("#fileTd >br").remove();
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
				  $("#fileTd").append(tmp);  
        	});
        	
        },
        error : function(error) {
           
        }
    });  
	}
 
}
function fn_egov_downFile(atchFileId, fileSn){
	window.open("<c:url value='/cmm/fms/getImage.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>","new","width=400, height=300, left=100, top=100,scrollbars=no,titlebar=no,status=no,resizable=no,fullscreen=no");
}

$(document).ready(function(){
	$("#searchEndt").attr("readonly",true);
	$("#searchStdt").attr("readonly",true);
});
</script>

<form name="subForm" method="post">
<input type="hidden" name="ncrdId" value='<c:out value="${ncrdVO.ncrdId}"/>'>
</form>
<div id="mainwrap">
<h1>관련기관/업체사원관리  >상세</h1>
	<div class="tabmenu">
		<ul>
			<li class="active"><a href="<c:url value='/com/business/company/companyEmplyrList.do'/>">업체사원관리</a></li>
			<li><a href="<c:url value='/com/business/company/companyList.do'/>">업체관리</a></li>
		</ul>
	</div>
    <table class="tablewrite">
      <colgroup>
        <col class="width150">
        <col class="width520">
        <col class="width150">
        <col class="width520">
      </colgroup>
      <tbody>
        <tr>
          <th>소속기관</th>
          <td><c:out value="${ncrdVO.cmpnyNm}"/></td>
          <th>구분</th>
          <td><c:out value="${ncrdVO.idntfcNm}"/></td>
        </tr>
        <tr>
          <th>부서</th>
          <td><c:out value="${ncrdVO.deptNm}"/></td>
          <th>직위</th>
          <td><c:out value="${ncrdVO.ofcpsNm}"/></td>
        </tr>
        <tr>
          <th>성명</th>
          <td><c:out value="${ncrdVO.ncrdNm}"/></td>
          <th>성별</th>
          <td>
          <c:if test="${ncrdVO.sexdstnCode == 'W' }">여성</c:if>
          <c:if test="${ncrdVO.sexdstnCode != 'W' }">남성</c:if>          
          </td>
        </tr>
        <tr>
          <th>전화번호</th>
          <td><c:out value="${ncrdVO.telno}"/></td>
          <th>휴대전화번호</th>
          <td><c:out value="${ncrdVO.mbtlnum}"/></td>
        </tr>
        <tr>
          <th>팩스</th>
          <td><c:out value="${ncrdVO.fxnum}"/></td>
          <th>이메일</th>
          <td><c:out value="${ncrdVO.emailAdres}"/></td>
        </tr>
        <tr>         
          <th>비고</th>
          <td colspan="3"><c:out value="${ncrdVO.rm}"/></td>
        </tr>
        <tr>
          <th>명함보기</th>
          <td colspan="3">
          		<c:if test="${ncrdVO.atchFileId ne null}">
          			<a href="javascript:fn_egov_downFile('${ncrdVO.atchFileId }',0)"><i class="fas fa-cloud-download-alt"></i></a>
          		</c:if>
          </td>
        </tr>
      </tbody>
    </table>
    <br>
    <div class="space20"></div>
    	<div class="btngroup fl">	
	    	<!-- <select class="select" id="diarySection" onchange="javascript:fn_diarySection();"> -->
	    	<select class="select" id="diarySection" >
				<option value="" <c:if test="${empty diaryManageVO.diarySection }">selected="selected"</c:if>>전체</option>
				<option value="1" <c:if test="${diaryManageVO.diarySection eq '1'}">selected="selected"</c:if>>일반업무</option>
				<option value="2" <c:if test="${diaryManageVO.diarySection eq '2' }">selected="selected"</c:if>>제약사항</option>
				<option value="3" <c:if test="${diaryManageVO.diarySection eq '3'}">selected="selected"</c:if>>특수사항</option>
			</select>
		</div>
		<div class="btngroup fr">
			<input class="datepicker width200" name="" id="searchStdt" value="" placeholder="시작시간" title="시작시간"> &nbsp;~&nbsp;
			<input class="datepicker width200" name="" id="searchEndt" value="" placeholder="시작시간" title="시작시간">	
			<button type="button" class="btn03" onclick="location.href='javascript:fn_diarySection();'">검색</button>
		</div>
	<div class="space10"></div>
    <table class="tablewrite" id="diaryListTable">
		<colgroup>
			<col>								
			<col class="width300">
			<col class="width150">
			<col class="width150">
		</colgroup>
		<thead>
			<tr>
				<th>제목</th>
				<th>과제명</th>
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
							<tr onclick="javascript:fn_diarySelect(this);" diaryId="<c:out value='${diary.diaryId }'/>" atchFileId="<c:out value='${diary.atchFileId }'/>" diaryNm="<c:out value='${diary.diaryNm }'/>" diaryContens="<c:out value='${diary.diaryContens }'/>" diarySection="<c:out value='${diary.diarySection }'/>">											
								<td>
									<c:out value="${diary.diaryNm }"/>
								</td>
								<td><c:out value='${diary.taskNm }'/></td>
								<td>
									<c:out value="${fn:substring(diary.frstRegistPnttm,0,10) }"/>
								</td>
								<td>
									<c:if test="${diary.cnt > 0 }">
										<i class="fas fa-save"></i>
									</c:if>													
								</td>
							</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>			
		</tbody>
	</table>
	<table class="tablewrite" id="diaryTable">
	<colgroup>
	<col class="width180">
	<col>
	</colgroup>
		<tbody>								
			<tr>
				<th>제목</th>
				<td>
					<input type="text" class="k-textbox width100p" id="diaryNm" readonly="readonly">
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea class="k-textbox width100p height200" id="diaryContens" readonly="readonly"></textarea>										
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td id="fileTd">																		
				</td>
			</tr>																
		</tbody>
	</table>
    <div class="btngroup fr">
   		<button type="button" class="btn04" onclick="location.href='<c:url value='/com/business/company/companyEmplyrList.do'/>'">목록</button>	
   		<button type="button" class="btn01" onclick="location.href='javascript:fn_GotoModify();'">수정</button>
	</div>
</div>