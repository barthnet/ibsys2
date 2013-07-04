package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class DistributionWish extends Model {

	public String item;
	
	public int directSale;
	public double price;
	public double penalty;

	public int period0;
	public int period1;
	public int period2;
	public int period3;
	
	public String user;
	
	public DistributionWish clone() {
		DistributionWish w = new DistributionWish();
		w.item = this.item;
		return w;
	}
	
	public static void deleteAll(String userName) {
		List<DistributionWish> caps = DistributionWish.find("byUser", userName).fetch();
		for (DistributionWish capacity : caps) {
			capacity.delete();
		}
	}

	public Item getItemAsObject() {
		return Item.find("byItemIdAndUser", this.item, this.user).first();
	}

	public static void merge(List<DistributionWish> listToMerge) {
		for (DistributionWish dist : listToMerge) {
			DistributionWish d = DistributionWish.find("byItemAndUser", dist.item, dist.user).first();
			if (d == null) d = new DistributionWish();
			d.merge(dist);
			d.save();
		}
	}

	public void merge(DistributionWish dist) {
		this.item = dist.item;
		this.period0 = dist.period0;
		this.period1 = dist.period1;
		this.period2 = dist.period2;
		this.period3 = dist.period3;
		this.directSale = dist.directSale;
		this.penalty = dist.penalty;
		this.price = dist.price;
	}

	@Override
	public String toString() {
		return "DistributionWish [item=" + item + ", period0=" + period0 + ", period1=" + period1 + ", period2=" + period2 + ", period3=" + period3 + "]";
	}

}
