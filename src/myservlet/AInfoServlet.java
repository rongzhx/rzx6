package myservlet;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

import entity.Activity;
import entity.ActivityList;
import entity.Joiner;
import entity.Manager;

@WebServlet("/AInfoServlet")
public class AInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the object.
	 */
	public AInfoServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("here is AInfoServlet");
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected int comp(String a) {
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();
		String now = df.format(date);
		String[] ns = now.split("[-]");
		String[] as = a.split("[-]");
		for(int i = 0;i < 3;i++) {
			if(Integer.parseInt(ns[i]) > Integer.parseInt(as[i])) return -1;
			else if(Integer.parseInt(ns[i]) < Integer.parseInt(as[i])) return 1;
		}
		return 0;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		System.out.println("here is AInfoServlet");
		ActivityList al = new ActivityList();
		List<Joiner> jn = new ArrayList<Joiner>();
		Joiner j = new Joiner();
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
				String sql = "select * from activity";
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()) {
					String st = rs.getString("starttime");
					String et = rs.getString("endtime");
					System.out.println(st + " + " + et);
					if(comp(et) >= 0 && comp(st) <= 0) {
						Activity a = new Activity();
						a.setActivity_name(rs.getString("name"));
						a.setOrganized_club(rs.getString("organized"));
						a.setActivity_type((rs.getString("type")).split("[+]"));
						a.setActivity_poster(rs.getString("poster"));
						a.setActivity_describe(rs.getString("adescibe"));
						a.setStart_time(rs.getString("starttime"));
						a.setEnd_time(rs.getString("endtime"));
						a.setActivity_location(rs.getString("location"));
						a.setJoin_number(String.valueOf(rs.getInt("number")));
						if((rs.getString("joiner")).compareTo("无") == 0) {}
						else {
							String[] joiners = (rs.getString("joiner")).split("\\|");
							for(String s: joiners) {
								String[] attribute = s.split("[+]");
								j.setJoin_students(attribute[0]);
								j.setJoin_time(attribute[1]);
								jn.add(j);
							}
						}
						a.setJoiners(jn);
						System.out.println(a.getActivity_name());
					    al.addActivity(a);
					}
				}
				for(Activity temp:(al.getActivities())) System.out.println(temp.getActivity_name());
				request.getSession().setAttribute("ListActivities", al);//注册一个manager即club
				request.getRequestDispatcher("AInfo.jsp").forward(request, response);
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

		//save to session
		request.getSession().setAttribute("ListActivities", al);//注册一个activity
	}
	
	public void init() throws ServletException {
		// Put your code here
	}

}
