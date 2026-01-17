package org.example.car;

public class test
{
    public static void main(String[] args)
    {
        SkrzyniaBiegow skrzynia = new SkrzyniaBiegow("test", "test", 7);
        Silnik silnik = new Silnik("Subaru", "FA20", 4500);
        Sprzeglo sprzeglo = new Sprzeglo("test", "test");
        Samochod car = new Samochod(silnik, skrzynia, "KR4286", "Toyota", "GT86", 2, 0, sprzeglo);

        car.wlacz();
        car.zwiekszBieg();
        car.updatePosition();

        car.wylacz();
    }
}
