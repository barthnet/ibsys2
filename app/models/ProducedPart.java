package models;

import java.util.HashMap;

import javax.persistence.Entity;

/**
 * A class for the self produced parts.
 * 
 * @extends {@link AbstractPart}
 * @author DaNieKL
 * 
 */
@Entity
public class ProducedPart extends AbstractPart {

	/**
	 * Bearbeitungszeiten und Rüstzeiten. Die erste Map dient dazu, den Bearbeitungsplatz anhand der
	 * Nummer abzuspeichern und das Array speichert Bearbeitungsdauer und Rüstzeit.
	 */
	private HashMap<Integer, int[][]> mDuration;

	/**
	 * @return the mDuration
	 */
	public HashMap<Integer, int[][]> getDuration() {
		return mDuration;
	}

	/**
	 * @param mDuration
	 *            the mDuration to set
	 */
	public void setDuration(HashMap<Integer, int[][]> duration) {
		this.mDuration = duration;
	}

}
