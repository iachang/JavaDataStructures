public class OffByN implements CharacterComparator {
    private int N;
    public OffByN(int N) {
        this.N = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if ((x + this.N) == (int) y || (x - this.N) == (int) y) {
            return true;
        }
        return false;
    }
}
