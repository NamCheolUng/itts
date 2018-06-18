function makeFileAttachment() {
	var maxFileNum = document.board.posblAtchFileNumber.value;
	if (maxFileNum == null || maxFileNum == "") {
		maxFileNum = 3;
	}
	var multi_selector = new MultiSelector(document.getElementById('egovComFileList'), maxFileNum);
	multi_selector.addElement(document.getElementById('egovComFileUploader'));
}

// 파일 업로드 하기 전 이름 변경
function reNameAtchFile() {
	for (i = 0; i < $(".atchFile").length; i++) {
		$(".atchFile:eq(" + i + ")").attr("name", "file_" + i);
	}
}

// 첨부파일 추가
function addAtchFile() {
	var template = "<tr>";
	template += "<th>첨부파일</th>";
	template += "		<td>";
	template += "			<input name=\"\" class=\"inputFile width300 atchFile\" type=\"file\">";
	template += "			<button type=\"button\" class=\"btn fileplus\" onclick=\"deleteAtchFile(this)\">삭제</button>";
	template += "		</td>";
	template += "</tr>";
	$("#atchFile").append(template);
}

function addAtchFileScollMove() {
	var template = "<tr>";
	template += "<th>첨부파일</th>";
	template += "		<td>";
	template += "			<input name=\"\" class=\"inputFile width300 atchFile\" type=\"file\">";
	template += "			<button type=\"button\" class=\"btn fileplus\" onclick=\"deleteAtchFile(this)\">삭제</button>";
	template += "		</td>";
	template += "</tr>";
	$("#atchFile").append(template);
	$('.subcontents').animate({scrollTop : $("#atchFile td").last().offset().top}, 400);
}

// 추가된 첨부파일 삭제
function deleteAtchFile(obj) {
	$(obj).parent().parent().remove();
}