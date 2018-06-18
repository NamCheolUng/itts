package tis.com.epay.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface EpayApprInfoService {

	public List<EgovMap> selectEpayApprInfoList(EpayApprInfoVO epayApprInfoVO) throws Exception;

	public Integer epayApprInfoListCount(EpayApprInfoVO epayApprInfoVO) throws Exception;
	
	public void updateEpayApprHist(EpayApprHistVO epayApprHistVO) throws Exception;
	
	public void epayApprHist(EpayApprHistVO epayApprHistVO) throws Exception;
	// 다음 결재자 사원번호 조회
	public EgovMap epayApprHistNextEmplNo(EpayApprHistVO epayApprHistVO) throws Exception;
	// 결재할 문서 개수 조회
	public Integer epayApprWaitingCount(EpayApprInfoVO epayApprInfoVO) throws Exception;
	// 결재 반려 처리
	public void epayApprReturn(EpayApprHistVO epayApprHistVO) throws Exception;
}
