
public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        int invert = word.length() - 1;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != word.charAt(invert - i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int invert = word.length() - 1;
        for (int i = 0; i < word.length(); i++) {
            if ((invert - i != i) && !cc.equalChars(word.charAt(i), word.charAt(invert - i))) {
                return false;
            }
        }
        return true;
    }
}
