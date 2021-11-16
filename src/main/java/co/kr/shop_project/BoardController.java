package co.kr.shop_project;
import java.text.DateFormat;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.SqlSession;//mybatis사용
import java.util.*;//HashMap사용

import model.board.BoardDTO;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import javax.naming.NamingException;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BoardController {
	
	@Autowired
	private SqlSession sqlSession;//setter 작업 자동
	
	//글쓰기 폼, 답글쓰기
	@RequestMapping("writeForm_sh.do")//글 내용보기에서 넘어온 것들이라 String으로
	public String writeF(Model model,String num_sh,String ref_sh,String re_step_sh,String re_level_sh,String pageNum_sh) {
		if(num_sh==null) {//원글쓰기
			num_sh="0";//글 번호
			ref_sh="1";//글 그룹
			re_step_sh="0";//글 순서
			re_level_sh="0";//글 깊이
		}
		
		model.addAttribute("pageNum_sh",pageNum_sh);
		model.addAttribute("num_sh",new Integer(num_sh));
		model.addAttribute("ref_sh",new Integer(ref_sh));
		model.addAttribute("re_step_sh",new Integer(re_step_sh));
		model.addAttribute("re_level_sh",new Integer(re_level_sh));
		
		//return "board/writeForm";//뷰 리턴
		return ".main.board.writeForm_sh";//뷰 리턴
	}
	
	//DB글쓰기, 답글쓰기
	@RequestMapping(value="writePro_sh.do",method=RequestMethod.POST)
	public String writePro(@ModelAttribute("boardDTO") BoardDTO boardDTO, HttpServletRequest request)
	throws IOException,NamingException{
		
		int maxNum=0;//최대 글번호 넣을 변수 선언, 지역변수라 초기화 해줘야 함
		if(sqlSession.selectOne("board.numMax") != null) {
			//최대 글번호가 있으면(글을 썼으면)
			maxNum=sqlSession.selectOne("board.numMax");
		}
		
		if(maxNum != 0) {//최대 글번호가 0이 아니면, 글이 존재하면
			maxNum=maxNum+1;//ref그룹에 넣으려고
		}else {//처음 글이면
			maxNum=1;//ref그룹에 1을 넣으려고
		}
		
		String ip=request.getRemoteAddr();//ip구하기
		boardDTO.setIp_sh(ip);
		
		if(boardDTO.getNum_sh() != 0) {//답글이면
			//답글 위치 확보
			sqlSession.update("board.reStep",boardDTO);
			boardDTO.setRe_step_sh(boardDTO.getRe_step_sh()+1);//글 순서+1
			boardDTO.setRe_level_sh(boardDTO.getRe_level_sh()+1);//글 깊이+1
			
		}else {//원글이면
			boardDTO.setRef_sh(new Integer(maxNum));//글그룹
			boardDTO.setRe_step_sh(new Integer(0));//글순서
			boardDTO.setRe_level_sh(new Integer(0));//글깊이
		}
		
		sqlSession.insert("board.insertDao",boardDTO);//글쓰기 답글쓰기
		
		return "redirect:list_sh.do";//response.sendRedirect("list.jsp")와 같음
	}
	
	@RequestMapping("list_sh.do")
	public String listboard(Model model, String pageNum_sh) {
		if(pageNum_sh==null) {
			pageNum_sh="1";
		}
		
		int pageSize=10;//1페이지에 글 10개씩
		int currentPage=Integer.parseInt(pageNum_sh);//현재 페이지
		int startRow=(currentPage-1)*pageSize+1;//페이지의 첫번째 행(row)을 구한다
		int endRow=currentPage*pageSize;//페이지의 마지막 행(row)을 구한다
		
		int count=0;//총 글 개수 넣기 위해
		int pageBlock=10;//1블럭당 10개의 페이지로 처리 하려고
		
		count=sqlSession.selectOne("board.countDao");//총 글 개수
		
		int number=count-(currentPage-1)*pageSize;//글번호 역순으로 하려고
		
		Map<String,Integer> map=new HashMap<String, Integer>();
		map.put("start", startRow-1);//시작위치, mysql은 0(행=row)부터 시작한다
		map.put("cnt", pageSize);
		
		int pageCount=count/pageSize+(count%pageSize==0?0:1);//총 페이지 수 구하기
		//                            꽁다리 row개수 구하기                          
		//            37/10=3  37%10=7나머지가 0이 아니니까 1출력 결과는 3+1이니까 4 결국 4페이지  
		
		int startPage=(currentPage/pageBlock)*10+1;//시작페이지
		int endPage=startPage+pageBlock-1;//end 페이지
		
		List<BoardDTO> list=sqlSession.selectList("board.listDao",map);
		
		model.addAttribute("pageNum_sh",pageNum_sh);
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
		
		//return "board/list";//뷰 리턴
		return ".main.board.list_sh";//뷰 리턴
	}
	
	//글 내용 보기
	@RequestMapping("content_sh.do")
	public String content(Model model,String num_sh,String pageNum_sh) {
		
		int num1=Integer.parseInt(num_sh);
		sqlSession.update("board.readcountDao",num1);//조회수 증가
		
		BoardDTO bdto=sqlSession.selectOne("board.getBoard",num1);
		String content_sh=bdto.getContent_sh();
		content_sh=content_sh.replace("\n", "<br>");//textA로 안하고 그냥 출력할 때 해줘야함
		
		model.addAttribute("pageNum_sh",pageNum_sh);
		model.addAttribute("num_sh",bdto.getNum_sh());//num1로 넣었음
		model.addAttribute("content_sh",content_sh);
		model.addAttribute("dto",bdto);
		
		//return "board/content";//뷰 리턴 
		return ".main.board.content_sh";//뷰 리턴 
	}
	
	@RequestMapping("updateForm_sh.do")
	public ModelAndView updateForm(String num_sh,String pageNum_sh) {
		int num1=Integer.parseInt(num_sh);
		BoardDTO bdto=sqlSession.selectOne("board.getBoard",num1);
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageNum_sh",pageNum_sh);
		mv.addObject("dto",bdto);
		//mv.setViewName("board/updateForm");// 뷰
		mv.setViewName(".main.board.updateForm_sh");// 뷰
		
		return mv;
	}
	
	//DB글 수정
	@RequestMapping(value="updatePro_sh.do",method=RequestMethod.POST)
	public ModelAndView updatePro(BoardDTO boardDTO,String pageNum_sh) {
		
		sqlSession.update("board.updateDao",boardDTO);//글 수정
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageNum_sh",pageNum_sh);
		mv.setViewName("redirect:list_sh.do");
		
		return mv;
		
	}
	
	//글삭제
	@RequestMapping("delete_sh.do")
	public String delete(Model model,String num_sh,String pageNum_sh) {
		
		sqlSession.delete("board.deleteDao",new Integer(num_sh));//글삭제
		
		model.addAttribute("pageNum_sh",pageNum_sh);
		
		return "redirect:list_sh.do";
	}
}//class end
