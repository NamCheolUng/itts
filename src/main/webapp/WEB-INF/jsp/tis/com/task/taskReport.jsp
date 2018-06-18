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
<div id="subwrap">
	<div class="btngroup fl">
		<h1>과제관리 > 문서도우미 > 과제보고서</h1>
	</div>
	<div class="btngroup fr">
		<button type="button" class="btn02" onClick="javascript:fn_goList();">이전</button>
		<button type="button" class="btn01">한글내보내기</button>
		<button type="button" class="btn01">저장</button>
	</div>
	<div class="space20"></div>
	<div class="btngroup fl">
		<h4><i class="fas fa-dot-circle"></i>과제보고서</h4>			
	</div>
	<div class="btngroup fr">
		<input  class="datepicker width200">
		<input  class="datepicker width200">		
	</div>

	<table class="tableview">
		<colgroup>
			<col class="width180">
			<col class="width500">
			<col class="width180">
			<col class="width500">
			<col class="width180">
			<col class="width180">
			<col class="width180">
		</colgroup>
		<tbody>
			<tr>
				<th>과제명</th>
				<td><input type="text" class="k-textbox width100p"></td>
				<th>보고일자</th>
				<td><input class="datepicker width200"></td>
				<th>수행사</th>
				<th colspan="2">고객사</th>				
			</tr>
			<tr>
				<th>보고기간</th>
				<td ><input type="text" class="k-textbox width100p"></td>
				<th>보고자</th>
				<td ><input type="text" class="k-textbox width100p"></td>
				<td rowspan="2"></td>
				<td rowspan="2"></td>
				<td rowspan="2"></td>
			</tr>
			<tr>
				<th>특이사항</th>
				<td colspan="6"><input type="text" class="k-textbox width100p"></td>
			</tr>
		</tbody>
	</table>
	<div class="space20"></div>
	
	1.업무별 실적/계획  ..주차(계획 진행률%/ 실적진행률%)
	<table class="tableview">
		<colgroup>
			<col class="width180">
			<col class="width600">
			<col class="width600">
		</colgroup>
		<tbody>
			<tr>
				<th>과제명</th>
				<th>금주실적</th>
				<th>차주계획</th>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
			</tr>			
		</tbody>
	</table>
	<div class="space20"></div>
	
	2.이슈내역
	<table class="tableview">
		<colgroup>
			<col class="width180">
			<col class="width180">
			<col class="width600">
			<col class="width180">
			<col class="width180">
		</colgroup>
		<tbody>
			<tr>
				<th>ID</th>
				<th>제기일</th>
				<th>이슈</th>
				<th>상태</th>
				<th>완료일</th>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>						
		</tbody>
	</table>
	<div class="space20"></div>
	
	3.기타사항
	<input type="text" class="k-textbox width100p">
</div>
</body>