import edu.princeton.cs.algs4.Queue;

/**
 * @author Pedro Guilherme S. Moreira
 */

public class Board {

	/**
	 * The core of the immutability of {@link Board}.
	 */
	private final int[][] BLOCKS;
	/**
	 * Holds the dimension since the instance creation.
	 */
	private final int N;
	/**
	 * The index of the hole in the board.
	 */
	private final int iHole, jHole;

	/**
	 * Construct a board from an {@code n}-by-{@code n} array of blocks.
	 * 
	 * @param blocks {@code n}-by-{@code n} array of blocks with values in the range
	 *               [0, {@code n}^2).
	 */
	public Board(int[][] blocks) {
		int i = 0;
		int j = 0;

		N = blocks.length;
		BLOCKS = copyMatrix(blocks);

		outer: for (i = 0; i < N; i++)
			for (j = 0; j < N; j++)
				if (blocks[i][j] == 0)
					break outer;

		iHole = i;
		jHole = j;
	}

	/**
	 * Return the dimension of the immutable board.
	 * 
	 * @return board dimension {@code N}
	 */
	public int dimension() {
		return N;
	}

	/**
	 * Finds the Hamming cost heuristics for the board.
	 * 
	 * @return number of blocks out of place
	 */
	public int hamming() {
		int accum = 0;
		int[][] goal = generateGoal(N);

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (BLOCKS[i][j] == 0)
					continue;
				accum += (BLOCKS[i][j] != goal[i][j]) ? 1 : 0;
			}

		return accum;
	}

	/**
	 * 
	 * @return sum of Manhattan distances between blocks and goal
	 */
	public int manhattan() {
		int accum = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (BLOCKS[i][j] == 0)
					continue;

				int ir = (BLOCKS[i][j] - 1) / N;
				int jr = (BLOCKS[i][j] - 1) % N;

				accum += Math.abs(i - ir) + Math.abs(j - jr);
			}
		}

		return accum;
	}

	/**
	 * Is this board the goal board?
	 * 
	 * @return {@code true} if it is the goal
	 */
	public boolean isGoal() {
		return this.hamming() == 0;
	}

	/**
	 * Generates a board that is obtained by exchanging any pair of blocks.
	 * 
	 * @return a {@link Board} with the same configuration but with the first 2
	 *         horizontal adjacent blocks exchanged
	 */
	public Board twin() {
		int[][] modified = new int[N][N];
		boolean exch = false;

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				modified[i][j] = BLOCKS[i][j];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N - 1; j++) {
				if (modified[i][j] != 0 && modified[i][j + 1] != 0) {
					int tmp = modified[i][j];
					modified[i][j] = modified[i][j + 1];
					modified[i][j + 1] = tmp;
					exch = true;
					break;
				}
			}
			if (exch)
				break;
		}

		return new Board(modified);
	}

	/**
	 * Does this board equals {@code other}?
	 */
	@Override
	public boolean equals(Object other) {
		if (other == this)
			return true;
		if (other == null)
			return false;
		if (other.getClass() != this.getClass())
			return false;

		Board that = (Board) other;

		if (that.N != this.N)
			return false;

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (that.BLOCKS[i][j] != this.BLOCKS[i][j])
					return false;

		return true;
	}

	/**
	 * 
	 * @return all neighboring boards
	 */
	public Iterable<Board> neighbors() {
		int[][] tmp = copyMatrix(BLOCKS);
		int[][] possiblePositions = new int[][] { { iHole - 1, jHole }, { iHole + 1, jHole }, { iHole, jHole - 1 },
				{ iHole, jHole + 1 }, };
		Queue<Board> neighborsQueue = new Queue<>();

		for (int[] pos : possiblePositions) {
			int i = pos[0];
			int j = pos[1];

			if (i < 0 || i >= N)
				continue;
			if (j < 0 || j >= N)
				continue;

			swapMatrix(tmp, new int[] { iHole, jHole }, new int[] { i, j });
			neighborsQueue.enqueue(new Board(tmp));
			swapMatrix(tmp, new int[] { iHole, jHole }, new int[] { i, j });
		}

		return neighborsQueue;
	}

	/**
	 * String representation of this board.
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", BLOCKS[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	/**
	 * Generates a goal board given the {@code size}.
	 * 
	 * @param size the size of the goal board
	 * @return the array representing the blocks of a goal board
	 */
	private int[][] generateGoal(int size) {
		int[][] output = new int[size][size];
		int counter = 0;

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				output[i][j] = (++counter) % (size * size);

		return output;
	}

	/**
	 * Swap the elements of the multidimensional array {@code mat} specified by the
	 * indexes {@code first} and {@code second}.
	 * 
	 * @param mat    multidimensional array that will have the elements swapped
	 * @param first  index of the first element
	 * @param second index of the second element
	 */
	private void swapMatrix(int[][] mat, int[] first, int[] second) {
		int tmp = mat[first[0]][first[1]];
		mat[first[0]][first[1]] = mat[second[0]][second[1]];
		mat[second[0]][second[1]] = tmp;
	}

	/**
	 * Creates a copy of a matrix and returns it.
	 * 
	 * @param orig the original matrix that will be copied
	 * @return a copy of the original matrix
	 */
	private int[][] copyMatrix(int[][] orig) {
		int[][] clone = new int[orig.length][];

		for (int i = 0; i < orig.length; i++) {
			clone[i] = new int[orig[i].length];
			for (int j = 0; j < orig[i].length; j++)
				clone[i][j] = orig[i][j];
		}

		return clone;
	}

}
