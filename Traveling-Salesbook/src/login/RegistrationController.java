package login;

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

public class RegistrationController implements Initializable{
	
	@FXML
	private ComboBox<String> security;
	
	@FXML
	private void openLogin(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Main.stage.getScene().setRoot(root);
	}
	
	@FXML
	private void register(MouseEvent event) throws IOException  {
		Parent fxml = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
		Main.stage.getScene().setRoot(fxml);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		security.getItems().setAll("What was the name of your first pet?", "In what city were you born?");
	}
}
