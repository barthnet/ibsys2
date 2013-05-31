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
	
	public int orderNumber;
	public int period;
	public int amount;
	
	@OneToOne
	public Item item;

}
