package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import play.db.jpa.Model;

@Entity
public class Component extends Model {

	public String componentId;
	
	public String item;
	public String parent;

	public int amount;

	public Item getItemAsObject() {
		return Item.find("byItem", this.item).first();
	}

	public Item getParentAsObject() {
		return Item.find("byItem", this.item).first();
	}
	
	@PrePersist
	protected void onCreate() {
		this.componentId = this.parent + this.item;
	}

	@Override
	public String toString() {
		return "Component [item=" + item + ", amount=" + amount + "]";
	}

}
