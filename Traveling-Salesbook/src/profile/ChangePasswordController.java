package profile;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import main.Main;
import util.DBUtil;
import static util.DataUtil.bytesToHex;

public class ChangePasswordController implements Initializable{
	
	@FXML
	AnchorPane changePasswordMain;
	@FXML
	PasswordField currentPassword;
	@FXML
	PasswordField newPassword;
	@FXML
	PasswordField confirmNewPassword;
	
	final BooleanProperty firstTime = new SimpleBooleanProperty(true); 
	
	@FXML
	private void changePassword() {
			String hashedPass = new String();
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] encodedhash = digest.digest(
						currentPassword.getText().getBytes(StandardCharsets.UTF_8));
				hashedPass = bytesToHex(encodedhash);
				System.out.println("hashed pass: " + hashedPass);
			}
			catch (NoSuchAlgorithmException ex) {
				System.err.println("Error! Algorithm for hash undefined!");
			}

			// Create and execute sql statement to check credentials and update password.
			String Sql = "SELECT * FROM users WHERE userID = '" + Main.userID + "' AND password = '" + hashedPass +"'";
			try {
				ResultSet rs = DBUtil.dbExecuteQuery(Sql);		
				//if rs has next, then there is a match, process login and store the userID of current user to Main.userID
				if (rs.next()) {
					if(newPassword.getText().equals(confirmNewPassword.getText())) {
						MessageDigest digest;
						try {
							digest = MessageDigest.getInstance("SHA-256");						
							byte[] encodedhash = digest.digest(newPassword.getText().getBytes(StandardCharsets.UTF_8));
							hashedPass = bytesToHex(encodedhash);
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						}

						String updatePassword = "UPDATE users \n"
								+ "SET password = '" + hashedPass 
								+ "'\n WHERE userID = '" + Main.userID + "';";
						DBUtil.dbExecuteUpdate(updatePassword);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Password change confirmation");
						alert.setHeaderText(null);
						alert.setContentText("Your password was successfully changed!");
						alert.showAndWait();
						EditProfileController.changePasswordStage.hide();
					}
					
				}
				else { // empty result set.
					System.err.println("Couldn't find a match for your password!");
					// password not match, popup an alert window
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Login Failed");
					alert.setHeaderText(null);
					alert.setContentText("Sorry, but something looks wrong your input password.");
					alert.showAndWait();
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}

	
	@FXML
	private void cancel() {
		EditProfileController.changePasswordStage.hide();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//not focus on the Current Password textField on start
		currentPassword.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
            	changePasswordMain.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });	
	}
}
