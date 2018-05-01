package friends;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.Main;
import profile.Profile;

public class AddFriendsLayoutController implements Initializable{
	public static int selectedProfile = -1;
	
	@FXML
	TextField firstNameInAddFriends;
	@FXML
	TextField lastNameInAddFriends;
	@FXML
	TextField usernameInAddFriends;
	@FXML
	AnchorPane addFriendsSearchArea;
	@FXML
	TableView <Profile> searchTable;
	@FXML
	TableView <Profile> friendsTable;
	@FXML
	private TableColumn<Profile, String> fUsername;//fUsername=friend's username
	@FXML
	private TableColumn<Profile, String> fFirst;//fFirst=friend's First name
	@FXML
	private TableColumn<Profile, String> fLast;//fLast=friend's last
	/*@FXML
	private TableColumn<Profile, String> pUsername;//pUsername=profile username
	@FXML
	private TableColumn<Profile, String> pFirst;//pFirst=profile first name
	@FXML
	private TableColumn<Profile, String> pLast;*///pLast=profile last
	@FXML
	Button addButton;

	final BooleanProperty firstTime = new SimpleBooleanProperty(true); 
	
	
	@FXML
	private void clearSearchFields() {
		firstNameInAddFriends.clear();
		lastNameInAddFriends.clear();
		usernameInAddFriends.clear();
	}
	
	@FXML 
	private void searchForFriends() {
		/*
		 * We are searching for people with the user input. This needs to show on the searchTable. This should EXCLUDE profiles you are already friends with. 
		 * The second table should show the current friends list.
		 * Ideally when you add a profile from the searchTable it will disappear from that list but then show up on the friendsTable.
		 * This means the addFriendsToTable should handle the propagation of the friendsTable
		 * Theoretically another function should be make a call to FriendsDAO.addFriend(searchProfileToBeFriend so they can establish id, user's ID)
		 * That will establish the friend relation and therefore show up in the friendsTable here
		 */
		
		//TODO: Need to make the display look like 2 separate tables. May need to adjust the sizes of them in AddFriendsLayout
		//Get profile matches from database then fill the searchTable with results
		//usernameInAddFriends.textProperty().get();
		ObservableList<Profile> searchProfiles = FriendsDAO.searchPeople(usernameInAddFriends.getText(), firstNameInAddFriends.getText(), lastNameInAddFriends.getText());
		if(!searchProfiles.isEmpty() && searchTable != null) {
			//TODO: Make searchTable a TableView in the fxml file so this table can properly show the profiles
			//searchTable.setItems(searchProfiles);	
		}
		
	}
	
	@FXML
	private void addFriendsToList() {
		ObservableList<Profile> friendsProfiles = FriendsDAO.getFriends(Main.userID);
		if(!friendsProfiles.isEmpty() && friendsTable != null) {
			friendsTable.setItems(friendsProfiles);
		}
	}
	
	@FXML
	private void addFriendRelation() {
		selectedProfile = searchTable.getSelectionModel().getSelectedIndex();
		if(selectedProfile != -1) {			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Add Friend");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to add this person to your friends list?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				//keep track of freindId for database deletion
				//int friendId = indexIDMap.get(selectedFriend);
				
				//Add this friend from searchList to friendsRelation database table
				FriendsDAO.addFriend(searchTable.getSelectionModel().getSelectedItem(), Main.userID);
				
				//This is just to check if it has been added
				//System.out.println("Your table before: " + searchTable.getItems().toString());
				
				
				searchForFriends();
			} else {
			    alert.close();
			}
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No profile selected");
			alert.setHeaderText(null);
			alert.setContentText("Please select a profile to add.");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void cancel() {
		clearSearchFields();
		//clear table view
		FriendsLayoutController.addFriendStage.hide();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		addFriendsToList();
		//not focus on the username textField on start
		//addFriendsSearchArea.requestFocus();
		usernameInAddFriends.textProperty().addListener((observable,  oldValue,  newValue) -> {
            if(!newValue.isEmpty() && firstTime.get()){
            	usernameInAddFriends.requestFocus(); // Delegate the focus to container
                //firstTime.setValue(false); // Variable value changed for future references
                
                searchForFriends();
                
            }
        });
		
		firstNameInAddFriends.textProperty().addListener((observable,  oldValue,  newValue) -> {
            if(!newValue.isEmpty() && firstTime.get()){
            	firstNameInAddFriends.requestFocus(); // Delegate the focus to container
                //firstTime.setValue(false); // Variable value changed for future references
                searchForFriends();
                
            }
        });
		
		lastNameInAddFriends.textProperty().addListener((observable,  oldValue,  newValue) -> {
            if(!newValue.isEmpty() && firstTime.get()){
            	lastNameInAddFriends.requestFocus(); // Delegate the focus to container
                //firstTime.setValue(false); // Variable value changed for future references
                searchForFriends();
                
            }
        });
		
		//When add button is clicked, add current selected profile
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addFriendRelation();
			}
		});		
		
		//Friends Table
		fUsername.setCellValueFactory(new PropertyValueFactory<Profile,String>("username"));
		fFirst.setCellValueFactory(new PropertyValueFactory<Profile,String>("lastName"));
		fLast.setCellValueFactory(new PropertyValueFactory<Profile,String>("firstName"));
		
		//Search Profile Table
		//pUsername.setCellValueFactory(new PropertyValueFactory<Profile,String>("username"));
		//pFirst.setCellValueFactory(new PropertyValueFactory<Profile,String>("lastName"));
		//pLast.setCellValueFactory(new PropertyValueFactory<Profile,String>("firstName"));
		
	}
	
}
