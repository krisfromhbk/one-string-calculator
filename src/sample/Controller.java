package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Controller {

    @FXML
    private TextField paramName;

    @FXML
    private TextField expression;

    @FXML
    private Button exitButton;

    @FXML
    public void onOpenClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Opening .json File");
        File file = fileChooser.showOpenDialog(Main.getStage());
        try {
            Main.setFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onChangeClicked() throws Exception {
        if ((paramName.getText() != null) & (expression.getText() != null)) {
            Main.setParam(paramName.getText());
            Main.setExpression(expression.getText());
            Main.parseFuckingJson();
        } else {
            //space for error window
        }
    }

    @FXML
    public void onLinkClicked() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://vk.com/kiril_samoilov"));
    }

    @FXML
    public void onExitClicked() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
