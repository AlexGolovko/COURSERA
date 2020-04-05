import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reachability {
    public static void main(String[] args) {
        System.out.println(new Reach(new StaticScanner()).isReachable());
    }
}

class StaticScanner {
    int x;
    int y;
    List<Vertex> vertexes;

    StaticScanner() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        vertexes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            vertexes.add(new Vertex(i));
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
            vertexes.get(x).edges.add(vertexes.get(y));
            vertexes.get(y).edges.add(vertexes.get(x));
        }
        x = scanner.nextInt() - 1;
        y = scanner.nextInt() - 1;
    }
}

class Reach {
    List<Vertex> vertx;
    int start;
    int end;
    List<Integer> visited;
    int isReachable = 0;

    public Reach(StaticScanner scanner) {
        this.vertx = scanner.vertexes;
        start = scanner.x;
        end = scanner.y;
    }

    int isReachable() {
        visited = new ArrayList<>();
        explore(start);
        return isReachable;
    }

    private void explore(int vertxId) {
        visited.add(vertxId);
        for (Vertex edge : vertx.get(vertxId).edges) {
            if (edge.id == end||isReachable==1) {
                isReachable = 1;
                return;
            }
            if (visited.contains(edge.id)) continue;
            explore(edge.id);
        }
    }
}

class Vertex {
    int id;
    List<Vertex> edges;

    public Vertex(int id) {
        this.id = id;
        edges = new ArrayList<>();
    }
}