package models;

import javax.persistence.Entity;

/**
 * A class for the incoming orders in the stock.
 * 
 * 
 * @extends {@link AbstractOrder}
 * @author Woda
 * 
 */
@Entity
public class ArrivedOrder extends AbstractOrder {

	/**
	 * Gesamtkosten (Materialkosten + Bestellkosten)
	 */
	private double mEntirecosts;

	/**
	 * Materialkosten
	 */
	private double mMaterialcosts;

	/**
	 * Bestellkosten
	 */
	// TODO eventuell auslagern ? ist schon in der Artikelklasse vorhanden.
	private double mOrdercosts;

	/**
	 * Stückkosten
	 */
	private double mPiececosts;

	/**
	 * Zeit
	 */
	private int mTime;

	/**
	 * @return the entirecosts
	 */
	public double getEntirecosts() {
		return mEntirecosts;
	}

	/**
	 * @param entirecosts
	 *            the entirecosts to set
	 */
	public void setEntirecosts(double entirecosts) {
		this.mEntirecosts = entirecosts;
	}

	/**
	 * @return the materialcosts
	 */
	public double getMaterialcosts() {
		return mMaterialcosts;
	}

	/**
	 * @param materialcosts
	 *            the materialcosts to set
	 */
	public void setMaterialcosts(double materialcosts) {
		this.mMaterialcosts = materialcosts;
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
	 * @return the piececosts
	 */
	public double getPiececosts() {
		return mPiececosts;
	}

	/**
	 * @param piececosts
	 *            the piececosts to set
	 */
	public void setPiececosts(double piececosts) {
		this.mPiececosts = piececosts;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return mTime;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(int time) {
		this.mTime = time;
	}

}
