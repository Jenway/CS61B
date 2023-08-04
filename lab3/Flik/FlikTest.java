import static org.junit.Assert.*;

import org.junit.Test;
public class FlikTest {

    @Test
    public void isSameNumberTest(){
        assertTrue(Flik.isSameNumber(2,2));
        assertTrue(Flik.isSameNumber(129,129));
        assertTrue(!Flik.isSameNumber(2,22));
    }

}
