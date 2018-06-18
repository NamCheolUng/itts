<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel='stylesheet prefetch' href='http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css'>
<script type="text/javascript" src="<c:url value='/js/tis/jquery.min.js'/>"></script>
<style>
body {
  background: #eee !important;
}

.wrapper {
  margin-top: 80px;
  margin-bottom: 80px;
}

.loginForm {
  max-width: 380px;
  padding: 15px 35px 45px;
  margin: 0 auto;
  background-color: #fff;
  border: 1px solid rgba(0, 0, 0, 0.1);
}
.loginForm .loginForm-heading{
  margin-bottom: 30px;
}
.loginForm .form-control {
  position: relative;
  font-size: 16px;
  height: auto;
  padding: 10px;
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
  margin-bottom: 20px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
<script>
$(document).ready(function(){
	fnInit();
});

function fnInit() {
	if ("${msg}" ==1) {
		alert("로그인이 필요합니다.");
	}else if ("${msg}" == 2) {
		alert("아이디와 패스워드가 틀립니다.");
	}else if ("${msg}" == 3) {
		alert("잘못된 접근입니다.");
	} else if ("${msg}" == 4) {
		alert("외부접속은 허용되지 않습니다.");
	} else if ("${msg}" == 5) {
		alert("웹서버가 IPv4를 지원하지 않습니다.\n관리자에게 문의하세요.");
	} else if ("${msg}" == 6) {
		alert("사용할 수 없는 계정입니다");
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
        document.loginForm.action="<c:url value='/com/inc/actionLogin.do'/>";
        document.loginForm.submit();

}
</script>
  <div class="wrapper">
    <form name="loginForm" class="loginForm" onsubmit="actionLogin(); return false;" method="post">
    	<input name="method" type="hidden" value="login">
		<input name="refer" type="hidden" value="">
		<input name="userSe" type="hidden" value="USR">
		<input name="j_username" type="hidden">
		
      <h2 class="loginForm-heading">로그인</h2>
      <input type="text" class="form-control" name="id" placeholder="ID" />
      <input type="password" class="form-control" name="password" placeholder="Password"/>      
      <button class="btn btn-lg btn-primary btn-block" onclick="javascript:actionLogin();">로그인</button>   
    </form>
  </div>
