
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

    <!-- <base href="https://demos.telerik.com/kendo-ui/treeview/checkboxes"> -->
    <style>html { font-size: 14px; font-family: Arial, Helvetica, sans-serif; }</style>
    <title></title>
    
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/common.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/layout.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/board.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/fontawesome-all.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/kendo.common.min.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/tis/kendo.material.min.css'/>">
	
	<script type="text/javascript" src="<c:url value='/js/tis/jquery.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/kendo.all.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/bootstrap.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/common.js'/>"></script>
	
	<script>
	
	var idx = 0;
	var apprOrdrIdx = 1;
	
		// 결재자 추가
		function fn_addApprover(tableId){
			var checkedNodes = [], treeView = $("#treeview").data("kendoTreeView"), message;

	        checkedNodeIds(treeView.dataSource.view(), checkedNodes);
	
	        /* if (checkedNodes.length > 3) {
	        	alert('결재자는 3명 이상 추가 할 수 없습니다.');
	        	return;
	        }  */
	     	
			var checkbox1 = jQuery('input[name="chk"]');
	        
	        for(var i = 0; i < checkedNodes.length; i++){
				
	        	// 기존 사용자 중복 체크
				var isDuplicate = false;
	        	
	        	checkbox1.each(function() {
					//var approverId = jQuery(this).closest('tr').find('input[name="apprLnList.emplNo"]').val();
					var approverId = $(this).closest('tr').find('input[id="apprLnList.emplNo"]').val();
					if( approverId == checkedNodes[i]) {
						isDuplicate = true;
					}
	        	});
	        	
	        	if(isDuplicate) {
	        		//alert('결재자 ' + checkedNodes[i] + ' 는 이미 등록되어 있습니다.');
	        		continue;
	        	}
	        	
				$.ajax({    
	    			url:'<c:url value="/com/epay/popup/epayOrgnztInfoForEmplyr.do"/>',
	    			data:{emplNo:checkedNodes[i]}, 
	    			dataType : 'json',
	    			type : 'POST',
	    			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
	    			success:function(data){
	    				
	    				if(data.userInfo){
	    					
	    					var table = $('#'+tableId).find('tbody');
	    			        
	    					var dom = '<tr>'
	    						dom +=	'<td><input type="checkbox" name="chk" class="" />'
	    						dom +=		'<input type="hidden" id="apprLnList.emplNo" name="apprLnList.emplNo" value="'+data.userInfo.emplNo+'" />'
	    						dom +=		'<input type="hidden" name="apprLnList.userNm" value="'+data.userInfo.userNm+'" />'
	    						dom +=		'<input type="hidden" name="apprLnList.apprOrdr" value="'+apprOrdrIdx+'" />'
	    						dom +=	'</td>'
	    						dom +=	'<td>' + data.userInfo.userNm + '</td>' 
	    						dom +=	'<td>' + data.userInfo.orgnztNm + '</td>'
	    						dom +=	'<td>' + data.userInfo.ofcpsNm + '</td>';
	    						dom +=	'<td><input type="text" name="apprLnList.position" class="inputText width100p" placeholder="직책을 입력해주세요." /></td>';
	    						dom +='</tr>';
	    						
	    					$(dom).appendTo(table);
	    					
	    					idx++;
	    					apprOrdrIdx++;
	    				}
	    			}
		        });
			}
	        fncLineReCnt();//test
		}
		
		// 결재선 초기화
		function fncInit() {
			var checkbox = $('input[name="chk"]');
			checkbox.each(function() {
				$(this).closest('tr').remove();
			});
		}
		
		//저장 함수
		function fncSave() {
			
			var tableRowCnt = $('#approval').find('tbody tr').length;
			
			if(tableRowCnt == 0){
				alert('저장할 결재자가 없습니다. 결재자를 추가 해주세요.');
				return false;
			}
			
			if( tableRowCnt > 4 ) {
				alert('결재자는 4명까지 지정할 수 있습니다.');
				return false;
			}	
			
			if (confirm("결재라인을 저장하시겠습니까?")) {
				
				fncLineReCnt();
				
				var frm = $('form[name="frm"]');
				
				frm.find('input[name="apprLnList.emplNo"]').each(function(i) {
					fncRename(this, i);
				});
				
				frm.find('input[name="apprLnList.userNm"]').each(function(i) {
					fncRename(this, i);
				});
				
				frm.find('input[name="apprLnList.apprOrdr"]').each(function(i) {
					fncRename(this, i);
				});
				
				frm.find('input[name="apprLnList.position"]').each(function(i) {
					fncRename(this, i);
				});
				
				 $.ajax({
					type:"post",
					url:"<c:url value='/com/epay/popup/registApprLn.do'/>",
					data:$("form[name=frm]").serializeArray(),
					dataType : 'json',
					type : 'POST',
					contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
					success:function(data){
						window.opener.fn_epayApprLnPopupCallback(data);
						window.close();
					}
				}); 
			}
		}
		
		// object의 name 속성을 재정의한다.(배열로 만들기 위함)
		function fncRename(obj, i) {
			var names = $(obj).attr('name').split('.');
			var rname = names[0] + '[' + i + '].' + names[1];
			$(obj).attr('name', rname);
		}
		
		// 저장 전 결재순서 셋팅
		function fncSetSanctnOrdr(tbl) {
			// 기존 객체 삭제
			$('#' + tbl).find('input[name="apprLnList.apprOrdr"]').remove();

			for (var i = 1; i <= 100; i++) {
				var ordr = 1;
				$('#' + tbl).find('input[name="order"]').each(function() {
					var vOrder = $(this).val();
					if (vOrder == i) {
						var dom = '<input type="hidden" name="apprLnList.apprOrdr" value="'+ordr+'_'+vOrder+'" />';
						$(this).closest('tr').append(dom);
						ordr++;
					}
				});
			}
		}
		
		// 즐겨찾기 목록 조회
		function fn_selectFavoriteItem(apprLineId) {
			$.ajax({
				url : '<c:url value="/com/epay/popup/epayApprLnCfgList.do"/>',
				data : {
					apprLineId : apprLineId
				},
				type : 'post',
				dataType : 'json'
			}).done(function(data) {
				// 기존 결재자 삭제
				fncInit();

				for(var i = 0; i < data.apprLnCfgList.length; i++) {
					var dom = '<tr>'
						dom +=	'<td><input type="checkbox" name="chk" class="" />'
						dom += 		'<input type="hidden" name="apprLnList.apprLnCfgId" value="'+data.apprLnCfgList[i].apprLnCfgId+'" />'
						dom += 		'<input type="hidden" name="apprLnList.apprLineId" value="'+data.apprLnCfgList[i].apprLineId+'" />'
						dom += 		'<input type="hidden" name="apprLnList.apprTy" value="'+data.apprLnCfgList[i].apprTy+'" />'
						dom += 		'<input type="hidden" name="apprLnList.apprOrdr" value="'+data.apprLnCfgList[i].apprOrdr+'" />'
						dom += 		'<input type="hidden" name="apprLnList.emplNo" value="'+data.apprLnCfgList[i].emplNo+'" />'
						dom += 		'<input type="hidden" name="apprLnList.userNm" value="'+data.apprLnCfgList[i].userNm+'" />'
						dom += 	'</td>'
						dom += 	'<td>' + data.apprLnCfgList[i].userNm + '</td>'
						dom += 	'<td>' + data.apprLnCfgList[i].orgnztNm + '</td>' 
						dom += 	'<td>' + data.apprLnCfgList[i].ofcpsNm	+ '</td>'
						dom += 	'<td><input type="text" name="apprLnList.position" class="inputText width100p" placeholder="직책을 입력해주세요." value="'+ data.apprLnCfgList[i].position+'"/></td>'
						dom +='</tr>'
						$('#approval tbody').append(dom);
						
						idx++;
				}
				
			}).fail(function(obj, status, errorMsg) {
				alert('정보를 가져오는 중 오류가 발생했습니다.\n관리자에게 문의바랍니다.');
			});
		}
		
		// 즐겨찾기 저장
		function fn_saveFavoriteItem() {
			var tmpSan = $('#approval').find('input[name="chk"]:checked').length;
			
			if(tmpSan == 0){
				alert('즐겨찾기 저장할 결재자가 없습니다.');
				return false;
			}
			
			if( tmpSan > 4 ) {
				alert('결재자는 4명까지 지정할 수 있습니다.');
				return false;
			}
		
			var apprLineNm = $('#apprLineNm').val();
			
			if (apprLineNm == "") {
				alert('즐겨찾기명을 입력 해 주세요.');
				$('#apprLineNm').focus();
			} else {
				if (confirm("현재의 결재선이 즐겨찾기로 저장됩니다.\n 저장하시겠습니까?")) {
					
					fncLineReCnt();
					
					var frm = $('form[name="frm"]');
					
					frm.find('input[name="apprLnList.emplNo"]').each(function(i) {
						fncRename(this, i);
					});
					
					frm.find('input[name="apprLnList.userNm"]').each(function(i) {
						fncRename(this, i);
					});
					
					frm.find('input[name="apprLnList.apprOrdr"]').each(function(i) {
						fncRename(this, i);
					});
					
					frm.find('input[name="apprLnList.position"]').each(function(i) {
						fncRename(this, i);
					});
					
					$.ajax({
						type:"post",
						url:"<c:url value='/com/epay/popup/saveFavoriteItem.do'/>",
						data:$("form[name=frm]").serializeArray(),
						dataType : 'json',
						type : 'POST',
						contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
						success:function(data){
							fn_requestList();
						}
					});
					
				}
			}
		}
		
		function fn_requestList(){
			document.frm.method.value = 'payment';
			document.frm.action = "<c:url value='/com/epay/popup/epayApprLnPopup.do'/>";
			document.frm.submit();
		}
		
		// 즐겨찾기 삭제
		function fn_removeFavoriteItem(obj, apprLineId) {
			$.ajax({
				type:"post",
				url:"<c:url value='/com/epay/popup/removeFavoriteItem.do'/>",
				data : {
					apprLineId : apprLineId
				},
				type : 'post',
				dataType : 'json'
			}).done(function(data) {
				$(obj).closest('tr').remove();
				alert('삭제되었습니다.');
			}).fail(function(obj, status, errorMsg) {
				alert('정보를 가져오는 중 오류가 발생했습니다.\n관리자에게 문의바랍니다.');
			});
		}
		
		// 결재자 삭제
		function fn_removeApproval() {
			var checkbox = $('input[name="chk"]:checked');
			if (checkbox.length == 0) {
				alert("대상을 먼저 선택해 주세요.");
			} else {
				if(confirm("삭제 하시겠습니까?")) {
					checkbox.each(function() {
						$(this).closest('tr').remove();
					});
					
					fncLineReCnt();
				}
			}
		}
		
		// 결재자 올리기
		function fncUp() {
			var checkbox = $('input[name="chk"]:checked');
			checkbox.each(function() {
				var len = $(this).closest('tbody').find('tr').length;
				if (len > 1) {
					$(this).closest('tr').prev().before(
						$(this).closest('tr'));
				}
			});
			fncLineReCnt();
		}

		// 결재자 내리기
		function fncDown() {
			var checkbox = $('input[name="chk"]:checked');
			$(checkbox.get().reverse()).each(function() {
				var len = $(this).closest('tbody').find('tr').length;
				if (len > 1) {
					$(this).closest('tr').next().after(
							$(this).closest('tr'));
				}
			});
			fncLineReCnt();
		}
		
		 // 결재순서 재설정
		function fncLineReCnt() {
			var sanctnCnt = 2; // 담당자의 결재순서가 1이기 때문에 결재자는 2부터 시작함.
			var checkbox = $('#approval').find('input[name="chk"]');
			checkbox.each(function() {
				$(this).closest('tr').find('input[name="apprLnList.apprOrdr"]').val(sanctnCnt);
				sanctnCnt++;
			});
		}
		
		function fncAddSanctner(tableId, sanctnTy) {
			var $tree = $('#jstree').jstree(true);
			var selectedId = $tree.get_selected();
			var nodeType = $tree.get_type(selectedId);
			
			// 기존 사용자 중복 체크
			var flag = true;
			var checkbox1 = $('input[name="chk"]');
			checkbox1.each(function() {
				var sanctnerId = $(this).closest('tr').find('input[name="sanctnerLnList.sanctnerId"]').val();
				if( sanctnerId == selectedId ) {
					flag = false;
				}
			});
			// 순서 결정
			var sanctnCnt = 1;
			if( sanctnTy == 'SANCTNER' ) {
				var checkbox2 = $('#sanctner').find('input[name="chk"]');
				checkbox2.each(function() {
					sanctnCnt++;
				});
			}
			if( flag ) {
				var url = ctx+"/sppartners/adb/selectAdbTree.do";
				var param = 'treeId=INTERNAL&nodeType=' + nodeType + '&nodeId='
						+ selectedId;
				$.post(url, param,	function(data) {
					if (nodeType == "user" || nodeType == "user_") {
						var table = $('#'+tableId).find('tbody');
						var dom = "<tr>"
								+ '<td><input type="checkbox" name="chk" class="" />'
								+ '<input type="hidden" name="sanctnerLnList.sanctnTy" value="'+sanctnTy+'" />'
								+ '<input type="hidden" name="sanctnerLnList.sanctnerId" value="'+data.esntlId+'" />'
								+ '<input type="hidden" name="sanctnerLnList.userNm" value="'+data.userNm+'" />'
								+ '<input type="hidden" name="order" value="'+sanctnCnt+'" />'
								+ '</td>'
								+ '<td>' + data.userNm + '</td>' 
								+ '<td>' + data.orgnztNm + '</td>'
								+ '<td>' + data.rspofcNm + '</td>';
						if( sanctnTy == 'SANCTNER' ) {
							dom += '<td>' + '<input type="checkbox" name="spprtr" />' + '</td>';
							
						}
						dom += '</tr>';
						$(dom).appendTo(table);
						fncEventSpprtr();	
					} else {
						alert("대상을 먼저 선택해 주세요.");
					}
				});
			} else {
				alert('이미 등록되어 있습니다.');
			}
		}
	</script>
	
    <style>
        #treeview .k-sprite {
            background-image: url("/images/tis/logo2.png");
        }

        .rootfolder { background-position: 0 0; }
        .dept     { background-position: 0 -16px; }
        .user        { background-position: 0 -32px; }
        .html       { background-position: 0 -48px; }
        .image      { background-position: 0 -64px; } 
    </style>

    <form name="frm" method="post">
	<!-- <div class="popupwrap"> -->  
	<div class="subwrap">
		<h1><i class="fa fa-th-large" aria-hidden="true"></i>결재라인지정</h1>
		<div class="space20"></div>
			<!-- 작성문서정보 -->
			<div style="width:320px !important; position:relative; top:25px !important;">
			
				<table class="tablelist">
					<colgroup>
						<col class="width50">
						<col class="widthauto">
					</colgroup>
					<thead>
						<tr>
							<th colspan="2">조직도</th>
						</tr>
						<tr id="treeview" colspan="2">
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>

				<!-- <h1>조직도</h1>
				<div class="demo-section k-content">
			        <div>
			            <h4>Check nodes</h4>
			            <div id="treeview"></div>
			        </div>
			        <div style="padding-top: 2em;">
			            <h4>Status</h4>
			            <p id="result">No nodes checked.</p>
			        </div>
		    	</div> -->
		    	
		    	<div class="space20"></div>
				<div class="clear"></div>
				<div>
					<!-- <table class="tableview"> -->
					<table class="tablelist">
						<colgroup>
							<col class="widthauto">
							<col class="width80">
						</colgroup>
						<tbody>
							<tr>
								<td><input type="text" id="apprLineNm" name="apprLineNm" value="${apprLineNm}" class="inputText width100p" placeholder="즐겨찾기명을 입력해주세요." /></td>
								<td class="alignR"><button type="button" class="btn01" onclick="fn_saveFavoriteItem();">저장</button></td>
							</tr>
						</tbody>
					</table>
					<div class="space20"></div>
					<table class="tablewrite" id="bkmk">
						<colgroup>
							<col class="widthauto">
							<col class="width110">
						</colgroup>
						<tbody>
						<c:if test="${empty apprLineList }">
							<tr>
								<td colspan="2">등록된 즐겨찾기 결재라인이 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach var="apprLine" items="${apprLineList }">
							<tr>
								<td>${apprLine.apprLineNm }</td>
								<td class="alignR">
									<button type="button" class="btn05" onClick="fn_selectFavoriteItem('${apprLine.apprLineId}');">선택</button>
									<button type="button" class="btn02" onClick="fn_removeFavoriteItem(this, '${apprLine.apprLineId}')">삭제</button>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div style="position: fixed; top: 50px; height: 50px; right: 10px;">
				<input type="hidden" name="bkmkAt" value="N" />
				<button type="button" class="btn" onclick="fn_addApprover('approval');">결재자추가</button>
				<div>
					<div class="space10"></div>
					<div class="popup12contents">
						<div>
							<!-- <table class="tablewrite fr alignC" id="approval"> -->
							<table class="tablelist" id="approval">
								<colgroup>
									<col class="width50">
									<col class="width70">
									<col class="width90">
									<col class="width70">
									<col class="width140">
								</colgroup>
								<thead>
									<tr>
										<th colspan="5">결재자</th>
									</tr>
									<tr>
										<th>선택</th>
										<th>성명</th>
										<th>부서</th>
										<th>직급</th>
										<th>직책</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="sanctnerLn" items="${sanctnVO.sanctnerLnList }">
										<c:if test="${sanctnerLn.sanctnTy eq 'SANCTNER' or sanctnerLn.sanctnTy eq 'SPPRTR' }">
											<tr>
												<td>
													<input type="checkbox" name="chk" class="" />
													<input type="hidden" name="sanctnerLnList.sanctnTy" value="${sanctnerLn.sanctnTy }" />
													<input type="hidden" name="sanctnerLnList.sanctnerId" value="${sanctnerLn.sanctnerId }" />
													<input type="hidden" name="sanctnerLnList.userNm" value="${sanctnerLn.loginVO.name }" />
													<input type="hidden" name="order" value="${fn:split(sanctnerLn.sanctnOrdr, '_')[1]}" />
												</td>
												<td>${sanctnerLn.loginVO.name }</td>
												<td>${sanctnerLn.loginVO.orgnztNm }</td>
												<td>${sanctnerLn.loginVO.rspofcNm }</td>
												<td><input type="text" id="" class="inputText width100p" placeholder="직책을 입력해주세요." /></td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</div>
			</div>
			<!-- 하단메뉴 -->
			<div class="space40"></div>
			<div class="bottombtngroup" style="position: fixed; bottom: 10px; right: 10px;">
				<div class="fr">
					<button type="button" class="btn04" onclick="fncUp();">▲ 올리기</button>
					<button type="button" class="btn04" onclick="fncDown();">▼ 내리기</button>
					<button type="button" class="btn04" onclick="fn_removeApproval();">삭제</button>
					<button type="button" class="btn04" onclick="fncInit();">결재선초기화</button>
					<button type="button" class="btn04" onClick="fncSave();">저장</button>
				</div>
			</div>
			<div class="space30"></div>
	</div>
	</form>




<script>

	// function that gathers IDs of checked nodes
    function checkedNodeIds(nodes, checkedNodes) {
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i].checked) {
                checkedNodes.push(nodes[i].id);
            }
            if (nodes[i].hasChildren) {
                checkedNodeIds(nodes[i].children.view(), checkedNodes);
            }
        }
    }

    // show checked node IDs on datasource change
    function onCheck() {
        var checkedNodes = [],
            treeView = $("#treeview").data("kendoTreeView"),
            message;

        checkedNodeIds(treeView.dataSource.view(), checkedNodes);

        if (checkedNodes.length > 0) {
            message = "IDs of checked nodes: " + checkedNodes.join(",");
        } else {
            message = "No nodes checked.";
        }

        $("#result").html(message);
    }
        
    function fn_loadTreeData(){
			
    	$.ajax({    
    		url:'<c:url value="/com/epay/popup/epayOrgnztInfo.do"/>',
    		data:$("form[name=frm]").serializeArray(), 
    		dataType : 'json',
    		type : 'POST',
    		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
    		success:function(data){
    				
    			$("#treeview").kendoTreeView({
    		    	checkboxes: {
    		        checkChildren: true
    		        },
    		        check: onCheck
    		    }); 
    				 
    			var treeview = $("#treeview").data("kendoTreeView");
    				 
    			if(data.deptList){

    				$.each(data.deptList, function(idx, item){
    					treeview.append({ id:item.code, text: item.codeNm, expanded:false, spriteCssClass: "dept", imageUrl:"../../../images/tis/group_16x16.png" });	 
    				});
    			}
    				 
    			if(data.userList){

    				$.each(data.userList, function(idx, item){
    						 
    					var dataSource = treeview.dataSource;
    					var dataItem = dataSource.get(item.orgnztId);
    					var $selected = treeview.findByUid(dataItem.uid);
    						 
    					//treeview.append({ id:item.emplNo, text: item.userNm, spriteCssClass: "user" }, treeview.findByUid(item.orgnztId));
    					treeview.append({ id:item.emplNo, text: item.userNm, expanded:false, spriteCssClass: "user", imageUrl:"../../../images/tis/user_16x16.png" }, $selected);
    				});
    			}
    				 
    			treeview.collapse(".k-item");
    				
    			} // end success:function(data)
    		}); // end $.ajax 
    	}
        
        $(document).ready( function() {
    	    
        	fn_loadTreeData();
        		
        }); // end $(document).ready()
        
    </script>