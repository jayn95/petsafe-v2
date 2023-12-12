package petsafe;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import petsafe.components.Recipe;

public class Home {

  @FXML
  private FlowPane content;

  @FXML
  private ScrollPane scrollPane;

  @FXML
  private TextField searchBar;

  private List<Recipe> recipes;

  @FXML
  public void initialize() {
    recipes = new ArrayList<>();
    SQLite sql = new SQLite();
    ResultSet results = sql.selectAll("SELECT * FROM recipes");

    try {
      while (results.next()) {
        // int id = results.getInt("id");
        String name = results.getString("name");
        String description = results.getString("description");
        String image = results.getString("image");
        boolean petsafe = results.getInt("petsafe") == 1;

        Recipe recipe = new Recipe(name, description, image, 5, petsafe);
        recipes.add(recipe);
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    
    // scrollPane.setMaxHeight(460);

    Timeline scrollTimeline = new Timeline();
    FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1));

    // Scrollbar customization

    Platform.runLater(() -> {
      content.getChildren().addAll(recipes);
      adjustWidth();

      scrollPane.lookup(".scroll-bar:vertical").setOpacity(0);

      scrollPane.vvalueProperty().addListener((observable, oldVal, newVal) -> {
        scrollPane.lookup(".scroll-bar:vertical").setOpacity(1);

        scrollTimeline.stop();
        scrollTimeline.getKeyFrames().clear();
        fadeOutTransition.stop();

        scrollTimeline.getKeyFrames().add(
            new KeyFrame(Duration.seconds(3), e -> {
              fadeOutTransition.setNode(scrollPane.lookup(".scroll-bar:vertical"));
              fadeOutTransition.setFromValue(1);
              fadeOutTransition.setToValue(0);
              fadeOutTransition.play();
            }));

        scrollTimeline.play();
      });
    });
  }

  @FXML
  private void listenTextChange() {
    String searchString = searchBar.getText().trim().toLowerCase();

    List<Recipe> tmp = new ArrayList<>();

    for (Recipe recipe : recipes) {
      if (recipe.getRecipeName().toLowerCase().contains(searchString)) {
        tmp.add(recipe);
      } 
    }

    content.getChildren().clear();
    content.getChildren().addAll(tmp);
    
  }

  private void adjustWidth() {
    double availableWidth = content.getWidth();
    
    int columns = (int) (availableWidth / (recipes.get(0).getPrefWidth()));
    double gap = Math.max((availableWidth % recipes.get(0).getPrefWidth()) / columns, 0.0);
    
    System.out.println(gap);
    content.setHgap(gap);
    content.setVgap(gap);
  }

  @FXML
  private void addRecipe() throws IOException {
    App.setRoot("addRecipe");
  }
}