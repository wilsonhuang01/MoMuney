package com.example.momuney.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

	// The list of Transactions.
	private List<Transaction> transactions;
	// Categories.
	private Map<String, Category> categories;

	/**
	 * This constructor should only be called by DbConnection.
	 */
	public User() {
		super();
	}

	/**
	 * Create a new User with specified Transactions and Categories.
	 * @param transactions the list of Transactions
	 * @param categories the list of Categories
	 */
	public User(List<Transaction> transactions, Map<String, Category> categories) {
		if (transactions == null) throw new NullPointerException("transactions is null");
		if (categories == null) throw new NullPointerException("categories is null");

		this.transactions = transactions;
		this.categories = categories;
	}

	/**
	 * Create a default User with no Transactions or Categories.
	 * @return a default User with no Transactions or Categories
	 */
	public static User createDefaultUser() {
		// create a new user object to store the data
		List<Transaction> transactions = new ArrayList<>();
		Map<String, Category> categories = new HashMap<>();
		return new User(transactions, categories);
	}

	/**
	 * Add a Transaction.
	 * @param transaction the Transaction to be added
	 */
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

	/**
	 * Add the name and amount to the Category.
	 * @param name the name of the Category
	 * @param amount the amount spent
	 */
	public void addToCategory(String name, double amount) {
		if (!categories.containsKey(name)) {
			createCategory(name);
		}

		categories.get(name).addSpending(amount);
	}

	/**
	 * Create a new Category if the Category does not exist.
	 * @param name the name of the Category
	 * @return true if the Category is successfully created, otherwise false
	 */
	public boolean createCategory(String name) {
		if (categories.containsKey(name)) return false;

		Category temp = new Category(name, 0);
		categories.put(name, temp);

		return categories.containsKey(name);
	}

	/**
	 * Get the list of Transactions.
	 * @return the list of Transactions
	 */
	public List<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * get the list of Categories.
	 * @return the list of Categories
	 */
	public Map<String, Category> getCategories() {
		return categories;
	}

	/**
	 * Get the String representation of the User.
	 * @return the String representation of the User
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(transactions);
		sb.append(categories);

		return sb.toString();
	}
}
