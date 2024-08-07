package org.ypolin.kata;

import java.util.*;
import java.util.stream.Collectors;


public class CarMileage {
    public static int isInteresting(int number, int[] awesomePhrases) {
        if (overallValidate(number, awesomePhrases)) return 2;

        if (overallValidate(number + 1, awesomePhrases) || overallValidate(number + 2, awesomePhrases))
            return 1;

        return 0;
    }

    private static boolean overallValidate(int number, int[] awesomePhrases) {
        return validateDigitsCount(number) && (validatePhrases(number, awesomePhrases) || validateZeros(number) ||
                validateMonotonous(number) || incrementing(number) ||
                decrementing(number) || validatePalindrome(number));
    }

    private static boolean validateDigitsCount(int number) {
        if (number >= 100 && number < 1000000000) {
            return true;
        }
        return false;
    }

    private static boolean validatePhrases(int number, int[] awesomePhrases) {
        List<Integer> phrases = Arrays.stream(awesomePhrases).boxed().collect(Collectors.toList());
        if (phrases.contains(number)) {
            return true;
        }
        return false;
    }

    private static boolean validatePalindrome(int number) {
        String strNumber = String.valueOf(number);
        StringBuilder sb = new StringBuilder(strNumber);
        if (sb.toString().equals(sb.reverse().toString())) {
            return true;
        }
        return false;
    }

    private static boolean validateMonotonous(int number) {
        String strNumber = String.valueOf(number);

        if (strNumber.chars().mapToObj(ch -> (char) ch).allMatch(character -> character.equals(strNumber.charAt(0)))) {
            return true;
        }
        return false;
    }

    private static boolean validateZeros(int number) {
        String strNumber = String.valueOf(number);
        if (strNumber.substring(1, strNumber.length()).matches("0+")) {
            return true;
        }
        return false;
    }

    private static boolean incrementing(int number) {
        String strNumber = String.valueOf(number);
        for (int i = strNumber.length() - 1; i > 0; i--) {
            int digit = Character.getNumericValue(strNumber.charAt(i));
            int prDigit = Character.getNumericValue(strNumber.charAt(i - 1));
            if (i == strNumber.length() - 1 && digit == 0 && prDigit == 9) {
                continue;
            }
            if (digit - 1 != prDigit) {
                return false;
            }
        }
        return true;
    }

    private static boolean decrementing(int number) {
        String strNumber = String.valueOf(number);
        for (int i = strNumber.length() - 1; i > 0; i--) {
            int digit = Character.getNumericValue(strNumber.charAt(i));
            int prDigit = Character.getNumericValue(strNumber.charAt(i - 1));
            if (digit + 1 != prDigit) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        System.out.println(CarMileage.isInteresting(109, new int[]{}));
    }
}
