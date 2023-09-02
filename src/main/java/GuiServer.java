import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class GuiServer extends Application {

	TextField s1, s2, s3, s4, c1;
	Button serverChoice, clientChoice;
	@FXML
	Button b2 = new Button();
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	Client clientConnection;

	ListView<String> listItems, listItems2;
	@FXML
	BorderPane root;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {

			Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

			primaryStage.setTitle("My Application");
			Scene s1 = new Scene(root, 1000,800);

			primaryStage.setScene(s1);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}


	}
	public Scene createServerGui() {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: coral");

		pane.setCenter(listItems);

		return new Scene(pane, 500, 400);


	}

	public void startServer(javafx.event.ActionEvent e) throws IOException{


		FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerScene.fxml"));
		Parent root2 = loader.load();
		root.getScene().setRoot(root2);

	}

	public void startClient(javafx.event.ActionEvent e) throws IOException{


		FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatScene.fxml"));
		Parent root2 = loader.load();
		root.getScene().setRoot(root2);

	}

}



