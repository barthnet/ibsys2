package models;

import play.db.jpa.Model;

/**
 * Abstrakte Klasse fuer einen Artikel
 *
 * @author Woda
 *
 */
public abstract class AbstractPart extends Model {

	/**
	 * Artikelnummer
	 */
	private Long id;

	/**
	 * Lagerbestand
	 */
	private int amount;

	/**
	 * Lagerwert
	 */
	private double stockvalue;

	/**
	 * Startmenge
	 */
	private int startamount;

	/**
	 * Menge/Startmenge
	 */
	private double pct;

	/**
	 * Preis
	 */
	private double price;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the stockvalue
	 */
	public double getStockvalue() {
		return stockvalue;
	}

	/**
	 * @param stockvalue
	 *            the stockvalue to set
	 */
	public void setStockvalue(double stockvalue) {
		this.stockvalue = stockvalue;
	}

	/**
	 * @return the startamount
	 */
	public int getStartamount() {
		return startamount;
	}

	/**
	 * @param startamount
	 *            the startamount to set
	 */
	public void setStartamount(int startamount) {
		this.startamount = startamount;
	}

	/**
	 * @return the pct
	 */
	public double getPct() {
		return pct;
	}

	/**
	 * @param pct
	 *            the pct to set
	 */
	public void setPct(double pct) {
		this.pct = pct;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

}
