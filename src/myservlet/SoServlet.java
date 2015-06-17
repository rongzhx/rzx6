package myservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Activity;
import entity.ActivityList;
import entity.Joiner;

@WebServlet("/SoServlet")
public class SoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the object.
	 */
	public SoServlet() {
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("here is AInfoServlet");
		doPost(request,response);
	}
	/*
	public static String converterToFirstSpell(String chines){         
        String pinyinName = "";  
        char[] nameChar = chines.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < nameChar.length; i++) {  
            if (nameChar[i] > 128) {  
                try {  
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);  
                } catch (BadHanyuPinyinOutputFormatCombination e) {  
                    e.printStackTrace();  
                }  
            }else{  
                pinyinName += nameChar[i];  
            }  
        }  
        return pinyinName;  
    }
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
	
	protected int comp2(String a, String b) {
		String[] as = a.split("[-]");
		String[] bs = b.split("[-]");
		for(int i = 0;i < 3;i++) {
			if(Integer.parseInt(as[i]) > Integer.parseInt(bs[i])) return -1;
			else if(Integer.parseInt(as[i]) < Integer.parseInt(bs[i])) return 1;
		}
		return 0;
	}
	
	protected String tsf(String[] s) {
		boolean flag = true;
		String ans = "";
		for(String i:s) {
			if(flag) {
				ans = i; flag = false;
			}
			else ans+=i;
		}
		return ans;
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		System.out.println("here is SortServlet");
		String sort_type = request.getParameter("sort_type");
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
				List<Activity> templ = new ArrayList<Activity>();
				templ = al.getActivities();
				
				// sort by club_name
				if(sort_type.compareTo("活动名称") == 0) {
					Collections.sort(templ, new Comparator<Activity>() {
			            public int compare(Activity arg0, Activity arg1) {
			                Hanyu hanyu = new Hanyu();
			            	String arg0_name = hanyu.getStringPinYin(arg0.getActivity_name());
			            	String arg1_name = hanyu.getStringPinYin(arg1.getActivity_name());
			                return arg0_name.compareTo(arg1_name);
			            }
			        });
				}
				
				// sort by start time
				if(sort_type.compareTo("报名开始时间") == 0) {
					Collections.sort(templ, new Comparator<Activity>() {
			            public int compare(Activity arg0, Activity arg1) {
			            	String arg0_name = arg0.getStart_time();
			            	String arg1_name = arg1.getStart_time();
			                return comp2(arg0_name, arg1_name);
			            }
			        });
				}
				
				// sort by end time
				if(sort_type.compareTo("报名截止时间") == 0) {
					Collections.sort(templ, new Comparator<Activity>() {
			            public int compare(Activity arg0, Activity arg1) {
			            	String arg0_name = arg0.getEnd_time();
			            	String arg1_name = arg1.getEnd_time();
			                return comp2(arg0_name, arg1_name);
			            }
			        });
				}
				
				// sort by organized club
				if(sort_type.compareTo("活动组织者") == 0) {
					Collections.sort(templ, new Comparator<Activity>() {
			            public int compare(Activity arg0, Activity arg1) {
			            	String arg0_name = converterToFirstSpell(arg0.getOrganized_club());
			            	String arg1_name = converterToFirstSpell(arg1.getOrganized_club());
			                return comp2(arg0_name, arg1_name);
			            }
			        });
				}
				
				// sort by activity type
				if(sort_type.compareTo("活动类型") == 0) {
					Collections.sort(templ, new Comparator<Activity>() {
			            public int compare(Activity arg0, Activity arg1) {
			            	String arg0_name = converterToFirstSpell(tsf(arg0.getActivity_type()));
			            	String arg1_name = converterToFirstSpell(tsf(arg1.getActivity_type()));
			                return comp2(arg0_name, arg1_name);
			            }
			        });
				}
				
				al.setActivities(templ);
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
	}

}
