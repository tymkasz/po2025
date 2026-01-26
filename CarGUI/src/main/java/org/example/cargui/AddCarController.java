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

    private CarSimulatorController mainController;

    // Text fields (car data)
    @FXML private TextField manufacturerField;
    @FXML private TextField modelField;
    @FXML private TextField plateField;
    @FXML private TextField weightField;

    // ComboBoxes
    @FXML private ComboBox<String> engineTypeCombo;
    @FXML private ComboBox<String> gearboxTypeCombo;
    @FXML private ComboBox<String> clutchTypeCombo;

    @FXML private Button confirmButton;
    @FXML private Button cancelButton;

    // Passing references to the main window
    public void setMainController(CarSimulatorController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        // Filling list of engines
        engineTypeCombo.getItems().addAll(
                "Standard 1.6 (120 HP)",
                "Turbo Diesel 2.0 (180 HP)",
                "Sport V6 (350 HP)",
                "Monster V8 (500 HP)"
        );
        engineTypeCombo.getSelectionModel().selectFirst();

        // Filling list of gearboxes
        gearboxTypeCombo.getItems().addAll(
                "City Manual (5-Speed)",
                "Sport Manual (6-Speed)",
                "Heavy Truck (12-Speed)"
        );
        gearboxTypeCombo.getSelectionModel().selectFirst();

        // Filling list of clutches
        clutchTypeCombo.getItems().addAll(
                "Standard Clutch",
                "Sport Clutch",
                "Ceramic Racing Clutch"
        );
        clutchTypeCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void onConfirm() {
        try {
            // Downloading default data
            String manufacturer = manufacturerField.getText();
            String model = modelField.getText();
            String plate = plateField.getText();
            // Parsing numbers (may throw an error if you type "abc")
            int weight = Integer.parseInt(weightField.getText());

            // CREATING COMPONENTS BASED ON SELECTION (Factory)
            Clutch selectedClutch = createClutch(clutchTypeCombo.getValue());
            // Injecting Clutch into Gearbox
            Gearbox selectedGearbox = createGearbox(gearboxTypeCombo.getValue(), selectedClutch);
            Engine selectedEngine = createEngine(engineTypeCombo.getValue());

            // BUILDING A CAR
            // We use the full Car constructor with dependency injection
            Car newCar = new Car(
                    selectedEngine,
                    selectedGearbox,
                    plate,
                    manufacturer,
                    model,
                    weight
            );

            // Sending the finished car to the main window
            if (mainController != null) {
                mainController.addCarToList(newCar);
            }
            //Closing the window
            closeWindow();

        } catch (NumberFormatException e) {
            System.err.println("Error: Please enter valid numbers in the Weight and Speed fields");
            // TODO: zmiana koloru ramki na czerwono
        } catch (Exception e) {
            System.err.println("Another error when creating a car:" + e.getMessage());
        }
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    // FACTORY METHODS (Create objects based on name)

    private Engine createEngine(String selection) {
        if (selection == null) selection = "Standard";
        return switch (selection) {
            case "Turbo Diesel 2.0 (180 HP)" -> new Engine("VW", "TDI", 4500, 180, "2.0 TDI", 12000, 180);
            case "Sport V6 (350 HP)" -> new Engine("Nissan", "VQ37", 7500, 350, "3.7L V6", 25000, 220);
            case "Monster V8 (500 HP)" -> new Engine("Ford", "Coyote", 7000, 500, "5.0L V8", 35000, 250);
            default -> new Engine("Generic", "1.6 MPI", 6000, 120, "EcoEngine", 5000, 130);
        };
    }

    private Gearbox createGearbox(String selection, Clutch clutch) {
        if (selection == null) selection = "Standard";
        return switch (selection) {
            case "Sport Manual (6-Speed)" -> new Gearbox("ZF", "6MT", 6, clutch, "Short Shift", 6000, 55);
            case "Heavy Truck (12-Speed)" -> new Gearbox("Eaton", "Fuller", 12, clutch, "Heavy Duty", 15000, 200);
            default -> new Gearbox("Aisin", "5MT", 5, clutch, "City Box", 3000, 45);
        };
    }

    private Clutch createClutch(String selection) {
        if (selection == null) selection = "Standard";

        return switch (selection) {
            case "Sport Clutch" -> new Clutch("Exedy", "Stage 1", "Sport Disc", 1500, 12);
            case "Ceramic Racing Clutch" -> new Clutch("Sachs", "Race", "Ceramic", 4000, 10);
            // "Standard Clutch"
            default -> new Clutch("Valeo", "OEM", "Organic", 800, 15);
        };
    }
}