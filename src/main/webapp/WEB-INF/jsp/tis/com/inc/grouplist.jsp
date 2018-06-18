<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script>
/*****************
 * 사용법
 * 리스트의 그룹 행은 <span id="group_list_{그룹id}">
 * 리스트의 그룹 행의 '전체 체크'의 checkbox는 name="group_list_all_check_{그룹id}" id="group_list_all_check_{그룹id}" 룰 호함
 * 각 리스트의 checkbox는 id="group_list_check_{그룹id}_{행id} 를 포함"
 */

var groupToggleCallback = null;
var oneItemCheckedCallback = null;
var allItemCheckedCallback = null;

function fn_setGroupToggleCallback(callback) {
	groupToggleCallback = callback;
}

function fn_setOneItemCheckedCallback(callback) {
	oneItemCheckedCallback = callback;
}

function fn_setAllItemCheckedCallback(callback) {
	allItemCheckedCallback = callback;
}

function fn_initGroupList() {
	$('span[id ^= "group_list_"]').css('cursor', 'pointer');
	
	/* Group 별 리스트 토글 */
	$('span[id ^= "group_list_"]').click(function() {
		var groupId = $(this)[0].id;
		
		groupToggleCallback(groupId, ($("." + groupId).css("display") == "none"));
		
		$("." + groupId).toggle("fast");
		
	});
	
	/* 리스트 아이템 check */
	$('input[id ^= "group_list_check_"]').click(function() {
		var id = $(this)[0].id;
		var checkbox = $("#" + id)[0];
		
		if(oneItemCheckedCallback != null) {
			oneItemCheckedCallback(checkbox.name, checkbox.checked, "'" + checkbox.value + "'");
		}
	});
	
	/* Group 별 리스트 아이템 check */
	$('input[id ^= "group_list_all_check_"]').click(function() {
		var id = $(this)[0].id;
		var checkbox = $("#" + id)[0];
		
		var values = "";
		
		var groupId = this.id.substring('group_list_all_check_'.length, this.id.length);
	
		var findString = 'input[id ^= "group_list_check_' + groupId + '_"]';
		$(findString).each(function(index) {
			$(this).prop('checked', checkbox.checked);
			$(this).attr('checked', checkbox.checked);
			var childCheckbox = $(this)[0];
			values += "'" + childCheckbox.value + "',";
		});
		
		if(values.length > 1 && values.substr(-1) == ",") {
			values = values.substr(0, values.length - 1);
		}
		
		if(allItemCheckedCallback != null) {
			allItemCheckedCallback(checkbox.checked, values);
		}
	});
}
</script>
