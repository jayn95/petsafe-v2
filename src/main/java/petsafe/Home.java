package petsafe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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

    // int recipeCount = recipes.size();

    // for (int i = 0; i < recipeCount; i++) {
    //   Recipe recipe = recipes.get(i);
    //   int row = i / 4;
    //   int column = i % 4;

    //   content.add(recipe, column, row);
    // }
    
    scrollPane.setMaxHeight(460);

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
    String searchString = searchBar.getText();

    List<Recipe> tmp = new ArrayList<>();
    
    // for (Node recipe : content.getChildren()) {
    //   if (recipe instanceof Recipe) {
    //     if (((Recipe) recipe).getRecipeName().contains(searchString)) {
    //       recipe.setVisible(true);

    //     } else {
    //       recipe.setVisible(false);
    //     }
    //   }
    // }

    for (Recipe recipe : recipes) {
      if (recipe.getRecipeName().contains(searchString)) {
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
}