import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Pedro Guilherme S. Moreira
 *
 */
public class SolverTest {

	private static Solver solvable;
	private static Solver unsolvable;
	private static Solver anotherSolvable;
	private static Solver anotherUnsolvable;
	private static Solver puzzle17;

	@BeforeClass
	public static void initAllSolvers() {
		solvable = new Solver(new Board(new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 }, }));

		unsolvable = new Solver(new Board(new int[][] { { 1, 2, 3 }, { 4, 6, 5 }, { 7, 8, 0 }, }));

		anotherSolvable = new Solver(
				new Board(new int[][] { { 1, 2, 4, 12 }, { 5, 6, 3, 0 }, { 9, 10, 8, 7 }, { 13, 14, 11, 15 }, }));

		anotherUnsolvable = new Solver(
				new Board(new int[][] { { 3, 2, 4, 8 }, { 1, 6, 0, 12 }, { 5, 10, 7, 11 }, { 9, 13, 14, 15 }, }));

		puzzle17 = new Solver(new Board(new int[][] { { 5, 1, 8 }, { 2, 7, 3 }, { 4, 0, 6 }, }));
	}

	@Test
	public void testSolvable() {
		assertTrue(solvable.isSolvable());
	}

	@Test
	public void testUnsolvable() {
		assertFalse(unsolvable.isSolvable());
	}

	@Test
	public void testAnotherUnsolvable() {
		assertFalse(anotherUnsolvable.isSolvable());
	}

	@Test
	public void testSolvableMoves() {
		assertEquals(4, solvable.moves());
	}

	@Test
	public void testUnsolvableMoves() {
		assertEquals(-1, unsolvable.moves());
	}

	@Test
	public void testUnsolvableSolutionIsNull() {
		assertNull(unsolvable.solution());
	}

	@Test
	public void testSolutionLength() {
		int solutionLength = 0;
		for (Board sol : solvable.solution())
			solutionLength++;
		assertEquals(5, solutionLength);
	}

	@Test
	public void testIllegalArgumentExceptionWhenNull() {
		try {
			Solver s = new Solver(null);
			fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testAnotherMoves() {
		assertEquals(10, anotherSolvable.moves());
	}

	@Test
	public void testAnotherSolutionLength() {
		int solutionLength = 0;
		for (Board sol : anotherSolvable.solution())
			solutionLength++;
		assertEquals(11, solutionLength);
	}

	@Test
	public void testPuzzle17Moves() {
		assertEquals(17, puzzle17.moves());
	}

	@Test
	public void testPuzzle17Solution() {
		int solutionLength = 0;
		for (Board sol : puzzle17.solution())
			solutionLength++;
		assertEquals(18, solutionLength);
	}

}
