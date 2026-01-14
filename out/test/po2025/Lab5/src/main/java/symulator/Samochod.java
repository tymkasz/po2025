package symulator;

public class Samochod
{
    private Silnik silnik;
    private SkrzyniaBiegow skrzynia;
    private Pozycja aktualnaPozycja;

    private boolean stanWlaczenia;
    private String nrRejestr;
    private String model;
    private String producent;
    private final int maxSpeed;

    public Samochod(String nrRejestr, String producent, String model, int maxSpeed)
    {
        // Konstruktor osobny do tworzenia testowego silnika i skrzyni
        // Parametry przekazywane są z pierwszego konstruktora
        Silnik silnik = new Silnik("test", "test", 0);
        SkrzyniaBiegow skrzynia = new SkrzyniaBiegow("test", "test", 0);

        // Wywołanie innego konstruktora (przeciążenie)
        this(silnik, skrzynia, nrRejestr, producent, model, maxSpeed);
    }

    public Samochod(Silnik silnik, SkrzyniaBiegow skrzynia, String nrRejestr, String producent, String model, int maxSpeed)
    {
        this.nrRejestr = nrRejestr;
        this.producent = producent;
        this.model = model;
        this.maxSpeed = maxSpeed;

        this.stanWlaczenia = false;

        this.aktualnaPozycja = new Pozycja(1, 1);

        this.silnik = silnik;
        this.skrzynia = skrzynia;
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

}
