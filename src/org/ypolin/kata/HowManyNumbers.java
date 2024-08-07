package org.ypolin.kata;

import java.util.*;

/**
 * https://www.codewars.com/kata/5877e7d568909e5ff90017e6
 *
 * How many numbers III?
 *
 * Description:
 * We want to generate all the numbers of three digits where:
 *
 * the sum of their digits is equal to 10
 * their digits are in increasing order (the numbers may have two or more equal contiguous digits)
 * The numbers that fulfill these constraints are: [118, 127, 136, 145, 226, 235, 244, 334]. There're 8 numbers in total with 118 being the lowest and 334 being the greatest.
 *
 * Task
 * Implement a function which receives two arguments:
 *
 * the sum of digits (sum)
 * the number of digits (count)
 * This function should return three values:
 *
 * the total number of values which have count digits that add up to sum and are in increasing order
 * the lowest such value
 * the greatest such value
 * Note: if there're no values which satisfy these constaints, you should return an empty value (refer to the examples to see what exactly is expected).
 */
public class HowManyNumbers {
    public static List<Long> findAll(final int sumDigits, final int numDigits) {
        int max = Math.round(sumDigits / (float) numDigits);
        if (max > 9) {
            return new ArrayList<>();
        }
        Deque<Integer> deque = new ArrayDeque<>();
        TreeSet<Long> seq = new TreeSet<>();

        for (int i = max; i >= 1; i--) {
            findNext(deque, i, sumDigits, numDigits, seq);
            deque.pollLast();
        }
        return Arrays.asList(Long.valueOf(seq.size()), seq.first(), seq.last());
    }

    private static void findNext(Deque<Integer> deque, int startIndex, int sumDigits, int numDigits, Set<Long> seq) {
        deque.addLast(startIndex);
        sumDigits = sumDigits - startIndex;
        numDigits--;
        if (numDigits == 1) {
            deque.addLast(sumDigits);
            deque.stream().map(String::valueOf).reduce(String::concat).ifPresent(s -> seq.add(Long.valueOf(s)));
            deque.pollLast();
            return;
        }
        int max = sumDigits / numDigits;
        for (int j = startIndex; j <= max; j++) {
            if (Math.round((sumDigits - j) / (float)(numDigits - 1)) <= 9) {
                findNext(deque, j, sumDigits, numDigits, seq);
                deque.pollLast();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(findAll(27, 4));
    }
}