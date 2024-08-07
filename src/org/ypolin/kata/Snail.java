package org.ypolin.kata;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * https://www.codewars.com/kata/521c2db8ddc89b9b7a0000c1
 * <p>
 * Snail Sort
 * <p>
 * Given an n x n array, return the array elements arranged from outermost elements to the middle element, traveling clockwise.
 * <p>
 * array = [[1,2,3],
 * [4,5,6],
 * [7,8,9]]
 * snail(array) #=> [1,2,3,6,9,8,7,4,5]
 * For better understanding, please follow the numbers of the next array consecutively:
 * <p>
 * array = [[1,2,3],
 * [8,9,4],
 * [7,6,5]]
 * snail(array) #=> [1,2,3,4,5,6,7,8,9]
 * This image will illustrate things more clearly:
 * <p>
 * <p>
 * NOTE: The idea is not sort the elements from the lowest value to the highest; the idea is to traverse the 2-d array in a clockwise snailshell pattern.
 * <p>
 * NOTE 2: The 0x0 (empty matrix) is represented as en empty array inside an array [[]].
 */
public class Snail {

    public static int[] snail(int[][] array) {
        if (array.length == 0) {
            return new int[]{};
        }
        int n = array.length - 1, k = 0;
        List<Integer> snail = new LinkedList<>();

        while (n > 0) {
            for (int i = k; i < n; i++) {
                snail.add(array[k][i]);
            }
            for (int i = k; i < n; i++) {
                snail.add(array[i][n]);
            }
            for (int i = 0; i < n - k; i++) {
                snail.add(array[n][n - i]);
            }
            for (int i = 0; i < n - k; i++) {
                snail.add(array[n - i][k]);
            }
            k++;
            n--;
        }
        if (k % 2 == 0) snail.add(array[k / 2][k / 2]);
        return snail.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(snail(new int[][]{
                {1, 2, 3, 6, 0},
                {8, 9, 4, 3, 1},
                {7, 6, 5, 2, 6},
                {1, 8, 5, 4, 8},
                {5, 2, 6, 9, 0}})));

        System.out.println(Arrays.toString(snail(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}})));

        System.out.println(Arrays.toString(snail(new int[][]{
                {1, 2, 3, 4},
                {4, 5, 6, 1},
                {7, 8, 9, 0},
                {3, 8, 5, 2}})));
    }
}