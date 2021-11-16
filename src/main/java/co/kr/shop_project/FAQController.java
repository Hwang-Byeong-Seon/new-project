package co.kr.shop_project;
import java.text.DateFormat;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSession;//mybatis���
import java.util.*;//HashMap���


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
	private SqlSession sqlSession;//setter �۾� �ڵ�
	
	//�۾��� ��, ��۾���
	@RequestMapping("writeForm_faq.do")//�� ���뺸�⿡�� �Ѿ�� �͵��̶� String����
	public String writeF(Model model,String num_faq,String ref_faq,String re_step_faq,String re_level_faq,String pageNum_faq) {
		if(num_faq==null) {//���۾���
			num_faq="0";//�� ��ȣ
			ref_faq="1";//�� �׷�
			re_step_faq="0";//�� ����
			re_level_faq="0";//�� ����
		}
		
		model.addAttribute("pageNum_faq",pageNum_faq);
		model.addAttribute("num_faq",new Integer(num_faq));
		model.addAttribute("ref_faq",new Integer(ref_faq));
		model.addAttribute("re_step_faq",new Integer(re_step_faq));
		model.addAttribute("re_level_faq",new Integer(re_level_faq));
		
		//return "board/writeForm";//�� ����
		return ".main.faq.writeForm_faq";//�� ����
	}
	
	//DB�۾���, ��۾���
	@RequestMapping(value="writePro_faq.do",method=RequestMethod.POST)
	public String writePro(@ModelAttribute("FaqDTO") FaqDTO faqDTO, HttpServletRequest request)
	throws IOException,NamingException{
		
		// ���� ���ε� ó��
		String fileName=null;
		String fileName2=null;
		String fileName3=null;
		
		MultipartFile uploadFile = faqDTO.getUploadFile();
		MultipartFile uploadFile2 = faqDTO.getUploadFile2();
		MultipartFile uploadFile3 = faqDTO.getUploadFile3();
		
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
		faqDTO.setFileName(fileName);
		faqDTO.setFileName2(fileName2);
		faqDTO.setFileName3(fileName3);
		//////////////////////////////
		
		int maxNum=0;//�ִ� �۹�ȣ ���� ���� ����, ���������� �ʱ�ȭ ����� ��
		if(sqlSession.selectOne("board_faq.numMax") != null) {
			//�ִ� �۹�ȣ�� ������(���� ������)
			maxNum=sqlSession.selectOne("board_faq.numMax");
		}
		
		if(maxNum != 0) {//�ִ� �۹�ȣ�� 0�� �ƴϸ�, ���� �����ϸ�
			maxNum=maxNum+1;//ref�׷쿡 ��������
		}else {//ó�� ���̸�
			maxNum=1;//ref�׷쿡 1�� ��������
		}
		
		String ip=request.getRemoteAddr();//ip���ϱ�
		faqDTO.setIp_faq(ip);
		
		if(faqDTO.getNum_faq() != 0) {//����̸�
			//��� ��ġ Ȯ��
			sqlSession.update("board_faq.reStep",faqDTO);
			faqDTO.setRe_step_faq(faqDTO.getRe_step_faq()+1);//�� ����+1
			faqDTO.setRe_level_faq(faqDTO.getRe_level_faq()+1);//�� ����+1
			
		}else {//�����̸�
			faqDTO.setRef_faq(new Integer(maxNum));//�۱׷�
			faqDTO.setRe_step_faq(new Integer(0));//�ۼ���
			faqDTO.setRe_level_faq(new Integer(0));//�۱���
		}
		
		sqlSession.insert("board_faq.insertDao",faqDTO);//�۾��� ��۾���
		
		return "redirect:list_faq.do";//response.sendRedirect("list.jsp")�� ����
	}
	
	@RequestMapping("list_faq.do")
	public String listboard(Model model, String pageNum_faq) {
		if(pageNum_faq==null) {
			pageNum_faq="1";
		}
		
		int pageSize=10;//1�������� �� 10����
		int currentPage=Integer.parseInt(pageNum_faq);//���� ������
		int startRow=(currentPage-1)*pageSize+1;//�������� ù��° ��(row)�� ���Ѵ�
		int endRow=currentPage*pageSize;//�������� ������ ��(row)�� ���Ѵ�
		
		int count=0;//�� �� ���� �ֱ� ����
		int pageBlock=10;//1���� 10���� �������� ó�� �Ϸ���
		
		count=sqlSession.selectOne("board_faq.countDao");//�� �� ����
		
		int number=count-(currentPage-1)*pageSize;//�۹�ȣ �������� �Ϸ���
		
		Map<String,Integer> map=new HashMap<String, Integer>();
		map.put("start", startRow-1);//������ġ, mysql�� 0(��=row)���� �����Ѵ�
		map.put("cnt", pageSize);
		
		int pageCount=count/pageSize+(count%pageSize==0?0:1);//�� ������ �� ���ϱ�
		//                            �Ǵٸ� row���� ���ϱ�                          
		//            37/10=3  37%10=7�������� 0�� �ƴϴϱ� 1��� ����� 3+1�̴ϱ� 4 �ᱹ 4������  
		
		int startPage=(currentPage/pageBlock)*10+1;//����������
		int endPage=startPage+pageBlock-1;//end ������
		
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
		
		//return "board/list";//�� ����
		return ".main.faq.list_faq";//�� ����
	}
	
	//�� ���� ����
	@RequestMapping("content_faq.do")
	public String content(Model model,String num_faq,String pageNum_faq,String fileName,String fileName2,String fileName3) {
		
		
		
		int num1=Integer.parseInt(num_faq);
		sqlSession.update("board_faq.readcountDao",num1);//��ȸ�� ����
		
		FaqDTO bdto=sqlSession.selectOne("board_faq.getBoard",num1);
		String content_faq=bdto.getContent_faq();
		content_faq=content_faq.replace("\n", "<br>");//textA�� ���ϰ� �׳� ����� �� �������
		
		model.addAttribute("pageNum_faq",pageNum_faq);
		model.addAttribute("num_faq",bdto.getNum_faq());//num1�� �־���
		model.addAttribute("content_faq",content_faq);
		model.addAttribute("dto",bdto);
		
		System.out.println("FAQController dto.fileName:"+bdto.getFileName());
		System.out.println("FAQController dto.fileName2:"+bdto.getFileName2());
		System.out.println("FAQController dto.fileName3:"+bdto.getFileName3());
		
		//return "board/content";//�� ���� 
		return ".main.faq.content_faq";//�� ���� 
	}
	
	@RequestMapping("updateForm_faq.do")
	public ModelAndView updateForm(String num_faq,String pageNum_faq) {
		int num1=Integer.parseInt(num_faq);
		FaqDTO bdto=sqlSession.selectOne("board_faq.getBoard",num1);
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageNum_faq",pageNum_faq);
		mv.addObject("dto",bdto);
		//mv.setViewName("board/updateForm");// ��
		mv.setViewName(".main.faq.updateForm_faq");// ��
		
		return mv;
	}
	
	//DB�� ����
	@RequestMapping(value="updatePro_faq.do",method=RequestMethod.POST)
	public ModelAndView updatePro(FaqDTO faqDTO,String pageNum_faq) {
		
		sqlSession.update("board_faq.updateDao",faqDTO);//�� ����
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageNum_faq",pageNum_faq);
		mv.setViewName("redirect:list_faq.do");
		
		return mv;
		
	}
	
	//�ۻ���
	@RequestMapping("delete_faq.do")
	public String delete(Model model,String num_faq,String pageNum_faq) {
		
		sqlSession.delete("board_faq.deleteDao",new Integer(num_faq));//�ۻ���
		
		model.addAttribute("pageNum_faq",pageNum_faq);
		
		return "redirect:list_faq.do";
	}
	
}//class end
