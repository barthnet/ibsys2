package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.Logger;
import play.db.jpa.Model;
import utils.ItemHelper;

/**
 * 
 * @author sven
 * 
 */
@Entity
public class Workplace extends Model {

	public int workplaceId;

	public String name;
	public String user;

	public String[] waitingList;
	public String[] nodes;
	public String inWork;
	public int[] productionPlanList;

	public Workplace clone() {
		Workplace w = new Workplace();
		w.workplaceId = this.workplaceId;
		w.name = this.name;
		w.nodes = this.nodes;
		return w;
	}

	public int[] calculateTimeRequirement(String item, String userName, boolean log, boolean root) {
//		Logger.info("calculateTimeRequirement %s %s %s", item, userName, log);
		WorkplaceNode node = null;
		int needTotal[] = { 0, 0, 0 };
		if (this.nodes != null && this.nodes.length > 0) {
			for (int i = this.nodes.length - 1; i >= 0; i--) {
				int need[] = null;
				node = WorkplaceNode.find("byNodeId", this.nodes[i]).first();
				if (node != null && node.item.equals(item)) {
					Workplace w = Workplace.find("byWorkplaceIdAndUser", node.workplace, userName).first();
//					if (log)
//						Logger.info("call iterate on workplace %s", w.workplaceId);
					need = w.calculateTimeRequirement(item, userName, log, false);
//					if (log)
//						Logger.info("get result from iterate on workplace %s for item %s and wp %s amount: %S, timeneed: %s, setuptime: %s", w.workplaceId,
//								item, w.workplaceId, need[0], need[1], need[2]);
					needTotal[0] += need[0];
					// needTotal[1] += need[1];
					// needTotal[2] += need[2];
//					if (log)
//						Logger.info("add iterate result to major result amount: %S, timeneed: %s, setuptime: %s", needTotal[0], needTotal[1], needTotal[2]);
				}
			}
		}
		// List<WaitingList> wList =
		// WaitingList.find("byItemAndUserAndWorkplace", item, userName,
		// this.workplaceId).fetch();
		List<WaitingList> wList = this.getWaitingListAsObjectList();
		boolean found = false;
		if (wList != null && !wList.isEmpty()) {
//			Logger.info("wList not Empty");

			for (WaitingList wait : wList) {
				if (!wait.item.equals(item)) {
					continue;
				}
//				if (log)
//					Logger.info("wList item %s, workplace %s", wait.amount, wait.workplace);
				needTotal[0] += wait.amount;
				found = true;
				// needTotal[1] += wait.timeneed;
				needTotal[2] += ItemHelper.getSetupTime(this.workplaceId, wait.item);
			}
		}

		if (!found && needTotal[0] > 0)
			needTotal[2] += ItemHelper.getSetupTime(this.workplaceId, item);

		needTotal[1] += needTotal[0] * ItemHelper.getProcessTime(this.workplaceId, item);

		WaitingList inWork = this.getInWorkAsObject();
		if (inWork != null && !root && inWork.item.equals(item)) {
//			if (log)
//				Logger.info("inWork amount: %s, timeneed: %s, workplace: %s", inWork.amount, inWork.timeneed, inWork.workplace);
			needTotal[0] += inWork.amount;
		}

		if (root) {
			needTotal[1] = needTotal[0] * ItemHelper.getProcessTime(this.workplaceId, item);
			if (inWork != null && inWork.item.equals(item))
				needTotal[1] += inWork.timeneed;
		}
		// if (log)
		// Logger.info(" workplace: %s, amount: %s, timeneed: %s, setuptime: %s", this.workplaceId, needTotal[0], needTotal[1], needTotal[2]);
		return needTotal;
	}

	public static void deleteAll(String userName) {
		List<Workplace> caps = Workplace.find("byUser", userName).fetch();
		for (Workplace capacity : caps) {
			capacity.delete();
		}
	}

	public void addWaitingList(WaitingList wList) {
		if (waitingList == null) {
			waitingList = new String[] { wList.waitingListId };
		} else {
			String[] newWaitingList = Arrays.copyOf(this.waitingList, this.waitingList.length + 1);
			newWaitingList[this.waitingList.length] = wList.waitingListId;
			this.waitingList = newWaitingList;
		}
	}

	public void addProductionPlanList(ProductionOrder pList) {
		if (productionPlanList == null) {
			productionPlanList = new int[] { pList.orderNumber };
		} else {
			int[] newProductionPlanList = Arrays.copyOf(this.productionPlanList, this.productionPlanList.length + 1);
			newProductionPlanList[this.productionPlanList.length] = pList.orderNumber;
			this.productionPlanList = newProductionPlanList;
		}
	}

	public List<WaitingList> getWaitingListAsObjectList() {
		List<WaitingList> wList = new ArrayList<>();
		if (this.waitingList == null) {
			return null;
		}
		for (int count = 0, length = this.waitingList.length; count < length; count++) {
			// Logger.info("workplace getWaitingListAsObjectList %s %s",
			// this.user, this.waitingList[count]);
			WaitingList wL = WaitingList.find("byWaitingListIdAndUser", this.waitingList[count], this.user).first();
			wList.add(wL);
		}
		// Logger.info("%s", wList);
		return wList;
	}

	public List<ProductionOrder> getProductionPlanListAsObjectList() {
		List<ProductionOrder> pList = new ArrayList<>();
		if (this.productionPlanList == null) {
			return null;
		}
		for (int count = 0, length = this.productionPlanList.length; count < length; count++) {
			ProductionOrder pO = ProductionOrder.find("byOrderNumberAndUser", this.productionPlanList[count], this.user).first();
			pList.add(pO);
		}
		return pList;
	}

	public WaitingList getInWorkAsObject() {
		return WaitingList.find("byWaitingListIdAndUser", this.inWork, this.user).first();
	}

	public static void deleteAllProductionPlanLists(String userName) {
		List<Workplace> places = Workplace.find("byUser", userName).fetch();
		for (Workplace workplace : places) {
			workplace.productionPlanList = null;
			workplace.save();
		}
	}

	@Override
	public String toString() {
		return "Workplace [workplaceId=" + workplaceId + ", name=" + name + ", user=" + user + ", waitingList=" + Arrays.toString(waitingList) + ", nodes="
				+ Arrays.toString(nodes) + ", inWork=" + inWork + ", productionPlanList=" + Arrays.toString(productionPlanList) + "]";
	}

	// @Override
	// public String toString() {
	// return "Workplace [workplaceId=" + workplaceId + ", name=" + name +
	// ", user=" + user + ", waitingList=" + Arrays.toString(waitingList) +
	// ", inWork="
	// + inWork + ", productionPlanList=" + Arrays.toString(productionPlanList)
	// + "]";
	// }

}
