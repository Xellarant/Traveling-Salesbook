package login;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;

public class RegistrationController implements Initializable{

	//private Stage failedRegistrationStage = new Stage();
	
	@FXML
	private ComboBox<String> security;
	
	@FXML
	private void openLogin(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Main.stage.getScene().setRoot(root);
	}
	
	@FXML
	private void register(MouseEvent event) throws IOException  {
//		try {
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			Connection conn = DriverManager.getConnection("jdbc:sqlserver://traveling-salesbook.chfgpq4clmvj.us-west-1.rds.amazonaws.com;user=travelingsalesman;password=namselasgnilevart;database=salesbook");
//			Statement sta = conn.createStatement();
//
//			System.out.println("Attempting sql string: " + Sql);
//			ResultSet rs = sta.executeQuery(Sql);
//			if (rs.isBeforeFirst()) { // can only be before First if there is at least 1 row in result set.
//				System.out.println("Your query returned a match!");
//
//				// log success!
//				Parent fxml = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
//				Main.stage.getScene().setRoot(fxml);
//			}
//			else { // empty result set.
//				System.err.println("Couldn't find a match for your login info!");
//
//				AnchorPane failedLoginFXML = (AnchorPane)FXMLLoader.load(getClass().getResource("failedLogin.fxml"));
//				Scene scene = new Scene(failedLoginFXML);
//				failedLoginStage.setScene(scene);
//				failedLoginStage.setResizable(false);
//				failedLoginStage.setTitle("Login failed!");
//				failedLoginStage.show();
//
//				// alternate way to make a popup:
////				Alert alert = new Alert(Alert.AlertType.ERROR);
////				alert.setTitle("Login Failed");
////				alert.setHeaderText(null);
////				alert.setContentText("Sorry, but something looks wrong with that sign in information.");
////
////				alert.showAndWait();
//			}
//		}
//		catch (SQLException ex) {
//			System.err.println("Uh oh! Looks like there was a problem setting up your SQL connection!");
//			ex.printStackTrace();
//		}
//		catch (ClassNotFoundException ex) {
//			System.err.println("Uh oh! Looks like there was a problem setting up your JDBC driver!");
//			ex.printStackTrace();
//		}
		Parent fxml = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
		Main.stage.getScene().setRoot(fxml);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		security.getItems().setAll("What was the name of your first pet?", "In what city were you born?");
	}
}
