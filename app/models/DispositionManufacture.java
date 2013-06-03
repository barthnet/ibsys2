package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class DispositionManufacture extends Model {

	@OneToOne
	public Item item;
	
//	@OneToMany
	public String[] childs;

	public int distributionWish;
	public int stock;
	public int safetyStock;
	public int waitingList;
	public int processList;
	public int production;

	@Override
	public String toString() {
		return "DispositionManufacture [item=" + item + ", distributionWish="
				+ distributionWish + ", stock=" + stock + ", safetyStock="
				+ safetyStock + ", waitingList=" + waitingList
				+ ", processList=" + processList + ", production=" + production
				+ "]";
	}

}
