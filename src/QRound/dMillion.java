package QRound;

import java.util.*;

public class dMillion {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cases = scanner.nextInt();
        for (int i = 0; i < cases; i++) {
            solve(scanner, i+1);
        }
    }

    public static void solve(Scanner scanner, int caseNum) {
        int diceNum = scanner.nextInt();
        ArrayList<Integer> dice = new ArrayList<>();
        for (int i = 0; i < diceNum; i++) {
            dice.add(scanner.nextInt());
        }
        Collections.sort(dice);
        int highest = dice.get(0);
        int lowest = dice.get(0);
        for (int i = 1; i < dice.size(); i++) {
            int next = dice.get(i);
            if (next > highest) {
                highest++;
            } else if (lowest > 1) {
                lowest--;
            }
        }
        System.out.println("Case #"+caseNum+": "+Math.min(highest-lowest+1, diceNum));
    }
}