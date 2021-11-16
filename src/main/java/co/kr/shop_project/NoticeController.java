package co.kr.shop_project;

import java.text.DateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSession;//MyBatis ���
import java.util.*;//HashMap ���

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
	private SqlSession sqlSession;//setter �۾� �۵�
	
	//�۾��� ��, ��۾���
	@RequestMapping("writeForm_no.do")
	public String writeF(Model model,String num_no,String ref_no,String re_step_no,String re_level_no,String pageNum) {
		if(num_no==null) {//���۾���
			num_no="0";//�۹�ȣ
			ref_no="1";//�� �׷�
			re_step_no="0";//�� ����
			re_level_no="0";//�� ����
		}
		
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("num_no",new Integer(num_no));
		model.addAttribute("ref_no",new Integer(ref_no));
		model.addAttribute("re_step_no",new Integer(re_step_no));
		model.addAttribute("re_level_no",new Integer(re_level_no));
		
		return ".main.notice.writeForm_no";//�� ����
	}
	
	//DB�۾���, ��۾���
	@RequestMapping(value="writePro_no.do", method=RequestMethod.POST)
	public String writePro(@ModelAttribute("noticeDTO") NoticeDTO noticeDTO, HttpServletRequest request) 
			throws IOException,NamingException{
		
		// ���� ���ε� ó��
		String fileName=null;
		String fileName2=null;
		String fileName3=null;
				
		MultipartFile uploadFile = noticeDTO.getUploadFile();
		MultipartFile uploadFile2 = noticeDTO.getUploadFile2();
		MultipartFile uploadFile3 = noticeDTO.getUploadFile3();
				
		if (!uploadFile.isEmpty()) {
			String originalFileName = uploadFile.getOriginalFilename();
			String ext = FilenameUtils.getExtension(originalFileName);	//Ȯ���� ���ϱ�
			UUID uuid = UUID.randomUUID();	//UUID ���ϱ�
			fileName=uuid+"."+ext;
			
			uploadFile.transferTo(new File("c:\\upload\\" + fileName));
			}
		if (!uploadFile2.isEmpty()) {
			String originalFileName2 = uploadFile2.getOriginalFilename();
			String ext2 = FilenameUtils.getExtension(originalFileName2);	//Ȯ���� ���ϱ�
			UUID uuid2 = UUID.randomUUID();	//UUID ���ϱ�
					
			fileName2=uuid2+"."+ext2;
					
			uploadFile2.transferTo(new File("c:\\upload\\" + fileName2));
					
			}
		if (!uploadFile3.isEmpty()) {
			String originalFileName3 = uploadFile3.getOriginalFilename();
			String ext3 = FilenameUtils.getExtension(originalFileName3);	//Ȯ���� ���ϱ�
			UUID uuid3 = UUID.randomUUID();	//UUID ���ϱ�
					
			fileName3=uuid3+"."+ext3;
					
			uploadFile3.transferTo(new File("c:\\upload\\" + fileName3));
			}
		noticeDTO.setFileName(fileName);
		noticeDTO.setFileName2(fileName2);
		noticeDTO.setFileName3(fileName3);
		
		int maxNum=0;//�ִ� �۹�ȣ ���� ���� ����
		if(sqlSession.selectOne("board_no.numMax_no") != null) {
			//�ִ� �۹�ȣ�� ������
			maxNum=sqlSession.selectOne("board_no.numMax_no");			
		}
		
		if(maxNum!=0) {//�ִ� �۹�ȣ�� 0�� �ƴϸ�, ���� ������
			maxNum=maxNum+1;// ref�׷쿡 ��������
		}else {//ó�� ���̸�
			maxNum=1;//ref�׷쿡 1�� ��������
			
		}
		
		if(noticeDTO.getNum_no()!=0) {//����̸�
			//��� ��ġ Ȯ��
			sqlSession.update("board_no.reStep_no",noticeDTO);
			noticeDTO.setRe_step_no(noticeDTO.getRe_step_no()+1);//�ۼ���+1
			noticeDTO.setRe_level_no(noticeDTO.getRe_level_no()+1);//�۱���+1
		}else {//�����̸�
			noticeDTO.setRef_no(new Integer(maxNum)); //�۱׷�
			noticeDTO.setRe_step_no(new Integer(0)); //�ۼ���
			noticeDTO.setRe_level_no(new Integer(0)); //�� ����
		}//else end
		
		sqlSession.insert("board_no.insertDao_no",noticeDTO);//�۾���, ���ó��
		return "redirect:list_no.do";//response.sendRedirect("list.jsp")�� ���� ���
	}
	
	//����Ʈ
	@RequestMapping("list_no.do")
	public String listboard(Model model,String pageNum, Integer mem_id, Integer mem_pw) {
		if(pageNum==null) {
			pageNum="1";
		}
		
		int pageSize=10;//1 �������� 10����
		int currentPage=Integer.parseInt(pageNum);//����������
		int startRow=(currentPage-1)*pageSize+1;//�������� ù��° ���� ���Ѵ�
		int endRow=currentPage*pageSize;//�������� ������ ��(row)�� ���Ѵ�
		
		int count=0;//�� �� ���� ��������
		int pageBlock=10;//�� ���� 10�� �������� ó��
		
		count=sqlSession.selectOne("board_no.countDao_no");//�� �� ����
		
		int number=count-(currentPage-1)*pageSize;//�۹�ȣ �������� �Ϸ���
		
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("start", startRow-1);//������ġ, mysql�� 0���� ����
		map.put("cnt", pageSize);
		
		map.put("mem_id", mem_id);
		
		int pageCount=count/pageSize+(count%pageSize==0?0:1);//�� ������ �� ���ϱ�
		//                               �� row���� ���ϱ�
		
		int startPage=(currentPage/10)*10+1;//���� ������ ���ϱ�
		int endPage=startPage+pageBlock-1;//�� ������
		
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
		return ".main.notice.list_no";//�� ����
	}
	
	//�۳��� ����
	@RequestMapping("content_no.do")
	public String content(Model model,String num_no,String pageNum, Integer mem_id) {
		
        Map<String,Integer> map=new HashMap<String, Integer>();
		
		map.put("mem_id", mem_id);
		
		int num1=Integer.parseInt(num_no);
		sqlSession.update("board_no.readcountDao_no",num1);//��ȸ�� ����
		
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
	
	//�ۼ���
	@RequestMapping("updateForm_no.do")
	public ModelAndView updateForm(String num_no,String pageNum) {
		int num1=Integer.parseInt(num_no);
		NoticeDTO ndto=sqlSession.selectOne("board_no.getBoard_no",num1);
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageNum",pageNum);
		mv.addObject("dto",ndto);
		mv.setViewName(".main.notice.updateForm_no"); //��
		
		return mv;
	}
	
	//DB�ۼ���
	@RequestMapping(value="updatePro_no.do",method=RequestMethod.POST)
	public ModelAndView updatePro(NoticeDTO noticeDTO,String pageNum) {
		sqlSession.update("board_no.updateDao_no",noticeDTO);//�ۼ���
		ModelAndView mv=new ModelAndView();
		
		mv.addObject("pageNum",pageNum);
		mv.setViewName("redirect:list_no.do");
		
		return mv;
	}
	
	//�ۻ���
	@RequestMapping("delete_no.do")
	public String delete(Model model,String num_no,String pageNum) {
		sqlSession.delete("board_no.deleteDao_no",new Integer(num_no));//�ۻ���
		model.addAttribute("pageNum",pageNum);
		
		return "redirect:list_no.do";
	}
	
}//class end
