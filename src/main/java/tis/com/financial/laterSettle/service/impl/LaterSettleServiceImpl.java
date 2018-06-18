package tis.com.financial.laterSettle.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tis.com.financial.laterSettle.service.FoodExpensesVO;
import tis.com.financial.laterSettle.service.LaterSettleService;
import tis.com.financial.laterSettle.service.LaterSettleUserVO;
import tis.com.financial.laterSettle.service.ParkingVO;

@Service("laterSettleService")
public class LaterSettleServiceImpl implements LaterSettleService{

	@Resource(name="laterSettleDAO")
	private LaterSettleDAO laterSettleDAO;

	@Override
	public List<?> selectFoodExpensesList(FoodExpensesVO foodExpensesVO) throws Exception{
		return laterSettleDAO.selectFoodExpensesList(foodExpensesVO);
	}
	
	@Override
	public List<?> selectFoodExpensesListExcel(FoodExpensesVO foodExpensesVO) throws Exception{
		return laterSettleDAO.selectFoodExpensesListExcel(foodExpensesVO);
	}
	
	@Override
	public List<?> selectParkingExpensesListExcel(ParkingVO parkingVO) throws Exception{
		return laterSettleDAO.selectParkingExpensesListExcel(parkingVO);
	}
	
	@Override
	public List<?> selectUserList(LaterSettleUserVO laterSettleUserVO) throws Exception{
		return laterSettleDAO.selectUserList(laterSettleUserVO);
	}
	
	public void insertEmplyrToFoodList(FoodExpensesVO foodExpensesVO) throws Exception {
			laterSettleDAO.insertEmplyrToFoodList(foodExpensesVO);
	}

	public void updateFoodList(FoodExpensesVO foodExpensesVO) throws Exception {
		laterSettleDAO.updateFoodList(foodExpensesVO);
	}

	public void deleteFoodList(FoodExpensesVO foodExpensesVO) throws Exception {
		laterSettleDAO.deleteFoodList(foodExpensesVO);
	}
	
	@Override
	public int checkEmplyrUser(FoodExpensesVO foodExpensesVO) throws Exception{
		return laterSettleDAO.checkEmplyrUser(foodExpensesVO);
	}
	
	@Override
	public List<?> selectyearList(FoodExpensesVO foodExpensesVO) throws Exception{
		return laterSettleDAO.selectyearList(foodExpensesVO);
	}
	
	@Override
	public List<?> selectParkingExpenseList(ParkingVO parkingVO) throws Exception{
		return laterSettleDAO.selectParkingExpenseList(parkingVO);
	}
	
	public void insertEmplyrToParkingList(ParkingVO parkingVO) throws Exception {
		laterSettleDAO.insertEmplyrToParkingList(parkingVO);
	}
	
	@Override
	public int checkEmplyrUserParking(ParkingVO parkingVO) throws Exception{
		return laterSettleDAO.checkEmplyrUserParking(parkingVO);
	}
	
	public void updateparkingList(ParkingVO parkingVO) throws Exception {
		laterSettleDAO.updateparkingList(parkingVO);
	}
	
	public void deleteParkingList(ParkingVO parkingVO) throws Exception {
		laterSettleDAO.deleteParkingList(parkingVO);
	}
}