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
import posts.Post;
import posts.PostDAO;
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
		showFriendDetails.show();
	}
	
	@FXML
	private void removeFriend(MouseEvent event) throws IOException {
		//TODO: Figure out why this won't actually remove something from the database
		selectedFriend = table.getSelectionModel().getSelectedItem().getUserID();//looks at selected friend's userID which is the friendsID
		if(selectedFriend != -1) {			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Remove Friend");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to remove this person from your friends list?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				//keep track of freindId for database deletion
				//int friendId = indexIDMap.get(selectedFriend);
				
				//Delete this friend from friends list in Database
				FriendsDAO.deleteFriend(Main.userID, selectedFriend);
				
				//This is just to check if it has been removed
				System.out.println("Your table before: " + table.getItems().toString());
				if(table.getItems().remove(table.getSelectionModel().getSelectedIndex()) != null)
				{
					System.out.println("The user was successfully removed");
					System.out.println("Your table after: " + table.getItems().toString());
				}
				else
				{
					System.out.println("The user was NOT removed");
				}
				
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
		//Establish list for friends
		//ObservableList<Profile> friends = FXCollections.observableArrayList();
		
		//Fill the observable list with all friends in Database
		ObservableList<Profile> friends = FriendsDAO.getFriends(Main.userID);//userID need to be changed
		
		//Fill 
		//for(Profile profile : friendList)
		//{
			//friends.add(profile);
		//}
		return friends;
	}
	
	@FXML
	private void showFriends() {
		//Get friends from database
		ObservableList<Profile> friends = getFriends();
		//table.getItems().clear();
		//int i = 0;
		table.setItems(friends);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		//Add the the table view to the main view anchor
		//This view's anchor is in ProfileLayoutController
		//table = new TableView<Profile>();
		fnColumn.setCellValueFactory(new PropertyValueFactory<Profile,String>("firstName"));
		lnColumn.setCellValueFactory(new PropertyValueFactory<Profile,String>("lastName"));
		unColumn.setCellValueFactory(new PropertyValueFactory<Profile,String>("username"));
		
		//friendViewMain.getChildren().add(table);
		showFriends();
	}
}
