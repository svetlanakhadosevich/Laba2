package ea.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SH
 */
class CalculatorTest {
    @Test
    public void TestCalc() throws Exception {
        Calculator calculator = new Calculator();
        List<String> tmpOne = new ArrayList<>();
        String [] args = new String[0];
        tmpOne.add(Double.toString(calculator.result("5+7+6/2")));
        tmpOne.add(Double.toString(calculator.result("5+7+6*2")));
        tmpOne.add(Double.toString(calculator.result("(30*8)/10")));
        List<String> tmpTwo = new ArrayList<>();
        tmpTwo.add("15");
        tmpTwo.add("24");
        tmpTwo.add("24");
        Assert.assertEquals(tmpOne, tmpTwo);
    }
}