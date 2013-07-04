package models;

import java.util.List;

import javax.persistence.*;

import play.db.jpa.*;

/**
 * muss OpenOrder heißen, da es mit Order anscheinend zu Namenkonflikten kommt
 * 
 * @author mopa
 * 
 */
@Entity
public class OpenOrder extends Model {

	public String item;
	public String user;

	public int mode;
	public int orderPeriod;
	public int amount;
	public double expectedArrival;

	public Item getItemAsObject() {
		return Item.find("byItemIdAndUser", this.item, this.user).first();
	}
	
	public static void deleteAll(String userName) {
		List<OpenOrder> caps = OpenOrder.find("byUser", userName).fetch();
		for (OpenOrder capacity : caps) {
			capacity.delete();
		}
	}

	@Override
	public String toString() {
		return "OpenOrder [item=" + item + ", mode=" + mode + ", orderPeriod="
				+ orderPeriod + ", amount=" + amount + ", expectedArrival="
				+ expectedArrival + "]";
	}

}
