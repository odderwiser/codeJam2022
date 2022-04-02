package QRound;

import java.util.Scanner;

public class PunchCard {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cases = scanner.nextInt();
        int[][] sizes  = new int[cases][2]; // 0 -  rows, 1, columns
        for (int i = 0; i < cases; i++) {
            sizes[i][0] = scanner.nextInt();
            sizes[i][1] = scanner.nextInt();
        }
        for (int i = 0; i < cases; i++) {
            System.out.println("Case #"+(i+1)+":");
            System.out.print(solve(sizes[i][0], sizes[i][1]));
        }
    }

    private static String solve(int rows, int cols) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            string.append(printLine(i, cols, false));
            string.append(printLine(i, cols, true));
        }
        return string.append(printLine(1, cols, false)).toString();
    }

    private static String printLine(int i, int cols, boolean isCell) {
        StringBuilder line = new StringBuilder();
        char[] chars = (isCell) ? new char[]{'|', '.'} : new char[]{ '+', '-'};
        if (i == 0) {
            line.append('.').append('.');
        } else {
            line.append(chars);
        }
        for (int j = 1; j < cols; j++) {
            line.append(chars);
        }
        return line.append(chars[0]).append('\n').toString();
    }

}
