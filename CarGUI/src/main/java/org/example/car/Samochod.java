package org.example.car;

public class Samochod
{
    private Silnik silnik;
    private SkrzyniaBiegow skrzynia;
    private Sprzeglo sprzeglo;
    private Pozycja aktualnaPozycja;

    private boolean stanWlaczenia;
    private final String nrRejestr;
    private String model;
    private String producent;
    private final int maxSpeed;
    private int waga;

    public Samochod(String nrRejestr, String producent, String model, int maxSpeed, int waga)
    {
        this(
                new Silnik("Toyota", "Boxer", 7000),     // silnik
                new SkrzyniaBiegow("Aisin", "Manual", 7), // skrzynia
                nrRejestr,
                producent,
                model,
                maxSpeed,
                waga,
                new Sprzeglo("Exedy", "Stage1")          // sprzeglo
        );
    }

    public Samochod(Silnik silnik, SkrzyniaBiegow skrzynia, String nrRejestr, String producent, String model, int maxSpeed, int waga, Sprzeglo sprzeglo)
    {
        this.nrRejestr = nrRejestr;
        this.producent = producent;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.waga = waga;

        this.stanWlaczenia = false;

        this.aktualnaPozycja = new Pozycja(1, 1);

        this.silnik = silnik;
        this.skrzynia = skrzynia;
        this.sprzeglo = sprzeglo;
    }

    public void wlacz()
    {
        this.silnik.uruchom();
        this.stanWlaczenia = true;
        System.out.println("Samochód włączony");
    }

    public void wylacz()
    {
        this.silnik.zatrzymaj();

        while (this.skrzynia.getAktualnyBieg() > 0)
        {
            this.skrzynia.zmniejszBieg();
        }

        this.stanWlaczenia = false;
        System.out.println("Samochód wyłączony");
    }

    public void zwiekszBieg()
    {
        this.skrzynia.zwiekszBieg();
    }

    public void zmniejszBieg()
    {
        this.skrzynia.zmniejszBieg();
    }

    public void updatePosition()
    {
        this.aktualnaPozycja.updatePosition(9, 5);
    }

    public String toString(){
        return producent + " " + model + " (" + nrRejestr + ")";
    }

    public int getBieg(){
        return this.skrzynia.getAktualnyBieg();
    }

    public String getProducent()
    {
        return this.producent;
    }

    public String getReg(){
        return this.nrRejestr;
    }

    public int getWaga(){
        return this.waga;
    }

    public int getSpeed(){
        return this.speed;
    }

    public String getModel(){
        return this.model;
    }

    public Silnik getSilnik()
    {
        return this.silnik;
    }

    public SkrzyniaBiegow getSkrzynia()
    {
        return this.skrzynia;
    }

    public boolean getIsSprzegloPressed()
    {
        return this.sprzeglo.isPressed();
    }

    public Sprzeglo getSprzeglo()
    {
        return this.sprzeglo;
    }

}
