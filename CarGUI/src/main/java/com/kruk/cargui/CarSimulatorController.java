package com.kruk.cargui;

import com.kruk.car.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import com.kruk.car.*;

import java.io.IOException;

// Class is Observer -> can get messages from Car
public class CarSimulatorController implements Observer {
    // FXML COMPONENTS

    // Clutch Controls
    @FXML private Button EaseDown;
    @FXML private Button Press;
    @FXML private TextField nameClutchField;
    @FXML private TextField priceClutchField;
    @FXML private TextField weightClutchField;
    @FXML private TextField clutchStateField;

    // Engine Controls
    @FXML private Button SpeedUp;
    @FXML private Button SlowDown;
    @FXML private TextField nameEngineField;
    @FXML private TextField priceEngineField;
    @FXML private TextField weightEngineField;
    @FXML private TextField rpmField;
    @FXML private ComboBox<Engine> engineTypeCombo;

    // Gearbox Controls
    @FXML private Button GearUp;
    @FXML private Button GearDown;
    @FXML private TextField nameGearboxField;
    @FXML private TextField priceGearboxField;
    @FXML private TextField weightGearboxField;
    @FXML private TextField gearTextField;

    // Car & Dashboard
    @FXML private Button TurnOffButton;
    @FXML private Button StartButton;
    @FXML private ComboBox<Car> carSelectorCombo;
    @FXML private Button AddingNew;
    @FXML private Button DeletingCar;
    @FXML public TextField manufacturerField;
    @FXML private TextField modelField;
    @FXML private TextField plateField;
    @FXML private TextField weightField;
    @FXML private TextField speedField;
    @FXML private ImageView carImageView;
    @FXML private Circle indicatorStatus; // Status LED
    @FXML private AnchorPane gamePane;    // Game Background

    // Logic
    private Car currentCar;

    // INITIALIZATION

    @FXML
    private void initialize() {
        // Create initial cars
        // Mustang
        Clutch clutch1 = new Clutch("Exedy", "Stage 1", "Sport", 1000, 10);
        Gearbox gearbox1 = new Gearbox("Tremec", "Manual", 6, clutch1, "Sport", 4000, 50);
        Engine engine1 = new Engine("Ford", "V8", 7000, 450, "Coyote", 15000, 200);
        Car mustang = new Car(engine1, gearbox1, "DW 123", "Ford", "Mustang", 1600);

        // Toyota
        Clutch clutch2 = new Clutch("Aisin", "KH-634", "Sporty", 800, 15);
        Gearbox gearbox2 = new Gearbox("Aisin", "Manual", 6, clutch2, "Sporty", 3000, 45);
        Engine engine2 = new Engine("Toyota", "V6", 6500, 200, "GT", 10000, 200);
        Car toyota = new Car(engine2, gearbox2, "KR 123", "Toyota", "GT86", 1300);

        // Porsche
        Clutch clutch3 = new Clutch("Dual", "OP-09", "German Classic", 900, 10);
        Gearbox gearbox3 = new Gearbox("PDK", "Manual", 7, clutch3, "Classic", 4000, 50);
        Engine engine3 = new Engine("Porsche", "V7", 6700, 350, "OHV", 18000, 180);
        Car porsche = new Car(engine3, gearbox3, "KRA 123", "Porsche", "911", 1300);

        carSelectorCombo.getItems().addAll(mustang, toyota, porsche);

        // Listener for car selection
        carSelectorCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectCar(newValue);
            }
        });

        // Mouse handler
        gamePane.setOnMouseClicked(event -> {
            if (currentCar != null && currentCar.isOn()) {
                double xTarget = event.getX();
                double yTarget = event.getY();

                // Image centring
                double correctedX = xTarget - (carImageView.getFitWidth() / 2);
                double correctedY = yTarget - (carImageView.getFitHeight() / 2);

                System.out.println("Driving to: " + (int)correctedX + ", " + (int)correctedY);
                currentCar.driveTo(correctedX, correctedY);
            }
        });

        // Photo initialization (hidden by default)
        carImageView.setVisible(false);
    }

    private void selectCar(Car car) {
        this.currentCar = car;

        // Observer registration
        car.addObserver(this);

        updateCarImage(car);
        carImageView.setVisible(true);
        refreshGUI();
    }

    @Override
    public void update() {
        // GUI update must be in Platform.runLater
        Platform.runLater(() -> {
            if (currentCar == null) return;

            // Photo movement
            carImageView.setTranslateX(currentCar.getXPosition());
            carImageView.setTranslateY(currentCar.getYPosition());

            refreshGUI();
        });
    }

    private void refreshGUI() {
        if (currentCar == null) return;

        // Dynamic counters (Update every frame)
        rpmField.setText(String.valueOf(currentCar.getEngine().getRpm()));
        speedField.setText(String.valueOf(currentCar.getSpeed()));
        gearTextField.setText(String.valueOf(currentCar.getGearbox().getCurrentGear()));
        clutchStateField.setText(currentCar.getGearbox().getClutch().isPressed() ? "Pressed" : "Released");

        // Static Info (Update on selection)
        manufacturerField.setText(currentCar.getManufacturer());
        modelField.setText(currentCar.getModel());
        plateField.setText(currentCar.getPlateNumber());
        weightField.setText(String.valueOf(currentCar.getWeight()));

        // Components Info
        if (currentCar.getEngine() != null) {
            nameEngineField.setText(currentCar.getEngine().getComponentName());
            priceEngineField.setText(String.valueOf(currentCar.getEngine().getPrice()));
            weightEngineField.setText(String.valueOf(currentCar.getEngine().getWeight()));
        }
        if (currentCar.getGearbox() != null) {
            nameGearboxField.setText(currentCar.getGearbox().getComponentName());
            priceGearboxField.setText(String.valueOf(currentCar.getGearbox().getPrice()));
            weightGearboxField.setText(String.valueOf(currentCar.getGearbox().getWeight()));
        }
        if (currentCar.getClutch() != null) {
            nameClutchField.setText(currentCar.getClutch().getComponentName());
            priceClutchField.setText(String.valueOf(currentCar.getClutch().getPrice()));
            weightClutchField.setText(String.valueOf(currentCar.getClutch().getWeight()));
        }

        // Engine status LED
        if (currentCar.isOn()) {
            indicatorStatus.setFill(Color.LIGHTGREEN);
            indicatorStatus.setEffect(new Glow(0.8));
        } else {
            indicatorStatus.setFill(Color.RED);
            indicatorStatus.setEffect(null);
        }
    }

    // Loading image
    private void updateCarImage(Car car) {
        String modelName = car.getModel().toLowerCase();
        String imagePath = switch (modelName) {
            case "mustang" -> "/mustang.png";
            case "gt86", "toyota" -> "/gt86.png";
            case "911", "porsche" -> "/porsche.png";
            default -> "/default.png";
        };

        try {
            var resource = getClass().getResource(imagePath);
            if (resource != null) {
                carImageView.setImage(new Image(resource.toExternalForm()));
            } else {
                // Fallback
                var fallback = getClass().getResource("/car-icon.png");
                if (fallback != null) carImageView.setImage(new Image(fallback.toExternalForm()));
            }
            carImageView.setRotate(0);
        } catch (Exception e) {
            System.err.println("Image load error: " + e.getMessage());
        }
    }

    // BUTTON ACTIONS

    @FXML
    private void onStart(ActionEvent actionEvent) {
        if (currentCar != null) currentCar.startEngine();
    }

    @FXML
    private void onTurnOff(ActionEvent actionEvent) {
        if (currentCar != null) currentCar.stopEngine();
    }

    @FXML
    private void GearUp() {
        if (currentCar != null) currentCar.shiftUp();
    }

    @FXML
    private void GearDown() {
        if (currentCar != null) currentCar.shiftDown();
    }

    @FXML
    private void SpeedUp() {
        if (currentCar != null) {
            currentCar.getEngine().increaseRpm(500);
        }
    }

    @FXML
    private void SlowDown() {
        if (currentCar != null) {
            currentCar.getEngine().decreaseRpm(500);
        }
    }

    @FXML
    private void Press(ActionEvent actionEvent) {
        if (currentCar != null && currentCar.getGearbox().getClutch() != null) {
            currentCar.getGearbox().getClutch().press();
            refreshGUI();
        }
    }

    @FXML
    private void EaseDown(ActionEvent actionEvent) {
        if (currentCar != null && currentCar.getClutch() != null) {
            currentCar.getClutch().release();
            refreshGUI();
        }
    }

    @FXML
    private void DeletingCar(ActionEvent actionEvent) {
        Car selectedCar = carSelectorCombo.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            // Stop thread before deleting
            selectedCar.killThread();
            carSelectorCombo.getSelectionModel().clearSelection();
            carSelectorCombo.getItems().remove(selectedCar);
            System.out.println("Deleted car: " + selectedCar.getModel());
            this.currentCar = null;
            carImageView.setVisible(false);
            refreshGUI(); // Clear fields
        }
    }

    @FXML
    private void OpenAddCarWindow() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addCar.fxml"));
            Parent root = loader.load();

            AddCarController controller = loader.getController();
            controller.setMainController(this);

            Stage stage = new Stage();
            stage.setTitle("Add New Car");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Called from AddCarController
    public void addCarToList(Car car) {
        carSelectorCombo.getItems().add(car);
        carSelectorCombo.getSelectionModel().select(car);
    }
}