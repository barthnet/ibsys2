package models;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class WaitingList extends Model {
	
	public int order;
	public int period;
	public int amount;
	public int setupTime;
	public int processTime;
	
	@OneToOne
	public Item item;

}
