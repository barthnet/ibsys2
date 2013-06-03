package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class DistributionWish extends Model {

	public String item;

	public int period;
	public int period1;
	public int period2;
	public int period3;

	@Override
	public String toString() {
		return "DistributionWish [item=" + item + ", period=" + period + ", period1=" + period1
				+ ", period2=" + period2 + ", period3=" + period3 + "]";
	}

	public void merge(DistributionWish other) {

		this.period = other.period;
		this.period1 = other.period1;
		this.period2 = other.period2;
		this.period3 = other.period3;

//		this.item.name = other.item.name;
//		this.item.name_en = other.item.name_en;
//		this.item.price = other.item.price;
//		this.item.type = other.item.type;
		this.save();
	}

}
