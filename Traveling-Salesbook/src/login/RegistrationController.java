package login;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import util.DBUtil;

public class RegistrationController implements Initializable{

	private Stage failedRegistrationStage = new Stage();
	@FXML
	private TextField usernameField, firstNameField, lastNameField, DOBField, emailField, answerField;
	@FXML
	private PasswordField passField, confirmPassField;
	@FXML
	private ComboBox<String> security;
	private int userID;
	
	@FXML
	private void openLogin(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Main.stage.getScene().setRoot(root);
	}
	
	@FXML
	private void register() throws IOException, SQLException {

		if (!passField.getText().equals(confirmPassField.getText())) { // if pass does not match confirmation
			// passwords did not match, popup an alert window
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Password Mismatch");
			alert.setHeaderText(null);
			alert.setContentText("Your passwords did not match! Please correct this and try again.");
			alert.showAndWait();
			return;
		}

		// Create and execute sql statement to check credentials.
		String sqlStatement = String.format("exec RegisterNewUser '%s','%s','%s','%s','%s','%s','%s','%s';",
				usernameField.getText(), passField.getText(), firstNameField.getText(), lastNameField.getText(),
				emailField.getText(), DOBField.getText(), security.getValue(), answerField.getText());
		ResultSet rs = DBUtil.dbExecuteQuery(sqlStatement);

		if (rs.isBeforeFirst()) { // if the result set was not empty...
			rs.next();	// grab first row of set.
			if (rs.getString("Response").contains("Succ")) {
				userID = rs.getInt("UserID");

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Registration Succeeded!");
				alert.setHeaderText(null);
				alert.setContentText("You've successfully been registered in the Salesbook.");
				alert.showAndWait();
			}
			else if (rs.getString("Response").contains("Err")) {
				System.err.println("Proc returned duplication error in response! Registration failed.");

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Registration Failed");
				alert.setHeaderText(null);
				alert.setContentText("Uh oh! It seems we already have that user in our database.");
				alert.showAndWait();
				return;
			}
			else {
				System.err.println("Unkown error read from proc response! ¯\\_(ツ)_/¯");
			}
		}
		else { // result set was empty! What happened???
			System.err.println("There was a problem registering! (no result returned from proc)");
		}

		Parent fxml = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
		Main.stage.getScene().setRoot(fxml);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		security.getItems().setAll("What was the name of your first pet?", "In what city were you born?");

		answerField.setOnAction(event -> {
			try {
				register();
			}
			catch (IOException | SQLException ex) {
				System.err.println("Something went wrong setting the Action event for the answer field!");
				ex.printStackTrace();
			}
		});

	}
}
