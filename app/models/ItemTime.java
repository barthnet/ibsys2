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

	public Item getItemAsObject(String userName) {
		return Item.find("byItemAndUser", this.item, userName).first();
	}

	public Workplace getWorkplaceAsObject(String userName) {
		return Workplace.find("byWorkplaceIdAndUser", this.workplace, userName).first();
	}

	@Override
	public String toString() {
		return "ItemTime [workplace=" + workplace + ", item=" + item + ", setupTime=" + setupTime + ", processTime=" + processTime + "]";
	}

}
