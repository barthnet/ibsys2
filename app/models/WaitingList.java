package models;

import javax.persistence.*;

import play.db.jpa.Model;

/**
 * 
 * @author sven
 *
 */
@Entity
public class WaitingList extends Model {
	
//	public int order;
	public int period;
	public int amount;
	
	@OneToOne
	public Item item;

}
