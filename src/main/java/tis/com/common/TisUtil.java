package tis.com.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;


public class TisUtil {
	
	public static boolean isNumeric(String s) {
		try {
			String number = s.replace(",", "");
			Double.parseDouble(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * [sample] 
	 * curl --data "Hello from Slackbot" $'https://ieetu-ws.slack.com/services/hooks/slackbot?token=nV4AKLpq5gETHU9TTYKeQWne&channel=@genesu'
	 * 
	 * https://slack.com/api/chat.postMessage?token=xoxp-329834363111-329834363287-336002077717-bf75391c08094a7565e517ab36acd89a&channel=@jmj1116&text=메신저 테스트
	 */
	public static void postURLforSlack(String msg, String channel) throws IOException{
		try{
			
			String url = "https://slack.com/api/chat.postMessage";
			//String url = "https://ieetu-ws.slack.com/services/hooks/slackbot"; // 해당 URL은 한글 깨지는 이슈가 있음.
			String token = "xoxp-329834363111-329834363287-336002077717-bf75391c08094a7565e517ab36acd89a";
			String username = "IEETU - PMS 관리자";

			String param = "?token=" + URLEncoder.encode(token, "UTF-8") 
					+ "&channel=" + URLEncoder.encode("@" + channel, "UTF-8") 
					+ "&text=" + URLEncoder.encode(msg, "UTF-8")
					+ "&username=" + URLEncoder.encode(username, "UTF-8");
		
			URL postUrl = new URL(url+param);
			URLConnection conn = postUrl.openConnection();
		
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Method", "POST");		
			
			DataOutputStream out = null;
			
			try{
				out = new DataOutputStream(conn.getOutputStream());
				//out.writeBytes(param);
				//out.writeBytes(URLEncoder.encode(msg, "UTF-8"));
				out.flush();
			}finally{
				if(out != null) out.close();
			}
			
			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);
			
			int line = 1;
			while(scan.hasNext()){
				String str = scan.nextLine();
				System.out.println((line++) + ":" + str);
			}
			scan.close();
			
		} catch (MalformedURLException e){
			System.out.println("The URL address is incorrect");
			e.printStackTrace();
		} catch(IOException e){
			System.out.println("It can't connect to the web page");
			e.printStackTrace();
		}
	}
	
}
