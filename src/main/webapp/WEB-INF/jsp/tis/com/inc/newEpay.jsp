<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${leftmenu == 'draft'}">
	<span class="badge"><c:out value="${newCnt}"/>건</span>
</c:if>
<c:if test="${leftmenu == 'appr'}">
	<span class="badge"><c:out value="${newCnt}"/>건</span>
</c:if>