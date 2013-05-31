package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class DispositionOrder extends Model {
	
	public int usedP1;
	public int usedP2;
	public int usedP3;
	
	public double price;
	public int deliveryCost;
	public double deliveryTime;
	public double deliveryVariance;
	public int discount;
	
	@OneToOne
	public Item item;
	
}
