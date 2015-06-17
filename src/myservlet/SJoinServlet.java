package myservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Manager;

/**
 * Servlet implementation class SJoinServlet
 */
@WebServlet("/SJoinServlet")
public class SJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SJoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//学生报名活动est
		System.out.println("test!!! Post !!!");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
	
		
		try
		{
			String s = request.getParameter("student_name");
			System.out.println(s);
			request.getRequestDispatcher("SInfo.jsp").forward(request, response);
			//request.getRequestDispatcher("/MInfo.jsp").forward(request, response);//服务器内部跳转 传递到ManagerInfo
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
