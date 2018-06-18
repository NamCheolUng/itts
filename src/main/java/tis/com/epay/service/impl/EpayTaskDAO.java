package tis.com.epay.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayExpHistVO;
import tis.com.epay.service.EpayTaskPersonVO;
import tis.com.task.service.TaskVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("EpayTaskDAO")
public class EpayTaskDAO extends EgovComAbstractDAO{
	
	public List<?> selectEpayTaskList(EpayTaskPersonVO epayTaskPersonVO) throws Exception{
		return list("EpayTaskDAO.selectEpayTaskList", epayTaskPersonVO);
	}
	
	public List<?> selectEpayTaskAllList() throws Exception{
		return list("EpayTaskDAO.selectEpayTaskAllList");
	}
	
	// 선택한 과제 참여자에 해당되는 과제 목록을 조회함.
	public EgovMap selectEpayTaskForPerson(String emplNo) throws Exception{
		
		EgovMap returnMap = null;
		List<?> result = list("EpayTaskDAO.selectEpayTaskForPerson", emplNo);
		if(result.size() > 0){
			returnMap = (EgovMap) result.get(0);
		}
		return returnMap;
	}
}
