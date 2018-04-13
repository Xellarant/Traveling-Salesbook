package profile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import main.Main;

public class EditProfileController implements Initializable{
	@FXML
	private ComboBox<String> onlineStatus;
	
	//initialize online status selection
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		onlineStatus.getItems().setAll("Online", "Away", "Busy", "Offline");		
	}
	
	//cancel editing profile, back to welcome page
	@FXML
	private void cancelEditing(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
		Main.stage.getScene().setRoot(root);
	}
	
	//update profile
	@FXML
	private void processlEditing(MouseEvent event) throws IOException {
		//add an alert, show profile update successfully
		//store new data to database
		Parent root = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
		Main.stage.getScene().setRoot(root);
	}
	
	//change password
	@FXML
	private void changePassword(MouseEvent event) throws IOException {
		
	}

}
