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
        BigInteger value = BigInteger.valueOf(0);
        LinkedList<Node> queue = new LinkedList<>();
        ArrayList<Node> sortedLeafs = new ArrayList<>(leafs);
        Collections.sort(sortedLeafs);
        for (Node node: sortedLeafs) {
            if (!node.parent.visited) {
                node.parent.visited = true;
                value = value.add( handleParent(node.parent,queue));
            }
        }
        while(!queue.isEmpty()){
            Node node = queue.removeFirst();
            value = value.add( handleParent(node,queue));
        }
        System.out.println("Case #"+caseNum+": "+value);
    }

    public static BigInteger handleParent(Node node, LinkedList<Node> queue) { //node is the child now
        BigInteger value = BigInteger.valueOf(0);
        Node smallest = Collections.min(node.children, Comparator.comparingInt(o -> o.value));
        node.children.remove(smallest);
        for (Node child : node.children) {
            value = value.add(BigInteger.valueOf(child.value));
        }
        if (node.parent != null) {
            if (smallest.value > node.value) {
                node.parent.children.remove(node);
                node.parent.children.add(smallest);
            }
            if (!node.parent.visited) {
                queue.addFirst(node.parent);
                node.parent.visited = true;
            }
        } else {
            value = value.add(BigInteger.valueOf(smallest.value));
        }
        return value;
    }

    public static class Node implements Comparable<Node> {
        Node parent;
        int value;
        int id;
        HashSet<Node> children;
        boolean visited;
        int height;

        public Node(int value, int id) {
            this.value = value;
            this.id = id;
            this.visited = false;
            this.children = new HashSet<>();
            this.height = 0;
        }

        public void setParent(Node other) {
            this.parent = other;
            other.children.add(this);
            this.height = other.height+1;
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
            int i =  o.height - this.height;
            if (i == 0) {
                return this.id - o.id;
            }
            return i;
        }
    }
}
