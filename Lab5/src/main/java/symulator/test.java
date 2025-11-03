package symulator;

public class test
{
    static void main(String[] args)
    {
        SkrzyniaBiegow skrzynia = new SkrzyniaBiegow("test", "test", 7);
        Silnik silnik = new Silnik("Subaru", "FA20", 4500);
        Samochod car = new Samochod(silnik, skrzynia, "KR4286", "Toyota", "GT86", 400);

        car.wlacz();
        car.zwiekszBieg();
        car.updatePosition();

        car.wylacz();
    }
}
