package tis.com.financial.bankBook.service;

import java.util.List;

/**
 * @Class Name : TbBankbookmanageService.java
 * @Description : TbBankbookmanage Business class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180125
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public interface TbBankbookmanageService {
	
	/**
	 * tb_bankbookmanage을 등록한다.
	 * @param vo - 등록할 정보가 담긴 TbBankbookmanageVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    String insertTbBankbookmanage(TbBankbookmanageVO vo) throws Exception;
    
    /**
	 * tb_bankbookmanage을 수정한다.
	 * @param vo - 수정할 정보가 담긴 TbBankbookmanageVO
	 * @return void형
	 * @exception Exception
	 */
    void updateTbBankbookmanage(TbBankbookmanageVO vo) throws Exception;
    
    /**
	 * tb_bankbookmanage을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 TbBankbookmanageVO
	 * @return void형 
	 * @exception Exception
	 */
    void deleteTbBankbookmanage(TbBankbookmanageVO vo) throws Exception;
    
    /**
	 * tb_bankbookmanage을 조회한다.
	 * @param vo - 조회할 정보가 담긴 TbBankbookmanageVO
	 * @return 조회한 tb_bankbookmanage
	 * @exception Exception
	 */
    TbBankbookmanageVO selectTbBankbookmanage(TbBankbookmanageVO vo) throws Exception;
    
    /**
	 * tb_bankbookmanage 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_bankbookmanage 목록
	 * @exception Exception
	 */
    List selectTbBankbookmanageList(TbBankbookmanageVO searchVO) throws Exception;
    
    /**
	 * tb_bankbookmanage 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_bankbookmanage 총 갯수
	 * @exception
	 */
    int selectTbBankbookmanageListTotCnt(TbBankbookmanageVO searchVO);
    
    List selectTbBankbookUseList() throws Exception;

	int selectTbBankbookmanageByCpCode(String cpCode) throws Exception;
    
}
