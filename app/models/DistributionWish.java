package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class DistributionWish extends Model {

	@OneToOne
	public Item item;

	public int period;
	public int period1;
	public int period2;
	public int period3;
	@Override
	public String toString() {
		return "DistributionWish [item=" + item + ", period=" + period + ", period1=" + period1 + ", period2=" + period2 + ", period3=" + period3 + "]";
	}	
}
