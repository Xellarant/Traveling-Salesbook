package posts;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AddPostLayoutController {
	
	@FXML
	private TextArea postContent;
	
	@FXML
	private void clear() {
		postContent.clear();
	}
	
	@FXML
	private void post() {
		
		postContent.clear();
		PostLayoutController.addPostStage.hide();	
	}
	
	@FXML
	private void cancel() {
		postContent.clear();
		PostLayoutController.addPostStage.hide();
	}
}
