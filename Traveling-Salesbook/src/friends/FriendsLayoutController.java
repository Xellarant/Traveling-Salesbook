package friends;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

public class FriendsLayoutController {
	
	public static Stage addFriendStage;
	public static Stage showFriendDetails;
	
	@FXML
	private void cancelListing(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
		Main.stage.getScene().setRoot(fxml);
	}
	
	@FXML
	private void addFriend(MouseEvent event) throws IOException {
		addFriendStage = new Stage();
		addFriendStage.initModality(Modality.APPLICATION_MODAL);
		
		AnchorPane addFriendFXML = (AnchorPane)FXMLLoader.load(getClass().getResource("AddFriendsLayout.fxml"));
		Scene scene = new Scene(addFriendFXML,500,350);
		addFriendStage.setScene(scene);
		addFriendStage.getIcons().add(new Image("file:icons/add.png"));
		addFriendStage.setResizable(false);
		addFriendStage.setTitle("Add A Friend");
		addFriendStage.show();
	}
	
	@FXML
	private void showDetails(MouseEvent event) throws IOException {
		showFriendDetails = new Stage();
		showFriendDetails.initModality(Modality.APPLICATION_MODAL);
		
		AnchorPane friendsDetailsFXML = (AnchorPane)FXMLLoader.load(getClass().getResource("ShowFriendsDetailsLayout.fxml"));
		Scene scene = new Scene(friendsDetailsFXML,400,600);
		showFriendDetails.setScene(scene);
		showFriendDetails.getIcons().add(new Image("file:icons/friends.png"));
		showFriendDetails.setResizable(false);
		showFriendDetails.setTitle("Friend's Profile");
		showFriendDetails.show();
	}
	
	@FXML
	private void removeFriend(MouseEvent event) throws IOException {
		
	}
	
}
