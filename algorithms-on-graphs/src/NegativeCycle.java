import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class NegativeCycle {
    static NegativeCycle cycle = new NegativeCycle();

    public static int negativeCycle() {
        Graph graph = cycle.new Graph().scan();
        final Node commonNode = cycle.new Node(graph.nodes.size());
        commonNode.distance = 0;
        graph.nodes.forEach(node -> {
            graph.edges.add(cycle.new Edge(commonNode, node, 0));
        });
        graph.nodes.add(commonNode);
        for (int i = 0; i < graph.nodes.size() - 1; i++) {
            graph.edges.forEach(edge -> {
                if (edge.startNode.distance != Long.MAX_VALUE && edge.startNode.distance + edge.weight < edge.finishNode.distance)
                    edge.finishNode.distance = edge.startNode.distance + edge.weight;
            });
        }
        AtomicInteger isNegativeCycle = new AtomicInteger(0);
        for (int i = 0; i < graph.edges.size(); i++) {
            graph.edges.forEach(edge -> {
                if (edge.startNode.distance != Long.MAX_VALUE && edge.startNode.distance + edge.weight < edge.finishNode.distance) {
                    edge.finishNode.distance = Long.MIN_VALUE;
                    isNegativeCycle.set(1);
                }
            });
        }
        return isNegativeCycle.get();
    }


    public static void main(String[] args) {
        System.out.println(negativeCycle());
    }

    private class Graph {
        final List<Node> nodes = new ArrayList<>();
        final List<Edge> edges = new ArrayList<>();

        Graph scan() {
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
                Edge edge = new Edge(nodes.get(x - 1), nodes.get(y - 1), w);
                nodes.get(x - 1).reachableEdge.add(edge);
                edges.add(edge);
            }
            return this;
        }
    }

    private class Node {
        final int id;
        final List<Edge> reachableEdge = new ArrayList<>();
        long distance = Long.MAX_VALUE;
        boolean isVisited = false;

        public Node(int i) {
            id = i;
        }
    }

    private class Edge {
        final Node startNode;
        final Node finishNode;
        final int weight;

        Edge(Node startNode, Node finishNode, int weight) {
            this.startNode = startNode;
            this.finishNode = finishNode;
            this.weight = weight;
        }
    }
}

