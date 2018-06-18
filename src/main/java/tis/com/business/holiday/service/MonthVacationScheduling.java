package tis.com.business.holiday.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.uss.ion.vct.service.EgovVcatnManageService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;


@Service("monthVacationScheduling")
public class MonthVacationScheduling extends EgovAbstractServiceImpl {

	@Resource(name = "egovVcatnManageService")
	private EgovVcatnManageService egovVcatnManageService;

	public void main() throws Exception {
		//매월 1일 3시에 유저 테이블 생성하고 한달이 지나면 1일 추가해줌
		egovVcatnManageService.insertUserDefaultVacationCnt();
		
		egovVcatnManageService.updateMonthVacationInc();
		
	}
}
