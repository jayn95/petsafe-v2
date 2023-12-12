module petsafe {
  requires transitive javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;
  requires transitive java.sql;
  requires com.google.gson;
  requires de.jensd.fx.glyphs.fontawesome;

  opens petsafe to javafx.fxml;
  opens petsafe.components to javafx.fxml;
  
  exports petsafe;
}
