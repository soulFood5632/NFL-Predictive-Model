
import NFLData.dataanalyzer.ValueHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ValueHolderTests {

    @Test
    public void basicTest(){
        ValueHolder value = new ValueHolder(0.735, 10);
        Assertions.assertEquals(0.74, value.getWins(), 0.001);
        Assertions.assertEquals(10, value.getValue(), 0.001);
    }

    @Test
    public void testImmutability(){
        ValueHolder value = new ValueHolder(0.735, 10);
        double t = value.getValue();
        t++;
        Assertions.assertEquals(10, value.getValue(), 0.001);
        Assertions.assertEquals(11D, t, 0.001);
    }
}
