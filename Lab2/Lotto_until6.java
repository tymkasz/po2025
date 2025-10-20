package Lab2;
import java.util.Random;
import java.util.HashSet;

public class Lotto_until6 {
    public static void mian(String[] args){
        // HashSet - no duplicates
        HashSet<Integer> inputNumbers = new HashSet<Integer>();
        Random generator = new Random();

        final int maxRange = 49;
        final int maxAmount = 6;

        if (args.length != 6){
            System.out.println("You must type " + maxAmount + " numbers!");
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
        System.out.println();
        System.out.println("Lotto simulation started.");

        // variable to store how many draws were done
        long drawCount = 0;
        // flag for while loop
        boolean jackpot = false;

        // starting time
        long startTime = System.currentTimeMillis();

        while (!jackpot){
            drawCount++;
            HashSet<Integer> randomNumbers = new HashSet<Integer>();

            while (randomNumbers.size() < maxAmount){
                int randomNumber = generator.nextInt(maxRange) + 1;
                randomNumbers.add(randomNumber);
            }

            if (inputNumbers.equals(randomNumbers)){
                jackpot = true;
            }

            System.out.println("Drawn numbers: " + randomNumbers);
        }

        long endTime = System.currentTimeMillis();

        long totalTime = endTime - startTime;

        System.out.println("Amount of draws: " + drawCount);
        System.out.println("Time of the simulation: " + totalTime + " ms");

    }
}
