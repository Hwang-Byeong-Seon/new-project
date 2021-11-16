package model.board;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class FaqDTO {
	
	private int num_faq;        //글번호
	private String writer_faq;  //글쓴이
	private String subject_faq; //글제목
	private String pw_faq;      //암호
	
	private Date regdate_faq;   //등록일
	private int readcount_faq;  //조회수
	
	private int ref_faq;        //날짜, 답글 그룹
	private int re_step_faq;    //글 순서 처리
	private int re_level_faq;   //답글 들여쓰기
	
	private String content_faq; //글내용
	private String ip_faq;      //클라이언트IP
	
	private String fileName;
	private String fileName2;
	private String fileName3;
	private MultipartFile uploadFile;
	private MultipartFile uploadFile2;
	private MultipartFile uploadFile3;
	
	public FaqDTO() {}//디폴트 생성자

	public int getNum_faq() {
		return num_faq;
	}

	public void setNum_faq(int num_faq) {
		this.num_faq = num_faq;
	}

	public String getWriter_faq() {
		return writer_faq;
	}

	public void setWriter_faq(String writer_faq) {
		this.writer_faq = writer_faq;
	}

	public String getSubject_faq() {
		return subject_faq;
	}

	public void setSubject_faq(String subject_faq) {
		this.subject_faq = subject_faq;
	}

	public String getPw_faq() {
		return pw_faq;
	}

	public void setPw_faq(String pw_faq) {
		this.pw_faq = pw_faq;
	}

	public Date getRegdate_faq() {
		return regdate_faq;
	}

	public void setRegdate_faq(Date regdate_faq) {
		this.regdate_faq = regdate_faq;
	}

	public int getReadcount_faq() {
		return readcount_faq;
	}

	public void setReadcount_faq(int readcount_faq) {
		this.readcount_faq = readcount_faq;
	}

	public int getRef_faq() {
		return ref_faq;
	}

	public void setRef_faq(int ref_faq) {
		this.ref_faq = ref_faq;
	}

	public int getRe_step_faq() {
		return re_step_faq;
	}

	public void setRe_step_faq(int re_step_faq) {
		this.re_step_faq = re_step_faq;
	}

	public int getRe_level_faq() {
		return re_level_faq;
	}

	public void setRe_level_faq(int re_level_faq) {
		this.re_level_faq = re_level_faq;
	}

	public String getContent_faq() {
		return content_faq;
	}

	public void setContent_faq(String content_faq) {
		this.content_faq = content_faq;
	}

	public String getIp_faq() {
		return ip_faq;
	}

	public void setIp_faq(String ip_faq) {
		this.ip_faq = ip_faq;
	}
	
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	public MultipartFile getUploadFile2() {
		return uploadFile2;
	}
	public void setUploadFile2(MultipartFile uploadFile2) {
		this.uploadFile2 = uploadFile2;
	}
	public MultipartFile getUploadFile3() {
		return uploadFile3;
	}
	public void setUploadFile3(MultipartFile uploadFile3) {
		this.uploadFile3 = uploadFile3;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName2() {
		return fileName2;
	}
	public void setFileName2(String fileName2) {
		this.fileName2 = fileName2;
	}
	public String getFileName3() {
		return fileName3;
	}
	public void setFileName3(String fileName3) {
		this.fileName3 = fileName3;
	}
}
