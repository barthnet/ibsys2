package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class ProductionOrder extends Model {

	
	public String item;

	public int orderNumber;
	public int amount;

	@Override
	public String toString() {
		return "ProductionOrder [item=" + item + ", orderNumber=" + orderNumber + ", amount=" + amount + "]";
	}

}
