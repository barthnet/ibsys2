package models;

import javax.persistence.*;

@Entity
public class WaitingList {
	
	
	public int amount;
	public int setupTime;
	
	@OneToOne
	public Item item;

}
