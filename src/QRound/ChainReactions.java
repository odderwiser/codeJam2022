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
        HashSet<Node> leafs = new HashSet<>(); //collection for storing leafs
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
            Node parentNode = tree.get(parent);
            Node node = tree.get(i);
            //ensure correct values in leafs
            leafs.add(node);
            node.setParent(parentNode);
            leafs.remove(parentNode);
        }
        BigInteger value = BigInteger.valueOf(0); //accumulating result
        PriorityQueue<Node> sortedLeafs = new PriorityQueue<>(leafs); //move leaves to priority ques
        //approach is bottom-up, "breadth" first
        //always start from a node that is furthest away from the source / root: has the biggest height.
        while (!sortedLeafs.isEmpty()) {
            //when taking a leaf, look at it's children.
            Node node = sortedLeafs.remove();
            if (!node.parent.isVisited) {
                node.parent.isVisited = true;
                value = value.add( handleParent(node.parent,sortedLeafs));
            }
        }
        System.out.println("Case #"+caseNum+": "+value);
    }

    public static BigInteger handleParent(Node node, Collection<Node> queue) { //node is the child now
        BigInteger value = BigInteger.valueOf(0); //value to return
        Node smallest = Collections.min(node.children, Comparator.comparingInt(o -> o.value)); //find smallest value among
        //children, this one will be replaced with the parent value, to ensure the highest value is preserved
        node.children.remove(smallest); //not include that value in result
        for (Node child : node.children) { // add all values of ramaining children
            value = value.add(BigInteger.valueOf(child.value));
        }
        //if not source, do things
        if (node.parent != null) {
            //if smallest child has bigger value than parent, replace the current node with the child for next iteration
            if (smallest.value > node.value) {
                node.parent.children.remove(node);
                node.parent.children.add(smallest);
                smallest.height--;
            }
            //only add parent to the queue if not in queue yet.
            if (!node.parent.isVisited) {
                queue.add(node);
                //node.parent.visited = true;
            }
        } else {
            //if node is source, it has value 0, so add the remainign value to the return result.
            value = value.add(BigInteger.valueOf(smallest.value));
        }
        return value;
    }

    public static class Node implements Comparable<Node> {
        Node parent; //points at parent
        int value;
        int id;
        HashSet<Node> children; //collection of direct children
        boolean isVisited; //true if added to the queue
        int height; // how far from source is he node?

        public Node(int value, int id) {
            this.value = value;
            this.id = id;
            this.isVisited = false;
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
        public int compareTo(Node o) { //sort first by height, later by id.
            int i =  o.height - this.height;
            if (i == 0) {
                return this.id - o.id;
            }
            return i;
        }
    }
}
