package animals;
// extends -> dziedziczenie
// implements -> dziedziczenie (interfejsu)
public class Parrot extends Animal
{
    public Parrot(String name)
    {
        // super()
        // In this example it is not only this.name = name
        // It evokes whole constructor from 'Animal'
        super(name);

        // Setting legs inherited from 'Animal' abstract class
        this.legs = 2;
    }

    @Override
    public String getDescription()
    {
        return "This is a parrot. Its name is " + this.name + " and it has " + this.legs + " legs.";
    }

    public void fly()
    {
        System.out.println(this.name + " flies!");
    }

    @Override
    public int getLegs()
    {
        return this.legs;
    }

    @Override
    public void makeSound()
    {
        System.out.println("Trrrrrr");
    }
}
