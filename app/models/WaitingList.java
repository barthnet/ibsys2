package models;

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

	public int workplace;

	public int orderNumber;
	public int period;
	public int amount;
	public int timeneed;

	@PrePersist
	protected void onCreate() {
		this.waitingListId = this.workplace + "" + this.period + "" + this.orderNumber + "" + this.amount;
	}

	public Item getItemAsObject() {
		return Item.find("byItemId", this.item).first();
	}

	public Workplace getWorkplaceAsObject() {
		return Workplace.find("byWorkplaceId", this.workplace).first();
	}

	@Override
	public String toString() {
		return "WaitingList [waitingListId=" + waitingListId + ", item=" + item + ", workplace=" + workplace + ", orderNumber=" + orderNumber + ", period="
				+ period + ", amount=" + amount + ", timeneed=" + timeneed + "]";
	}

}
