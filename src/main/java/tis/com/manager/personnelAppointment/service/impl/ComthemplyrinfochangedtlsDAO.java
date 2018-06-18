package tis.com.manager.personnelAppointment.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.manager.personnelAppointment.service.ComthemplyrinfochangedtlsVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

/**
 * @Class Name : ComthemplyrinfochangedtlsDAO.java
 * @Description : Comthemplyrinfochangedtls DAO Class
 * @Modification Information
 *
 * @author 이영목
 * @since 20180123
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Repository("comthemplyrinfochangedtlsDAO")
public class ComthemplyrinfochangedtlsDAO extends EgovComAbstractDAO {

	/**
	 * comthemplyrinfochangedtls을 등록한다.
	 * @param vo - 등록할 정보가 담긴 ComthemplyrinfochangedtlsVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertComthemplyrinfochangedtls(ComthemplyrinfochangedtlsVO vo) throws Exception {
        return (String)insert("comthemplyrinfochangedtlsDAO.insertComthemplyrinfochangedtls_S", vo);
    }

    /**
	 * comthemplyrinfochangedtls을 수정한다.
	 * @param vo - 수정할 정보가 담긴 ComthemplyrinfochangedtlsVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateComthemplyrinfochangedtls(ComthemplyrinfochangedtlsVO vo) throws Exception {
        update("comthemplyrinfochangedtlsDAO.updateComthemplyrinfochangedtls_S", vo);
    }
    
    public void updateAppointmentChange(ComthemplyrinfochangedtlsVO vo) throws Exception {
        update("comthemplyrinfochangedtlsDAO.updateAppointmentChange", vo);
    }

    /**
	 * comthemplyrinfochangedtls을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 ComthemplyrinfochangedtlsVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteComthemplyrinfochangedtls(ComthemplyrinfochangedtlsVO vo) throws Exception {
        delete("comthemplyrinfochangedtlsDAO.deleteComthemplyrinfochangedtls_S", vo);
    }

    /**
	 * comthemplyrinfochangedtls을 조회한다.
	 * @param vo - 조회할 정보가 담긴 ComthemplyrinfochangedtlsVO
	 * @return 조회한 comthemplyrinfochangedtls
	 * @exception Exception
	 */
    public ComthemplyrinfochangedtlsVO selectComthemplyrinfochangedtls(ComthemplyrinfochangedtlsVO vo) throws Exception {
        return (ComthemplyrinfochangedtlsVO) select("comthemplyrinfochangedtlsDAO.selectComthemplyrinfochangedtls_S", vo);
    }

    /**
	 * comthemplyrinfochangedtls 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return comthemplyrinfochangedtls 목록
	 * @exception Exception
	 */
    public List<?> selectComthemplyrinfochangedtlsList(ComthemplyrinfochangedtlsVO searchVO) throws Exception {
        return list("comthemplyrinfochangedtlsDAO.selectComthemplyrinfochangedtlsList_D", searchVO);
    }

    /**
	 * comthemplyrinfochangedtls 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return comthemplyrinfochangedtls 총 갯수
	 * @exception
	 */
    public int selectComthemplyrinfochangedtlsListTotCnt(ComthemplyrinfochangedtlsVO searchVO) {
        return (Integer)select("comthemplyrinfochangedtlsDAO.selectComthemplyrinfochangedtlsListTotCnt_S", searchVO);
    }

}
