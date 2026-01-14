package symulator;

public class SkrzyniaBiegow extends Komponent
{
    private int aktualnyBieg;
    private int iloscBiegow;

    public SkrzyniaBiegow(String producent, String model, int iloscBiegow)
    {
        super(producent, model);

        // Ustawienie ilości biegów obiektu na iloscBiegow z konstruktora
        this.iloscBiegow = iloscBiegow;
    }

    public void zwiekszBieg()
    {
        if (this.aktualnyBieg != this.iloscBiegow)
        {
            this.aktualnyBieg++;
        } else
        {
            System.out.println("Nie można zwiększyć biegu");
        }
    }

    public void zmniejszBieg()
    {
        if (this.aktualnyBieg > 0)
        {
            this.aktualnyBieg--;
            if (this.aktualnyBieg == 0)
            {
                System.out.println("Luz");
            }
        } else
        {
            System.out.println("Nie można zmniejszyć biegu");
        }
    }

    public int getAktualnyBieg()
    {
        return this.aktualnyBieg;
    }
}
