package models;

import javax.persistence.*;

import flexjson.JSON;

import play.db.jpa.*;

/**
 * @author Boris
 */
@Entity
public class Item extends Model {

	public int itemId;
	public String name;
	public String name_en;
	public String type;
	public int amount;
	public double price;
	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", name=" + name + ", name_en=" + name_en + ", type=" + type + ", amount=" + amount + ", price=" + price + ", id="
				+ id + "]";
	}
	
//	@JSON
//	public String getItemId() {
//		return type + itemId;
//	}

	

}
