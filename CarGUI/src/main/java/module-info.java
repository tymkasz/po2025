module org.example.cargui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.example.cargui to javafx.fxml;
    exports org.example.cargui;
}