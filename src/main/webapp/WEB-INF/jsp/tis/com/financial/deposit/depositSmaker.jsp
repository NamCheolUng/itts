<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/EgovPageLink.do?link=tis/com/inc/header" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/lmenu" />
<c:import url="/EgovPageLink.do?link=tis/com/inc/modal" />
<script>
$(document).ready(function(){
	
    $('input[name="affiliationId"]').change(function(){
		if($(this).val() == 'I') {
			location.href = "<c:url value='/com/financial/deposit/deposit.do?affiliationId=I'/>" 
		}else {
			location.href = "<c:url value='/com/financial/deposit/deposit.do?affiliationId=S'/>" 
		}
	});
})
</script>
<div id="subwrap">
	<h1>보증금관리 (Smaker)</h1>
	<div class="space20"></div>
	<div class="btngroup fr">
		<label><input type="radio" name="affiliationId" id="affiliationId" value="I">이튜</label>
		<label><input type="radio" name="affiliationId" id="affiliationId" value="S" checked="checked">에스메이커</label>
	</div>
	<div id="axceledit">
    <div class="box wide">
        <div class="box-col">
        <ul class="options">
            <li>
                <button class="k-button" id="save">저장</button>
                <button class="k-button" id="cancel">취소</button>
            </li>
        </ul>
        </div>
    </div>
	<div id="spreadsheet" style="width: 100%"></div>
    <script>
        $(function() {
            var shouldPopulateHeader = true; 
            
            var dataSource = new kendo.data.DataSource({
                   requestEnd: function (e) {
                    setTimeout(function(e) {
                      if(shouldPopulateHeader) {
                        shouldPopulateHeader = false;

                        var spreadsheet = $("#spreadsheet").data("kendoSpreadsheet");
                        var sheet = spreadsheet.activeSheet();

                        // Change the default headers for the first and the second column
                        sheet.batch(function(){
                            sheet.range("A1").value("지급일자").enable(false);
                            sheet.range("B1").value("환급일자").enable(false);
                            sheet.range("C1").value("과제명(건명)").enable(false);
                            sheet.range("D1").value("금액").enable(false);
                            sheet.range("E1").value("환급금액").enable(false);
                            sheet.range("F1").value("비고").enable(false);

                            sheet.range("A2:A100").textAlign('right');
                            sheet.range("B2:B100").textAlign('right');
                            sheet.range("C2:C100").textAlign('right');
                            sheet.range("D2:D100").format('#,###').textAlign('right');
                            sheet.range("E2:E100").format('#,###').textAlign('right');
                            sheet.range("F2:F100").textAlign('right');
                          
                          sheet.hideColumn(6);
                        }, { recalc: true });
                      }
                    }, 0);
                  },
                transport: {
                    read:  {
                    	url: "<c:url value='/com/financial/deposit/loadExcelData.do'/>?affiliationId=S",
                        dataType: "jsonp"
                    },
                    update: {
                    	contentType: "application/json; charset=utf-8",
                    	url: "<c:url value='/com/financial/deposit/updtExcelData.do'/>?affiliationId=S",
                        dataType: "jsonp",
                    },
                     destroy: {
                    	 url: "<c:url value='/com/financial/deposit/deleteExcelData.do'/>?affiliationId=S",
                        dataType: "jsonp"
                    },
                    create: {
                    	url: "<c:url value='/com/financial/deposit/insertExcelData.do'/>?affiliationId=S",
                        dataType: "jsonp"
                    },
                    parameterMap: function(options, operation) {
                        if (operation !== "read" && options.models) {
                            return {models: kendo.stringify(options.models)};
                        }
                    }
                },
                batch: true,
                change: function() {
                   $("#cancel, #save").toggleClass("k-state-disabled", !this.hasChanges());
                },
                schema: {
                    model: {
                        id: "depositId",
                        fields: {
                        	payDt: { type: "date" },
                        	refundDt: { type: "date"},
                        	depositNm: { type: "string" },
                        	cost: { type: "number" },
                        	refundCost: { type: "number"},
                        	rm: { type: "string" },
                        	depositId: { type: "string" }
                        }
                    }
                }
            });
            $("#spreadsheet").kendoSpreadsheet({
                columns: 20,
                rows: 100,
                toolbar: {
                	home: [ "exportAs",["bold", "italic", "underline"], "format", "fontSize", "fontFamily", "backgroundColor", "textColor", "borders", "filter" ]
                },
                sheetsbar: false,
                sheets: [{
                    name: "test",
                    dataSource: dataSource,
                    rows: [{
                        height: 40,
                        cells: [
                        {
                            bold: "true",
                            background: "rgb(236,239,241)",
                            textAlign: "center",
                            color: "black",
                        },{
                            bold: "true",
                            background: "rgb(236,239,241)",
                            textAlign: "center",
                            color: "black"
                        },{
                            bold: "true",
                            background: "rgb(236,239,241)",
                            textAlign: "center",
                            color: "black",
                        },{
                            bold: "true",
                            background: "rgb(236,239,241)",
                            textAlign: "center",
                            color: "black"
                        },{
                            bold: "true",
                            background: "rgb(236,239,241)",
                            textAlign: "center",
                            color: "black"
                        },{
                            bold: "true",
                            background: "rgb(236,239,241)",
                            textAlign: "center",
                            color: "black"
                        }]
                    }],
                    columns: [
                        { width: 150 },
                        { width: 150 },
                        { width: 150 },
                        { width: 150 },
                        { width: 150 },
                        { width: 150 }
                    ]
                }]
            }).data('kendoSpreadsheet').sheets()[0].range("A2:B100").format('yyyy-MM-dd');

            $("#save").click(function() {
                if (!$(this).hasClass("k-state-disabled")) {
                    dataSource.sync();
                  	location.href = "<c:url value='/com/financial/deposit/deposit.do?affiliationId=S'/>"
                }
            });

            $("#cancel").click(function() {
                if (!$(this).hasClass("k-state-disabled")) {
                    dataSource.cancelChanges();
                }
            });
        }).data('kendoSpreadsheet').sheets()[0].range("A2:A100").format('yyyy-MM-dd');

</script>
	</div>
<h3>( 삭제시에는 셀번호 우측클릭 후 delete선택  후 저장 )</h3>
</div>