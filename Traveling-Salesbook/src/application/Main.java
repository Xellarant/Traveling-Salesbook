package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


public class Main extends Application {

	String hashedPass;

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Traveling Salesbook");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label email = new Label("Email:");
		grid.add(email, 0, 1);

		TextField emailTextField = new TextField();
		grid.add(emailTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				// grab the hashed password.
				try {
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					byte[] encodedhash = digest.digest(
							pwBox.getText().getBytes(StandardCharsets.UTF_8));
					hashedPass = bytesToHex(encodedhash);
					System.out.println("hashed pass: " + hashedPass);
				}
				catch (NoSuchAlgorithmException ex) {
					System.err.println("Error! Algorithm for hash undefined!");
				}

				// Create and execute sql statement to check credentials.
				String Sql = "select COUNT(*) from salesbook.dbo.users WHERE email = '" + emailTextField.getText()
						+ "' AND password = '" + hashedPass +"'";

				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					Connection conn = DriverManager.getConnection("jdbc:sqlserver://traveling-salesbook.chfgpq4clmvj.us-west-1.rds.amazonaws.com;user=travelingsalesman;password=namselasgnilevart;database=salesbook");
					Statement sta = conn.createStatement();

					System.out.println("Attempting sql string: " + Sql);
					ResultSet rs = sta.executeQuery(Sql);
					if (rs.isBeforeFirst()) {
						System.out.println("Your query returned a match!");
					}
					else {
						System.err.println("Something went wrong with the sql statement!");

					}
				}
				catch (SQLException ex) {
					System.err.println("Uh oh! Looks like there was a problem setting up your SQL connection!");
					ex.printStackTrace();
				}
				catch (ClassNotFoundException ex) {
					System.err.println("Uh oh! Looks like there was a problem setting up your JDBC driver!");
					ex.printStackTrace();
				}

			}
		});

		Scene scene = new Scene(grid, 300, 275);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					btn.fire();
				}
			}
		});

		primaryStage.setScene(scene);

		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	private static String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
