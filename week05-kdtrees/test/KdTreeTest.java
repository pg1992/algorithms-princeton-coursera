import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTreeTest {

	@Test
	public void testKdTreeNotEmptyAfterInsert() {
		KdTree kd = new KdTree();
		kd.insert(new Point2D(0, 0));
		assertFalse(kd.isEmpty());
	}

	@Test
	public void testKdTreeEmptyAfterCreation() {
		KdTree kd = new KdTree();
		assertTrue(kd.isEmpty());
	}

	@Test
	public void testInsertNDistinctPoints() {
		KdTree kd = new KdTree();
		for (int i = 0; i < 10; i++)
			kd.insert(new Point2D(i, i));
		assertEquals(10, kd.size());
	}

	@Test
	public void testContainsNullThrowsIllegalArgumentException() {
		KdTree kd = new KdTree();

		try {
			kd.contains(null);
		} catch (IllegalArgumentException ex) {
			return;
		}

		fail("IllegalArgumentException was not raised.");
	}

	@Test
	public void testInsertNullThrowsIllegalArgumentException() {
		KdTree kd = new KdTree();

		try {
			kd.insert(null);
		} catch (IllegalArgumentException ex) {
			return;
		}

		fail("IllegalArgumentException was not raised.");
	}

	@Test
	public void testNearestNullThrowsIllegalArgumentException() {
		KdTree kd = new KdTree();

		try {
			kd.nearest(null);
		} catch (IllegalArgumentException ex) {
			return;
		}

		fail("IllegalArgumentException was not raised.");
	}

	@Test
	public void testRangeNullThrowsIllegalArgumentException() {
		KdTree kd = new KdTree();

		try {
			kd.range(null);
		} catch (IllegalArgumentException ex) {
			return;
		}

		fail("IllegalArgumentException was not raised.");
	}

	@Test
	public void testContainsPoints() {
		KdTree kd = new KdTree();
		Point2D points[] = new Point2D[] { new Point2D(0, 0), new Point2D(0, 1), new Point2D(1, 0),
				new Point2D(1, 1), };
		Point2D pointsOutside[] = new Point2D[] { new Point2D(2, 2), new Point2D(2, 3), new Point2D(3, 2),
				new Point2D(3, 3), };

		for (Point2D p : points)
			kd.insert(p);

		for (Point2D p : points)
			assertTrue(kd.contains(p));

		for (Point2D p : pointsOutside)
			assertFalse(kd.contains(p));
	}

	@Test
	public void testInsertDuplicatesDoesNotChangeSize() {
		KdTree kd = new KdTree();

		for (int i = 0; i < 5; i++) {
			kd.insert(new Point2D(i, i));
			kd.insert(new Point2D(i, i));
		}

		assertEquals(5, kd.size());
	}

	@Test
	public void testNearestPoint() {
		KdTree kd = new KdTree();

		Point2D ul = new Point2D(0.0, 1.0);
		Point2D um = new Point2D(0.5, 1.0);
		Point2D ur = new Point2D(1.0, 1.0);

		Point2D ml = new Point2D(0.0, 0.5);
		Point2D mm = new Point2D(0.5, 0.5);
		Point2D mr = new Point2D(1.0, 0.5);

		Point2D bl = new Point2D(0.0, 0.0);
		Point2D bm = new Point2D(0.5, 0.0);
		Point2D br = new Point2D(1.0, 0.0);

		kd.insert(ul);
		kd.insert(um);
		kd.insert(ur);

		kd.insert(ml);
		kd.insert(mm);
		kd.insert(mr);

		kd.insert(bl);
		kd.insert(bm);
		kd.insert(br);

		assertEquals(bl, kd.nearest(new Point2D(0.1, 0.1)));
		assertEquals(mm, kd.nearest(new Point2D(0.5, 0.6)));
		assertEquals(ur, kd.nearest(new Point2D(0.9, 1.0)));
		assertEquals(ml, kd.nearest(new Point2D(0.2, 0.3)));
		assertEquals(bl, kd.nearest(new Point2D(0.2, 0.2)));
		assertEquals(ul, kd.nearest(new Point2D(0.2, 0.9)));
		assertEquals(bm, kd.nearest(new Point2D(0.6, 0.1)));
		assertEquals(mr, kd.nearest(new Point2D(0.9, 0.6)));
		assertEquals(br, kd.nearest(new Point2D(0.9, 0.0)));
	}

	@Test
	public void testNullRangeThrowsIllegalArgumentException() {
		KdTree kd = new KdTree();

		try {
			kd.range(null);
		} catch (IllegalArgumentException ex) {
			return;
		}

		fail("IllegalArgumentException was not raised.");
	}

	@Test
	public void testRangeContainsPoint() {
		KdTree kd = new KdTree();
		Point2D point = new Point2D(.5, .5);
		kd.insert(point);
		boolean found = false;
		for (Point2D p : kd.range(new RectHV(.4, .4, .6, .6)))
			if (p.equals(point))
				found = true;
		assertTrue(found);
	}

	@Test
	public void testRangeDoesNotContainPoint() {
		KdTree kd = new KdTree();
		Point2D point = new Point2D(.2, .5);
		kd.insert(point);
		boolean found = false;
		for (Point2D p : kd.range(new RectHV(.4, .4, .6, .6)))
			if (p.equals(point))
				found = true;
		assertFalse(found);
	}

}
