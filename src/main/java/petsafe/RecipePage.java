package petsafe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class RecipePage {

  @FXML
  private Label description;

  @FXML
  private Label ingredients;

  @FXML
  private Label name;

  @FXML
  private Label procedure;

  @FXML
  private HBox ratingHolder;

  @FXML
  private AnchorPane imageContainer;

  private boolean ispetsafe;
  private String imageFilePath;
  private int rating;

  private List<String> ingredientsList, procedureList;

  @FXML
  void backToHome(ActionEvent event) throws IOException {
    App.setRoot("home");
  }

  public void printPassedParams(String name) {
    this.name.setText(name);
  }

  public void setRecipeData(String name, int rating, String imagePath, String description, boolean ispetsafe,
      List<String> ingredients, List<String> procedure) {

    ingredientsList = new ArrayList<>(ingredients);
    procedureList = new ArrayList<>(procedure);

    this.ispetsafe = ispetsafe;
    this.imageFilePath = imagePath;
    this.rating = rating;

    // Set all Data
    this.ingredients.setText(String.join("\n", ingredients)); // Ingredients
    this.procedure.setText(String.join("\n", procedure)); // Procedure

    this.name.setText(name); // Name
    this.description.setText(description); // Description

    for (int i = 0; i < rating; i++) { // Rating
      ratingHolder.getChildren().add(new FontAwesomeIconView(FontAwesomeIcon.STAR));
    }

    // Image
    imageContainer.setStyle(
        "-fx-background-image: url('" + imagePath + "');"
            + "-fx-background-size: cover;"
            + "-fx-background-position: center center;"
            + "-fx-background-color: gray;");
  }
}
