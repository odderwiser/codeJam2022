package QRound;

import java.math.BigInteger;
import java.util.*;

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
        ArrayList<Node> tree = new ArrayList<>(); // structure to iterate through tree
        tree.add(new Node(0, 0)); //add source
        //parse first line of input: values
        for (int i = 1; i <= nodeSize; i++) {
            Node node = new Node(scanner.nextInt(), i);
            tree.add(node);
        }
        //add parents to the nodes
        for (int i = 1; i <= nodeSize; i++) {
            int parent = scanner.nextInt();
            Node node = tree.get(i);
            node.setParent(tree.get(parent));
        }
        BigInteger value = handleParent(tree.get(0)).add(BigInteger.valueOf(tree.get(0).value));
        System.out.println("Case #"+caseNum+": "+value);
    }

    public static BigInteger handleParent(Node node) {
        BigInteger value = BigInteger.valueOf(0); //value to return
        //pre-order recursion, first fold children to leaves before assessing their value
        for(Node child : node.children) {
            if (child.children.size()> 0) {
                value = value.add(handleParent(child));
            }
        }
        Node smallest = Collections.min(node.children, Comparator.comparingInt(o -> o.value)); //find smallest value among
        //children, this one will be replaced with the parent value, to ensure the highest value is preserved
        node.children.remove(smallest); //not include that value in result
        for (Node child : node.children) { // add all values of remaining children
            value = value.add(BigInteger.valueOf(child.value));
        }
        node.value = Math.max(node.value, smallest.value);
        node.children=null;
        return value;
    }

    public static class Node {
        Node parent; //points at parent
        int value;
        int id;
        HashSet<Node> children; //collection of direct children

        public Node(int value, int id) {
            this.value = value;
            this.id = id;
            this.children = new HashSet<>();
        }

        public void setParent(Node other) {
            this.parent = other;
            other.children.add(this);
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
    }
}
