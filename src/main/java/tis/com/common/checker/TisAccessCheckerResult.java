package tis.com.common.checker;

public class TisAccessCheckerResult {
	private boolean success;
	
	private String viewName = "";

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
}
