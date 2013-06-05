package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Component extends Model {

	public String item;

	public int amount;

	public Item getItemAsObject() {
		return Item.find("byItem", this.item).first();
	}

	@Override
	public String toString() {
		return "Component [item=" + item + ", amount=" + amount + "]";
	}
	
}
