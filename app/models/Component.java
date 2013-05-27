package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Component extends Model {

	@OneToOne
	public Item item;
	public int amount;
	
}
