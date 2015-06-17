package myservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Student;

/**
 * Servlet implementation class SRegServlet
 */
@WebServlet("/SRegServlet")
public class SRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SRegServlet() {
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
		String student_phone="", student_nickname="", student_name="", student_id="", student_password="", student_university="",student_college="";
		String student_sex="";
		String[] join_activities={""};
		try
		{
			student_nickname =  request.getParameter("student_nickname");
			student_name = request.getParameter("student_name");
			student_phone = request.getParameter("student_phone");
			System.out.println(student_phone);
			student_id = request.getParameter("student_id");
			student_password = request.getParameter("student_password");
			student_university = request.getParameter("student_university");
			student_college = request.getParameter("student_college");
			student_sex = request.getParameter("student_sex");
			join_activities = request.getParameterValues("join_activities");
			if(student_nickname!=null)
				s.setStudent_nickname(student_nickname);
			if(student_name!=null)
				s.setStudent_name(student_name);
			if(student_phone!=null)
				s.setStudent_phone(student_phone);
			if(student_id!=null)
				s.setStudent_id(student_id);
			if(student_password!=null)
				s.setStudent_password(student_password);
			if(student_university!=null)
				s.setStudent_university(student_university);
			if(student_college!=null)
				s.setStudent_college(student_college);
			if(student_sex!=null)
				s.setStudent_sex(student_sex);
			//if(join_activities!=null)
				//s.setJoin_activities(join_activities);
			
			//save to session
			request.getSession().setAttribute("regStudent", s);

			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://127.0.0.1:3306/scutcs?useUnicode=true";
			String user = "root";
			String password = "";
			try {
				Class.forName(driver);
				Connection conn = DriverManager.getConnection(url, user, password);
				if(!conn.isClosed()) 
		             System.out.println("Succeeded connecting to the Database!");
	   		    String sql1 = "insert into studenttable(username, realname, phone, id, password, university, major, joinactivities, sex)";
	   		    sql1 = sql1 + " values(?,?,?,?,?,?,?,?,?)";
		        PreparedStatement ps1=conn.prepareStatement(sql1);
		        System.out.println(sql1);
			    ps1.setString(1, student_nickname);
		        ps1.setString(2, student_name);
			    ps1.setString(3, student_phone);
		        ps1.setString(4, student_id);
		        ps1.setString(5, student_password);
		        ps1.setString(6, student_university);
		        ps1.setString(7, student_college);
		        ps1.setString(8, "无");
		        ps1.setString(9, student_sex);
		        int i = ps1.executeUpdate();
		        System.out.println("execute sql successfully");
			} catch(ClassNotFoundException e) {
				System.out.println("Sorry,can`t find the Driver!"); 
		        e.printStackTrace();
		    } catch(SQLException e) {
		        e.printStackTrace();
		    } catch(Exception e) {
		        e.printStackTrace();
		    }
			request.getRequestDispatcher("/SInfo.jsp").forward(request, response);//服务器内部跳转
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
