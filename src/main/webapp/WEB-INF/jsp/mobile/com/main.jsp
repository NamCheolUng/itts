<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=mobile/com/inc/header" />
<body>
<div class="toolbar-group">
			<div class="toolbar bg-blue-500 color-white">
				<center><span class="toolbar-label">(주)이튜통합정보시스템</span></center>
			</div>
			<ul class="toolbar tabs bg-blue-500 color-white">
				<li><a href="<c:url value='/mobile/com/document/bankBook.do'/>">증빙서류</a></li>
				<li><a href="<c:url value='/mobile/com/receipt/receiptInsert.do'/>">영수증</a></li>
				<li><a href="<c:url value='/mobile/com/callingCard/callingCard.do'/>">명함</a></li>
			</ul>
		</div>
</body>
</html>