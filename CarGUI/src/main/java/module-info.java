module org.example.cargui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.kruk.cargui to javafx.fxml;
    exports com.kruk.cargui;
}