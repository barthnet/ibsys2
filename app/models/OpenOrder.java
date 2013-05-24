package models;

import javax.persistence.*;

import play.db.jpa.*;

/**
 * muss OpenOrder heißen, da es mit Order anscheinend zu Namenkonflikten kommt
 * @author mopa
 *
 */
@Entity
public class OpenOrder extends Model {
	
	public int mode;
	public int orderperiod;
	public int amount;

	@OneToOne
	public Item item;

	@Override
	public String toString() {
		return "OpenOrder [mode=" + mode + ", orderperiod=" + orderperiod + ", amount=" + amount + "item=" + item + "]";
	}
	
}
