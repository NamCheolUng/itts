package egovframework.com.cop.adb.service;

import java.util.List;
import java.util.Map;

/**
 * 주소록정보를 관리하기 위한 서비스 인터페이스 클래스
 * @author 공통컴포넌트팀 윤성록
 * @since 2009.09.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.9.25  윤성록          최초 생성
 *
 * </pre>
 */
public interface EgovAdressBookService {
    
  
    /**
     * 주소록 목록을 조회한다.
     * @param AdressBookVO
     * @return  Map<String, Object>
     * @exception Exception
     */
    public Map<String, Object> selectAdressBookList(AdressBookVO adressBookVO) throws Exception;
    
    /**
     * 주소록 정보를 조회한다.
     * @param AdressBookVO
     * @return  AdressBookVO
     * @exception Exception
     */
    public AdressBookVO selectAdressBook(AdressBookVO adressBookVO) throws Exception;
    
    public List<AdressBookUserVO> selectAdbkCodeList() throws Exception;
    
    public String selectAdbkEntrprsSeCode(AdressBookVO adbVO) throws Exception;
    
    
    /**
     * 주소록 정보를 삭제한다.
     * @param AdressBook
     * @return 
     * @exception Exception
     */
    public void deleteAdressBook(AdressBook adressBook) throws Exception;
    
    /**
     * 사용자 목록을 조회한다.
     * @param AdressBookUserVO
     * @return Map<String, Object>
     * @exception Exception
     */
    public Map<String, Object> selectManList(AdressBookUserVO adressBookUserVO) throws Exception;
    
    /**
     * 명함 목록을 조회한다.
     * @param AdressBookUserVO
     * @return Map<String, Object>
     * @exception Exception
     */
    public Map<String, Object> selectCardList(AdressBookUserVO adressBookUserVO) throws Exception;
    
    /**
     * 주소록 정보를 등록한다.
     * 
     * @param AdressBook
     * @throws Exception
     */
    public void insertAdressBook(AdressBookVO adbkVO) throws Exception;
    
    public void insertAdressBookUser(AdressBookUser abUser) throws Exception;

          
    /**
     * 주소록 정보를 수정한다.
     * @param AdressBookVO
     * @return 
     * @exception Exception
     */
    public void updateAdressBook(AdressBookVO adressBookVO) throws Exception;
    
    /**
     * 주소록 구성원 정보를 불러온다.
     * @param String
     * @return 
     * @exception Exception
     */
    public AdressBookUser selectAdbkUser(String id) throws Exception;
    
    
    public List<AdressBookUserVO> selectCompanyListist(AdressBookUserVO adressBookUserVO) throws Exception;
    
    public int selectCompanyLististCnt(AdressBookUserVO adressBookUserVO) throws Exception;
    
    public AdressBookUserVO selectUserDetail(AdressBookUserVO adressBookUserVO) throws Exception;
    
    public void updateAdressBookUser(AdressBookUserVO adressBookUserVO) throws Exception;
    
}