package models;

import java.util.List;

import play.db.jpa.Model;

/**
 * A abstract class for all parts.
 * 
 * @extends {@link Model}
 * @author Woda
 * 
 */
public abstract class AbstractPart extends Model {

	/**
	 * Artikelnummer
	 */
	private Long mId;

	/**
	 * Lagerbestand
	 */
	private int mAmount;

	/**
	 * Lagerwert
	 */
	private double mStockvalue;

	/**
	 * Startmenge
	 */
	private int mStartamount;

	/**
	 * Menge/Startmenge
	 */
	private double mPct;

	/**
	 * Preis
	 */
	private double mPrice;

	/**
	 * Bezeichnung
	 */
	private String mName;

	/**
	 * Teileverwendung
	 */
	private List<Integer> mUtilization;

	/**
	 * @return the id
	 */
	public Long getId() {
		return mId;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return mAmount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(int amount) {
		this.mAmount = amount;
	}

	/**
	 * @return the stockvalue
	 */
	public double getStockvalue() {
		return mStockvalue;
	}

	/**
	 * @param stockvalue
	 *            the stockvalue to set
	 */
	public void setStockvalue(double stockvalue) {
		this.mStockvalue = stockvalue;
	}

	/**
	 * @return the startamount
	 */
	public int getStartamount() {
		return mStartamount;
	}

	/**
	 * @param startamount
	 *            the startamount to set
	 */
	public void setStartamount(int startamount) {
		this.mStartamount = startamount;
	}

	/**
	 * @return the pct
	 */
	public double getPct() {
		return mPct;
	}

	/**
	 * @param pct
	 *            the pct to set
	 */
	public void setPct(double pct) {
		this.mPct = pct;
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
	 * @return the name
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.mName = name;
	}

	/**
	 * @return the utilization
	 */
	public List<Integer> getUtilization() {
		return mUtilization;
	}

	/**
	 * @param utilization
	 *            the utilization to set
	 */
	public void setUtilization(List<Integer> utilization) {
		this.mUtilization = utilization;
	}

}
