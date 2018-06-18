package egovframework.com.uss.cmt.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.uss.cmt.service.CmtDefaultVO;
import egovframework.com.uss.cmt.service.CmtManageVO;
import egovframework.com.uss.cmt.service.EgovCmtManageService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * 출퇴근관리에 관한 비지니스 클래스를 정의한다.
 * @author 표준프레임워크 개발팀
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  개발팀          최초 생성
 *
 * </pre>
 */
@Service("cmtManageService")
public class EgovCmtManageServiceImpl extends EgovAbstractServiceImpl implements EgovCmtManageService {

	/** cmtManageDAO */
	@Resource(name = "cmtManageDAO")
	private EgovCmtManageDAO cmtManageDAO;

	/** egovCmtManageIdGnrService */
	@Resource(name = "egovCmtManageIdGnrService")
	private EgovIdGnrService idgenService;

	/**
	 * 출퇴근정보 목록 화면에 출력
	 * @param  DeptInfo (부서별 - optional) 검색조건
	 * @return List<CmtManageVO> 업무사용자 목록정보
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CmtManageVO> selectCmtInfoList(CmtDefaultVO cmtSearchVO) throws Exception {
		List<CmtManageVO> result = (List<CmtManageVO>) cmtManageDAO.selectCmtInfoList(cmtSearchVO);
		return result;
	}
	
	public List<CmtManageVO> selectUserCmtList(CmtManageVO cmtManageVO) throws Exception {
		List<CmtManageVO> result = (List<CmtManageVO>) cmtManageDAO.selectUserCmtList(cmtManageVO);
		return result;
	}
	
	public List<CmtManageVO> selectDateCmtList(CmtManageVO cmtManageVO) throws Exception {
		return (List<CmtManageVO>) cmtManageDAO.selectDateCmtList(cmtManageVO);
	}
	public List<CmtManageVO> selectUserMonthCmtList(CmtManageVO cmtManageVO) throws Exception {
		return (List<CmtManageVO>) cmtManageDAO.selectUserMonthCmtList(cmtManageVO);
	}
	
	public List<CmtManageVO> selectUserCmtYearList(CmtManageVO cmtManageVO) throws Exception {
		List<CmtManageVO> result = (List<CmtManageVO>) cmtManageDAO.selectUserCmtYearList(cmtManageVO);
		return result;
	}
	
	
	/**
	 * 출근정보 입력, 디바이스를 통해 외부 연계입력가능
	 * @param cmtManageVO를 등록정보
	 * @return result 등록결과
	 * @throws Exception
	 */
	@Override
	public String insertWrkStartCmtInfo(CmtManageVO cmtManageVO) throws Exception {

		CmtManageVO tmpVO = cmtManageDAO.selectTodayWrkStartInfo(cmtManageVO);
		
		Date current = new Date();	
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(current);
		int dayNum = cal.get(Calendar.DAY_OF_WEEK);
		
		if(dayNum == 1 || dayNum == 7){
			cmtManageVO.setWrkStartStatus("정상");			
		}else{		
			SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss", Locale.KOREA); 
			Date d1 = f.parse("09:00:00"); 
			Date d2 = f.parse(f.format(current)); 
			long diff = d2.getTime() - d1.getTime(); 
			
			if(diff > 600000){
				cmtManageVO.setWrkStartStatus("지각");
			}else
				cmtManageVO.setWrkStartStatus("정상");			
			
		}
		
		if(tmpVO != null && tmpVO.getOutWrkStime() != null && !tmpVO.getOutWrkStime().isEmpty() ){
			return String.valueOf(cmtManageDAO.updateWrkStartCmtInfo(cmtManageVO));
			
		}else{
			// Key
			String wrktmId = idgenService.getNextStringId();
			cmtManageVO.setWrktmId(wrktmId);
			return cmtManageDAO.insertWrkStartCmtInfo(cmtManageVO);
		}
		
		
	}

	/**
	 * 퇴근 정보 입력을 위한 wrktm id 확인
	 * @param cmtManageVO 검색조건
	 * @return 총사용자갯수(int)
	 * @throws Exception
	 */
	@Override
	public String selectWrktmId(CmtManageVO cmtManageVO) throws Exception {

		return cmtManageDAO.selectWrktmId(cmtManageVO);
	}

	/**
	 * 퇴근 정보 입력
	 * @param cmtManageVO를 등록정보
	 * @return result 등록결과
	 * @throws Exception
	 */
	@Override
	public int insertWrkEndCmtInfo(CmtManageVO cmtManageVO) throws Exception {

		String rm = cmtManageVO.getRm();
		cmtManageVO = cmtManageDAO.selectWrkStartInfo(cmtManageVO);
		
		cmtManageVO.setRm(rm);
		Date current = new Date();	
		String endDate = cmtManageVO.getWrkStartTime().substring(0,11);
		String endTime = endDate + "18:00:00" ;
		String ngtTime = endDate + "19:00:00" ;
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA); 
		Date d1 = f.parse(cmtManageVO.getWrkStartTime()); 
		Date d2 = f.parse(f.format(current)); 
		Date d3 = f.parse(endTime); 
		Date d4 = f.parse(ngtTime); 
		
		long diff = d2.getTime() - d1.getTime(); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");		 
		Date currdate = formatter.parse(cmtManageVO.getWrktDt()); 
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(currdate);
		int dayNum = cal.get(Calendar.DAY_OF_WEEK);
		
		if(dayNum == 1 || dayNum == 7){
			float wt = ((float)diff/3600000);
			cmtManageVO.setWrkEndStatus("휴일근무");	
			cmtManageVO.setWrkHours(String.format("%.1f", wt));
			cmtManageVO.setOvtmwrkHours(String.format("%.1f", wt));
		}else{	
			if(d2.getTime() < d3.getTime()){			
				cmtManageVO.setWrkEndStatus("조퇴");
				cmtManageVO.setOvtmwrkHours("0");
				float wt = ((float)diff/3600000);
				if(wt > 5)
					cmtManageVO.setWrkHours(String.format("%.1f", wt - 1));
				else
					cmtManageVO.setWrkHours(String.format("%.1f", wt));
	
			}else if(diff >= 39600000 || d2.getTime() >= d4.getTime()){
				cmtManageVO.setWrkEndStatus("야근");
				float wt = ((float)diff/3600000);	
				cmtManageVO.setWrkHours(String.format("%.1f", wt - 2));
				cmtManageVO.setOvtmwrkHours(String.format("%.1f", wt - 10));
			}else{
				cmtManageVO.setWrkEndStatus("정상");
				cmtManageVO.setOvtmwrkHours("0");
				cmtManageVO.setWrkHours("8");
			}	
		}
		return cmtManageDAO.insertWrkEndCmtInfo(cmtManageVO);
	}

	public CmtManageVO selectTodayWrkStartInfo(CmtManageVO cmtManageVO) throws Exception{
		return cmtManageDAO.selectTodayWrkStartInfo(cmtManageVO);
	}
	
	public CmtManageVO selectUserCmtDetail(CmtManageVO cmtManageVO) throws Exception{
		return cmtManageDAO.selectUserCmtDetail(cmtManageVO);
	}
	
	public void updateWrkCmtInfoOfManager(CmtManageVO cmtManageVO) throws Exception{

		SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);

		long d1ms = 32400000; //9시 정각을 밀리세컨즈로
		Date d2 = fdate.parse(cmtManageVO.getWrkStartTime()); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(d2);
		long d2ms = cal.get(Calendar.SECOND) * 1000 + cal.get(Calendar.MINUTE) * 60000 + cal.get(Calendar.HOUR_OF_DAY) * 3600000 ;
		long diff = d2ms - d1ms; 
		
		int dayNum = cal.get(Calendar.DAY_OF_WEEK);
		
		if(dayNum == 1 || dayNum == 7){
			float wt = ((float)diff/3600000);
			cmtManageVO.setWrkEndStatus("휴일근무");	
			cmtManageVO.setWrkHours(String.format("%.1f", wt));
			cmtManageVO.setOvtmwrkHours(String.format("%.1f", wt));
		}else{
			
			if(diff > 600000){
				cmtManageVO.setWrkStartStatus("지각");
			}else
				cmtManageVO.setWrkStartStatus("정상");
			
			String endDate = cmtManageVO.getWrkStartTime().substring(0,11);
			String endTime = endDate + "18:00:00" ;
			String ngtTime = endDate + "19:00:00" ;
			
			Date date1 = fdate.parse(cmtManageVO.getWrkStartTime()); 
			Date date2 = fdate.parse(cmtManageVO.getWrkEndTime()); 
			Date date3 = fdate.parse(endTime); 
			Date date4 = fdate.parse(ngtTime); 
			
			long diff2 = date2.getTime() - date1.getTime(); 

			if(date2.getTime() < date3.getTime()){
				cmtManageVO.setWrkEndStatus("조퇴");
				cmtManageVO.setOvtmwrkHours("0");
				float wt = ((float)diff/3600000);
				if(wt > 5)
					cmtManageVO.setWrkHours(String.format("%.1f", wt - 1));
				else
					cmtManageVO.setWrkHours(String.format("%.1f", wt));
	
			}else if(diff2 >= 39600000 || date2.getTime() >= date4.getTime()){
				cmtManageVO.setWrkEndStatus("야근");
				float wt = ((float)diff/3600000);	
				cmtManageVO.setWrkHours(String.format("%.1f", wt - 2));
				cmtManageVO.setOvtmwrkHours(String.format("%.1f", wt - 10));
			}else{
				cmtManageVO.setWrkEndStatus("정상");
				cmtManageVO.setOvtmwrkHours("0");
				cmtManageVO.setWrkHours("8");
			}	
		}
		cmtManageDAO.updateWrkCmtInfoOfManager(cmtManageVO);
	}
	
	public int updateWrkStartCmtInfo(CmtManageVO cmtManageVO) throws Exception{
		return cmtManageDAO.updateWrkStartCmtInfo(cmtManageVO);
	}

	public String GetDateString(Date current, int day) {
	     
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(current);
	    cal.add(Calendar.DATE, day);

	     
	    // 특정 형태의 날짜로 값을 뽑기
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	    String strDate = df.format(cal.getTime());
	    return strDate;
	}

	
	public void insertWrkCmtInfoOfManager(CmtManageVO cmtManageVO)
			throws Exception {
		String wrktmId = idgenService.getNextStringId();
		cmtManageVO.setWrktmId(wrktmId);
		SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA); 
		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss", Locale.KOREA); 
		long d1ms = 32400000; 
		Date d2 = fdate.parse(cmtManageVO.getWrkStartTime()); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(d2);
		long d2ms = cal.get(Calendar.SECOND) * 1000 + cal.get(Calendar.MINUTE) * 60000 + cal.get(Calendar.HOUR_OF_DAY) * 3600000 ;
		long diff = d2ms - d1ms; 
		
		int dayNum = cal.get(Calendar.DAY_OF_WEEK);
		
		if(dayNum == 1 || dayNum == 7){
			float wt = ((float)diff/3600000);
			cmtManageVO.setWrkEndStatus("휴일근무");	
			cmtManageVO.setWrkHours(String.format("%.1f", wt));
			cmtManageVO.setOvtmwrkHours(String.format("%.1f", wt));
		}else{			
			if(diff > 600000){
				cmtManageVO.setWrkStartStatus("지각");
			}else
				cmtManageVO.setWrkStartStatus("정상");
			
			String endDate = cmtManageVO.getWrkStartTime().substring(0,11);
			String endTime = endDate + "18:00:00" ;
			String ngtTime = endDate + "19:00:00" ;
			
			Date date1 = fdate.parse(cmtManageVO.getWrkStartTime()); 
			Date date2 = fdate.parse(cmtManageVO.getWrkEndTime()); 
			Date date3 = fdate.parse(endTime); 
			Date date4 = fdate.parse(ngtTime); 
			
			long diff2 = date2.getTime() - date1.getTime(); 
			
			if(date2.getTime() < date3.getTime()){			
				cmtManageVO.setWrkEndStatus("조퇴");
				cmtManageVO.setOvtmwrkHours("0");
				float wt = ((float)diff/3600000);
				if(wt > 5)
					cmtManageVO.setWrkHours(String.format("%.1f", wt - 1));
				else
					cmtManageVO.setWrkHours(String.format("%.1f", wt));
	
			}else if(diff2 >= 39600000 || date2.getTime() >= date4.getTime() ){
				cmtManageVO.setWrkEndStatus("야근");
				float wt = ((float)diff/3600000);	
				cmtManageVO.setWrkHours(String.format("%.1f", wt - 2));
				cmtManageVO.setOvtmwrkHours(String.format("%.1f", wt - 10));
			}else{
				cmtManageVO.setWrkEndStatus("정상");
				cmtManageVO.setOvtmwrkHours("0");
				cmtManageVO.setWrkHours("8");
		}
	}
		cmtManageDAO.insertWrkCmtInfoOfManager(cmtManageVO);
	}
	@Override
	public String insertOutWrkStartCmtInfo(CmtManageVO cmtManageVO)
			throws Exception {
		// TODO Auto-generated method stub
		//출장관해서 작업 필요
		SimpleDateFormat fullfml = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		SimpleDateFormat dtfml = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yyyymmddfm1 = new SimpleDateFormat("yyyyMMdd");
		Date sdate = fullfml.parse(cmtManageVO.getOutWrkStime());
		String strdate = yyyymmddfm1.format(sdate);
		
		CmtManageVO saveCmtMgVO = new CmtManageVO();
		saveCmtMgVO.setEmplyrId(cmtManageVO.getEmplyrId());
		saveCmtMgVO.setWrktDt(strdate);		
		
		CmtManageVO tmpVO = cmtManageDAO.selectTodayWrkStartInfo(saveCmtMgVO);
		
		if(tmpVO != null){
			//출근후 출장
			saveCmtMgVO.setWrktmId(tmpVO.getWrktmId());
			saveCmtMgVO.setOrgnztId(cmtManageVO.getOrgnztId());	
			saveCmtMgVO.setOutWrkStime(cmtManageVO.getOutWrkStime());
			saveCmtMgVO.setOutWrkPlace(cmtManageVO.getOutWrkPlace());
			saveCmtMgVO.setRm(cmtManageVO.getRm());
			Date endday  = fullfml.parse(cmtManageVO.getOutWrkEtime());
			String strendDate = GetDateString(endday, 0);

			if(strdate.equals(strendDate)){	
				
				saveCmtMgVO.setOutWrkEtime(cmtManageVO.getOutWrkEtime());
				saveCmtMgVO.setWrkEndTime(cmtManageVO.getOutWrkEtime());
				saveCmtMgVO.setOutRm(cmtManageVO.getOutRm());
				System.out.println(cmtManageVO.getOutRm());
				if(cmtManageVO.getOutEndWrk().equals("Y")){
					cmtManageDAO.updateOutWrkStartCmtInfo(saveCmtMgVO);
				}else{
					cmtManageDAO.updateOutWrkStartCmtInfoNoEnd(saveCmtMgVO);
				}
				return null;
			}
			
			while(true){
				Calendar cal = Calendar.getInstance();
			    cal.setTime(sdate);
			    cal.add(Calendar.DATE, 1);
			    sdate = cal.getTime();			    
			    strdate = yyyymmddfm1.format(sdate);
			    
			    String wrktmId = idgenService.getNextStringId();
				saveCmtMgVO.setWrktmId(wrktmId);
				saveCmtMgVO.setWrktDt(dtfml.format(sdate).replace("-", ""));
			    saveCmtMgVO.setWrkStartTime(dtfml.format(sdate)+ " 09:00:00");
				saveCmtMgVO.setOutWrkStime(dtfml.format(sdate)+ " 09:00:00");
				

				if(strdate.equals(strendDate)){
					//퇴근 시간까지 insert
					saveCmtMgVO.setOutWrkEtime(cmtManageVO.getOutWrkEtime());
					saveCmtMgVO.setWrkEndTime(cmtManageVO.getOutWrkEtime());
					cmtManageDAO.insertOutWrkStartCmtInfo(saveCmtMgVO);
					break;
				}else{
					saveCmtMgVO.setOutWrkEtime(dtfml.format(sdate)+ " 18:00:00");
					saveCmtMgVO.setWrkEndTime(dtfml.format(sdate)+ " 18:00:00");
					cmtManageDAO.insertOutWrkStartCmtInfo(saveCmtMgVO);
				}
			}			
		}else{
			//출근 없는날 출장
			String wrktmId = idgenService.getNextStringId();
			saveCmtMgVO.setWrktmId(wrktmId);
			saveCmtMgVO.setWrkStartTime(cmtManageVO.getOutWrkStime());
			saveCmtMgVO.setOrgnztId(cmtManageVO.getOrgnztId());	
			saveCmtMgVO.setOutWrkStime(cmtManageVO.getOutWrkStime());
			saveCmtMgVO.setOutWrkPlace(cmtManageVO.getOutWrkPlace());
			saveCmtMgVO.setRm(cmtManageVO.getRm());
			
			while(true){
				Date endday  = fullfml.parse(cmtManageVO.getOutWrkEtime());
				String strendDate = GetDateString(endday, 0);

				if(strdate.equals(strendDate)){
					//퇴근 시간까지 insert
					saveCmtMgVO.setOutWrkEtime(cmtManageVO.getOutWrkEtime());
					saveCmtMgVO.setWrkEndTime(cmtManageVO.getOutWrkEtime());
					cmtManageDAO.insertOutWrkStartCmtInfo(saveCmtMgVO);
					break;
				}else{
					saveCmtMgVO.setOutWrkEtime(dtfml.format(sdate)+ " 18:00:00");
					saveCmtMgVO.setWrkEndTime(dtfml.format(sdate)+ " 18:00:00");
					cmtManageDAO.insertOutWrkStartCmtInfo(saveCmtMgVO);
				}
				
				Calendar cal = Calendar.getInstance();
			    cal.setTime(sdate);
			    cal.add(Calendar.DATE, 1);
			    sdate = cal.getTime();			    
			    strdate = yyyymmddfm1.format(sdate);
			    
			    wrktmId = idgenService.getNextStringId();
				saveCmtMgVO.setWrktmId(wrktmId);
				saveCmtMgVO.setWrktDt(dtfml.format(sdate).replace("-", ""));
			    saveCmtMgVO.setWrkStartTime(dtfml.format(sdate)+ " 09:00:00");
				saveCmtMgVO.setOutWrkStime(dtfml.format(sdate)+ " 09:00:00");
				
			}						
		}
		return null;
	}
	
	
}