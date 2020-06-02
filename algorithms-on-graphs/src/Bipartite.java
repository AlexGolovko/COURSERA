import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class Bipartite {
    public static int bipartite(List<Vertex> vertices) {
        //write your code here
        vertices.get(0).color = Color.WHITE;
        vertices.get(0).isVisited = true;
        Layer layer = new Layer();
        layer.color = Color.WHITE;
        layer.vertices.add(vertices.get(0));
        List<Layer> layers = new ArrayList<>();
        layers.add(layer);
        return bipartiteInternal(layers);
    }

    private static int bipartiteInternal(List<Layer> layers) {
        Layer currLayer = layers.get(layers.size() - 1);
        Layer nextLayer = new Layer();
        nextLayer.color = Color.opposite(currLayer.color);
        for (Vertex vertex : currLayer.vertices) {
            vertex.isVisited = true;
            vertex.color = currLayer.color;
            if (vertex.connected.stream().anyMatch(vertex1 ->
                    vertex1.color != null && vertex1.color != nextLayer.color
            )) {
                return 0;
            }
            nextLayer.vertices.addAll(vertex.connected.stream().filter(vertex1 -> !vertex1.isVisited).collect(Collectors.toSet()));
        }
        if (nextLayer.vertices.isEmpty()) {
            return 1;
        }
        layers.add(nextLayer);
        return bipartiteInternal(layers);

    }

    public static int bipartiteOnQueue(List<Vertex> vertices) {
        Vertex vertex = vertices.get(0);
        Queue<Vertex> queue = new ConcurrentLinkedQueue<>();
        queue.add(vertex);
        vertex.color = Color.RED;
        while (!queue.isEmpty()) {
            vertex = queue.poll();
            for (Vertex vert : vertex.connected) {
                if (vert.color == null) {
                    vert.color = Color.opposite(vertex.color);
                    queue.add(vert);
                }
                if (vert.color != null && vert.color == vertex.color) {
                    return 0;
                }
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        System.out.println(bipartiteOnQueue(new GraphScanner().scan()));
    }

    static class GraphScanner {

        public List<Vertex> scan() {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            List<Vertex> vertices = new ArrayList<>();
            int m = scanner.nextInt();
            for (int i = 0; i < n; i++) {
                vertices.add(new Vertex(i));
            }
            for (int i = 0; i < m; i++) {
                int x, y;
                x = scanner.nextInt() - 1;
                y = scanner.nextInt() - 1;
                vertices.get(x).connected.add(vertices.get(y));
                vertices.get(y).connected.add(vertices.get(x));
            }
            return vertices;
        }
    }

    static class Vertex {
        int id;
        Color color;
        boolean isVisited;
        List<Vertex> connected;

        public Vertex(int id) {
            this.id = id;
            connected = new ArrayList<>();
            isVisited = false;
        }
    }

    static class Layer {
        Color color;
        Set<Vertex> vertices = new HashSet<>();
    }

    static enum Color {
        WHITE, RED;

        public static Color opposite(Color color) {
            if (RED == color)
                return WHITE;
            else
                return RED;
        }
    }

}

