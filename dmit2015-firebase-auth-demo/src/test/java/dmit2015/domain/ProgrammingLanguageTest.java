package dmit2015.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProgrammingLanguageTest {
    @Test // ^ is not the power operator, use Math.pow() method instead
    public void powerOperator_ReturnsCorrectResults() {
        assertEquals(8, Math.pow(2, 3));
    }
    @Test // to avoid integer division errors divide one of the operands by zero
    public void divideOperator_IntegerDivision_ReturnsCorrectResults() {
        assertEquals(0.80, 4.0 / 5, 0);
    }
    @Test // this version of Java allows you to compare strings using == operator
    public void equalOperator_StringCompare_ReturnsCorrectResults() {
        assertTrue("dmit2015" == "dmit2015");
    }
    @Test // an ArithmeticException is thrown when you try to divide by zero
    public void divideOperator_DivisionByZero_ThrowsArithmeticException() {
        Exception ex = assertThrows(ArithmeticException.class, () -> {
            assertEquals(0, 3 / 0);
        });
        assertEquals("/ by zero", ex.getMessage());
    }
}