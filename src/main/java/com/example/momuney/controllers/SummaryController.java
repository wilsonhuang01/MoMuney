package com.example.momuney.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.momuney.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.example.momuney.models.Category;
import com.example.momuney.models.Date;
import com.example.momuney.DbConnection;
import com.example.momuney.models.Transaction;
import com.example.momuney.models.User;

public class SummaryController {
	
	// Connection to the database.
	private DbConnection manager = DbConnection.getInstance();
	// The User object.
	private User user = manager.getUser();
	// The list of Transactions.
	private List<Transaction> transactions = user.getTransactions();
	private Map<String, Category> categories = user.getCategories();
	
	@FXML Button clearDateFromFilter;
	@FXML Button clearDateToFilter;
	@FXML Button enterButton;
	@FXML Button menuButton;
	@FXML ChoiceBox<String> searchFilter;
	@FXML DatePicker dateFrom;
	@FXML DatePicker dateTo;
	@FXML Label message;
	@FXML PieChart pieChart;
	@FXML StackPane messagePane;
	@FXML TextField keywords;
	
	/**
	 * Initialize the Summary scene.
	 */
	@FXML
	private void initialize() {
		initializeSearchFilter();
		generateChart();
	}
	
	/**
	 * Generate a PieChart.
	 */
	private void generateChart() {
		// if there is no Categories found, prompt the user to input a Transaction
		if (user.getCategories().isEmpty()) {
			pieChart.setTitle("Please input a new transaction");
			return;
		}
		
		// generate a chart using the User's Categories
		generateChart(categories);
	}
	
	/**
	 * Generate a PieChart with specified Categories.
	 * @param categories the Categories to be shown
	 */
	private void generateChart(Map<String, Category> categories) {
		if (Category.categoriesEqual(categories, this.categories) && !pieChart.getData().isEmpty()) return;
		
		// clear the chart
		pieChart.getData().clear();
		
		double totalSpending = 0;
		
		// get the total spending to calculate percentage of each Category
		for (Category category : categories.values()) {
			totalSpending += category.getSpending();
		}
		
		// add data to the pie chart
		for (Category category : categories.values()) {
			double temp = category.getSpending() / totalSpending;
			String str = category.getName() + ", $" + category.getSpending() + "\n" + toPercentage(temp);
			PieChart.Data data = new PieChart.Data(str, category.getSpending());
			pieChart.getData().add(data);
		}
		
		pieChart.setLegendVisible(false);
		this.categories = categories;
	}
	
	/**
	 * Convert a number between 0 and 1 to percentage.
	 * @param number a number between 0 and 1
	 * @return the String representation of the percentage
	 */
	private String toPercentage(double number) {
		int temp = (int) Math.round(number * 100);
		return String.valueOf(temp) + "%";
	}
	
	/**
	 * Initialize the searchFilter ChoiceBox.
	 */
	private void initializeSearchFilter() {
		searchFilter.getItems().add("Vendor name");
		searchFilter.getItems().add("Vendor location");
	}
	
	/**
	 * Filter the list of Transactions.
	 */
	@FXML
	private void filter() {
		this.transactions = user.getTransactions();
		// filters
		searchBy();
		filterByDate();
		
		// create a HashMap using the filtered Transactions
		HashMap<String, Category> categories = new HashMap<>();
		for (Transaction transaction : transactions) {
			String name = transaction.getCategory();
			
			if (categories.containsKey(name)) {
				Category category = categories.get(name);
				category.addSpending(transaction.getAmount());
				categories.put(name, category);
			} else {
				Category temp = new Category(transaction.getCategory(), transaction.getAmount());
				categories.put(name, temp);
			}
		}
		
		
		if (categories.size() == 0) {
			message("No categories found!");
			return;
		}
		
		// generate the chart
		generateChart(categories);
		message("Filter(s) updated!");
	}
	
	/**
	 * Filter the list of Transactions by Vendor name or Vendor location and keywords.
	 */
	private void searchBy() {
		// get the keywords
		String keywords = this.keywords.getText();
		if (keywords.equals("")) return;
		if (searchFilter.getValue() == null) return;
		
		// get the searching method
		String searchBy = this.searchFilter.getValue();
		
		ArrayList<Transaction> result = new ArrayList<>();
		
		if (searchBy.equals("Vendor name")) {
			// if the Transaction's Vendor name matches, add it to the result list
			for (Transaction transaction : transactions) {
				if (transaction.getVendor().getName().toLowerCase()
						.contains(keywords.toLowerCase())) {
					result.add(transaction);
				}
			}
		} else {
			// if the Transaction's Vendor location matches, add it to the result list
			for (Transaction transaction : user.getTransactions()) {
				if (transaction.getVendor().getLocation().toLowerCase()
						.contains(keywords.toLowerCase())) {
					result.add(transaction);
				}
			}
		}
		
		transactions = result;
	}
	
	/**
	 * Filter the list of Transactions by Date.
	 */
	private void filterByDate() {
		// get the LocalDates
		LocalDate from = dateFrom.getValue();
		LocalDate to = dateTo.getValue();
		if (from == null && to == null) return;
		if (from != null && to != null && from.isAfter(to)) return;
		
		ArrayList<Transaction> result = new ArrayList<>();
		
		// convert to Dates
		Date dFrom = from == null ? null : new Date(from.getMonthValue(), from.getDayOfMonth(), from.getYear());
		Date dTo = to == null ? null : new Date(to.getMonthValue(), to.getDayOfMonth(), to.getYear());
		
		for (Transaction transaction : transactions) {
			// if the Transaction's Date is between the specified Dates, add it to the result list
			boolean isBetween = true;
			
			if (dFrom != null && transaction.getDate().compareTo(dFrom) < 0) {
				isBetween = false;
			}
			if (dTo != null && transaction.getDate().compareTo(dTo) > 0) {
				isBetween = false;
			}
			
			if (isBetween) {
				result.add(transaction);
			}
		}
		
		transactions = result;
	}
	
	/**
	 * Clear the dateFrom DatePicker.
	 */
	@FXML
	private void clearDateFrom() {
		dateFrom.setValue(null);
		filter();
	}
	
	/**
	 * Clear the dateTo DatePicker.
	 */
	@FXML
	private void clearDateTo() {
		dateTo.setValue(null);
		filter();
	}
	
	/**
	 * Display a message.
	 * @param message the messaged to be displayed
	 */
	private void message(String message) {
		// return if the pane is not in it's original position
		if (messagePane.getTranslateY() != 0) return;
		
		this.message.setText(message);
		
		// animation
		Timeline tl = new Timeline(
			new KeyFrame(Duration.seconds(0.3), new KeyValue(messagePane.translateYProperty(), 60)),
			new KeyFrame(Duration.seconds(1))
		);
		tl.setCycleCount(2);
		tl.setAutoReverse(true);
		
		tl.play();
	}
	
	/**
	 * Switch to the Menu scene.
	 * @throws IOException
	 */
	@FXML
	private void switchToMenu() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Menu.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		Stage stage = (Stage) menuButton.getScene().getWindow();
		stage.setScene(scene);
	}
}
