public class OffByOne implements CharacterComparator{

    @Override
    public boolean equalChars(char x, char y){
        int offset = (int) x - (int) y;
        offset = (offset < 0)?-offset:offset;
        return (offset == 1);
    }
}
