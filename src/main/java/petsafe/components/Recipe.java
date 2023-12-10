package petsafe.components;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Recipe extends VBox {
  @FXML
  private AnchorPane image;

  @FXML
  private Label recipeName;

  @FXML
  private HBox ratingContainer;

  @FXML
  private Label recipeDescription;

  @FXML
  private Label isPetSafe;

  public Recipe(String name, String description, String imgPath, int rating, boolean petsafe) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recipe.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    BackgroundImage bg = new BackgroundImage(
        new Image(getClass().getResource("/petsafe/assets/thumbnails/" + imgPath).toExternalForm()),
        BackgroundRepeat.NO_REPEAT, // repeatX
        BackgroundRepeat.NO_REPEAT, // repeatY
        BackgroundPosition.CENTER, // position
        new BackgroundSize(-1, -1, false, false, false, true));

    image.setBackground(new Background(bg));

    recipeName.setText(name);
    recipeDescription.setText(description);
    isPetSafe.setText(petsafe ? "Safe for pets" : "Not safe for pets");
  }
}
