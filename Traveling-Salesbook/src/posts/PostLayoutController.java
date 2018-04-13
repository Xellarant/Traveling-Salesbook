package posts;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import main.Main;

public class PostLayoutController {
	@FXML
	private void cancelListing(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../root/RootLayout.fxml"));
		Main.stage.getScene().setRoot(fxml);
	}
	
	@FXML
	private void addPost(MouseEvent event) throws IOException {
		
	}
	
	@FXML
	private void editPost(MouseEvent event) throws IOException {
		
	}
	
	@FXML
	private void removePost(MouseEvent event) throws IOException {
		
	}
}
