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
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

public class LoginController implements Initializable{
	public static Stage forgetPasswordStage;
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
		Parent fxml = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
		Main.stage.getScene().setRoot(fxml);
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
