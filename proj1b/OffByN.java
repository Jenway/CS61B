public class OffByN implements CharacterComparator {
    private int offset;
    public OffByN(int n){
        offset = n;
    }

    @Override
    public boolean equalChars(char x, char y){
        int diff = (int) x - (int) y;
        diff = (diff < 0)?-diff:diff;
        return (diff == offset);
    }
}
