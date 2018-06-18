package tis.com.business.item.service;

import java.util.List;
import tis.com.business.item.service.TbItemmanageDefaultVO;
import tis.com.business.item.service.TbItemmanageVO;

/**
 * @Class Name : TbItemmanageService.java
 * @Description : TbItemmanage Business class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180130
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public interface TbItemmanageService {
	
	/**
	 * tb_itemmanage을 등록한다.
	 * @param vo - 등록할 정보가 담긴 TbItemmanageVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    String insertTbItemmanage(TbItemmanageVO vo) throws Exception;
    
    /**
	 * tb_itemmanage을 수정한다.
	 * @param vo - 수정할 정보가 담긴 TbItemmanageVO
	 * @return void형
	 * @exception Exception
	 */
    void updateTbItemmanage(TbItemmanageVO vo) throws Exception;
    
    /**
	 * tb_itemmanage을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 TbItemmanageVO
	 * @return void형 
	 * @exception Exception
	 */
    void deleteTbItemmanage(TbItemmanageVO vo) throws Exception;
    
    /**
	 * tb_itemmanage을 조회한다.
	 * @param vo - 조회할 정보가 담긴 TbItemmanageVO
	 * @return 조회한 tb_itemmanage
	 * @exception Exception
	 */
    TbItemmanageVO selectTbItemmanage(TbItemmanageVO vo) throws Exception;
    
    /**
	 * tb_itemmanage 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_itemmanage 목록
	 * @exception Exception
	 */
    List selectTbItemmanageList(TbItemmanageDefaultVO searchVO) throws Exception;
    
    List selectTbItemNameList() throws Exception;
    
    /**
	 * tb_itemmanage 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_itemmanage 총 갯수
	 * @exception
	 */
    int selectTbItemmanageListTotCnt(TbItemmanageDefaultVO searchVO);
    
    String selectGetItemID();
}
