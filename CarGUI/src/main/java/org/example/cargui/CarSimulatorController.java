package org.example.cargui;

import javafx.animation.AnimationTimer;
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
import org.example.car.Car;

import java.io.IOException;

public class CarSimulatorController {

    // CONSTANTS
    private static final double START_X_POSITION = -150.0; // Reset position
    private static final double MAX_Y_POSITION = 850.0;    // Bottom limit
    private static final double MIN_Y_POSITION = -250.0;   // Top limit

    private static final double FRICTION_CLUTCH_PRESSED = 0.998; // Rolling friction
    private static final double BRAKE_FORCE_WHEELS = 5.0;        // Brake strength
    private static final double MOVEMENT_FACTOR = 0.05;          // Speed to Pixel factor

    private static final int RED_LINE_RPM = 6000;  // Red zone start
    private static final int WARN_RPM = 4500;      // Yellow zone start

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
    @FXML private Button onDeletingCar;
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
    private AnimationTimer timer;
    private double currentVelocity = 0;

    // Control Flags
    private boolean isUpPressed = false;
    private boolean isDownPressed = false;
    private boolean isRightPressed = false; // Gas
    private boolean isLeftPressed = false;  // Brake

    // INITIALIZATION

    @FXML
    private void initialize() {
        // Create initial cars
        Car toyota = new Car("DW 12345", "Toyota", "GT86", 230, 1250);
        Car ford = new Car("KR 55555", "Ford", "Mustang", 250, 1700);
        Car porsche = new Car("W1 SPEED", "Porsche", "911", 300, 1450);
        Car defaultCar = new Car("XX 00000", "Generic", "Default", 150, 1000);

        this.carSelectorCombo.getItems().addAll(toyota, ford, porsche, defaultCar);

        // Listener for car selection
        this.carSelectorCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.currentCar = newValue;
                carImageView.setVisible(true);
                updateCarImage(newValue);
                refresh();
                System.out.println("Selected car: " + newValue.getModel());
            }
        });

        // Default state
        this.currentCar = null;
        carImageView.setVisible(false);
        this.refresh();

        // Keyboard setup (Wait for scene to load)
        carImageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                setupKeyboard(newScene);
            }
        });

        startTimer();
    }

    private void setupKeyboard(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> isUpPressed = true;
                case DOWN -> isDownPressed = true;
                case RIGHT -> isRightPressed = true;
                case LEFT -> isLeftPressed = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP -> isUpPressed = false;
                case DOWN -> isDownPressed = false;
                case RIGHT -> isRightPressed = false;
                case LEFT -> isLeftPressed = false;
            }
        });

        // Focus management
        Parent root = scene.getRoot();
        root.setFocusTraversable(true);
        root.setOnMouseClicked(event -> root.requestFocus());
        carImageView.setOnMouseClicked(event -> root.requestFocus());
    }

    private void updateCarImage(Car car) {
        String imagePath = switch (car.getModel().toLowerCase()) {
            case "gt86" -> "/gt86.png";
            case "mustang" -> "/mustang.png";
            case "911", "porsche" -> "/porsche.png";
            default -> "/default.png";
        };

        try {
            var resource = getClass().getResource(imagePath);
            Image imageToLoad;

            if (resource != null) {
                imageToLoad = new Image(resource.toExternalForm());
            } else {
                System.out.println("Missing image: " + imagePath + ", loading fallback.");
                imageToLoad = new Image(getClass().getResource("/car-icon.png").toExternalForm());
            }

            carImageView.setImage(imageToLoad);
            carImageView.setFitWidth(200);
            carImageView.setFitHeight(100);
            carImageView.setPreserveRatio(true);
            carImageView.setTranslateX(0);
            carImageView.setTranslateY(0);

        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    // BUTTON ACTIONS

    @FXML
    private void onStart(ActionEvent actionEvent) {
        if (currentCar != null) currentCar.start();
        refresh();
    }

    @FXML
    private void onTurnOff(ActionEvent actionEvent) {
        if (currentCar != null) currentCar.stop();
        refresh();
    }

    @FXML
    private void GearUp(ActionEvent actionEvent) {
        if (currentCar != null) currentCar.shiftUp();
        refresh();
    }

    @FXML
    private void GearDown(ActionEvent actionEvent) {
        if (currentCar != null) currentCar.shiftDown();
        refresh();
    }

    @FXML
    private void Press(ActionEvent actionEvent) {
        if (currentCar != null && currentCar.getClutch() != null) {
            currentCar.getClutch().press();
            refresh();
        }
    }

    @FXML
    private void EaseDown(ActionEvent actionEvent) {
        if (currentCar != null && currentCar.getClutch() != null) {
            currentCar.getClutch().release();
            refresh();
        }
    }

    @FXML
    private void SpeedUp(ActionEvent actionEvent) {
        if (currentCar == null) return;
        if (currentCar.isOn() && currentCar.getEngine() != null) {
            currentCar.getEngine().increaseRpm(400);
            System.out.println("Gas added (Button)");
        } else {
            System.out.println("Engine is off!");
        }
        refresh();
    }

    @FXML
    private void SlowDown(ActionEvent actionEvent) {
        if (currentCar != null && currentCar.getEngine() != null) {
            currentCar.getEngine().decreaseRpm(400);
        }
        refresh();
    }

    @FXML
    private void onDeletingCar(ActionEvent actionEvent) {
        Car selectedCar = carSelectorCombo.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            carSelectorCombo.getItems().remove(selectedCar);
            System.out.println("Deleted car: " + selectedCar.getModel());
            this.currentCar = null; // Clear current car
            refresh();
        }
    }

    @FXML
    private void onOpenAddCarWindow() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addCar.fxml"));
            Parent root = loader.load();

            AddCarController controller = loader.getController();
            // This method must exist in AddCarController:
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
        this.carSelectorCombo.getItems().add(car);
        this.carSelectorCombo.getSelectionModel().select(car);
        this.refresh();
    }

    // SIMULATION LOOP & LOGIC

    private void startTimer() {
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (currentCar == null) return;

                // Y AXIS (Lane Change)
                if (isUpPressed) {
                    carImageView.setTranslateY(carImageView.getTranslateY() - 5);
                    carImageView.setRotate(-15);
                } else if (isDownPressed) {
                    carImageView.setTranslateY(carImageView.getTranslateY() + 5);
                    carImageView.setRotate(15);
                } else {
                    carImageView.setRotate(0);
                }

                // X AXIS (Physics)
                if (isRightPressed && currentCar.isOn()) {
                    // GAS
                    if (!currentCar.isClutchPressed()) {
                        int gear = currentCar.getCurrentGear();
                        if (gear == 0) gear = 1;
                        // Acceleration depends on gear (lower gear = faster rpm gain)
                        int acceleration = 15 / gear;
                        currentCar.getEngine().increaseRpm(acceleration);
                    } else {
                        // Revving on neutral/clutch
                        currentCar.getEngine().increaseRpm(100);
                    }

                } else if (isLeftPressed) {
                    // BRAKE
                    currentCar.brake();
                    if (currentCar.isClutchPressed()) {
                        currentVelocity -= BRAKE_FORCE_WHEELS;
                        if (currentVelocity < 0) currentVelocity = 0;
                    }
                } else {
                    // IDLE (Engine drag)
                    currentCar.getEngine().decreaseRpm(10);
                }

                // Stall Logic
                if (currentCar.isOn() && currentCar.getCurrentGear() > 0 &&
                        !currentCar.isClutchPressed() && currentCar.getEngine().getRpm() < 300) {
                    currentCar.stop();
                    System.out.println("Engine stalled!");
                }

                // SPEED CALCULATION
                double engineTargetSpeed = currentCar.getSpeed();

                if (!currentCar.isClutchPressed()) {
                    // Clutch Released: Wheels locked to engine speed
                    currentVelocity = engineTargetSpeed;
                } else {
                    // Clutch Pressed: Coasting (Friction slows down)
                    currentVelocity *= FRICTION_CLUTCH_PRESSED;
                    if (currentVelocity < 0.1) currentVelocity = 0;
                }

                // Move Image
                carImageView.setTranslateX(carImageView.getTranslateX() + (currentVelocity * MOVEMENT_FACTOR));

                // MAP LOOPING
                double currentWindowWidth = gamePane.getWidth();

                // Loop X
                if (carImageView.getTranslateX() > currentWindowWidth) {
                    carImageView.setTranslateX(START_X_POSITION);
                }

                // Loop Y
                if (carImageView.getTranslateY() > MAX_Y_POSITION) {
                    carImageView.setTranslateY(MIN_Y_POSITION);
                } else if (carImageView.getTranslateY() < MIN_Y_POSITION) {
                    carImageView.setTranslateY(MAX_Y_POSITION);
                }

                // Update UI
                refresh();
                // Overwrite speed field with physics speed
                speedField.setText(String.valueOf(Math.round(currentVelocity)));

                // Focus fix for keyboard
                if (isUpPressed || isDownPressed || isRightPressed || isLeftPressed) {
                    carImageView.requestFocus();
                }
            }
        };
        this.timer.start();
    }

    private void refresh() {
        if (currentCar == null) {
            clearFields();
            return;
        }

        // Basic Info
        manufacturerField.setText(currentCar.getManufacturer());
        modelField.setText(currentCar.getModel());
        plateField.setText(currentCar.getPlateNumber());
        weightField.setText(String.valueOf(currentCar.getWeight()));
        speedField.setText(String.valueOf(currentCar.getSpeed()));

        // Gearbox
        if (currentCar.getGearbox() != null) {
            nameGearboxField.setText(currentCar.getGearbox().getComponentName());
            priceGearboxField.setText(String.valueOf(currentCar.getGearbox().getPrice()));
            weightGearboxField.setText(String.valueOf(currentCar.getGearbox().getWeight()));
            gearTextField.setText(String.valueOf(currentCar.getCurrentGear()));
        }

        // Engine
        if (currentCar.getEngine() != null) {
            rpmField.setText(String.valueOf(currentCar.getEngine().getRpm()));
            nameEngineField.setText(currentCar.getEngine().getComponentName());
            priceEngineField.setText(String.valueOf(currentCar.getEngine().getPrice()));
            weightEngineField.setText(String.valueOf(currentCar.getEngine().getWeight()));

            // Tachometer Colors
            int rpm = currentCar.getEngine().getRpm();
            if (rpm > RED_LINE_RPM) {
                rpmField.setStyle("-fx-control-inner-background: #ffcccc; -fx-text-fill: red; -fx-font-weight: bold;");
            } else if (rpm > WARN_RPM) {
                rpmField.setStyle("-fx-control-inner-background: #ffffe0; -fx-text-fill: black;");
            } else {
                rpmField.setStyle(null);
            }
        }

        // Clutch
        if (currentCar.getClutch() != null) {
            clutchStateField.setText(currentCar.isClutchPressed() ? "Pressed" : "Released");
            nameClutchField.setText(currentCar.getClutch().getComponentName());
            priceClutchField.setText(String.valueOf(currentCar.getClutch().getPrice()));
            weightClutchField.setText(String.valueOf(currentCar.getClutch().getWeight()));
        }

        // Status LED
        if (indicatorStatus != null) {
            if (currentCar.isOn()) {
                indicatorStatus.setFill(Color.LIGHTGREEN);
                indicatorStatus.setEffect(new Glow(0.8));
            } else {
                indicatorStatus.setFill(Color.RED);
                indicatorStatus.setEffect(null);
            }
        }
    }

    private void clearFields() {
        manufacturerField.setText("");
        modelField.setText("");
        plateField.setText("");
        weightField.setText("");
        speedField.setText("");
        nameGearboxField.setText("");
        gearTextField.setText("");
        rpmField.setText("");
        clutchStateField.setText("");
        if (indicatorStatus != null) indicatorStatus.setFill(Color.GREY);
    }
}