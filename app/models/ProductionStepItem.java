package models;

import javax.persistence.Id;

import play.db.jpa.GenericModel;

public class ProductionStepItem extends GenericModel {

	@Id
	private Long id;
	private Long productionstep_id;
	private Long item_id;
	private int amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductionstep_id() {
		return productionstep_id;
	}

	public void setProductionstep_id(Long productionstep_id) {
		this.productionstep_id = productionstep_id;
	}

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
