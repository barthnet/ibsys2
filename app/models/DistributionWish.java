package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class DistributionWish extends Model {

	public String item;

	public int period0;
	public int period1;
	public int period2;
	public int period3;

	public Item getItemAsObject() {
		return Item.find("byItemId", this.item).first();
	}

	public static void merge(List<DistributionWish> listToMerge) {
		for (DistributionWish dist : listToMerge) {
			DistributionWish d = DistributionWish.find("byItem", dist.item).first();
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
	}

	@Override
	public String toString() {
		return "DistributionWish [item=" + item + ", period0=" + period0 + ", period1=" + period1 + ", period2=" + period2 + ", period3=" + period3 + "]";
	}

}
