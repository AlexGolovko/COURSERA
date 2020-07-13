import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Toposort {

    public static void main(String[] args) {
        List<Integer> topologicOrder = new TopologicalSort(new TopoScanner().scan()).handle();
        for (int i = topologicOrder.size() - 1; i >= 0; i--) {
            System.out.print((topologicOrder.get(i) + 1) + " ");
        }
    }
}

class TopologicalSort {
    private List<TopoVertex> vertices;

    TopologicalSort(List<TopoVertex> vertices) {
        this.vertices = vertices;
    }

    List<Integer> handle() {
        ArrayList<Integer> order = new ArrayList<>(vertices.size());
        vertices.stream().filter(it -> !it.connected.isEmpty()).forEach(it -> explore(it, order));
        vertices.stream().filter(it -> it.connected.isEmpty()).forEach(it -> explore(it, order));
        return order;
    }

    private void explore(TopoVertex vertx, List<Integer> order) {
        vertx.connected.forEach(
                it -> {
                    if (it.ordered == 0) explore(it, order);
                }
        );
        if (vertx.ordered == 0) {
            order.add(vertx.id);
            vertx.ordered = 1;
        }
    }
}

class TopoScanner {

    public List<TopoVertex> scan() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<TopoVertex> vertices = new ArrayList<>();
        int m = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            vertices.add(new TopoVertex(i));
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
            vertices.get(x).connected.add(vertices.get(y));
        }
        return vertices;
    }
}

class TopoVertex {
    int id;
    int ordered;
    List<TopoVertex> connected;

    public TopoVertex(int id) {
        this.id = id;
        ordered = 0;
        connected = new ArrayList<>();
    }
}