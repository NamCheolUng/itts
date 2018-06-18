package tis.com.manager.managerHoliday.service;

import java.util.List;

/**
 * @Class Name : TbAllvcatnmanageService.java
 * @Description : TbAllvcatnmanage Business class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180214
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public interface TbAllvcatnmanageService {
	
	/**
	 * tb_allvcatnmanage을 등록한다.
	 * @param vo - 등록할 정보가 담긴 TbAllvcatnmanageVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    String insertTbAllvcatnmanage(TbAllvcatnmanageVO vo) throws Exception;
    
    /**
	 * tb_allvcatnmanage을 수정한다.
	 * @param vo - 수정할 정보가 담긴 TbAllvcatnmanageVO
	 * @return void형
	 * @exception Exception
	 */
    void updateTbAllvcatnmanage(TbAllvcatnmanageVO vo) throws Exception;
    
    /**
	 * tb_allvcatnmanage을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 TbAllvcatnmanageVO
	 * @return void형 
	 * @exception Exception
	 */
    void deleteTbAllvcatnmanage(TbAllvcatnmanageVO vo) throws Exception;
    
    /**
	 * tb_allvcatnmanage을 조회한다.
	 * @param vo - 조회할 정보가 담긴 TbAllvcatnmanageVO
	 * @return 조회한 tb_allvcatnmanage
	 * @exception Exception
	 */
    TbAllvcatnmanageVO selectTbAllvcatnmanage(TbAllvcatnmanageVO vo) throws Exception;
    
    /**
	 * tb_allvcatnmanage 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_allvcatnmanage 목록
	 * @exception Exception
	 */
    List selectTbAllvcatnmanageList(TbAllvcatnmanageVO vo) throws Exception;
    
    /**
	 * tb_allvcatnmanage 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_allvcatnmanage 총 갯수
	 * @exception
	 */
    int selectTbAllvcatnmanageListTotCnt(TbAllvcatnmanageVO vo);
    
    List selectTbAllvcatnmanageYearList() throws Exception;

	List<?> selectTbAllvcatnmanageListForMonth(TbAllvcatnmanageVO vo) throws Exception;
    
}
