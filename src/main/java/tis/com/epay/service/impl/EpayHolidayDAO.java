package tis.com.epay.service.impl;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayCnsulVO;
import tis.com.epay.service.EpayHolidayVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("EpayHolidayDAO")
public class EpayHolidayDAO extends EgovComAbstractDAO{
	
	public EgovMap selectEpayHolidayWithDInfo(EpayHolidayVO epayHolidayVO) throws Exception{
		return (EgovMap) select("EpayHolidayDAO.selectEpayHolidayWithDInfo", epayHolidayVO);
	}
	
	public EgovMap selectEpayHoliday(EpayHolidayVO epayHolidayVO) throws Exception{
		return (EgovMap) select("EpayHolidayDAO.selectEpayHoliday", epayHolidayVO);
	}
	
	public Object insertEpayHoliday(EpayHolidayVO epayHolidayVO) throws Exception{
		return insert("EpayHolidayDAO.insertEpayHoliday", epayHolidayVO);
	}
	
	public void updateEpayHoliday(EpayHolidayVO epayHolidayVO) throws Exception{
		 update("EpayHolidayDAO.updateEpayHoliday", epayHolidayVO);
	}
	
	public void deleteEpayHoliday(EpayHolidayVO epayHolidayVO) throws Exception{
		delete("EpayHolidayDAO.deleteEpayHoliday", epayHolidayVO);
	}
	
	public void insertEpayManagerHoliday(EpayHolidayVO epayHolidayVO) throws Exception{
		insert("EpayHolidayDAO.insertEpayManagerHoliday", epayHolidayVO);
	}
}
