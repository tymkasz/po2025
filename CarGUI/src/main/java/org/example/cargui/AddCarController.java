package org.example.cargui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.car.Car;
import org.example.car.Clutch;
import org.example.car.Engine;
import org.example.car.Gearbox;

public class AddCarController {

    // Reference to the main application controller
    // Needed to pass the new car back to the main list
    private CarSimulatorController mainController;

    // FXML UI Components (Fields linked to .fxml file)
    @FXML private TextField manufacturerField;
    @FXML private TextField modelField;
    @FXML private TextField plateField;
    @FXML private TextField weightField;
    @FXML private TextField maxSpeedField;

    @FXML private ComboBox<String> clutchTypeCombo;
    @FXML private ComboBox<String> engineTypeCombo;
    @FXML private ComboBox<String> gearboxTypeCombo;

    @FXML private Button confirmButton;
    @FXML private Button cancelButton;

    /**
     * Sets the reference to the main controller.
     * This allows us to call methods like addCarToList() on the main window.
     * @param mainController The instance of the main window controller.
     */
    public void setMainController(CarSimulatorController mainController) {
        this.mainController = mainController;
    }

    /**
     * Triggered when the Confirm button is clicked.
     * Validates input, creates a Car object, and sends it to the main controller.
     */
    @FXML
    private void onConfirm() {
        try {
            // Retrieve text data from GUI fields
            String manufacturer = manufacturerField.getText();
            String model = modelField.getText();
            String plateNumber = plateField.getText();
            int weight = Integer.parseInt(weightField.getText());
            int maxSpeed = Integer.parseInt(maxSpeedField.getText());

            // Create components based on selection
            Engine selectedEngine = createEngine(engineTypeCombo.getValue());
            Gearbox selectedGearbox = createGearbox(gearboxTypeCombo.getValue());
            Clutch selectedClutch = createClutch(clutchTypeCombo.getValue());

            // Create a new Car object
            Car newCar = new Car(
                    selectedEngine,
                    selectedGearbox,
                    plateNumber,
                    manufacturer,
                    model,
                    maxSpeed,
                    weight,
                    selectedClutch
            );

            // Send the new car to the main controller to update the list
            if (mainController != null) {
                mainController.addCarToList(newCar);
            }

            // Close the popup window
            closeWindow();

        } catch (NumberFormatException e) {
            // Handle invalid input (e.g. "abc" in speed field)
            System.err.println("Error: Invalid number format. Please check Speed and Weight fields.");
        }
    }

    /**
     * Triggered when the Cancel button is clicked.
     * Closes the window without saving.
     */
    @FXML
    private void onCancel() {
        closeWindow();
    }

    // Helper method to close the current stage (window)
    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    private Engine createEngine(String selection) {
        if (selection == null) selection = "Standard"; // Zabezpieczenie

        return switch (selection) {
            case "Sport V6 Turbo (350 HP)" ->
                    new Engine("Toyota", "2GR-FE", 7500, "V6 Biturbo", 25000.0, 180.0);

            case "Monster V8 (500 HP)" ->
                    new Engine("Ford", "Coyote", 6800, "5.0L V8", 40000.0, 250.0);

            case "High-Rev Bike Engine" ->
                    new Engine("Suzuki", "Hayabusa", 11000, "1.3L Inline-4", 12000.0, 80.0);

            default -> // "Standard 1.6"
                    new Engine("Generic", "1.6 MPI", 6000, "EcoTec", 8000.0, 120.0);
        };
    }

    private Gearbox createGearbox(String selection) {
        if (selection == null) selection = "Standard";

        return switch (selection) {
            case "Manual 6-Speed (Sport)" ->
                    new Gearbox("ZF", "6MT-Sport", 6, "Short Shifter", 5000.0, 55.0);

            case "Truck 12-Speed (Heavy)" ->
                    new Gearbox("Eaton", "Fuller", 12, "Heavy Duty", 15000.0, 200.0);

            default -> // "Manual 5-Speed"
                    new Gearbox("Aisin", "5MT", 5, "City Box", 3000.0, 45.0);
        };
    }

    private Clutch createClutch(String selection) {
        if (selection == null) selection = "Standard";

        return switch (selection) {
            case "Sport Clutch" ->
                    new Clutch("Exedy", "Stage 1", "Sport Plate", 1500.0, 12.0);

            case "Racing Ceramic Clutch" ->
                    new Clutch("Sachs", "Race", "Ceramic Disc", 4000.0, 10.0);

            default -> // "Standard"
                    new Clutch("Valeo", "OEM", "Organic Disc", 800.0, 15.0);
        };
    }

    @FXML
    private void initialize() {

        // 1. Populate Engine Options
        engineTypeCombo.getItems().addAll(
                "Standard 1.6 (150 HP)",
                "Sport V6 Turbo (350 HP)",
                "Monster V8 (500 HP)"
        );
        engineTypeCombo.getSelectionModel().selectFirst();

        // 2. Populate Gearbox Options
        gearboxTypeCombo.getItems().addAll(
                "Manual 5-Speed (City)",
                "Manual 6-Speed (Sport)",
                "Truck 12-Speed (Heavy)"
        );
        gearboxTypeCombo.getSelectionModel().selectFirst();

        // 3. Populate Clutch Options
        clutchTypeCombo.getItems().addAll(
                "Standard Clutch",
                "Sport Clutch",
                "Racing Ceramic Clutch"
        );
        clutchTypeCombo.getSelectionModel().selectFirst();

    }

}