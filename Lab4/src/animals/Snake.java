package animals;

public class Snake extends Animal
{
    public Snake(String name)
    {
        super(name);
        this.legs = 0;
    }

    @Override
    public String getDescription()
    {
        return "This is a snake. Its name is " + this.name + " and, of course, it has " + this.legs + " legs.";
    }

    @Override
    public int getLegs()
    {
        return this.legs;
    }

    public void Hissing()
    {
        System.out.println(this.name + " hisses.");
    }

    @Override
    public void makeSound()
    {
        System.out.println("Sssssssss...");
    }
}
