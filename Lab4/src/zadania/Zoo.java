package zadania;

import animals.Animal;
import animals.Parrot;
import animals.Dog;
import animals.Snake;

import java.util.Random;

public class Zoo
{
    Animal[] animals = new Animal[100];

    Random animalsGenerator = new Random();

    private void fillZoo()
    {
        for (int i = 0; i < animals.length; i++)
        {
            // Parrot - 0; Dog - 1; Snake - 2
            int animalType = animalsGenerator.nextInt(3);

            switch(animalType)
            {
                case 0:
                    animals[i] = new Parrot("Parrot " + i);
                    break;
                case 1:
                    animals[i] = new Dog("Dog " + i);
                    break;
                case 2:
                    animals[i] = new Snake("Snake " + i);
                    break;
            }
            animalType = 0;
        }
    }
    // Constructor -> any instance of Zoo will fill the zoo
    public Zoo()
    {
        this.fillZoo();
    }
    // Method for calculating total legs in the zoo
    public int totalLegs()
    {
        int totalLegs = 0;

        for (Animal animal : animals)
        {
            totalLegs += animal.legs;
        }

        return totalLegs;
    }

}

