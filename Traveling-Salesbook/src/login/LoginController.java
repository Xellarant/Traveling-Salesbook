package login;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import util.DBUtil;

import static util.DataUtil.bytesToHex;

public class LoginController implements Initializable{
	public static Stage forgetPasswordStage;
	public static Stage failedLoginStage = new Stage();
	public PasswordField pwBox;
	public String hashedPass = new String();
	public Button btnLogin;
	@FXML
	private Pane content_area;
	@FXML
	private TextField username;

	final BooleanProperty firstTime = new SimpleBooleanProperty(true); 

	@FXML
	private void openReg(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("Registration.fxml"));
		content_area.getChildren().removeAll();
		content_area.getChildren().setAll(fxml);
	}

	@FXML
	private void passEntered() {
		btnLogin.fire();
	}
	
	@FXML
	private void login() throws IOException {

		// grab the hashed password.
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(
					pwBox.getText().getBytes(StandardCharsets.UTF_8));
			hashedPass = bytesToHex(encodedhash);
			System.out.println("hashed pass: " + hashedPass);
		}
		catch (NoSuchAlgorithmException ex) {
			System.err.println("Error! Algorithm for hash undefined!");
		}

		// Create and execute sql statement to check credentials.
		String Sql = "SELECT * FROM users WHERE (email = '" + username.getText()
				+ "' OR username = '" + username.getText() + "') AND password = '" + hashedPass +"'";
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(Sql);		
			//if rs has next, then there is a match, process login and store the userID of current user to Main.userID
			if (rs.next()) {
				//get userID
				Main.userID = rs.getInt("userID");
				System.out.println("Your query returned a match!");
				// log success!
				Parent fxml = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
				Main.stage.getScene().setRoot(fxml);
				
			}
			else { // empty result set.
				System.err.println("Couldn't find a match for your login info!");
				// username or password not match, popup an alert window
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Login Failed");
				alert.setHeaderText(null);
				alert.setContentText("Sorry, but something looks wrong with that sign in information.");
				alert.showAndWait();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void forgetPassword() throws IOException{
		forgetPasswordStage = new Stage();
		forgetPasswordStage.initModality(Modality.APPLICATION_MODAL);
		
		AnchorPane changePasswordFXML = (AnchorPane)FXMLLoader.load(getClass().getResource("forgetPasswordLayout.fxml"));
		Scene scene = new Scene(changePasswordFXML,250,270);

		forgetPasswordStage.setScene(scene);
		forgetPasswordStage.getIcons().add(new Image("file:icons/password.png"));
		forgetPasswordStage.setResizable(false);
		forgetPasswordStage.setTitle("Forget Password");
		forgetPasswordStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//not focus on the username textField on start
        username.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                content_area.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					login();
				}
				catch (IOException ex) {
					System.err.println("We encountered an error parsing input/output. This is a serious problem... Please contact your nearest salesman.");
				}
			}
		});

	}

}
