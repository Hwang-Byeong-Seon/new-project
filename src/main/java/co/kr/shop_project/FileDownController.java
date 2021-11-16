package co.kr.shop_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FileDownController {
	
    @RequestMapping(value = "fileDownload.do")
    public void fileDownload1(String fileName, HttpServletRequest request,HttpServletResponse response) 
    		throws IOException, ServletException, NamingException {
        //String path =  request.getSession().getServletContext().getRealPath("저장경로");
         
        String filename =request.getParameter("fileName");
        System.out.println(filename);
        String realFilename="";
         
        try {
            String browser = request.getHeader("User-Agent"); 
            //파일 인코딩 
            if (browser.contains("MSIE") || browser.contains("Trident")
                    || browser.contains("Chrome")) {
                filename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+",
                        "%20");
            } else {
                filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException ex) {
            System.out.println("UnsupportedEncodingException");
        }
        realFilename = "c:\\upload\\" + filename;
        System.out.println(realFilename);
        File file1 = new File(realFilename);
        if (!file1.exists()) {
            return ;
        }
         
        // 파일명 지정        
        response.setContentType("application/octer-stream");
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(realFilename);
 
            int ncount = 0;
            byte[] bytes = new byte[512];
 
            while ((ncount = fis.read(bytes)) != -1 ) {
                os.write(bytes, 0, ncount);
            }
            fis.close();
            os.close();
        } catch (Exception e) {
            System.out.println("FileNotFoundException : " + e);
        }
        
    }
    @RequestMapping(value = "fileDownload2.do")
    public void fileDownload2(String fileName2, HttpServletRequest request,HttpServletResponse response) 
    		throws IOException, ServletException, NamingException {
        //String path =  request.getSession().getServletContext().getRealPath("저장경로");
         
        String filename2 =request.getParameter("fileName2");
        System.out.println(filename2);
        String realFilename2="";
         
        try {
            String browser2 = request.getHeader("User-Agent"); 
            //파일 인코딩 
            if (browser2.contains("MSIE2") || browser2.contains("Trident2")
                    || browser2.contains("Chrome2")) {
                filename2 = URLEncoder.encode(filename2, "UTF-8").replaceAll("\\+",
                        "%20");
            } else {
                filename2 = new String(filename2.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException ex) {
            System.out.println("UnsupportedEncodingException");
        }
        realFilename2 = "c:\\upload\\" + filename2;
        System.out.println(realFilename2);
        File file2 = new File(realFilename2);
        if (!file2.exists()) {
            return ;
        }
         
        // 파일명 지정        
        response.setContentType("application/octer-stream");
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename2 + "\"");
        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(realFilename2);
 
            int ncount = 0;
            byte[] bytes = new byte[512];
 
            while ((ncount = fis.read(bytes)) != -1 ) {
                os.write(bytes, 0, ncount);
            }
            fis.close();
            os.close();
        } catch (Exception e) {
            System.out.println("FileNotFoundException : " + e);
        }
        
    }
    @RequestMapping(value = "fileDownload3.do")
    public void fileDownload3(String fileName3, HttpServletRequest request,HttpServletResponse response) 
    		throws IOException, ServletException, NamingException {
        //String path =  request.getSession().getServletContext().getRealPath("저장경로");
         
        String filename3 =request.getParameter("fileName3");
        System.out.println(filename3);
        String realFilename3="";
         
        try {
            String browser3 = request.getHeader("User-Agent"); 
            //파일 인코딩 
            if (browser3.contains("MSIE3") || browser3.contains("Trident3")
                    || browser3.contains("Chrome3")) {
                filename3 = URLEncoder.encode(filename3, "UTF-8").replaceAll("\\+",
                        "%20");
            } else {
                filename3 = new String(filename3.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException ex) {
            System.out.println("UnsupportedEncodingException");
        }
        realFilename3 = "c:\\upload\\" + filename3;
        System.out.println(realFilename3);
        File file1 = new File(realFilename3);
        if (!file1.exists()) {
            return ;
        }
         
        // 파일명 지정        
        response.setContentType("application/octer-stream");
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename3 + "\"");
        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(realFilename3);
 
            int ncount = 0;
            byte[] bytes = new byte[512];
 
            while ((ncount = fis.read(bytes)) != -1 ) {
                os.write(bytes, 0, ncount);
            }
            fis.close();
            os.close();
        } catch (Exception e) {
            System.out.println("FileNotFoundException : " + e);
        }
        
    }
}