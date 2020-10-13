import java.util.*;

public class Dijkstra {

    public static void main(String[] args) {
        System.out.println(new DijkstraDistance().evaluate(new DijkstraGraph().scan()));
    }

    public static long internal() {
        return new DijkstraDistance().evaluate(new DijkstraGraph().scan());
    }

    static class DijkstraDistance {
        public long evaluate(DijkstraGraph graph) {
            graph.start.distance = 0;
            final PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingLong(u -> u.distance));
            queue.add(graph.start);
            long distance;
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                node.isVisited = true;
                for (Edge edge : node.reachableEdge) {
                    if (!edge.node.isVisited) {
                        distance = node.distance + edge.cost;
                        if (distance < edge.node.distance) {
                            edge.node.distance = distance;
                            queue.add(edge.node);
                        }
                    }
                }
            }
            return graph.finish.distance == Long.MAX_VALUE ? -1 : graph.finish.distance;
        }
    }

    static class DijkstraGraph {
        final List<Node> nodes = new ArrayList<>();
        Node start;
        Node finish;

        DijkstraGraph scan() {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            for (int i = 0; i < n; i++) {
                nodes.add(new Node(i));
            }
            for (int i = 0; i < m; i++) {
                int x, y, w;
                x = scanner.nextInt();
                y = scanner.nextInt();
                w = scanner.nextInt();
                nodes.get(x - 1).reachableEdge.add(new Edge(nodes.get(y - 1), w));
            }
            start = nodes.get(scanner.nextInt() - 1);
            finish = nodes.get(scanner.nextInt() - 1);
            return this;
        }
    }

    static class Node {
        final int id;
        final List<Edge> reachableEdge = new ArrayList<>();
        long distance = Long.MAX_VALUE;
        boolean isVisited = false;

        public Node(int i) {
            id = i;
        }
    }

    static class Edge {
        final Node node;
        final int cost;

        public Edge(Node node, int w) {
            this.node = node;
            this.cost = w;
        }
    }
}

