package entity;

import java.util.ArrayList;
import java.util.List;

public class ActivityList {
	private List<Activity> activities = new ArrayList<Activity>();
	public ActivityList()
	{
		
	}
	public List<Activity> getActivities() {
		return activities;
	}
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	public void addActivity(Activity ac)
	{
		Activity nac = new Activity(ac);
		activities.add(ac);
	}
	
}
