package login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ForgetPasswordController implements Initializable{
	@FXML
	AnchorPane forgetPasswordMain;
	@FXML
	TextField securityAnswer;
	
	final BooleanProperty firstTime = new SimpleBooleanProperty(true); 
	
	@FXML
	public void confirmNewPassword() {
		//check security question
		//add alert
		LoginController.forgetPasswordStage.hide();
	}
	
	@FXML
	public void cancel() {
		LoginController.forgetPasswordStage.hide();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//not focus on the username textField on start
		securityAnswer.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
            	forgetPasswordMain.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });	
	}
}
