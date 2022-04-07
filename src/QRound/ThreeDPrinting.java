package QRound;

import java.util.Scanner;

public class ThreeDPrinting {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cases = scanner.nextInt();
        for (int i = 0; i < cases; i++) {
            solve(scanner, i+1);
        }
    }

    public static void solve(Scanner scanner, int caseNum) {
        int[] ink = new int[4];
        for(int i = 0; i< ink.length; i++) {
            ink[i] = scanner.nextInt();
        }
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < ink.length; i++) {
                ink[i] = Math.min(ink[i], scanner.nextInt());
            }
        }
        int value = 0;
        int million = (int) Math.pow(10, 6);
        StringBuilder answer = new StringBuilder();
        for (int j : ink) {
            if (value == million) {
                answer.append(" ").append(0);
            } else if (value + j < million) {
                value += j;
                answer.append(" ").append(j);
            } else {
                int toAdd = million - value;
                value = million;
                answer.append(" ").append(toAdd);
            }
        }
        if (value < million) {
            answer = new StringBuilder(" IMPOSSIBLE");
        }
        System.out.println("Case #"+caseNum+":"+answer);

    }
}
