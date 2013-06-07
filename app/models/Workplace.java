package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.Logger;
import play.db.jpa.Model;

/**
 * 
 * @author sven
 * 
 */
@Entity
public class Workplace extends Model {

	public int workplaceId;

	public String name;

	public String[] waitingList;
	public String inWork;
	public int[] productionPlanList;

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
			WaitingList wL = WaitingList.find("byWaitingListId", this.waitingList[count]).first();
			wList.add(wL);
		}
		return wList;
	}

	public List<ProductionOrder> getProductionPlanListAsObjectList() {
		List<ProductionOrder> pList = new ArrayList<>();
		if (this.productionPlanList == null) {
			return null;
		}
		for (int count = 0, length = this.productionPlanList.length; count < length; count++) {
			ProductionOrder pO = ProductionOrder.find("byOrderNumber", this.productionPlanList[count]).first();
			pList.add(pO);
		}
		return pList;
	}

	public WaitingList getInWorkAsObject() {
		return WaitingList.find("byWaitingListId", inWork).first();
	}

	public static void deleteAllProductionPlanLists() {
		List<Workplace> places = Workplace.findAll();
		for (Workplace workplace : places) {
			workplace.productionPlanList = null;
			workplace.save();
		}
	}

	@Override
	public String toString() {
		return "Workplace [workplaceId=" + workplaceId + ", name=" + name + ", waitingList=" + Arrays.toString(waitingList) + ", inWork=" + inWork
				+ ", productionPlanList=" + Arrays.toString(productionPlanList) + "]";
	}

}
