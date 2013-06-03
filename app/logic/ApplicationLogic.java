package logic;

import java.util.ArrayList;
import java.util.List;

import utils.ItemHelper;

import models.ProductionOrder;
import models.Workplace;

public class ApplicationLogic {

	public static void mapProductionOrders(List<ProductionOrder> orders) {

		List<Workplace> workplaces = new ArrayList<Workplace>();

		// Für alle ProductionOrders...
		for (ProductionOrder order : orders) {

			// Gib mir für das Item der Order alle Workplaces
			workplaces = ItemHelper.getWorkplaces(order.item);

			// Füge jedem dieser Workplaces die Order hinzu
			for (Workplace workplace : workplaces) {
				workplace.productionPlanList.add(order);
			}
		}
	}
}
