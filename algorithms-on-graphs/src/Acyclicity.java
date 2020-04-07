import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Acyclicity {

    public static void main(String[] args) {
        System.out.println(new Cycle(new DirectedScanner().scan()).hasCycle());
    }
}

class Cycle {
    private DirectedScanner scanner;

    public Cycle(DirectedScanner scan) {
        scanner = scan;
    }

    public int hasCycle() {
        AtomicInteger isCycle = new AtomicInteger(0);
        for (DirectedVertex vertex : scanner.vertices) {
            if (isCycle.get() == 1) break;
            HashSet<DirectedVertex> visited = new HashSet<>();
            explore(visited, vertex, isCycle);

        }
        return isCycle.get();
    }


    private void explore(HashSet<DirectedVertex> visited, DirectedVertex vertex, AtomicInteger isCycle) {
        if (visited.contains(vertex)) {
            isCycle.set(1);
            return;
        }
        visited.add(vertex);
        vertex.getVertexes().forEach(it -> {
                    if (isCycle.get() != 1) {
                        explore(visited, it, isCycle);
                        visited.remove(it);
                    }
                }
        );
    }
}

class DirectedScanner {
    ArrayList<DirectedVertex> vertices;

    public DirectedScanner scan() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        vertices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            vertices.add(new DirectedVertex(i));
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
            vertices.get(x).addConnection(vertices.get(y));
        }
        return this;
    }
}

class DirectedVertex {

    private int id;

    public List<DirectedVertex> getVertexes() {
        return vertexes;
    }

    private List<DirectedVertex> vertexes;

    public DirectedVertex(int id) {
        this.id = id;
        this.vertexes = new ArrayList<>();
    }

    public void addConnection(DirectedVertex vertex) {
        vertexes.add(vertex);
    }

    public int getId() {
        return id;
    }
}
