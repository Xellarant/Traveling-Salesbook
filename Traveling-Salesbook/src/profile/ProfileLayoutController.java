package profile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;


import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.Main;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.Main;



public class ProfileLayoutController implements Initializable{

	@FXML
	private Pane contentArea;
	
	@FXML
	private Label username;	

	@FXML
	private Label firstName;

	@FXML
	private Label lastName;

	@FXML
	private Label birthday;

	@FXML
	private Label email;

	@FXML
	private Label phone;

	@FXML
	private Label occupation;

	@FXML
	private Label school;

	@FXML
	private Label status;
		
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
	
	//display user profile 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Profile profile = ProfileDAO.searchProfile(String.valueOf(Main.userID));
		username.setText(profile.getUsername());
		firstName.setText(profile.getFirstName());
		lastName.setText(profile.getLastName());
		email.setText(profile.getEmail());
		birthday.setText(profile.getBirthday());
		phone.setText(profile.getPhoneNumber());
		occupation.setText(profile.getOccupation());
		school.setText(profile.getSchool());
		status.setText(profile.getStatus());
	}
	
}
