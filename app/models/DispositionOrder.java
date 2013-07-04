package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class DispositionOrder extends Model {

	public String item;
	public String user;

	public int usedP1;
	public int usedP2;
	public int usedP3;

	public int consumptionPeriod0;
	public int consumptionPeriod1;
	public int consumptionPeriod2;
	public int consumptionPeriod3;
	
	public int futureStock0;
	public int futureStock1;
	public int futureStock2;
	public int futureStock3;

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
	
	public DispositionOrder clone() {
		DispositionOrder o = new DispositionOrder();
		o.item = this.item;
		o.usedP1 = this.usedP1;
		o.usedP2 = this.usedP2;
		o.usedP3 = this.usedP3;
		o.price = this.price;
		o.deliveryCost = this.deliveryCost;
		o.deliveryTime = this.deliveryTime;
		o.deliveryVariance = this.deliveryVariance;
		o.discount = this.discount;
		return o;
	}
	
	public static void deleteAll(String userName) {
		List<DispositionOrder> caps = DispositionOrder.find("byUser", userName).fetch();
		for (DispositionOrder capacity : caps) {
			capacity.delete();
		}
	}

	public Item getItemAsObject() {
		return Item.find("byItemAndUser", this.item, this.user).first();
	}

	public static void merge(List<DispositionOrder> listToMerge) {
		for (DispositionOrder order : listToMerge) {
			DispositionOrder o = DispositionOrder.find("byItemAndUser", order.item, order.user).first();
			o.merge(order);
			o.save();
		}
	}

	public void merge(DispositionOrder order) {
//		this.consumptionPeriod0 = order.consumptionPeriod0;
//		this.consumptionPeriod1 = order.consumptionPeriod1;
//		this.consumptionPeriod2 = order.consumptionPeriod2;
//		this.consumptionPeriod3 = order.consumptionPeriod3;
//		
//		this.futureStock0 = order.futureStock0;
//		this.futureStock1 = order.futureStock1;
//		this.futureStock2 = order.futureStock2;
//		this.futureStock3 = order.futureStock3;
		
		this.mode = order.mode;
		this.quantity = order.quantity;
//		public double expectedArrival;
	}

	@Override
	public String toString() {
		return "DispositionOrder [item=" + item + ", usedP1=" + usedP1 + ", usedP2=" + usedP2 + ", usedP3=" + usedP3 + ", price=" + price + ", deliveryCost="
				+ deliveryCost + ", deliveryTime=" + deliveryTime + ", deliveryVariance=" + deliveryVariance + ", discount=" + discount + "]";
	}

}
