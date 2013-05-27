package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class ItemTime extends Model {
	
	@OneToOne
	public Workplace workplace;
	
	@OneToOne
	public Item item;
	
	public int setupTime;
	
	public int processTime;
	
}
