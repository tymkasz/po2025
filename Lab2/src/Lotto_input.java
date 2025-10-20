import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class Lotto_input {
    public static void main(String[] args) {
        // creating an array
        ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
        ArrayList<Integer> inputNumbers = new ArrayList<Integer>();

        // new instance of class Random
        Random generator = new Random();
        Scanner input = new Scanner(System.in);

        // final - a variable cannot be changed
        final int maxRange = 49;
        final int maxAmount = 6;

        System.out.println("Type your six numbers (max 49): ");
        int i = 0;
        while (i < 6) {
            int input_num = input.nextInt();
            if (input_num > 49 || input_num < 1) {
                i = 0;
                inputNumbers.clear();
                System.out.println("Try again!");
            } else {
                inputNumbers.add(input_num);
                i++;
            }
        }

        System.out.println("Input numbers: " + inputNumbers);
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

        // matchedNumbers = inputNumbers -> for retainAll method to work
        ArrayList<Integer> matchedNumbers = new ArrayList<Integer>(inputNumbers);
        // retainAll - store the comparison output
        matchedNumbers.retainAll(randomNumbers);
        int amountCorrect = matchedNumbers.size();
        System.out.println("Amount of matched numbers: " + amountCorrect);



    }
}