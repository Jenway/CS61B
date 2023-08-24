import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void testaddsizeempty() {
        ArrayDeque<String> dq = new ArrayDeque<>();
        assertTrue(dq.isEmpty());

        dq.addFirst("first");
        assertEquals(1, dq.size());

        dq.addLast("last");
        assertEquals(2, dq.size());

        dq.printDeque();
//        dq.printRawDeque();

        String first = dq.removeFirst();
//        dq.printRawDeque();

        assertEquals("first", first);

        String last = dq.removeLast();
        assertEquals("last", last);

        assertEquals(0, dq.size());
    }

    @Test
    public void testgrowshrink() {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < 16; i++) {
            dq.addLast(i);
        }
        for (int i = -16; i < 0; i++) {
            dq.addFirst(i);
        }
        for (int i = -1; i >= 16; i--) {
            assertEquals(Optional.of(i), dq.get(i));
        }
        for (int i = 0; i < 30; i++) {
            dq.printDeque();
//            dq.printRawDeque();
            dq.removeFirst();
        }
        assertEquals(2, dq.size());
        dq.printDeque();
    }

    @Test
    public void slideTest() {
        ArrayDeque<String> dq = new ArrayDeque<>();
        dq.addLast("a");
        dq.addLast("b");
        dq.addFirst("c");
        dq.addLast("d");
        dq.addLast("e");
        dq.printDeque();
//        dq.printRawDeque();
        dq.addFirst("f");
        dq.addLast("g");
        dq.addLast("h");
        dq.printDeque();
//        dq.printRawDeque();
        dq.addLast("Z");
        dq.printDeque();
//        dq.printRawDeque();
        dq.removeLast();
        dq.printDeque();
//        dq.printRawDeque();

    }

    @Test
    public void oneTwoThreeTest() {
//        [1,2,3,4,5,6,7,8]
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        dq.addLast(6);
        dq.addLast(7);
        dq.addFirst(5);
        dq.addLast(8);
        dq.addLast(1);
        dq.addFirst(4);
        dq.addLast(2);
        dq.addLast(3);
        dq.printDeque();
//        dq.printRawDeque();
        dq.addLast(9);
        dq.addFirst(7);
        dq.printDeque();
//        dq.printRawDeque();
    }

    @Test
    public void d003Test() {
        /* d003) AD-basic: Random addFirst/removeLast/isEmpty tests. */
        ArrayDeque<Integer> ad0 = new ArrayDeque<>();
        ad0.addFirst(0);
        ad0.addFirst(1);
        int temp = ad0.removeLast();
        System.out.println(temp);
        int temp2 = ad0.removeLast();
        System.out.println(temp2);
        ad0.addFirst(4);


    }
}
