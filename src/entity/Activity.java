package entity;

import java.util.ArrayList;
import java.util.List;

public class Activity {
	private String activity_name;
	private String organized_club;
	private String[] activity_type;
	private String activity_poster;
	private String activity_describe;
	private String start_time;
	private String end_time;
	private String activity_location;
	
	private List<Joiner> joiners= new ArrayList<Joiner>();
	
	private String join_number;
	
	public Activity()
	{
		
	}

	public Activity(Activity ac) {
		this.setActivity_name(ac.getActivity_name());
		this.setOrganized_club(ac.getOrganized_club());
		this.setActivity_describe(ac.getActivity_describe());
		this.setActivity_location(ac.getActivity_location());
		this.setActivity_poster(ac.getActivity_poster());
		this.setActivity_type(ac.getActivity_type());
		this.setEnd_time(ac.getEnd_time());
		this.setStart_time(ac.getStart_time());
		this.setJoin_number(ac.getJoin_number());
		this.setJoiners(ac.getJoiners());
	}
	
	public String getActivity_name() {
		return activity_name;
	}

	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}

	public String getOrganized_club() {
		return organized_club;
	}

	public void setOrganized_club(String organized_club) {
		this.organized_club = organized_club;
	}

	public String[] getActivity_type() {
		return activity_type;
	}

	public void setActivity_type(String[] activity_type) {
		this.activity_type = activity_type;
	}

	public String getActivity_poster() {
		return activity_poster;
	}

	public void setActivity_poster(String activity_poster) {
		this.activity_poster = activity_poster;
	}

	public String getActivity_describe() {
		return activity_describe;
	}

	public void setActivity_describe(String activity_describe) {
		this.activity_describe = activity_describe;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getActivity_location() {
		return activity_location;
	}

	public void setActivity_location(String activity_location) {
		this.activity_location = activity_location;
	}


	public List<Joiner> getJoiners() {
		return joiners;
	}

	public void setJoiners(List<Joiner> joiners) {
		this.joiners = joiners;
	}

	public String getJoin_number() {
		return join_number;
	}

	public void setJoin_number(String join_number) {
		this.join_number = join_number;
	}

	
	
	
}
