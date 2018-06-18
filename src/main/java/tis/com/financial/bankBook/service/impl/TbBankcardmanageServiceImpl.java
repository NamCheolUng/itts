package tis.com.financial.bankBook.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tis.com.financial.bankBook.service.TbBankcardmanageService;
import tis.com.financial.bankBook.service.TbBankcardmanageVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : TbBankcardmanageServiceImpl.java
 * @Description : TbBankcardmanage Business Implement class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180126
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Service("tbBankcardmanageService")
public class TbBankcardmanageServiceImpl extends EgovAbstractServiceImpl implements
        TbBankcardmanageService {
        
    private static final Logger LOGGER = LoggerFactory.getLogger(TbBankcardmanageServiceImpl.class);

    @Resource(name="tbBankcardmanageDAO")
    private TbBankcardmanageDAO tbBankcardmanageDAO;
    
    /** ID Generation */
    //@Resource(name="{egovTbBankcardmanageIdGnrService}")    
    //private EgovIdGnrService egovIdGnrService;

	/**
	 * tb_bankcardmanage을 등록한다.
	 * @param vo - 등록할 정보가 담긴 TbBankcardmanageVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertTbBankcardmanage(TbBankcardmanageVO vo) throws Exception {
    	LOGGER.debug(vo.toString());
    	
    	/** ID Generation Service */
    	//TODO 해당 테이블 속성에 따라 ID 제너레이션 서비스 사용
    	//String id = egovIdGnrService.getNextStringId();
    	//vo.setId(id);
    	LOGGER.debug(vo.toString());
    	
    	tbBankcardmanageDAO.insertTbBankcardmanage(vo);
    	//TODO 해당 테이블 정보에 맞게 수정    	
        return null;
    }

    /**
	 * tb_bankcardmanage을 수정한다.
	 * @param vo - 수정할 정보가 담긴 TbBankcardmanageVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateTbBankcardmanage(TbBankcardmanageVO vo) throws Exception {
        tbBankcardmanageDAO.updateTbBankcardmanage(vo);
    }

    /**
	 * tb_bankcardmanage을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 TbBankcardmanageVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteTbBankcardmanage(TbBankcardmanageVO vo) throws Exception {
        tbBankcardmanageDAO.deleteTbBankcardmanage(vo);
    }

    /**
	 * tb_bankcardmanage을 조회한다.
	 * @param vo - 조회할 정보가 담긴 TbBankcardmanageVO
	 * @return 조회한 tb_bankcardmanage
	 * @exception Exception
	 */
    public TbBankcardmanageVO selectTbBankcardmanage(TbBankcardmanageVO vo) throws Exception {
        TbBankcardmanageVO resultVO = tbBankcardmanageDAO.selectTbBankcardmanage(vo);
        if (resultVO == null)
            throw processException("info.nodata.msg");
        return resultVO;
    }

    /**
	 * tb_bankcardmanage 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_bankcardmanage 목록
	 * @exception Exception
	 */
    public List<?> selectTbBankcardmanageList(TbBankcardmanageVO searchVO) throws Exception {
        return tbBankcardmanageDAO.selectTbBankcardmanageList(searchVO);
    }

    /**
	 * tb_bankcardmanage 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_bankcardmanage 총 갯수
	 * @exception
	 */
    public int selectTbBankcardmanageListTotCnt(TbBankcardmanageVO searchVO) {
		return tbBankcardmanageDAO.selectTbBankcardmanageListTotCnt(searchVO);
	}
    
    public List<?> selectEmplyrList() throws Exception {
        return tbBankcardmanageDAO.selectEmplyrList();
    }

}
