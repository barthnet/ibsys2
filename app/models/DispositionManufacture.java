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
	public String user;
	public int itemNumber;
	public int ioNumber;
	public String productItem;
	public String[] itemChilds;

	public int distributionWish;
	public int parentWaitingList;
	public int safetyStock;
	public int stock;
	public int waitingList;
	public int inWork;
	public int production;
	
	public DispositionManufacture clone() {
		DispositionManufacture d = new DispositionManufacture();
		d.item = this.item;
		d.ioNumber = this.ioNumber;
		d.itemNumber = this.itemNumber;
		d.productItem = this.productItem;
		d.itemChilds = this.itemChilds;
		return d;
	}
	
	public static void deleteAll(String userName) {
		List<DispositionManufacture> caps = DispositionManufacture.find("byUser", userName).fetch();
		for (DispositionManufacture capacity : caps) {
			capacity.delete();
		}
	}

	public Item getItemAsObject() {
		return Item.find("byItemIdAndUser", this.item, this.user).first();
	}

	public List<Item> getItemChildsAsObject() throws NotFoundException {
		List<Item> itemChildObjects = new ArrayList<>();
		Item item = null;
		for (int count = 0, length = this.itemChilds.length; count < length; count++) {
			item = Item.find("byItemIdAndUser", this.itemChilds[count], this.user).first();
			itemChildObjects.add(item);
		}
		return itemChildObjects;
	}

	public static void merge(List<DispositionManufacture> listToMerge) {
		for (DispositionManufacture disp : listToMerge) {
			DispositionManufacture d = DispositionManufacture.find("byItemAndProductItemAndUser", disp.item, disp.productItem, disp.user).first();
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
		this.production = disp.production;
	}

	@Override
	public String toString() {
		return "DispositionManufacture [item=" + item + ", productItem=" + productItem + ", itemChilds=" + Arrays.toString(itemChilds) + ", distributionWish="
				+ distributionWish + ", parentSafetyStock=" + parentWaitingList + ", safetyStock=" + safetyStock + ", stock=" + stock + ", waitingList="
				+ waitingList + ", inWork=" + inWork + ", production=" + production + "]";
	}
}
