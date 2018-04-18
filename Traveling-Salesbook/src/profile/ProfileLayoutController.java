package profile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class ProfileLayoutController implements Initializable{
	
	
	@FXML
	private Pane contentArea;
	
	@FXML
	private TextField firstName;
		
	@FXML
	private void setProfile(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("EditProfileLayout.fxml"));
		contentArea.getChildren().removeAll();
		contentArea.getChildren().setAll(fxml);
		contentArea.requestFocus();
	}

	@FXML
	private void listFriends(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../friends/FriendsLayout.fxml"));
		contentArea.getChildren().removeAll();
		contentArea.getChildren().setAll(fxml);
		contentArea.requestFocus();
	}
	
	@FXML
	private void listPosts(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../posts/PostsLayout.fxml"));
		contentArea.getChildren().removeAll();
		contentArea.getChildren().setAll(fxml);
		contentArea.requestFocus();
	}
	



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub	
	}
	
}
