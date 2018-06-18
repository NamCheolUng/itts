package tis.com.financial.laterSettle.service;

import java.util.List;

public interface LaterSettleService {
	
	/*사원별 식비리스트 조회*/
	List<?> selectFoodExpensesList(FoodExpensesVO foodExpensesVO) throws Exception;

	/*사원별 식비리스트 조회(엑셀다운)*/
	List<?> selectFoodExpensesListExcel(FoodExpensesVO foodExpensesVO) throws Exception;
	
	/*사원별 주차비리스트 조회(엑셀다운)*/
	List<?> selectParkingExpensesListExcel(ParkingVO parkingVO) throws Exception;
	
	/*유저검색*/
	List<?> selectUserList(LaterSettleUserVO laterSettleUserVO) throws Exception;
	
	/*중식비리스트에 사원 등록*/
	public void insertEmplyrToFoodList(FoodExpensesVO foodExpensesVO) throws Exception;
	
	/*중식비관리 업데이트*/
	public void updateFoodList(FoodExpensesVO foodExpensesVO) throws Exception;
	
	/*중식비관리 삭제*/
	public void deleteFoodList(FoodExpensesVO foodExpensesVO) throws Exception;
	
	/*중식비관리 중복유저 체크*/
	public int checkEmplyrUser(FoodExpensesVO foodExpensesVO) throws Exception;
	
	/*등록된 연도 가져오기*/
	List<?> selectyearList(FoodExpensesVO foodExpensesVO) throws Exception;
	
	/*사원별 주차정보 조회*/
	List<?> selectParkingExpenseList(ParkingVO parkingVO) throws Exception;
	
	/*주차비리스트에 사원 등록*/
	public void insertEmplyrToParkingList(ParkingVO parkingVO) throws Exception;
	
	/*주차비관리 중복유저 체크*/
	public int checkEmplyrUserParking(ParkingVO parkingVO) throws Exception;
	
	/*주차비관리 업데이트*/
	public void updateparkingList(ParkingVO parkingVO) throws Exception;
	
	/*주차비관리 삭제*/
	public void deleteParkingList(ParkingVO parkingVO) throws Exception;
	
}
