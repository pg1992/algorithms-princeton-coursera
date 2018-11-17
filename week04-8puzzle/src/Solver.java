import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * 
 * @author Pedro Guilherme S. Moreira
 *
 */
public class Solver {

	private final Comparator<PQNode> PRIORITY_COMPARISON = new PriorityComparison();

	private MinPQ<PQNode> mainMoves = new MinPQ<>(PRIORITY_COMPARISON);
	private MinPQ<PQNode> twinMoves = new MinPQ<>(PRIORITY_COMPARISON);

	private Deque<PQNode> steps = new LinkedList<>();

	private boolean solvable;

	/**
	 * Find a solution to the {@code initial} board (using the A* algorithm).
	 * 
	 * @param initial a {@link Board} object with the initial configuration
	 */
	public Solver(Board initial) {
		if (initial == null)
			throw new IllegalArgumentException("Argument cannot be null");

		PQNode cur;
		PQNode curTwin;

		mainMoves.insert(new PQNode(initial, null, 0));
		twinMoves.insert(new PQNode(initial.twin(), null, 0));
		steps.addFirst(new PQNode(initial, null, 0));

		while (true) {
			cur = mainMoves.delMin();
			curTwin = twinMoves.delMin();

			steps.addLast(cur);

			if (cur.board.isGoal()) {
				solvable = true;
				break;
			}

			if (curTwin.board.isGoal()) {
				solvable = false;
				break;
			}

			for (Board neighbor : cur.board.neighbors()) {
				if (!neighbor.equals(cur.prev))
					mainMoves.insert(new PQNode(neighbor, cur.board, cur.moves + 1));
			}

			for (Board neighbor : curTwin.board.neighbors()) {
				if (!neighbor.equals(curTwin.prev))
					twinMoves.insert(new PQNode(neighbor, curTwin.board, curTwin.moves + 1));
			}
		}

		Deque<PQNode> revert = new LinkedList<>();
		revert.addLast(steps.removeLast());

		while (!revert.peekFirst().board.equals(steps.peekFirst().board)) {
			cur = steps.removeLast();
			if (cur.board.equals(revert.peekFirst().prev))
				revert.addFirst(cur);
		}

		steps = revert;
	}

	public boolean isSolvable() {
		return solvable;
	}

	public int moves() {
		if (!solvable)
			return -1;
		return steps.peekLast().moves;
	}

	public Iterable<Board> solution() {
		if (!solvable)
			return null;

		Deque<Board> boardSteps = new LinkedList<>();

		for (PQNode pqn : steps)
			boardSteps.addLast(pqn.board);

		return boardSteps;
	}

	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}

	private class PQNode {
		final Board board;
		final Board prev;
		final int moves;

		PQNode(Board board, Board prev, int moves) {
			this.board = board;
			this.prev = prev;
			this.moves = moves;
		}

		int priority() {
			return board.manhattan() + moves;
		}

		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();

			s.append(String.format("priority  = %2d\n", priority()));
			s.append(String.format("moves     = %2d\n", moves));
			s.append(String.format("manhattan = %2d\n", board.manhattan()));
			s.append(board);

			return s.toString();
		}
	}

	private class PriorityComparison implements Comparator<PQNode> {

		@Override
		public int compare(PQNode n1, PQNode n2) {
			return n1.priority() - n2.priority();
		}

	}

}
