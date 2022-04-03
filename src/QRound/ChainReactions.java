package QRound;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;

public class ChainReactions {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cases = scanner.nextInt();
        for (int i = 0; i < cases; i++) {
            solve(scanner, i+1);
        }
    }

    public static void solve(Scanner scanner, int caseNum) {
        int nodeSize = scanner.nextInt();
        HashSet<Node> leafs = new HashSet<>();
        ArrayList<Node> tree = new ArrayList<>(); //0 - value, 1 - parent
        tree.add(new Node(0, 0));
        for (int i = 1; i <= nodeSize; i++) {
            Node node = new Node(scanner.nextInt(), i);
            tree.add(node);
        }
        for (int i = 1; i <= nodeSize; i++) {
            int parent = scanner.nextInt();
            Node parentNode = tree.get(parent);
            Node node = tree.get(i);
            leafs.add(node);
            node.setParent(parentNode);
            leafs.remove(parentNode);
        }
        //getting potential parents part
        for (Node node : leafs) {
            node.findHighestValue();
        }
        long totalValue = 0;
        while (!leafs.isEmpty()) {
            for (int i = 0; i < tree.size(); i++) {
                //trivial case
                Node node = tree.get(i);
                if (node.potentialChildren.size()== 1) {
                    Node child = node.potentialChildren.get(0);
                    leafs.remove(child);
                    totalValue+=node.value;
                    node.value = 0;
                } else if (node.potentialChildren.size() > 1){

                }

            }
        }
    }

    public static long nonTrivial(Node parent, HashSet<Node> leafs, ArrayList<Node> tree) {
        long totalValue = 0;
        if (parent.potentialChildren.size() == 1) {
            Node child = parent.potentialChildren.get(0);
            leafs.remove(child);
            totalValue+=parent.value;
            parent.value = 0;
            return totalValue;
        }
        HashSet<Node> otherParents = new HashSet<>();
        for (Node node : parent.potentialChildren) {
            node.potentialParents.remove(parent);
            Node otherParent = Collections.max(node.potentialParents);
            node
        }
        while (otherParents.size() > 1)

    }

    public static class Node implements Comparable<Node> {
        Node parent;
        int value;
        int id;
        ArrayList<Node> potentialChildren = new ArrayList<>();
        HashSet<Node> potentialParents;

        public Node(int value, int id) {
            this.value = value;
            this.id = id;
        }

        public void setParent(Node other) {
            this.parent = other;
        }

        public void findHighestValue() {
            potentialParents = new HashSet<>();
            Node newParent = this;
            Node highestParent = this;
            potentialParents.add(this);
            while (newParent.parent != null) {
                newParent = newParent.parent;
                potentialParents.add(newParent);
                if (newParent.value > highestParent.value) {
                    highestParent = newParent;
                }
            }
            //Collections.sort(potentialParents, Comparator.comparingInt(o -> o.value));
            highestParent.potentialChildren.add(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return id == node.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public int compareTo(Node o) {
            return this.value - o.value;
        }
    }
}
