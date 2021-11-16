package co.kr.shop_project;
import java.text.DateFormat;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.SqlSession;//mybatis���
import java.util.*;//HashMap���

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
	private SqlSession sqlSession;//setter �۾� �ڵ�
	
	//�۾��� ��, ��۾���
	@RequestMapping("writeForm_sh.do")//�� ���뺸�⿡�� �Ѿ�� �͵��̶� String����
	public String writeF(Model model,String num_sh,String ref_sh,String re_step_sh,String re_level_sh,String pageNum_sh) {
		if(num_sh==null) {//���۾���
			num_sh="0";//�� ��ȣ
			ref_sh="1";//�� �׷�
			re_step_sh="0";//�� ����
			re_level_sh="0";//�� ����
		}
		
		model.addAttribute("pageNum_sh",pageNum_sh);
		model.addAttribute("num_sh",new Integer(num_sh));
		model.addAttribute("ref_sh",new Integer(ref_sh));
		model.addAttribute("re_step_sh",new Integer(re_step_sh));
		model.addAttribute("re_level_sh",new Integer(re_level_sh));
		
		//return "board/writeForm";//�� ����
		return ".main.board.writeForm_sh";//�� ����
	}
	
	//DB�۾���, ��۾���
	@RequestMapping(value="writePro_sh.do",method=RequestMethod.POST)
	public String writePro(@ModelAttribute("boardDTO") BoardDTO boardDTO, HttpServletRequest request)
	throws IOException,NamingException{
		
		int maxNum=0;//�ִ� �۹�ȣ ���� ���� ����, ���������� �ʱ�ȭ ����� ��
		if(sqlSession.selectOne("board.numMax") != null) {
			//�ִ� �۹�ȣ�� ������(���� ������)
			maxNum=sqlSession.selectOne("board.numMax");
		}
		
		if(maxNum != 0) {//�ִ� �۹�ȣ�� 0�� �ƴϸ�, ���� �����ϸ�
			maxNum=maxNum+1;//ref�׷쿡 ��������
		}else {//ó�� ���̸�
			maxNum=1;//ref�׷쿡 1�� ��������
		}
		
		String ip=request.getRemoteAddr();//ip���ϱ�
		boardDTO.setIp_sh(ip);
		
		if(boardDTO.getNum_sh() != 0) {//����̸�
			//��� ��ġ Ȯ��
			sqlSession.update("board.reStep",boardDTO);
			boardDTO.setRe_step_sh(boardDTO.getRe_step_sh()+1);//�� ����+1
			boardDTO.setRe_level_sh(boardDTO.getRe_level_sh()+1);//�� ����+1
			
		}else {//�����̸�
			boardDTO.setRef_sh(new Integer(maxNum));//�۱׷�
			boardDTO.setRe_step_sh(new Integer(0));//�ۼ���
			boardDTO.setRe_level_sh(new Integer(0));//�۱���
		}
		
		sqlSession.insert("board.insertDao",boardDTO);//�۾��� ��۾���
		
		return "redirect:list_sh.do";//response.sendRedirect("list.jsp")�� ����
	}
	
	@RequestMapping("list_sh.do")
	public String listboard(Model model, String pageNum_sh) {
		if(pageNum_sh==null) {
			pageNum_sh="1";
		}
		
		int pageSize=10;//1�������� �� 10����
		int currentPage=Integer.parseInt(pageNum_sh);//���� ������
		int startRow=(currentPage-1)*pageSize+1;//�������� ù��° ��(row)�� ���Ѵ�
		int endRow=currentPage*pageSize;//�������� ������ ��(row)�� ���Ѵ�
		
		int count=0;//�� �� ���� �ֱ� ����
		int pageBlock=10;//1���� 10���� �������� ó�� �Ϸ���
		
		count=sqlSession.selectOne("board.countDao");//�� �� ����
		
		int number=count-(currentPage-1)*pageSize;//�۹�ȣ �������� �Ϸ���
		
		Map<String,Integer> map=new HashMap<String, Integer>();
		map.put("start", startRow-1);//������ġ, mysql�� 0(��=row)���� �����Ѵ�
		map.put("cnt", pageSize);
		
		int pageCount=count/pageSize+(count%pageSize==0?0:1);//�� ������ �� ���ϱ�
		//                            �Ǵٸ� row���� ���ϱ�                          
		//            37/10=3  37%10=7�������� 0�� �ƴϴϱ� 1��� ����� 3+1�̴ϱ� 4 �ᱹ 4������  
		
		int startPage=(currentPage/pageBlock)*10+1;//����������
		int endPage=startPage+pageBlock-1;//end ������
		
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
		
		//return "board/list";//�� ����
		return ".main.board.list_sh";//�� ����
	}
	
	//�� ���� ����
	@RequestMapping("content_sh.do")
	public String content(Model model,String num_sh,String pageNum_sh) {
		
		int num1=Integer.parseInt(num_sh);
		sqlSession.update("board.readcountDao",num1);//��ȸ�� ����
		
		BoardDTO bdto=sqlSession.selectOne("board.getBoard",num1);
		String content_sh=bdto.getContent_sh();
		content_sh=content_sh.replace("\n", "<br>");//textA�� ���ϰ� �׳� ����� �� �������
		
		model.addAttribute("pageNum_sh",pageNum_sh);
		model.addAttribute("num_sh",bdto.getNum_sh());//num1�� �־���
		model.addAttribute("content_sh",content_sh);
		model.addAttribute("dto",bdto);
		
		//return "board/content";//�� ���� 
		return ".main.board.content_sh";//�� ���� 
	}
	
	@RequestMapping("updateForm_sh.do")
	public ModelAndView updateForm(String num_sh,String pageNum_sh) {
		int num1=Integer.parseInt(num_sh);
		BoardDTO bdto=sqlSession.selectOne("board.getBoard",num1);
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageNum_sh",pageNum_sh);
		mv.addObject("dto",bdto);
		//mv.setViewName("board/updateForm");// ��
		mv.setViewName(".main.board.updateForm_sh");// ��
		
		return mv;
	}
	
	//DB�� ����
	@RequestMapping(value="updatePro_sh.do",method=RequestMethod.POST)
	public ModelAndView updatePro(BoardDTO boardDTO,String pageNum_sh) {
		
		sqlSession.update("board.updateDao",boardDTO);//�� ����
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageNum_sh",pageNum_sh);
		mv.setViewName("redirect:list_sh.do");
		
		return mv;
		
	}
	
	//�ۻ���
	@RequestMapping("delete_sh.do")
	public String delete(Model model,String num_sh,String pageNum_sh) {
		
		sqlSession.delete("board.deleteDao",new Integer(num_sh));//�ۻ���
		
		model.addAttribute("pageNum_sh",pageNum_sh);
		
		return "redirect:list_sh.do";
	}
}//class end
