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

import entity.Manager;

/**
 * Servlet implementation class MLoginServlet
 */
@WebServlet("/MLoginServlet")
public class MLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MLoginServlet() {
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
		System.out.println("test!!! Post !!!");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
				
		Manager m = new Manager();
		String club_name="", club_describe="",manager_password="";
		String[] club_type={""}, hold_activities={""};
		
		club_name = request.getParameter("club_name");
		manager_password = request.getParameter("manager_password");
		System.out.println(club_name);
		System.out.println(manager_password);
		//club_icon
		try
		{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://127.0.0.1:3306/scutcs";
			String user = "root";
			String password = "";
			try {
				Class.forName(driver);
				System.out.println("here is the test");
				Connection conn = DriverManager.getConnection(url, user, password);
				if(!conn.isClosed()) 
		             System.out.println("Succeeded connecting to the Database!");
				Statement statement = conn.createStatement();
				String sql = "select * from managertable";// where name=\'" + club_name + "'";
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()) {
					String name = rs.getString("name");
					if(club_name.compareTo(name) == 0) {
						String pw = rs.getString("password");
						System.out.println(pw);
						if(pw.compareTo(manager_password) == 0) {
							m.setClub_name(club_name);
							m.setManager_password(manager_password);
							m.setClub_describe(rs.getString("sdescribe"));
							m.setClub_type((rs.getString("type")).split("[+]"));
							String[] temp = (rs.getString("holdactivities")).split("[+]");
							List<String> ac = new ArrayList<String>();
							for(String i:temp) ac.add(i);
							for(String i:temp) System.out.println(i);
							m.setHold_activities(ac);
							request.getSession().setAttribute("regManager", m);//注册一个manager即club
							request.getRequestDispatcher("MInfo.jsp").forward(request, response);
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
