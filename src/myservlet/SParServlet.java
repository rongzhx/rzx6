package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Student;

@WebServlet("/SParServlet")
public class SParServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the object.
	 */
	public SParServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("here is SParServlet");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		Date date = new Date();
        DateFormat df1 = DateFormat.getDateInstance();
		String s = df1.format(date);
		
		Student student = new Student();
		
		try
		{
			String sn = request.getParameter("student_nickname");
			String an = request.getParameter("activity_name");
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://127.0.0.1:3306/scutcs?useUnicode=true";
			String user = "root";
			String password = "";
			try {
				Class.forName(driver);
				Connection conn = DriverManager.getConnection(url, user, password);
				if(!conn.isClosed()) 
		             System.out.println("Succeeded connecting to the Database!");
				Statement statement = conn.createStatement();
				// save to student table
				String sql = "select * from studenttable";
				ResultSet rs = statement.executeQuery(sql);
				String join_activity = "";
				while(rs.next()) {
					String ssn = rs.getString("username");
					if(ssn.compareTo(sn) == 0) {
						join_activity = rs.getString("joinactivities");
						if(join_activity.compareTo("无") == 0) 
							join_activity = an;
						else
							join_activity = join_activity + "+" + an;
						
						// register a student
						student.setStudent_nickname(ssn);
						student.setStudent_name(rs.getString("realname"));
						student.setStudent_phone(rs.getString("phone"));
						student.setStudent_id(rs.getString("id"));
						student.setStudent_password(rs.getString("password"));
						student.setStudent_university(rs.getString("university"));
						student.setStudent_college(rs.getString("major"));
						student.setStudent_sex(rs.getString("sex"));
						String[] temp = join_activity.split("[+]");
						List<String> ac = new ArrayList<String>();
						for(String i : temp) ac.add(i);
					    student.setJoin_activities(ac);
					    
						break;
					}
				}
				String sql1 = "update studenttable set joinactivities='" + join_activity
						      + "' where username='" + sn + "'";
				PreparedStatement pstmt;
			    pstmt = (PreparedStatement)conn.prepareStatement(sql1);
			    int i = pstmt.executeUpdate();
			    System.out.println(sql1);
			    
			    // save to activity table
			    sql = "select * from activity";
			    rs = statement.executeQuery(sql);
			    String joiner = "";
			    int number = 0;
			    while(rs.next()) {
			    	String aan = rs.getString("name");
			    	if(aan.compareTo(an) == 0) {
			    		joiner = rs.getString("joiner");
			    		if(joiner.compareTo("无") == 0) 
			    			joiner = sn + "+" + s;
			    		else
			    			joiner = joiner + "|" + sn + "+" + s;
			    		number = rs.getInt("number");
			    		number++;
			    		break;
			    	}
			    }
			    sql1 = "update activity set joiner='" + joiner
					      + "' where name='" + an + "'";
			    pstmt = (PreparedStatement)conn.prepareStatement(sql1);
			    i = pstmt.executeUpdate();
			    System.out.println(sql1);
			    sql1 = "update activity set number='" + String.valueOf(number)
					      + "' where name='" + an + "'";
			    pstmt = (PreparedStatement)conn.prepareStatement(sql1);
			    i = pstmt.executeUpdate();
			    System.out.println(sql1);
			    
			    request.getSession().setAttribute("regStudent", student);//注册一个manager即club
				request.getRequestDispatcher("SInfo.jsp").forward(request, response);
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

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
