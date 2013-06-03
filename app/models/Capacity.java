package models;

import play.db.jpa.Model;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Capacity extends Model {

	@OneToOne
	public Item item;
	public int time;
	public int setupTime;
	public int shift;
	public int overtime;
	
}
