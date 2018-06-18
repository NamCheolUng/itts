package tis.com.financial.bankBook.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tis.com.financial.bankBook.service.TbBankbookmanageService;
import tis.com.financial.bankBook.service.TbBankbookmanageVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : TbBankbookmanageServiceImpl.java
 * @Description : TbBankbookmanage Business Implement class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180125
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Service("tbBankbookmanageService")
public class TbBankbookmanageServiceImpl extends EgovAbstractServiceImpl implements
        TbBankbookmanageService {
        
    private static final Logger LOGGER = LoggerFactory.getLogger(TbBankbookmanageServiceImpl.class);

    @Resource(name="tbBankbookmanageDAO")
    private TbBankbookmanageDAO tbBankbookmanageDAO;
    
    /** ID Generation */
    //@Resource(name="{egovTbBankbookmanageIdGnrService}")    
    //private EgovIdGnrService egovIdGnrService;

	/**
	 * tb_bankbookmanage을 등록한다.
	 * @param vo - 등록할 정보가 담긴 TbBankbookmanageVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertTbBankbookmanage(TbBankbookmanageVO vo) throws Exception {
    	LOGGER.debug(vo.toString());
    	
    	/** ID Generation Service */
    	//TODO 해당 테이블 속성에 따라 ID 제너레이션 서비스 사용
    	//String id = egovIdGnrService.getNextStringId();
    	//vo.setId(id);
    	LOGGER.debug(vo.toString());
    	
    	tbBankbookmanageDAO.insertTbBankbookmanage(vo);
    	//TODO 해당 테이블 정보에 맞게 수정    	
        return null;
    }

    /**
	 * tb_bankbookmanage을 수정한다.
	 * @param vo - 수정할 정보가 담긴 TbBankbookmanageVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateTbBankbookmanage(TbBankbookmanageVO vo) throws Exception {
        tbBankbookmanageDAO.updateTbBankbookmanage(vo);
    }

    /**
	 * tb_bankbookmanage을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 TbBankbookmanageVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteTbBankbookmanage(TbBankbookmanageVO vo) throws Exception {
        tbBankbookmanageDAO.deleteTbBankbookmanage(vo);
    }

    /**
	 * tb_bankbookmanage을 조회한다.
	 * @param vo - 조회할 정보가 담긴 TbBankbookmanageVO
	 * @return 조회한 tb_bankbookmanage
	 * @exception Exception
	 */
    public TbBankbookmanageVO selectTbBankbookmanage(TbBankbookmanageVO vo) throws Exception {
        TbBankbookmanageVO resultVO = tbBankbookmanageDAO.selectTbBankbookmanage(vo);
        if (resultVO == null)
            throw processException("info.nodata.msg");
        return resultVO;
    }

    /**
	 * tb_bankbookmanage 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_bankbookmanage 목록
	 * @exception Exception
	 */
    public List<?> selectTbBankbookmanageList(TbBankbookmanageVO searchVO) throws Exception {
        return tbBankbookmanageDAO.selectTbBankbookmanageList(searchVO);
    }

    /**
	 * tb_bankbookmanage 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_bankbookmanage 총 갯수
	 * @exception
	 */
    public int selectTbBankbookmanageListTotCnt(TbBankbookmanageVO searchVO) {
		return tbBankbookmanageDAO.selectTbBankbookmanageListTotCnt(searchVO);
	}
 
    public List<?> selectTbBankbookUseList() throws Exception {
        return tbBankbookmanageDAO.selectTbBankbookUseList();
    }

	@Override
	public int selectTbBankbookmanageByCpCode(String cpCode) throws Exception {
		return tbBankbookmanageDAO.selectTbBankbookmanageByCpCode(cpCode);
	}

}
