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
        HashMap<Integer, Integer> dice = new HashMap<>();
        for (int i = 0; i < diceNum; i++) {
            int die = scanner.nextInt();
            int value = dice.getOrDefault(die,0);
            dice.put(die, value+1);
        }
        ArrayList<Die> sortedDie = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : dice.entrySet()) {
            sortedDie.add(new Die(entry.getKey(), entry.getValue()));
        }
        Collections.sort(sortedDie);
        int highest = 0;
        int lowest = -1;
        for (int i = 0; i < sortedDie.size(); i++) {
            Die die = sortedDie.get(i);
                if (lowest != 1 ) {
                    int maxLowest;
                    int leftOver;
                    if (lowest  < 0 ) {
                        maxLowest = Math.max(1, die.size + 1 - die.amount);
                        highest = die.size;
                        leftOver = die.amount-die.size;
                        lowest = maxLowest;
                    } else {
                        maxLowest = Math.max(1, lowest -die.amount);
                        leftOver = die.amount-lowest +1;
                        lowest = maxLowest;
                    }
                    highest += Math.max(0, leftOver);
                } else {
                    highest += Math.min(die.size-highest, die.amount);
                }
        }
        System.out.println("Case #"+caseNum+": "+Math.min(highest-lowest+1, diceNum));
    }

    public static class Die implements Comparable<Die> {
        public int size;
        public int amount;

        Die (int k, int v) {
            size = k;
            amount = v;
        }

        @Override
        public int compareTo(Die o) {
            return this.size - o.size;
        }
    }
}

//you want to find longest consecutive length
//longest range will always start from 1, because even the highest Si has a 1