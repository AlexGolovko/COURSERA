import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConnectedComponents {

    public static void main(String[] args) {
        ConnectedComponents connectedComponents = new ConnectedComponents();
        System.out.println(connectedComponents.find());
    }

    private int find() {
        ComponentCounter componentCounter = new ComponentCounter(new CustomScanner());

        return componentCounter.count();
    }
}

class CustomScanner {
    List<Vertx> vertexes;

    CustomScanner() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        vertexes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            vertexes.add(new Vertx(i));
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
            vertexes.get(x).edges.add(vertexes.get(y));
            vertexes.get(y).edges.add(vertexes.get(x));
        }
    }
}

class Vertx {
    int id;
    List<Vertx> edges;

    public Vertx(int id) {
        this.id = id;
        edges = new ArrayList<>();
    }
}

class ComponentCounter {
    private CustomScanner scanner;
    private ConcurrentMap<Vertx, Vertx> visited;
    private int componentCounter;

    public ComponentCounter(CustomScanner customScanner) {
        this.scanner = customScanner;
        this.componentCounter = 0;
        visited = new ConcurrentHashMap<>();
    }

    public int count() {
        for (Vertx vertex : scanner.vertexes) {
            if (!visited.containsKey(vertex)) {
                componentCounter += 1;
                explore(vertex);
            }
        }
        return componentCounter;
    }

    private void explore(Vertx vertex) {
        for (Vertx edge : vertex.edges) {
            if (!visited.containsKey(edge)) {
                visited.put(edge, edge);
                explore(edge);
            }
        }
    }
}


