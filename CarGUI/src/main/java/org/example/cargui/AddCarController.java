package org.example.cargui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCarController {
    private HelloController helloController;
    @FXML
    public TextField modelChooseField;
    @FXML
    public TextField regChooseField;
    @FXML
    public TextField wagaChooseField;
    @FXML
    public TextField predkoscChooseField;
    @FXML
    public Button confirmButton;
    @FXML
    public Button cancelButton;

    public void setHelloController(HelloController helloController){
        this.helloController = helloController;
    }
    private void onConfirmButton(){
        String model = modelChooseField.getText();
        String registration = regChooseField.getText();
        double weight;
        int speed;

        try {
            weight = Double.parseDouble(wagaChooseField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawne dane. Spróbuj ponownie.");
        }

        helloController.AddingCar(model, registration, weight, speed);
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    private void onCancelButton(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


}
