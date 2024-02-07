import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BinaryTrie implements Serializable {

    private final Node root;

    // Huffman trie node
    private static class Node implements Comparable<Node> {

        private final char ch;
        private final int freq;
        private final BinaryTrie.Node left, right;

        Node(char ch, int freq, BinaryTrie.Node left, BinaryTrie.Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        for (char ch : frequencyTable.keySet()) {
            if (frequencyTable.get(ch) > 0) {
                pq.add(new Node(ch, frequencyTable.get(ch), null, null));
            }
        }
        while (pq.size() > 1) {
            Node left = pq.remove();
            Node right = pq.remove();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.add(parent);
        }
        root = pq.remove();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        Node x = root;
        BitSequence query = new BitSequence();
        for (int i = 0; i < querySequence.length(); i++) {
            if (x.isLeaf()) {
                return new Match(query, x.ch);
            }
            if (querySequence.bitAt(i) == 0) {
                x = x.left;
                query = query.appended(0);
            } else {
                x = x.right;
                query = query.appended(1);
            }
        }
        return new Match(query, x.ch);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> st = new HashMap<Character, BitSequence>();
        buildLookupTable(root, new BitSequence(), st);
        return st;
    }

    private void buildLookupTable(Node x, BitSequence prefix, Map<Character, BitSequence> st) {
        if (x.isLeaf()) {
            st.put(x.ch, prefix);
        } else {
            buildLookupTable(x.left, prefix.appended(0), st);
            buildLookupTable(x.right, prefix.appended(1), st);
        }
    }
}