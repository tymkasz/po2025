package org.example.car;

public class Samochod
{
    private final Silnik silnik;
    private final SkrzyniaBiegow skrzynia;
    private final Sprzeglo sprzeglo;
    private final Pozycja aktualnaPozycja;

    private boolean stanWlaczenia;
    private final String nrRejestr;
    private final String model;
    private final String producent;
    private final int maxSpeed;
    private final int waga;

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

    // Metoda do włączenia auta
    public void wlacz()
    {
        this.silnik.uruchom();
        this.stanWlaczenia = true;
        System.out.println("Samochód włączony");
    }

    // Metoda do wyłączenia auta
    public void wylacz()
    {
        this.silnik.zatrzymaj();

        while (this.getAktualnyBieg() > 0)
        {
            this.skrzynia.zmniejszBieg();
        }

        this.stanWlaczenia = false;
        System.out.println("Samochód wyłączony");
    }

    // Metoda do zwiększenia biegu
    public void zwiekszBieg()
    {
        // Zabezpieczenie sprzęgłem
        if (this.getIsSprzegloPressed())
        {
            this.skrzynia.zwiekszBieg();
        } else
        {
            System.out.println("Wciśnij sprzęgło, aby zmienić bieg!");
        }
    }

    // Metoda do zmniejszenia biegu
    public void zmniejszBieg()
    {
        // Zabezpieczenie sprzęgłem
        if (this.getIsSprzegloPressed())
        {
            this.skrzynia.zmniejszBieg();
        } else
        {
            System.out.println("Wciśnij sprzęgło, aby zmienić bieg!");
        }
    }

    // Metoda do obliczenia prędkości
    public int getSpeed()
    {
        // Zabezpieczenie przed jazdą wyłączonym autem
        if (!this.stanWlaczenia) return 0;

        // Dynamiczne obliczanie prędkości
        // Auto jest na luzie
        if (this.getAktualnyBieg() == 0) return 0;

        // Wzór Prędkość = (Obroty * Bieg)/Stała -> Stała "na oko"
        int calculateSpeed = (this.silnik.getObroty() * this.getAktualnyBieg())/80;

        // Jeśli obliczona prędkość > maxSpeed -> ustaw maxSpeed
        if (calculateSpeed >= this.maxSpeed) return this.maxSpeed;

        return calculateSpeed;
    }

    // Gettery
    public void updatePosition() { this.aktualnaPozycja.updatePosition(9, 5); }
    public String toString() { return producent + " " + model + " (" + nrRejestr + ")"; }
    public String getProducent() { return this.producent; }
    public String getReg() { return this.nrRejestr; }
    public int getWaga() { return this.waga; }
    public int getAktualnyBieg() { return this.skrzynia.getAktualnyBieg(); }
    public String getModel() { return this.model; }
    public Silnik getSilnik() { return this.silnik; }
    public SkrzyniaBiegow getSkrzynia() { return this.skrzynia; }
    public boolean getIsSprzegloPressed() { return this.sprzeglo.isPressed(); }
    public Sprzeglo getSprzeglo() { return this.sprzeglo; }
    public boolean czyWlaczony() { return this.stanWlaczenia; }

}
