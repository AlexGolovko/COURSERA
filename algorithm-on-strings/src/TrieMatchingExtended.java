import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TrieMatchingExtended implements Runnable {

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
    public Set<Integer> computeSuffixTreeEdges(final String text) {
        final Set<Integer> result = new LinkedHashSet();
        // Implement this function yourself
        final Node root = buildTree(patterns);
        final int length = text.length();
        for (int idx = 0; idx < length; idx++) {
            computeSuffixTree(text, idx, idx, root, result);
//            if (computeSuffixTree(text, idx, root, result)) {
//                result.add(String.valueOf(idx));
//            }
        }
        return result;
    }

    private void computeSuffixTree(final String text, final int textIdx, final int currentIdx, final Node node, final Set<Integer> result) {
        if (node.leaf.isEmpty()) {
            result.add(textIdx);
            return;
//            return true;
        }
        if (node.patternFinish) {
            result.add(textIdx);
        }
        if (currentIdx < text.length()) {
//            if (node.value != text.charAt(currentIdx + 1)) {
//                return;
//            }
            final Optional<Node> first = node.findFirst(text.charAt(currentIdx));
            //                return computeSuffixTree(text, idx + 1, first.get(), result);
            first.ifPresent(value -> computeSuffixTree(text, textIdx, currentIdx + 1, value, result));
        }
//        return false;
    }

    static public void main(String[] args) throws IOException {
        new TrieMatchingExtended().run();
    }

    public void print(Set<Integer> x) {
        for (Integer a : x) {
            System.out.println(a);
        }
    }

    @Override
    public void run() {
        Set<Integer> edges = null;
        try {
            FastScanner scanner = new FastScanner();
            final String text = scanner.next();
            final int patternSize = scanner.nextInt();
            for (int i = 0; i < patternSize; i++) {
                patterns.add(scanner.next());
            }
            edges = computeSuffixTreeEdges(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        print(edges);
    }

    public Node buildTree(ArrayList<String> patterns) {
//        final AtomicInteger atomicInteger = new AtomicInteger(0);
        final Node root = new Node((char) /*atomicInteger.getAndAdd(1)*/0);
        patterns.sort(Comparator.comparingInt(String::length).reversed());
        patterns.forEach(pattern -> {
            Node currentNode = root;
            char[] charArray = pattern.toCharArray();
            for (int idx = 0, charArrayLength = charArray.length; idx < charArrayLength; idx++) {
                char c = charArray[idx];
                final Optional<Node> node = currentNode.findFirst(c);
                if (node.isPresent()) {
                    currentNode = node.get();
                    if (idx == (charArrayLength - 1)) {
                        currentNode.patternFinish = true;
                    }
                } else {
                    final Node newNode = new Node(/*atomicInteger.getAndAdd(1),*/ c);
                    currentNode.addLeaf(newNode);
                    currentNode = newNode;
                }
            }
        });
        return root;
    }

    public static class Node {
        //        private int id;
        private Character value;
        private boolean patternFinish = false;
        private List<Node> leaf = new ArrayList<>();

        public Node(char c) {
            value = c;
        }

//        public Node(int id, char c) {
//            this.id = id;
//            this.value = c;
//        }

        public List<Node> getLeaf() {
            return leaf;
        }

        public void addLeaf(Node leaf) {
            this.leaf.add(leaf);
        }

//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public Character getValue() {
//            return value;
//        }
//
//        public void setValue(Character value) {
//            this.value = value;
//        }

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