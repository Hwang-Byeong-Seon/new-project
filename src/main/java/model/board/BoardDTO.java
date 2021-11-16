package model.board;
import java.util.Date;

public class BoardDTO {
	//전역변수=필드=프로퍼티=property
	private int num_sh;        //글번호
	private String writer_sh;  //글쓴이
	private String subject_sh; //글제목
	private String pw_sh;      //암호
	
	private Date regdate_sh;   //등록일
	private int readcount_sh;  //조회수
	
	private int ref_sh;        //날짜, 답글 그룹
	private int re_step_sh;    //글 순서 처리
	private int re_level_sh;   //답글 들여쓰기
	
	private String content_sh; //글내용
	private String ip_sh;      //클라이언트IP
	
	public BoardDTO() {}//디폴트 생성자

	public int getNum_sh() {
		return num_sh;
	}

	public void setNum_sh(int num_sh) {
		this.num_sh = num_sh;
	}

	public String getWriter_sh() {
		return writer_sh;
	}

	public void setWriter_sh(String writer_sh) {
		this.writer_sh = writer_sh;
	}

	public String getSubject_sh() {
		return subject_sh;
	}

	public void setSubject_sh(String subject_sh) {
		this.subject_sh = subject_sh;
	}

	public String getPw_sh() {
		return pw_sh;
	}

	public void setPw_sh(String pw_sh) {
		this.pw_sh = pw_sh;
	}

	public Date getRegdate_sh() {
		return regdate_sh;
	}

	public void setRegdate_sh(Date regdate_sh) {
		this.regdate_sh = regdate_sh;
	}

	public int getReadcount_sh() {
		return readcount_sh;
	}

	public void setReadcount_sh(int readcount_sh) {
		this.readcount_sh = readcount_sh;
	}

	public int getRef_sh() {
		return ref_sh;
	}

	public void setRef_sh(int ref_sh) {
		this.ref_sh = ref_sh;
	}

	public int getRe_step_sh() {
		return re_step_sh;
	}

	public void setRe_step_sh(int re_step_sh) {
		this.re_step_sh = re_step_sh;
	}

	public int getRe_level_sh() {
		return re_level_sh;
	}

	public void setRe_level_sh(int re_level_sh) {
		this.re_level_sh = re_level_sh;
	}

	public String getContent_sh() {
		return content_sh;
	}

	public void setContent_sh(String content_sh) {
		this.content_sh = content_sh;
	}

	public String getIp_sh() {
		return ip_sh;
	}

	public void setIp_sh(String ip_sh) {
		this.ip_sh = ip_sh;
	}
	
}
