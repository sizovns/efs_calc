import org.junit.Test;
import service.CalculateService;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import static org.junit.Assert.assertEquals;

public class Test_Calc {

    @Test
    public void testPlus() {
        double res = CalculateService.calc("15+5");
        assertEquals(20.0, res, 0.0);
    }

    @Test
    public void testMinus() {
        double res = CalculateService.calc("15-5");
        assertEquals(10.0, res, 0.0);
    }

    @Test
    public void testMultiply() {
        double res = CalculateService.calc("15*5");
        assertEquals(75.0, res, 0.0);
    }

    @Test
    public void testDivide() {
        double res = CalculateService.calc("15/5");
        assertEquals(3.0, res, 0.0);
    }

    @Test
    public void testDivideZero() throws  ArithmeticException {
        Throwable thrown = catchThrowable(() ->  CalculateService.calc("15/0"));
        assertThat(thrown).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void testAbc() throws NumberFormatException {
        Throwable thrown = catchThrowable(() -> CalculateService.calc("abc"));
        assertThat(thrown).isInstanceOf(NumberFormatException.class);
    }

    @Test
    public void testDifficultMath() {
        double res = CalculateService.calc("20-3*(5-5)+(21-7)/(5-3)");
        assertEquals(27.0, res, 0.0);
    }

    @Test
    public void testDifficultDoubleMath() {
        double res = CalculateService.calc("20.004-3*(5-5)+(21.11-7.96)/(5.31-3.14)");
        assertEquals(26.06391, res, 0.0);
    }

}
