package com.example.momuney.controllers;

import java.io.IOException;
import java.time.LocalDate;

import com.example.momuney.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import com.example.momuney.models.Vendor;

public class InputController {
	
	// Connection to the database.
	private DbConnection connection = DbConnection.getInstance();
	// The User object.
	private User user = connection.getUser();
	
	@FXML Button confirmButton;
	@FXML Button enterButton;
	@FXML Button menuButton;
	@FXML ChoiceBox<String> category;
	@FXML DatePicker date;
	@FXML Label message;
	@FXML StackPane messagePane;
	@FXML TextField amount;
	@FXML TextField newType;
	@FXML TextField vendorName;
	@FXML TextField vendorLocation;
	
	/**
	 * Initialize the InputTransaction scene.
	 */
	@FXML
	private void initialize() {
		refreshCategories();
	}
	
	/**
	 * Create a new Transaction.
	 */
	@FXML
	private void createTransaction() {
		String vendorName = this.vendorName.getText();
		String vendorLocation = this.vendorLocation.getText();
		if (vendorName.equals("") && vendorLocation.equals("")) {
			message("Please enter a name or location.");
			return;
		}
		Vendor vendor = new Vendor(vendorName, vendorLocation);
		
		// check if the amount field is empty and get the amount
		String amountText = this.amount.getText();
		if (amountText == "") {
			message("Please enter an amount.");
			return;
		}
		if (!isNumeric(amountText)) {
			message("The amount is not numeric.");
			return;
		}
		double amount = Double.parseDouble(amountText);
		amount = (double) Math.round(100 * amount) / 100; // round to 2 decimal places
		
		// check if the Date field is empty and get the Date
		LocalDate lDate = date.getValue();
		if (lDate == null) {
			message("Please select a date.");
			return;
		}
		int month = lDate.getMonthValue();
		int day = lDate.getDayOfMonth();
		int year = lDate.getYear();
		Date date = new Date(month, day, year);
		
		// check if the Category field is empty and get the Category
		String category = this.category.getValue();
		if (category == null) {
			message("Please select a category.");
			return;
		}
		
		// create a new Transaction
		Transaction transaction = new Transaction(vendor, category, date, amount);
		
		// update the User object and database
		user.addTransaction(transaction);
		user.addToCategory(category, amount);
		connection.updateUserData(user);
		
		// clear fields
		clearAllFields();
		
		message("Transaction created!");
	}
	
	/**
	 * Check if a text is numeric.
	 * @param text the text to be checked
	 * @return true if the text is numeric
	 */
	private boolean isNumeric(String text) {
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Create a new Category.
	 */
	@FXML
	private void createCategory() {
		String categoryName = newType.getText();
		if (categoryName.equals("")) {
			message("Please enter a category name.");
			return;
		}
		
		// create a new Category if it does not exist
		if (!user.createCategory(categoryName)) {
			message("Category already exists.");
			return;
		}
		
		// update the database
		connection.updateUserData(user);
		
		// refresh the Category ChoiceBox
		refreshCategories();
		newType.clear();
		message("Category created!");
	}
	
	/**
	 * Refresh the Category ChoiceBox.
	 */
	private void refreshCategories() {
		// refresh choices for the Category ChoiceBox
		this.category.getItems().clear();
		
		// add Categories to the ChoiceBox
		for (Category category : user.getCategories().values()) {
			this.category.getItems().add(category.getName());
		}
	}
	
	/**
	 * Clear all fields in the scene.
	 */
	private void clearAllFields() {
		vendorName.clear();
		vendorLocation.clear();
		amount.clear();
		date.setValue(null);
		category.setValue(null);
		newType.clear();
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
