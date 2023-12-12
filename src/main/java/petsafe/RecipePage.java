package petsafe;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RecipePage {

    @FXML
    private Label description;

    @FXML
    private Label ingredients;

    @FXML
    private Label name;
    
    @FXML
    private Label procedure;
    
    private boolean ispetsafe;
    private String imageFilename;
    private int rating;

    @FXML
    void backToHome(ActionEvent event) throws IOException {
      App.setRoot("home");
    }

    public void printPassedParams(String name) {
      this.name.setText(name);
    }

}
