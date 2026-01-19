package org.example.car;

public class SkrzyniaBiegow extends Komponent
{
    private int aktualnyBieg;
    private final int iloscBiegow;
    // Nowe pola - estetyka
    private String nazwaKomponentu = "Manual 6-bieg";
    private double cena = 6000.0;
    private double waga = 60.0;

    // Gettery
    public String getNazwaKomponentu() { return this.nazwaKomponentu; }
    public double getCena() { return this.cena; }
    public double getWaga() { return this.waga; }
    public int getAktualnyBieg() { return this.aktualnyBieg; }

    public SkrzyniaBiegow(String producent, String model, int iloscBiegow)
    {
        // super() -> Wywołanie Komponent -> rodzica
        super(producent, model);

        // Ustawienie ilości biegów obiektu na iloscBiegow z konstruktora
        this.iloscBiegow = iloscBiegow;
    }

    public void zwiekszBieg()
    {
        if (this.aktualnyBieg == -1)
        {
            this.aktualnyBieg = 0;
            System.out.println("Luz (N)");
        } else if (this.aktualnyBieg < this.iloscBiegow)
        {
            this.aktualnyBieg++;
        } else
        {
            System.out.println("Nie można zwiększyć biegu (MAX)");
        }

    }

    public void zmniejszBieg()
    {
        if (this.aktualnyBieg == 0)
        {
            this.aktualnyBieg = -1;
            System.out.println("Bieg wsteczny (R)");
        } else if (this.aktualnyBieg > 0)
        {
            this.aktualnyBieg--;
            if (this.aktualnyBieg == 0)
            {
                System.out.println("Luz (N)");
            }
        }
    }

}
