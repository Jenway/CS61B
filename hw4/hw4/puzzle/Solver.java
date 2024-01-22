package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import  java.util.Map;

public class Solver {

    private List<WorldState> solution = new ArrayList<>();

    private Map<WorldState, Integer> edtgCaches = new HashMap<>();

    private class SearchNode {
        private WorldState state;
        private int moves;

        private SearchNode prev;

        public SearchNode(WorldState state) {
            this.state = state;
            this.moves = 0;
            this.prev = null;
        }

        public SearchNode(WorldState state, int m, SearchNode p) {
            this.state = state;
            this.moves = m;
            this.prev = p;
        }

    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode a, SearchNode b) {
            int aEdt = getEdt(a);
            int bEdt = getEdt(b);
            int aPriority = a.moves + aEdt;
            int bPriority = b.moves + bEdt;
            return aPriority - bPriority;
        }
        private int getEdt(SearchNode sn) {
            if (!edtgCaches.containsKey(sn.state)) {
                edtgCaches.put(sn.state, sn.state.estimatedDistanceToGoal());
            }
            return edtgCaches.get(sn.state);
        }
    }


    public Solver(WorldState initial) {
        SearchNode currentNode = new SearchNode(initial);
        MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
        pq.insert(currentNode);
        while (!pq.isEmpty()) {
            currentNode = pq.delMin();
            if (currentNode.state.isGoal()) {
                break;
            }
            for (WorldState nextState : currentNode.state.neighbors()) {
                SearchNode newNode = new SearchNode(nextState, currentNode.moves + 1, currentNode);
                // A critical optimization checks that no enqueued WorldState is its own
                // grandparent
                if (currentNode.prev != null && nextState.equals(currentNode.prev.state)) {
                    continue;
                }
                pq.insert(newNode);
            }
        }
        Stack<WorldState> path = new Stack<>();
        for (SearchNode n = currentNode; n != null; n = n.prev) {
            path.push(n.state);
        }
        while (!path.isEmpty()) {
            solution.add(path.pop());
        }
    }

    public int moves() {
        return solution.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }
}
