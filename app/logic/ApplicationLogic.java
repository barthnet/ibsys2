package logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.sql.Delete;

import com.ning.http.client.oauth.ConsumerKey;

import models.Capacity;
import models.Component;
import models.DispositionManufacture;
import models.DispositionOrder;
import models.DistributionWish;
import models.Item;
import models.OpenOrder;
import models.ProductionOrder;
import models.User;
import models.WaitingList;
import models.Workplace;
import models.WorkplaceNode;
import play.Logger;
import play.test.Fixtures;
import utils.ItemHelper;

public class ApplicationLogic {

	public static void resetData() {
		Logger.info("reset Data");
		Fixtures.deleteAllModels();
		Fixtures.loadModels("initial-items.yml", "initial-workplaces.yml", "ItemTime.yml", "initial-dispositionOrder.yml", "initial-productionPlan.yml",
				"initial-components.yml", "initial-workplaceNodes.yml");
	}

	public static void resetUserData(String userName) {
		Logger.info("resetUserData %s", userName);
		Capacity.deleteAll(userName);
		DispositionManufacture.deleteAll(userName);
		DispositionOrder.deleteAll(userName);
		DistributionWish.deleteAll(userName);
		Item.deleteAll(userName);
		OpenOrder.deleteAll(userName);
		ProductionOrder.deleteAll(userName);
		WaitingList.deleteAll(userName);
		Workplace.deleteAll(userName);
		User user = User.find("byName", userName).first();
		if (user != null) {
			user.isSimulatable = false;
			user.save();
		}
		cloneAllObjects(userName);
	}

	public static void cloneAllObjects(String userName) {
		Logger.info("cloneAllObjects %s", userName);
		List<Workplace> wList = Workplace.find("byUserIsNull").fetch();
		for (Workplace workplace : wList) {
			Workplace w = workplace.clone();
			w.user = userName;
			w.save();
		}
		// List<Workplace> wList2 = Workplace.find("byUser", userName).fetch();
		// Logger.info("wList2 %s", wList2);

		List<Item> iList = Item.find("byUserIsNull").fetch();
		for (Item item : iList) {
			Item i = item.clone();
			i.user = userName;
			i.save();
		}

		List<DispositionManufacture> dList = DispositionManufacture.find("byUserIsNull").fetch();
		for (DispositionManufacture dispositionManufacture : dList) {
			// Logger.info("clone: %s", dispositionManufacture);
			DispositionManufacture d = dispositionManufacture.clone();
			d.user = userName;
			d.save();
		}

		List<DispositionOrder> doList = DispositionOrder.find("byUserIsNull").fetch();
		for (DispositionOrder dispositionOrder : doList) {
			DispositionOrder d = dispositionOrder.clone();
			d.user = userName;
			d.save();
		}

		List<DistributionWish> dwList = DistributionWish.find("byUserIsNull").fetch();
		for (DistributionWish distributionWish : dwList) {
			DistributionWish d = distributionWish.clone();
			d.user = userName;
			d.save();
		}
	}

	public static void wishToPlan(String userName) {
		Logger.info("wishToPLan");
		List<DistributionWish> wishList = DistributionWish.find("byUser", userName).fetch();
		if (wishList == null || wishList.isEmpty()) {
			wishList = new ArrayList<>();
			List<Item> pItems = Item.find("byTypeAndUser", "P", userName).fetch();
			for (Item item : pItems) {
				DistributionWish wish = new DistributionWish();
				wish.item = item.itemId;
				wish.user = userName;
				wish.save();
				wishList.add(wish);
			}
		}
		for (DistributionWish wish : wishList) {
			DispositionManufacture disp = DispositionManufacture.find("byItemAndUser", wish.item, userName).first();
			disp.distributionWish = wish.period0 + wish.directSale;
			disp.save();
			// Logger.info("wishToPlan %s", disp);
		}
		// calcProductionPlan();
	}

	public static void calcProductionPlan(String userName) {
		// Logger.info("setDependencies");
		Logger.info("calcProductionPlan");
		// List<DispositionManufacture> disps =
		// DispositionManufacture.find("byUser", userName).fetch();
		List<DispositionManufacture> disps = DispositionManufacture.find("user = ? order by productItem asc, ioNumber asc", userName).fetch();
		DispositionManufacture parent = new DispositionManufacture();
		// parent.user = userName;
		for (int i = 0, length = disps.size(); i < length; i++) {
			DispositionManufacture disp = disps.get(i);
			// Logger.info("calcPlan: %s", disp);
			Item item = Item.find("byItemIdAndUser", disp.item, userName).first();
			if ("P".equals(item.type)) {
				parent = new DispositionManufacture();
				// parent.user = userName;
			} else {
				disp.distributionWish = parent.production;
			}
			boolean mulitpleItem = item.itemNumber == 26 || item.itemNumber == 16 || item.itemNumber == 17 ? true : false;
			if (mulitpleItem) {
				disp.stock = item.amount / 3;
			} else {
				disp.stock = item.amount;
			}

			// TODO in item model yml aufnehmen
			disp.safetyStock = disp.safetyStock > 0 ? disp.safetyStock : 75;
			// disp.safetyStock = 0;
			disp.parentWaitingList = parent.waitingList;
			disp.inWork = 0;
			disp.waitingList = 0;
			List<WaitingList> wL = WaitingList.find("byItemAndUser", disp.item, userName).fetch();
			for (WaitingList waitingList : wL) {
				Workplace wP = Workplace.find("byWorkplaceIdAndUser", waitingList.workplace, userName).first();
				if (wP.inWork != null && wP.inWork.equals(waitingList.waitingListId)) {
					if (mulitpleItem) {
						disp.inWork += waitingList.amount / 3;
					} else {
						disp.inWork += waitingList.amount;
					}
				} else {
					if (mulitpleItem) {
						disp.waitingList += waitingList.amount / 3;
					} else {
						disp.waitingList += waitingList.amount;
					}
				}
			}
			disp.production = disp.distributionWish + disp.parentWaitingList + disp.safetyStock - disp.stock - disp.waitingList - disp.inWork;
			if (disp.production < 0) {
				disp.production = 0;
			}
			disp.save();
			if (disp.itemChilds != null && disp.itemChilds.length > 0) {
				parent = disp;
			}
			// Logger.info("disp: %s", disp);
		}
	}

	public static void planToOrder(String userName) {
		// Logger.info("planToOrder");
		List<DispositionManufacture> plans = DispositionManufacture.find("user = ? order by itemNumber asc", userName).fetch();
		// List<DispositionManufacture> plans =
		// DispositionManufacture.findAll();
		Workplace.deleteAllProductionPlanLists(userName);
		Logger.info("planToOrder %s", ProductionOrder.find("byUser", userName).fetch().size());
		ProductionOrder.deleteAll(userName);
		int no = 0;
		for (DispositionManufacture dispo : plans) {
			ProductionOrder prodOrder = ProductionOrder.find("byItemAndUser", dispo.item, userName).first();
			Item item = dispo.getItemAsObject();
			if (prodOrder != null) {
				// Logger.info("pOrder not null: %s", prodOrder);
				prodOrder.amount += dispo.production;
			} else {
				prodOrder = new ProductionOrder();
				prodOrder.item = item.itemId;
				prodOrder.user = userName;
				// prodOrder.itemNumber = item.itemNumber;
				prodOrder.orderNumber = no;
				no++;
				prodOrder.amount = dispo.production;
				prodOrder.assignToWorkplaces();
				// Logger.info("pOrder null: %s", prodOrder);
			}

			prodOrder.save();

		}
	}

	public static void calculateInitialCapacity(String userName) {
		// Logger.info("calculateInitialCapacity %s", userName);
		List<Workplace> places = Workplace.find("byUser", userName).fetch();
		for (Workplace workplace : places) {
			Capacity cap = Capacity.find("byWorkplaceAndUser", workplace.workplaceId, userName).first();
			if (cap == null) {
				cap = new Capacity();
				cap.user = userName;
				cap.workplace = workplace.workplaceId;
				cap.save();
				// if (workplace.workplaceId == 11)
				// Logger.info("create new capacity initial %s", cap);
			}

			cap.originalSetupTime = 0;
			cap.originalTime = 0;

			// Logger.info("workplace %s", workplace);
			if (workplace.nodes != null && workplace.nodes.length > 0) {
				// Logger.info("hasNodes");
				for (int i = workplace.nodes.length - 1; i >= 0; i--) {
					int need[] = { 0, 0, 0 };
					WorkplaceNode n = WorkplaceNode.find("byNodeId", workplace.nodes[i]).first();
					boolean log = (n.item.equals("E26")) && workplace.workplaceId == 7 ? true : false;
					need = workplace.calculateTimeRequirement(n.item, userName, log, true);
					// if (log)
					// Logger.info("%s item: %s, time: %s, setup: %s",
					// workplace.workplaceId, n.item, need[1], need[2]);
					cap.originalSetupTime += need[2];
					cap.originalTime += need[1];
					// if (log)
					// Logger.info("%s %s: %s", workplace.workplaceId, n.item,
					// cap);
				}
				if (workplace.workplaceId == 7) {
					List<WaitingList> wList = workplace.getWaitingListAsObjectList();
					if (wList != null && !wList.isEmpty()) {
						int time = 0;
						int setup = 0;
						for (WaitingList wait : wList) {
							if (wait.item.equals("E26")) {
								time += wait.timeneed;
								setup += ItemHelper.getSetupTime(cap.workplace, wait.item);
							}
						}
						cap.originalTime += time;
						cap.originalSetupTime += setup;
					}

					WaitingList inWork = workplace.getInWorkAsObject();
					if (inWork != null && inWork.item.equals("E26")) {
						cap.originalTime += inWork.timeneed;
					}
				}
			} else {

				List<WaitingList> wList = workplace.getWaitingListAsObjectList();
				if (wList != null && !wList.isEmpty()) {
					int time = 0;
					int setup = 0;
					for (WaitingList wait : wList) {
						time += wait.timeneed;
						setup += ItemHelper.getSetupTime(cap.workplace, wait.item);
					}
					cap.originalTime += time;
					cap.originalSetupTime += setup;
				}

				WaitingList inWork = workplace.getInWorkAsObject();
				if (inWork != null) {
					cap.originalTime += inWork.timeneed;
				}
			}
			cap.save();
			// Logger.info("cap: %s", cap);
		}

		// Logger.info("\n\nall Caps: %s", Capacity.findAll());
	}

	public static void calculateCapacity(String userName) {
		// Logger.info("calculateCapacity %s", userName);
		// Gib mir alle Arbeitsplätze
		List<Workplace> places = Workplace.find("byUser", userName).fetch();
		// Capacity.deleteAll(userName);
		Logger.info("calculateCapacity: %s %s", places.size(), userName);

		// Rechne für jeden Arbeitsplatz die Kapazität aus
		for (Workplace workplace : places) {

			// Gibt es bereits ein Kapazitätsobjekt zu dem Arbeitsplatz
			// if (workplace.workplaceId == 11)
			// Logger.info("%s %s", workplace.workplaceId, userName);

			Capacity cap = Capacity.find("workplace = ? and user = ?", workplace.workplaceId, userName).first();
			// Falls nicht, ein neues Anlegen
			if (cap == null) {
				// Logger.info("null %s", cap);
				cap = new Capacity();
				cap.user = userName;
				cap.workplace = workplace.workplaceId;
				// if (workplace.workplaceId == 11)
				// Logger.info("create new capacity %s", cap);
			}
			// Alles auf 0 setzen
			cap.time = 0;
			cap.setupTime = 0;
			cap.totaltime = 0;
			cap.overtime = 0;
			cap.shift = 0;

			// Die Zeiten für die Warteschlangen errechnen und hinzufügen, falls
			// vorhanden
			// List<WaitingList> wList = workplace.getWaitingListAsObjectList();
			// if (wList != null && !wList.isEmpty()) {
			// // Logger.info("wList: %s", wList.size());
			// int time = 0;
			// int setup = 0;
			// //Für jedes Item in der Warteschlange Zeit und Rüstzeit
			// hinzuaddieren
			// for (WaitingList wait : wList) {
			// time += wait.timeneed;
			// setup += ItemHelper.getSetupTime(cap.workplace, wait.item);
			// }
			// // Logger.info("WaitL: %s", time);
			// cap.time += time;
			// cap.setupTime += setup;
			// }
			//
			// //Zeit für das in Arbeit befindliche Item hinzuaddieren
			// WaitingList inWork = workplace.getInWorkAsObject();
			//
			// if (inWork != null) {
			// // Logger.info("inWork: %s", inWork.amount);
			// cap.time += inWork.timeneed;
			// }

			// Die Zeiten für die Produktionsaufträge errechnen und hinzufügen.
			List<ProductionOrder> pOrders = workplace.getProductionPlanListAsObjectList();
			if (pOrders != null && !pOrders.isEmpty()) {
				// if (workplace.workplaceId == 11)
				// Logger.info("pOrders: %s", pOrders);
				int time = 0;
				for (ProductionOrder productionOrder : pOrders) {
					if (productionOrder.amount > 0) {
						time += productionOrder.amount * ItemHelper.getProcessTime(cap.workplace, productionOrder.item);
						cap.setupTime += ItemHelper.getSetupTime(cap.workplace, productionOrder.item);
					}

					// if (workplace.workplaceId == 1) {
					// Logger.info("pOrder: %s %s",productionOrder.item,
					// (productionOrder.amount *
					// ItemHelper.getProcessTime(cap.workplace,
					// productionOrder.item)));
					// Logger.info("times %s amount %s",
					// ItemHelper.getProcessTime(cap.workplace,
					// productionOrder.item), productionOrder.amount);
					// }
				}

				// Logger.info("pOrders: %s", time);
				cap.time += time;
			}

			// if (workplace.workplaceId == 11)
			// Logger.info("before %s", cap);
			cap.time += cap.originalTime;
			cap.setupTime += cap.originalSetupTime;

			cap.totaltime = cap.time + cap.setupTime;

			// cap.totaltime = cap.time + cap.setupTime;
			// cap.totaltime += cap.originalSetupTime + cap.originalTime;
			// if (workplace.workplaceId == 11)
			// Logger.info("after %s", cap);
			cap.shift = 1;
			if (cap.totaltime == 0) {
				cap.overtime = 0;
			} else if (cap.totaltime <= 3600) {
				cap.shift = 1;
				if (cap.totaltime < 2400) {
					cap.overtime = 0;
				} else {
					cap.overtime = cap.totaltime - 2400;
				}
			} else if (cap.totaltime <= 6000) {
				cap.shift = 2;
				if (cap.totaltime < 4800) {
					cap.overtime = 0;
				} else {
					cap.overtime = cap.totaltime - 4800;
				}
			} else if (cap.totaltime > 6000) {
				cap.shift = 3;
				if (cap.totaltime < 7200) {
					cap.overtime = 0;
				} else {
					cap.overtime = cap.totaltime - 7200;
					if (cap.overtime >= 1200) {
						cap.overtime = 1200;
					}
				}
			}
			// overtime per day
			cap.overtime = (int) Math.ceil(cap.overtime / 5);
			cap.save();
			// Logger.info("capacity %s", cap);
		}
	}

	public static void calculateDisposition(String userName) {
		Logger.info("calculateDisposition");
		calculateConsumption(userName);
		User user = User.find("byName", userName).first();
		int actPeriod = Integer.valueOf(user.period);
		List<DispositionOrder> dispoOrders = DispositionOrder.find("byUser", userName).fetch();
		for (DispositionOrder dispoOrder : dispoOrders) {
			// TODO calculateExpectedArrival Methode dynamisch statt hardcoded
			dispoOrder.expectedArrival = calculateExpectedArrival("recommended", dispoOrder.item, 5, actPeriod, userName);
			Item item = Item.find("byItemIdAndUser", dispoOrder.item, userName).first();
			dispoOrder.amount = item.amount;
			calculateFutureStock(dispoOrder.item, "recommended", userName);

			int period = -1;
			int quantity = 0;
			// quantity anpassen?!
			if (item.amount == 0) {
				period = 0;
				quantity = dispoOrder.consumptionPeriod0;
			} else if (dispoOrder.futureStock0 <= 0) {
				period = 0;
				quantity = dispoOrder.consumptionPeriod0;
			} else if (dispoOrder.futureStock1 <= 0) {
				period = 1;
				quantity = dispoOrder.consumptionPeriod1 + dispoOrder.consumptionPeriod0;
			} else if (dispoOrder.futureStock2 <= 0) {
				period = 2;
				quantity = dispoOrder.consumptionPeriod2 + dispoOrder.consumptionPeriod1 + dispoOrder.consumptionPeriod0;
			} else if (dispoOrder.futureStock3 <= 0) {
				period = 3;
				quantity = dispoOrder.consumptionPeriod3 + dispoOrder.consumptionPeriod2 + dispoOrder.consumptionPeriod1 + dispoOrder.consumptionPeriod0;
			}

			if (period == -1)
				continue;

			// Bestellmenge = Diskontmenge
			if (quantity < dispoOrder.discount) {
				dispoOrder.quantity = dispoOrder.discount + (int) (0.5 * dispoOrder.consumptionPeriod1);
			} else {
				dispoOrder.quantity = quantity + (int) (0.5 * dispoOrder.consumptionPeriod1);
			}

			// Wenn Lieferzeit zu lang, dann Express Bestellung

			if (Math.ceil(dispoOrder.expectedArrival) > (period + actPeriod)) {
				dispoOrder.mode = 4;
				dispoOrder.expectedArrival = calculateExpectedArrival("recommended", dispoOrder.item, 4, actPeriod, userName);
			} else {
				dispoOrder.mode = 5;
			}

			dispoOrder.save();

			// Kalkuliere Bestände mit neuen DispoOrders neu
			// calculateFutureStock(dispoOrder.item, "recommended", userName);

		}
	}

	public static void calculateConsumption(String userName) {
		List<DispositionOrder> dispoOrders = DispositionOrder.find("byUser", userName).fetch();
		for (DispositionOrder dispoOrder : dispoOrders) {
			// aktueller Verbrauch
			List<Component> components = Component.find("byItem", dispoOrder.item).fetch();
			int actConsumption = 0;
			for (Component component : components) {
				List<DispositionManufacture> dms = DispositionManufacture.find("byItemAndUser", component.parent, userName).fetch();
				for (DispositionManufacture dm : dms) {
					if (dm != null) {
						actConsumption += dm.production * component.amount;
					}
				}
			}
			dispoOrder.consumptionPeriod0 = actConsumption;

			List<WaitingList> waitingLists = WaitingList.find("byItemAndUser", dispoOrder.item, userName).fetch();
			if (waitingLists != null && waitingLists.size() > 0) {
				for (WaitingList waiting : waitingLists) {
					if (waiting.inWork == false) {
						dispoOrder.consumptionPeriod0 += waiting.amount;
					}
				}

			}

			dispoOrder.consumptionPeriod1 = 0;
			dispoOrder.consumptionPeriod2 = 0;
			dispoOrder.consumptionPeriod3 = 0;

			// Verbrauch Prognosen
			if (dispoOrder.usedP1 > 0) {
				DistributionWish wish = DistributionWish.find("byItemAndUser", "P1", userName).first();
				dispoOrder.consumptionPeriod1 += wish.period1 * dispoOrder.usedP1;
				dispoOrder.consumptionPeriod2 += wish.period2 * dispoOrder.usedP1;
				dispoOrder.consumptionPeriod3 += wish.period3 * dispoOrder.usedP1;
			}

			if (dispoOrder.usedP2 > 0) {
				DistributionWish wish = DistributionWish.find("byItemAndUser", "P2", userName).first();
				dispoOrder.consumptionPeriod1 += wish.period1 * dispoOrder.usedP2;
				dispoOrder.consumptionPeriod2 += wish.period2 * dispoOrder.usedP2;
				dispoOrder.consumptionPeriod3 += wish.period3 * dispoOrder.usedP2;
			}

			if (dispoOrder.usedP3 > 0) {
				DistributionWish wish = DistributionWish.find("byItemAndUser", "P3", userName).first();
				dispoOrder.consumptionPeriod1 += wish.period1 * dispoOrder.usedP3;
				dispoOrder.consumptionPeriod2 += wish.period2 * dispoOrder.usedP3;
				dispoOrder.consumptionPeriod3 += wish.period3 * dispoOrder.usedP3;
			}

			// Logger.info("Dispo: %s Con0: %s Con1: %s Con2: %s Con3: %s",
			// dispoOrder.item, dispoOrder.consumptionPeriod0,
			// dispoOrder.consumptionPeriod1,
			// dispoOrder.consumptionPeriod2, dispoOrder.consumptionPeriod3);

			dispoOrder.save();
		}
	}

	public static double calculateExpectedArrival(String method, String itemId, int mode, int orderPeriod, String userName) {
		double expectedArrival = 0.0;
		DispositionOrder dispoOrder = DispositionOrder.find("byItemAndUser", itemId, userName).first();
		expectedArrival = 0.2 + orderPeriod;
		// If express order half delivery time and no variance
		if (mode == 0) {
			return 0;
		} else if (mode == 5) {
			expectedArrival += dispoOrder.deliveryTime;
			switch (method) {
			case "optimistic": {
				break;
			}
			case "riskaverse": {
				expectedArrival += dispoOrder.deliveryVariance;
				break;
			}
			case "recommended": {
				expectedArrival += (dispoOrder.deliveryVariance * 0.75);
				break;
			}
			}
		} else if (mode == 4) {
			expectedArrival += (dispoOrder.deliveryTime / 2);
		}
		// Logger.info("Expected arrival for %s: %s", dispoOrder.item,
		// expectedArrival);
		return expectedArrival;
	}

	public static void calculateFutureStock(String itemId, String method, String userName) {
		User user = User.find("byName", userName).first();
		int actPeriod = Integer.valueOf(user.period);

		// remove consumption from stock, add dispoOrders amount to expected
		// period
		DispositionOrder dispoOrder = DispositionOrder.find("byItemAnduser", itemId, userName).first();
		dispoOrder.futureStock0 = dispoOrder.amount - dispoOrder.consumptionPeriod0;
		dispoOrder.futureStock1 = dispoOrder.futureStock0 - dispoOrder.consumptionPeriod1;
		dispoOrder.futureStock2 = dispoOrder.futureStock1 - dispoOrder.consumptionPeriod2;
		dispoOrder.futureStock3 = dispoOrder.futureStock2 - dispoOrder.consumptionPeriod3;

		/*
		 * double deltaDispo = dispoOrder.expectedArrival - actPeriod; if
		 * (deltaDispo <= 1) { dispoOrder.futureStock0 += dispoOrder.quantity;
		 * dispoOrder.futureStock1 += dispoOrder.quantity;
		 * dispoOrder.futureStock2 += dispoOrder.quantity;
		 * dispoOrder.futureStock3 += dispoOrder.quantity; } else if (deltaDispo
		 * <= 2) { dispoOrder.futureStock1 += dispoOrder.quantity;
		 * dispoOrder.futureStock2 += dispoOrder.quantity;
		 * dispoOrder.futureStock3 += dispoOrder.quantity; } else if (deltaDispo
		 * <= 3) { dispoOrder.futureStock2 += dispoOrder.quantity;
		 * dispoOrder.futureStock3 += dispoOrder.quantity; } else {
		 * dispoOrder.futureStock3 -= dispoOrder.quantity; }
		 */

		// add openOrder amount to expected period
		List<OpenOrder> openOrders = OpenOrder.find("byItemAndUser", itemId, userName).fetch();
		for (OpenOrder oOrder : openOrders) {
			oOrder.expectedArrival = calculateExpectedArrival(method, itemId, oOrder.mode, oOrder.orderPeriod, userName);
			double deltaOpenOrder = oOrder.expectedArrival - actPeriod;
			if (deltaOpenOrder <= 1) {
				dispoOrder.futureStock0 += oOrder.amount;
				dispoOrder.futureStock1 += oOrder.amount;
				dispoOrder.futureStock2 += oOrder.amount;
				dispoOrder.futureStock3 += oOrder.amount;
			} else if (deltaOpenOrder <= 2) {
				dispoOrder.futureStock1 += oOrder.amount;
				dispoOrder.futureStock2 += oOrder.amount;
				dispoOrder.futureStock3 += oOrder.amount;
			} else if (deltaOpenOrder <= 3) {
				dispoOrder.futureStock2 += oOrder.amount;
				dispoOrder.futureStock3 += oOrder.amount;
			} else {
				dispoOrder.futureStock3 -= oOrder.amount;
			}
		}
		dispoOrder.save();
	}

}
