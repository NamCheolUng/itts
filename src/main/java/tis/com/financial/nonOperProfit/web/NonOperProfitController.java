package tis.com.financial.nonOperProfit.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tis.com.financial.nonOperProfit.service.NonOperProfitService;
import tis.com.financial.nonOperProfit.service.NonOperVO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class NonOperProfitController {
	
	@Resource(name = "NonOperProfitService")
    private NonOperProfitService nonOperService;
	
	/*영업외 이익관리 리스트*/
	@RequestMapping(value = "/com/financial/nonOperProfit/nonOperProfitList.do")
	public String nonOperProfitList(@RequestParam("affiliationId") String affiliationId, HttpServletRequest request ) throws Exception {
		request.getSession().setAttribute("affiliationId", affiliationId);
		if(affiliationId.equals("I")){
			return "tis/com/financial/nonOperProfit/nonOperProfitListIeetu";
		}else{
			return "tis/com/financial/nonOperProfit/nonOperProfitListSmaker";
		}
	}
	
	/*엑셀데이터 불러오기*/
	@RequestMapping(value="/com/financial/nonOperProfit/loadExcelData.do", produces = "application/json; charset=utf8")
	public @ResponseBody String loadExcelData(@RequestParam("callback") String collback, @RequestParam("affiliationId") String affiliationId,
												HttpServletRequest request) throws Exception {
		NonOperVO nonOperVO  = new NonOperVO();
		nonOperVO.setAffiliationId(affiliationId);
		List<?> loadData = nonOperService.loadData(nonOperVO);

		if(loadData.isEmpty()){
			nonOperService.addDefauitData(nonOperVO);
			loadData = nonOperService.loadData(nonOperVO);
		}
		return collback + "(" + new Gson().toJson(loadData) + ")";
		
	}
	
	/*엑셀데이터 업데이트*/
	@RequestMapping(value = "/com/financial/nonOperProfit/updtExcelData.do", produces = "application/json; charset=utf8")
	public void updtExcelData(@RequestParam("models") String models, @RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		JsonArray entries = (JsonArray) new JsonParser().parse(models);
		for(int i=0; i < entries.size() ; i++){
			JsonElement jsonOtdealDt = ((JsonObject)entries.get(i)).get("otdealDt");
			JsonElement jsonOtdealNm = ((JsonObject)entries.get(i)).get("otdealNm");
			JsonElement jsonAmount = ((JsonObject)entries.get(i)).get("amount");
			JsonElement jsonRm = ((JsonObject)entries.get(i)).get("rm");
			JsonElement jsonOtdealId = ((JsonObject)entries.get(i)).get("otdealId");
			
			String otdealDt = jsonOtdealDt.toString();
			String otdealNm = jsonOtdealNm.toString();
			String stringAmount = jsonAmount.toString();
			int amount = Integer.parseInt(stringAmount);
			String rm = jsonRm.toString();
			String otdealId = jsonOtdealId.toString();
			
			NonOperVO nonOperVO  = new NonOperVO();
			
			nonOperVO.setOtdealDt(otdealDt.replaceAll("\"", ""));
			nonOperVO.setOtdealNm(otdealNm.replaceAll("\"", ""));
			nonOperVO.setAmount(amount);
			nonOperVO.setRm(rm.replaceAll("\"", ""));
			nonOperVO.setOtdealId(otdealId.replaceAll("\"", ""));
			nonOperVO.setAffiliationId(affiliationId);

			nonOperService.updateData(nonOperVO);
		}
	}
	
	/*엑셀데이터 추가*/
	@RequestMapping(value = "/com/financial/nonOperProfit/insertExcelData.do", produces = "application/json; charset=utf8")
	public void insertExcelData(@RequestParam("models") String models, @RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		JsonArray entries = (JsonArray) new JsonParser().parse(models);
		
		for(int i=0; i < entries.size() ; i++){
			JsonElement jsonOtdealDt = ((JsonObject)entries.get(i)).get("otdealDt");
			JsonElement jsonOtdealNm = ((JsonObject)entries.get(i)).get("otdealNm");
			JsonElement jsonAmount = ((JsonObject)entries.get(i)).get("amount");
			JsonElement jsonRm = ((JsonObject)entries.get(i)).get("rm");
			String otdealDt = jsonOtdealDt.toString();
			String otdealNm = jsonOtdealNm.toString();
			String StringAmount = jsonAmount.toString();
			int amount = Integer.parseInt(StringAmount);
			String rm = jsonRm.toString();
			
			NonOperVO nonOperVO  = new NonOperVO();
			
			nonOperVO.setOtdealDt(otdealDt.replaceAll("\"", ""));
			nonOperVO.setOtdealNm(otdealNm.replaceAll("\"", ""));
			nonOperVO.setAmount(amount);
			nonOperVO.setRm(rm.replaceAll("\"", ""));
			nonOperVO.setAffiliationId(affiliationId);
			
			nonOperService.insertData(nonOperVO);
		}
	}
	/*엑셀데이터 삭제*/
	@RequestMapping(value = "/com/financial/nonOperProfit/deleteExcelData.do", produces = "application/json; charset=utf8")
	public void deleteExcelData(@RequestParam("models") String models, @RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		
		JsonArray entries = (JsonArray) new JsonParser().parse(models);
		for(int i=0; i < entries.size() ; i++){
			JsonElement jsonOtdealId = ((JsonObject)entries.get(i)).get("otdealId");
			
			String otdealId = jsonOtdealId.toString();
			
			NonOperVO nonOperVO  = new NonOperVO();
			nonOperVO.setOtdealId(otdealId.replaceAll("\"", ""));
			nonOperVO.setAffiliationId(affiliationId);
			
			nonOperService.deleteData(nonOperVO);
		}
	}
}