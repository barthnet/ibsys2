package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javassist.NotFoundException;

import javax.persistence.Entity;

import org.hibernate.dialect.FirebirdDialect;

import play.db.jpa.Model;

@Entity
public class DispositionManufacture extends Model {

	public String item;
	public String productItem;
	public String[] itemChilds;

	public int distributionWish;
	public int parentWaitingList;
	public int safetyStock;
	public int stock;
	public int waitingList;
	public int inWork;
	public int production;

	public Item getItemAsObject() {
		return Item.find("byItemId", this.item).first();
	}

	public List<Item> getItemChildsAsObject() throws NotFoundException {
		List<Item> itemChildObjects = new ArrayList<>();
		Item item = null;
		for (int count = 0, length = this.itemChilds.length; count < length; count++) {
			item = Item.find("byItemId", this.itemChilds[count]).first();
			itemChildObjects.add(item);
		}
		return itemChildObjects;
	}

	public static void merge(List<DispositionManufacture> listToMerge) {
		for (DispositionManufacture disp : listToMerge) {
			DispositionManufacture d = DispositionManufacture.find("byItemAndProductItem", disp.item, disp.productItem).first();
			d.merge(disp);
			d.save();
		}
	}

	public void merge(DispositionManufacture disp) {
		this.distributionWish = disp.distributionWish;
		this.parentWaitingList = disp.parentWaitingList;
		this.safetyStock = disp.safetyStock;
		this.stock = disp.stock;
		this.waitingList = disp.waitingList;
		this.inWork = disp.inWork;
	}

	@Override
	public String toString() {
		return "DispositionManufacture [item=" + item + ", productItem=" + productItem + ", itemChilds=" + Arrays.toString(itemChilds) + ", distributionWish="
				+ distributionWish + ", parentSafetyStock=" + parentWaitingList + ", safetyStock=" + safetyStock + ", stock=" + stock + ", waitingList="
				+ waitingList + ", inWork=" + inWork + ", production=" + production + "]";
	}
}
