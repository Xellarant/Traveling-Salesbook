package friends;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddFriendsLayoutController implements Initializable{

	@FXML
	TextField firstNameInAddFriends;
	@FXML
	TextField lastNameInAddFriends;
	@FXML
	TextField usernameInAddFriends;
	@FXML
	AnchorPane addFriendsSearchArea;

	final BooleanProperty firstTime = new SimpleBooleanProperty(true); 
	
	
	@FXML
	private void clearSearchFields() {
		firstNameInAddFriends.clear();
		lastNameInAddFriends.clear();
		usernameInAddFriends.clear();
	}
	
	@FXML 
	private void searchForFriends() {
		
	}
	
	@FXML
	private void addFriendsToList() {
		
	}
	
	@FXML
	private void cancel() {
		clearSearchFields();
		//clear table view
		FriendsLayoutController.addFriendStage.hide();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//not focus on the username textField on start
		usernameInAddFriends.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
            	addFriendsSearchArea.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
		
	}
	
}
