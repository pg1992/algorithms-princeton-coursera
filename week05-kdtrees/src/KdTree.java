import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

	private Node root;
	private int size;

	public KdTree() {
		root = null;
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		root = insert(root, p, false);
	}

	private Node insert(Node n, Point2D p, boolean h) {
		if (n == null) {
			n = new Node(p, h);
			size++;
		}

		if (!n.point.equals(p)) {
			if (n.horizontal) {
				if (p.y() > n.point.y())
					n.right = insert(n.right, p, !h);
				else
					n.left = insert(n.left, p, !h);
			} else {
				if (p.x() > n.point.x())
					n.right = insert(n.right, p, !h);
				else
					n.left = insert(n.left, p, !h);
			}
		}

		return n;
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();

		return contains(root, p);
	}

	private boolean contains(Node n, Point2D p) {
		if (n == null)
			return false;
		else if (n.point.equals(p))
			return true;
		else {
			if (n.horizontal) {
				if (p.y() > n.point.y())
					return contains(n.right, p);
				else
					return contains(n.left, p);
			} else {
				if (p.x() > n.point.x())
					return contains(n.right, p);
				else
					return contains(n.left, p);
			}
		}
	}

	public void draw() {
		if (root == null)
			return;
		draw(root, new RectHV(0, 0, 1, 1));
	}

	private void draw(Node n, RectHV rect) {
		if (n == null)
			return;

		double xmin = rect.xmin();
		double ymin = rect.ymin();
		double xmax = rect.xmax();
		double ymax = rect.ymax();
		double x = n.point.x();
		double y = n.point.y();

		RectHV leftRect;
		RectHV rightRect;

		StdDraw.setPenRadius();
		if (n.horizontal) {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(xmin, y, xmax, y);
			leftRect = new RectHV(xmin, ymin, xmax, y);
			rightRect = new RectHV(xmin, y, xmax, ymax);
		} else {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(x, ymin, x, ymax);
			leftRect = new RectHV(xmin, ymin, x, ymax);
			rightRect = new RectHV(x, ymin, xmax, ymax);
		}

		StdDraw.setPenColor();
		StdDraw.setPenRadius(.01);
		n.point.draw();

		draw(n.left, leftRect);
		draw(n.right, rightRect);
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new IllegalArgumentException();
		return range(root, rect, new RectHV(0, 0, 1, 1));
	}

	private Iterable<Point2D> range(Node n, RectHV rect, RectHV limits) {
		if (n == null || !rect.intersects(limits))
			return new Queue<Point2D>();

		double xmin = limits.xmin();
		double xmax = limits.xmax();
		double ymin = limits.ymin();
		double ymax = limits.ymax();
		double x = n.point.x();
		double y = n.point.y();

		RectHV leftLimits;
		RectHV rightLimits;

		if (n.horizontal) {
			leftLimits = new RectHV(xmin, ymin, xmax, y);
			rightLimits = new RectHV(xmin, y, xmax, ymax);
		} else {
			leftLimits = new RectHV(xmin, ymin, x, ymax);
			rightLimits = new RectHV(x, ymin, xmax, ymax);
		}

		Iterable<Point2D> leftContained = range(n.left, rect, leftLimits);
		Iterable<Point2D> rightContained = range(n.right, rect, rightLimits);
		Queue<Point2D> contained = new Queue<>();

		if (rect.contains(n.point))
			contained.enqueue(n.point);

		for (Point2D p : leftContained)
			contained.enqueue(p);
		for (Point2D p : rightContained)
			contained.enqueue(p);

		return contained;
	}

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();

		return nearest(root, p, new RectHV(0, 0, 1, 1), Double.POSITIVE_INFINITY);
	}

	private Point2D nearest(Node n, Point2D p, RectHV limits, double minDist) {
		if (n == null)
			return null;

		double xmin = limits.xmin();
		double xmax = limits.xmax();
		double ymin = limits.ymin();
		double ymax = limits.ymax();
		double x = n.point.x();
		double y = n.point.y();

		RectHV leftLimits;
		RectHV rightLimits;

		if (n.horizontal) {
			leftLimits = new RectHV(xmin, ymin, xmax, y);
			rightLimits = new RectHV(xmin, y, xmax, ymax);
		} else {
			leftLimits = new RectHV(xmin, ymin, x, ymax);
			rightLimits = new RectHV(x, ymin, xmax, ymax);
		}

		double dist = n.point.distanceSquaredTo(p);
		Point2D pl = null;
		Point2D pr = null;
		
		if (n.horizontal) {
			if (p.y() > n.point.y()) {
				if (rightLimits.distanceSquaredTo(p) < minDist)
					pr = nearest(n.right, p, rightLimits, Math.min(dist, minDist));
				if (pr != null)
					minDist = Math.min(p.distanceSquaredTo(pr), minDist);
				if (leftLimits.distanceSquaredTo(p) < minDist)
					pl = nearest(n.left, p, leftLimits, Math.min(dist, minDist));
				if (pl != null)
					minDist = Math.min(p.distanceSquaredTo(pl), minDist);
			} else {
				if (leftLimits.distanceSquaredTo(p) < minDist)
					pl = nearest(n.left, p, leftLimits, Math.min(dist, minDist));
				if (pl != null)
					minDist = Math.min(p.distanceSquaredTo(pl), minDist);
				if (rightLimits.distanceSquaredTo(p) < minDist)
					pr = nearest(n.right, p, rightLimits, Math.min(dist, minDist));
				if (pr != null)
					minDist = Math.min(p.distanceSquaredTo(pr), minDist);
			}
		} else {
			if (p.x() > n.point.x()) {
				if (rightLimits.distanceSquaredTo(p) < minDist)
					pr = nearest(n.right, p, rightLimits, Math.min(dist, minDist));
				if (pr != null)
					minDist = Math.min(p.distanceSquaredTo(pr), minDist);
				if (leftLimits.distanceSquaredTo(p) < minDist)
					pl = nearest(n.left, p, leftLimits, Math.min(dist, minDist));
				if (pl != null)
					minDist = Math.min(p.distanceSquaredTo(pl), minDist);
			} else {
				if (leftLimits.distanceSquaredTo(p) < minDist)
					pl = nearest(n.left, p, leftLimits, Math.min(dist, minDist));
				if (pl != null)
					minDist = Math.min(p.distanceSquaredTo(pl), minDist);
				if (rightLimits.distanceSquaredTo(p) < minDist)
					pr = nearest(n.right, p, rightLimits, Math.min(dist, minDist));
				if (pr != null)
					minDist = Math.min(p.distanceSquaredTo(pr), minDist);
			}
		}

		if (pl == null && pr == null)
			return dist < minDist ? n.point : null;
		else if (pl == null)
			return pr;
		else if (pr == null)
			return pl;
		else if (pl.distanceSquaredTo(p) < pr.distanceSquaredTo(p))
			return pl;
		else
			return pr;
	}

	private class Node {
		Point2D point;
		boolean horizontal;
		Node left, right;

		Node(Point2D point, boolean horizontal) {
			this.point = point;
			this.horizontal = horizontal;
		}
	}

}
