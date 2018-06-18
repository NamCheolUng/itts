package tis.com.financial.bankBook.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.financial.bankBook.service.TbBankcardmanageVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

/**
 * @Class Name : TbBankcardmanageDAO.java
 * @Description : TbBankcardmanage DAO Class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180126
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Repository("tbBankcardmanageDAO")
public class TbBankcardmanageDAO extends EgovComAbstractDAO {

	/**
	 * tb_bankcardmanage을 등록한다.
	 * @param vo - 등록할 정보가 담긴 TbBankcardmanageVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertTbBankcardmanage(TbBankcardmanageVO vo) throws Exception {
        return (String)insert("tbBankcardmanageDAO.insertTbBankcardmanage_S", vo);
    }

    /**
	 * tb_bankcardmanage을 수정한다.
	 * @param vo - 수정할 정보가 담긴 TbBankcardmanageVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateTbBankcardmanage(TbBankcardmanageVO vo) throws Exception {
        update("tbBankcardmanageDAO.updateTbBankcardmanage_S", vo);
    }

    /**
	 * tb_bankcardmanage을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 TbBankcardmanageVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteTbBankcardmanage(TbBankcardmanageVO vo) throws Exception {
        delete("tbBankcardmanageDAO.deleteTbBankcardmanage_S", vo);
    }

    /**
	 * tb_bankcardmanage을 조회한다.
	 * @param vo - 조회할 정보가 담긴 TbBankcardmanageVO
	 * @return 조회한 tb_bankcardmanage
	 * @exception Exception
	 */
    public TbBankcardmanageVO selectTbBankcardmanage(TbBankcardmanageVO vo) throws Exception {
        return (TbBankcardmanageVO) select("tbBankcardmanageDAO.selectTbBankcardmanage_S", vo);
    }

    /**
	 * tb_bankcardmanage 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return tb_bankcardmanage 목록
	 * @exception Exception
	 */
    public List<?> selectTbBankcardmanageList(TbBankcardmanageVO searchVO) throws Exception {
        return list("tbBankcardmanageDAO.selectTbBankcardmanageList_D", searchVO);
    }

    /**
	 * tb_bankcardmanage 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return tb_bankcardmanage 총 갯수
	 * @exception
	 */
    public int selectTbBankcardmanageListTotCnt(TbBankcardmanageVO searchVO) {
        return (Integer)select("tbBankcardmanageDAO.selectTbBankcardmanageListTotCnt_S", searchVO);
    }

    public List<?> selectEmplyrList() throws Exception {
        return list("tbBankcardmanageDAO.selectEmplyrList");
    }
    
}
