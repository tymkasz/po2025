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

    // Sprzęgło
    @FXML private Button EaseDown;
    @FXML private Button Press;
    @FXML private TextField nazwaSprzegloField; // fx:id="nazwaSprzegloField"
    @FXML private TextField cenaSprzegloField;  // fx:id="cenaSprzegloField"
    @FXML private TextField wagaSprzegloField;  // fx:id="wagaSprzegloField"
    @FXML private TextField sprzegloStanField;
    // Silnik
    @FXML private Button SpeedUp;
    @FXML private Button SlowDown;
    @FXML private TextField nazwaSilnikField;  // Sprawdź w SceneBuilderze czy id to fx:id="nazwaSilnikField"
    @FXML private TextField cenaSilnikField;   // fx:id="cenaSilnikField"
    @FXML private TextField wagaSilnikField;   // fx:id="wagaSilnikField"
    @FXML private TextField obrotyField;
    // Skrzynia biegów
    @FXML private Button GearUp;
    @FXML private Button GearDown;
    @FXML private TextField nazwaSkrzyniaField;
    @FXML private TextField cenaSkrzyniaField;
    @FXML private TextField wagaSkrzyniaField;
    @FXML private TextField biegTextField;
    // Samochód
    @FXML private Button TurnOffButton;
    @FXML private Button StartButton;
    @FXML private ComboBox<Samochod> carSelectorCombo;
    @FXML private Button AddingNew;
    @FXML private Button DeletingCar;
    @FXML public TextField producentField;
    @FXML private TextField modelField;
    @FXML private TextField regField;
    @FXML private TextField wagaField;
    @FXML private TextField predkoscField;
    @FXML private ImageView carImageView;
    @FXML private Samochod currentCar;

    private AnimationTimer timer;
    // Flagi sterowania
    private boolean isUpPressed = false;
    private boolean isDownPressed = false;
    private boolean isRightPressed = false; // Gaz
    private boolean isLeftPressed = false;  // Hamulec
    // Fizyczna prędkość auta
    private double currentVelocity = 0;

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

        // Obsługa klawiszy strzałek
        // Czekamy, aż scena zostanie załadowana, żeby podpiąć klawiaturę
        carImageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null)
            {
                newScene.setOnKeyPressed(event -> {
                    switch (event.getCode())
                    {
                        case UP: isUpPressed = true; break;
                        case DOWN: isDownPressed = true; break;
                        case RIGHT: isRightPressed = true; break;
                        case LEFT: isLeftPressed = true; break;
                    }
                });

                newScene.setOnKeyReleased(event -> {
                    switch (event.getCode())
                    {
                        case UP: isUpPressed = false; break;
                        case DOWN: isDownPressed = false; break;
                        case RIGHT: isRightPressed = false; break;
                        case LEFT: isLeftPressed = false; break;
                    }
                });

                // Główny panel aplikacji
                Parent root = newScene.getRoot();
                // Focus na roocie
                root.setFocusTraversable(true);
                // Gdy kliknięcie w tło -> focus na tło (zabranie z przycisków)
                root.setOnMouseClicked(event -> root.requestFocus());
                // Analogicznie dla obrazka auta
                carImageView.setOnMouseClicked(event -> root.requestFocus());

            }
        });

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
            carImageView.setTranslateX(0);
            carImageView.setTranslateY(0);

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
            nazwaSkrzyniaField.setText(currentCar.getSkrzynia().getNazwaKomponentu());
            cenaSkrzyniaField.setText(String.valueOf(currentCar.getSkrzynia().getCena()));
            wagaSkrzyniaField.setText(String.valueOf(currentCar.getSkrzynia().getWaga()));
        }

        if (currentCar.getSilnik() != null)
        {
            // Upewnij się, że masz metodę getObroty() w Silniku
            obrotyField.setText(String.valueOf(currentCar.getSilnik().getObroty()));

            if (nazwaSilnikField != null)
            {
                nazwaSilnikField.setText(currentCar.getSilnik().getNazwaKomponentu());
                cenaSilnikField.setText(String.valueOf(currentCar.getSilnik().getCena()));
                wagaSilnikField.setText(String.valueOf(currentCar.getSilnik().getWaga()));
            }
        }

        if (currentCar.getSprzeglo() != null)
        {
            String stan = currentCar.getIsSprzegloPressed() ? "Wciśnięte" : "Zwolnione";
            sprzegloStanField.setText(stan);

            if (nazwaSprzegloField != null) {
                nazwaSprzegloField.setText(currentCar.getSprzeglo().getNazwaKomponentu());
                cenaSprzegloField.setText(String.valueOf(currentCar.getSprzeglo().getCena()));
                wagaSprzegloField.setText(String.valueOf(currentCar.getSprzeglo().getWaga()));
            }
        }
    }

    private void startTimer() {
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Jeśli nie ma auta, nic nie rób
                if (currentCar == null) return;

                // Oś Y (Góra / Dół) - Zmiana pasa ruchu
                // Działa zawsze, niezależnie od tego czy silnik włączony (można przepychać auto)
                if (isUpPressed) {
                    carImageView.setTranslateY(carImageView.getTranslateY() - 5);
                }
                if (isDownPressed) {
                    carImageView.setTranslateY(carImageView.getTranslateY() + 5);
                }

                // Oś X (Gaz / Hamulec)
                if (isRightPressed && currentCar.czyWlaczony()) {
                    // GAZ: Jeśli sprzęgło puszczone -> rosną obroty
                    if (!currentCar.getIsSprzegloPressed()) {
                        // Dodajemy mniej niż przyciskiem
                        int gear = currentCar.getAktualnyBieg();
                        if (gear == 0) gear = 1;
                        // Baza przyspieszenia to 15
                        // Dzielimy przez numer biegu
                        // Na 1 szybciej zbiera obroty niż np. na 5
                        int acceleration = 15/gear;
                        currentCar.getSilnik().zwiekszObroty(acceleration);
                    } else {
                        // Na sprzęgle "gazujemy" na pusto (obroty rosną szybciej)
                        currentCar.getSilnik().zwiekszObroty(100);
                    }

                } else if (isLeftPressed) // HAMULEC
                {
                    currentCar.hamuj();

                    // Jeśli wciśnięte sprzęgło
                    if (currentCar.getIsSprzegloPressed())
                    {
                        currentVelocity -= 5;
                        if (currentVelocity < 0) currentVelocity = 0;
                    }
                } else
                {
                    // PUSZCZENIE GAZU: Silnik powoli zwalnia (opór)
                    currentCar.getSilnik().zmniejszObroty(10);
                }

                // Symulacja zgaśnięcia silnika
                // Obroty spadną poniżej minimalnych na biegu i bez sprzęgła -> silnik gaśnie
                if (currentCar.czyWlaczony()
                    && currentCar.getAktualnyBieg() > 0
                    && !currentCar.getIsSprzegloPressed()
                    && currentCar.getSilnik().getObroty() < 300)
                {
                    currentCar.wylacz();
                    System.out.println("Silnik zgasł. Zbyt małe obroty na biegu");
                }

                // Pobranie aktualnej prędkości (która wynika z obrotów ustawionych wyżej)
                double engineTargetSpeed = currentCar.getSpeed();

                if (!currentCar.getIsSprzegloPressed())
                {
                    // Sprzęgło PUSZCZONE (zwolnione):
                    // Koła kręcą się tak, jak silnik (auto przyspiesza lub hamuje silnikiem)
                    currentVelocity = engineTargetSpeed;
                } else
                {
                    // Sprzęgło WCIŚNIĘTE:
                    // Rozłączamy silnik! Auto toczy się siłą rozpędu, ale powoli zwalnia (tarcie)
                    currentVelocity *= 0.998; // Zwalniamy o 1% co klatkę

                    if (currentVelocity < 0.1) currentVelocity = 0; // Zatrzymanie
                }

                // Przesunięcie obrazka
                double moveFactor = 0.05;
                carImageView.setTranslateX(carImageView.getTranslateX() + (currentVelocity * moveFactor));

                // Pętla mapy (teleportacja jak wyjedzie za ekran)
                if (carImageView.getTranslateX() > 800) {
                    carImageView.setTranslateX(-150);
                }

                // Aktualizacja interfejsu
                // Bez tego liczby na ekranie by stały w miejscu!
                refresh();
                // NADPISANIE pola prędkości (żeby pokazywało prędkość kół, a nie silnika)
                predkoscField.setText(String.valueOf(Math.round(currentVelocity)));

                // Jeśli klawisze nie działają, to wymusza działanie na obrazku
                if (isUpPressed || isDownPressed || isRightPressed || isLeftPressed) {
                    carImageView.requestFocus();
                }
            }
        };
        this.timer.start();
    }

}
