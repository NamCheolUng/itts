package tis.com.financial.bankBook.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.financial.bankBook.service.TbBankbookmanageVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

/**
 * @Class Name : TbBankbookmanageDAO.java
 * @Description : TbBankbookmanage DAO Class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180125
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Repository("tbBankbookmanageDAO")
public class TbBankbookmanageDAO extends EgovComAbstractDAO {

	/**
	 * tb_bankbookmanage을 등록한다.
	 * @param vo - 등록할 정보가 담긴 TbBankbookmanageVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertTbBankbookmanage(TbBankbookmanageVO vo) throws Exception {
        return (String)insert("tbBankbookmanageDAO.insertTbBankbookmanage_S", vo);
    }

    /**
	 * tb_bankbookmanage을 수정한다.
	 * @param vo - 수정할 정보가 담긴 TbBankbookmanageVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateTbBankbookmanage(TbBankbookmanageVO vo) throws Exception {
        update("tbBankbookmanageDAO.updateTbBankbookmanage_S", vo);
    }

    /**
	 * tb_bankbookmanage을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 TbBankbookmanageVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteTbBankbookmanage(TbBankbookmanageVO vo) throws Exception {
        delete("tbBankbookmanageDAO.deleteTbBankbookmanage_S", vo);
    }

    /**
	 * tb_bankbookmanage을 조회한다.
	 * @param vo - 조회할 정보가 담긴 TbBankbookmanageVO
	 * @return 조회한 tb_bankbookmanage
	 * @exception Exception
	 */
    public TbBankbookmanageVO selectTbBankbookmanage(TbBankbookmanageVO vo) throws Exception {
        return (TbBankbookmanageVO) select("tbBankbookmanageDAO.selectTbBankbookmanage_S", vo);
    }

    /**
	 * tb_bankbookmanage 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return tb_bankbookmanage 목록
	 * @exception Exception
	 */
    public List<?> selectTbBankbookmanageList(TbBankbookmanageVO searchVO) throws Exception {
        return list("tbBankbookmanageDAO.selectTbBankbookmanageList_D", searchVO);
    }

    /**
	 * tb_bankbookmanage 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return tb_bankbookmanage 총 갯수
	 * @exception
	 */
    public int selectTbBankbookmanageListTotCnt(TbBankbookmanageVO searchVO) {
        return (Integer)select("tbBankbookmanageDAO.selectTbBankbookmanageListTotCnt_S", searchVO);
    }

    public List<?> selectTbBankbookUseList() throws Exception {
        return list("tbBankbookmanageDAO.selectTbBankbookUseList");
    }

	public int selectTbBankbookmanageByCpCode(String cpCode) throws Exception {
		Object obj = select("tbBankbookmanageDAO.selectTbBankbookmanageByCpCode", cpCode);
		return (Integer)(obj != null ? obj : 0);
	}
    
}
