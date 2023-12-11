package petsafe.components;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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
    // System.out.println(imgFile);

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

    
    recipeName.setText(name);
    recipeDescription.setText(description);
    isPetSafe.setText(petsafe ? "Safe for pets" : "Not safe for pets");

    // Set Mouse Click Event
    this.setOnMouseClicked((e) -> {
      System.out.println("Clicked Recipe " + name);
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
}
