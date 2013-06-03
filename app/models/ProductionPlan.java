package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

/**
 * 
 * @author sven
 * 
 */
@Entity
public class ProductionPlan extends Model {

	
	public List<String> dispositionManufacture;

	public String product;

	// @OneToMany
	// public List<DispositionManufacture> dispositionManufactureP2;
	// @OneToMany
	// public List<DispositionManufacture> dispositionManufactureP3;
	
	

	@Override
	public String toString() {
		return "ProductionPlan [dispositionManufacture="
				+ dispositionManufacture + ", product=" + product + "]";
	}

}
