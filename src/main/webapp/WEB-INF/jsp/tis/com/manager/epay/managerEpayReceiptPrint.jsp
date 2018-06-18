<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/layout.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/board.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/common.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/fontawesome-all.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/kendo.common.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/kendo.material.min.css'/>">

<script type="text/javascript" src="<c:url value='/js/tis/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/tis/jszip.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/tis/kendo.all.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/tis/bootstrap.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/tis/common.js'/>"></script>
<script type="text/javascript">
$(document).ready(function(){
	   var beforePrint = function() {
         
      };
      var afterPrint = function() {
         // window.close();
          
      };

      if (window.matchMedia) {
          var mediaQueryList = window.matchMedia('print');
          mediaQueryList.addListener(function(mql) {
              if (mql.matches) {
                  beforePrint();
              } else {
                  afterPrint();
              }
          });
      }

      window.onbeforeprint = beforePrint;
      window.onafterprint = afterPrint;
      
      window.print();

});
	

</script>
<style>
@page  
{ 
    size: auto;   /* auto is the initial value */ 

    /* this affects the margin in the printer settings */ 
    margin: 25mm 25mm 25mm 25mm;  
} 

body  
{ 
    /* this affects the margin on the content before sending to printer */ 
    margin: 0px;  
}
.left {
   float: left;
   width: 50%;
}
 
.right {
   float: right;
   width: 50%;
}
</style>
<body>
	
	<c:if test="${printGbn eq 'A' }">
	 	<c:forEach var="id" items="${atchFileIdList }">
			<div style="page-break-before: always;">
				 <img src='<c:url value='/cmm/fms/getImage.do'/>?atchFileId=<c:out value="${id}"/>&fileSn=0'  style="width:500px;height:500px;" alt=""/>
			</div>
		</c:forEach>
	</c:if>
	
	<c:if test="${printGbn eq '4' }">
		
			<c:forEach var="id" items="${atchFileIdList }" varStatus="status">
				<c:if test="${status.first or status.count%4 == 1}">
					<div style="page-break-before: always;">
				</c:if>
				<%--  <div class="<c:if test='${status.count%2==1 }'>left</c:if> <c:if test='${status.count%2==0 }'>right</c:if>"> --%>
					 <img src='<c:url value='/cmm/fms/getImage.do'/>?atchFileId=<c:out value="${id}"/>&fileSn=0' alt="" style="width:300px;height:300px;"  />
			<%--  </div> --%> 
				<c:if test="${status.last or status.count%4 == 0}">
					</div>
				</c:if>
			</c:forEach>
	
	</c:if>
	
</body>
