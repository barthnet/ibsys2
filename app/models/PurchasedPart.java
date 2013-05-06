package models;

import javax.persistence.Entity;

@Entity
public class PurchasedPart extends AbstractPart {

	private double deliveryTime;
	private double variance;
	
	public double getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(double deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public double getVariance() {
		return variance;
	}
	public void setVariance(double variance) {
		this.variance = variance;
	}
	
	
}
