package logic;

import java.util.List;

import models.DispositionManufacture;
import models.Item;
import models.ProductionOrder;

public class ApplicationLogic {
	
	public static void planToOrder() {
		List<DispositionManufacture> plans = DispositionManufacture.findAll();
		ProductionOrder.deleteAll();
		for (DispositionManufacture dispo : plans) {
			Item item = Item.find("byItemId", dispo.item.itemId).first();
			ProductionOrder prodOrder = ProductionOrder.find("byItem", item).first();
			if (prodOrder != null) {
				prodOrder.amount += dispo.production;
			} else {
				prodOrder = new ProductionOrder();
				prodOrder.item = item;
				prodOrder.amount = dispo.production;
			}
			prodOrder.save();
		}
	}

}
