package tis.com.task.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import tis.com.task.service.impl.TaskDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("majorSchduleScheduler")
public class MajorSchduleScheduler {
	
	@Resource(name="taskDAO")
	private TaskDAO taskDAO;
	
	@Autowired
	private JavaMailSender EMSMailSender;
	
	public void majorSchduleMailPush() throws Exception{
		List<EgovMap> list =  (List<EgovMap>) taskDAO.selectMajorSchedule();		
		for(int i=0;i<list.size();i++){
			EgovMap tmp = list.get(i);
			TaskVO taskVO = new TaskVO();
			taskVO.setTaskId(tmp.get("taskId").toString());
			List<EgovMap> perList =(List<EgovMap>) taskDAO.selectTaskPersonList(taskVO);
			for(int j=0;j<perList.size();j++){
				EgovMap temp = perList.get(j);
				String mailAdres = temp.get("emailAdres").toString();
				try {
					  SimpleMailMessage sendemail = new SimpleMailMessage();
					  	//발신계정은 임의로 입력해도 됨. 
						sendemail.setTo(mailAdres);
						sendemail.setFrom("ieetumaster@gmail.com");
						sendemail.setSubject(tmp.get("userNm").toString()+" "+tmp.get("schdulNm").toString()+"시작 하루전");
						sendemail.setText(tmp.get("schdulNm").toString()+" "
									 	+	tmp.get("schdulSdt").toString()+"~"+tmp.get("schdulEdt").toString()
								);
						//test mail
						//sendemail.setFrom("");
						EMSMailSender.send(sendemail);
						
						 String simpleApiKey = "AAAADlyUF8E:APA91bG8Lg528r5BVzKFpYgLTVmVfw0IMDrKn72n9mL9A0ZzdKszGuI2SiFi95OPyD8BBQe9R_YtM2aBU_EAuzmxkX4hqQlgGfxOks-B8SOb8718Jyr9yQxYTUY2MUevUboHaAyzNWEq";
						    String gcmURL = "https://android.googleapis.com/fcm/send";    
						    String MESSAGE_ID = String.valueOf(Math.random() % 100 + 1);    //메시지 고유 ID
						    boolean SHOW_ON_IDLE = false;    //옙 활성화 상태일때 보여줄것인지
						    int LIVE_TIME = 1;    //옙 비활성화 상태일때 FCM가 메시지를 유효화하는 시간
						    int RETRY = 2;    //메시지 전송실패시 재시도 횟수
						    ArrayList<String> token = new ArrayList<String>();
						    token.add(tmp.get("pushTokenId").toString());
						 Sender sender = new Sender(simpleApiKey);
					        Message message = new Message.Builder()
					        .collapseKey(MESSAGE_ID)
					        .delayWhileIdle(SHOW_ON_IDLE)
					        .timeToLive(LIVE_TIME)
					        .addData("message",tmp.get("userNm").toString()+" "+tmp.get("schdulNm").toString()+"시작 하루전")
					        .build();
					        MulticastResult result1 = sender.send(message,token,RETRY);
					        if (result1 != null) {
					            List<Result> resultList = result1.getResults();
					            for (Result result : resultList) {
					            }
					        }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
	
		}
	}
}
