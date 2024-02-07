import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {
    public static class TrieNode {
        private final Map<Character, TrieNode> children;
        private final List<String> names;
        private boolean isWord;

        TrieNode() {
            this.children = new HashMap<>();
            this.names = new ArrayList<>();
        }

        public Map<Character, TrieNode> getChildren() {
            return children;
        }

        public List<String> getNames() {
            return names;
        }

        public boolean isWord() {
            return isWord;
        }
    }

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void add(String key, String name) {
        TrieNode cur = root;
        for (char c : key.toCharArray()) {
            if (cur.children.containsKey(c)) {
                cur = cur.children.get(c);
            } else {
                TrieNode t = new TrieNode();
                cur.children.put(c, t);
                cur = t;
            }
        }
        cur.names.add(name);
        cur.isWord = true;
    }

    public TrieNode findNode(String key) {
        TrieNode cur = root;
        for (char c : key.toCharArray()) {
            TrieNode node = cur.children.get(c);
            cur = node;
            if (node == null) {
                break;
            }
        }
        return cur;
    }
}
