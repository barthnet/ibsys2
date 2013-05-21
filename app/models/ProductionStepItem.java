package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.jpa.GenericModel;

/**
 * 
 * @author Wod
 * 
 */
@Entity
public class ProductionStepItem extends GenericModel {

	@Id
	private Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "id")
	private ProductionStep productionstep;

	public ProductionStep getProductionstep() {
		return productionstep;
	}

	public void setProductionstep(ProductionStep productionstep) {
		this.productionstep = productionstep;
	}

	private Long item_id;
	private int amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
