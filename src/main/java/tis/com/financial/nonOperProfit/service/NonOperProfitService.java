package tis.com.financial.nonOperProfit.service;

import java.util.List;

public interface NonOperProfitService {
	/*조회*/
	List<?> loadData(NonOperVO nonOperVO)throws Exception;
	
	/*수정*/
	public void updateData(NonOperVO nonOperVO) throws Exception;
	
	/*추가*/
	public void insertData(NonOperVO nonOperVO) throws Exception;
	
	/*삭제*/
	public void deleteData(NonOperVO nonOperVO) throws Exception;
	
	/*기본값 추가*/
	public void addDefauitData(NonOperVO nonOperVO) throws Exception;
}
