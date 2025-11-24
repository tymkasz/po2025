package org.example.cargui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.example.car.Samochod;

public class HelloController {
    @FXML
    private TextField biegTextField;
    @FXML
    private Button EaseDown;
    @FXML
    private Button Press;
    @FXML
    private Button SpeedUp;
    @FXML
    private Button SlowDown;
    @FXML
    private Button GearUp;
    @FXML
    private Button GearDown;
    @FXML
    private Button TurnOffButton;
    @FXML
    private Button StartButton;
    @FXML
    private ComboBox<Samochod> carSelectorCombo;
    @FXML
    private Button AddingNew;
    @FXML
    private Button DeletingCar;
    @FXML
    private TextField modelField;
    @FXML
    private TextField regField;
    @FXML
    private TextField wagaField;
    @FXML
    private TextField predkoscField;
    @FXML
    private ImageView carImageView;
    @FXML
    private Samochod currentCar;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void onStart(ActionEvent actionEvent) {
        this.currentCar.wlacz();
        this.updateView();
    }

    @FXML
    private void onTurnOff(ActionEvent actionEvent) {
        this.currentCar.wylacz();
        this.updateView();
    }

    @FXML
    private void GearUp(ActionEvent actionEvent) {
        this.currentCar.zwiekszBieg();
        this.updateView();
    }

    @FXML
    private void GearDown(ActionEvent actionEvent) {
        this.currentCar.zmniejszBieg();
        this.updateView();
    }

    @FXML
    private void SpeedUp(ActionEvent actionEvent) {
        System.out.println("Speeding up");
    }

    @FXML
    private void SlowDown(ActionEvent actionEvent) {
        System.out.println("Slowing down");
    }

    @FXML
    private void Press(ActionEvent actionEvent) {
        System.out.println("Pressing up");
    }

    @FXML
    private void EaseDown(ActionEvent actionEvent) {
        System.out.println("Easing down");
    }

    @FXML
    private void AddingCar(ActionEvent actionEvent) {
        System.out.println("Adding new car");
    }

    @FXML
    private void DeletinCar(ActionEvent actionEvent) {
        System.out.println("Deleting a car");
    }

    @FXML
    private void initialize(){

        Samochod car = new Samochod("BE2345", "Toyota", "GT86", 300);
        this.currentCar = car;

        this.carSelectorCombo.getItems().add(car);

        this.updateView();
    }

    private void updateView(){
        this.biegTextField.setText(String.valueOf(this.currentCar.getBieg()));

    }
}
