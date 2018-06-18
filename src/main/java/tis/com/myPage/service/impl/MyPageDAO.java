package tis.com.myPage.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.common.service.DefaultVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("myPageDAO")
public class MyPageDAO extends EgovComAbstractDAO{

	public List<?> selectMyPageScheduler(DefaultVO defaultVO) throws Exception{
		return list("myPageDAO.selectMyPageScheduler",defaultVO);
	}
	
	public List<?> selectMySchduleList(DefaultVO defaultVO) throws Exception{
		return list("myPageDAO.selectMySchduleList",defaultVO);
	}
	
	public List<?> selectSchdulList(DefaultVO defaultVO) throws Exception{
		return list("myPageDAO.selectSchdulList",defaultVO);
	}
	
	public EgovMap selectPrgrsAllCnt(DefaultVO defaultVO) throws Exception{
		return (EgovMap) select("myPageDAO.selectPrgrsAllCnt",defaultVO);
	}
	
}
