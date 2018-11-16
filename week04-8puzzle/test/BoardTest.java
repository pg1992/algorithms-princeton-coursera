import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BoardTest {

	@Test
	public void immutabilityTest() {
		Board b1, b2;
		int[][] initial = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }, };

		b1 = new Board(initial);
		initial[0][2] = 10;
		initial[2][0] = 20;
		b2 = new Board(initial);

		assertNotEquals(b2, b1);
	}

	@Test
	public void dimensionTest() {
		Board b = new Board(new int[5][5]);

		assertEquals(5, b.dimension());
	}

	@Test
	public void hammingTest3_1() {
		Board b = new Board(new int[][] { { 1, 0, 3 }, { 4, 2, 5 }, { 7, 8, 6 }, });

		assertEquals(3, b.hamming());
	}

	@Test
	public void hammingTest3_2() {
		Board b = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 }, });

		assertEquals(5, b.hamming());
	}

	@Test
	public void hammingTest4() {
		Board b = new Board(new int[][] { { 1, 0, 3, 9 }, { 4, 2, 5, 10 }, { 7, 8, 6, 11 }, { 15, 14, 13, 12 }, });

		assertEquals(12, b.hamming());
	}

	@Test
	public void isGoalTrueTest() {
		Board b = new Board(new int[][] { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 },
				{ 16, 17, 18, 19, 20 }, { 21, 22, 23, 24, 0 }, });

		assertTrue(b.isGoal());
	}

	@Test
	public void isGoalFalseTest() {
		Board b = new Board(new int[][] { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 10, 9 }, { 11, 12, 13, 14, 15 },
				{ 16, 17, 18, 19, 20 }, { 21, 22, 23, 24, 0 }, });

		assertFalse(b.isGoal());
	}

	@Test
	public void manhattanTest3_1() {
		Board b = new Board(new int[][] { { 4, 1, 3 }, { 0, 2, 5 }, { 7, 8, 6 }, });

		assertEquals(5, b.manhattan());
	}

	@Test
	public void manhattanTest3_2() {
		Board b = new Board(new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 }, });

		assertEquals(4, b.manhattan());
	}

	@Test
	public void manhattanTest3_3() {
		Board b = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 }, });

		assertEquals(10, b.manhattan());
	}

	@Test
	public void equalityNullTest() {
		Board b = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }, });

		assertFalse(b.equals(null));
	}

	@Test
	public void equalitySelfTest() {
		Board b = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }, });

		assertTrue(b.equals(b));
	}

	@Test
	public void equalityDifferentBoards() {
		Board b1 = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 8, 9, 0 }, });

		Board b2 = new Board(new int[][] { { 2, 1, 3 }, { 4, 5, 6 }, { 8, 9, 0 }, });

		assertFalse(b1.equals(b2));
	}

	@Test
	public void equalityDifferentClassesTest() {
		Board b = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }, });

		assertFalse(b.equals(new Object()));
	}

	@Test
	public void equalityDifferentObjectSameContentTest() {
		int[][] content = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }, };

		Board b1 = new Board(content);
		Board b2 = new Board(content);

		assertTrue(b1 != b2);
		assertTrue(b1.equals(b2));
	}

	@Test
	public void equalityDifferentDimensionTest() {
		Board b1 = new Board(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 }, });

		Board b2 = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }, });

		assertFalse(b1.equals(b2));
	}

	@Test
	public void toStringTest() {
		String expected = "3\n" + " 0  1  3 \n" + " 4  2  5 \n" + " 7  8  6 \n";

		Board b = new Board(new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 }, });

		assertEquals(expected, b.toString());
	}

	@Test
	public void twinTest3_1() {
		Board b = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }, });

		Board bTwin = new Board(new int[][] { { 2, 1, 3 }, { 4, 5, 6 }, { 7, 8, 0 }, });

		assertTrue(bTwin.equals(b.twin()));
	}

	@Test
	public void twinTest3_2() {
		Board b = new Board(new int[][] { { 0, 2, 3 }, { 4, 5, 6 }, { 7, 8, 1 }, });

		Board bTwin = new Board(new int[][] { { 0, 3, 2 }, { 4, 5, 6 }, { 7, 8, 1 }, });

		assertTrue(bTwin.equals(b.twin()));
	}

	@Test
	public void twinTest3_3() {
		Board b = new Board(new int[][] { { 5, 0, 3 }, { 4, 2, 6 }, { 7, 8, 1 }, });

		Board bTwin = new Board(new int[][] { { 5, 0, 3 }, { 2, 4, 6 }, { 7, 8, 1 }, });

		assertTrue(bTwin.equals(b.twin()));
	}

	@Test
	public void neighborsLengthTest3_1() {
		Board b = new Board(new int[][] { { 8, 1, 3 }, { 4, 2, 0 }, { 7, 6, 5 }, });

		int neighborsCount = 0;

		for (Board neighbor : b.neighbors())
			neighborsCount++;

		assertEquals(3, neighborsCount);
	}

	@Test
	public void neighborsLengthTest3_2() {
		Board b = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 }, });

		int neighborsCount = 0;

		for (Board neighbor : b.neighbors())
			neighborsCount++;

		assertEquals(4, neighborsCount);
	}

	@Test
	public void neighborsLengthTest3_3() {
		Board b = new Board(new int[][] { { 0, 1, 3 }, { 4, 8, 2 }, { 7, 6, 5 }, });

		int neighborsCount = 0;

		for (Board neighbor : b.neighbors())
			neighborsCount++;

		assertEquals(2, neighborsCount);
	}

	@Test
	public void neighborsTest3_1() {
		Board b = new Board(new int[][] { { 8, 1, 3 }, { 4, 2, 0 }, { 7, 6, 5 }, });

		Board expectedNeighbor = new Board(new int[][] { { 8, 1, 0 }, { 4, 2, 3 }, { 7, 6, 5 }, });

		Iterable<Board> actualNeighbors = b.neighbors();
		boolean neighborExists = false;

		for (Board an : actualNeighbors)
			if (an.equals(expectedNeighbor))
				neighborExists = true;

		assertTrue(neighborExists);
	}

	@Test
	public void neighborsTest3_2() {
		Board b = new Board(new int[][] { { 8, 1, 3 }, { 4, 2, 0 }, { 7, 6, 5 }, });

		Board expectedNeighbor = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 }, });

		Iterable<Board> actualNeighbors = b.neighbors();
		boolean neighborExists = false;

		for (Board an : actualNeighbors)
			if (an.equals(expectedNeighbor))
				neighborExists = true;

		assertTrue(neighborExists);
	}

	@Test
	public void neighborsTest3_3() {
		Board b = new Board(new int[][] { { 8, 1, 3 }, { 4, 2, 0 }, { 7, 6, 5 }, });

		Board expectedNeighbor = new Board(new int[][] { { 8, 1, 3 }, { 4, 2, 5 }, { 7, 6, 0 }, });

		Iterable<Board> actualNeighbors = b.neighbors();
		boolean neighborExists = false;

		for (Board an : actualNeighbors)
			if (an.equals(expectedNeighbor))
				neighborExists = true;

		assertTrue(neighborExists);
	}

	@Test
	public void neighborsDontContainItselfTest() {
		Board b = new Board(new int[][] { { 8, 1, 3 }, { 4, 2, 0 }, { 7, 6, 5 }, });

		Iterable<Board> actualNeighbors = b.neighbors();
		boolean neighborExists = false;

		for (Board an : actualNeighbors)
			if (an.equals(b))
				neighborExists = true;

		assertFalse(neighborExists);
	}

	@Test
	public void neighborsCornerLengthTest() {
		Board b = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }, });

		int neighborCount = 0;
		for (Board neighbor : b.neighbors())
			neighborCount++;

		assertEquals(2, neighborCount);
	}

	@Test
	public void neighborsCornerTest3_1() {
		Board b = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }, });

		Board expectedNeighbor = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 0 }, { 7, 8, 6 }, });

		boolean neighborExists = false;

		for (Board actualNeighbor : b.neighbors())
			if (actualNeighbor.equals(expectedNeighbor))
				neighborExists = true;

		assertTrue(neighborExists);
	}

	@Test
	public void neighborsCornerTest3_2() {
		Board b = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }, });

		Board expectedNeighbor = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 }, });

		boolean neighborExists = false;

		for (Board actualNeighbor : b.neighbors())
			if (actualNeighbor.equals(expectedNeighbor))
				neighborExists = true;

		assertTrue(neighborExists);
	}

}
