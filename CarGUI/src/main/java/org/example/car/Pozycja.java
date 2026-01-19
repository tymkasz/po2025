package org.example.car;

public class Pozycja {
    private double x;
    private double y;

    // Konstruktor domyślny
    public Pozycja() {
        this(0.0, 0.0);
    }

    // Konstruktor z wstępnym położeniem podanym przez użytkownika
    public Pozycja(double startX, double startY) {
        this.x = startX;
        this.y = startY;
    }

    // Modyfikacja położenia
    public void updatePosition(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    // Gettery
    public double getX() { return x; }
    public double getY() { return y; }

}
