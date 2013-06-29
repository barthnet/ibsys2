package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class DispositionOrder extends Model {

	public String item;

	public int usedP1;
	public int usedP2;
	public int usedP3;

	public int consumptionPeriod0;
	public int consumptionPeriod1;
	public int consumptionPeriod2;
	public int consumptionPeriod3;

	public double price;
	public double deliveryCost;
	public double deliveryTime;
	public double deliveryVariance;
	public int discount;
	
	public int amount;
	
	/*
	 * mode 5: normal
       mode 4: fast
       mode 3: JIT
       mode 2: cheap vendor
       mode 1: special order
	 */
	public int mode;
	public int quantity;
	public double expectedArrival;

	public Item getItemAsObject() {
		return Item.find("byItem", this.item).first();
	}

	public static void merge(List<DispositionOrder> listToMerge) {
		for (DispositionOrder order : listToMerge) {
			DispositionOrder o = DispositionOrder.find("byItem", order.item).first();
			o.merge(order);
			o.save();
		}
	}

	public void merge(DispositionOrder order) {
		// TODO merge
	}

	@Override
	public String toString() {
		return "DispositionOrder [item=" + item + ", usedP1=" + usedP1 + ", usedP2=" + usedP2 + ", usedP3=" + usedP3 + ", price=" + price + ", deliveryCost="
				+ deliveryCost + ", deliveryTime=" + deliveryTime + ", deliveryVariance=" + deliveryVariance + ", discount=" + discount + "]";
	}

}
