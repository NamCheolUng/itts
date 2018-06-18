package tis.com.task.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import tis.com.task.service.TaskEnvVO;
import tis.com.task.service.TaskOrgztPerVO;
import tis.com.task.service.TaskPersonVO;
import tis.com.task.service.TaskService;
import tis.com.task.service.TaskStepVO;
import tis.com.task.service.TaskVO;
import egovframework.com.cop.smt.sim.service.IndvdlSchdulManageVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("taskService")
public class TaskServiceImpl implements TaskService{
	
	@Resource(name="taskDAO")
	private TaskDAO taskDAO;
	
	@Resource(name="tisTaskIdGnrService")
	private EgovIdGnrService  tisTaskIdGnrService;
	
	@Resource(name="tisTaskPerIdGnrService")
	private EgovIdGnrService  tisTaskPerIdGnrService;
	
	@Resource(name="tisTaskDeptMngerGnrService")
	private EgovIdGnrService  tisTaskDeptMngerGnrService;

	@Override
	public List<?> selectTaskList(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectTaskList(taskVO);
	}

	@Override
	public int selectTaskListCnt(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectTaskListCnt(taskVO);
	}
	
	@Override
	public void insertTask(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		taskVO.setTaskId(tisTaskIdGnrService.getNextStringId());
		if(taskVO.getRelaTaskId() != null && !taskVO.getRelaTaskId().isEmpty()){
			String []tmp = taskVO.getRelaTaskId().split("//");
			for(int i=0;i<tmp.length;i++){
				if(tmp[i].isEmpty()){
					continue;
				}
				taskVO.setRelaTaskId(tmp[i]);
				taskDAO.insertRelatedTask(taskVO);
			}
		}
		
		taskDAO.insertTask(taskVO);
	}

	@Override
	public void deleteTask(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		taskDAO.deleteTask(taskVO);
	}

	@Override
	public void updateTask(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		taskDAO.deleteRelatedTask(taskVO);
		if(taskVO.getRelaTaskId() != null && !taskVO.getRelaTaskId().isEmpty()){			
			String []tmp = taskVO.getRelaTaskId().split("//");
			for(int i=0;i<tmp.length;i++){
				if(tmp[i].isEmpty()){
					continue;
				}
				taskVO.setRelaTaskId(tmp[i]);
				taskDAO.insertRelatedTask(taskVO);
			}
		}
		taskDAO.updateTask(taskVO);
	}

	@Override
	public EgovMap selectTaskDetail(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectTaskDetail(taskVO);
	}

	@Override
	public int taskNmCompare(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.taskNmCompare(taskVO);
	}

	@Override
	public List<?> selectRelatedTaskList(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectRelatedTaskList(taskVO);
	}

	@Override
	public void insertTaskEnv(TaskEnvVO taskEnvVO) throws Exception {
		// TODO Auto-generated method stub
		taskDAO.insertTaskEnv(taskEnvVO);
	}

	@Override
	public void updateTaskEnv(TaskEnvVO taskEnvVO) throws Exception {
		// TODO Auto-generated method stub
		taskDAO.updateTaskEnv(taskEnvVO);
	}

	@Override
	public EgovMap selectTaskEnv(TaskEnvVO taskEnvVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectTaskEnv(taskEnvVO);
	}

	@Override
	public void insertTaskSubEnv(TaskEnvVO taskEnvVO) throws Exception {
		// TODO Auto-generated method stub
		taskDAO.insertTaskSubEnv(taskEnvVO);
	}


	@Override
	public void deleteTaskSubEnv(TaskEnvVO taskEnvVO) throws Exception {
		// TODO Auto-generated method stub
		taskDAO.deleteTaskSubEnv(taskEnvVO);
	}

	@Override
	public List<?> selectTaskSubEnvList(TaskEnvVO taskEnvVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectTaskSubEnvList(taskEnvVO);
	}

	@Override
	public List<?> selectTaskStepTemplate(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectTaskStepTemplate(taskVO);
	}

	@Override
	public void insertTaskStep(TaskStepVO taskStepVO) throws Exception {
		// TODO Auto-generated method stub
		//String id = tisTaskStepIdGnrService.getNextStringId();
		for(int i=0;i<taskStepVO.getTaskStepVOList().size();i++){
			TaskStepVO tmp = taskStepVO.getTaskStepVOList().get(i);
			tmp.setTaskStepId(taskStepVO.getTaskStepId());
			tmp.setTaskId(taskStepVO.getTaskId());
			tmp.setPlanSdt(tmp.getPlanSdt()+" 09:00");
			tmp.setPlanEdt(tmp.getPlanEdt()+" 18:00");
			taskDAO.insertTaskStep(tmp);			
		}
		//taskStepVO.setTaskStepId(id);
		taskDAO.updateTaskStepId(taskStepVO);	
	}

	@Override
	public void updateTaskStep(TaskStepVO taskStepVO) throws Exception {
		// TODO Auto-generated method stub
		for(int i=0;i<taskStepVO.getTaskStepVOList().size();i++){
			TaskStepVO tmp = taskStepVO.getTaskStepVOList().get(i);
			tmp.setTaskStepId(taskStepVO.getTaskStepId());
			tmp.setTaskId(taskStepVO.getTaskId());
			tmp.setPlanSdt(tmp.getPlanSdt()+" 09:00");
			tmp.setPlanEdt(tmp.getPlanEdt()+" 18:00");
			taskDAO.updateTaskStep(tmp);	
		}
		
	}

	@Override
	public List<?> selectTaskStep(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectTaskStep(taskVO);
	}

	@Override
	public List<?> selectTaskSchduleList(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectTaskSchduleList(taskVO);
	}

	@Override
	public List<?> selectUserList_task(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectUserList_task(taskVO);
	}

	@Override
	public List<?> selectTaskPersonList(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectTaskPersonList(taskVO);
	}

	@Override
	public void taskPersonSave(TaskPersonVO taskPersonVO) throws Exception {
		// TODO Auto-generated method stub
		for(int i=0;i<taskPersonVO.getTaskPersonVOList().size();i++){
			TaskPersonVO tmp  =  taskPersonVO.getTaskPersonVOList().get(i);
			if(tmp.getEmplNo() != null && !tmp.getEmplNo().isEmpty()){
				tmp.setTaskId(taskPersonVO.getTaskId());				
				if(tmp.getTaskPerId()!= null && !tmp.getTaskPerId().isEmpty()){
					taskDAO.updateTaskPerson(tmp);
				}else{
					tmp.setTaskPerId(tisTaskPerIdGnrService.getNextStringId());
					taskDAO.insertTaskPerson(tmp);
				}
			}
		}
	}

	@Override
	public List<?> selectCompanyList(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectCompanyList(taskVO);
	}

	@Override
	public List<?> selectCompanyManagerList(TaskOrgztPerVO taskOrgztPerVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectCompanyManagerList(taskOrgztPerVO);
	}

	@Override
	public List<?> selectTbOrgztPersonList(TaskOrgztPerVO taskOrgztPerVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectTbOrgztPersonList(taskOrgztPerVO);
	}

	@Override
	public void taskDeptMngerSave(TaskOrgztPerVO taskOrgztPerVO) throws Exception {
		// TODO Auto-generated method stub
		for(int i=0;i<taskOrgztPerVO.getTaskOrgztPerVOList().size();i++){
			TaskOrgztPerVO tmp = taskOrgztPerVO.getTaskOrgztPerVOList().get(i);
			if(tmp.getNcrdId() != null && !tmp.getNcrdId().isEmpty()){
				tmp.setTaskId(taskOrgztPerVO.getTaskId());			
				tmp.setRegstr(taskOrgztPerVO.getRegstr());
				tmp.setSept(taskOrgztPerVO.getSept());
				if(tmp.getOrgztPerId() != null && !tmp.getOrgztPerId().isEmpty()){
					taskDAO.updateTaskDeptMnger(tmp);
				}else{
					tmp.setOrgztPerId(tisTaskDeptMngerGnrService.getNextStringId());
					taskDAO.insertTaskDeptMnger(tmp);
				}
			}
		}
	}

	@Override
	public List<?> selectDelaySchdulList(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectDelaySchdulList(taskVO);
	}

	@Override
	public void updateSchdulPrgrSttusAndTime(
			IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception {
		// TODO Auto-generated method stub
		taskDAO.updateSchdulPrgrSttusAndTime(indvdlSchdulManageVO);
	}

	@Override
	public EgovMap selectParticiEmplNo(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectParticiEmplNo(taskVO);
	}

	@Override
	public List<?> selectTaskSetpMaster() throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.selectTaskSetpMaster();
	}


}
