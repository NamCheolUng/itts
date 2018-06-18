package tis.com.common.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.common.service.TisCodeVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;


@Repository("TisCodeDAO")
public class TisCodeDAO extends EgovComAbstractDAO {

	public List<?> selectCodeDetailList(String codeId) throws Exception{
		return list("tisCodeDAO.selectCodeDetailList", codeId);
	}

	public List<?> selectCodeDetailByVOList(TisCodeVO vo) throws Exception{
		return list("tisCodeDAO.selectCodeDetailByVOList", vo);
	}
}
