package co.kr.shop_project;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.SqlSession;//Mybatis 사용 
import java.util.*;//HashMap 사용

import model.board.MemDTO;


import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MemberController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping("main.do")
	public String mm() {
		
		//return "main";//views/main
		return ".main.layout";//뷰 리턴
	}
	
	//회원가입 폼 
	@RequestMapping("mem_insertForm.do")
	public String insertForm() {
		//return "/member/insertForm";//뷰 리턴
		return ".main.member.mem_insertForm";
	}
	
	//id중복체크 
	@RequestMapping(value = "confirmID.do" , method = RequestMethod.POST )
	public String check_id(String mem_id,Model model) {
		int check=-1;
		
	
		MemDTO mdto=sqlSession.selectOne("mem_shop.member_select",mem_id);
		
		if(mdto==null) {
			check=1;//사용 가능 
		}else {
			check=-1;//사용 중
		}
		System.out.println("check:"+check);
		model.addAttribute("check",check);
		return "member/confirmID";//뷰 리턴 confirmID.jsp ajax기 떄문에 그대로 둔다
	}

	//회원가입 
	@RequestMapping(value = "mem_insertPro.do" , method = RequestMethod.POST)
	public String insertPro(@ModelAttribute("MemDTO")MemDTO memDTO ,
			HttpServletRequest request) throws IOException, NamingException{
		
		String mem_addr=request.getParameter("mem_addr");
		
		String mem_em1=request.getParameter("mem_em1");
		String mem_em2=request.getParameter("mem_em2");
		String mem_em=mem_em1+mem_em2;
		memDTO.setMem_em(mem_em);
		
		String mem_tel1=request.getParameter("mem_tel1");
		String mem_tel2=request.getParameter("mem_tel2");
		String mem_tel3=request.getParameter("mem_tel3");
		String mem_tel=mem_tel1+mem_tel2+mem_tel3;
	
		memDTO.setMem_tel(mem_tel);
		
	
		memDTO.setMem_tel(mem_tel);
		
		sqlSession.insert("mem_shop.insertMember",memDTO);//회원가입
	
		//return "main";//뷰리턴
		return ".main.layout";//뷰리턴 ,,,메인화면 header.jsp/side.jsp 로그인 넣어주기
		//return "member/login";
		
	}
	
	
	
	@RequestMapping("mem_loginForm.do")
	public String loginForm() {
		return ".main.member.mem_loginForm";//뷰리턴//로그인 폼
		
	}
	
	@RequestMapping(value = "mem_loginPro.do" , method = RequestMethod.POST)
	public String loginPro(String mem_id,String mem_pw ,Model model) {
		HashMap<String, String> map =new HashMap<String, String>();
		
		map.put("mem_id", mem_id);
		map.put("mem_pw", mem_pw);
		
		MemDTO mdto = sqlSession.selectOne("mem_shop.loginMember",map);
		
		if(mdto == null) {
			model.addAttribute("msg","로그인 실패");
			
			return ".main.member.mem_loginForm";
		}
		
		model.addAttribute("mdto", mdto);
		
		return ".main.member.mem_loginSuccess";//뷰 리턴
	}
	
	@RequestMapping("mem_logOut.do")
	public String logOut() {
		
		return ".main.member.mem_logOut";//뷰리턴
	}
	
	//내 정보 수정 폼
	@RequestMapping(value = "mem_UpdateForm.do" , method = RequestMethod.POST)
	public String mem_updateForm(String mem_id ,Model model)
	{
		MemDTO mdto = sqlSession.selectOne("mem_shop.member_select",mem_id);
		
		//ppp@daum.net
		String mem_em = mdto.getMem_em();
		int idx1 = mem_em.indexOf("@");
		String mem_em1=mem_em.substring(0,idx1);//ppp
		String mem_em2=mem_em.substring(idx1);//@daum.net
		
		
		model.addAttribute("email1",mem_em1);
		model.addAttribute("email2",mem_em2);
		
		//01023231313
		//012345678910
		String mem_tel = mdto.getMem_tel();
		
		String mem_tel1 = mem_tel.substring(0,3); //4위치는 end 직전 자리 010 
		String mem_tel2=mem_tel.substring(3,7);//2323
		String mem_tel3=mem_tel.substring(7);//1313
		
		model.addAttribute("tel1",mem_tel1);
		model.addAttribute("tel2",mem_tel2);
		model.addAttribute("tel3",mem_tel3);
		
		model.addAttribute("mdto",mdto);
		return ".main.member.mem_updateForm";//뷰리턴
		
	}
	//DB 내정보 수정 
	@RequestMapping(value = "mem_UpdatePro.do" ,method = RequestMethod.POST)
	public String mem_updatePro(@ModelAttribute("memDTO")MemDTO memDTO
			, HttpServletRequest request) throws IOException,NamingException{
		
		
		String mem_em1 = request.getParameter("mem_em1");
		String mem_em2 = request.getParameter("mem_em2");
		String mem_em = mem_em1+mem_em2;
		memDTO.setMem_em(mem_em);
		
		String mem_tel1 =request.getParameter("mem_tel1");
		String mem_tel2 =request.getParameter("mem_tel2");
		String mem_tel3 =request.getParameter("mem_tel3");
		String mem_tel = mem_tel1 + mem_tel2 + mem_tel3;
	
		memDTO.setMem_tel(mem_tel);
		
		sqlSession.update("mem_shop.mem_update",memDTO);
		

		//return "main";//뷰리턴
		//return ".main.layout";//뷰리턴
		return "redirect:list_sh.do";
		
	}
	
	@RequestMapping("mem_delete.do")
	public String delete(String mem_id) {
		sqlSession.delete("mem_shop.member_delete",mem_id);
		//return "main";
		//return ".main.layout";
		return "redirect:list_sh.do";
		
	}
	
	
	
}//class-end
