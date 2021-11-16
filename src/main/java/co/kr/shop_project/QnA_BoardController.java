package co.kr.shop_project;
import java.text.DateFormat;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.SqlSession;//MyBatis ���
import java.util.*;//HashMap ���

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
	private SqlSession sqlSession;//setter �۾� �ڵ� 
	
	//�۾��� ��, ��۾���
		@RequestMapping("qna_writeForm.do")
		public String qna_writeF(Model model,String q_num,String q_ref,String q_re_step,String q_re_level,String pageNum) {
			if(q_num==null) {//���� ����
				q_num="0";//�۹�ȣ
				q_ref="1";//�� �׷�
				q_re_step="0";//�ۼ���
				q_re_level="0";//�� ����
			}
			
			model.addAttribute("pageNum",pageNum);
			model.addAttribute("q_num",new Integer(q_num));
			model.addAttribute("q_ref",new Integer(q_ref));
			model.addAttribute("q_re_step",new Integer(q_re_step));
			model.addAttribute("q_re_level",new Integer(q_re_level));
			
			//return "board/writeForm";//�丮��
			return ".main.qna_board.qna_writeForm"; //�丮��
		}
		
		//DB�۾��� ,��۾���
		@RequestMapping(value="qna_writePro.do",method=RequestMethod.POST)
		public String qna_writePro(@ModelAttribute("QnaDTO") QnaDTO qnaDTO, HttpServletRequest request,Model model)
		throws IOException,NamingException{
			
			int maxNum=0;//�ִ� �۹�ȣ ���� ���� ����
			if(sqlSession.selectOne("qna_board.numMax") != null) {
				//�ִ�۹�ȣ�� ������
				maxNum=sqlSession.selectOne("qna_board.numMax");
			}
			
			if(maxNum != 0) {//�ִ� �۹�ȣ�� 0�� �ƴϸ�, ���� �����ϸ�
				maxNum=maxNum+1;//ref�׷��� ��������
			}else {//ó�� ���̸�
				maxNum=1;//ref�׷쿡 1�� ��������
			}
			
			String ip=request.getRemoteAddr();//ip���Ѵ�
			qnaDTO.setQ_ip(ip);
			
			if(qnaDTO.getQ_num() !=0 ) {//����̸�
				//��� ��ġ Ȯ��
				sqlSession.update("qna_board.reStep",qnaDTO);
				qnaDTO.setQ_re_step(qnaDTO.getQ_re_step()+1);//�ۼ���+1
				qnaDTO.setQ_re_level(qnaDTO.getQ_re_level()+1);//�� ����+1
				
			}else {//�����̸�
				qnaDTO.setQ_ref(new Integer(maxNum));//�۱׷�
				qnaDTO.setQ_re_step(new Integer(0));//�ۼ���
				qnaDTO.setQ_re_level(new Integer(0));//�� ���� 
			}
			
			
			//��б� ����
			String secret = request.getParameter("secret");
			
			if(secret != null) {
				qnaDTO.setQ_secret(new Boolean(true));
			}
			else if(secret == null) {
				qnaDTO.setQ_secret(new Boolean(false));
			}
			System.out.println("secret : " + secret);
			model.addAttribute("secret",secret);
			
			
			
			sqlSession.insert("qna_board.insertDao",qnaDTO);//�۾���,���ó��
			
			return "redirect:qna_list.do";//response.sendRedirect("list.jsp")�� ����
		}
		
	
	@RequestMapping("qna_list.do")
	public String qna_listboard(Model model, String pageNum) {
		if(pageNum==null) {
			pageNum="1";
		}
		
		int pageSize=10;//1 �������� 10����
		int currentPage=Integer.parseInt(pageNum);//���� ������ 
		
		int startRow=(currentPage-1)*pageSize+1;//�������� ù��° ��(row)�� ���Ѵ� 
		int endRow=currentPage*pageSize;//�������� ������ ��(row)�� ���Ѵ� 
		
		int count=0;//�� �۰��� �������� 
		int pageBlock=10;//1���� 10�� �������� ó�� �Ϸ��� 
		
        count=sqlSession.selectOne("qna_board.countDao");//�� �۰��� ��� 
        
        int number=count-(currentPage-1)*pageSize;//�۹�ȣ �������� �Ϸ��� 
        
        Map<String,Integer> map=new HashMap<String, Integer>();
        map.put("start", startRow-1);//������ġ, mysql�� 0(��=row)���� ����
        map.put("cnt", pageSize);
        
        int pageCount=count/pageSize+(count%pageSize==0?0:1);//�������� ���ϱ�
        //                             �Ǵٸ� row���� ���ϱ�
        
        int startPage=(currentPage/pageBlock)*10+1;//����������
        int endPage=startPage+pageBlock-1;//end ������
        
        
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
		//return "board/list";//�丮��
        
        
        return ".main.qna_board.qna_list"; //�丮��
	}
	
	
	
	
	//�۳��� ����
		@RequestMapping("qna_content.do")
		public String qna_content(Model model,String q_num,String pageNum,String mem_id) {
			
			int num1=Integer.parseInt(q_num);
			sqlSession.update("qna_board.readcountDao",num1);//��ȸ�� ����
			
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
			//return "board/content";//�� ����
			return ".main.qna_board.qna_content"; //�丮��
		}
		
		
		@RequestMapping("qna_updateForm.do")
		public ModelAndView updateForm(String q_num,String pageNum) {
			int num1=Integer.parseInt(q_num);
			QnaDTO bdto=sqlSession.selectOne("qna_board.getBoard",num1);
			
			ModelAndView mv = new ModelAndView();
			mv.addObject("pageNum",pageNum);
			mv.addObject("dto",bdto);
			mv.setViewName(".main.qna_board.qna_updateForm"); //�丮��");
			
			return mv;
		}
		
		
	//DB�� ����
	
	@RequestMapping(value="qna_updatePro.do",method=RequestMethod.POST)
	public ModelAndView qna_updatePro(QnaDTO qnaDTO,String pageNum) {
		sqlSession.update("qna_board.updateDao",qnaDTO);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageNum",pageNum);
		mv.setViewName("redirect:qna_list.do");
		
		return mv;
	}
	//�� ����
	@RequestMapping("qna_delete.do")
	public String qna_delete(Model model,String q_num,String pageNum) {
		
		sqlSession.delete("qna_board.deleteDao",new Integer(q_num)); //�� ����
		model.addAttribute("pageNum",pageNum);
		return "redirect:qna_list.do";
	}
	
}
