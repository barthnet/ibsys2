/**
 * 
 */
package models;

import javax.persistence.Id;

import play.db.jpa.GenericModel;

/**
 * @author Woda
 * 
 */
public class ProductionStep extends GenericModel {

	@Id
	private Long id;
	private Long item_id;
	private Long workstation_id;
	private Long prev_fertigungsschritt_id;
	private Long next_fertigungsschritt_id;
	private int production_time;
	private int setup_time;

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

	public Long getWorkstation_id() {
		return workstation_id;
	}

	public void setWorkstation_id(Long workstation_id) {
		this.workstation_id = workstation_id;
	}

	public Long getPrev_fertigungsschritt_id() {
		return prev_fertigungsschritt_id;
	}

	public void setPrev_fertigungsschritt_id(Long prev_fertigungsschritt_id) {
		this.prev_fertigungsschritt_id = prev_fertigungsschritt_id;
	}

	public Long getNext_fertigungsschritt_id() {
		return next_fertigungsschritt_id;
	}

	public void setNext_fertigungsschritt_id(Long next_fertigungsschritt_id) {
		this.next_fertigungsschritt_id = next_fertigungsschritt_id;
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

}
