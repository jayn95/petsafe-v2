package petsafe;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddRecipe {

    @FXML
    private TextArea description;

    @FXML
    private TextArea ingredients;

    @FXML
    private CheckBox ispetsafe;

    @FXML
    private TextField name;

    @FXML
    private TextArea procedure;

    @FXML
    private TextField imagePath;

    @FXML
    void addRecipe(ActionEvent event) {
      if (
        description.getText().trim().isEmpty()
        || ingredients.getText().trim().isEmpty()
        || name.getText().trim().isEmpty()
        || procedure.getText().trim().isEmpty()
        || imagePath.getText().trim().isEmpty()  
      ) {
        System.err.println("You need to fill in all inputs!");
        return;
      }

      Gson gson = new Gson();
      SQLite sql = new SQLite();

      List<String> ingredientList = Arrays.asList(ingredients.getText().trim().split("\n"));
      String jsonIngredients = gson.toJson(ingredientList);

      List<String> procedureList = Arrays.asList(procedure.getText().trim().split("\n"));
      String jsonProcedure = gson.toJson(procedureList);

      System.out.println(jsonIngredients + "\n\n" + jsonProcedure);

      String[] sqlColumns = { "name", "description", "petsafe", "image", "ingredients", "procedure" };
      String[] sqlValues = { name.getText().trim(), description.getText().trim(), ispetsafe.isSelected() ? "1" : "0", imagePath.getText().trim(), jsonIngredients, jsonProcedure };

      if (sql.addRow("recipes", sqlColumns, sqlValues)) {
        System.out.println("Success!");

        name.clear();
        imagePath.clear();
        description.clear();
        ingredients.clear();
        procedure.clear();
        ispetsafe.setSelected(false);
      } 
    }

    @FXML
    void backToHome(ActionEvent event) throws IOException {
      App.setRoot("home");
    }

}
