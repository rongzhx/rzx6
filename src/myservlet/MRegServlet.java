package myservlet;

import java.io.IOException;

import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Manager;

/**
 * Servlet implementation class MRegServlet
 */
@WebServlet("/MRegServlet")
public class MRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MRegServlet() {
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
		String[] club_type={""};
		//club_icon
		try
		{
			club_name = request.getParameter("club_name");
			club_describe = request.getParameter("club_describe");
			manager_password = request.getParameter("manager_password");
			club_type = request.getParameterValues("club_type");
			if(club_name!=null)
				m.setClub_name(club_name);
			if(club_describe!=null)
				m.setClub_describe(club_describe);
			if(manager_password!=null)
				m.setManager_password(manager_password);
			if(club_type!=null)
				m.setClub_type(club_type);
			//save to session
			String type_final = "";
			Boolean a = true;
			for(String i:club_type) {
				if(a) {
					type_final+=i; a = false;
				}
				else {
					type_final = type_final + "+" + i;
				}
			}
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://127.0.0.1:3306/scutcs?useUnicode=true";
			String user = "root";
			String password = "";
			try {
				Class.forName(driver);
				Connection conn = DriverManager.getConnection(url, user, password);
				if(!conn.isClosed()) 
		             System.out.println("Succeeded connecting to the Database!");
	   		    String sql1 = "insert into managertable(name, icon, type, sdescribe, password, holdactivities) values(?,?,?,?,?,?)";
		        PreparedStatement ps1=conn.prepareStatement(sql1);
		        System.out.println(sql1);
			    ps1.setString(1, club_name);
		        ps1.setString(2, "http://www.baidu.com");
			    ps1.setString(3, type_final);
		        ps1.setString(4, club_describe);
		        ps1.setString(5, manager_password);
		        ps1.setString(6, "无");
		        int i=ps1.executeUpdate();
		        ps1.close();
		        conn.close();
				System.out.println("execute sql successfully");
			} catch(ClassNotFoundException e) {
				System.out.println("Sorry,can`t find the Driver!"); 
		        e.printStackTrace();
		    } catch(SQLException e) {
		        e.printStackTrace();
		    } catch(Exception e) {
		        e.printStackTrace();
		    }
			request.getSession().setAttribute("regManager", m);//注册一个manager即club
			request.getRequestDispatcher("/MInfo.jsp").forward(request, response);//服务器内部跳转 传递到ManagerInfo
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
