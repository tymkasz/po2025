package symulator;

public class Silnik extends Komponent
{
    private int maxObroty;
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


}
