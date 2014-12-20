package ch.epfl.sweng.test;

import ch.epfl.sweng.example.ExampleClass;
import junit.framework.TestCase;

/**
 * Sample test class.
 * @author SwengStaff
 *
 */
public class ExampleClassTest extends TestCase {

    private static final int FIRSTNUMBER = 1;
    private static final int SECONDNUMBER = 2;
    private static final int EXPECTEDSUM = 3;

    public void testAdd() {
        ExampleClass example = new ExampleClass();
        int sum;

        sum = example.add(FIRSTNUMBER, SECONDNUMBER);

        assertEquals(EXPECTEDSUM, sum);
    }
}
