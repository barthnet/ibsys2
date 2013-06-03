package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class ProductionOrder extends Model {

	@OneToOne
	public Item item;
	public int amount;
}
