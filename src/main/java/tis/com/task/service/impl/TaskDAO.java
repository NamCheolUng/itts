package tis.com.task.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.task.service.TaskEnvVO;
import tis.com.task.service.TaskOrgztPerVO;
import tis.com.task.service.TaskPersonVO;
import tis.com.task.service.TaskStepVO;
import tis.com.task.service.TaskVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.cop.smt.sim.service.IndvdlSchdulManageVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("taskDAO")
public class TaskDAO extends EgovComAbstractDAO{
	
	public List<?> selectTaskList(TaskVO taskVO) throws Exception{
		return list("taskDAO.selectTaskList",taskVO);
	}
	
	public void insertTask(TaskVO taskVO) throws Exception{
		 insert("taskDAO.insertTask",taskVO);
	}
	
	public void updateTask(TaskVO taskVO) throws Exception{
		 update("taskDAO.updateTask",taskVO);
	}
	
	public void deleteTask(TaskVO taskVO) throws Exception{
		 delete("taskDAO.deleteTask",taskVO);
	}
	
	public EgovMap selectTaskDetail(TaskVO taskVO) throws Exception{
		 return (EgovMap) select("taskDAO.selectTaskDetail",taskVO);
	}
	
	public int selectTaskListCnt(TaskVO taskVO) throws Exception{
		 return (int) select("taskDAO.selectTaskListCnt",taskVO);
	}
	
	public int taskNmCompare(TaskVO taskVO) throws Exception{
		 return (int) select("taskDAO.taskNmCompare",taskVO);
	}
	
	public void insertRelatedTask(TaskVO taskVO) throws Exception{
		 insert("taskDAO.insertRelatedTask",taskVO);
	}
	
	public void deleteRelatedTask(TaskVO taskVO) throws Exception{
		 delete("taskDAO.deleteRelatedTask",taskVO);
	}
	
	public List<?> selectRelatedTaskList(TaskVO taskVO) throws Exception{
		 return list("taskDAO.selectRelatedTaskList",taskVO);
	}
	
	public void insertTaskEnv(TaskEnvVO taskEnvVO) throws Exception{
		 insert("taskDAO.insertTaskEnv",taskEnvVO);
	}
	
	public void updateTaskEnv(TaskEnvVO taskEnvVO) throws Exception{
		 update("taskDAO.updateTaskEnv",taskEnvVO);
	}
	
	public EgovMap selectTaskEnv(TaskEnvVO taskEnvVO) throws Exception{
		 return (EgovMap) select("taskDAO.selectTaskEnv",taskEnvVO);
	}
	
	public void insertTaskSubEnv(TaskEnvVO taskEnvVO) throws Exception{
		 insert("taskDAO.insertTaskSubEnv",taskEnvVO);
	}
	
	public void deleteTaskSubEnv(TaskEnvVO taskEnvVO) throws Exception{
		 delete("taskDAO.deleteTaskSubEnv",taskEnvVO);
	}
	
	public List<?> selectTaskSubEnvList(TaskEnvVO taskEnvVO) throws Exception{
		 return list("taskDAO.selectTaskSubEnvList",taskEnvVO);
	}
	
	public List<?> selectTaskStepTemplate(TaskVO taskVO) throws Exception{
		 return list("taskDAO.selectTaskStepTemplate",taskVO);
	}
	
	public List<?> selectTaskStep(TaskVO taskVO) throws Exception{
		 return list("taskDAO.selectTaskStep",taskVO);
	}
	
	public void insertTaskStep(TaskStepVO taskStepVO) throws Exception{
		 insert("taskDAO.insertTaskStep",taskStepVO);
	}
	
	public void updateTaskStep(TaskStepVO taskStepVO) throws Exception{
		 update("taskDAO.updateTaskStep",taskStepVO);
	}
	
	public void updateTaskStepId(TaskStepVO taskStepVO) throws Exception{
		 update("taskDAO.updateTaskStepId",taskStepVO);
	}
	
	public List<?> selectTaskSchduleList(TaskVO taskVO) throws Exception{
		 return list("taskDAO.selectTaskSchduleList",taskVO);
	}
	
	public List<?> selectUserList_task(TaskVO taskVO) throws Exception{
		 return list("taskDAO.selectUserList_task",taskVO);
	}
	
	public List<?> selectTaskPersonList(TaskVO taskVO) throws Exception{
		 return list("taskDAO.selectTaskPersonList",taskVO);
	}
	
	public void insertTaskPerson(TaskPersonVO taskPersonVO) throws Exception{
		 insert("taskDAO.insertTaskPerson",taskPersonVO);
	}
	
	public void updateTaskPerson(TaskPersonVO taskPersonVO) throws Exception{
		 update("taskDAO.updateTaskPerson",taskPersonVO);
	}
	
	public List<?> selectCompanyList(TaskVO taskVO) throws Exception{
		 return list("taskDAO.selectCompanyList",taskVO);
	}
	
	public List<?> selectCompanyManagerList(TaskOrgztPerVO taskOrgztPerVO) throws Exception{
		 return list("taskDAO.selectCompanyManagerList",taskOrgztPerVO);
	}
	
	public List<?> selectTbOrgztPersonList(TaskOrgztPerVO taskOrgztPerVO) throws Exception{
		 return list("taskDAO.selectTbOrgztPersonList",taskOrgztPerVO);
	}
	
	public void insertTaskDeptMnger(TaskOrgztPerVO taskOrgztPerVO) throws Exception{
		 insert("taskDAO.insertTaskDeptMnger",taskOrgztPerVO);
	}
	
	public void updateTaskDeptMnger(TaskOrgztPerVO taskOrgztPerVO) throws Exception{
		 update("taskDAO.updateTaskDeptMnger",taskOrgztPerVO);
	}
	
	public List<?> selectMajorSchedule() throws Exception{
		 return list("taskDAO.selectMajorSchedule");
	}
	
	public List<?> selectDelaySchdulList(TaskVO taskVO) throws Exception{
		 return list("taskDAO.selectDelaySchdulList",taskVO);
	}
	
	public void updateSchdulPrgrSttusAndTime(IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception{
		 update("taskDAO.updateSchdulPrgrSttusAndTime",indvdlSchdulManageVO);
	}
	
	public EgovMap selectParticiEmplNo(TaskVO taskVO) throws Exception{
		return (EgovMap) select("taskDAO.selectParticiEmplNo",taskVO);
	}
	
	public List<?> selectTaskSetpMaster() throws Exception{
		 return list("taskDAO.selectTaskSetpMaster");
	}
	
	
}
