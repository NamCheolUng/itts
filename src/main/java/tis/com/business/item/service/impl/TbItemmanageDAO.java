package tis.com.business.item.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import tis.com.business.item.service.TbItemmanageVO;
import tis.com.business.item.service.TbItemmanageDefaultVO;

/**
 * @Class Name : TbItemmanageDAO.java
 * @Description : TbItemmanage DAO Class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180130
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Repository("tbItemmanageDAO")
public class TbItemmanageDAO extends EgovComAbstractDAO {

	/**
	 * tb_itemmanage을 등록한다.
	 * @param vo - 등록할 정보가 담긴 TbItemmanageVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertTbItemmanage(TbItemmanageVO vo) throws Exception {
        return (String)insert("tbItemmanageDAO.insertTbItemmanage_S", vo);
    }

    /**
	 * tb_itemmanage을 수정한다.
	 * @param vo - 수정할 정보가 담긴 TbItemmanageVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateTbItemmanage(TbItemmanageVO vo) throws Exception {
        update("tbItemmanageDAO.updateTbItemmanage_S", vo);
    }

    /**
	 * tb_itemmanage을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 TbItemmanageVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteTbItemmanage(TbItemmanageVO vo) throws Exception {
        delete("tbItemmanageDAO.deleteTbItemmanage_S", vo);
    }

    /**
	 * tb_itemmanage을 조회한다.
	 * @param vo - 조회할 정보가 담긴 TbItemmanageVO
	 * @return 조회한 tb_itemmanage
	 * @exception Exception
	 */
    public TbItemmanageVO selectTbItemmanage(TbItemmanageVO vo) throws Exception {
        return (TbItemmanageVO) select("tbItemmanageDAO.selectTbItemmanage_S", vo);
    }

    /**
	 * tb_itemmanage 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return tb_itemmanage 목록
	 * @exception Exception
	 */
    public List<?> selectTbItemmanageList(TbItemmanageDefaultVO searchVO) throws Exception {
        return list("tbItemmanageDAO.selectTbItemmanageList_D", searchVO);
    }

    public List<?> selectTbItemNameList() throws Exception {
        return list("tbItemmanageDAO.selectTbItemNameList");
    }
    
    /**
	 * tb_itemmanage 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return tb_itemmanage 총 갯수
	 * @exception
	 */
    public int selectTbItemmanageListTotCnt(TbItemmanageDefaultVO searchVO) {
        return (Integer)select("tbItemmanageDAO.selectTbItemmanageListTotCnt_S", searchVO);
    }

    public String selectGetItemID() {
        return (String)select("tbItemmanageDAO.selectGetItemID");
    }

}
