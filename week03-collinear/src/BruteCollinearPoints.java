public class BruteCollinearPoints {

    private int nsegs;
    private Point[][] endPoints = new Point[0][2];
    private LineSegment[] segs;

    public BruteCollinearPoints(Point[] inputPoints) {
        validate(inputPoints);

        Point[] points = new Point[inputPoints.length];
        for (int i = 0; i < points.length; i++)
            points[i] = inputPoints[i];

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

                        if (slopePS == slopePR) {
                            Point min = p;
                            Point max = p;

                            for (Point pt : new Point[] {q, r, s}) {
                                if (pt.compareTo(max) > 0) max = pt;
                                if (pt.compareTo(min) < 0) min = pt;
                            }
                            includeSegment(min, max);
                        }
                    }
                }
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
