package tis.com.manager.personnelAppointment.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tis.com.manager.personnelAppointment.service.ComthemplyrinfochangedtlsService;
import tis.com.manager.personnelAppointment.service.ComthemplyrinfochangedtlsVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : ComthemplyrinfochangedtlsServiceImpl.java
 * @Description : Comthemplyrinfochangedtls Business Implement class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180123
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Service("comthemplyrinfochangedtlsService")
public class ComthemplyrinfochangedtlsServiceImpl extends EgovAbstractServiceImpl implements
        ComthemplyrinfochangedtlsService {
        
    private static final Logger LOGGER = LoggerFactory.getLogger(ComthemplyrinfochangedtlsServiceImpl.class);

    @Resource(name="comthemplyrinfochangedtlsDAO")
    private ComthemplyrinfochangedtlsDAO comthemplyrinfochangedtlsDAO;
    
    /** ID Generation */
    //@Resource(name="{egovComthemplyrinfochangedtlsIdGnrService}")    
    //private EgovIdGnrService egovIdGnrService;

	/**
	 * comthemplyrinfochangedtls을 등록한다.
	 * @param vo - 등록할 정보가 담긴 ComthemplyrinfochangedtlsVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertComthemplyrinfochangedtls(ComthemplyrinfochangedtlsVO vo) throws Exception {
    	LOGGER.debug(vo.toString());
    	
    	/** ID Generation Service */
    	//TODO 해당 테이블 속성에 따라 ID 제너레이션 서비스 사용
    	//String id = egovIdGnrService.getNextStringId();
    	//vo.setId(id);
    	LOGGER.debug(vo.toString());
    	
    	comthemplyrinfochangedtlsDAO.insertComthemplyrinfochangedtls(vo);
    	//TODO 해당 테이블 정보에 맞게 수정    	
        return null;
    }

    /**
	 * comthemplyrinfochangedtls을 수정한다.
	 * @param vo - 수정할 정보가 담긴 ComthemplyrinfochangedtlsVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateComthemplyrinfochangedtls(ComthemplyrinfochangedtlsVO vo) throws Exception {
        comthemplyrinfochangedtlsDAO.updateComthemplyrinfochangedtls(vo);
    }

    public void updateAppointmentChange(ComthemplyrinfochangedtlsVO vo) throws Exception {
        comthemplyrinfochangedtlsDAO.updateAppointmentChange(vo);
    }
    
    /**
	 * comthemplyrinfochangedtls을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 ComthemplyrinfochangedtlsVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteComthemplyrinfochangedtls(ComthemplyrinfochangedtlsVO vo) throws Exception {
        comthemplyrinfochangedtlsDAO.deleteComthemplyrinfochangedtls(vo);
    }

    /**
	 * comthemplyrinfochangedtls을 조회한다.
	 * @param vo - 조회할 정보가 담긴 ComthemplyrinfochangedtlsVO
	 * @return 조회한 comthemplyrinfochangedtls
	 * @exception Exception
	 */
    public ComthemplyrinfochangedtlsVO selectComthemplyrinfochangedtls(ComthemplyrinfochangedtlsVO vo) throws Exception {
        ComthemplyrinfochangedtlsVO resultVO = comthemplyrinfochangedtlsDAO.selectComthemplyrinfochangedtls(vo);
        if (resultVO == null)
            throw processException("info.nodata.msg");
        return resultVO;
    }

    /**
	 * comthemplyrinfochangedtls 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return comthemplyrinfochangedtls 목록
	 * @exception Exception
	 */
    public List<?> selectComthemplyrinfochangedtlsList(ComthemplyrinfochangedtlsVO searchVO) throws Exception {
        return comthemplyrinfochangedtlsDAO.selectComthemplyrinfochangedtlsList(searchVO);
    }

    /**
	 * comthemplyrinfochangedtls 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return comthemplyrinfochangedtls 총 갯수
	 * @exception
	 */
    public int selectComthemplyrinfochangedtlsListTotCnt(ComthemplyrinfochangedtlsVO searchVO) {
		return comthemplyrinfochangedtlsDAO.selectComthemplyrinfochangedtlsListTotCnt(searchVO);
	}
    
}
