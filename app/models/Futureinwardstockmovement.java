/**
 * 
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.jpa.GenericModel;

/**
 * @author Woda
 * 
 */
@Entity
public class Futureinwardstockmovement extends GenericModel {

	@Id
	private Long id;

	@OneToOne
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	private Long article_id;
	private String mode;
	private String orderperiod;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArticle_id() {
		return article_id;
	}

	public void setArticle_id(Long article_id) {
		this.article_id = article_id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getOrderperiod() {
		return orderperiod;
	}

	public void setOrderperiod(String orderperiod) {
		this.orderperiod = orderperiod;
	}
}
