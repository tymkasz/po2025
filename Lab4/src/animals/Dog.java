package animals;

public class Dog extends Animal
{
    public Dog(String name)
    {
        // Evocation of 'Animal' abstract class
        super(name);
        // Setting amount of legs
        this.legs = 4;
    }

    @Override
    public String getDescription()
    {
        return "This is a dog. Its name is " + this.name + ". It has " + this.legs + " legs.";
    }

    @Override
    public int getLegs()
    {
        return this.legs;
    }

    public void barks()
    {
        System.out.println(this.name + " barks!");
    }

    @Override
    public void makeSound()
    {
        System.out.println("Woooof!");
    }
}
