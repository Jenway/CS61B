package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    boolean isCircle = false;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        edgeTo[1] = 0;
        marked[1] = true;
    }

    @Override
    public void solve() {
        solveHelper(1);
    }

    private void solveHelper(int i) {
        announce();
        for (int adj : maze.adj(i)) {
            if (adj != edgeTo[i]) {
                if (marked[adj]) {
                    edgeTo[adj] = i;
                    announce();
                    isCircle = true;
                    return;
                } else {
                    marked[adj] = true;
                    edgeTo[adj] = i;
                    solveHelper(adj);
                }
            }
            if (isCircle) {
                return;
            }
        }
    }

    // Helper methods go here
}

