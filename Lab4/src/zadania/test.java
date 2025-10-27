package zadania;

import animals.Dog;
import animals.Parrot;
import animals.Snake;

public class test
{
    public static void main(String[] args)
    {
        Zoo zoo = new Zoo();
        System.out.println("A zoo is created and filled with animals.");

        int legs = zoo.totalLegs();
        System.out.println("Total amount of legs: " + legs);
        System.out.println();

        // System.out.println("My zoo: " + zoo);
        Dog dog = new Dog("Luna");
        Parrot parrot = new Parrot("Krysia");
        Snake snake = new Snake("Szatan");

        System.out.println(parrot.getDescription());
        parrot.fly();
        parrot.makeSound();
        System.out.println("Amount of legs: " + parrot.getLegs());

        System.out.println();

        System.out.println(dog.getDescription());
        dog.barks();
        dog.makeSound();
        System.out.println("Amount of legs: " + dog.getLegs());
        int dogLegs = dog.getLegs();
        System.out.println();

        System.out.println(snake.getDescription());
        snake.Hissing();
        snake.makeSound();
        System.out.println("Amount of legs: " + snake.getLegs());

        System.out.println();
    }
}
