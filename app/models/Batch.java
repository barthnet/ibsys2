/**
 * 
 */
package models;

/**
 * This Class represents a part of a production order. A pOrder consists of several batches. One
 * batch produces 10 items
 * 
 * @author Woda
 * 
 */
public class Batch {

	/**
	 * ID nur innnerhalb eines Produktionsauftrages eindeutig.
	 */
	private Long mId;

	/**
	 * Menge
	 */
	private int mAmmount;

	/**
	 * Kosten
	 */
	private double mCost;

	/**
	 * Durchlaufzeit
	 */
	private double mCycletime;

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
	 * @return the ammount
	 */
	public int getAmmount() {
		return mAmmount;
	}

	/**
	 * @param ammount
	 *            the ammount to set
	 */
	public void setAmmount(int ammount) {
		this.mAmmount = ammount;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return mCost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(double cost) {
		this.mCost = cost;
	}

	/**
	 * @return the cycletime
	 */
	public double getCycletime() {
		return mCycletime;
	}

	/**
	 * @param cycletime
	 *            the cycletime to set
	 */
	public void setCycletime(double cycletime) {
		this.mCycletime = cycletime;
	}

}
