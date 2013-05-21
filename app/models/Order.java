/**
 * @author Sven 
 */

package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import play.db.jpa.GenericModel;

@Entity
@Table(name = "orders")
public class Order extends GenericModel {

	@Id
	private Long id;

	private int period;
	private int order_id;
	private String mode;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "id")
	private Item item;
	private int amount;

	public Order(Long id, Item item) {
		super();
		this.id = id;
		this.item = item;
	}

	public Order() {
		super();
	}

	public Order(Long id, String mowl) {
		super();
		this.id = id;
		this.mode = mowl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
