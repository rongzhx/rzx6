package entity;

import java.util.ArrayList;
import java.util.List;

public class Manager {
	private String club_name = "";
	//club_icon
	private String[] club_type = {""};
	private String club_describe = "";
	private String manager_password = "";
	private List<String> hold_activities= new ArrayList<String>();
	
	public Manager()
	{
		
	}

	public String getClub_name() {
		return club_name;
	}

	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}

	public String[] getClub_type() {
		return club_type;
	}

	public void setClub_type(String[] club_type) {
		this.club_type = club_type;
	}

	public String getClub_describe() {
		return club_describe;
	}

	public void setClub_describe(String club_describe) {
		this.club_describe = club_describe;
	}

	public String getManager_password() {
		return manager_password;
	}

	public void setManager_password(String manager_password) {
		this.manager_password = manager_password;		
	}

	public List<String> getHold_activities() {
		return hold_activities;
	}

	public void setHold_activities(List<String> hold_activities) {
		this.hold_activities = hold_activities;
	}




	
}
