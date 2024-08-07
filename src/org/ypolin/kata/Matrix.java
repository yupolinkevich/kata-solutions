package org.ypolin.kata;

import java.text.DecimalFormat;

/**
 * https://www.codewars.com/kata/52a382ee44408cea2500074c
 *
 * Matrix Determinant
 *
 * Description:
 * Write a function that accepts a square matrix (N x N 2D array) and returns the determinant of the matrix.
 *
 * How to take the determinant of a matrix -- it is simplest to start with the smallest cases:
 *
 * A 1x1 matrix |a| has determinant a.
 *
 * A 2x2 matrix [ [a, b], [c, d] ] or
 *
 * |a  b|
 * |c  d|
 * has determinant: a*d - b*c.
 *
 * The determinant of an n x n sized matrix is calculated by reducing the problem to the calculation of the determinants of n matrices ofn-1 x n-1 size.
 *
 * For the 3x3 case, [ [a, b, c], [d, e, f], [g, h, i] ] or
 *
 * |a b c|
 * |d e f|
 * |g h i|
 * the determinant is: a * det(a_minor) - b * det(b_minor) + c * det(c_minor) where det(a_minor) refers to taking the determinant of the 2x2 matrix created by crossing out the row and column in which the element a occurs:
 *
 * |- - -|
 * |- e f|
 * |- h i|
 * Note the alternation of signs.
 *
 * The determinant of larger matrices are calculated analogously, e.g. if M is a 4x4 matrix with first row [a, b, c, d], then:
 *
 * det(M) = a * det(a_minor) - b * det(b_minor) + c * det(c_minor) - d * det(d_minor)
 */
public class Matrix {
    public static int determinant(int[][] matrix) {
        int det = 0;
        if (matrix.length == 1) {
            return matrix[0][0];
        }
        if (matrix.length > 2 && matrix[0].length > 2) {
            for (int i = 0; i < matrix[0].length; i++) {
                if (i % 2 == 0) {
                    det = det + matrix[0][i] * determinant(getSubMatrix(matrix, i));
                } else {
                    det = det - matrix[0][i] * determinant(getSubMatrix(matrix, i));
                }
            }
        } else {
            det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
        return det;
    }

    private static int[][] getSubMatrix(int[][] matrix, int elementY) {
        int[][] subMatrix = new int[matrix.length - 1][matrix[0].length - 1];
        for (int i = 1, x = 0; i < matrix.length; i++, x++) {
            int y = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                if (j != elementY) {
                    subMatrix[x][y++] = matrix[i][j];
                }
            }
        }
        return subMatrix;
    }

    public static void main(String[] args) {
        int[][] m = new int[][]{{2, 5, 3}, {1, -2, -1}, {1, 3, 4}};
        System.out.println(determinant(m));

    }
}
