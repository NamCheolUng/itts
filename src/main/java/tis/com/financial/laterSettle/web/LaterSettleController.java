package tis.com.financial.laterSettle.web;

import java.util.List;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserDefaultVO;
import tis.com.financial.laterSettle.service.FoodExpensesVO;
import tis.com.financial.laterSettle.service.LaterSettleService;
import tis.com.financial.laterSettle.service.LaterSettleUserVO;
import tis.com.financial.laterSettle.service.ParkingVO;

@Controller
public class LaterSettleController {
	@Resource(name = "laterSettleService")
    private LaterSettleService laterSettleService;
	
	@Autowired
	private JavaMailSender EMSMailSender;
	  
	/*중식비(이튜)*/
	@RequestMapping(value = "/com/financial/laterSettle/foodExpense.do")
	public String foodExpenseIeetu(@ModelAttribute("foodExpensesVO") FoodExpensesVO foodExpensesVO, Model model,
									@RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		
		request.getSession().setAttribute("affiliationId", affiliationId);
		LaterSettleUserVO laterSettleUserVO = new LaterSettleUserVO();
		laterSettleUserVO.setAffiliationId(affiliationId);
		
		List<?> userList = laterSettleService.selectUserList(laterSettleUserVO);
		model.addAttribute("userList", userList);
		
		List<?> foodExpenseList = laterSettleService.selectFoodExpensesList(foodExpensesVO);
		List<?> yearListResult = laterSettleService.selectyearList(foodExpensesVO);
		
		model.addAttribute("foodExpenseList", foodExpenseList);
		model.addAttribute("foodExpensesVO", foodExpensesVO);
		model.addAttribute("yearListResult", yearListResult);

		if(affiliationId.equals("I")){
			return "tis/com/financial/laterSettle/foodExpenseIeetu";}
		else{
			return "tis/com/financial/laterSettle/foodExpenseSmaker";}	
	}
	
	/*중식비관리 사원추가*/
	@RequestMapping(value = "/com/financial/laterSettle/addEmplToFoodList.do")
	public String addEmplToFoodList(@ModelAttribute("foodExpensesVO") FoodExpensesVO foodExpensesVO, Model model, HttpServletRequest request) throws Exception {

		laterSettleService.insertEmplyrToFoodList(foodExpensesVO);
		
		String url = foodExpensesVO.getAffiliationId();

		return "redirect:/com/financial/laterSettle/foodExpense.do?affiliationId="+url;
	}
	
	/*중식비관리 사원중복확인*/
	@RequestMapping(value = "/com/financial/laterSettle/chekUserDupl.do")
	public @ResponseBody String chekUserDupl(ModelMap model, @RequestParam("emplNo") String emplNo,
											@RequestParam("emplNm") String emplNm, @RequestParam("foodDt") String foodDt,
											@RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		
		FoodExpensesVO foodExpensesVO = new FoodExpensesVO();
		
		foodExpensesVO.setEmplNo(emplNo);
		foodExpensesVO.setEmplNm(emplNm);
		foodExpensesVO.setFoodDt(foodDt);
		foodExpensesVO.setAffiliationId(affiliationId);
		
		return new Gson().toJson( laterSettleService.checkEmplyrUser(foodExpensesVO));
	}
	
	/*중식비관리 업데이트*/
	@RequestMapping(value = "/com/financial/laterSettle/updtFoodList.do")
	public void updtFoodList(@RequestParam("jsonData") String jsonData, @RequestParam("foodDt") String foodDt, @RequestParam("affiliationId") String affiliationId ,
								HttpServletRequest request) throws Exception {
		JsonArray entries = (JsonArray) new JsonParser().parse(jsonData);
		for(int i=0; i < entries.size() ; i++){
			int cost = 0;
			int foodCnt = 0;
			
		    JsonElement jsonFoodCnt = ((JsonObject)entries.get(i)).get("foodCnt");
		    JsonElement jsonCost = ((JsonObject)entries.get(i)).get("cost");
		    JsonElement jsonPaymentDt = ((JsonObject)entries.get(i)).get("paymentDt");
		    JsonElement jsonRm = ((JsonObject)entries.get(i)).get("rm");
		    JsonElement jsonEmplNo = ((JsonObject)entries.get(i)).get("jsonEmplNo");
		    JsonElement jsonEmplNm = ((JsonObject)entries.get(i)).get("jsonEmplNm");	

			String stringFoodCnt = jsonFoodCnt.toString().replaceAll("\"", "");
			if(!stringFoodCnt.equals("")){
				foodCnt = Integer.parseInt(stringFoodCnt);
			}			
			String stringCost = jsonCost.toString().replaceAll("\"", "");	
			if(!stringCost.equals("")){
				cost = Integer.parseInt(stringCost);
			}
			String paymentDt = jsonPaymentDt.toString();
			String rm = jsonRm.toString();
			String emplNo = jsonEmplNo.toString();
			String emplNm = jsonEmplNm.toString();

			FoodExpensesVO foodExpensesVO = new FoodExpensesVO();

			foodExpensesVO.setFoodCnt(foodCnt);
			foodExpensesVO.setCost(cost);
			foodExpensesVO.setPaymentDt(paymentDt.replaceAll("\"", ""));
			foodExpensesVO.setRm(rm.replaceAll("\"", ""));
			foodExpensesVO.setEmplNo(emplNo.replaceAll("\"", ""));
			foodExpensesVO.setEmplNm(emplNm.replaceAll("\"", ""));
			foodExpensesVO.setFoodDt(foodDt.replaceAll("\"", ""));
			foodExpensesVO.setAffiliationId(affiliationId);

			laterSettleService.updateFoodList(foodExpensesVO);
		}
	}
	
	/*중식비 리스트 삭제*/
	@RequestMapping(value = "/com/financial/laterSettle/FoodExpenseDelete.do")
	public void FoodExpenseDelete( @RequestParam("data") String data, HttpServletRequest request) throws Exception {
		String[] dateList = data.split(",");
		FoodExpensesVO foodExpensesVO = new FoodExpensesVO();

		foodExpensesVO.setEmplNo(dateList[0]);
		foodExpensesVO.setEmplNm(dateList[1]);
		foodExpensesVO.setFoodDt(dateList[2]);
		foodExpensesVO.setAffiliationId(dateList[3]);
		
		laterSettleService.deleteFoodList(foodExpensesVO);

	}
	
	/*주차비 리스트 조회*/
	@RequestMapping(value = "/com/financial/laterSettle/parkingExpense.do")
	public String parkingExpense(@ModelAttribute("ParkingVO") ParkingVO parkingVO, Model model,
								@RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		
		LaterSettleUserVO laterSettleUserVO = new LaterSettleUserVO();
		laterSettleUserVO.setAffiliationId(affiliationId);
		List<?> userList = laterSettleService.selectUserList(laterSettleUserVO);
		model.addAttribute("userList", userList);
		
		List<?> parkingExpenseList = laterSettleService.selectParkingExpenseList(parkingVO);
		model.addAttribute("parkingList", parkingExpenseList);

		if(affiliationId.equals("I")){
			return "tis/com/financial/laterSettle/parkingExpenseIeetu";}
		else{
			return "tis/com/financial/laterSettle/parkingExpenseSmaker";}	
	}
	
	/*주차비관리 사원추가*/
	@RequestMapping(value = "/com/financial/laterSettle/addparkingExpense.do")
	public String addparkingExpense(@ModelAttribute("ParkingVO") ParkingVO parkingVO, Model model, HttpServletRequest request) throws Exception {
		
		laterSettleService.insertEmplyrToParkingList(parkingVO);
		
		String url = parkingVO.getAffiliationId();

		return "redirect:/com/financial/laterSettle/parkingExpense.do?affiliationId="+url;
	}
	
	/*주차비관리 사원중복확인*/
	@RequestMapping(value = "/com/financial/laterSettle/chekUserDuplParking.do")
	public @ResponseBody String chekUserDuplParking(ModelMap model,
											@RequestParam("carNumb") String carNumb,
											@RequestParam("emplNm") String emplNm,
											@RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		
		ParkingVO parkingVO = new ParkingVO();
		
		parkingVO.setCarNumb(carNumb);
		parkingVO.setEmplNm(emplNm);
		parkingVO.setAffiliationId(affiliationId);
		
		return new Gson().toJson( laterSettleService.checkEmplyrUserParking(parkingVO));
	}
	
	/*주차비관리 업데이트*/
	@RequestMapping(value = "/com/financial/laterSettle/updtParkingList.do")
	public void updtParkingList(@RequestParam("jsonData") String jsonData, @RequestParam("affiliationId") String affiliationId,
								HttpServletRequest request ) throws Exception {
		
		JsonArray entries = (JsonArray) new JsonParser().parse(jsonData);
		for(int i=0; i < entries.size() ; i++){
			int cost = 0;
			
		    JsonElement jsonEmplNm = ((JsonObject)entries.get(i)).get("emplNm");
		    JsonElement jsonCarNumb = ((JsonObject)entries.get(i)).get("carNumb");
		    JsonElement jsonExtensionDt = ((JsonObject)entries.get(i)).get("extensionDt");
		    JsonElement jsonCarDiv = ((JsonObject)entries.get(i)).get("carDiv");
		    JsonElement jsonCost = ((JsonObject)entries.get(i)).get("cost");
		    JsonElement jsonPaymentDt = ((JsonObject)entries.get(i)).get("paymentDt");	
		    JsonElement jsonExpirationDt = ((JsonObject)entries.get(i)).get("expirationDt");	
		    JsonElement jsonRm = ((JsonObject)entries.get(i)).get("rm");	

			String emplNm = jsonEmplNm.toString();
			String carNumb = jsonCarNumb.toString();
			String extensionDt = jsonExtensionDt.toString();
			String carDiv = jsonCarDiv.toString();

			String stringCost = jsonCost.toString().replaceAll("\"", "");
			if(!stringCost.equals("")){
				cost = Integer.parseInt(stringCost);
			}			
			String paymentDt = jsonPaymentDt.toString();
			String expirationDt = jsonExpirationDt.toString();
			String rm = jsonRm.toString();


			ParkingVO parkingVO = new ParkingVO();
			
			parkingVO.setEmplNm(emplNm.replaceAll("\"", ""));
			parkingVO.setCarNumb(carNumb.replaceAll("\"", ""));
			parkingVO.setExtensionDt(extensionDt.replaceAll("\"", ""));
			parkingVO.setCarDiv(carDiv.replaceAll("\"", ""));
			parkingVO.setCost(cost);
			parkingVO.setPaymentDt(paymentDt.replaceAll("\"", ""));
			parkingVO.setExpirationDt(expirationDt.replaceAll("\"", ""));
			parkingVO.setRm(rm.replaceAll("\"", ""));
			parkingVO.setAffiliationId(affiliationId);

			laterSettleService.updateparkingList(parkingVO);
		}
	}
	
	/*주차비 리스트 삭제*/
	@RequestMapping(value = "/com/financial/laterSettle/parkingExpenseDelete.do")
	public void parkingExpenseDelete( @RequestParam("data") String data, HttpServletRequest request) throws Exception {
		String[] dateList = data.split(",");
		ParkingVO parkingVO = new ParkingVO();

		parkingVO.setCarNumb(dateList[0]);
		parkingVO.setEmplNm(dateList[1]);
		parkingVO.setAffiliationId(dateList[2]);
		
		laterSettleService.deleteParkingList(parkingVO);

	}
	
	/*푸드엑셀 다운로드*/
	@RequestMapping(value = "/com/financial/laterSettle/downloadExcel.do")
	public ModelAndView downloadDetailEmplExcel(ModelMap model,
			@RequestParam("affiliationId") String affiliationId,
			@RequestParam("foodDt") String foodDt,
			@RequestParam("emplNo") String emplNo, HttpServletRequest request ) throws Exception{
		
		return new ModelAndView("laterSettleFoodDownload", "excel", emplNo+","+affiliationId+","+foodDt);
	}
	
	/*주차엑셀 다운로드*/
	@RequestMapping(value = "/com/financial/laterSettle/parkingDownloadExcel.do")
	public ModelAndView parkingDownloadExcel(ModelMap model,
			@RequestParam("affiliationId") String affiliationId,
			@RequestParam("carNumb") String carNumb, HttpServletRequest request) throws Exception{
		
		return new ModelAndView("laterSettleParkingDownload", "excel", carNumb+","+affiliationId);
	}
}