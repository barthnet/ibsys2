package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

/**
 * 
 * @author sven
 *
 */
@Entity
public class Workplace extends Model {
	
	public int workplaceId;
	public String name;
	public String name_en;
	
	@OneToMany
	public List<WaitingList> waitingList;	
	@OneToOne
	public WaitingList processList;
	@OneToMany
	public List<ProductionOrder> productionPlanList;
	
}
