import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class NonSharedSubstring implements Runnable {
    public void solve(int p, ArrayList<String> patterns) {
        final Node root = buildTree(patterns);
        printTree(root);
    }

    public Node buildTree(ArrayList<String> patterns) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        final Node root = new Node((char) atomicInteger.getAndAdd(1));
        patterns.forEach(pattern -> {
            Node currentNode = root;
            for (char c : pattern.toCharArray()) {
                final Optional<Node> node = currentNode.findFirst(c);
                if (node.isPresent()) {
                    currentNode = node.get();
                } else {
                    final Node newNode = new Node(atomicInteger.getAndAdd(1), c);
                    currentNode.addLeaf(newNode);
                    currentNode = newNode;
                }
            }
        });
        return root;
    }

    public void printTree(Node node) {
        final ArrayList<String> result = new ArrayList<>();
        node.print(0);
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            final int p = Integer.parseInt(in.readLine());
            final ArrayList<String> patterns = new ArrayList<>(p);
            for (int i = 0; i < p; i++) {
                patterns.add(in.readLine());
            }
            solve(p, patterns);
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new Thread(new NonSharedSubstring()).start();
    }

    public static class Node {
        private int id;
        private Character value;
        private List<Node> leaf = new ArrayList<>();

        public Node(char c) {
            value = c;
        }

        public Node(int id, char c) {
            this.id = id;
            this.value = c;
        }

        public List<Node> getLeaf() {
            return leaf;
        }

        public void addLeaf(Node leaf) {
            this.leaf.add(leaf);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Character getValue() {
            return value;
        }

        public void setValue(Character value) {
            this.value = value;
        }

        public Optional<Node> findFirst(char c) {
            return leaf.stream().filter(val -> val.value == c).findFirst();
        }


        public void print(int current) {
            leaf.forEach(val -> {
                System.out.println(this.id + "->" + val.id + ":" + val.value);
                val.print(this.id);
            });
        }
    }
}
