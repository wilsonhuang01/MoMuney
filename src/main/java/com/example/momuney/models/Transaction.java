package com.example.momuney.models;

public class Transaction {

	// The Vendor of the Transaction.
	private Vendor vendor;
	// The name of the Category of the Transaction.
	private String category;
	// The Date when the Transaction took place.
	private Date date;
	// The amount spent on the Transaction.
	private double amount;

	/**
	 * This constructor should only be called by DbConnection.
	 */
	public Transaction() {
		super();
	}

	/**
	 * Create a new Transaction with specified Vendor, Category, Date, and amount.
	 * @param vendor the Vendor of the Transaction
	 * @param category the name of the Category of the Transaction
	 * @param date the Date when the Transaction took place
	 * @param amount the amount spent on the Transaction
	 */
	public Transaction(Vendor vendor, String category, Date date, double amount) {
		if (vendor == null) throw new NullPointerException("vendor is null");
		if (category == null) throw new NullPointerException("category is null");
		if (date == null) throw new NullPointerException("date is null");
		if (amount < 0) throw new IllegalArgumentException("amount is negative");

		this.vendor = vendor;
		this.category = category;
		this.date = date;
		this.amount = amount;
	}

	/**
	 * Get the Vendor of the Transaction.
	 * @return the Vendor of the Transaction
	 */
	public Vendor getVendor() {
		return vendor;
	}

	/**
	 * Get the name of the Category of the Transaction.
	 * @return the name of the Category of the Transaction
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Get the Date of the Transaction.
	 * @return the Date of the Transaction
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Get the amount spent on the Transaction.
	 * @return the amount spent on the Transaction
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Get the String representation of the Transaction.
	 * Ex. Whole Foods -- San Jose
	 *     Grocery
	 *     08/24/2021
	 *     13.29
	 * @return the String representation of the Transaction
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(vendor);
		sb.append("\n");
		sb.append(category);
		sb.append("\n");
		sb.append(date);
		sb.append("\n");
		sb.append(amount);

		return sb.toString();
	}
}
