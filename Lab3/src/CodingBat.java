package Lab3;

public class CodingBat {
    static Integer sumDouble(int a, int b){
        int sum = a + b;

        if (a == b){
            sum = 2*sum;
        }

        return sum;
    }

    static Boolean monkeyTrouble(boolean aSmile, boolean bSmile){
        if ((aSmile && bSmile) || (!aSmile && !bSmile)){
            return true;
        }
        return false;
    }

    static Boolean lucky13(int[] nums) {
        for (int num : nums){
            if (num == 1 || num == 3){
                return false;
            }
        }

        return true;
    }

    static String withoutEnd(String str) {
        return str.substring(1, str.length() - 1);
    }

    public static void main(String[] args) {
        System.out.println("sumDouble: " + sumDouble(2,4));
        System.out.println("monkeyTrouble: " + monkeyTrouble(false, true));
        System.out.println("lucky13: " + lucky13(new int[] {1, 4, 8}));
        System.out.println("withoutEnd: " + withoutEnd("Grandma"));

    }

}

