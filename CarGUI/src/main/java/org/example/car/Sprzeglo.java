package org.example.car;

public class Sprzeglo extends Komponent
{
    private boolean stanSprzegla;

    public Sprzeglo(String producent, String model)
    {
        super(producent, model);
    }

    public void wcisnij()
    {
        this.stanSprzegla = true;
        System.out.println("Sprzęgło wciśnięte");
    }

    public void zwolnij()
    {
        this.stanSprzegla = false;
        System.out.println("Sprzęgło zwolnione");
    }

    public boolean isPressed()
    {
        return this.stanSprzegla;
    }

}
