package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Component extends Model {

	public String item;
	public int amount;
	
}
