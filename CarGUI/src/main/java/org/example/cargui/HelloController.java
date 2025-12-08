package org.example.cargui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.car.Samochod;
import java.io.IOException;
import java.util.ArrayList;

public class HelloController {

    private ArrayList<Samochod> carList = new ArrayList<>();

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
    public TextField producentField;
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
    private TextField nazwaSkrzyniaField;
    @FXML
    private TextField cenaSkrzyniaField;
    @FXML
    private TextField wagaSkrzyniaField;
    @FXML
    private TextField biegTextField;



    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void onStart(ActionEvent actionEvent) {
        this.currentCar.wlacz();
        refresh();
    }

    @FXML
    private void onTurnOff(ActionEvent actionEvent) {
        this.currentCar.wylacz();
        refresh();
    }

    @FXML
    private void GearUp(ActionEvent actionEvent) {
        this.currentCar.zwiekszBieg();
        refresh();
    }

    @FXML
    private void GearDown(ActionEvent actionEvent) {
        this.currentCar.zmniejszBieg();
        refresh();
    }

    @FXML
    private void SpeedUp(ActionEvent actionEvent) {
        System.out.println("Speeding up");
        refresh();
    }

    @FXML
    private void SlowDown(ActionEvent actionEvent) {
        System.out.println("Slowing down");
        refresh();
    }

    @FXML
    private void Press(ActionEvent actionEvent) {
        System.out.println("Pressing up");
        refresh();
    }

    @FXML
    private void EaseDown(ActionEvent actionEvent) {
        System.out.println("Easing down");
        refresh();
    }

    @FXML
    private void DeletingCar(ActionEvent actionEvent) {
        System.out.println("Deleting a car");
        refresh();
    }

    @FXML
    private void initialize(){

        Samochod car = new Samochod("BE2345", "Toyota", "GT86", 300, 2, 0);
        this.currentCar = car;

        this.carSelectorCombo.getItems().add(car);


        Image carImage = new Image(getClass().getResource("/car-icon.png").toExternalForm());
        System.out.println("Image width: " + carImage.getWidth() + ", height: " + carImage.getHeight());
        carImageView.setImage(carImage);

        carImageView.setFitWidth(200);
        carImageView.setFitHeight(100);

        carImageView.setTranslateX(0);
        carImageView.setTranslateY(0);

        this.refresh();

    }

    private void refresh()
    {
        producentField.setText(String.valueOf(currentCar.getProducent()));
        wagaField.setText(String.valueOf(currentCar.getWaga()));
        regField.setText(currentCar.getReg());
        predkoscField.setText(String.valueOf(currentCar.getSpeed()));
        modelField.setText(currentCar.getModel());

        nazwaSkrzyniaField.setText(this.currentCar.getSkrzynia().getModel());
    }

    @FXML
    private void onOpenAddCarWindow() throws IOException
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addCar.fxml"));
            Parent root = loader.load();

            AddCarController controller = loader.getController();
            controller.setHelloController(this);

            Stage stage = new Stage();
            stage.setTitle("Dodaj nowy samochód");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDeletingCar()
    {
        Samochod selectedCar = carSelectorCombo.getSelectionModel().getSelectedItem();

        if (selectedCar != null) {
            // Usuń z listy
            carSelectorCombo.getItems().remove(selectedCar);
            System.out.println("Usunięto auto: " + selectedCar.getModel());
        } else {
            System.out.println("Nie wybrano auta do usunięcia.");
        }
    }

    void AddingCar(Samochod car) {
        // Dodanie auta do listy
        this.carSelectorCombo.getItems().add(car);
        // Wybór auta dodanego
        carSelectorCombo.getSelectionModel().select(car);
        this.refresh();

    }

}
