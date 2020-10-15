import java.util.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.CheckedInputStream;

public class SuffixTree {

    private final ArrayList<String> patterns = new ArrayList<>();

    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding 
    // substrings of the text) in any order.
    public List<String> computeSuffixTreeEdges(final String text) {
        final List<String> result = new ArrayList<>();
        // Implement this function yourself
        final Node root = buildTree(patterns);
        final int length = text.length();
        for (int idx = 0; idx < length; idx++) {
            if (computeSuffixTree(text, idx, root)) {
                result.add(String.valueOf(idx));
            }
        }
        return result;
    }

    private boolean computeSuffixTree(final String text, final int idx, final Node node) {
        if (node.leaf.isEmpty()) {
            return true;
        }
        if (idx < text.length()) {
            final Optional<Node> first = node.findFirst(text.charAt(idx));
            if (first.isPresent()) {
                return computeSuffixTree(text, idx + 1, first.get());
            }
        }
        return false;
    }

    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void print(List<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        final String text = scanner.next();
        final int patternSize = scanner.nextInt();
        for (int i = 0; i < patternSize; i++) {
            patterns.add(scanner.next());
        }
        List<String> edges = computeSuffixTreeEdges(text);
        print(edges);
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
            for (Node node : leaf) {
                if (node.value == c) {
                    return Optional.of(node);
                }
            }
            return Optional.empty();
        }
    }
}