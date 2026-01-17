package org.example.car;

public class Silnik extends Komponent
{
    private final int maxObroty;
    private int obroty;
    private final int obrotyBegin = 800;

    public Silnik(String producent, String model, int maxObroty)
    {
        super(producent, model);

        // Ustawienie max obrotów na maxObroty wprowadzone w konstruktorze
        this.maxObroty = maxObroty;
        // Ustawienie obrotów na 0
        this.obroty = 0;
    }

    public void uruchom()
    {
        if (this.obroty == 0)
        {
            // Jeśli auto było wyłączone to ustaw na początkowe obroty
            this.obroty = obrotyBegin;
        }
    }

    public void zatrzymaj()
    {
        // Wyzeruj obroty
        this.obroty = 0;
    }

    public int getObroty()
    {
        return this.obroty;
    }

    // Metoda do dodawania gazu
    public void zwiekszObroty(int ilosc)
    {
        if (this.obroty + ilosc <= this.maxObroty)
        {
            this.obroty += ilosc;
        }
        else
        {
            // Ustaw max, jeśli przekroczymy obroty
            this.obroty = this.maxObroty;
            System.out.println("Osiągnięto maksymalne obroty");
        }
    }

    // Metoda ujmowania gazu
    public void zmniejszObroty(int ilosc)
    {
        if (this.obroty - ilosc >= this.obrotyBegin)
        {
            this.obroty -= ilosc;
        }
        else
        {
            // Schodzimy na minimalne obroty (obrotyBegin), aby auto samo nie zgasło
            if (this.obroty > 0)
            {
                this.obroty = this.obrotyBegin;
            }
        }
    }


}
