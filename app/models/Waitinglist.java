/**
 * @author Boris
 */

package models;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.jpa.GenericModel;

public class Waitinglist extends GenericModel {

	private int period;
	private int order;
	private int firstbatch;
	private int lastbatch;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "id")
	private Item item;
	private int amount;
	private int timeneed;

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getFirstbatch() {
		return firstbatch;
	}

	public void setFirstbatch(int firstbatch) {
		this.firstbatch = firstbatch;
	}

	public int getLastbatch() {
		return lastbatch;
	}

	public void setLastbatch(int lastbatch) {
		this.lastbatch = lastbatch;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTimeneed() {
		return timeneed;
	}

	public void setTimeneed(int timeneed) {
		this.timeneed = timeneed;
	}
}
