package models;

import javax.persistence.Entity;

/**
 * This Class describes a purchased part.
 * 
 * @extends {@link AbstractPart}
 * @author Woda
 * 
 */
@Entity
public class PurchasedPart extends AbstractPart {

	/**
	 * Lieferzeit
	 */
	private double mDeliveryTime;

	/**
	 * Abweichung der Lieferzeit
	 */
	private double mVariance;

	/**
	 * Preis
	 */
	private double mPrice;

	/**
	 * Bestellkosten
	 */
	private double mOrdercosts;

	/**
	 * Diskontmenge
	 */
	private int mDiscountAmount;

	/**
	 * @return the deliveryTime
	 */
	public double getDeliveryTime() {
		return mDeliveryTime;
	}

	/**
	 * @param deliveryTime
	 *            the deliveryTime to set
	 */
	public void setDeliveryTime(double deliveryTime) {
		this.mDeliveryTime = deliveryTime;
	}

	/**
	 * @return the variance
	 */
	public double getVariance() {
		return mVariance;
	}

	/**
	 * @param variance
	 *            the variance to set
	 */
	public void setVariance(double variance) {
		this.mVariance = variance;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return mPrice;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.mPrice = price;
	}

	/**
	 * @return the ordercosts
	 */
	public double getOrdercosts() {
		return mOrdercosts;
	}

	/**
	 * @param ordercosts
	 *            the ordercosts to set
	 */
	public void setOrdercosts(double ordercosts) {
		this.mOrdercosts = ordercosts;
	}

	/**
	 * @return the discountAmount
	 */
	public int getDiscountAmount() {
		return mDiscountAmount;
	}

	/**
	 * @param discountAmount
	 *            the discountAmount to set
	 */
	public void setDiscountAmount(int discountAmount) {
		this.mDiscountAmount = discountAmount;
	}
}
