package tis.com.financial.deposit.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tis.com.financial.deposit.service.DepositService;
import tis.com.financial.deposit.service.DepositVO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class DepositController {
	
	@Resource(name = "DepositService")
    private DepositService depositService;
	
	/*보증금*/
	@RequestMapping(value = "/com/financial/deposit/deposit.do")
	public String depositIeetu(@RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		request.getSession().setAttribute("affiliationId", affiliationId);
		if(affiliationId.equals("I")){
			return "tis/com/financial/deposit/depositIeetu";
		}
		else{
			return "tis/com/financial/deposit/depositSmaker";
		}
	}
	
	/*엑셀데이터 불러오기*/
	@RequestMapping(value="/com/financial/deposit/loadExcelData.do", produces = "application/json; charset=utf8")
	public @ResponseBody String loadExcelData(@RequestParam("callback") String collback, @RequestParam("affiliationId") String affiliationId, 
			HttpServletRequest request ) throws Exception {
		DepositVO depositVO  = new DepositVO();
		depositVO.setAffiliationId(affiliationId);

		List<?> loadData = depositService.loadData(depositVO);
		if(loadData.isEmpty()){
			depositService.addDefauitData(depositVO);
			loadData = depositService.loadData(depositVO);
		}
		return collback + "(" + new Gson().toJson(loadData) + ")";
		
	}
	
	/*엑셀데이터 업데이트*/
	@RequestMapping(value = "/com/financial/deposit/updtExcelData.do", produces = "application/json; charset=utf8")
	public void updtExcelData(@RequestParam("models") String models, @RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		JsonArray entries = (JsonArray) new JsonParser().parse(models);
		
		for(int i=0; i < entries.size() ; i++){
			JsonElement jsonDepositId = ((JsonObject)entries.get(i)).get("depositId");
			JsonElement jsonDepositNm = ((JsonObject)entries.get(i)).get("depositNm");
			JsonElement jsonCost = ((JsonObject)entries.get(i)).get("cost");
			JsonElement jsonPayDt = ((JsonObject)entries.get(i)).get("payDt");
			JsonElement jsonRefundDt = ((JsonObject)entries.get(i)).get("refundDt");
			JsonElement jsonRefundCost = ((JsonObject)entries.get(i)).get("refundCost");
			JsonElement jsonRm = ((JsonObject)entries.get(i)).get("rm");
			
			String depositId = jsonDepositId.toString();
			String depositNm = jsonDepositNm.toString();
			String stringCost = jsonCost.toString();
			int cost = Integer.parseInt(stringCost);
			String payDt = jsonPayDt.toString();
			String refundDt = jsonRefundDt.toString();
			String stringRefundCost = jsonRefundCost.toString();
			int refundCost = Integer.parseInt(stringRefundCost);
			String rm = jsonRm.toString();
			
			DepositVO depositVO  = new DepositVO();
			
			depositVO.setDepositId(depositId.replaceAll("\"", ""));
			depositVO.setDepositNm(depositNm.replaceAll("\"", ""));
			depositVO.setCost(cost);
			depositVO.setPayDt(payDt.replaceAll("\"", ""));
			depositVO.setRefundDt(refundDt.replaceAll("\"", ""));
			depositVO.setRefundCost(refundCost);
			depositVO.setRm(rm.replaceAll("\"", ""));
			depositVO.setAffiliationId(affiliationId);

			depositService.updateData(depositVO);
		}
	}
	
	/*엑셀데이터 추가*/
	@RequestMapping(value = "/com/financial/deposit/insertExcelData.do", produces = "application/json; charset=utf8")
	public void insertExcelData(@RequestParam("models") String models, @RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		JsonArray entries = (JsonArray) new JsonParser().parse(models);
		
		for(int i=0; i < entries.size() ; i++){
			JsonElement jsonDepositId = ((JsonObject)entries.get(i)).get("depositId");
			JsonElement jsonDepositNm = ((JsonObject)entries.get(i)).get("depositNm");
			JsonElement jsonCost = ((JsonObject)entries.get(i)).get("cost");
			JsonElement jsonPayDt = ((JsonObject)entries.get(i)).get("payDt");
			JsonElement jsonRefundDt = ((JsonObject)entries.get(i)).get("refundDt");
			JsonElement jsonRefundCost = ((JsonObject)entries.get(i)).get("refundCost");
			JsonElement jsonRm = ((JsonObject)entries.get(i)).get("rm");
			
			String depositId = jsonDepositId.toString();
			String depositNm = jsonDepositNm.toString();
			String stringCost = jsonCost.toString();
			int cost = Integer.parseInt(stringCost);
			String payDt = jsonPayDt.toString();
			String refundDt = jsonRefundDt.toString();
			String stringRefundCost = jsonRefundCost.toString();
			int refundCost = Integer.parseInt(stringRefundCost);
			String rm = jsonRm.toString();
			
			DepositVO depositVO  = new DepositVO();
			
			depositVO.setDepositId(depositId.replaceAll("\"", ""));
			depositVO.setDepositNm(depositNm.replaceAll("\"", ""));
			depositVO.setCost(cost);
			depositVO.setPayDt(payDt.replaceAll("\"", ""));
			depositVO.setRefundDt(refundDt.replaceAll("\"", ""));
			depositVO.setRefundCost(refundCost);
			depositVO.setRm(rm.replaceAll("\"", ""));
			depositVO.setAffiliationId(affiliationId);
			
			depositService.insertData(depositVO);
		}
	}
	
	/*엑셀데이터 삭제*/
	@RequestMapping(value = "/com/financial/deposit/deleteExcelData.do", produces = "application/json; charset=utf8")
	public void deleteExcelData(@RequestParam("models") String models, @RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		
		JsonArray entries = (JsonArray) new JsonParser().parse(models);
		for(int i=0; i < entries.size() ; i++){
		    JsonElement jsonDepositId = ((JsonObject)entries.get(i)).get("depositId");
			
			String depositId = jsonDepositId.toString();
			
			DepositVO depositVO  = new DepositVO();
			depositVO.setDepositId(depositId.replaceAll("\"", ""));
			depositVO.setAffiliationId(affiliationId);
			
			depositService.deleteData(depositVO);
		}
	}
}