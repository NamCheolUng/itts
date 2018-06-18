package tis.com.financial.bankBook.service;

import java.util.List;

/**
 * @Class Name : TbBankcardmanageService.java
 * @Description : TbBankcardmanage Business class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180126
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public interface TbBankcardmanageService {
	
	/**
	 * tb_bankcardmanage을 등록한다.
	 * @param vo - 등록할 정보가 담긴 TbBankcardmanageVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    String insertTbBankcardmanage(TbBankcardmanageVO vo) throws Exception;
    
    /**
	 * tb_bankcardmanage을 수정한다.
	 * @param vo - 수정할 정보가 담긴 TbBankcardmanageVO
	 * @return void형
	 * @exception Exception
	 */
    void updateTbBankcardmanage(TbBankcardmanageVO vo) throws Exception;
    
    /**
	 * tb_bankcardmanage을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 TbBankcardmanageVO
	 * @return void형 
	 * @exception Exception
	 */
    void deleteTbBankcardmanage(TbBankcardmanageVO vo) throws Exception;
    
    /**
	 * tb_bankcardmanage을 조회한다.
	 * @param vo - 조회할 정보가 담긴 TbBankcardmanageVO
	 * @return 조회한 tb_bankcardmanage
	 * @exception Exception
	 */
    TbBankcardmanageVO selectTbBankcardmanage(TbBankcardmanageVO vo) throws Exception;
    
    /**
	 * tb_bankcardmanage 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_bankcardmanage 목록
	 * @exception Exception
	 */
    List selectTbBankcardmanageList(TbBankcardmanageVO searchVO) throws Exception;
    
    /**
	 * tb_bankcardmanage 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_bankcardmanage 총 갯수
	 * @exception
	 */
    int selectTbBankcardmanageListTotCnt(TbBankcardmanageVO searchVO);
    
    List selectEmplyrList() throws Exception;
    
}
