package tis.com.financial.periodicSettle.service;

import java.util.List;

public interface PeriodicSettleService {
	/*지출조회*/
	List<?> loadData_E(PeriodicSettleVO periodicSettleVO)throws Exception;
	
	/*수입조회*/
	List<?> loadData_P(PeriodicSettleVO periodicSettleVO)throws Exception;
	
	/*수정*/
	public void updateData(PeriodicSettleVO periodicSettleVO) throws Exception;
	
	/*추가*/
	public void insertData(PeriodicSettleVO periodicSettleVO) throws Exception;
	
	/*삭제*/
	public void deleteData(PeriodicSettleVO periodicSettleVO) throws Exception;
	
	/*기본값 추가*/
	public void addDefauitData(PeriodicSettleVO periodicSettleVO) throws Exception;
	
}
