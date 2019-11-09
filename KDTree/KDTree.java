package bearmaps;

import java.util.Collections;
import java.util.List;

public class KDTree implements PointSet {
    List<Point> points;
    private Node root;
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;

    private class Node {
        private Node left, right; //left refers to down child, right refers to up child
        private Point p;
        private boolean orientation; //Horizontal : false, Vertical : true

        Node(Point p, boolean orientation) {
            this.p = p;
            this.orientation = orientation;
            this.left = null;
            this.right = null;
        }

        private boolean hasLeft() {
            return left != null;
        }

        private boolean hasRight() {
            return right != null;
        }

        private double distance(Point pp) {
            return getDist(pp.getX(), p.getX(), pp.getY(), p.getY());
        }

        private double bestDist(Point pp) {
            if (orientation == HORIZONTAL) {
                return getDist(pp.getX(), p.getX(), 0, 0);
            } else {
                return getDist(0, 0, pp.getY(), p.getY());
            }
        }

        private int compare(Point pp) { //Compares according to n's comparator
            if (orientation == HORIZONTAL) {
                if (pp.getX() < p.getX()) {
                    return 1;
                } else {
                    return -1;
                }
            } else { //Vertical orientation
                if (pp.getY() < p.getY()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }

    public KDTree(List<Point> points) {
        this.points = points;
        for (Point p : points) {
            root = add(root, p, HORIZONTAL);
        }
    }

    private Node add(Node n, Point p, boolean orientation) {
        if (n == null) {
            return new Node(p, orientation);
        }
        if (p.equals(n.p)) { //If points are equal, there is literally nothing to change
            return n;
        }
        int compare = comparePoints(n.p, p, orientation);
        if (compare > 0) {
            n.left = add(n.left, p, !orientation);
        } else if (compare <= 0) {
            n.right = add(n.right, p, !orientation);
        }
        return n;
    }

    private int comparePoints(Point a, Point b, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(a.getX(), b.getX());
        } else {
            return Double.compare(a.getY(), b.getY());
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Node n = root;
        double err = Integer.MAX_VALUE;
        Point pp = new Point(x, y);
        return nearest(n, pp, n).p;
    }

    private Node nearest(Node n, Point goal, Node best) {
        Node goodSide, badSide;
        if (n == null) {
            return best;
        }
        if (n.distance(goal) < best.distance(goal)) {
            best = n;
        }
        if (n.compare(goal) > 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearest(goodSide, goal, best);
        if (n.bestDist(goal) <= best.distance(goal)) { //Green pruning rule
            best = nearest(badSide, goal, best);
        }
        return best;
    }

    private double getDist(double x1, double x2, double y1, double y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }
}
