package org.example.car;

public class Position {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public double setX(double x) { return x; }
    public double setY(double y) { return y; }
}
