package login;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

import static main.Main.bytesToHex;

public class LoginController implements Initializable{
	public static Stage forgetPasswordStage;
	public PasswordField pwBox;
	public String hashedPass = new String();
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
	private void login(MouseEvent event) throws IOException {

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
		String Sql = "select * from salesbook.dbo.users WHERE (email = '" + username.getText()
				+ "' OR username = '" + username.getText() + "') AND password = '" + hashedPass +"'";

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://traveling-salesbook.chfgpq4clmvj.us-west-1.rds.amazonaws.com;user=travelingsalesman;password=namselasgnilevart;database=salesbook");
			Statement sta = conn.createStatement();

			System.out.println("Attempting sql string: " + Sql);
			ResultSet rs = sta.executeQuery(Sql);
			if (rs.isBeforeFirst()) {
				System.out.println("Your query returned a match!");

				// log success!
				Parent fxml = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
				Main.stage.getScene().setRoot(fxml);
			}
			else {
				System.err.println("Couldn't find a match for your login info!");

			}
		}
		catch (SQLException ex) {
			System.err.println("Uh oh! Looks like there was a problem setting up your SQL connection!");
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex) {
			System.err.println("Uh oh! Looks like there was a problem setting up your JDBC driver!");
			ex.printStackTrace();
		}
	}
	
	@FXML
	private void forgetPassword() throws IOException{
		forgetPasswordStage = new Stage();
		forgetPasswordStage.initModality(Modality.APPLICATION_MODAL);
		
		AnchorPane changePasswordFXML = (AnchorPane)FXMLLoader.load(getClass().getResource("forgetPasswordLayout.fxml"));
		Scene scene = new Scene(changePasswordFXML,250,270);
		forgetPasswordStage.setScene(scene);
		forgetPasswordStage.getIcons().add(new Image("file:icon/password.png"));
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
		
	}
}
