package models;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class WaitingList extends Model {
	
	
	public int amount;
	public int setupTime;
	
	@OneToOne
	public Item item;

}
