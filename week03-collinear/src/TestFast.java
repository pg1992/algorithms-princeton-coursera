import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class TestFast {

    public static void main(String[] args) {

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
        StdOut.println("\nThere are " + collinear.numberOfSegments() + " segments");
        StdDraw.show();

        for (int i = 0; i < points.length; i++)
            points[i] = new Point(0, 0);

        LineSegment[] segments = collinear.segments();
        for (int i = 0; i < segments.length; i++)
            segments[i] = new LineSegment(new Point(0, 0), new Point(0, 0));

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println("\nThere are " + collinear.numberOfSegments() + " segments");
        StdDraw.show();
    }

}
