package Lab2;
import java.util.Random;
import java.util.ArrayList;

public class Lotto_argInput {
    public static void main(String[] args) {
        // creating an array
        ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
        ArrayList<Integer> inputNumbers = new ArrayList<Integer>();

        // new instance of class Random
        Random generator = new Random();

        // final - a variable cannot be changed
        final int maxRange = 49;
        final int maxAmount = 6;

        if (args.length != 6) {
            System.out.println("You must type six numbers!");
            return;
        }
        for (int i = 0; i < 6; i++) {
            while (i < 6) {
                int input_num = Integer.parseInt(args[i]);
                if (input_num > 49 || input_num < 1 || !inputNumbers.add(input_num)) {
                    System.out.println("Try again!");
                    return;
                } else {
                    inputNumbers.add(input_num);
                    i++;
                }
            }
        }

        System.out.println("Your types: " + inputNumbers);

        /*
        adding random numbers between 1 and 49 to the array
        in Lotto duplicates are prohibited and max amount of numbers is 6
         */
        while(randomNumbers.size() < maxAmount) {
            int randomNumber = generator.nextInt(maxRange) + 1;
            // storing random number to prevent duplications
            if (!randomNumbers.contains(randomNumber)) {
                randomNumbers.add(randomNumber);
            }
        }

        System.out.println("Array of six random numbers between 1 and 49: " + randomNumbers);

        ArrayList<Integer> matchedNumbers = new ArrayList<Integer>(inputNumbers);
        matchedNumbers.retainAll(randomNumbers);
        int numbersCorrect = matchedNumbers.size();

        System.out.println("Matched numbers: " + numbersCorrect);

    }
}