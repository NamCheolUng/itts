<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />


<div id="subwrap">
	<h1><c:out value="${userManageVO.emplyrNm}"/> (<c:out value="${userManageVO.emplNo}"/>) 인사이력</h1>
	<table class="tablelist">
		<thead>
			<tr>
				<th>번호</th>
				<th>발령일자</th>
				<th>발령부서</th>
				<th>발령직급</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="result" items="${chang_result}" varStatus="status">
			<tr>
				<td><c:out value="${status.index + 1}"/></td>
				<td><c:out value="${result.changeDe}"/></td>
				<td><c:out value="${result.afOrgnztNm}"/></td>
				<td><c:out value="${result.afOfcpsNm}"/></td>
				<td><c:out value="${result.rm}"/></td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	<div class="space20"></div>
		<div class="btngroup fr">
		<button type="button" class="btn04" onclick="location.href='<c:url value='/uss/umt/EgovUserManage.do'/>' ">목록</button>
		<a href="#" data-toggle="modal" data-target="#modal08"><button type="button" class="btn01">인사발령</button></a>
	</div>
</div>