import java.util.Random;
import java.util.ArrayList;

public class Lotto {
    public static void main(String[] args) {
        // creating an array
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        // new instance of class Random
        Random generator = new Random();
        // final - a variable cannot be changed
        final int maxRange = 49;
        final int maxAmount = 6;

        /*
        adding random numbers between 1 and 49 to the array
        in Lotto duplicates are prohibited and max amount of numbers is 6
         */
        while(numbers.size() < maxAmount) {
            int randomNumber = generator.nextInt(maxRange) + 1;
            // storing random number to prevent duplications
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }

        System.out.println("Array of random numbers between 1 and 49: " + numbers);
    }
}
