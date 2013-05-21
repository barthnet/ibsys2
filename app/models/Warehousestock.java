/**
 * 
 */
package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.jpa.GenericModel;

/**
 * @author Woda
 * 
 */
@Entity
public class Warehousestock extends GenericModel {

	@Id
	private Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "id")
	private Item item;
	private int amount;
	private int startamount;
	private double pct;
	private double price;
	private double stockvalue;

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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getStartamount() {
		return startamount;
	}

	public void setStartamount(int startamount) {
		this.startamount = startamount;
	}

	public double getPct() {
		return pct;
	}

	public void setPct(double pct) {
		this.pct = pct;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getStockvalue() {
		return stockvalue;
	}

	public void setStockvalue(double stockvalue) {
		this.stockvalue = stockvalue;
	}

}
