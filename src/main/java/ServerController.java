import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable{

    Server serverConnection;
    @FXML
    ListView<String> listItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serverConnection = new Server(data -> {
            Platform.runLater(() -> {
                listItems.getItems().add(data.toString());
            });
        });
    }
}
