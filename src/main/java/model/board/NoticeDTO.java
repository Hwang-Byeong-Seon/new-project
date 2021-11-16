package model.board;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class NoticeDTO {
	//전역변수=필드=property
	private int num_no; //글번호
	private String writer_no; //글쓴이
	private String subject_no; //글제목
	private Date regdate_no; //날짜, 글등록일
	private int readcount_no; //조회수
	private int ref_no; //답글 그룹
	private int re_step_no; //글순서
	private int re_level_no; //답글 들여쓰기
	private String content_no; //글내용
	
	private String fileName;
	private String fileName2;
	private String fileName3;
	private MultipartFile uploadFile;
	private MultipartFile uploadFile2;
	private MultipartFile uploadFile3;
	
	public NoticeDTO() {}//디폴트 생성자

	public int getNum_no() {
		return num_no;
	}

	public void setNum_no(int num_no) {
		this.num_no = num_no;
	}

	public String getWriter_no() {
		return writer_no;
	}

	public void setWriter_no(String writer_no) {
		this.writer_no = writer_no;
	}

	public String getSubject_no() {
		return subject_no;
	}

	public void setSubject_no(String subject_no) {
		this.subject_no = subject_no;
	}

	public Date getRegdate_no() {
		return regdate_no;
	}

	public void setRegdate_no(Date regdate_no) {
		this.regdate_no = regdate_no;
	}

	public int getReadcount_no() {
		return readcount_no;
	}

	public void setReadcount_no(int readcount_no) {
		this.readcount_no = readcount_no;
	}

	public int getRef_no() {
		return ref_no;
	}

	public void setRef_no(int ref_no) {
		this.ref_no = ref_no;
	}

	public int getRe_step_no() {
		return re_step_no;
	}

	public void setRe_step_no(int re_step_no) {
		this.re_step_no = re_step_no;
	}

	public int getRe_level_no() {
		return re_level_no;
	}

	public void setRe_level_no(int re_level_no) {
		this.re_level_no = re_level_no;
	}

	public String getContent_no() {
		return content_no;
	}

	public void setContent_no(String content_no) {
		this.content_no = content_no;
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

	
}//class end
