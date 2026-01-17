package org.example.car;

public class Pozycja
{
    double x;
    double y;

    // Przeciążenie -> dwa konstruktory (obiekty) w zależności od potrzeb użytkownika
    // Konstruktor domyslny -> domyślna pozycja x=0.0 i y=0.0
    public Pozycja()
    {
        this.x = 0.0;
        this.y = 0.0;
    }

    // Konstruktor z wstępnym położeniem podanym przez użytkownika
    public Pozycja(int startX, int startY)
    {
        this.x = startX;
        this.y = startY;
    }

    // Modyfikacja położenia
    public void updatePosition(double deltaX, double deltaY)
    {
        this.x += deltaX;
        this.y += deltaY;
    }

    // Uzyskanie położenia
    public String getPosition()
    {
        return String.format("x: " + this.x + ", y: " + this.y);
    }

}
