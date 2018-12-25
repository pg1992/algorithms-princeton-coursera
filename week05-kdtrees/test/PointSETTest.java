import static org.junit.Assert.*;

import org.junit.Test;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSETTest {

	@Test
	public void testNewPointSETIsEmpty() {
		PointSET ps = new PointSET();
		assertTrue(ps.isEmpty());
	}
	
	@Test
	public void testIncludeSinglePoint() {
		PointSET ps = new PointSET();
		ps.insert(new Point2D(0, 0));
		assertFalse(ps.isEmpty());
	}
	
	@Test
	public void testInclude5DistinctPoints() {
		PointSET ps = new PointSET();
		
		for (int i = 0; i < 5; i++)
			ps.insert(new Point2D(i, 0));
		
		assertFalse(ps.isEmpty());
		assertEquals(5, ps.size());
	}
	
	@Test
	public void testPointSETContainsPoint() {
		PointSET ps = new PointSET();
		
		ps.insert(new Point2D(1, 2));
		
		assertTrue(ps.contains(new Point2D(1, 2)));
	}
	
	@Test
	public void testPointSETDoesNotContainPoint() {
		PointSET ps = new PointSET();
		
		ps.insert(new Point2D(1, 2));
		
		assertFalse(ps.contains(new Point2D(2, 1)));
	}
	
	@Test
	public void testInsertNullThrowsIllegalArgumentException() {
		PointSET ps = new PointSET();
		
		try {
			ps.insert(null);
		}
		catch (IllegalArgumentException ex) {
			return;
		}
		
		fail("IllegalArgumentException was not raised.");
	}
	
	@Test
	public void testContainsNullThrowsIllegalArgumentException() {
		PointSET ps = new PointSET();
		
		try {
			ps.contains(null);
		}
		catch (IllegalArgumentException ex) {
			return;
		}
		
		fail("IllegalArgumentException was not raised.");
	}
	
	@Test
	public void testRegionContainsPoint() {
		PointSET ps = new PointSET();
		Point2D point = new Point2D(.5, .5);
		RectHV region = new RectHV(.4, .4, .6, .6);
		boolean contains = false;

		ps.insert(point);
		
		for (Point2D p : ps.range(region)) {
			contains = contains || p.equals(point);
		}
		
		assertTrue(contains);
	}
	
	@Test
	public void testNullRangeThrowsIllegalArgumentException() {
		PointSET ps = new PointSET();
		ps.insert(new Point2D(.5, .5));
		
		try {
			ps.range(null);
		}
		catch (IllegalArgumentException ex) {
			return;
		}
		
		fail("IllegalArgumentException was not raised.");
	}
	
	@Test
	public void testRegionDoesNotContainPoint() {
		PointSET ps = new PointSET();
		Point2D point = new Point2D(.5, .7);
		RectHV region = new RectHV(.4, .4, .6, .6);
		boolean contains = false;

		ps.insert(point);
		
		for (Point2D p : ps.range(region)) {
			contains = contains || p.equals(point);
		}
		
		assertFalse(contains);
	}
	
	@Test
	public void testNearestNullThrowsIllegalArgumentException() {
		PointSET ps = new PointSET();
		ps.insert(new Point2D(.5, .5));
		
		try {
			ps.nearest(null);
		}
		catch (IllegalArgumentException ex) {
			return;
		}
		
		fail("IllegalArgumentException was not raised.");
	}
	
	@Test
	public void testNearestPoint() {
		PointSET ps = new PointSET();
		
		Point2D bottomLeft = new Point2D(0, 0);
		Point2D center = new Point2D(.5, .5);
		Point2D upperRight = new Point2D(1, 1);
		
		Point2D target;
		Point2D expected;

		ps.insert(bottomLeft);
		ps.insert(center);
		ps.insert(upperRight);

		target = new Point2D(.5, .6); 
		expected = center;
		assertEquals(expected, ps.nearest(target));
		
		target = new Point2D(0, .1); 
		expected = bottomLeft;
		assertEquals(expected, ps.nearest(target));

		target = new Point2D(1, .9); 
		expected = upperRight;
		assertEquals(expected, ps.nearest(target));
	}

}
