package co.kr.shop_project;

import java.text.DateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSession;//MyBatis 사용
import java.util.*;//HashMap 사용

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import javax.naming.NamingException;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import model.board.NoticeDTO;

import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class NoticeController {
	
	@Autowired
	private SqlSession sqlSession;//setter 작업 작동
	
	//글쓰기 폼, 답글쓰기
	@RequestMapping("writeForm_no.do")
	public String writeF(Model model,String num_no,String ref_no,String re_step_no,String re_level_no,String pageNum) {
		if(num_no==null) {//원글쓰기
			num_no="0";//글번호
			ref_no="1";//글 그룹
			re_step_no="0";//글 순서
			re_level_no="0";//글 깊이
		}
		
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("num_no",new Integer(num_no));
		model.addAttribute("ref_no",new Integer(ref_no));
		model.addAttribute("re_step_no",new Integer(re_step_no));
		model.addAttribute("re_level_no",new Integer(re_level_no));
		
		return ".main.notice.writeForm_no";//뷰 리턴
	}
	
	//DB글쓰기, 답글쓰기
	@RequestMapping(value="writePro_no.do", method=RequestMethod.POST)
	public String writePro(@ModelAttribute("noticeDTO") NoticeDTO noticeDTO, HttpServletRequest request) 
			throws IOException,NamingException{
		
		// 파일 업로드 처리
		String fileName=null;
		String fileName2=null;
		String fileName3=null;
				
		MultipartFile uploadFile = noticeDTO.getUploadFile();
		MultipartFile uploadFile2 = noticeDTO.getUploadFile2();
		MultipartFile uploadFile3 = noticeDTO.getUploadFile3();
				
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
		noticeDTO.setFileName(fileName);
		noticeDTO.setFileName2(fileName2);
		noticeDTO.setFileName3(fileName3);
		
		int maxNum=0;//최대 글번호 넣을 변수 선언
		if(sqlSession.selectOne("board_no.numMax_no") != null) {
			//최대 글번호가 있으면
			maxNum=sqlSession.selectOne("board_no.numMax_no");			
		}
		
		if(maxNum!=0) {//최대 글번호가 0이 아니면, 글이 있으면
			maxNum=maxNum+1;// ref그룹에 넣으려고
		}else {//처음 글이면
			maxNum=1;//ref그룹에 1을 넣으려고
			
		}
		
		if(noticeDTO.getNum_no()!=0) {//답글이면
			//답글 위치 확보
			sqlSession.update("board_no.reStep_no",noticeDTO);
			noticeDTO.setRe_step_no(noticeDTO.getRe_step_no()+1);//글순서+1
			noticeDTO.setRe_level_no(noticeDTO.getRe_level_no()+1);//글깊이+1
		}else {//원글이면
			noticeDTO.setRef_no(new Integer(maxNum)); //글그룹
			noticeDTO.setRe_step_no(new Integer(0)); //글순서
			noticeDTO.setRe_level_no(new Integer(0)); //글 깊이
		}//else end
		
		sqlSession.insert("board_no.insertDao_no",noticeDTO);//글쓰기, 답글처리
		return "redirect:list_no.do";//response.sendRedirect("list.jsp")와 같은 기능
	}
	
	//리스트
	@RequestMapping("list_no.do")
	public String listboard(Model model,String pageNum, Integer mem_id, Integer mem_pw) {
		if(pageNum==null) {
			pageNum="1";
		}
		
		int pageSize=10;//1 페이지에 10개씩
		int currentPage=Integer.parseInt(pageNum);//현재페이지
		int startRow=(currentPage-1)*pageSize+1;//페이지의 첫번째 행을 구한다
		int endRow=currentPage*pageSize;//페이지의 마지막 행(row)을 구한다
		
		int count=0;//총 글 개수 넣으려고
		int pageBlock=10;//한 블럭당 10개 페이지로 처리
		
		count=sqlSession.selectOne("board_no.countDao_no");//총 글 개수
		
		int number=count-(currentPage-1)*pageSize;//글번호 역순으로 하려고
		
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("start", startRow-1);//시작위치, mysql은 0부터 시작
		map.put("cnt", pageSize);
		
		map.put("mem_id", mem_id);
		
		int pageCount=count/pageSize+(count%pageSize==0?0:1);//총 페이지 수 구하기
		//                               끝 row개수 구하기
		
		int startPage=(currentPage/10)*10+1;//시작 페이지 구하기
		int endPage=startPage+pageBlock-1;//끝 페이지
		
		List<NoticeDTO> list=sqlSession.selectList("board_no.listDao_no",map);
		
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
		
		model.addAttribute("mem_id",mem_id);
		return ".main.notice.list_no";//뷰 리턴
	}
	
	//글내용 보기
	@RequestMapping("content_no.do")
	public String content(Model model,String num_no,String pageNum, Integer mem_id) {
		
        Map<String,Integer> map=new HashMap<String, Integer>();
		
		map.put("mem_id", mem_id);
		
		int num1=Integer.parseInt(num_no);
		sqlSession.update("board_no.readcountDao_no",num1);//조회수 증가
		
		NoticeDTO ndto=sqlSession.selectOne("board_no.getBoard_no",num1);
		String content=ndto.getContent_no();
		content=content.replace("\n","<br>");
		
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("num_no",num1);
		model.addAttribute("content_no",content);
		model.addAttribute("dto",ndto);
		model.addAttribute("mem_id",mem_id);
		
		return ".main.notice.content_no";
	}
	
	//글수정
	@RequestMapping("updateForm_no.do")
	public ModelAndView updateForm(String num_no,String pageNum) {
		int num1=Integer.parseInt(num_no);
		NoticeDTO ndto=sqlSession.selectOne("board_no.getBoard_no",num1);
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageNum",pageNum);
		mv.addObject("dto",ndto);
		mv.setViewName(".main.notice.updateForm_no"); //뷰
		
		return mv;
	}
	
	//DB글수정
	@RequestMapping(value="updatePro_no.do",method=RequestMethod.POST)
	public ModelAndView updatePro(NoticeDTO noticeDTO,String pageNum) {
		sqlSession.update("board_no.updateDao_no",noticeDTO);//글수정
		ModelAndView mv=new ModelAndView();
		
		mv.addObject("pageNum",pageNum);
		mv.setViewName("redirect:list_no.do");
		
		return mv;
	}
	
	//글삭제
	@RequestMapping("delete_no.do")
	public String delete(Model model,String num_no,String pageNum) {
		sqlSession.delete("board_no.deleteDao_no",new Integer(num_no));//글삭제
		model.addAttribute("pageNum",pageNum);
		
		return "redirect:list_no.do";
	}
	
}//class end
