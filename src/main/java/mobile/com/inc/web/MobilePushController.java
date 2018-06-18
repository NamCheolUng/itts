package mobile.com.inc.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.google.android.gcm.server.*;

@Controller
public class MobilePushController {
	@Autowired
	private JavaMailSender EMSMailSender;
	
	@RequestMapping(value = "/mobile/com/inc/pushToMobile.do")
	public void pushToMobile(HttpServletRequest request,
			@RequestParam(value="sUserId", required=false) String sUserId,
			@RequestParam(value="sMessage", required=false) String sMessage,
			
			@RequestParam(value="mailAddress", required=false) String mailAddress, 
			@RequestParam(value="mailTitle", required=false) String mailTitle,
			@RequestParam(value="mailText", required=false) String mailText ) throws Exception {
		
		
		/*메일*/
		String[] sAddress = mailAddress.split("/");
		String[] sTitle = mailTitle.split("/");
		String[] sMsg = mailText.split("/");
		
			for(int i=0; i < sAddress.length; i++){ 
			
		    String setfrom = "ieetumaster@gmail.com";         
		    String tomail  = sAddress[i];
		    String title   = sTitle[i]; 
		    String content = sMsg[i]; 
		   
		    try {
		      MimeMessage mailMessage = EMSMailSender.createMimeMessage();
		      MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
		 
		      messageHelper.setFrom(setfrom); 
		      messageHelper.setTo(tomail); 
		      messageHelper.setSubject(title);
		      messageHelper.setText(content);  
		     
		      EMSMailSender.send(mailMessage);
		    } catch(Exception e){
		    }
		  }
		
		String[] userId = sUserId.split("/");
		String[] message = sMessage.split("/");
		for(int i=0; i < userId.length; i++){
			
		    ArrayList<String> token = new ArrayList<String>();    //token값을 ArrayList에 저장
		    String MESSAGE_ID = String.valueOf(Math.random() % 100 + 1);    //메시지 고유 ID
		    boolean SHOW_ON_IDLE = false;    //옙 활성화 상태일때 보여줄것인지
		    int LIVE_TIME = 1;    //옙 비활성화 상태일때 FCM가 메시지를 유효화하는 시간
		    int RETRY = 2;    //메시지 전송실패시 재시도 횟수
		    String id = userId[i];	    
		    
		    String simpleApiKey = "AAAADlyUF8E:APA91bG8Lg528r5BVzKFpYgLTVmVfw0IMDrKn72n9mL9A0ZzdKszGuI2SiFi95OPyD8BBQe9R_YtM2aBU_EAuzmxkX4hqQlgGfxOks-B8SOb8718Jyr9yQxYTUY2MUevUboHaAyzNWEq";
		    String gcmURL = "https://android.googleapis.com/fcm/send";    
		    Connection conn = null; 
		    Statement stmt = null; 
		    ResultSet rs = null;
		    
		    String msg = message[i];
		    
		    if(msg==null || msg.equals("")){
		        msg="";
		    }
		    
		    msg = new String(msg.getBytes("UTF-8"), "UTF-8");   //메시지 한글깨짐 처리
		    
		    try {
		    	conn = DriverManager.getConnection("jdbc:mysql://118.45.180.217:3306","tis","12tis!@");
		        stmt = conn.createStatement();            
		        String sql = "select PUSH_TOKEN_ID from tis.comtnemplyrinfo where EMPL_NO='"+id+"'";
		        rs = stmt.executeQuery(sql);
		        
		        //모든 등록ID를 리스트로 묶음
		        while(rs.next()){
		            token.add(rs.getString("PUSH_TOKEN_ID"));
		        }
		        conn.close();
		        
		        Sender sender = new Sender(simpleApiKey);
		        Message pMessage = new Message.Builder()
		        .collapseKey(MESSAGE_ID)
		        .delayWhileIdle(SHOW_ON_IDLE)
		        .timeToLive(LIVE_TIME)
		        .addData("message",msg)
		        .build();
		        MulticastResult result1 = sender.send(pMessage,token,RETRY);
		        if (result1 != null) {
		            List<Result> resultList = result1.getResults();
		            for (Result result : resultList) {
		            }
		        }
		    }catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	}
}