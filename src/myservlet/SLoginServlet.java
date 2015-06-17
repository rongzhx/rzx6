package myservlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Student;

/**
 * Servlet implementation class SInfoServlet
 */
@WebServlet("/SLoginServlet")
public class SLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SLoginServlet() {
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
		System.out.println("test!!!!!!!!!!!");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		Student s = new Student();		
		String sun = request.getParameter("student_nickname");
		String spw = request.getParameter("student_password");
		try
		{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://127.0.0.1:3306/scutcs";
			String user = "root";
			String password = "";
			try {
				Class.forName(driver);
				Connection conn = DriverManager.getConnection(url, user, password);
				if(!conn.isClosed()) 
		             System.out.println("Succeeded connecting to the Database!");
				Statement statement = conn.createStatement();
				String sql = "select * from studenttable";
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()) {
					String username = rs.getString("username");
					if(username.compareTo(sun) == 0) {
						String pw = rs.getString("password");
						System.out.println(pw + "　" + spw);
						if(pw.compareTo(spw) == 0) {
							s.setStudent_nickname(sun);
							s.setStudent_name(rs.getString("realname"));
							s.setStudent_university(rs.getString("university"));
							s.setStudent_college(rs.getString("major"));
							s.setStudent_id(rs.getString("id"));
							s.setStudent_phone(rs.getString("phone"));
							s.setStudent_sex(rs.getString("sex"));
							String[] temp = (rs.getString("joinactivities")).split("[+]");
							List<String> ac = new ArrayList<String>();
							for(String i : temp) ac.add(i);
						    s.setJoin_activities(ac);
							request.getSession().setAttribute("regStudent", s);//注册一个manager即club
							request.getRequestDispatcher("SInfo.jsp").forward(request, response);
						}
						else
							response.sendRedirect(request.getContextPath()+"/WrongOP.jsp");//请求重定向
					}
				}
			} catch(ClassNotFoundException e) {
				System.out.println("Sorry,can`t find the Driver!"); 
	            e.printStackTrace();
	        } catch(SQLException e) {
	            e.printStackTrace();
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
