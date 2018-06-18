package tis.com.manager.personnelAppointment.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
/**
 * 인사 발령을 DB에 적용 시키는  스케즐러 
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2018.01.16  이영목           최초 생성
 */
@Service("personAppointScheduling")
public class PersonAppointScheduling extends EgovAbstractServiceImpl {

	@Resource(name = "comthemplyrinfochangedtlsService")
	private ComthemplyrinfochangedtlsService emplyrinfochangeService;
	
	public void main() throws Exception {
		//같은 날짜에 인사 발령이 있는지 체크 후 DB에 반영하는 루틴 실행
		
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		Date currentTime = new Date ();
		String mTime = mSimpleDateFormat.format ( currentTime );
		
		ComthemplyrinfochangedtlsVO comthemplyrinfochangedtlsVO = new ComthemplyrinfochangedtlsVO();
		BigDecimal today = new BigDecimal(Integer.parseInt(mTime));		
		comthemplyrinfochangedtlsVO.setChangeDe(today);
				
		emplyrinfochangeService.updateAppointmentChange(comthemplyrinfochangedtlsVO);		
		
	}
}
