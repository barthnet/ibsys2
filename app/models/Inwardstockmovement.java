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
public class Inwardstockmovement extends GenericModel {

	@Id
	private Long id;
	private int period;
	
	@OneToOne
	private Order order;
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

	public Order getOrder() {
		return order;
	}

	public void setOrders_id(Order order) {
		this.order = order;
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
