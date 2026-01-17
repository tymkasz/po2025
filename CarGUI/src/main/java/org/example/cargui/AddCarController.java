package org.example.cargui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.car.Samochod;

public class AddCarController {
    private CarSimulatorController carSimulatorController;
    @FXML
    public TextField modelChooseField;
    @FXML
    public TextField regChooseField;
    @FXML
    public TextField wagaChooseField;
    @FXML
    public TextField maxSpeedChooseField;
    @FXML
    public TextField producentChooseField;
    @FXML
    public Button confirmButton;
    @FXML
    public Button cancelButton;

    public void setHelloController(CarSimulatorController carSimulatorController){
        this.carSimulatorController = carSimulatorController;
    }
    @FXML
    private void onConfirmButton(){
        String producent = producentChooseField.getText();
        String model = modelChooseField.getText();
        String registration = regChooseField.getText();

        try {
            int maxSpeed = Integer.parseInt(maxSpeedChooseField.getText());
            int weight = Integer.parseInt(wagaChooseField.getText());

            Samochod car = new Samochod(registration, producent, model, maxSpeed, weight);

            carSimulatorController.AddingCar(car);
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            System.out.println("Niepoprawne dane. Spróbuj ponownie.");
        }


    }
    @FXML
    private void onCancelButton(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
