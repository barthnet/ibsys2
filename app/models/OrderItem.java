/**
 * @author Boris
 */

package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class OrderItem extends Model {

	Long id;
	Item item;
	int mode;
	int deliverydays;
	double deviance;

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
