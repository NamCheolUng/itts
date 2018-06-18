<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel='stylesheet prefetch' href='http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css'>
<script type="text/javascript" src="<c:url value='/js/tis/jquery.min.js'/>"></script>

<style>
body {
  background: #eee !important;
}

.wrapper {
  margin-top: 50%;
}

.loginForm {
  max-width: 80%;
  padding: 2% 3% 4%;
  margin: 0 auto;
  background-color: #fff;
  border: 1px solid rgba(0, 0, 0, 0.1);
}
.loginForm .loginForm-heading{
  margin-bottom: 30px;
}
.loginForm .form-control {
  position: relative;
  font-size: 30px;
  height: auto;
  padding: 20px;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}
.loginForm .form-control:focus {
  z-index: 2;
}
.loginForm input[type="text"] {
  margin-bottom: -1px;
  border-bottom-left-radius: 0;
  border-bottom-right-radius: 0;
}
.loginForm input[type="password"] {
  margin-bottom: 30px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
<script>
$(document).ready(function(){
	fnInit(); 
});


function fnInit() {
	if ("${message}" != "") {
		alert("${message}");
	}
}

function actionLogin() {
	
     if (document.loginForm.id.value =="") {
        alert("아이디를 입력하세요");
        document.loginForm.id.focus();
        return;
    }
    if (document.loginForm.password.value =="") {
        alert("비밀번호를 입력하세요");
        document.loginForm.password.focus();
        return;
    }

    	document.loginForm.id.value = document.loginForm.id.value.toLowerCase()

    	document.loginForm.tokenId.value = localStorage.tokenid;
        document.loginForm.action="<c:url value='/mobile/com/inc/actionLogin.do'/>";
        document.loginForm.submit();

}
function setMessage(token){
	localStorage.tokenid = token;
}
</script>
  <div class="wrapper">
    <form name="loginForm" class="loginForm" onsubmit="actionLogin(); return false;" method="post">
    	<input name="method" type="hidden" value="login">
		<input name="refer" type="hidden" value="">
		<input name="userSe" type="hidden" value="USR">
		<input name="j_username" type="hidden">
		<input name="tokenId" type="hidden">
		
      <h1 class="loginForm-heading">로그인</h1>
      <input type="text" class="form-control" name="id" placeholder="ID"/>
      <input type="password" class="form-control" name="password" placeholder="Password"/>      
      <button type="button" class="btn btn-lg btn-primary btn-block" onclick="javascript:actionLogin();">로그인</button>   
    </form>
  </div>
