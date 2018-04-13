package login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import main.Main;

public class LoginController implements Initializable{
	@FXML
	private Pane content_area;
	@FXML
	private TextField username;
//	@FXML
//	private AnchorPane signinView;
	
	final BooleanProperty firstTime = new SimpleBooleanProperty(true); 

	@FXML
	private void openReg(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("Registration.fxml"));
		content_area.getChildren().removeAll();
		content_area.getChildren().setAll(fxml);
	}
	
	@FXML
	private void login(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
		Main.stage.getScene().setRoot(fxml);
	}
	
	@FXML
	private void forgetPassword() {
		
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
