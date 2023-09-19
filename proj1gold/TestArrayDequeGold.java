import static  org.junit.Assert.*;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestArrayDequeGold {

    @Test
    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> testArray = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> stdArray = new ArrayDequeSolution<>();
        String log = "";
        ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(BAOS);
// IMPORTANT: Save the old System.out!
        PrintStream old = System.out;

        for (int i = 0; i < 1000; i++) {
            if (stdArray.isEmpty()) {
                int addNumber = StdRandom.uniform(1000);
                boolean callFirst = (StdRandom.uniform(2)) == 0;
                if (callFirst) {
                    log = log + "addFirst(" + addNumber + ")\n";
                    testArray.addFirst(addNumber);
                    stdArray.addFirst(addNumber);
                } else {
                    log = log + "addLast(" + addNumber + ")\n";
                    testArray.addLast(addNumber);
                    stdArray.addLast(addNumber);
                }
            } else {
                int x = StdRandom.uniform(6);
                int addNumber = StdRandom.uniform(1000);
                Integer testRemoveNumber = 1;
                Integer stdRemoveNumber = 1;
                switch (x) {
                    case 0:
                        log = log + "addFirst(" + addNumber + ")\n";
                        testArray.addFirst(addNumber);
                        stdArray.addFirst(addNumber);
                        break;
                    case 1:
                        log = log + "addLast(" + addNumber + ")\n";
                        testArray.addLast(addNumber);
                        stdArray.addLast(addNumber);
                        break;
                    case 2:
                        log = log + "removeFirst()\n";
                        testRemoveNumber = testArray.removeFirst();
                        stdRemoveNumber = stdArray.removeFirst();
                        break;
                    case 3:
                        log = log + "removeLast()\n";
                        testRemoveNumber = testArray.removeLast();
                        stdRemoveNumber = stdArray.removeLast();
                        break;
                    default:
                }
                if (x == 4) {
                    log = log +"printDeque()\n";
                    System.setOut(ps);
                    testArray.printDeque();
                    String testString = ps.toString();
                    System.out.flush();
                    stdArray.printDeque();
                    String stdString = ps.toString();
                    System.out.flush();
                    System.setOut(old);
                    assertEquals(log,stdString,testString);
                } else {
                    assertEquals(log, stdRemoveNumber, testRemoveNumber);
                }
            }
        }

    }
}
