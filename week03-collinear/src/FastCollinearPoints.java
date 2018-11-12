import java.util.Arrays;

public class FastCollinearPoints {

    private int nsegs;
    private Point[][] endPoints = new Point[0][2];
    private LineSegment[] segs;

    public FastCollinearPoints(Point[] inputPoints) {
        validate(inputPoints);

        Point[] points = new Point[inputPoints.length];
        for (int i = 0; i < points.length; i++)
            points[i] = inputPoints[i];

        for (int i = 0; i < points.length; i++) {
            Point p = inputPoints[i];
            Arrays.sort(points, p.slopeOrder());

            int count = 1;
            for (int j = 2; j < points.length; j++) {
                if (p.slopeTo(points[j]) == p.slopeTo(points[j - 1])) {
                    count++;
                }
                else if (count > 2) {
                    Point max = p;
                    Point min = p;
                    for (int k = 0; k < count; k++) {
                        int index = j - k - 1;
                        if (points[index].compareTo(max) > 0) max = points[index];
                        if (points[index].compareTo(min) < 0) min = points[index];
                    }
                    includeSegment(min, max);
                    count = 1;
                }
                else {
                    count = 1;
                }
            }
            if (count > 2) {
                Point max = p;
                Point min = p;
                for (int k = 0; k < count; k++) {
                    int index = points.length - k - 1;
                    if (points[index].compareTo(max) > 0) max = points[index];
                    if (points[index].compareTo(min) < 0) min = points[index];
                }
                includeSegment(min, max);
            }
        }

        segs = new LineSegment[nsegs];
    }

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

    private void includeSegment(Point min, Point max) {
        for (Point[] pair : endPoints)
            if (pair[0] == min && pair[1] == max)
                return;

        nsegs++;

        if (endPoints.length <= nsegs) resize(2 * nsegs);
        endPoints[nsegs - 1] = new Point[] {min, max};
    }

    private void resize(int size) {
        Point[][] newPoints = new Point[size][2];
        for (int i = 0; i < endPoints.length; i++)
            newPoints[i] = endPoints[i];
        endPoints = newPoints;
    }

    public int numberOfSegments() {
        return nsegs;
    }

    public LineSegment[] segments() {
        for (int i = 0; i < nsegs; i++)
            segs[i] = new LineSegment(endPoints[i][0], endPoints[i][1]);

        return segs;
    }

}
