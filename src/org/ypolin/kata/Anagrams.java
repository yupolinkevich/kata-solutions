package org.ypolin.kata;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * https://www.codewars.com/kata/53e57dada0cb0400ba000688/java
 *
 * Alphabetic Anagrams
 *
 * Consider a "word" as any sequence of capital letters A-Z (not limited to just "dictionary words"). For any word with at least two different letters, there are other words composed of the same letters but in a different order (for instance, STATIONARILY/ANTIROYALIST, which happen to both be dictionary words; for our purposes "AAIILNORSTTY" is also a "word" composed of the same letters as these two).
 * We can then assign a number to every word, based on where it falls in an alphabetically sorted list of all words made up of the same group of letters. One way to do this would be to generate the entire list of words and find the desired one, but this would be slow if the word is long.
 * Given a word, return its number. Your function should be able to accept any word 25 letters or less in length (possibly with some letters repeated), and take no more than 500 milliseconds to run. To compare, when the solution code runs the 27 test cases in JS, it takes 101ms.
 * For very large words, you'll run into number precision issues in JS (if the word's position is greater than 2^53). For the JS tests with large positions, there's some leeway (.000000001%). If you feel like you're getting it right for the smaller ranks, and only failing by rounding on the larger, submit a couple more times and see if it takes.
 * <p>
 * Python, Java and Haskell have arbitrary integer precision, so you must be precise in those languages (unless someone corrects me).
 * <p>
 * C# is using a long, which may not have the best precision, but the tests are locked so we can't change it.
 * <p>
 * Sample words, with their rank:
 * ABAB = 2
 * AAAB = 1
 * BAAA = 4
 * QUESTION = 24572
 * BOOKKEEPER = 10743
 */
public class Anagrams {
    public static BigInteger listPosition(String word) {

        char[] letters = word.toCharArray();
        Map<Character, Long> lettersTypes = word.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        char[] sorted = Arrays.copyOf(letters, letters.length);
        Arrays.sort(sorted);
        StringBuilder sortedStr = new StringBuilder(String.valueOf(sorted));
        int remaining = word.length();
        BigInteger res = BigInteger.ONE;
        for (char l : letters) {
            remaining--;
            int pos = sortedStr.indexOf(String.valueOf(l));
            Collection<Long> types = lettersTypes.values();
            res = res.add(combinationWithRepeat(remaining, types).multiply(new BigInteger(String.valueOf(pos))));
            sortedStr = sortedStr.deleteCharAt(pos);
            Long charCount = lettersTypes.get(l);
            if (charCount == 1) {
                lettersTypes.remove(l);
            } else {
                lettersTypes.put(l, charCount - 1);
            }
        }
        return res;
    }

    public static BigInteger factorial(int n) {
        BigInteger factorial = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial;
    }

    public static BigInteger combinationWithRepeat(int n, Collection<Long> types) {
        BigInteger denominator = BigInteger.ONE;
        for (Long type : types) {
            denominator = denominator.multiply(factorial(type.intValue()));
        }
        return factorial(n).divide(denominator);
    }

    public static void main(String[] args) {
        System.out.println(listPosition("BOOKKEEPER"));
    }
}
