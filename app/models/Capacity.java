package models;

import java.util.List;

import play.db.jpa.Model;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Capacity extends Model {

	public int workplace;

	public int time;
	public int setupTime;
	public int shift;
	public int overtime;
	public int totaltime;

	public Item getWorkplaceAsObject() {
		return Workplace.find("byWorkplaceId", this.workplace).first();
	}

	public static void merge(List<Capacity> listToMerge) {
		for (Capacity cap : listToMerge) {
			Capacity c = Capacity.find("byWorkplace", cap.workplace).first();
			c.merge(cap);
			c.save();
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
		return "Capacity [workplace=" + workplace + ", time=" + time + ", setupTime=" + setupTime + ", shift=" + shift + ", overtime=" + overtime
				+ ", totaltime=" + totaltime + "]";
	}

}
