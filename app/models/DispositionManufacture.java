package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class DispositionManufacture extends Model {
	
	@OneToOne
	public Item item;
	
	public int distributionWish;
	public int stock;
	public int safetyStock;
	public int waitingList;
	public int processList;
	public int production;
	
}
