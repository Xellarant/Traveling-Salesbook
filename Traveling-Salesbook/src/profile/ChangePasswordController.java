package profile;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

public class ChangePasswordController implements Initializable{
	
	@FXML
	AnchorPane changePasswordMain;
	@FXML
	PasswordField currentPassword;
	
	final BooleanProperty firstTime = new SimpleBooleanProperty(true); 
	
	@FXML
	private void changePassword() {
		//alert
		EditProfileController.changePasswordStage.hide();
		
	}
	
	@FXML
	private void cancel() {
		EditProfileController.changePasswordStage.hide();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//not focus on the username textField on start
		currentPassword.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
            	changePasswordMain.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });	
	}
}
