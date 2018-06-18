package tis.com.financial.periodicSettle.web;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tis.com.financial.periodicSettle.service.PeriodicSettleService;
import tis.com.financial.periodicSettle.service.PeriodicSettleVO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class PeriodicSettleController {
	
	@Resource(name = "periodicSettleService")
    private PeriodicSettleService periodicSettleService;
	
	/*지출*/
	@RequestMapping(value = "/com/financial/periodicSettle/expense.do")
	public String expenseIeetu(@RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		request.getSession().setAttribute("affiliationId", affiliationId);
		if(affiliationId.equals("I")){
			return "tis/com/financial/periodicSettle/expenseIeetu";
		}else{
			return "tis/com/financial/periodicSettle/expenseSmaker";
		}
	}
	
	/*수입*/
	@RequestMapping(value = "/com/financial/periodicSettle/profit.do")
	public String profitIeetu(@RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		request.getSession().setAttribute("affiliationId", affiliationId);
		if(affiliationId.equals("I")){
			return "tis/com/financial/periodicSettle/profitIeetu";
		}else{
			return "tis/com/financial/periodicSettle/profitSmaker";
		}
	}

	/*엑셀데이터 불러오기*/
	@RequestMapping(value="/com/financial/periodicSettle/loadExcelData.do", produces = "application/json; charset=utf8")
	public @ResponseBody String loadExcelData(@RequestParam("callback") String collback, @RequestParam("affiliationId") String affiliationId,
											  @RequestParam("typeAt") String typeAt, HttpServletRequest request) throws Exception {
		PeriodicSettleVO periodicSettleVO  = new PeriodicSettleVO();
		periodicSettleVO.setAffiliationId(affiliationId);
		periodicSettleVO.setTypeAt(typeAt);
		
		if(typeAt.equals("E")){
			
			List<?> loadData = periodicSettleService.loadData_E(periodicSettleVO);
			
			if(loadData.isEmpty()){
				periodicSettleService.addDefauitData(periodicSettleVO);
				loadData = periodicSettleService.loadData_E(periodicSettleVO);
			}

			return collback + "(" + new Gson().toJson(loadData) + ")";
		}else{
			
			List<?> loadData = periodicSettleService.loadData_P(periodicSettleVO);
			
			if(loadData.isEmpty()){
				periodicSettleService.addDefauitData(periodicSettleVO);
				loadData = periodicSettleService.loadData_P(periodicSettleVO);
			}
			return collback + "(" + new Gson().toJson(loadData) + ")";
		}
	}
	
	/*엑셀데이터 업데이트*/
	@RequestMapping(value = "/com/financial/periodicSettle/updtExcelData.do", produces = "application/json; charset=utf8")
	public void updtExcelData(@RequestParam("models") String models, @RequestParam("affiliationId") String affiliationId,
							  @RequestParam("typeAt") String typeAt, HttpServletRequest request) throws Exception {
		JsonArray entries = (JsonArray) new JsonParser().parse(models);
		for(int i=0; i < entries.size() ; i++){
			String orgnztNm = "";
			
			JsonElement jsonDivNm = ((JsonObject)entries.get(i)).get("divNm");
			JsonElement jsonCalculDe = ((JsonObject)entries.get(i)).get("calculDe");
			JsonElement jsonCalculNm = ((JsonObject)entries.get(i)).get("calculNm");
			JsonElement jsonCmpnyNm = ((JsonObject)entries.get(i)).get("cmpnyNm");
			JsonElement jsonCost = ((JsonObject)entries.get(i)).get("cost");
			JsonElement jsonPaymentScDt = ((JsonObject)entries.get(i)).get("paymentScDt");
			JsonElement jsonRm = ((JsonObject)entries.get(i)).get("rm");
			JsonElement jsonCalculId = ((JsonObject)entries.get(i)).get("calculId");
			
			if(typeAt.equals("P")){
				JsonElement jsonOrgnztNm = ((JsonObject)entries.get(i)).get("orgnztNm");
				orgnztNm = jsonOrgnztNm.toString();
			}
			
			String divNm = jsonDivNm.toString();
			String calculDe = jsonCalculDe.toString();
			String calculNm = jsonCalculNm.toString();
			String cmpnyNm = jsonCmpnyNm.toString();
			String stringCost = jsonCost.toString();
			int cost = Integer.parseInt(stringCost);
			String paymentScDt = jsonPaymentScDt.toString();
			String rm = jsonRm.toString();
			String calculId = jsonCalculId.toString();
			
			PeriodicSettleVO periodicSettleVO  = new PeriodicSettleVO();
			
			periodicSettleVO.setDivNm(divNm.replaceAll("\"", ""));
			periodicSettleVO.setCalculDe(calculDe.replaceAll("\"", ""));
			periodicSettleVO.setCalculNm(calculNm.replaceAll("\"", ""));
			periodicSettleVO.setCmpnyNm(cmpnyNm.replaceAll("\"", ""));
			periodicSettleVO.setCost(cost);
			periodicSettleVO.setPaymentScDt(paymentScDt.replaceAll("\"", ""));
			periodicSettleVO.setRm(rm.replaceAll("\"", ""));
			periodicSettleVO.setCalculId(calculId.replaceAll("\"", ""));
			periodicSettleVO.setAffiliationId(affiliationId);
			periodicSettleVO.setTypeAt(typeAt);
			periodicSettleVO.setOrgnztNm(orgnztNm.replaceAll("\"", ""));

			periodicSettleService.updateData(periodicSettleVO);
		}
	}
	
	/*엑셀데이터 추가*/
	@RequestMapping(value = "/com/financial/periodicSettle/insertExcelData.do", produces = "application/json; charset=utf8")
	public void insertExcelData(@RequestParam("models") String models, @RequestParam("affiliationId") String affiliationId,
								@RequestParam("typeAt") String typeAt, HttpServletRequest request) throws Exception {
		JsonArray entries = (JsonArray) new JsonParser().parse(models);
		
		for(int i=0; i < entries.size() ; i++){
			String orgnztNm = "";
			
			JsonElement jsonDivNm = ((JsonObject)entries.get(i)).get("divNm");
			JsonElement jsonCalculDe = ((JsonObject)entries.get(i)).get("calculDe");
			JsonElement jsonCalculNm = ((JsonObject)entries.get(i)).get("calculNm");
			JsonElement jsonCmpnyNm = ((JsonObject)entries.get(i)).get("cmpnyNm");
			JsonElement jsonCost = ((JsonObject)entries.get(i)).get("cost");
			JsonElement jsonPaymentScDt = ((JsonObject)entries.get(i)).get("paymentScDt");
			JsonElement jsonRm = ((JsonObject)entries.get(i)).get("rm");
			JsonElement jsonCalculId = ((JsonObject)entries.get(i)).get("calculId");
			
			if(typeAt.equals("P")){
				JsonElement jsonOrgnztNm = ((JsonObject)entries.get(i)).get("orgnztNm");
				orgnztNm = jsonOrgnztNm.toString();
			}
			
			String divNm = jsonDivNm.toString();
			String calculDe = jsonCalculDe.toString();
			String calculNm = jsonCalculNm.toString();
			String cmpnyNm = jsonCmpnyNm.toString();
			String stringCost = jsonCost.toString();
			int cost = Integer.parseInt(stringCost);
			String paymentScDt = jsonPaymentScDt.toString();
			String rm = jsonRm.toString();
			String calculId = jsonCalculId.toString();
			
			PeriodicSettleVO periodicSettleVO  = new PeriodicSettleVO();
			
			periodicSettleVO.setDivNm(divNm.replaceAll("\"", ""));
			periodicSettleVO.setCalculDe(calculDe.replaceAll("\"", ""));
			periodicSettleVO.setCalculNm(calculNm.replaceAll("\"", ""));
			periodicSettleVO.setCmpnyNm(cmpnyNm.replaceAll("\"", ""));
			periodicSettleVO.setCost(cost);
			periodicSettleVO.setPaymentScDt(paymentScDt.replaceAll("\"", ""));
			periodicSettleVO.setRm(rm.replaceAll("\"", ""));
			periodicSettleVO.setCalculId(calculId.replaceAll("\"", ""));
			periodicSettleVO.setAffiliationId(affiliationId);
			periodicSettleVO.setTypeAt(typeAt);
			periodicSettleVO.setOrgnztNm(orgnztNm.replaceAll("\"", ""));
			
			periodicSettleService.insertData(periodicSettleVO);
		}
	}
	
	/*엑셀데이터 삭제*/
	@RequestMapping(value = "/com/financial/periodicSettle/deleteExcelData.do", produces = "application/json; charset=utf8")
	public void deleteExcelData(@RequestParam("models") String models, @RequestParam("affiliationId") String affiliationId,
								@RequestParam("typeAt") String typeAt, HttpServletRequest request) throws Exception {
		
		JsonArray entries = (JsonArray) new JsonParser().parse(models);
		for(int i=0; i < entries.size() ; i++){
			JsonElement jsonCalculId = ((JsonObject)entries.get(i)).get("calculId");
			
			String calculId = jsonCalculId.toString();
			
			PeriodicSettleVO periodicSettleVO  = new PeriodicSettleVO();
			
			periodicSettleVO.setCalculId(calculId.replaceAll("\"", ""));
			periodicSettleVO.setAffiliationId(affiliationId);
			periodicSettleVO.setTypeAt(typeAt);
			
			periodicSettleService.deleteData(periodicSettleVO);
		}
	}
}