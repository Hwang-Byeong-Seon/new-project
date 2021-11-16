package model.board;

import java.util.Date;

public class QnaDTO {
	private int q_num;
	private String q_writer;  //글쓴이
	private String q_subject; //글제목
	private String q_pw;      //암호
	
	private Date q_regdate;   //등록일
	private int q_readcount;  //조회수
	
	private int q_ref;        //날짜, 답글 그룹
	private int q_re_step;    //글 순서 처리
	private int q_re_level;   //답글 들여쓰기
	
	private String q_content; //글내용
	private String q_ip;      //클라이언트IP
	private boolean q_secret;
	
	

	public QnaDTO() {}

	public int getQ_num() {
		return q_num;
	}

	public void setQ_num(int q_num) {
		this.q_num = q_num;
	}

	public String getQ_writer() {
		return q_writer;
	}

	public void setQ_writer(String q_writer) {
		this.q_writer = q_writer;
	}

	public String getQ_subject() {
		return q_subject;
	}

	public void setQ_subject(String q_subject) {
		this.q_subject = q_subject;
	}

	public String getQ_pw() {
		return q_pw;
	}

	public void setQ_pw(String q_pw) {
		this.q_pw = q_pw;
	}

	public Date getQ_regdate() {
		return q_regdate;
	}

	public void setQ_regdate(Date q_regdate) {
		this.q_regdate = q_regdate;
	}

	public int getQ_readcount() {
		return q_readcount;
	}

	public void setQ_readcount(int q_readcount) {
		this.q_readcount = q_readcount;
	}

	public int getQ_ref() {
		return q_ref;
	}

	public void setQ_ref(int q_ref) {
		this.q_ref = q_ref;
	}

	public int getQ_re_step() {
		return q_re_step;
	}

	public void setQ_re_step(int q_re_step) {
		this.q_re_step = q_re_step;
	}

	public int getQ_re_level() {
		return q_re_level;
	}

	public void setQ_re_level(int q_re_level) {
		this.q_re_level = q_re_level;
	}

	public String getQ_content() {
		return q_content;
	}

	public void setQ_content(String q_content) {
		this.q_content = q_content;
	}

	public String getQ_ip() {
		return q_ip;
	}

	public void setQ_ip(String q_ip) {
		this.q_ip = q_ip;
	}
	
	public boolean isQ_secret() {
		return q_secret;
	}

	public void setQ_secret(boolean q_secret) {
		this.q_secret = q_secret;
	}
	

}
