package org.example.cargui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.car.Car;

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
        // 1. Retrieve text data from GUI fields
        String manufacturer = manufacturerField.getText();
        String model = modelField.getText();
        String plateNumber = plateField.getText();

        try {
            // 2. Parse numerical values (Strings to Integers)
            // This might throw NumberFormatException if the user types letters
            int maxSpeed = Integer.parseInt(maxSpeedField.getText());
            int weight = Integer.parseInt(weightField.getText());

            // 3. Create a new Car object
            // Note: Ensure the constructor arguments match the order in Car.java
            // (plateNumber, manufacturer, model, maxSpeed, weight)
            Car newCar = new Car(plateNumber, manufacturer, model, maxSpeed, weight);

            // 4. Send the new car to the main controller to update the list
            if (mainController != null) {
                mainController.addCarToList(newCar);
            }

            // 5. Close the popup window
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
}