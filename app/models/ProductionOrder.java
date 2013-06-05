package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class ProductionOrder extends Model {

	public String item;

	public int orderNumber;
	public int amount;

	public Item getItemAsObject() {
		return Item.find("byItemId", this.item).first();
	}

	public static void saveAll(List<ProductionOrder> orders) {
		for (ProductionOrder productionOrder : orders) {
			ProductionOrder newOrder = new ProductionOrder();
			newOrder.item = productionOrder.item;
			newOrder.orderNumber = productionOrder.orderNumber;
			newOrder.amount = productionOrder.amount;
			newOrder.save();
		}
	}
	
	public void assignToWorkplaces() {
		List<ItemTime> workplaces = ItemTime.find("byItem", this.item).fetch();
		for (ItemTime itemTime : workplaces) {
			Workplace place = itemTime.getWorkplaceAsObject();
			place.addProductionPlanList(this);
		}
	}

	@Override
	public String toString() {
		return "ProductionOrder [item=" + item + ", orderNumber=" + orderNumber + ", amount=" + amount + "]";
	}

}
