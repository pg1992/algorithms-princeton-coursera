import java.util.Arrays;

public class BruteCollinearPoints {

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

    public BruteCollinearPoints(Point[] points) {
        validate(points);

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];

            for (int j = i+1; j < points.length; j++) {
                Point q = points[j];
                double slopePQ = p.slopeTo(q);

                for (int k = j+1; k < points.length; k++) {
                    Point r = points[k];
                    double slopePR = p.slopeTo(r);

                    if (slopePQ != slopePR) continue;

                    for (int l = k+1; l < points.length; l++) {
                        Point s = points[l];
                        double slopePS = p.slopeTo(s);

                        if (slopePS == slopePR)
                            includeSegment(new Point[] {p, q, r, s});
                    }
                }
            }
        }
    }

    private void includeSegment(Point[] points) {
        Arrays.sort(points);
        LineSegment cur = new LineSegment(points[0], points[points.length - 1]);

        if (segments != null)
            for (LineSegment segment : segments)
                if (segment.toString().equals(cur.toString())) return;

        LineSegment[] tmp = new LineSegment[numberOfSegments + 1];
        for (int i = 0; i < numberOfSegments; i++)
            tmp[i] = segments[i];

        segments = tmp;
        segments[numberOfSegments++] = cur;
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return segments;
    }


    public static void main(String[] args) {
    }

}
