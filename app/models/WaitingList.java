package models;

import java.util.List;

import javax.persistence.*;

import play.db.jpa.Model;

/**
 * 
 * @author sven
 * 
 */
@Entity
public class WaitingList extends Model {

	public String waitingListId;

	public String item;
	public String user;

	public int workplace;

	public int orderNumber;
	public int period;
	public int amount;
	public int timeneed;
	public boolean inWork = false;

	@PrePersist
	protected void onCreate() {
		this.waitingListId = this.workplace + "" + this.period + "" + this.orderNumber + "" + this.amount;
	}

	public Item getItemAsObject() {
		return Item.find("byItemIdAndUser", this.item, this.user).first();
	}
	
	public static void deleteAll(String userName) {
		List<WaitingList> caps = WaitingList.find("byUser", userName).fetch();
		for (WaitingList capacity : caps) {
			capacity.delete();
		}
	}

	public Workplace getWorkplaceAsObject() {
		return Workplace.find("byWorkplaceIdAndUser", this.workplace, this.user).first();
	}

	@Override
	public String toString() {
		return "WaitingList [waitingListId=" + waitingListId + ", item=" + item + ", workplace=" + workplace + ", orderNumber=" + orderNumber + ", period="
				+ period + ", amount=" + amount + ", timeneed=" + timeneed + "]";
	}

}
