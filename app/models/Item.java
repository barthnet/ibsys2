package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javassist.NotFoundException;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import play.db.jpa.Model;

/**
 * @author Boris
 */
@Entity
public class Item extends Model {

	public String itemId;
	public int itemNumber;
	public String type;
	public String name;
	public String user;

	public int amount;
	public double price;

	public String[] components;
	public String[] usedIn;

	//TODO sinn?
	public Item getItemAsObject() {
		return Item.find("byItemIdAndUser", this.itemId, this.user).first();
	}
	
	public Item clone() {
		Item item = new Item();
		item.itemId = this.itemId;
		item.itemNumber = this.itemNumber;
		item.type = this.type;
		item.name = this.name;
		item.price = this.price;
		item.components = this.components;
		item.usedIn = this.usedIn;
		return item;
	}
	
	public static void deleteAll(String userName) {
		List<Item> caps = Item.find("byUser", userName).fetch();
		for (Item capacity : caps) {
			capacity.delete();
		}
	}

	public List<Component> getItemComponentsAsObjectList() {
		List<Component> itemComponentObjects = new ArrayList<>();
		Component item = null;
		for (int count = 0, length = this.components.length; count < length; count++) {
			item = Component.find("byItem", this.components[count]).first();
			itemComponentObjects.add(item);
		}
		return itemComponentObjects;
	}
	
	public List<Component> getItemsUsedInAsObjectList() {
		List<Component> itemsUsedInAsObjectList = new ArrayList<>();
		Component item = null;
		for (int count = 0, length = this.usedIn.length; count < length; count++) {
			item = Component.find("byItem", this.usedIn[count]).first();
			itemsUsedInAsObjectList.add(item);
		}
		return itemsUsedInAsObjectList;
	}

	// fehlt da noch etwas??
	public void addComp(Component comp) {
		String[] newComponentList = Arrays.copyOf(this.components, this.components.length + 1);
		newComponentList[this.components.length] = comp.item;
	}
	
	@PrePersist
	protected void onCreate() {
		this.itemId = type + itemNumber;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemNumber=" + itemNumber + ", type=" + type + ", name=" + name + ", amount=" + amount + ", price=" + price
				+ ", components=" + Arrays.toString(components) + "]";
	}

}
