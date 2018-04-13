package posts;

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

public class PostLayoutController {
	public static Stage addPostStage;
	
	@FXML
	private void cancelListing(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
		Main.stage.getScene().setRoot(fxml);
	}
	
	@FXML
	private void addPost(MouseEvent event) throws IOException {
		addPostStage = new Stage();
		addPostStage.initModality(Modality.APPLICATION_MODAL);
		
		AnchorPane postFXML = (AnchorPane)FXMLLoader.load(getClass().getResource("AddPostLayout.fxml"));
		Scene scene = new Scene(postFXML,500,350);
//		scene.getStylesheets().add(getClass().getResource("../login/stylesheet.css").toExternalForm());
		addPostStage.setScene(scene);
		addPostStage.getIcons().add(new Image("file:icon/add.png"));
		addPostStage.setResizable(false);
		addPostStage.setTitle("Add A Post");
		addPostStage.show();
		
	}
	
	@FXML
	private void editPost(MouseEvent event) throws IOException {
		
	}
	
	@FXML
	private void removePost(MouseEvent event) throws IOException {
		
	}
	
	
}
