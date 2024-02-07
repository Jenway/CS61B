import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> frequencyTable = new java.util.HashMap<Character, Integer>();
        for (char c : inputSymbols) {
            if (frequencyTable.containsKey(c)) {
                frequencyTable.put(c, frequencyTable.get(c) + 1);
            } else {
                frequencyTable.put(c, 1);
            }
        }
        return frequencyTable;
    }

    public static void main(String[] args) {

//        1: Read the file as 8 bit symbols.
        String test = "tas.txt";
        String filename = test;
        String newFilename = test + ".huf";
        char[] inputSymbols = FileUtils.readFile(filename);

//        2: Build frequency table.
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);

//        3: Use frequency table to construct a binary decoding trie.
        BinaryTrie bt = new BinaryTrie(frequencyTable);

//        4: Write the binary decoding trie to the .huf file.
        ObjectWriter ow = new ObjectWriter(newFilename);
//        ow.writeObject(bt);

//        5: (optional: write the number of symbols to the .huf file)
        ow.writeObject(inputSymbols.length);

//        6: Use binary trie to create lookup table for encoding.
        Map<Character, BitSequence> lookupTable = bt.buildLookupTable();

//        7: Create a list of bitsequences.
        List<BitSequence> bitSequence = new ArrayList<>();

//        8: For each 8 bit symbol:
//        Lookup that symbol in the lookup table.
//                Add the appropriate bit sequence to the list of bitsequences.
        for (Character symbol : inputSymbols) {
            bitSequence.add(lookupTable.get(symbol));
        }

//        9: Assemble all bit sequences into one huge bit sequence.
        BitSequence masterSequence = BitSequence.assemble(bitSequence);

//        10: Write the huge bit sequence to the .huf file.
        ow.writeObject(masterSequence);
    }
}
