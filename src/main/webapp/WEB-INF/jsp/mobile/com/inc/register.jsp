<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel='stylesheet prefetch' href='http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css'>
<script type="text/javascript" src="<c:url value='/js/tis/jquery.min.js'/>"></script>
<script>
$(document).ready(function(){
	localStorage.tokenid = "${tokenId }";
});
</script>