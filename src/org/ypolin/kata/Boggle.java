package org.ypolin.kata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * https://www.codewars.com/kata/57680d0128ed87c94f000bfd
 *
 * Boggle Word Checker
 *
 * Write a function that determines whether a string is a valid guess in a Boggle board, as per the rules of Boggle. A Boggle board is a 2D array of individual characters, e.g.:
 *
 * [ ['I','L','A','W'],
 *   ['B','N','G','E'],
 *   ['I','U','A','O'],
 *   ['A','S','R','L'] ]
 * Valid guesses are strings which can be formed by connecting adjacent cells (horizontally, vertically, or diagonally) without re-using any previously used cells.
 *
 * For example, in the above board "BINGO", "LINGO", and "ILNBIA" would all be valid guesses, while "BUNGIE", "BINS", and "SINUS" would not.
 *
 * Your function should take two arguments (a 2D array and a string) and return true or false depending on whether the string is found in the array as per Boggle rules.
 *
 * Test cases will provide various array and string sizes (squared arrays up to 150x150 and strings up to 150 uppercase letters). You do not have to check whether the string is a real word or not, only if it's a valid guess.
 */
public class Boggle {
    private char[][] board;
    private String word;
    private int N = 0;

    public Boggle(final char[][] board, final String word) {
        this.board = board;
        this.word = word;
        this.N = this.board.length;
    }

    public boolean check() {
        List<int[]> startIndexes = findFirstLetterIndexes(this.word.charAt(0));
        if (startIndexes.isEmpty()) {
            return false;
        }
        if (this.word.length() == 1) {
            return true;
        }
        for (int[] start : startIndexes) {
            List<String> visitedIndexes = new ArrayList<>();
            visitedIndexes.add(String.format("%d %d", start[0], start[1]));
            if (findNext(visitedIndexes, start[0], start[1], 1)) {
                return true;
            }

        }
        return false;
    }

    private boolean findNext(List<String> visitedIndexes, int x, int y, int letterIndex) {
        char c = this.word.charAt(letterIndex);
        int[] possibleNextX = getPossibleNextIndexes(x);
        int[] possibleNextY = getPossibleNextIndexes(y);
        for (int xi = 0; xi < possibleNextX.length; xi++) {
            for (int yi = 0; yi < possibleNextY.length; yi++) {
                int nextX = possibleNextX[xi];
                int nextY = possibleNextY[yi];
                String xy = String.format("%d %d", nextX, nextY);
                if (visitedIndexes.contains(xy)) {
                    continue;
                }
                if (this.board[nextX][nextY] == c) {

                    if (letterIndex == this.word.length() - 1) {
                        return true;
                    }
                    List<String> visited = new ArrayList<>(visitedIndexes);
                    visited.add(xy);
                    if (findNext(visited, nextX, nextY, letterIndex + 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private List<int[]> findFirstLetterIndexes(char c) {
        List<int[]> indexes = new ArrayList<>();
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j] == c) {
                    indexes.add(new int[]{i, j});
                }
            }
        }
        return indexes;
    }

    private int[] getPossibleNextIndexes(int i) {
        if (i == 0) {
            return new int[]{i, i + 1};
        }
        if (i == N - 1) {
            return new int[]{i, i - 1};
        }
        return new int[]{i, i + 1, i - 1};
    }

    public static void main(String[] args) {
        char[][] board = {
                {'W', 'L','G', 'W', 'A', 'R', 'I', 'V', 'V', 'W', 'F', 'U', 'H', 'O', 'Z', 'H', 'T', 'Q', 'F', 'R', 'K', 'K', 'V', 'F', 'O', 'K', 'R', 'Q', 'S', 'A', 'K', 'O', 'S', 'O'},
                {'Y', 'Z', 'W', 'C', 'W', 'Z', 'B', 'E', 'L', 'X', 'E', 'H', 'Q', 'J', 'Q', 'O', 'H', 'E', 'Q', 'Q', 'L', 'N', 'W', 'H', 'I', 'R', 'V', 'H', 'N', 'R', 'X', 'Y', 'A', 'W'},
                {'G', 'W', 'Z', 'V', 'S', 'L', 'J', 'J', 'N', 'B', 'F', 'V', 'L', 'J', 'T', 'D', 'S', 'Q', 'N', 'H', 'I', 'K', 'P', 'L', 'K', 'F', 'L', 'S', 'O', 'X', 'H', 'I', 'A', 'Q'},
                {'D', 'N', 'I', 'M', 'U', 'S', 'W', 'O', 'G', 'Y', 'R', 'U', 'R', 'X', 'E', 'K', 'P', 'B', 'K', 'M', 'Z', 'R', 'E', 'R', 'O', 'V', 'C', 'A', 'O', 'P', 'H', 'S', 'I', 'V'},
                {'I', 'D', 'I', 'Q', 'K', 'N', 'M', 'M', 'A', 'B', 'M', 'N', 'S', 'R', 'Y', 'F', 'B', 'L', 'J', 'R', 'C', 'L', 'G', 'O', 'K', 'U', 'D', 'Y', 'R', 'W', 'R', 'Q', 'D', 'U'},
                {'S', 'W', 'V', 'Z', 'E', 'L', 'X', 'K', 'H', 'M', 'S', 'C', 'Y', 'A', 'E', 'M', 'Q', 'U', 'A', 'D', 'V', 'B', 'X', 'J', 'H', 'L', 'Q', 'X', 'D', 'T', 'B', 'Y', 'J', 'H'},
                {'V', 'C', 'U', 'D', 'S', 'V', 'Y', 'H', 'Y', 'R', 'R', 'X', 'Y', 'N', 'C', 'W', 'Y', 'G', 'M', 'O', 'Z', 'Y', 'R', 'R', 'N', 'Z', 'G', 'W', 'S', 'O', 'E', 'L', 'I', 'I'},
                {'H', 'S', 'Z', 'X', 'A', 'B', 'U', 'J', 'A', 'Q', 'E', 'H', 'W', 'G', 'Q', 'Y', 'Z', 'V', 'H', 'W', 'V', 'U', 'I', 'W', 'S', 'Q', 'L', 'O', 'M', 'O', 'U', 'D', 'I', 'D'},
                {'M', 'O', 'Q', 'D', 'I', 'K', 'J', 'M', 'P', 'T', 'P', 'O', 'D', 'M', 'M', 'O', 'J', 'F', 'M', 'H', 'Q', 'W', 'L', 'I', 'G', 'B', 'V', 'H', 'G', 'O', 'W', 'B', 'L', 'U'},
                {'S', 'Q', 'B', 'S', 'M', 'G', 'A', 'X', 'B', 'R', 'F', 'J', 'Y', 'Z', 'P', 'N', 'J', 'P', 'Z', 'Z', 'N', 'W', 'H', 'Y', 'D', 'X', 'W', 'Q', 'L', 'G', 'P', 'R', 'D', 'R'},
                {'J', 'A', 'M', 'E', 'P', 'Q', 'L', 'I', 'V', 'T', 'Z', 'T', 'P', 'J', 'E', 'B', 'D', 'F', 'T', 'F', 'B', 'X', 'X', 'F', 'H', 'L', 'L', 'U', 'G', 'C', 'A', 'J', 'S', 'M'},
                {'H', 'F', 'Z', 'U', 'O', 'K', 'H', 'U', 'F', 'W', 'N', 'K', 'K', 'O', 'O', 'F', 'X', 'P', 'G', 'C', 'O', 'V', 'C', 'Z', 'H', 'N', 'X', 'A', 'V', 'K', 'Z', 'B', 'T', 'D'},
                {'N', 'N', 'E', 'M', 'Z', 'S', 'L', 'F', 'U', 'U', 'K', 'X', 'X', 'R', 'E', 'I', 'T', 'A', 'T', 'K', 'W', 'B', 'X', 'C', 'P', 'O', 'B', 'F', 'I', 'T', 'H', 'I', 'A', 'X'},
                {'Q', 'L', 'S', 'M', 'T', 'Y', 'E', 'I', 'H', 'V', 'I', 'L', 'E', 'Z', 'O', 'Y', 'H', 'W', 'Q', 'L', 'P', 'R', 'W', 'C', 'N', 'K', 'P', 'S', 'B', 'O', 'N', 'O', 'D', 'A'},
                {'L', 'H', 'X', 'W', 'S', 'Y', 'V', 'J', 'K', 'H', 'J', 'W', 'I', 'A', 'G', 'W', 'W', 'L', 'Z', 'H', 'J', 'A', 'B', 'J', 'S', 'V', 'R', 'Q', 'G', 'U', 'V', 'C', 'Y', 'T'},
                {'C', 'V', 'T', 'Q', 'E', 'Y', 'P', 'A', 'P', 'W', 'Y', 'T', 'E', 'Q', 'R', 'N', 'X', 'M', 'J', 'T', 'Y', 'L', 'Z', 'B', 'U', 'S', 'Q', 'U', 'D', 'O', 'Y', 'O', 'C', 'G'},
                {'Z', 'A', 'C', 'R', 'F', 'V', 'M', 'H', 'W', 'E', 'C', 'V', 'R', 'P', 'W', 'V', 'T', 'M', 'V', 'S', 'M', 'U', 'K', 'R', 'Q', 'X', 'S', 'N', 'U', 'T', 'D', 'M', 'B', 'Q'},
                {'O', 'H', 'P', 'S', 'L', 'B', 'X', 'J', 'Z', 'U', 'M', 'E', 'J', 'Y', 'E', 'O', 'J', 'E', 'T', 'O', 'B', 'J', 'T', 'G', 'W', 'S', 'S', 'S', 'A', 'B', 'H', 'A', 'Y', 'I'},
                {'F', 'Y', 'B', 'U', 'Q', 'I', 'M', 'C', 'N', 'F', 'M', 'L', 'J', 'T', 'I', 'A', 'H', 'O', 'W', 'J', 'Y', 'X', 'S', 'T', 'N', 'F', 'F', 'E', 'R', 'I', 'K', 'X', 'T', 'F'},
                {'E', 'E', 'G', 'K', 'A', 'K', 'I', 'L', 'Z', 'S', 'H', 'D', 'A', 'H', 'G', 'Q', 'J', 'T', 'N', 'S', 'P', 'P', 'H', 'L', 'I', 'S', 'D', 'O', 'L', 'W', 'J', 'R', 'Q', 'W'},
                {'Y', 'M', 'B', 'F', 'H', 'L', 'U', 'F', 'C', 'D', 'T', 'I', 'Y', 'P', 'Q', 'O', 'T', 'L', 'E', 'S', 'H', 'F', 'H', 'X', 'X', 'Q', 'E', 'W', 'L', 'X', 'V', 'K', 'B', 'S'},
                {'O', 'C', 'U', 'F', 'P', 'E', 'V', 'M', 'A', 'K', 'B', 'T', 'N', 'G', 'T', 'P', 'L', 'S', 'Z', 'B', 'E', 'L', 'M', 'F', 'K', 'V', 'Z', 'R', 'H', 'P', 'Q', 'K', 'C', 'M'},
                {'T', 'L', 'J', 'J', 'X', 'U', 'F', 'Y', 'D', 'W', 'F', 'Z', 'G', 'D', 'C', 'E', 'R', 'P', 'L', 'P', 'M', 'T', 'P', 'C', 'J', 'G', 'F', 'B', 'B', 'D', 'T', 'G', 'W', 'K'},
                {'B', 'K', 'Y', 'T', 'E', 'V', 'R', 'D', 'G', 'N', 'X', 'W', 'Z', 'W', 'M', 'I', 'Q', 'O', 'C', 'Z', 'Q', 'Z', 'M', 'Z', 'N', 'C', 'P', 'M', 'S', 'H', 'R', 'G', 'B', 'D'},
                {'R', 'K', 'M', 'H', 'Z', 'R', 'S', 'J', 'M', 'N', 'D', 'W', 'K', 'Q', 'O', 'S', 'D', 'W', 'K', 'W', 'U', 'H', 'I', 'V', 'P', 'R', 'E', 'S', 'Z', 'H', 'C', 'A', 'I', 'C'},
                {'U', 'D', 'J', 'W', 'S', 'N', 'A', 'I', 'C', 'D', 'F', 'A', 'S', 'L', 'O', 'W', 'F', 'O', 'F', 'V', 'S', 'G', 'T', 'V', 'X', 'D', 'C', 'K', 'T', 'Y', 'K', 'L', 'H', 'J'},
                {'H', 'I', 'K', 'Y', 'O', 'V', 'A', 'W', 'L', 'O', 'J', 'B', 'S', 'L', 'Y', 'D', 'A', 'Y', 'D', 'I', 'M', 'L', 'W', 'J', 'G', 'X', 'D', 'P', 'L', 'U', 'M', 'U', 'L', 'W'},
                {'Y', 'M', 'R', 'E', 'S', 'I', 'E', 'C', 'Y', 'B', 'D', 'C', 'K', 'N', 'C', 'S', 'S', 'Q', 'G', 'T', 'W', 'U', 'N', 'O', 'O', 'E', 'V', 'I', 'P', 'C', 'U', 'S', 'Z', 'A'},
                {'W', 'K', 'P', 'G', 'Q', 'T', 'U', 'R', 'Y', 'G', 'A', 'A', 'L', 'J', 'V', 'S', 'I', 'K', 'F', 'D', 'U', 'B', 'I', 'R', 'J', 'R', 'B', 'U', 'M', 'B', 'P', 'I', 'D', 'C'},
                {'E', 'Z', 'N', 'C', 'Q', 'Y', 'O', 'D', 'G', 'Y', 'M', 'B', 'Q', 'H', 'O', 'E', 'U', 'G', 'P', 'M', 'L', 'R', 'F', 'X', 'Z', 'K', 'R', 'Z', 'E', 'Q', 'H', 'Z', 'A', 'K'},
                {'O', 'X', 'Z', 'W', 'S', 'W', 'Y', 'A', 'R', 'Q', 'P', 'G', 'W', 'Z', 'A', 'M', 'D', 'L', 'C', 'E', 'D', 'X', 'H', 'N', 'F', 'Y', 'R', 'Q', 'K', 'D', 'Z', 'K', 'X', 'M'},
                {'O', 'E', 'Z', 'E', 'X', 'W', 'Q', 'I', 'N', 'J', 'D', 'T', 'Z', 'I', 'R', 'D', 'T', 'V', 'A', 'I', 'B', 'B', 'G', 'T', 'W', 'Q', 'Y', 'G', 'M', 'W', 'G', 'S', 'V', 'M'},
                {'V', 'P', 'J', 'K', 'O', 'X', 'U', 'W', 'B', 'K', 'Q', 'N', 'Q', 'U', 'U', 'P', 'R', 'Q', 'M', 'F', 'L', 'E', 'P', 'O', 'J', 'V', 'H', 'W', 'E', 'L', 'O', 'K', 'H', 'D'},
                {'O', 'B', 'X', 'I', 'O', 'R', 'S', 'U', 'E', 'A', 'X', 'S', 'C', 'Y', 'I', 'C', 'Y', 'W', 'C', 'O', 'K', 'U', 'J', 'D', 'L', 'T', 'O', 'I', 'A', 'I', 'W', 'P', 'Q', 'O'}
        };

        Boggle ear = new Boggle(board, "QEQPFECGMPEOXLWTVMMSCMDN");
        System.out.println(ear.check());
    }
}
