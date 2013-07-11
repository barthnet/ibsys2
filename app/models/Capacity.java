package models;

import java.util.List;

import play.db.jpa.Model;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Capacity extends Model {

	public int workplace;
	public String user;

	public int time;
	public int setupTime;
	public int shift;
	public int overtime;
	public int totaltime;
	
	public int originalTime;
	public int originalSetupTime;

	public Item getWorkplaceAsObject() {
		return Workplace.find("byWorkplaceIdAndUser", this.workplace, this.user).first();
	}

	public static void merge(List<Capacity> listToMerge) {
		for (Capacity cap : listToMerge) {
			Capacity c = Capacity.find("byWorkplaceAndUser", cap.workplace, cap.user).first();
			c.merge(cap);
			c.save();
		}
	}
	
	public static void deleteAll(String userName) {
		List<Capacity> caps = Capacity.find("byUser", userName).fetch();
		for (Capacity capacity : caps) {
			capacity.delete();
		}
	}

	public void merge(Capacity cap) {
		this.time = cap.time;
		this.setupTime = cap.setupTime;
		this.shift = cap.shift;
		this.overtime = cap.overtime;
		this.totaltime = cap.totaltime;
	}

	@Override
	public String toString() {
		return "Capacity [workplace=" + workplace + ", user=" + user + ", time=" + time + ", setupTime=" + setupTime + ", shift=" + shift + ", overtime="
				+ overtime + ", totaltime=" + totaltime + ", originalTime=" + originalTime + ", originalSetupTime=" + originalSetupTime + "]";
	}

	
}
