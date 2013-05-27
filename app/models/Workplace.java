package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Workplace extends Model {
	
	public int workplaceId;
	public String name;
	public String name_en;
	
	@OneToMany
	public List<WaitingList> waitingList;	
	@OneToMany
	public List<WaitingList> processList;
	@OneToMany
	public List<WaitingList> productionPlanList;
	
}
