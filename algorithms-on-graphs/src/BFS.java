import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class BFS {
    public static void main(String[] args) {
//        System.out.println(new BFSPath().solve(new BFSScanner().scan()));
        System.out.println(new BFSPathClassic().solve(new BFSScanner().scan()));
    }

    static class BFSPath {
        public int solve(Task task) {
            List<Layer> layers = new ArrayList<>();
            int currentLayer = 0;
            Layer layer = new Layer(currentLayer);
            layer.vertx.add(task.vertx.get(task.startPoint));
            layers.add(layer);
            return solveInternal(layers, task);
        }

        private int solveInternal(List<Layer> layers, Task task) {
            Layer layer = layers.get(layers.size() - 1);
            int currLayer = layer.layer;
            Layer nextLayer = new Layer(currLayer + 1);
            layers.add(nextLayer);
            for (Vertex vertex : layer.vertx) {
                if (!vertex.isVisited) {
                    vertex.isVisited = true;
                    if (vertex.id == task.finishPoint) {
                        return currLayer;
                    }
                    nextLayer.vertx.addAll(vertex.connected.stream().filter(connectedVertex -> !connectedVertex.isVisited).collect(Collectors.toSet()));
                }
            }
            if (!nextLayer.vertx.isEmpty())
                return solveInternal(layers, task);
            else
                return -1;
        }
    }

    static class BFSPathClassic {
        public int solve(Task task) {
            Queue<Vertex> queue = new ConcurrentLinkedQueue();
            Vertex startVertex = task.vertx.get(task.startPoint);
            startVertex.layer = 0;
            startVertex.isVisited = true;
            queue.add(startVertex);
            while (!queue.isEmpty()) {
                Vertex vertex = queue.poll();
                for (Vertex vert : vertex.connected) {
                    if (!vert.isVisited && vert.id != task.finishPoint) {
                        vert.layer = vertex.layer + 1;
                        vert.isVisited = true;
                        queue.add(vert);
                    }
                    if (vert.id == task.finishPoint) {
                        return vertex.layer + 1;
                    }
                }
            }
            return -1;
        }
    }

    static class BFSScanner {

        public Task scan() {
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
            Task task = new Task();
            task.vertx = vertices;
            task.startPoint = scanner.nextInt() - 1;
            task.finishPoint = scanner.nextInt() - 1;
            return task;
        }
    }

    static class Layer {

        public int layer;
        public Set<Vertex> vertx;

        public Layer(int layer) {
            this.layer = layer;
            vertx = new HashSet<>();
        }
    }

    static class Vertex {
        int layer = Integer.MAX_VALUE;
        int id;
        int ordered;
        boolean isVisited;
        List<Vertex> connected;

        public Vertex(int id) {
            this.id = id;
            ordered = 0;
            connected = new ArrayList<>();
            isVisited = false;
        }
    }

    static class Task {
        List<Vertex> vertx;
        int startPoint;
        int finishPoint;
    }
}
