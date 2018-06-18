package tis.com.manager.managerHoliday.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.manager.managerHoliday.service.TbAllvcatnmanageVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

/**
 * @Class Name : TbAllvcatnmanageDAO.java
 * @Description : TbAllvcatnmanage DAO Class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180214
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Repository("tbAllvcatnmanageDAO")
public class TbAllvcatnmanageDAO extends EgovComAbstractDAO {

	/**
	 * tb_allvcatnmanage을 등록한다.
	 * @param vo - 등록할 정보가 담긴 TbAllvcatnmanageVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertTbAllvcatnmanage(TbAllvcatnmanageVO vo) throws Exception {
        return (String)insert("tbAllvcatnmanageDAO.insertTbAllvcatnmanage_S", vo);
    }

    /**
	 * tb_allvcatnmanage을 수정한다.
	 * @param vo - 수정할 정보가 담긴 TbAllvcatnmanageVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateTbAllvcatnmanage(TbAllvcatnmanageVO vo) throws Exception {
        update("tbAllvcatnmanageDAO.updateTbAllvcatnmanage_S", vo);
    }

    /**
	 * tb_allvcatnmanage을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 TbAllvcatnmanageVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteTbAllvcatnmanage(TbAllvcatnmanageVO vo) throws Exception {
        delete("tbAllvcatnmanageDAO.deleteTbAllvcatnmanage_S", vo);
    }

    /**
	 * tb_allvcatnmanage을 조회한다.
	 * @param vo - 조회할 정보가 담긴 TbAllvcatnmanageVO
	 * @return 조회한 tb_allvcatnmanage
	 * @exception Exception
	 */
    public TbAllvcatnmanageVO selectTbAllvcatnmanage(TbAllvcatnmanageVO vo) throws Exception {
        return (TbAllvcatnmanageVO) select("tbAllvcatnmanageDAO.selectTbAllvcatnmanage_S", vo);
    }

    /**
	 * tb_allvcatnmanage 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return tb_allvcatnmanage 목록
	 * @exception Exception
	 */
    public List<?> selectTbAllvcatnmanageList(TbAllvcatnmanageVO vo) throws Exception {
        return list("tbAllvcatnmanageDAO.selectTbAllvcatnmanageList_D", vo);
    }

    /**
	 * tb_allvcatnmanage 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return tb_allvcatnmanage 총 갯수
	 * @exception
	 */
    public int selectTbAllvcatnmanageListTotCnt(TbAllvcatnmanageVO vo) {
        return (Integer)select("tbAllvcatnmanageDAO.selectTbAllvcatnmanageListTotCnt_S", vo);
    }

    public List<?> selectTbAllvcatnmanageYearList() throws Exception {
        return list("tbAllvcatnmanageDAO.selectTbAllvcatnmanageYearList");
    }

    public List<?> selectTbAllvcatnmanageListForMonth(TbAllvcatnmanageVO vo) throws Exception {
        return list("tbAllvcatnmanageDAO.selectTbAllvcatnmanageLisForMonth", vo);
    }
}
