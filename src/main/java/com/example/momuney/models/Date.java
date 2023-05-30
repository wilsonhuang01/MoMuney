package com.example.momuney.models;

public class Date {
	
	/** The String representation of month. */
	private static final String[] monthStr = new String[] {"January", "Febuary", "March", "April"
			, "May", "June", "July", "August", "September", "October", "November", "December"};
	
	/** The month of the Date. */
	private int month;
	/** The day of the Date. */
	private int day;
	/** The year of the Date. */
	private int year;
	
	/**
	 * This constructor should only be called by DbConnection.
	 */
	public Date() {
		super();
	}
	
	/**
	 * Construct a new Date with specified month, day, and year.
	 * @param month the month of the Date
	 * @param day the day of the Date
	 * @param year the year of the Date
	 */
	public Date(int month, int day, int year) {
		if (month < 1 || month > 12) throw new IllegalArgumentException("month is invalid");
		if (day < 1 || day > 31) throw new IllegalArgumentException("day is invalid");
		if (year < 0) throw new IllegalArgumentException("year is invalid");
		
		this.month = month;
		this.day = day;
		this.year = year;
	}
	
	/**
	 * Get the month of the Date.
	 * @return the month of the Date
	 */
	public int getMonth() {
		return month;
	}
	
	/**
	 * Get the String representation of the month of the Date.
	 * @param monthAbbreviated true if the String representation shall be abbreviated to 3 characters
	 * @return the String representation of the month of the Date
	 */
	public String getMonthStr(boolean monthAbbreviated) {
		return monthAbbreviated ? monthStr[month - 1].substring(0, 3) : monthStr[month - 1];
	}
	
	/**
	 * Get the day of the Date.
	 * @return the day of the Date
	 */
	public int getDay() {
		return day;
	}
	
	/**
	 * Get the year of the Date.
	 * @return the year of the Date
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Get the String representation of the Date with '/' as the separator.
	 * Ex. 08/24/2021
	 * @return the String representation of the Date with '/' as the separator
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (month < 10) {
			sb.append('0');
			sb.append(month);
		} else {
			sb.append(month);
		}
		sb.append('/');
		
		if (day < 10) {
			sb.append('0');
			sb.append(day);
		} else {
			sb.append(day);
		}
		sb.append('/');
		
		sb.append(year);
		
		return sb.toString();
	}
	
	/**
	 * Get the String representation of the Date with ' ' and ',' as separators.
	 * Ex. August 24, 2021 or Aug 24, 2021
	 * @param monthAbbreviated true if the month shall be abbreviated to 3 characters
	 * @return the String representation of the Date with ' ' and ',' as separators
	 */
	public String toStringStr(boolean monthAbbreviated) {
		StringBuilder sb = new StringBuilder();
		sb.append(getMonthStr(monthAbbreviated));
		sb.append(' ');
		sb.append(day);
		sb.append(", ");
		sb.append(year);
		
		return sb.toString();
	}
	
	/**
	 * Compare this Date to another Date object.
	 * @param other the other Date to be compared
	 * @return 0 if this Date and the other Date have the same month, day, and year;
	 * a negative number if this Date comes before the other Date, otherwise a positive number
	 */
	public int compareTo(Date other) {
		if (year != other.year)
			return year - other.year;
		if (month != other.month)
			return month - other.month;
		return day - other.day;
	}
}
