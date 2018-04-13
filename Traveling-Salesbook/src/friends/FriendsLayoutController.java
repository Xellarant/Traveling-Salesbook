package friends;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import main.Main;

public class FriendsLayoutController {
	
	@FXML
	private void cancelListing(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
		Main.stage.getScene().setRoot(fxml);
	}
	
	@FXML
	private void addFriend(MouseEvent event) throws IOException {
		
	}
	
	@FXML
	private void showFriendPosts(MouseEvent event) throws IOException {
		
	}
	
	@FXML
	private void removeFriend(MouseEvent event) throws IOException {
		
	}
	
	@FXML
	private void viewFriendDetail(MouseEvent event) throws IOException {
		
	}
}
