import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
	private SET<Point2D> points;
	
	public PointSET() {
		points = new SET<>();
	}

	public boolean isEmpty() {
		return points.isEmpty();
	}

	public int size() {
		return points.size();
	}

	public void insert(Point2D p) {
		points.add(p);
	}

	public boolean contains(Point2D p) {
		return points.contains(p);
	}
	
	public void draw() {
		for (Point2D p : points)
			p.draw();
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new IllegalArgumentException();
		Queue<Point2D> pq = new Queue<>();
		for (Point2D p : points) {
			if (rect.contains(p))
				pq.enqueue(p);
		}
		return pq;
	}
	
	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		double dist;
		double minDist = Double.POSITIVE_INFINITY;
		Point2D minPoint = null;
		for (Point2D point : points) {
			dist = p.distanceSquaredTo(point);
			if (dist < minDist) {
				minDist = dist;
				minPoint = point;
			}
		}
		return minPoint;
	}

}
