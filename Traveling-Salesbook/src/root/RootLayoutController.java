package root;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.Main;

public class RootLayoutController implements Initializable{
	@FXML
	BorderPane rootLayout;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
			showProfile();
	}
	
	//load profile layout
	public void showProfile(){
        AnchorPane profileLayout;
		try {
			profileLayout = (AnchorPane) FXMLLoader.load(getClass().getResource("../profile/ProfileLayout.fxml"));
			rootLayout.setCenter(profileLayout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleExit() {
		System.exit(0);
	}
	
	public void listFriends() {
		
	}

	public void listPosts() {
		
	}
	
	public void signout() throws IOException {
		Main.userID = 0;
		Parent root = FXMLLoader.load(getClass().getResource("../login/Login.fxml"));
		Main.stage.getScene().setRoot(root);
	}
	
	public void about() {
		
	}
}
