package co.kr.shop_project;
import java.text.DateFormat;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSession;//mybatis사용
import java.util.*;//HashMap사용


import model.board.FaqDTO;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import javax.naming.NamingException;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class FAQController {
	
	@Autowired
	private SqlSession sqlSession;//setter 작업 자동
	
	//글쓰기 폼, 답글쓰기
	@RequestMapping("writeForm_faq.do")//글 내용보기에서 넘어온 것들이라 String으로
	public String writeF(Model model,String num_faq,String ref_faq,String re_step_faq,String re_level_faq,String pageNum_faq) {
		if(num_faq==null) {//원글쓰기
			num_faq="0";//글 번호
			ref_faq="1";//글 그룹
			re_step_faq="0";//글 순서
			re_level_faq="0";//글 깊이
		}
		
		model.addAttribute("pageNum_faq",pageNum_faq);
		model.addAttribute("num_faq",new Integer(num_faq));
		model.addAttribute("ref_faq",new Integer(ref_faq));
		model.addAttribute("re_step_faq",new Integer(re_step_faq));
		model.addAttribute("re_level_faq",new Integer(re_level_faq));
		
		//return "board/writeForm";//뷰 리턴
		return ".main.faq.writeForm_faq";//뷰 리턴
	}
	
	//DB글쓰기, 답글쓰기
	@RequestMapping(value="writePro_faq.do",method=RequestMethod.POST)
	public String writePro(@ModelAttribute("FaqDTO") FaqDTO faqDTO, HttpServletRequest request)
	throws IOException,NamingException{
		
		// 파일 업로드 처리
		String fileName=null;
		String fileName2=null;
		String fileName3=null;
		
		MultipartFile uploadFile = faqDTO.getUploadFile();
		MultipartFile uploadFile2 = faqDTO.getUploadFile2();
		MultipartFile uploadFile3 = faqDTO.getUploadFile3();
		
		if (!uploadFile.isEmpty()) {
			String originalFileName = uploadFile.getOriginalFilename();
			String ext = FilenameUtils.getExtension(originalFileName);	//확장자 구하기
			UUID uuid = UUID.randomUUID();	//UUID 구하기
			fileName=uuid+"."+ext;
	
			uploadFile.transferTo(new File("c:\\upload\\" + fileName));
		}
		if (!uploadFile2.isEmpty()) {
			String originalFileName2 = uploadFile2.getOriginalFilename();
			String ext2 = FilenameUtils.getExtension(originalFileName2);	//확장자 구하기
			UUID uuid2 = UUID.randomUUID();	//UUID 구하기
			
			fileName2=uuid2+"."+ext2;
			
			
			uploadFile2.transferTo(new File("c:\\upload\\" + fileName2));
			
		}
		if (!uploadFile3.isEmpty()) {
			String originalFileName3 = uploadFile3.getOriginalFilename();
			String ext3 = FilenameUtils.getExtension(originalFileName3);	//확장자 구하기
			UUID uuid3 = UUID.randomUUID();	//UUID 구하기
			
			fileName3=uuid3+"."+ext3;
			
			uploadFile3.transferTo(new File("c:\\upload\\" + fileName3));
		}
		faqDTO.setFileName(fileName);
		faqDTO.setFileName2(fileName2);
		faqDTO.setFileName3(fileName3);
		//////////////////////////////
		
		int maxNum=0;//최대 글번호 넣을 변수 선언, 지역변수라 초기화 해줘야 함
		if(sqlSession.selectOne("board_faq.numMax") != null) {
			//최대 글번호가 있으면(글을 썼으면)
			maxNum=sqlSession.selectOne("board_faq.numMax");
		}
		
		if(maxNum != 0) {//최대 글번호가 0이 아니면, 글이 존재하면
			maxNum=maxNum+1;//ref그룹에 넣으려고
		}else {//처음 글이면
			maxNum=1;//ref그룹에 1을 넣으려고
		}
		
		String ip=request.getRemoteAddr();//ip구하기
		faqDTO.setIp_faq(ip);
		
		if(faqDTO.getNum_faq() != 0) {//답글이면
			//답글 위치 확보
			sqlSession.update("board_faq.reStep",faqDTO);
			faqDTO.setRe_step_faq(faqDTO.getRe_step_faq()+1);//글 순서+1
			faqDTO.setRe_level_faq(faqDTO.getRe_level_faq()+1);//글 깊이+1
			
		}else {//원글이면
			faqDTO.setRef_faq(new Integer(maxNum));//글그룹
			faqDTO.setRe_step_faq(new Integer(0));//글순서
			faqDTO.setRe_level_faq(new Integer(0));//글깊이
		}
		
		sqlSession.insert("board_faq.insertDao",faqDTO);//글쓰기 답글쓰기
		
		return "redirect:list_faq.do";//response.sendRedirect("list.jsp")와 같음
	}
	
	@RequestMapping("list_faq.do")
	public String listboard(Model model, String pageNum_faq) {
		if(pageNum_faq==null) {
			pageNum_faq="1";
		}
		
		int pageSize=10;//1페이지에 글 10개씩
		int currentPage=Integer.parseInt(pageNum_faq);//현재 페이지
		int startRow=(currentPage-1)*pageSize+1;//페이지의 첫번째 행(row)을 구한다
		int endRow=currentPage*pageSize;//페이지의 마지막 행(row)을 구한다
		
		int count=0;//총 글 개수 넣기 위해
		int pageBlock=10;//1블럭당 10개의 페이지로 처리 하려고
		
		count=sqlSession.selectOne("board_faq.countDao");//총 글 개수
		
		int number=count-(currentPage-1)*pageSize;//글번호 역순으로 하려고
		
		Map<String,Integer> map=new HashMap<String, Integer>();
		map.put("start", startRow-1);//시작위치, mysql은 0(행=row)부터 시작한다
		map.put("cnt", pageSize);
		
		int pageCount=count/pageSize+(count%pageSize==0?0:1);//총 페이지 수 구하기
		//                            꽁다리 row개수 구하기                          
		//            37/10=3  37%10=7나머지가 0이 아니니까 1출력 결과는 3+1이니까 4 결국 4페이지  
		
		int startPage=(currentPage/pageBlock)*10+1;//시작페이지
		int endPage=startPage+pageBlock-1;//end 페이지
		
		List<FaqDTO> list=sqlSession.selectList("board_faq.listDao",map);
		
		model.addAttribute("pageNum_faq",pageNum_faq);
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
		return ".main.faq.list_faq";//뷰 리턴
	}
	
	//글 내용 보기
	@RequestMapping("content_faq.do")
	public String content(Model model,String num_faq,String pageNum_faq,String fileName,String fileName2,String fileName3) {
		
		
		
		int num1=Integer.parseInt(num_faq);
		sqlSession.update("board_faq.readcountDao",num1);//조회수 증가
		
		FaqDTO bdto=sqlSession.selectOne("board_faq.getBoard",num1);
		String content_faq=bdto.getContent_faq();
		content_faq=content_faq.replace("\n", "<br>");//textA로 안하고 그냥 출력할 때 해줘야함
		
		model.addAttribute("pageNum_faq",pageNum_faq);
		model.addAttribute("num_faq",bdto.getNum_faq());//num1로 넣었음
		model.addAttribute("content_faq",content_faq);
		model.addAttribute("dto",bdto);
		
		System.out.println("FAQController dto.fileName:"+bdto.getFileName());
		System.out.println("FAQController dto.fileName2:"+bdto.getFileName2());
		System.out.println("FAQController dto.fileName3:"+bdto.getFileName3());
		
		//return "board/content";//뷰 리턴 
		return ".main.faq.content_faq";//뷰 리턴 
	}
	
	@RequestMapping("updateForm_faq.do")
	public ModelAndView updateForm(String num_faq,String pageNum_faq) {
		int num1=Integer.parseInt(num_faq);
		FaqDTO bdto=sqlSession.selectOne("board_faq.getBoard",num1);
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageNum_faq",pageNum_faq);
		mv.addObject("dto",bdto);
		//mv.setViewName("board/updateForm");// 뷰
		mv.setViewName(".main.faq.updateForm_faq");// 뷰
		
		return mv;
	}
	
	//DB글 수정
	@RequestMapping(value="updatePro_faq.do",method=RequestMethod.POST)
	public ModelAndView updatePro(FaqDTO faqDTO,String pageNum_faq) {
		
		sqlSession.update("board_faq.updateDao",faqDTO);//글 수정
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageNum_faq",pageNum_faq);
		mv.setViewName("redirect:list_faq.do");
		
		return mv;
		
	}
	
	//글삭제
	@RequestMapping("delete_faq.do")
	public String delete(Model model,String num_faq,String pageNum_faq) {
		
		sqlSession.delete("board_faq.deleteDao",new Integer(num_faq));//글삭제
		
		model.addAttribute("pageNum_faq",pageNum_faq);
		
		return "redirect:list_faq.do";
	}
	
}//class end
