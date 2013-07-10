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
		return w;
	}
	
	public int[] calculateTimeRequirement(String item, String userName){
		WorkplaceNode node = null;
		int needTotal[] = {0,0,0};
		if (this.nodes != null && this.nodes.length > 0) {
			for (int i = this.nodes.length-1; i >= 0; i--) {
				int need[] = null;
				node = WorkplaceNode.find("byNodeId", this.nodes[i]).first();
				if (node != null && node.item.equals(item)) {
					Workplace w = Workplace.find("byWorkplaceIdAndUser", node.workplace, userName).first();
					need = w.calculateTimeRequirement(item, userName);
					needTotal[0] += need[0];
					needTotal[1] += need[1];
					needTotal[2] += need[2];
				}
			}			
		}
		List<WaitingList> wList = WaitingList.find("byItemAndUserAndWorkplace", item, userName, this.workplaceId).fetch();
		if (wList != null && !wList.isEmpty()) {
			for (WaitingList wait : wList) {
				needTotal[0] += wait.amount;
//				needTotal[1] += wait.timeneed;				
				needTotal[2] += ItemHelper.getSetupTime(this.workplaceId, wait.item);
			}
		}
		
		needTotal[1] += needTotal[0] * ItemHelper.getProcessTime(this.workplaceId, item);
		
		WaitingList inWork = this.getInWorkAsObject();
		if (inWork != null) {
			needTotal[0] += inWork.amount;
			needTotal[1] += inWork.timeneed;
		}
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
//			Logger.info("workplace getWaitingListAsObjectList %s %s", this.user, this.waitingList[count]);
			WaitingList wL = WaitingList.find("byWaitingListIdAndUser", this.waitingList[count], this.user).first();
			wList.add(wL);
		}
//		Logger.info("%s", wList);
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
		return "Workplace [workplaceId=" + workplaceId + ", name=" + name + ", user=" + user + ", waitingList=" + Arrays.toString(waitingList) + ", inWork="
				+ inWork + ", productionPlanList=" + Arrays.toString(productionPlanList) + "]";
	}

	

}
