package org.example.car;

public class Sprzeglo extends Komponent
{
    private boolean stanSprzegla;
    // Nowe pola - estetyka
    private String nazwaKomponentu = "Sportowe";
    private double cena = 2500.0;
    private double waga = 15.0;

    // Gettery
    public String getNazwaKomponentu() { return nazwaKomponentu; }
    public double getCena() { return cena; }
    public double getWaga() { return waga; }

    public Sprzeglo(String producent, String model)
    {
        // super() -> Wywołanie Komponent -> rodzica
        super(producent, model);
    }

    public void wcisnij()
    {
        this.stanSprzegla = true;
        System.out.println("Sprzęgło wciśnięte");
    }

    public void zwolnij()
    {
        this.stanSprzegla = false;
        System.out.println("Sprzęgło zwolnione");
    }

    public boolean isPressed()
    {
        return this.stanSprzegla;
    }

}
