package com.example.momuney.models;

import java.util.Map;

public class Category {

	// The name of the Category. Ex. Grocery, Snack, Books.
	private String name;
	// The current spending of this Category.
	private double spending;

	/**
	 * This constructor should only be called by DbConnection.
	 */
	public Category() {
		super();
	}

	/**
	 * Construct a new Category with a specified name and spending.
	 * @param name the name of the Category
	 * @param spending the current spending of the Category
	 */
	public Category(String name, double spending) {
		if (name == null) throw new NullPointerException("name is null");
		if (spending < 0) throw new IllegalArgumentException("spending is negative");

		this.name = name;
		addSpending(spending);
	}

	/**
	 * Add spending to the Category.
	 * @param spending the spending to be added
	 */
	public void addSpending(double spending) {
		if (spending < 0) throw new IllegalArgumentException("spending is negative");

		this.spending += spending;
		roundSpending();
	}

	/**
	 * Get the name of the Category.
	 * @return the name of the Category.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the current spending of the Category.
	 * @return the current spending of the Category
	 */
	public double getSpending() {
		return spending;
	}

	/**
	 * Get the String representation of the Category.
	 * @return the String representation of the Category
	 */
	public String toString() {
		return name + " -- " + spending;
	}

	/**
	 * Check if the Categories of map1 and map2 are equal.
	 * @param map1 the first map
	 * @param map2 the second map
	 * @return true if the Categories are equal
	 */
	public static boolean categoriesEqual(Map<String, Category> map1, Map<String, Category> map2) {
		if (map1 == null) throw new NullPointerException("map1 is null");
		if (map2 == null) throw new NullPointerException("map2 is null");

		for (String name : map1.keySet()) {
			if (!map2.containsKey(name)) return false;
			if (map1.get(name).getSpending() != map2.get(name).getSpending()) return false;
		}

		return true;
	}

	/**
	 * Round the spending to 2 decimal places.
	 */
	private void roundSpending() {
		spending = (double) Math.round(100 * spending) / 100;
	}
}