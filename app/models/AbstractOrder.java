package models;

import play.db.jpa.Model;

/**
 * A abstract class for the real orders of the purchased parts and not a batch order.
 * 
 * @extends {@link Model}
 * @author Woda
 * 
 */
public abstract class AbstractOrder extends Model {

	/**
	 * Eindeutige ID
	 */
	private Long mId;
	
	/**
	 * Artikelnummer
	 */
	private int mArticle;
	
	/**
	 * Menge
	 */
	private int mAmount;
	
	/**
	 * Bestellperiode
	 */
	private int mOrderperiod;
	
	/**
	 * Art der Bestellung
	 * 5: normal
	 * 4: fast
	 * 3: JIT
	 * 2: cheap vendor
	 * 1: special order
	 */
	private int mMode;
	

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
		this.mId = id;
	}

	/**
	 * @return the article
	 */
	public int getArticle() {
		return mArticle;
	}

	/**
	 * @param article
	 *            the article to set
	 */
	public void setArticle(int article) {
		this.mArticle = article;
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
	 * @return the orderperiod
	 */
	public int getOrderperiod() {
		return mOrderperiod;
	}

	/**
	 * @param orderperiod
	 *            the orderperiod to set
	 */
	public void setOrderperiod(int orderperiod) {
		this.mOrderperiod = orderperiod;
	}

	/**
	 * @return the mMode
	 */
	public int getMode() {
		return mMode;
	}

	/**
	 * @param mMode the mMode to set
	 */
	public void setMode(int mode) {
		this.mMode = mode;
	}

}
