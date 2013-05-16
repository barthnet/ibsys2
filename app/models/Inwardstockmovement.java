/**
 * 
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.GenericModel;

/**
 * @author Woda
 * 
 */
@Entity
public class Inwardstockmovement extends GenericModel {

	@Id
	private Long id;
	private int period;
	private Long orders_id;
	private int time;
	private double materialcosts;
	private double entirecosts;
	private double piececosts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public Long getOrders_id() {
		return orders_id;
	}

	public void setOrders_id(Long orders_id) {
		this.orders_id = orders_id;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public double getMaterialcosts() {
		return materialcosts;
	}

	public void setMaterialcosts(double materialcosts) {
		this.materialcosts = materialcosts;
	}

	public double getEntirecosts() {
		return entirecosts;
	}

	public void setEntirecosts(double entirecosts) {
		this.entirecosts = entirecosts;
	}

	public double getPiececosts() {
		return piececosts;
	}

	public void setPiececosts(double piececosts) {
		this.piececosts = piececosts;
	}

}
