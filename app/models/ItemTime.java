package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class ItemTime extends Model {

	public String workplace;

	public String item;

	public int setupTime;
	public int processTime;

}
