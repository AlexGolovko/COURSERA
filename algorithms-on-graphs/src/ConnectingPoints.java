import java.util.*;

public class ConnectingPoints {

    public static void main(String[] args) {
        System.out.println(minDist());
    }

    public static double minDist() {
        double distance = 0;
        Plot plot = Plot.fromScan().buildEdges();
        TreeSet<Point> pointsTree = new TreeSet<>();
        pointsTree.add(plot.points.get(0));
        PriorityQueue<Edge> edges = new PriorityQueue<>(plot.points.get(0).edges);
        while (pointsTree.size() != plot.points.size()) {
            Edge poll = edges.poll();
            if (poll != null && pointsTree.contains(poll.to) && !pointsTree.contains(poll.from)) {
                pointsTree.add(poll.from);
                edges.addAll(poll.from.edges);
                distance += poll.distance;
            }
            if (poll != null && !pointsTree.contains(poll.to) && pointsTree.contains(poll.from)) {
                pointsTree.add(poll.to);
                edges.addAll(poll.to.edges);
                distance += poll.distance;
            }
        }
        return distance;
    }

    public static class Plot {
        final List<Point> points;
        final Queue<Edge> edges;

        public Plot(int n) {
            points = new ArrayList<>(n);
            edges = new PriorityQueue<Edge>();
        }

        public static Plot fromScan() {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            Plot plot = new Plot(n);
            for (int i = 0; i < n; i++) {
                plot.points.add(new Point(i, scanner.nextInt(), scanner.nextInt()));
            }
            return plot;
        }

        public Plot buildEdges() {
            for (int from = 0; from < points.size(); from++) {
                for (int to = from + 1; to < points.size(); to++) {
                    Point pointTo = points.get(to);
                    Point pointFrom = points.get(from);
                    double ac = Math.abs(pointTo.x - pointFrom.x);
                    double cb = Math.abs(pointTo.y - pointFrom.y);
                    Edge edge = new Edge(pointFrom, pointTo, Math.hypot(ac, cb));
                    pointFrom.edges.add(edge);
                    pointTo.edges.add(edge);
                    edges.add(edge);
                }
            }
            return this;
        }
    }

    public static class Edge implements Comparable<Edge> {
        Point from;
        Point to;
        double distance;

        public Edge(Point from, Point to, double distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }

        @Override
        public int compareTo(Edge edge) {
            return Double.compare(this.distance, edge.distance);
        }
    }

    public static class Point implements Comparable<Point> {
        final int id;
        final int x;
        final int y;
        final List<Edge> edges;

        public Point(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.edges = new ArrayList<>();
        }

        @Override
        public int compareTo(Point point) {
            return Integer.compare(id, point.id);
        }
    }
}

