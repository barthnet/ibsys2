package logic;

import java.util.List;
import java.util.ArrayList;
import models.DispositionManufacture;
import models.Item;
import models.ProductionOrder;
import models.Capacity;
import models.ProductionOrder;
import models.WaitingList;
import models.Workplace;
import utils.ItemHelper;

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

	public static void calcCapacity() {
		List<Workplace> workplaces = Workplace.findAll();

		for (Workplace workplace : workplaces) {
			Capacity capa = new Capacity();

			// Warteliste dazurechnen
			for (WaitingList wait : workplace.waitingList) {

				int duration = ItemHelper.getProcessTime(workplace, wait.item);
				int setup = ItemHelper.getSetupTime(workplace, wait.item);

				capa.time += duration * wait.amount;
				capa.setupTime += setup;
			}
			// ProcessList dazurechnen
			capa.time += ItemHelper.getProcessTime(workplace, workplace.processList.item);
			capa.time += ItemHelper.getSetupTime(workplace, workplace.processList.item);
			
			//ProductionPlanList dazurechnen
			for(ProductionOrder order :workplace.productionPlanList) {
				
				int duration = ItemHelper.getProcessTime(workplace, order.item);
				int setup = ItemHelper.getSetupTime(workplace, order.item);
				
				capa.time += duration * order.amount;
				capa.setupTime += setup;				
			}
			
			// Gesamtdauer berechnen
			capa.totaltime = capa.time + capa.setupTime;
					
			// Überstunden berechnen
			// Schichten berechnen
			if(capa.totaltime < 2400) {
				capa.shift = 1;
				capa.overtime = capa.totaltime - 2400;
			}
			
			if(capa.totaltime < 3600) {
				capa.shift = 2;
				capa.overtime = capa.totaltime - 3600;
			}
			
			if(capa.totaltime < 4800) {
				capa.shift = 3;
				capa.overtime = capa.totaltime - 4800;
			}
						
			//Capacity Objekt persistieren
			capa.save();
		}
	}
}
