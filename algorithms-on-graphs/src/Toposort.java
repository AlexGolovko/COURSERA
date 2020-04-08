import java.util.*;

public class Toposort {

    public static void main(String[] args) {
        new TopologicalSort(new TopoScanner().scan()).handle().forEach(x -> System.out.print(x + " "));
    }
}

class TopologicalSort {
    private List<TopoVertex> vertices;

    TopologicalSort(List<TopoVertex> vertices) {
        this.vertices = vertices;
    }

    List<Integer> handle() {
        ArrayList<Integer> order = new ArrayList<>();
//explore()

        return order;
    }
}

class TopoScanner {

    public List<TopoVertex> scan() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Map<Integer, TopoVertex> vertices = new HashMap<>(n);
        int m = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            vertices.put(i, new TopoVertex(i));
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
            vertices.get(x).addVertex(vertices.get(y));
        }
        return new ArrayList<>(vertices.values());
    }
}

class TopoVertex {
    int Id;
    List<TopoVertex> connected;

    public TopoVertex(int id) {
        Id = id;
        connected = new ArrayList<>();
    }

    void addVertex(TopoVertex vertx) {
        connected.add(vertx);
    }
}