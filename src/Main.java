import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static Point[] closestPair(Point[] points) {
        Point[] res = new Point[2];
        double currMin = Double.POSITIVE_INFINITY;
        Point[] sorted = Arrays.copyOf(points, points.length);
        Arrays.sort(sorted, HORIZONTAL_COMPARATOR);
        int leftMostCandidateIndex = 0;
        SortedSet<Point> candidates = new TreeSet(VERTICAL_COMPARATOR);
        int n = 0;
        for (Point current : sorted) {
            while (current.x - sorted[leftMostCandidateIndex].x > currMin) {
                candidates.remove(sorted[leftMostCandidateIndex]);
                leftMostCandidateIndex++;
            }
            Point head = new Point(current.x, (int) (current.y - currMin));
            Point tail = new Point(current.x, (int) (current.y + currMin));
            for (Point point : candidates.subSet(head, tail)) {
                double distance = current.distance(point);
                n ++;
                if (distance < currMin) {
                    currMin = distance;
                    res[0] = current;
                    res[1] = point;
                }
            }
            candidates.add(current);
        }
        System.out.println("Расстояние считалось " + n + " раз");
        return res;
    }

    private static final Comparator<Point> VERTICAL_COMPARATOR = new Comparator<Point>() {
        @Override
        public int compare(Point a, Point b) {
            if (a.y < b.y) {
                return -1;
            }
            if (a.y > b.y) {
                return 1;
            }
            if (a.x < b.x) {
                return -1;
            }
            if (a.x > b.x) {
                return 1;
            }
            return 0;
        }
    };

    private static final Comparator<Point> HORIZONTAL_COMPARATOR = new Comparator<Point>() {
        @Override
        public int compare(Point a, Point b) {
            if (a.x < b.x) {
                return -1;
            }
            if (a.x > b.x) {
                return 1;
            }
            if (a.y < b.y) {
                return -1;
            }
            if (a.y > b.y) {
                return 1;
            }
            return 0;
        }
    };

    public static void main(String[] args){
        Point[] points = new Point[1000];
        for(int i = 0; i < 1000; i++){
            int x = ThreadLocalRandom.current().nextInt(-10000, 10001);
            int y = ThreadLocalRandom.current().nextInt(-10000, 10001);
            points[i] = new Point(x, y);
        }
        Point[] res = closestPair(points);
        System.out.println("Точка раз (" + res[0].x + ", " + res[0].y +")");
        System.out.println("Точка два (" + res[1].x + ", " + res[1].y +")");
        System.out.println("Расстояние " + res[1].distance(res[0]));
    }
}
