package tis.com.financial.laterSettle.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.financial.laterSettle.service.FoodExpensesVO;
import tis.com.financial.laterSettle.service.LaterSettleUserVO;
import tis.com.financial.laterSettle.service.ParkingVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("laterSettleDAO")
public class LaterSettleDAO extends EgovComAbstractDAO{
	
	public List<?> selectFoodExpensesList(FoodExpensesVO foodExpensesVO) throws Exception{
		return list("laterSettleDAO.selectFoodExpensesList", foodExpensesVO);
	}
	
	public List<?> selectFoodExpensesListExcel(FoodExpensesVO foodExpensesVO) throws Exception{
		return list("laterSettleDAO.selectFoodExpensesListExcel", foodExpensesVO);
	}
	
	public List<?> selectParkingExpensesListExcel(ParkingVO parkingVO) throws Exception{
		return list("laterSettleDAO.selectParkingExpensesListExcel", parkingVO);
	}
	
	public List<?> selectUserList(LaterSettleUserVO laterSettleUserVO) throws Exception{
		return list("laterSettleDAO.selectUserList", laterSettleUserVO);
	}

    public void insertEmplyrToFoodList(FoodExpensesVO foodExpensesVO){
         insert("laterSettleDAO.insertEmplyrToFoodList", foodExpensesVO);
    }
    
    public void updateFoodList(FoodExpensesVO foodExpensesVO){
        update("laterSettleDAO.updateFoodList", foodExpensesVO);
    }
    
    public void deleteFoodList(FoodExpensesVO foodExpensesVO){
        delete("laterSettleDAO.deleteFoodList", foodExpensesVO);
    }
  
	public int checkEmplyrUser(FoodExpensesVO foodExpensesVO) throws Exception{
		return (int)select("laterSettleDAO.checkEmplyrUser", foodExpensesVO);
	}
	
	public List<?> selectyearList(FoodExpensesVO foodExpensesVO) throws Exception{
		return list("laterSettleDAO.selectyearList", foodExpensesVO);
	}
	
	public List<?> selectParkingExpenseList(ParkingVO parkingVO) throws Exception{
		return list("laterSettleDAO.selectParkingExpenseList", parkingVO);
	}
	
    public void insertEmplyrToParkingList(ParkingVO parkingVO){
        insert("laterSettleDAO.insertEmplyrToParkingList", parkingVO);
    }
    
	public int checkEmplyrUserParking(ParkingVO parkingVO) throws Exception{
		return (int)select("laterSettleDAO.checkEmplyrUserParking", parkingVO);
	}
	
    public void updateparkingList(ParkingVO parkingVO){
        update("laterSettleDAO.updateparkingList", parkingVO);
    }
    
    public void deleteParkingList(ParkingVO parkingVO){
        delete("laterSettleDAO.deleteParkingList", parkingVO);
    }
}