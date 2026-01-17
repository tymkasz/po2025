package org.example.car;

// Abstract -> nie mogę stworzyć instancji klasy Komponent -> służy ona jedynie dziedziczeniu
// DRY - Don't Repeat Yourself
public abstract class Komponent
{
    protected String producent;
    protected String model;

    // Konstruktor -> wymuszenie dziedziczenia producenta i modelu
    public Komponent(String producent, String model)
    {
        this.producent = producent;
        this.model = model;
    }
    public String getProducent()
    {
        return this.producent;
    }

    public String getModel()
    {
        return this.model;
    }
}
