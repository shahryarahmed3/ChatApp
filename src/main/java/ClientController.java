import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    Client clientConnection;
    Server server;


    @FXML
    String clientNumber;

    @FXML
    TextField c1;
    @FXML
    TextField c2;
    @FXML
    ListView<String> clientList;
    @FXML
    ListView<String> listItems2;


//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        clientConnection = new Client(data -> {
//            Platform.runLater(() -> {
//                listItems2.getItems().add(data.toString());
//            });
//        });
//
//        clientConnection.start();
//
//    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientConnection = new Client(
                data -> {
                    Platform.runLater(() -> {
                        listItems2.getItems().add(data.toString());
                    });
                },
                clientNames -> {
                    Platform.runLater(() -> {
                        clientList.getItems().clear();
                        clientList.getItems().addAll(clientNames);
                    });
                });



        clientConnection.start();
    }

    public void b2Method(ActionEvent event) throws IOException {
        clientConnection.send(c1.getText());
        c1.clear();
    }

    public void b3Method(ActionEvent event) throws IOException {
        int num = Integer.parseInt(c2.getText());
        clientConnection.sendToIndividual(c1.getText(), num);
        c1.clear();
    }




}

