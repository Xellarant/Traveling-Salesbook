package profile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

public class EditProfileController implements Initializable{
	
	public static Stage changePasswordStage;
	
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
		changePasswordStage = new Stage();
		changePasswordStage.initModality(Modality.APPLICATION_MODAL);
		
		AnchorPane changePasswordFXML = (AnchorPane)FXMLLoader.load(getClass().getResource("ChangePasswordLayout.fxml"));
		Scene scene = new Scene(changePasswordFXML,250,240);
		changePasswordStage.setScene(scene);
		changePasswordStage.getIcons().add(new Image("file:icon/password.png"));
		changePasswordStage.setResizable(false);
		changePasswordStage.setTitle("Change Password");
		changePasswordStage.show();
	}

}
