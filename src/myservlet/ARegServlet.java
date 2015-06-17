package myservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Activity;
import entity.Joiner;
import entity.Manager;

/**
 * Servlet implementation class ARegServlet
 */
@WebServlet("/ARegServlet")
public class ARegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ARegServlet() {
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

	protected String transf(String date) {
		String[] ans = date.split("T");
		return ans[0];
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("test!!! MInfoservlet");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		System.out.println("here is ARegServlet");
		//HttpSession session=request.getSession();
		//Manager m = (Manager)session.getAttribute("regManager");
		Activity a = new Activity();
		String activity_name, organized_club, activity_describe, activity_location, club_name;
		String start_time, end_time;
		String activity_poster;
		String[] activity_type;
		
		Manager m = new Manager();
		
		try
		{
			activity_name = request.getParameter("activity_name");
			activity_describe = request.getParameter("activity_describe");
			activity_location = request.getParameter("activity_location");
			start_time = request.getParameter("start_time");
			end_time = request.getParameter("end_time");
			activity_type = request.getParameterValues("activity_type");
			activity_poster = request.getParameter("activity_poster");
			club_name = request.getParameter("club_name");
			
			//join_time = request.getParameter("join_time");
			//join_number = request.getParameter("join_number");
			//join_students = request.getParameterValues("join_students");
			
			if(activity_name!=null)
				a.setActivity_name(activity_name);
			if(activity_describe!=null)
				a.setActivity_describe(activity_describe);
			if(activity_location!=null)
				a.setActivity_location(activity_location);

			if(start_time!=null)
				a.setStart_time(transf(start_time));
			if(end_time!=null)
				a.setEnd_time(transf(end_time));
			if(activity_type!=null)
				a.setActivity_type(activity_type);
			if(activity_poster!=null)
				a.setActivity_poster(activity_poster);
			a.setJoin_number(String.valueOf(0));
			List<Joiner> js = new ArrayList<Joiner>();
			a.setJoiners(js);
			a.setOrganized_club(club_name);
			
			String type_final = "";
			Boolean flag = true;
			for(String i:activity_type) {
				if(flag) {
					type_final+=i; flag = false;
				}
				else {
					type_final = type_final + "+" + i;
				}
			}
			
			try
			{
				String driver = "com.mysql.jdbc.Driver";
				String url = "jdbc:mysql://127.0.0.1:3306/scutcs";
				String user = "root";
				String password = "";
				Class.forName(driver);
				Connection conn = DriverManager.getConnection(url, user, password);
				if(!conn.isClosed()) 
			        System.out.println("Succeeded connecting to the Database!");
				Statement statement = conn.createStatement();
				// save to activity
				String sql1 = "insert into activity(name, organized, type, poster, adescibe, starttime, endtime,"
							  + "location, joiner, number) values(?,?,?,?,?,?,?,?,?,?)";
			    PreparedStatement ps1=conn.prepareStatement(sql1);
			    System.out.println(sql1);
				ps1.setString(1, activity_name);
			    ps1.setString(2, club_name);
				ps1.setString(3, type_final);
			    ps1.setString(4, activity_poster);
			    ps1.setString(5, activity_describe);
			    ps1.setString(6, transf(start_time));
			    ps1.setString(7, transf(end_time));
			    ps1.setString(8, activity_location);
			    ps1.setString(9, "无");
			    ps1.setInt(10, 0);
			    int i=ps1.executeUpdate();
			        
			    // update manager table
			    sql1 = "select * from managertable";
			    ResultSet rs = statement.executeQuery(sql1);
			    String ha = "";
			    while(rs.next()) {
			        String mn = rs.getString("name");
			        if(mn.compareTo(club_name) == 0) {
			        	ha = rs.getString("holdactivities");
			        	if(ha.compareTo("无") == 0) 
			        		ha = activity_name;
			        	else
			        		ha = ha + "+" + activity_name;
			        	
			        	// register a manager
			        	m.setClub_name(club_name);
			        	m.setClub_describe(rs.getString("sdescribe"));
			        	m.setManager_password(rs.getString("password"));
			        	String[] club_type = (rs.getString("type")).split("[+]");
			        	m.setClub_type(club_type);
			        	List<String> hold_activities = new ArrayList<String>();
			        	for(String st:(ha.split("[+]")))
			        		hold_activities.add(st);
			        	m.setHold_activities(hold_activities);
			        	
			        	break;
			        }
			    }
			    sql1 = "update managertable set holdactivities='" + ha
				       + "' where name='" + club_name + "'";
			    PreparedStatement pstmt;
				pstmt = (PreparedStatement)conn.prepareStatement(sql1);
				i = pstmt.executeUpdate();
				System.out.println(sql1);
				    
				//save to session
			    request.getSession().setAttribute("regManager", m);//注册一个manager即club
				request.getRequestDispatcher("/MInfo.jsp").forward(request, response);//服务器内部跳转 传递到ManagerInfo
			} catch(ClassNotFoundException e) {
				System.out.println("Sorry,can`t find the Driver!"); 
				e.printStackTrace();
			} catch(SQLException e) {
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("test!!! Ainfo前");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
