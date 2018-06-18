package tis.com.business.item.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import tis.com.business.item.service.TbItemmanageService;
import tis.com.business.item.service.TbItemmanageDefaultVO;
import tis.com.business.item.service.TbItemmanageVO;
import tis.com.business.item.service.impl.TbItemmanageDAO;

/**
 * @Class Name : TbItemmanageServiceImpl.java
 * @Description : TbItemmanage Business Implement class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180130
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Service("tbItemmanageService")
public class TbItemmanageServiceImpl extends EgovAbstractServiceImpl implements
        TbItemmanageService {
        
    private static final Logger LOGGER = LoggerFactory.getLogger(TbItemmanageServiceImpl.class);

    @Resource(name="tbItemmanageDAO")
    private TbItemmanageDAO tbItemmanageDAO;
    
    /** ID Generation */
    //@Resource(name="{egovTbItemmanageIdGnrService}")    
    //private EgovIdGnrService egovIdGnrService;

	/**
	 * tb_itemmanage을 등록한다.
	 * @param vo - 등록할 정보가 담긴 TbItemmanageVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertTbItemmanage(TbItemmanageVO vo) throws Exception {
    	LOGGER.debug(vo.toString());
    	
    	/** ID Generation Service */
    	//TODO 해당 테이블 속성에 따라 ID 제너레이션 서비스 사용
    	//String id = egovIdGnrService.getNextStringId();
    	//vo.setId(id);
    	LOGGER.debug(vo.toString());
    	
    	tbItemmanageDAO.insertTbItemmanage(vo);
    	//TODO 해당 테이블 정보에 맞게 수정    	
        return null;
    }

    /**
	 * tb_itemmanage을 수정한다.
	 * @param vo - 수정할 정보가 담긴 TbItemmanageVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateTbItemmanage(TbItemmanageVO vo) throws Exception {
        tbItemmanageDAO.updateTbItemmanage(vo);
    }

    /**
	 * tb_itemmanage을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 TbItemmanageVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteTbItemmanage(TbItemmanageVO vo) throws Exception {
        tbItemmanageDAO.deleteTbItemmanage(vo);
    }

    /**
	 * tb_itemmanage을 조회한다.
	 * @param vo - 조회할 정보가 담긴 TbItemmanageVO
	 * @return 조회한 tb_itemmanage
	 * @exception Exception
	 */
    public TbItemmanageVO selectTbItemmanage(TbItemmanageVO vo) throws Exception {
        TbItemmanageVO resultVO = tbItemmanageDAO.selectTbItemmanage(vo);
        if (resultVO == null)
            throw processException("info.nodata.msg");
        return resultVO;
    }

    /**
	 * tb_itemmanage 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_itemmanage 목록
	 * @exception Exception
	 */
    public List<?> selectTbItemmanageList(TbItemmanageDefaultVO searchVO) throws Exception {
        return tbItemmanageDAO.selectTbItemmanageList(searchVO);
    }
    
    public List<?> selectTbItemNameList() throws Exception {
        return tbItemmanageDAO.selectTbItemNameList();
    }

    
    /**
	 * tb_itemmanage 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_itemmanage 총 갯수
	 * @exception
	 */
    public int selectTbItemmanageListTotCnt(TbItemmanageDefaultVO searchVO) {
		return tbItemmanageDAO.selectTbItemmanageListTotCnt(searchVO);
	}
    
    public String selectGetItemID() {
		return tbItemmanageDAO.selectGetItemID();
	}
    
}
