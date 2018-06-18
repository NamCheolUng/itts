package tis.com.myPage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import tis.com.common.service.DefaultVO;
import tis.com.myPage.service.MyPageService;

@Service("myPageService")
public class MyPageServiceImpl implements MyPageService{

	@Resource(name="myPageDAO")
	private MyPageDAO myPageDAO;

	@Override
	public List<?> selectMyPageScheduler(DefaultVO defaultVO) throws Exception {
		// TODO Auto-generated method stub
		return myPageDAO.selectMyPageScheduler(defaultVO);
	}

	@Override
	public List<?> selectMySchduleList(DefaultVO defaultVO) throws Exception {
		// TODO Auto-generated method stub
		return myPageDAO.selectMySchduleList(defaultVO);
	}

	@Override
	public List<?> selectSchdulList(DefaultVO defaultVO) throws Exception {
		// TODO Auto-generated method stub
		return myPageDAO.selectSchdulList(defaultVO);
	}

	@Override
	public EgovMap selectPrgrsAllCnt(DefaultVO defaultVO) throws Exception {
		// TODO Auto-generated method stub
		return myPageDAO.selectPrgrsAllCnt(defaultVO);
	}
}
