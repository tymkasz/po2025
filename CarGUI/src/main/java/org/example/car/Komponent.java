package org.example.car;

/**
 * Klasa bazowa dla wszystkich części samochodu (Silnik, Skrzynia, etc.).
 * Przechowuje wspólne cechy identyfikacyjne.
 **/
public abstract class Komponent {
    private final String producent;
    private final String model;

    /**
     * Konstruktor bazowy.
     *
     * @param producent Nazwa producenta części
     * @param model     Nazwa modelu części
     **/
    public Komponent(String producent, String model) {
        this.producent = producent;
        this.model = model;
    }

    public String getProducent() {
        return producent;
    }
    public String getModel() {
        return model;
    }
    @Override
    public String toString() { return producent + " " + model; }
}
