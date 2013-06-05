package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class ItemTime extends Model {

	public int workplace;

	public String item;

	public int setupTime;
	public int processTime;

	public Item getItemAsObject() {
		return Item.find("byItem", this.item).first();
	}

	public Workplace getWorkplaceAsObject() {
		return Workplace.find("byWorkplaceId", this.workplace).first();
	}

	@Override
	public String toString() {
		return "ItemTime [workplace=" + workplace + ", item=" + item + ", setupTime=" + setupTime + ", processTime=" + processTime + "]";
	}

}
