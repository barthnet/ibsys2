package models;

import java.security.InvalidParameterException;

/**
 * 
 * @author mopa
 *
 */
public class PeriodDate {
	
	private int period;
	
	private int day;
	
	public PeriodDate(int period, int day) {
		this.period = period;
		this.day = day;
		validate();
	}
	
	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {		
		this.day = day;
	}
	
	private void validate() {
		if (day < 0 || day > 5)
			throw new InvalidParameterException("Must between 0 and 5");
	}

}
