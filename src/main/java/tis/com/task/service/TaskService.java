package tis.com.task.service;

import java.util.List;

import egovframework.com.cop.smt.sim.service.IndvdlSchdulManageVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface TaskService {

	public List<?> selectTaskList(TaskVO taskVO) throws Exception;
	
	public int selectTaskListCnt(TaskVO taskVO) throws Exception;
	
	public void insertTask(TaskVO taskVO) throws Exception;
	
	public void deleteTask(TaskVO taskVO) throws Exception;
	
	public void updateTask(TaskVO taskVO) throws Exception;
	
	public EgovMap selectTaskDetail(TaskVO taskVO) throws Exception;
	
	public int taskNmCompare(TaskVO taskVO) throws Exception;
		
	public List<?> selectRelatedTaskList(TaskVO taskVO) throws Exception;
	
	public void insertTaskEnv(TaskEnvVO taskEnvVO) throws Exception;
	
	public void updateTaskEnv(TaskEnvVO taskEnvVO) throws Exception;
	
	public EgovMap selectTaskEnv(TaskEnvVO taskEnvVO) throws Exception;
	
	public void insertTaskSubEnv(TaskEnvVO taskEnvVO) throws Exception;
	
	public void deleteTaskSubEnv(TaskEnvVO taskEnvVO) throws Exception;
	
	public List<?> selectTaskSubEnvList(TaskEnvVO taskEnvVO) throws Exception;
	
	public List<?> selectTaskStepTemplate(TaskVO taskVO) throws Exception;
	
	public void insertTaskStep(TaskStepVO taskStepVO) throws Exception;
	
	public void updateTaskStep(TaskStepVO taskStepVO) throws Exception;
	
	public List<?> selectTaskStep(TaskVO taskVO) throws Exception;
	
	public List<?> selectTaskSchduleList(TaskVO taskVO) throws Exception;
	
	public List<?> selectUserList_task(TaskVO taskVO) throws Exception;
	
	public List<?> selectTaskPersonList(TaskVO taskVO) throws Exception;
	
	public void taskPersonSave(TaskPersonVO taskPersonVO) throws Exception;
	
	public List<?> selectCompanyList(TaskVO taskVO) throws Exception;
	
	public List<?> selectCompanyManagerList(TaskOrgztPerVO taskOrgztPerVO) throws Exception;
	
	public List<?> selectTbOrgztPersonList(TaskOrgztPerVO taskOrgztPerVO) throws Exception;
	
	public void taskDeptMngerSave(TaskOrgztPerVO taskOrgztPerVO) throws Exception;
	
	public List<?> selectDelaySchdulList(TaskVO taskVO) throws Exception;
	
	public void updateSchdulPrgrSttusAndTime(IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception;
	
	public EgovMap selectParticiEmplNo(TaskVO taskVO) throws Exception;
	
	public List<?> selectTaskSetpMaster() throws Exception;
	

}
