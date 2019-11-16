import java.util.*;

import java.io.*;

public class tree_height {
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

    public class TreeHeight {
        int n;
        int parent[];

        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = in.nextInt();
            }
        }

        int computeHeight() {
            // Replace this code with a faster implementation
            Tree tree = new Tree();
            tree.buildTreeLiner(parent);
            return tree.getHeight();
        }

    }

    class Tree {
        private Node root;

        private void setRoot(Node root) {
            this.root = root;
        }

        private Node getRoot() {
            return root;
        }

        public Tree buildTreeLiner(int[] massiv) {
            Map<Integer, Node> treeMap = new HashMap<>();
            for (int i = 0; i < massiv.length; i++) {
                treeMap.put(i, new Node(i));
            }
            for (int i = 0; i < massiv.length; i++) {
                if (massiv[i] == -1) {
                    this.root = treeMap.get(i);
                    continue;
                }
                Node currentNode = treeMap.get(i);
                Node parentNode = treeMap.get(massiv[i]);
                currentNode.setParentNode(parentNode);
                parentNode.addChildNode(currentNode);
            }
            return this;
        }

        public int getHeight() {
            return collectLenghtRecursive(root);
        }

        private int collectLenghtRecursive(Node node) {
            if (node == null) {
                return 0;
            }
            int height = 0;
            for (int i = 0; i < node.getChildNodes().size(); i++) {
                int temp = collectLenghtRecursive(node.getChildNodes().get(i));
                if (temp > height) height = temp;
            }
            return height+1;
        }
    }

    class Node {
        private Integer value;
        private List<Node> childNodes;
        private Node parentNode;

        Node(int value) {
            this.value = value;
            childNodes = new ArrayList<>();
        }

        public List<Node> getChildNodes() {
            return childNodes;
        }

        public void addChildNode(Node childNodes) {
            this.childNodes.add(childNodes);
        }

        public Node getParentNode() {
            return parentNode;
        }

        public void setParentNode(Node parentNode) {
            this.parentNode = parentNode;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new tree_height().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        TreeHeight tree = new TreeHeight();
        tree.read();
        System.out.println(tree.computeHeight());
    }
}