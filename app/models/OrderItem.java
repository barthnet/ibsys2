/**
 * @author Boris
 */

package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.jpa.GenericModel;

@Entity
public class OrderItem extends GenericModel {

	@Id
	private Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "id")
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
