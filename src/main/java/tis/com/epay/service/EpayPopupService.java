package tis.com.epay.service;

public interface EpayPopupService {
	public void saveFavoriteItem(EpayApprLineVO epayApprLineVO, EpayApprLnCfgVO epayApprLnCfgVO) throws Exception;
	public void removeFavoriteItem(EpayApprLineVO epayApprLineVO, EpayApprLnCfgVO epayApprLnCfgVO) throws Exception;
}
