package org.example.car;

public abstract class Komponent
{
    protected String producent;
    protected String model;

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
