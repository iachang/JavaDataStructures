public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        if ((x + 1) == (int) y || (x - 1) == (int) y) {
            return true;
        }
        return false;
    }
}
