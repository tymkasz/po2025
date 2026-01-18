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
import javafx.animation.AnimationTimer;

public class CarSimulatorController {

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
    private TextField obrotyField;
    @FXML
    private TextField sprzegloStanField;

    private AnimationTimer timer;



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
    private void DeletingCar(ActionEvent actionEvent) {
        System.out.println("Deleting a car");
        refresh();
    }

    @FXML
    private void initialize(){

        // Tworzenie auta i dodanie go do listy
        // Nowo powstałe auto nie jest currentCar
        Samochod toyota = new Samochod("DW 12345", "Toyota", "GT86", 230, 1250);
        Samochod ford = new Samochod("KR 55555", "Ford", "Mustang", 250, 1700);
        Samochod porsche = new Samochod("W1 SPEED", "Porsche", "Porsche", 300, 1450);
        Samochod defaultCar = new Samochod("XX 00000", "Generic", "Default", 150, 1000);
        this.carSelectorCombo.getItems().addAll(toyota, ford, porsche, defaultCar);

        this.carSelectorCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.currentCar = newValue; // Podmiana obiektu na ten wybrany z listy
                carImageView.setVisible(true); // Zmiana widoczności obrazka w przypadku wyboru modelu
                updateCarImage(newValue);

                refresh();                  // Aktualizacja pól tekstowych
                System.out.println("Wybrano na auto: " + newValue.getModel());
            }
        });

        // Początkowe auto to null -> wybierz model lub wprowadź swój
        this.currentCar = null;
        // Obrazek niewidoczny
        carImageView.setVisible(false);

        this.refresh();

        startTimer();

    }

    private void updateCarImage(Samochod car)
    {
        String imagePath = switch (car.getModel().toLowerCase()) {
            case "gt86" -> "/gt86.png";
            case "mustang" -> "/mustang.png";
            case "porsche" -> "/porsche.png";
            default -> "/default.png";
        };

        try {
            var resource = getClass().getResource(imagePath);
            Image imageToLoad;

            // Sprawdzamy czy plik istnieje
            if (resource != null) {
                imageToLoad = new Image(resource.toExternalForm());
            } else {
                // Fallback do domyślnego
                System.out.println("Brak pliku: " + imagePath + ", ładowanie domyślnego.");
                imageToLoad = new Image(getClass().getResource("/car-icon.png").toExternalForm());
            }

            // Ustawienie wielkości obrazka
            carImageView.setImage(imageToLoad);

            carImageView.setFitWidth(200);       // Maksymalna szerokość: 200px
            carImageView.setFitHeight(100);      // Maksymalna wysokość: 100px
            carImageView.setPreserveRatio(true); // Zachowaj proporcje (nie rozciągaj)

            // Reset pozycji
            // carImageView.setTranslateX(0);

        } catch (Exception e) {
            System.err.println("Błąd ładowania obrazka: " + e.getMessage());
        }

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

    @FXML
    private void Press(ActionEvent actionEvent)
    {
        // Musisz mieć getter getSprzeglo() w klasie Samochod!
        // Oraz metodę wcisnij() w klasie Sprzeglo
        if(this.currentCar.getSprzeglo() != null) {
            this.currentCar.getSprzeglo().wcisnij();
            refresh();
        }
    }

    @FXML
    private void EaseDown(ActionEvent actionEvent)
    {
        if(this.currentCar.getSprzeglo() != null) {
            this.currentCar.getSprzeglo().zwolnij();
            refresh();
        }
    }

    @FXML
    private void SpeedUp(ActionEvent actionEvent)
    {
        if (currentCar == null ) return;

        if (currentCar.czyWlaczony())
        {
            if (currentCar.getSilnik() != null)
            {
                currentCar.getSilnik().zwiekszObroty(400);
                System.out.println("Dodano gazu");
            }
        } else
        {
            System.out.println("Najpierw włącz silnik");
        }

        refresh();
    }

    @FXML
    private void SlowDown(ActionEvent actionEvent)
    {
        if (currentCar.getSilnik() != null) { currentCar.getSilnik().zmniejszObroty(400); }
        System.out.println("Odjęto gazu");
        refresh();
    }

    private void refresh() {

        if (currentCar == null)
        {
            producentField.setText("");
            producentField.setText("");
            modelField.setText("");
            regField.setText("");
            wagaField.setText("");
            predkoscField.setText("");
            nazwaSkrzyniaField.setText("");
            biegTextField.setText("");
            obrotyField.setText("");
            sprzegloStanField.setText("");
            return;

        }

        // Jeśli auto wybrane -> wyświetl dane
        producentField.setText(currentCar.getProducent());
        modelField.setText(currentCar.getModel());
        regField.setText(currentCar.getReg());
        wagaField.setText(String.valueOf(currentCar.getWaga()));
        predkoscField.setText(String.valueOf(currentCar.getSpeed()));

        // Komponenty
        if (currentCar.getSkrzynia() != null)
        {
            nazwaSkrzyniaField.setText(currentCar.getSkrzynia().getModel());
            biegTextField.setText(String.valueOf(currentCar.getAktualnyBieg()));
        }

        if (currentCar.getSilnik() != null)
        {
            // Upewnij się, że masz metodę getObroty() w Silniku
            obrotyField.setText(String.valueOf(currentCar.getSilnik().getObroty()));
        }

        if (currentCar.getSprzeglo() != null)
        {
            String stan = currentCar.getIsSprzegloPressed() ? "Wciśnięte" : "Zwolnione";
            sprzegloStanField.setText(stan);
        }
    }

    private void startTimer()
    {
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now)
            {
                // Logika ruchu
                // wywołanie metody 60 hz
                if (currentCar != null)
                {
                    // Pobranie prędkości
                    double speed = currentCar.getSpeed();
                    // Mały czynnik ruchu, aby auto nie poleciało w kosmos
                    double moveFactor = 0.05;
                    // getTranslateX aktualna pozycja
                    double newPosition = carImageView.getTranslateX() + (speed * moveFactor);
                    // Ustawienie pozycji
                    carImageView.setTranslateX(newPosition);

                    // Jeśli auto wyjedzie poza mapę
                    if (carImageView.getTranslateX() > 800) { carImageView.setTranslateX(-100); }
                }
            }
        };

        this.timer.start();

    }

}
