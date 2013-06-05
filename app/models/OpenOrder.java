package models;

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

	public int mode;
	public int orderPeriod;
	public int amount;

	public Item getItemAsObject() {
		return Item.find("byItemId", this.item).first();
	}

	@Override
	public String toString() {
		return "OpenOrder [item=" + item + ", mode=" + mode + ", orderPeriod=" + orderPeriod + ", amount=" + amount + "]";
	}

}
