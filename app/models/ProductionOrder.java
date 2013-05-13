/**
 * 
 */
package models;

import java.util.List;

import javax.persistence.Entity;

import play.db.jpa.Model;

/**
 * This class represents the completed production orders
 * 
 * @author Woda
 * 
 */
@Entity
public class ProductionOrder extends Model {

	/**
	 * Fertigungsauftragsnummer
	 */
	private Long mId;

	/**
	 * Durchschnittliche Stückkosten
	 */
	private double mAverageUnitCosts;

	/**
	 * Gesamtkosten
	 */
	private double mCost;

	/**
	 * Artikelnummer
	 */
	private int mItem;

	/**
	 * Periode
	 */
	private int mPeriod;

	/**
	 * Menge
	 */
	private int mQuantity;

	/**
	 * Fertigungslose
	 */
	private List<Batch> mBatch;

	/**
	 * @return the Id
	 */
	public Long getId() {
		return mId;
	}

	/**
	 * @param id
	 *            the Id to set
	 */
	public void setId(Long id) {
		this.mId = id;
	}

	/**
	 * @return the AverageUnitCosts
	 */
	public double getAverageUnitCosts() {
		return mAverageUnitCosts;
	}

	/**
	 * @param averageUnitCosts
	 *            the averageUnitCosts to set
	 */
	public void setAverageUnitCosts(double averageUnitCosts) {
		this.mAverageUnitCosts = averageUnitCosts;
	}

	/**
	 * @return the Cost
	 */
	public double getCost() {
		return mCost;
	}

	/**
	 * @param cost
	 *            the Cost to set
	 */
	public void setmCost(double cost) {
		this.mCost = cost;
	}

	/**
	 * @return the Item
	 */
	public int getItem() {
		return mItem;
	}

	/**
	 * @param item
	 *            the Item to set
	 */
	public void setItem(int item) {
		this.mItem = item;
	}

	/**
	 * @return the mPeriod
	 */
	public int getPeriod() {
		return mPeriod;
	}

	/**
	 * @param period
	 *            the Period to set
	 */
	public void setPeriod(int period) {
		this.mPeriod = period;
	}

	/**
	 * @return the Quantity
	 */
	public int getQuantity() {
		return mQuantity;
	}

	/**
	 * @param quantity
	 *            the Quantity to set
	 */
	public void setQuantity(int quantity) {
		this.mQuantity = quantity;
	}

	/**
	 * @return the Batch
	 */
	public List<Batch> getBatch() {
		return mBatch;
	}

	/**
	 * @param batch
	 *            the Batch to set
	 */
	public void setBatch(List<Batch> batch) {
		this.mBatch = batch;
	}

	/**
	 * Fügt ein Los dem Fertigungsauftrag hinzu.
	 */
	public void addBatch(Batch batch) {
		mBatch.add(batch);
	}

	/**
	 * Entfernt ein Los aus dem Fertigungsauftrag.
	 */
	public void removeBatch(Batch batch) {
		mBatch.remove(batch);
	}

}
