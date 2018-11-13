import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 
 * @author Pedro Guilherme S. Moreira
 *
 */
public class Solver {
	
	/**
	 * Find a solution to the {@code initial} board (using the A* algorithm).
	 * 
	 * @param initial a {@link Board} object with the initial configuration
	 */
	public Solver(Board initial) {
		
	}
	
	public boolean isSolvable() {
		throw new UnsupportedOperationException();
	}
	
	public int moves() {
		throw new UnsupportedOperationException();
	}
	
	public Iterable<Board> solution() {
		throw new UnsupportedOperationException();
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
	
}
