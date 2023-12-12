package petsafe.components;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import petsafe.App;
import petsafe.RecipePage;

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

  private String recipeNameStr;

  public Recipe(String name, String description, String imgPath, int rating, boolean petsafe, List<String> ingredients, List<String> procedure) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recipe.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    // Add effects
    DropShadow shadow = new DropShadow();
    shadow.setColor(Color.rgb(68, 93, 72, 0.5));
    shadow.setSpread(-1.0);
    shadow.setRadius(0);
  
    this.setEffect(shadow);

    try {
      fxmlLoader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    
    String assetsPath = "src/main/resources/petsafe/assets/thumbnails/";
    URI uri = Paths.get(assetsPath, imgPath).toUri();
    
    String imgFile = uri.toASCIIString();
    Image imgObject = new Image(uri.toString(), 400, 0, true, false);

    image.setStyle(
      "-fx-background-image: url('" + imgFile + "');" 
      + "-fx-background-size: cover;"
      + "-fx-background-position: center center;" 
      + "-fx-background-color: gray;"
    );

    Rectangle clip = new Rectangle();
    clip.widthProperty().bind(image.widthProperty());
    clip.heightProperty().bind(image.heightProperty());
    clip.setArcWidth(20);
    clip.setArcHeight(20);

    image.setClip(clip);

    recipeNameStr = name;
    recipeName.setText(name);

    recipeDescription.setText(description);
    isPetSafe.setText(petsafe ? "Safe for pets" : "Not safe for pets");

    // Set Mouse Click Event
    this.setOnMouseClicked((e) -> {
      // System.out.println("Clicked Recipe " + name);

      try {
        RecipePage controller = App.setRootGetController("recipePage");
        controller.setRecipeData(name, rating, imgObject, description, petsafe, ingredients, procedure);

      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    });

    this.setOnMouseEntered((e) -> {
      Timeline timeline = new Timeline(
        new KeyFrame(Duration.seconds(0.15), 
          new KeyValue(shadow.offsetYProperty(), 3),
          new KeyValue(shadow.radiusProperty(), 6),
          new KeyValue(this.translateYProperty(), -6)
        )
      );

      timeline.play();
    });

    this.setOnMouseExited((e) -> {
      Timeline timeline = new Timeline(
        new KeyFrame(Duration.seconds(0.15), 
          new KeyValue(shadow.offsetYProperty(), 0),
          new KeyValue(shadow.radiusProperty(), 0),
          new KeyValue(this.translateYProperty(), 0)
        )  
      );

      timeline.play();
    });

  }

  public String getRecipeName() {
    return recipeNameStr;
  }
}
