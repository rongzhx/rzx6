package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Activity;
import entity.Joiner;

@WebServlet("/MADInfoServlet")
public class MADIfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the object.
	 */
	public MADIfoServlet() {
		super();
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
		System.out.println("test!!!!!!!!!!!");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		Activity a = new Activity();
		Joiner j = new Joiner();
		List<Joiner> js = new ArrayList<Joiner>();
		String name = request.getParameter("activity_name");
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
					String aname = rs.getString("name");
					if(name.compareTo(aname) == 0) {
						a.setActivity_describe(rs.getString("adescibe"));
						a.setActivity_location(rs.getString("location"));
						a.setActivity_name(aname);
						a.setActivity_poster(rs.getString("poster"));
						a.setActivity_type((rs.getString("type")).split("[+]"));
						a.setEnd_time(rs.getString("endtime"));
						a.setStart_time(rs.getString("starttime"));
						a.setOrganized_club(rs.getString("organized"));
						a.setJoin_number(String.valueOf(rs.getInt("number")));
						if((rs.getString("joiner")).compareTo("нч") == 0) {}
						else {
							String[] joiners = (rs.getString("joiner")).split("\\|");
							for(String s: joiners) {
								String[] attribute = s.split("[+]");
								j.setJoin_students(attribute[0]);
								j.setJoin_time(attribute[1]);
								js.add(j);
							}
						}
						a.setJoiners(js);
						request.getSession().setAttribute("regActivity", a);
						request.getRequestDispatcher("MADInfo.jsp").forward(request, response);
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
