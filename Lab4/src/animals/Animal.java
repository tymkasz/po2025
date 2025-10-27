package animals;

public abstract class Animal
{
    // Attributes
    protected String name;
    public int legs;

    // Constructor for an animal to set a name
    public Animal(String name)
    {
        this.name = name;
    }
    // Abstract methods
    abstract String getDescription();
    abstract int getLegs();

    public void makeSound()
    {
        System.out.println("Make a sound.");
    }
}

// A class with every method being abstract is called interface
// It does not have any attributes (only final ones)
/*
Example:
java.util.ArrayList
      ^
      |
    interface List -> Collection (also interface) -> Iterable (and another interface)
 */

