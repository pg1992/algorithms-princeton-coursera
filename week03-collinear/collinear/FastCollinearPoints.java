import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class FastCollinearPoints {

    private int numberOfSegments;
    private LineSegment[] segments = new LineSegment[0];

    private void validate(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Argument can't be null");

        for (Point p: points)
            if (p == null)
                throw new IllegalArgumentException("No point can be null");

        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Duplicated points");
    }

    public FastCollinearPoints(Point[] points) {
        validate(points);

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Arrays.sort(points, i + 1, points.length, p.slopeOrder());

            int count = 1;
            for (int j = i + 2; j < points.length; j++) {
                if (p.slopeTo(points[j]) == p.slopeTo(points[j - 1])) {
                    count++;
                }
                else if (count > 2) {
                    Point[] thisPoints = new Point[count + 1];
                    thisPoints[0] = p;
                    for (int k = 0; k < count; k++)
                        thisPoints[k + 1] = points[j - k - 1];
                    insertSegment(thisPoints);
                    count = 1;
                }
                else {
                    count = 1;
                }
            }
            if (count > 2) {
                Point[] thisPoints = new Point[count + 1];
                thisPoints[0] = p;
                for (int k = 0; k < count; k++)
                    thisPoints[k + 1] = points[points.length - k - 1];
                insertSegment(thisPoints);
            }
        }
    }

    private void insertSegment(Point[] points) {
        if (segments == null)
            segments = new LineSegment[1];
        Arrays.sort(points);
        LineSegment ls = new LineSegment(points[0], points[points.length - 1]);
        for (int i = 0; i < numberOfSegments; i++)
            if (segments[i].toString().equals(ls.toString()))
                return;
        LineSegment[] tmp = new LineSegment[numberOfSegments + 1];
        for (int i = 0; i < numberOfSegments; i++)
            tmp[i] = segments[i];
        tmp[numberOfSegments++] = ls;
        segments = tmp;
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {
        // Testing exceptions
        try {
            FastCollinearPoints b = new FastCollinearPoints(null);
        }
        catch (IllegalArgumentException ex) {
            StdOut.println(ex);
        }

        try {
            FastCollinearPoints b = new FastCollinearPoints(
                    new Point[] {null, new Point(0, 0)}
                    );
        }
        catch (IllegalArgumentException ex) {
            StdOut.println(ex);
        }

        try {
            FastCollinearPoints b = new FastCollinearPoints(
                    new Point[] {
                        new Point(1, 2),
                        new Point(0, 0),
                        new Point(1, 2),
                        new Point(0, 0),
                    }
            );
        }
        catch (IllegalArgumentException ex) {
            StdOut.println(ex);
        }

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println("There are " + collinear.numberOfSegments() + " segments");
        StdDraw.show();
    }

}
