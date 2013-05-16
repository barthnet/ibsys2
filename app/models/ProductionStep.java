/**
 * 
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.jpa.GenericModel;

/**
 * @author Woda
 * 
 */
@Entity
public class ProductionStep extends GenericModel {

	@Id
	private Long id;

	@OneToOne
	private Item item;

	@OneToOne
	private Workstation workstation;

	@OneToOne
	private ProductionStep prev_productionstep;

	@OneToOne
	private ProductionStep next_productionstep;

	private int production_time;
	private int setup_time;

	public Workstation getWorkstation() {
		return workstation;
	}

	public void setWorkstation(Workstation workstation) {
		this.workstation = workstation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getProduction_time() {
		return production_time;
	}

	public void setProduction_time(int production_time) {
		this.production_time = production_time;
	}

	public int getSetup_time() {
		return setup_time;
	}

	public void setSetup_time(int setup_time) {
		this.setup_time = setup_time;
	}

	public ProductionStep getPrev_productionstep() {
		return prev_productionstep;
	}

	public void setPrev_productionstep(ProductionStep prev_productionstep) {
		this.prev_productionstep = prev_productionstep;
	}

	public ProductionStep getNext_productionstep() {
		return next_productionstep;
	}

	public void setNext_productionstep(ProductionStep next_productionstep) {
		this.next_productionstep = next_productionstep;
	}

}
