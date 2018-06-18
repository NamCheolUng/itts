package tis.com.manager.managerHoliday.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tis.com.manager.managerHoliday.service.TbAllvcatnmanageService;
import tis.com.manager.managerHoliday.service.TbAllvcatnmanageVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.vct.service.IndvdlYrycManage;
import egovframework.com.uss.ion.vct.service.VcatnManageVO;
import egovframework.com.uss.ion.vct.service.impl.VcatnManageDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : TbAllvcatnmanageServiceImpl.java
 * @Description : TbAllvcatnmanage Business Implement class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180214
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Service("tbAllvcatnmanageService")
public class TbAllvcatnmanageServiceImpl extends EgovAbstractServiceImpl implements
        TbAllvcatnmanageService {
        
    private static final Logger LOGGER = LoggerFactory.getLogger(TbAllvcatnmanageServiceImpl.class);

    @Resource(name="tbAllvcatnmanageDAO")
    private TbAllvcatnmanageDAO tbAllvcatnmanageDAO;
    
    @Resource(name="vcatnManageDAO")
    private VcatnManageDAO vcatnManageDAO;
    
    /** ID Generation */
    //@Resource(name="{egovTbAllvcatnmanageIdGnrService}")    
    //private EgovIdGnrService egovIdGnrService;

	/**
	 * tb_allvcatnmanage을 등록한다.
	 * @param vo - 등록할 정보가 담긴 TbAllvcatnmanageVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertTbAllvcatnmanage(TbAllvcatnmanageVO vo) throws Exception {
    	LOGGER.debug(vo.toString());
    	
    	/** ID Generation Service */
    	//TODO 해당 테이블 속성에 따라 ID 제너레이션 서비스 사용
    	//String id = egovIdGnrService.getNextStringId();
    	//vo.setId(id);
    	LOGGER.debug(vo.toString());
    	
    	tbAllvcatnmanageDAO.insertTbAllvcatnmanage(vo);
    	//TODO 해당 테이블 정보에 맞게 수정    	
        return null;
    }

    /**
	 * tb_allvcatnmanage을 수정한다.
	 * @param vo - 수정할 정보가 담긴 TbAllvcatnmanageVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateTbAllvcatnmanage(TbAllvcatnmanageVO vo) throws Exception {
        tbAllvcatnmanageDAO.updateTbAllvcatnmanage(vo);
    }

    /**
	 * tb_allvcatnmanage을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 TbAllvcatnmanageVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteTbAllvcatnmanage(TbAllvcatnmanageVO vo) throws Exception {
    	LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	vo = tbAllvcatnmanageDAO.selectTbAllvcatnmanage(vo);
    	
    	List<VcatnManageVO> vcaList = vcatnManageDAO.selectVcatnDateList(vo);   
    	
    	for(int i = 0; i < vcaList.size(); i++ ){
    		VcatnManageVO vcatnManageVO = vcaList.get(i);
    		IndvdlYrycManage tmpVO = new IndvdlYrycManage();
    		tmpVO.setUsid(vcatnManageVO.getApplcntId());
    		tmpVO.setOccrrncYear(vcatnManageVO.getOccrrncYear());
    		tmpVO.setUseYrycCo(vo.getVcatnCnt().doubleValue());
    		tmpVO.setRemndrYrycCo(vo.getVcatnCnt().doubleValue());
    		tmpVO.setLastUpdusrId(user.getUniqId());
    		vcatnManageDAO.updateIndvdlYrycDeleteManage(tmpVO);
    		vcatnManageDAO.deleteVcatnManage(vcatnManageVO);
    	}    	
        tbAllvcatnmanageDAO.deleteTbAllvcatnmanage(vo);
    }

    /**
	 * tb_allvcatnmanage을 조회한다.
	 * @param vo - 조회할 정보가 담긴 TbAllvcatnmanageVO
	 * @return 조회한 tb_allvcatnmanage
	 * @exception Exception
	 */
    public TbAllvcatnmanageVO selectTbAllvcatnmanage(TbAllvcatnmanageVO vo) throws Exception {
        TbAllvcatnmanageVO resultVO = tbAllvcatnmanageDAO.selectTbAllvcatnmanage(vo);
        if (resultVO == null)
            throw processException("info.nodata.msg");
        return resultVO;
    }

    /**
	 * tb_allvcatnmanage 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_allvcatnmanage 목록
	 * @exception Exception
	 */
    public List<?> selectTbAllvcatnmanageList(TbAllvcatnmanageVO vo) throws Exception {
        return tbAllvcatnmanageDAO.selectTbAllvcatnmanageList(vo);
    }

    /**
	 * tb_allvcatnmanage 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_allvcatnmanage 총 갯수
	 * @exception
	 */
    public int selectTbAllvcatnmanageListTotCnt(TbAllvcatnmanageVO vo) {
		return tbAllvcatnmanageDAO.selectTbAllvcatnmanageListTotCnt(vo);
	}
    
    public List<?> selectTbAllvcatnmanageYearList() throws Exception {
        return tbAllvcatnmanageDAO.selectTbAllvcatnmanageYearList();
    }

	@Override
	public List<?> selectTbAllvcatnmanageListForMonth(TbAllvcatnmanageVO vo) throws Exception {
		return tbAllvcatnmanageDAO.selectTbAllvcatnmanageListForMonth(vo);
	}
    
}
