/**
 * @author Boris
 */

package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.Model;

@Entity
public class OrderItem extends Model {

	@Id
	private Long id;
	private Item item;
	private int mode;
	private int deliverydays;
	private double deviance;

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

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getDeliverydays() {
		return deliverydays;
	}

	public void setDeliverydays(int deliverydays) {
		this.deliverydays = deliverydays;
	}

	public double getDeviance() {
		return deviance;
	}

	public void setDeviance(double deviance) {
		this.deviance = deviance;
	}
}
