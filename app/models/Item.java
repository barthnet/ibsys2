package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

/**
 * @author Boris
 */
@Entity
public class Item extends Model {

	public String itemId;
	public String name;
	public String name_en;
	public String type;
	public int amount;
	public double price;
	
	public String[] components;
	
	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", name=" + name + ", name_en=" + name_en + ", type=" + type + ", amount=" + amount + ", price=" + price + ", id="
				+ id + "]";
	}
	
	public void addComp(Component comp) {
		if (components == null) {
//			components = new ArrayList<>();
		}
//		components.add(comp);
	}
	
//	@JSON
//	public String getItemId() {
//		return type + itemId;
//	}

	

}
