package egovframework.rte.fdl.excel.download;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExcelFileName {
	
	public static void setFileNmae(String fileName, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException
	{
		String userAgent = req.getHeader("User-Agent");
		
		if(userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1){//ie10 or ie11
			fileName = URLEncoder.encode(fileName, "utf-8");
		}else{
			fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
		}
		  
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		resp.setHeader("Content-Transfer-Encoding", "binary");
	}
}
