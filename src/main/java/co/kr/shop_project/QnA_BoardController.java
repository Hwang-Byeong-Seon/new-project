package co.kr.shop_project;
import java.text.DateFormat;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.SqlSession;//MyBatis 사용
import java.util.*;//HashMap 사용

import model.board.MemDTO;
import model.board.QnaDTO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import javax.naming.NamingException;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class QnA_BoardController {
	
	@Autowired
	private SqlSession sqlSession;//setter 작업 자동 
	
	//글쓰기 폼, 답글쓰기
		@RequestMapping("qna_writeForm.do")
		public String qna_writeF(Model model,String q_num,String q_ref,String q_re_step,String q_re_level,String pageNum) {
			if(q_num==null) {//원글 쓰기
				q_num="0";//글번호
				q_ref="1";//글 그룹
				q_re_step="0";//글순서
				q_re_level="0";//글 깊이
			}
			
			model.addAttribute("pageNum",pageNum);
			model.addAttribute("q_num",new Integer(q_num));
			model.addAttribute("q_ref",new Integer(q_ref));
			model.addAttribute("q_re_step",new Integer(q_re_step));
			model.addAttribute("q_re_level",new Integer(q_re_level));
			
			//return "board/writeForm";//뷰리턴
			return ".main.qna_board.qna_writeForm"; //뷰리턴
		}
		
		//DB글쓰기 ,답글쓰기
		@RequestMapping(value="qna_writePro.do",method=RequestMethod.POST)
		public String qna_writePro(@ModelAttribute("QnaDTO") QnaDTO qnaDTO, HttpServletRequest request,Model model)
		throws IOException,NamingException{
			
			int maxNum=0;//최대 글번호 넣을 변수 선언
			if(sqlSession.selectOne("qna_board.numMax") != null) {
				//최대글번호가 있으면
				maxNum=sqlSession.selectOne("qna_board.numMax");
			}
			
			if(maxNum != 0) {//최대 글번호가 0이 아니면, 글이 존재하면
				maxNum=maxNum+1;//ref그룹이 넣으려고
			}else {//처음 글이면
				maxNum=1;//ref그룹에 1을 넣으려고
			}
			
			String ip=request.getRemoteAddr();//ip구한다
			qnaDTO.setQ_ip(ip);
			
			if(qnaDTO.getQ_num() !=0 ) {//답글이면
				//답글 위치 확보
				sqlSession.update("qna_board.reStep",qnaDTO);
				qnaDTO.setQ_re_step(qnaDTO.getQ_re_step()+1);//글순서+1
				qnaDTO.setQ_re_level(qnaDTO.getQ_re_level()+1);//글 깊이+1
				
			}else {//원글이면
				qnaDTO.setQ_ref(new Integer(maxNum));//글그룹
				qnaDTO.setQ_re_step(new Integer(0));//글순서
				qnaDTO.setQ_re_level(new Integer(0));//글 깊이 
			}
			
			
			//비밀글 설정
			String secret = request.getParameter("secret");
			
			if(secret != null) {
				qnaDTO.setQ_secret(new Boolean(true));
			}
			else if(secret == null) {
				qnaDTO.setQ_secret(new Boolean(false));
			}
			System.out.println("secret : " + secret);
			model.addAttribute("secret",secret);
			
			
			
			sqlSession.insert("qna_board.insertDao",qnaDTO);//글쓰기,답글처리
			
			return "redirect:qna_list.do";//response.sendRedirect("list.jsp")와 같다
		}
		
	
	@RequestMapping("qna_list.do")
	public String qna_listboard(Model model, String pageNum) {
		if(pageNum==null) {
			pageNum="1";
		}
		
		int pageSize=10;//1 페이지에 10개씩
		int currentPage=Integer.parseInt(pageNum);//현재 페이지 
		
		int startRow=(currentPage-1)*pageSize+1;//페이지의 첫번째 행(row)을 구한다 
		int endRow=currentPage*pageSize;//페이지의 마지막 행(row)을 구한다 
		
		int count=0;//총 글개수 넣으려고 
		int pageBlock=10;//1블럭당 10개 페이지로 처리 하려고 
		
        count=sqlSession.selectOne("qna_board.countDao");//총 글갯수 얻기 
        
        int number=count-(currentPage-1)*pageSize;//글번호 역순으로 하려고 
        
        Map<String,Integer> map=new HashMap<String, Integer>();
        map.put("start", startRow-1);//시작위치, mysql은 0(행=row)부터 시작
        map.put("cnt", pageSize);
        
        int pageCount=count/pageSize+(count%pageSize==0?0:1);//총페이지 구하기
        //                             꽁다리 row갯수 구하기
        
        int startPage=(currentPage/pageBlock)*10+1;//시작페이지
        int endPage=startPage+pageBlock-1;//end 페이지
        
        
        List<QnaDTO> list=sqlSession.selectList("qna_board.listDao",map);//////////
        
        
        /*
        /////////////////////////////////////////////////////////////////
        MemDTO mdto=sqlSession.selectOne("mem_shop.member_select",mem_id);
        
        String id2 = mdto.getMem_id();
		String pw2 = mdto.getMem_pw();
		
		model.addAttribute("id",id2);
		model.addAttribute("pw",pw2);
        ////////////////////////////////////////////////////////////////////
         * */
         
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("currentPage",currentPage);
        
        model.addAttribute("startRow",startRow);
        model.addAttribute("endRow",endRow);
        
        model.addAttribute("pageBlock",pageBlock);
        model.addAttribute("pageCount",pageCount);
        
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        
        model.addAttribute("count",count);
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("number",number);
        
        model.addAttribute("list",list);
		//return "board/list";//뷰리턴
        
        
        return ".main.qna_board.qna_list"; //뷰리턴
	}
	
	
	
	
	//글내용 보기
		@RequestMapping("qna_content.do")
		public String qna_content(Model model,String q_num,String pageNum,String mem_id) {
			
			int num1=Integer.parseInt(q_num);
			sqlSession.update("qna_board.readcountDao",num1);//조회수 증가
			
			QnaDTO bdto=sqlSession.selectOne("qna_board.getBoard",num1);
			MemDTO mdto=sqlSession.selectOne("mem_shop.member_select",mem_id);
			 
			String content=bdto.getQ_content();
			content=content.replace("\n", "<br>");
			
			String id2 = mdto.getMem_id();
			String pw2 = mdto.getMem_pw();
			
			model.addAttribute("pageNum",pageNum);
			model.addAttribute("q_num",num1);
			model.addAttribute("q_content",content);
			model.addAttribute("dto",bdto);
			
			model.addAttribute("id",id2);
			model.addAttribute("pw",pw2);
			//return "board/content";//뷰 리턴
			return ".main.qna_board.qna_content"; //뷰리턴
		}
		
		
		@RequestMapping("qna_updateForm.do")
		public ModelAndView updateForm(String q_num,String pageNum) {
			int num1=Integer.parseInt(q_num);
			QnaDTO bdto=sqlSession.selectOne("qna_board.getBoard",num1);
			
			ModelAndView mv = new ModelAndView();
			mv.addObject("pageNum",pageNum);
			mv.addObject("dto",bdto);
			mv.setViewName(".main.qna_board.qna_updateForm"); //뷰리턴");
			
			return mv;
		}
		
		
	//DB글 수정
	
	@RequestMapping(value="qna_updatePro.do",method=RequestMethod.POST)
	public ModelAndView qna_updatePro(QnaDTO qnaDTO,String pageNum) {
		sqlSession.update("qna_board.updateDao",qnaDTO);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageNum",pageNum);
		mv.setViewName("redirect:qna_list.do");
		
		return mv;
	}
	//글 삭제
	@RequestMapping("qna_delete.do")
	public String qna_delete(Model model,String q_num,String pageNum) {
		
		sqlSession.delete("qna_board.deleteDao",new Integer(q_num)); //글 삭제
		model.addAttribute("pageNum",pageNum);
		return "redirect:qna_list.do";
	}
	
}
