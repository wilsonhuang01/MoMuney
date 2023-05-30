package com.example.momuney.controllers;

import java.io.IOException;

import com.example.momuney.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {
	
	@FXML Button inputButton;
	@FXML Button pastButton;
	@FXML Button summaryButton;
	
	/**
	 * Switch to the InputTransaction scene.
	 * @throws IOException
	 */
	@FXML
	private void switchToInput() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Input.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = (Stage) inputButton.getScene().getWindow();
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Switch to the PastTransactions scene.
	 * @throws IOException
	 */
	@FXML
	private void switchToPast() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Past.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = (Stage) pastButton.getScene().getWindow();
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Switch to the Summary scene.
	 * @throws IOException
	 */
	@FXML
	private void switchToSummary() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Summary.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = (Stage) summaryButton.getScene().getWindow();
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
