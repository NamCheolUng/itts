package tis.com.financial.deposit.service;

import java.util.List;

public interface DepositService {
	/*조회*/
	List<?> loadData(DepositVO depositVO)throws Exception;
	
	/*수정*/
	public void updateData(DepositVO depositVO) throws Exception;
	
	/*추가*/
	public void insertData(DepositVO depositVO) throws Exception;
	
	/*삭제*/
	public void deleteData(DepositVO depositVO) throws Exception;
	
	/*추가*/
	public void addDefauitData(DepositVO depositVO) throws Exception;
}
