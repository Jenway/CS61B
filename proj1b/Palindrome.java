import java.util.Objects;

public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> words = new LinkedListDeque<>();
        for (int i =0;i<word.length();i++){
            words.addLast(word.charAt(i));
        }
        return words;
    }

    private boolean isPalindromeHelper(Deque<Character> words,CharacterComparator cc) {
        Character first = words.removeFirst();
        Character last = words.removeLast();
        if (first == null || last ==null){
            return true;
        } else if(cc.equalChars(first,last)) {
            return isPalindromeHelper(words,cc);
        } else {
            return false;
        }
    }
    public boolean isPalindrome(String word) {
        Deque<Character> words = wordToDeque(word);
        return isPalindromeHelper(words, Objects::equals);

    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> words = wordToDeque(word);
        return isPalindromeHelper(words, cc);
    }

}

//        Without Deque version for Task 3
//        boolean flag = true;
//        int len = word.length();
//        for(int i =0;i < (len / 2);i++){
//            if(word.charAt(i) != word.charAt(len - i -1)) {
//                flag =false;
//                break;
//            }
//        }
//        return flag;