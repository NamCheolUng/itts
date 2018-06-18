package tis.com.business.holiday.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.uss.ion.vct.service.EgovVcatnManageService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("yearVacationScheduling")
public class YearVacationScheduling extends EgovAbstractServiceImpl {

	@Resource(name = "egovVcatnManageService")
	private EgovVcatnManageService egovVcatnManageService;

	public void main() throws Exception {
		//1년에 한번 1년이 지났는지 확인후 기본 휴가일수 및 추가일수 추가해주기 
		egovVcatnManageService.insertYearVacationCnt();	
	}
	
}
