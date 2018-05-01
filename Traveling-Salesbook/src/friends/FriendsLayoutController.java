package friends;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import friends.FriendsLayoutController;
import friends.AddFriendsLayoutController;
import friends.FriendsDAO;
import profile.Profile;

public class FriendsLayoutController implements Initializable {
	
	public static Stage addFriendStage;
	public static Stage showFriendDetails;
	public static int selectedFriend = -1;
	
	@FXML
	private TableView<Profile> table;
	@FXML
	private AnchorPane friendViewMain;
	@FXML
	private TableColumn<Profile, String> fnColumn;
	@FXML
	private TableColumn<Profile, String> lnColumn;
	@FXML
	private TableColumn<Profile, String> unColumn;
	@FXML
	private Label FirstName;
	@FXML
	private Label LastName;
	@FXML
	private Label Birthday;
	@FXML
	private Label Email;
	@FXML
	private Label Street;
	@FXML
	private Label City;
	@FXML
	private Label ZipCode;
	@FXML
	private Label Status;
	@FXML
	private Label Posts;

	
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
		addFriendStage.getIcons().add(new Image("file:icon/add.png"));
		addFriendStage.setResizable(false);
		addFriendStage.setTitle("Add A Friend");
		addFriendStage.show();
	}
	
	@FXML
	private void showDetails(MouseEvent event) throws IOException {
		//TODO: Get the actual text fields to show the appropriate text
		showFriendDetails = new Stage();
		showFriendDetails.initModality(Modality.APPLICATION_MODAL);
		
		AnchorPane friendsDetailsFXML = (AnchorPane)FXMLLoader.load(getClass().getResource("ShowFriendsDetailsLayout.fxml"));
		Scene scene = new Scene(friendsDetailsFXML,400,600);
		showFriendDetails.setScene(scene);
		showFriendDetails.getIcons().add(new Image("file:icon/friends.png"));
		showFriendDetails.setResizable(false);
		showFriendDetails.setTitle("Friend's Profile");
	
		//TODO: Add profile data here to show in details
		//showFriendDetails.set
		showFriendDetails.show();
	}
	
	@FXML
	private void removeFriend(MouseEvent event) throws IOException {
		//TODO: This is always coming up as userID and friendsID are the same. Can't delete a friendRelation with yourself because they don't exist
		System.out.println("Selected friend's ID is: " + table.getSelectionModel().getSelectedItem().userIDProperty().get());
		selectedFriend = table.getSelectionModel().getSelectedItem().getUserID();//looks at selected friend's userID which is the friendsID
		if(selectedFriend != -1) {			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Remove Friend");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to remove this person from your friends list?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){		
				//Delete this friend from friends list in Database
				FriendsDAO.deleteFriend(Main.userID, selectedFriend);
				
				//This is just to check if it has been removed
				/*System.out.println("Your table before: " + table.getItems().toString());
				if(table.getItems().remove(table.getSelectionModel().getSelectedIndex()) != null)
				{
					System.out.println("The user was successfully removed");
					System.out.println("Your table after: " + table.getItems().toString());
				}
				else
				{
					System.out.println("The user was NOT removed");
				}*/
				
				showFriends();
			} else {
			    alert.close();
			}
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No post selected");
			alert.setHeaderText(null);
			alert.setContentText("Please select a post to remove.");
			alert.showAndWait();
		}
	}
	
	@FXML
	private ObservableList<Profile> getFriends() {
		//Establish list for friends and fill the observable from database
		ObservableList<Profile> friends = FriendsDAO.getFriends(Main.userID);//userID need to be changed
		
		return friends;
	}
	
	@FXML
	private void showFriends() {
		//Get friends from database
		ObservableList<Profile> friends = getFriends();

		table.setItems(friends);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	

		fnColumn.setCellValueFactory(new PropertyValueFactory<Profile,String>("firstName"));
		lnColumn.setCellValueFactory(new PropertyValueFactory<Profile,String>("lastName"));
		unColumn.setCellValueFactory(new PropertyValueFactory<Profile,String>("username"));
		
		showFriends();
	}
}
